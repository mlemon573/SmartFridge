/**
 * Represents an ingredient in a recipe
 */
public class Ingredient implements Comparable<Ingredient>
{
   private String name;

   /**
    * Constructor method for Ingredient
    *
    * @param name given name
    */
   public Ingredient(String name)
   {
      this.name = name;
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

   /**
    * Compares this object with the specified object for order
    *
    * @param o the ingredient to be compared.
    * @return the comparison of the names
    */
   @Override
   public int compareTo(Ingredient o)
   {
      return name.compareTo(o.toString());
   }
}
