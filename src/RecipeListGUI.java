import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeListGUI
{
   private JPanel recipeListPanel;
   private JList<Recipe> recipeJList;
   private JButton viewButton;

   private List<Recipe> recipeList;

   /**
    * Creates JFrame that contains the list of possible recipes
    */
   public static void createFrame(List<Recipe> recipeList)
   {
      JFrame frame = new JFrame();
      frame.setContentPane(new RecipeListGUI(recipeList).recipeListPanel);
      frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public RecipeListGUI(List<Recipe> recipeList)
   {
      this.recipeList = recipeList;
      $$$setupUI$$$();
      viewButton.addActionListener(e -> {
         RecipeViewerGUI.createFrame(recipeJList.getSelectedValuesList());
      });
   }

   private void createUIComponents()
   {
      DefaultListModel<Recipe> recipeJListModel = new DefaultListModel<>();
      for (Recipe r : recipeList) {recipeJListModel.addElement(r);}
      recipeJList = new JList<>(recipeJListModel);
   }

   /**
    * Method generated by IntelliJ IDEA GUI Designer
    * >>> IMPORTANT!! <<<
    * DO NOT edit this method OR call it in your code!
    */
   private void $$$setupUI$$$()
   {
      createUIComponents();
      recipeListPanel = new JPanel();
      recipeListPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0)
            , -1, -1));
      final JScrollPane scrollPane1 = new JScrollPane();
      recipeListPanel.add(scrollPane1, new GridConstraints(2, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
            false));
      recipeJList.setSelectionMode(2);
      scrollPane1.setViewportView(recipeJList);
      final Spacer spacer1 = new Spacer();
      recipeListPanel.add(spacer1, new GridConstraints(2, 2, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints
            .SIZEPOLICY_WANT_GROW, 1, new Dimension(10, -1), null, null, 0, false));
      final Spacer spacer2 = new Spacer();
      recipeListPanel.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints
            .SIZEPOLICY_WANT_GROW, 1, new Dimension(10, -1), null, null, 0, false));
      final Spacer spacer3 = new Spacer();
      recipeListPanel.add(spacer3, new GridConstraints(0, 0, 1, 3, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints
            .SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
      final Spacer spacer4 = new Spacer();
      recipeListPanel.add(spacer4, new GridConstraints(4, 0, 1, 3, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints
            .SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
      final JLabel label1 = new JLabel();
      label1.setFont(new Font(label1.getFont().getName(), label1.getFont()
            .getStyle(), 18));
      label1.setText("Possible Recipes");
      recipeListPanel.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
      viewButton = new JButton();
      viewButton.setText("View");
      recipeListPanel.add(viewButton, new GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints
            .SIZEPOLICY_FIXED, null, null, null, 0, false));
   }

   /** @noinspection ALL */
   public JComponent $$$getRootComponent$$$() { return recipeListPanel; }
}
