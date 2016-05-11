import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * The next window of the application after MainMenuGUI, allows the user to select
 * which recipes they would like to view
 */
public class RecipeListGUI
{
   private JPanel recipeListPanel;
   private JList<Recipe> recipeJList;
   private JButton viewButton;

   private List<Recipe> recipeList;

   /**
    * Constructor method for RecipeListGUI
    *
    * @param recipeList the list of possible recipes
    */
   public RecipeListGUI(List<Recipe> recipeList)
   {
      this.recipeList = recipeList;

      $$$setupUI$$$();

      recipeJList.addKeyListener(new KeyListener()
      {
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyPressed(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e)
         {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {RecipeViewerGUI.createFrame(recipeJList.getSelectedValuesList());}
         }
      });
      viewButton.addActionListener(e -> {
         RecipeViewerGUI.createFrame(recipeJList.getSelectedValuesList());
      });
      viewButton.addKeyListener(new KeyListener()
      {
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyPressed(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e)
         {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {RecipeViewerGUI.createFrame(recipeJList.getSelectedValuesList());}
         }
      });
   }

   /**
    * Creates JFrame that contains the list of possible recipes
    *
    * @param recipeList the list of possible recipes
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

   /**
    * Creates components that are too complex to simply add
    */
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
      recipeListPanel.setLayout(new GridLayoutManager(4, 3, new Insets(10, 10, 10,
            10), -1, -1));
      final JScrollPane scrollPane1 = new JScrollPane();
      recipeListPanel.add(scrollPane1, new GridConstraints(1, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
            false));
      recipeJList.setFixedCellWidth(200);
      recipeJList.setLayoutOrientation(0);
      recipeJList.setSelectionMode(2);
      recipeJList.setVisible(true);
      recipeJList.setVisibleRowCount(10);
      scrollPane1.setViewportView(recipeJList);
      final Spacer spacer1 = new Spacer();
      recipeListPanel.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension
            (25, -1), null, 0, false));
      final Spacer spacer2 = new Spacer();
      recipeListPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension
            (25, -1), null, 0, false));
      final Spacer spacer3 = new Spacer();
      recipeListPanel.add(spacer3, new GridConstraints(3, 0, 1, 3, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints
            .SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 25), null, 0, false));
      final JLabel label1 = new JLabel();
      label1.setFont(new Font(label1.getFont().getName(), label1.getFont()
            .getStyle(), 18));
      label1.setText("Possible Recipes");
      recipeListPanel.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
      viewButton = new JButton();
      viewButton.setText("View");
      recipeListPanel.add(viewButton, new GridConstraints(2, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints
            .SIZEPOLICY_FIXED, null, null, null, 0, false));
   }

   /** @noinspection ALL */
   public JComponent $$$getRootComponent$$$() { return recipeListPanel; }
}