import java.util.*;

public class Recipe
{
   private String name;
   private List<String> directions;
   private Map<Ingredient, IngredientAmount> pairMap;

   public Recipe()
   {
      directions = new ArrayList<>();
      pairMap = new TreeMap<>();
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
      return pairMap.keySet();
   }

   public Collection<IngredientAmount> getAmounts()
   {
      return pairMap.values();
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
      pairMap.put(ing, ia);
   }

   public boolean contains(Ingredient i)
   {
      return pairMap.containsKey(i);
   }
}