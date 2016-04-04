public class Ingredient implements Comparable<Ingredient>
{
   private String name;
   private String huffmanCode;
   private boolean hasRecipe;

   public Ingredient(String name)
   {
      this.name = name;
      hasRecipe = false;
   }

   public Ingredient(String name, boolean hasRecipe)
   {
      this.name = name;
      this.hasRecipe = hasRecipe;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public boolean getHasRecipe()
   {
      return hasRecipe;
   }

   public void setHasRecipe(boolean hasRecipe)
   {
      this.hasRecipe = hasRecipe;
   }

   public String getHuffmanCode()
   {

      return huffmanCode;
   }

   public void setHuffmanCode(String huffmanCode)
   {
      this.huffmanCode = huffmanCode;
   }

   @Override
   /**
    *
    * Assumes huffmanCode has been set
    */
   public int compareTo(Ingredient o)
   {
      return getHuffmanCode().compareTo(o.getHuffmanCode());
   }
}
