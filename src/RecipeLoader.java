import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads all valid recipes from a given folder
 */
public class RecipeLoader
{
   private List<Recipe> directory;
   private DocumentBuilder docBuilder;

   /**
    * Constructor method for RecipeLoader
    *
    * @param path relative path to search under to find recipes
    */
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

   /**
    * Parses through a given File to extract a valid recipe
    *
    * @param input File object to search through
    * @throws Exception if there is an issue when parsing through the recipe, such
    *                   as if key components are missing
    */
   private void parseRecipe(File input) throws Exception
   {
      Exception e = new Exception("malformed recipe in " + input.getName());
      Recipe newRecipe = new Recipe();
      Document doc = docBuilder.parse(input);
      doc.getDocumentElement().normalize();
      if ("recipe".equals(doc.getDocumentElement().getNodeName()))
      {
         NodeList nList;
         Node nNode, pNode;
         Element eElement;

         pNode = doc.getElementsByTagName("recipeName").item(0);
         if (pNode != null)
         {
            String name = pNode.getTextContent();
            newRecipe.setName(name);
         }
         else {throw e;}

         pNode = doc.getElementsByTagName("ingredients").item(0);
         if (pNode != null && pNode.getNodeType() == Node.ELEMENT_NODE)
         {
            nList = ((Element) pNode).getElementsByTagName("i");
            if (nList.getLength() == 0) {throw e;}
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
         else {throw e;}

         pNode = doc.getElementsByTagName("directions").item(0);
         if (pNode != null && pNode.getNodeType() == Node.ELEMENT_NODE)
         {
            nList = ((Element) pNode).getElementsByTagName("d");
            if (nList.getLength() == 0) {throw e;}
            for (int i = 0; i < nList.getLength(); i++)
            {
               String direction = nList.item(i).getTextContent();
               newRecipe.addDirection(direction);
            }
         }
         else {throw e;}

         directory.add(newRecipe);
      }
   }

   /**
    * Accessor method to get the list of valid recipes
    *
    * @return List of recipes
    */
   public List<Recipe> getDirectory()
   {
      return directory;
   }
}