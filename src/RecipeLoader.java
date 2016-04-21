import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeLoader
{
   private Set<Recipe> directory;

   public static void main(String[] args)
   {
      new RecipeLoader("recipes");
   }

   public RecipeLoader(String path)
   {
      directory = new HashSet<>();
      File[] listFiles = new File(path).listFiles();
      if (listFiles == null) {return;}
      for (File f : listFiles)
      {
         try {parseRecipe(f);} catch (Exception e) {e.printStackTrace();}
      }
   }

   private void parseRecipe(File input) throws FileNotFoundException,
         NumberFormatException
   {
      Recipe newRecipe = new Recipe();
      Scanner in = new Scanner(input);
      StringBuilder sb = new StringBuilder();
      while (in.hasNextLine()) {sb.append(in.nextLine());}
      String recipeText = sb.toString();
      if (isRecipe(recipeText))
      {
         newRecipe.setName(getRecipeName(recipeText));
         for (String s : getIngredientList(getIngredients(recipeText)))
         {
            newRecipe.addIngredient(getIngredient(s));
            newRecipe.addAmount(getIngredientAmount(s));
         }
         for (String s : getDirectionList(getDirections(recipeText)))
         {
            newRecipe.addDirection(s);
         }
      }
      directory.add(newRecipe);
   }

   public Set<Recipe> getDirectory()
   {
      return directory;
   }

   private boolean isRecipe(String s)
   {
      Pattern p = Pattern.compile("<recipe>([\\s\\S]*?)</recipe>");
      Matcher m = p.matcher(s);
      return m.matches();
   }

   private String getRecipeName(String s)
   {
      Pattern p = Pattern.compile("<recipeName>([\\s\\S]*?)</recipeName>");
      Matcher m = p.matcher(s);
      m.find();
      return m.group(1);
   }

   private String getIngredients(String s)
   {
      Pattern p = Pattern.compile("<ingredients>([\\s\\S]*?)</ingredients>");
      Matcher m = p.matcher(s);
      m.find();
      return m.group(1);
   }

   private List<String> getIngredientList(String s)
   {
      Pattern p = Pattern.compile("<i>([\\s\\S]*?)</i>");
      Matcher m = p.matcher(s);
      List<String> ingredientList = new ArrayList<>();
      while (m.find()) {ingredientList.add(m.group(1));}
      return ingredientList;
   }

   private Ingredient getIngredient(String s)
   {
      Pattern p = Pattern.compile("<n>([\\s\\S]*?)</n>");
      Matcher m = p.matcher(s);
      m.find();
      return new Ingredient(m.group(1));
   }

   private IngredientAmount getIngredientAmount(String s) throws
         NumberFormatException
   {
      Pattern p =
            Pattern.compile("<q>([\\s\\S]*?)</q>[\\s\\S]*?<u>([\\s\\S]*?)</u>");
      Matcher m = p.matcher(s);
      m.find();
      return new IngredientAmount(Double.parseDouble(m.group(1)), m.group(2));
   }

   private String getDirections(String s)
   {
      Pattern p = Pattern.compile("<directions>([\\s\\S]*?)</directions>");
      Matcher m = p.matcher(s);
      m.find();
      return m.group(1);
   }

   private List<String> getDirectionList(String s)
   {
      Pattern p = Pattern.compile("<d>([\\s\\S]*?)</d>");
      Matcher m = p.matcher(s);
      List<String> directionList = new ArrayList<>();
      while (m.find()) {directionList.add(m.group(1));}
      return directionList;
   }
}
