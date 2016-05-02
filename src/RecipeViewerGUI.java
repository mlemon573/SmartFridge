import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class RecipeViewerGUI
{
   public JPanel recipeViewerPanel;
   private JLabel recipeName;
   private JPanel ingredientPanel;
   private JPanel directionPanel;

   Recipe recipe;

   /**
    * Creates JFrame that contains the recipe
    */
   public static void createFrame(List<Recipe> recipeList)
   {
      JFrame frame = new JFrame();

      JTabbedPane tabbedPane = new JTabbedPane();
      for (Recipe r : recipeList)
      {tabbedPane.addTab(r.getName(), new RecipeViewerGUI(r).recipeViewerPanel);}

      frame.setContentPane(tabbedPane);
      frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public RecipeViewerGUI(Recipe recipe)
   {
      this.recipe = recipe;
      $$$setupUI$$$();
      recipeName.setText(recipe.getName());
   }

   private void createUIComponents()
   {
      ingredientPanel = new JPanel();
      Map<Ingredient, IngredientAmount> ingredientMap = recipe.getIngredientMap();
      ingredientPanel.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.anchor = GridBagConstraints.LINE_START;
      c.ipadx = 8;
      c.gridy = 0;
      for (Ingredient i : ingredientMap.keySet())
      {
         IngredientAmount ia = ingredientMap.get(i);
         NumberFormat nf = new DecimalFormat("#.##");
         c.gridx = 0;
         ingredientPanel.add(new JLabel(nf.format(ia.getQuantity())), c);
         c.gridx = c.gridx + 1;
         ingredientPanel.add(new JLabel(ia.getUnit()), c);
         c.gridx = c.gridx + 1;
         ingredientPanel.add(new JLabel(i.getName()), c);
         c.gridy = c.gridy + 1;
      }
      List<String> directions = recipe.getDirections();
      directionPanel = new JPanel();
      directionPanel.setLayout(new GridBagLayout());
      c.gridy = 0;
      for (int i = 1; i <= directions.size(); i++)
      {
         c.gridx = 0;
         directionPanel.add(new JLabel(i + ")"), c);
         c.gridx = c.gridx + 1;
         directionPanel.add(new JLabel(directions.get(i - 1)), c);
         c.gridy = c.gridy + 1;
      }
   }

   /**
    * Method generated by IntelliJ IDEA GUI Designer
    * >>> IMPORTANT!! <<<
    * DO NOT edit this method OR call it in your code!
    */
   private void $$$setupUI$$$()
   {
      createUIComponents();
      recipeViewerPanel = new JPanel();
      recipeViewerPanel.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0,
            0), -1, -1));
      final Spacer spacer1 = new Spacer();
      recipeViewerPanel.add(spacer1, new GridConstraints(1, 2, 5, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(10, -1), null,
            null, 0, false));
      final Spacer spacer2 = new Spacer();
      recipeViewerPanel.add(spacer2, new GridConstraints(1, 0, 5, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(10, -1), null,
            null, 0, false));
      final Spacer spacer3 = new Spacer();
      recipeViewerPanel.add(spacer3, new GridConstraints(0, 0, 1, 3,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
            GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null,
            null, 0, false));
      final Spacer spacer4 = new Spacer();
      recipeViewerPanel.add(spacer4, new GridConstraints(6, 0, 1, 3,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
            GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null,
            null, 0, false));
      recipeName = new JLabel();
      recipeName.setEnabled(true);
      recipeName.setFont(new Font(recipeName.getFont().getName(), recipeName
            .getFont().getStyle(), 24));
      recipeName.setText("");
      recipeViewerPanel.add(recipeName, new GridConstraints(1, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
            null, null, null, 0, false));
      final JLabel label1 = new JLabel();
      label1.setFont(new Font(label1.getFont().getName(), label1.getFont()
            .getStyle(), 18));
      label1.setText("Ingredients");
      recipeViewerPanel.add(label1, new GridConstraints(2, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
            null, null, null, 0, false));
      recipeViewerPanel.add(ingredientPanel, new GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
            false));
      final JLabel label2 = new JLabel();
      label2.setFont(new Font(label2.getFont().getName(), label2.getFont()
            .getStyle(), 18));
      label2.setText("Directions");
      recipeViewerPanel.add(label2, new GridConstraints(4, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
            null, null, null, 0, false));
      recipeViewerPanel.add(directionPanel, new GridConstraints(5, 1, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
            false));
   }

   /** @noinspection ALL */
   public JComponent $$$getRootComponent$$$() { return recipeViewerPanel; }
}
