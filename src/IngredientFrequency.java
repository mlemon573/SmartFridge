import java.util.*;

public class IngredientFrequency
{
   public static Map<Integer, Set<Ingredient>> sort(List<Recipe> recipeList)
   {
      Map<Ingredient, Integer> ingredientFrequencyMap = new TreeMap<>();
      for (Recipe r : recipeList)
      {
         for (Ingredient ingredient : r.getIngredients())
         {
            if (ingredientFrequencyMap.containsKey(ingredient))
            {
               ingredientFrequencyMap.put(ingredient,
                     ingredientFrequencyMap.remove(ingredient) + 1);
            }
            else {ingredientFrequencyMap.put(ingredient, 1);}
         }
      }

      Map<Integer, Set<Ingredient>> frequencySet =
            new TreeMap<>((Comparator<Integer>) (o1, o2) -> Integer.compare(o2, o1));
      for (Ingredient i : ingredientFrequencyMap.keySet())
      {
         int frequency = ingredientFrequencyMap.get(i);
         if (frequencySet.containsKey(frequency))
         {
            frequencySet.get(frequency).add(i);
         }
         else
         {
            Set<Ingredient> ingredients = new TreeSet<>();
            ingredients.add(i);
            frequencySet.put(frequency, ingredients);
         }
      }
      return frequencySet;
   }
}