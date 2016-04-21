public class IngredientAmount
{
   private double quantity;
   private String unit;

   public IngredientAmount(double quantity, String unit)
   {
      this.quantity = quantity;
      this.unit = unit;
   }

   public double getQuantity()
   {
      return quantity;
   }

   public String getUnit()
   {
      return unit;
   }

   private void cleanUnits()
   {
      if (unit.equals("")) {return;}
   }
}
