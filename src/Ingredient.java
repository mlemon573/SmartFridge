public class Ingredient implements Comparable<Ingredient>
{
   private String name;

   public Ingredient(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   @Override
   public String toString()
   {
      return getName();
   }

   @Override
   public int compareTo(Ingredient o)
   {
      return name.compareTo(o.getName());
   }

   @Override
   public int hashCode()
   {
      return name.hashCode();
   }

   @Override
   public boolean equals(Object o)
   {
      return o.getClass() == Ingredient.class
            && name.equalsIgnoreCase(((Ingredient) o).getName());
   }
}
