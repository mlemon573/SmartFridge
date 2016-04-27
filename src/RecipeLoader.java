import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeLoader
{
   private List<Recipe> directory;
   private DocumentBuilder docBuilder;

   public static void main(String[] args)
   {
      RecipeLoader recipeLoader = new RecipeLoader("recipes");
      List<Recipe> recipeList = recipeLoader.getDirectory();
      Map<Integer, Set<Ingredient>> sortedMap =
            IngredientFrequency.getSortedMap(recipeList);
      System.out.println("Ingredients:");
      System.out.println("------------------------");
      System.out.println();
      for (Integer freq : sortedMap.keySet())
      {
         for (Ingredient ing : sortedMap.get(freq))
         {
            System.out.printf("%-4d %s\n", freq, ing.getName());
         }
      }

      System.out.println("\n\nRecipes:");
      System.out.println("------------------------");
      for (Recipe r : recipeList)
      {
         System.out.println();
         System.out.println("Recipe:");
         System.out.printf("%s%n", r.getName());
         System.out.println("Ingredients:");
         Map<Ingredient, IngredientAmount> ingredientMap = r.getIngredientMap();
         for (Ingredient i : ingredientMap.keySet())
         {
            IngredientAmount ia = ingredientMap.get(i);
            NumberFormat nf = new DecimalFormat("#.##");
            System.out.printf("%-6s%-12s%s%n", nf.format(ia.getQuantity()), ia
                  .getUnit(), i.getName());
         }
         System.out.println("Directions:");
         List<String> directions = r.getDirections();
         for (int i = 1; i <= directions.size(); i++)
         {System.out.printf("%-6s%s%n", i + ")", directions.get(i - 1));}
         System.out.println("------------------------");
      }
   }

   public RecipeLoader(String path)
   {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      try {docBuilder = dbf.newDocumentBuilder();}
      catch (ParserConfigurationException e) {e.printStackTrace();}
      directory = new ArrayList<>();
      File[] listFiles = new File(path).listFiles();
      if (listFiles == null) {return;}
      for (File f : listFiles)
      {
         try {parseRecipe(f);} catch (Exception e) {e.printStackTrace();}
      }
   }

   private void parseRecipe(File input) throws NumberFormatException,
         SAXException, IOException
   {
      Recipe newRecipe = new Recipe();
      Document doc = docBuilder.parse(input);
      doc.getDocumentElement().normalize();
      if ("recipe".equals(doc.getDocumentElement().getNodeName()))
      {
         NodeList nList;
         Node nNode, pNode;
         Element eElement;

         pNode = doc.getElementsByTagName("recipeName").item(0);
         String name = pNode.getTextContent();
         newRecipe.setName(name);

         pNode = doc.getElementsByTagName("ingredients").item(0);
         if (pNode.getNodeType() == Node.ELEMENT_NODE)
         {
            nList = ((Element) pNode).getElementsByTagName("i");
            for (int i = 0; i < nList.getLength(); i++)
            {
               nNode = nList.item(i);
               if (nNode.getNodeType() != Node.ELEMENT_NODE) {continue;}
               eElement = (Element) nNode;
               String ingredientName =
                     eElement.getElementsByTagName("n").item(0).getTextContent();
               String quantityText =
                     eElement.getElementsByTagName("q").item(0).getTextContent();
               String unit =
                     eElement.getElementsByTagName("u").item(0).getTextContent();
               double quantity = Double.parseDouble(quantityText);
               Ingredient ingredient = new Ingredient(ingredientName);
               IngredientAmount ia = new IngredientAmount(quantity, unit);
               newRecipe.addIngredient(ingredient, ia);
            }
         }

         pNode = doc.getElementsByTagName("directions").item(0);
         if (pNode.getNodeType() == Node.ELEMENT_NODE)
         {
            nList = ((Element) pNode).getElementsByTagName("d");
            for (int i = 0; i < nList.getLength(); i++)
            {
               String direction = nList.item(i).getTextContent();
               newRecipe.addDirection(direction);
            }
         }

         directory.add(newRecipe);
      }
   }

   public List<Recipe> getDirectory()
   {
      return directory;
   }
}