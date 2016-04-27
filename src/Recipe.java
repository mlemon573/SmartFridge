import java.util.*;

public class Recipe
{
   private String name;
   private List<String> directions;
   private Map<Ingredient, IngredientAmount> ingredients;

   public Recipe()
   {
      directions = new ArrayList<>();
      ingredients = new TreeMap<>();
   }

   public String getName()
   {
      return name;
   }

   public List<String> getDirections()
   {
      return directions;
   }

   public Set<Ingredient> getIngredients()
   {
      return ingredients.keySet();
   }

   public Collection<IngredientAmount> getAmounts()
   {
      return ingredients.values();
   }

   public Map<Ingredient, IngredientAmount> getIngredientMap()
   {
      return ingredients;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void addDirection(String content)
   {
      directions.add(content);
   }

   public void addIngredient(Ingredient ing, IngredientAmount ia)
   {
      ingredients.put(ing, ia);
   }

   public boolean contains(Ingredient i)
   {
      return ingredients.containsKey(i);
   }
}