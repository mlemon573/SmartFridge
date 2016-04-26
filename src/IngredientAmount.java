public class IngredientAmount
{
   private double quantity;
   private String unit;

   private final String[] VOLUME_MEASUREMENTS = {
         "MILLILITER", "TEASPOON", "TABLESPOON", "OUNCE", "CUP", "PINT", "QUART",
         "LITER", "GALLON"
   };

   private final double[] VOLUME_CONVERSIONS =
         {1, 4.929, 14.787, 29.574, 240, 473.176, 946.353, 1000, 3785.41};

   public IngredientAmount(double quantity, String unit)
   {
      this.quantity = quantity;
      this.unit = unit;
      convertUnits();
   }

   public double getQuantity()
   {
      return quantity;
   }

   public String getUnit()
   {
      return unit;
   }

   /**
    * Converts units from whatever it comes in as, to a "prettier" format
    * Prefers numbers less than 5 and greater than or equal to 1 (rounds to .25)
    * Ignores "Ounces" bc it is ambiguous if it refers to volume or mass
    * If unsuccessful, changes nothing
    */
   private void convertUnits()
   {
      if (unit.equals("") || unit.equalsIgnoreCase("OUNCE")) {return;}
      if ((int) quantity == quantity && quantity <= 5 && quantity >= 1) {return;}

      int unitMatch = -1;
      for (int i = 0; i < VOLUME_MEASUREMENTS.length; i++)
      {
         if (unit.equalsIgnoreCase(VOLUME_MEASUREMENTS[i]))
         {
            unitMatch = i;
            break;
         }
      }
      if (unitMatch == -1) {return;}

      double volumeMil = quantity * VOLUME_CONVERSIONS[unitMatch];
      for (int i = 0; i < VOLUME_CONVERSIONS.length; i++)
      {
         double tempVol = volumeMil / VOLUME_CONVERSIONS[i];
         double rounded = (double) Math.round(tempVol * 4) / 4;
         if (rounded >= 1 && rounded <= 5)
         {
            quantity = rounded;
            String formattedUnit = VOLUME_MEASUREMENTS[i];
            formattedUnit = formattedUnit.substring(0, 1)
                  + formattedUnit.substring(1).toLowerCase();
            unit = formattedUnit;
            break;
         }
      }
   }
}
