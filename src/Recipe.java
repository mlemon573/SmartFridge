import java.util.ArrayList;
import java.util.List;

public class Recipe
{
   private String name;
   private List<String> directions;
   private List<Ingredient> ingredients;
   private List<IngredientAmount> amounts;

   public Recipe()
   {
      directions = new ArrayList<>();
      ingredients = new ArrayList<>();
      amounts = new ArrayList<>();
   }

   public String getName()
   {
      return name;
   }

   public List<String> getDirections()
   {
      return directions;
   }

   public List<Ingredient> getIngredients()
   {
      return ingredients;
   }

   public List<IngredientAmount> getAmounts()
   {
      return amounts;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void addDirection(String content)
   {
      directions.add(content);
   }

   public void addIngredient(Ingredient in)
   {
      ingredients.add(in);
   }

   public void addAmount(IngredientAmount ia)
   {
      amounts.add(ia);
   }

   public boolean isValid()
   {
      return ingredients.size() == amounts.size();
   }
}
