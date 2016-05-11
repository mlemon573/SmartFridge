import java.util.*;

/**
 * Represents a recipe, including name of the recipe, fully qualified ingredients,
 * and directions
 */
public class Recipe
{
   private String name;
   private List<String> directions;
   private Map<Ingredient, IngredientAmount> ingredients;

   /**
    * Constructor method for Recipe
    */
   public Recipe()
   {
      directions = new ArrayList<>();
      ingredients = new TreeMap<>();
   }

   /**
    * Accessor method to get the directions of the recipe
    *
    * @return List of directions
    */
   public List<String> getDirections()
   {
      return directions;
   }

   /**
    * Accessor method to get the ingredients of the recipe
    * (without amounts)
    *
    * @return Set containing the ingredients
    */
   public Set<Ingredient> getIngredients()
   {
      return ingredients.keySet();
   }

   /**
    * Accessor method to get the fully qualified ingredients of the recipe
    *
    * @return a map that has Ingredient objects for keys and IngredientAmount
    * objects for the values
    */
   public Map<Ingredient, IngredientAmount> getIngredientMap()
   {
      return ingredients;
   }

   /**
    * Mutator method to set the name of the recipe
    *
    * @param name name of the recipe
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * Adds a new direction to the recipe
    *
    * @param content a single line of the directions
    */
   public void addDirection(String content)
   {
      directions.add(content);
   }

   /**
    * Adds a new ingredient to the recipe
    *
    * @param ing an Ingredient object representing the ingredient to add
    * @param ia  an IngredientAmount object representing the amount of the
    *            ingredient to add
    */
   public void addIngredient(Ingredient ing, IngredientAmount ia)
   {
      ingredients.put(ing, ia);
   }

   /**
    * Determines if this recipe contains a given ingredient
    *
    * @param i ingredient to check for
    * @return if the recipe contains the ingredient
    */
   private boolean contains(Ingredient i)
   {
      return ingredients.containsKey(i);
   }

   /**
    * Determines if this recipe is possible with the given ingredients
    *
    * @param selectedIngredients ingredients used to determine if possible
    * @return if the recipe is possible
    */
   public boolean isPossible(List<Ingredient> selectedIngredients)
   {
      int matches = 0;
      for (Ingredient i : selectedIngredients)
      {
         if (contains(i)) {matches++;}
         if (matches == ingredients.size()) {return true;}
      }
      return false;
   }

   /**
    * Returns the name of the object
    *
    * @return a string representation of the object.
    */
   @Override
   public String toString()
   {
      return name;
   }
}