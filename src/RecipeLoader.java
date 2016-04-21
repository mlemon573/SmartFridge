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
      if (matchesRegex("recipe", recipeText))
      {
         newRecipe.setName(getRegex("recipeName", recipeText));
         for (String s : getRegexList("i", getRegex("ingredients", recipeText)))
         {
            newRecipe.addIngredient(new Ingredient(getRegex("n", s)));

            double quantity = Double.parseDouble(getRegex("q", s));
            String unit = getRegex("u", s);
            newRecipe.addAmount(new IngredientAmount(quantity, unit));
         }
         for (String s : getRegexList("d", getRegex("directions", recipeText)))
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

   private Matcher getMatcher(String tag, String searchText)
   {
      String formattedPattern =
            String.format("<%s>([\\s\\S]*?)</%s>", tag, tag);
      Pattern pattern = Pattern.compile(formattedPattern);
      return pattern.matcher(searchText);
   }

   private boolean matchesRegex(String tag, String searchText)
   {
      Matcher m = getMatcher(tag, searchText);
      return m.matches();
   }

   private List<String> getRegexList(String tag, String searchText)
   {
      Matcher m = getMatcher(tag, searchText);
      List<String> regexList = new ArrayList<>();
      while (m.find()) {regexList.add(m.group(1));}
      return regexList;
   }

   private String getRegex(String tag, String searchText)
   {
      Matcher m = getMatcher(tag, searchText);
      if (m.find()) {return m.group(1);}
      else {return "";}
   }
}
