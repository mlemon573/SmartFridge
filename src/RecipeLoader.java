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
import java.util.HashSet;
import java.util.Set;

public class RecipeLoader
{
   private Set<Recipe> directory;
   private DocumentBuilder docBuilder;

   public static void main(String[] args)
   {
      new RecipeLoader("recipes");
   }

   public RecipeLoader(String path)
   {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      try {docBuilder = dbf.newDocumentBuilder();}
      catch (ParserConfigurationException e) {e.printStackTrace();}
      directory = new HashSet<>();
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
               String ingredient =
                     eElement.getElementsByTagName("n").item(0).getTextContent();
               String quantityText =
                     eElement.getElementsByTagName("q").item(0).getTextContent();
               String unit =
                     eElement.getElementsByTagName("u").item(0).getTextContent();
               double quantity = Double.parseDouble(quantityText);
               newRecipe.addIngredient(new Ingredient(ingredient));
               newRecipe.addAmount(new IngredientAmount(quantity, unit));
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

   public Set<Recipe> getDirectory()
   {
      return directory;
   }
}