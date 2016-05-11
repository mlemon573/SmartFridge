import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * The main menu for the application, in charge of displaying ingredient list and
 * allowing selection of ingredients in one's fridge (or pantry)
 */
public class MainMenuGUI
{
   private JPanel menuPanel;
   private JLabel logoLabel;
   private JList<Ingredient> allIngredientJList;
   private JList<Ingredient> selectIngredientJList;
   private JButton findButton;
   private JButton redoButton;
   private JButton undoButton;

   private List<Recipe> recipeList;
   private Stack<Ingredient> undoStack;
   private Stack<Ingredient> redoStack;
   private DefaultListModel<Ingredient> selectIngredientModel;
   private DefaultListModel<Ingredient> allIngredientModel;

   /**
    * Constructor method for MainMenuGUI
    *
    * @param recipeList the list of possible recipes
    */
   public MainMenuGUI(List<Recipe> recipeList)
   {
      this.recipeList = recipeList;
      undoStack = new Stack<>();
      redoStack = new Stack<>();

      $$$setupUI$$$();

      findButton.addActionListener(e -> {
         selectIngredientJList.setSelectionInterval(0,
               selectIngredientModel.size() - 1);
         List<Ingredient> ingredients =
               selectIngredientJList.getSelectedValuesList();
         List<Recipe> possibleRecipes = new ArrayList<>();
         for (Recipe r : recipeList)
         {if (r.isPossible(ingredients)) {possibleRecipes.add(r);}}
         RecipeListGUI.createFrame(possibleRecipes);
      });
      undoButton.addActionListener(e -> undoChoice());
      undoButton.addKeyListener(new KeyListener()
      {
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyPressed(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e)
         {if (e.getKeyCode() == KeyEvent.VK_ENTER) {undoChoice();}}
      });
      redoButton.addActionListener(e -> redoChoice());
      redoButton.addKeyListener(new KeyListener()
      {
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyPressed(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e)
         {if (e.getKeyCode() == KeyEvent.VK_ENTER) {redoChoice();}}
      });
      allIngredientJList.addMouseListener(new MouseListener()
      {
         @Override
         public void mouseClicked(MouseEvent e) {}

         @Override
         public void mousePressed(MouseEvent e) {}

         @Override
         public void mouseReleased(MouseEvent e) {selectIngredient();}

         @Override
         public void mouseEntered(MouseEvent e) {}

         @Override
         public void mouseExited(MouseEvent e) {}
      });
      allIngredientJList.addKeyListener(new KeyListener()
      {
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyPressed(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e)
         {if (e.getKeyCode() == KeyEvent.VK_ENTER) {selectIngredient();}}
      });
   }

   public static void main(String[] args)
   {
      RecipeLoader recipeLoader = new RecipeLoader("recipes");
      List<Recipe> recipeList = recipeLoader.getDirectory();
      MainMenuGUI.createFrame(recipeList);
   }

   /**
    * Creates JFrame that contains the main menu
    *
    * @param recipeList the list of possible recipes
    */
   public static void createFrame(List<Recipe> recipeList)
   {
      JFrame frame = new JFrame();
      frame.setContentPane(new MainMenuGUI(recipeList).menuPanel);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   /**
    * Moves an Ingredient from all ingredients to selected ingredients
    */
   private void selectIngredient()
   {
      List<Ingredient> tempList = allIngredientJList.getSelectedValuesList();
      int index = allIngredientJList.getSelectedIndex();
      redoStack.removeAllElements();
      for (Ingredient i : tempList)
      {
         selectIngredientModel.addElement(i);
         undoStack.add(i);
         allIngredientModel.removeElement(i);
      }
      allIngredientJList.setSelectedIndex(index);
   }

   /**
    * Undoes the last choice made
    */
   private void undoChoice()
   {
      doChoice(undoStack, redoStack, selectIngredientModel, allIngredientModel);
   }

   /**
    * Redoes the last choice undone
    */
   private void redoChoice()
   {
      doChoice(redoStack, undoStack, allIngredientModel, selectIngredientModel);
   }

   /**
    * Helper method that transfers an element from the fromStack to the toStack,
    * as well as removing it from the fromModel and adding it to the toModel
    *
    * @param fromStack Stack of Ingredient objects to remove an element from
    * @param toStack   Stack of Ingredient objects to add an element to
    * @param fromModel DefaultListModel to remove an element from
    * @param toModel   DefaultListModel to add an element to
    */
   private void doChoice(Stack<Ingredient> fromStack, Stack<Ingredient> toStack,
                         DefaultListModel<Ingredient> fromModel,
                         DefaultListModel<Ingredient> toModel)
   {
      if (!fromStack.isEmpty())
      {
         Ingredient temp = fromStack.pop();
         fromModel.removeElement(temp);
         toModel.addElement(temp);
         toStack.push(temp);
      }
   }

   /**
    * Creates components that are too complex to simply add
    */
   private void createUIComponents()
   {
      Map<Integer, Set<Ingredient>> sortedMap =
            IngredientFrequency.getSortedMap(recipeList);
      allIngredientModel = new DefaultListModel<>();
      for (Integer freq : sortedMap.keySet())
      {
         for (Ingredient ing : sortedMap.get(freq))
         {allIngredientModel.addElement(ing);}
      }
      allIngredientJList = new JList<>(allIngredientModel);

      selectIngredientModel = new DefaultListModel<>();
      selectIngredientJList = new JList<>(selectIngredientModel);

      try
      {
         BufferedImage logoImage =
               ImageIO.read(getClass().getResource("smart-fridge.png"));
         BufferedImage undoImage =
               ImageIO.read(getClass().getResource("action-undo-2x.png"));
         BufferedImage redoImage =
               ImageIO.read(getClass().getResource("action-redo-2x.png"));

         logoLabel = new JLabel(new ImageIcon(logoImage));
         redoButton = new JButton(new ImageIcon(redoImage));
         undoButton = new JButton(new ImageIcon(undoImage));
      }
      catch (IOException ex) {ex.printStackTrace();}
   }

   /**
    * Method generated by IntelliJ IDEA GUI Designer
    * >>> IMPORTANT!! <<<
    * DO NOT edit this method OR call it in your code!
    */
   private void $$$setupUI$$$()
   {
      createUIComponents();
      menuPanel = new JPanel();
      menuPanel.setLayout(new GridLayoutManager(5, 7, new Insets(10, 10, 10, 10),
            -1, -1));
      logoLabel.setFont(new Font(logoLabel.getFont().getName(), logoLabel.getFont
            ().getStyle(), 24));
      logoLabel.setText("");
      menuPanel.add(logoLabel, new GridConstraints(0, 1, 1, 5, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new
            Dimension(200, 100), null, 0, false));
      final Spacer spacer1 = new Spacer();
      menuPanel.add(spacer1, new GridConstraints(0, 0, 4, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension
            (25, -1), null, 0, false));
      final Spacer spacer2 = new Spacer();
      menuPanel.add(spacer2, new GridConstraints(0, 6, 4, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension
            (25, -1), null, 0, false));
      final Spacer spacer3 = new Spacer();
      menuPanel.add(spacer3, new GridConstraints(4, 0, 1, 7, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints
            .SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 25), null, 0, false));
      final JScrollPane scrollPane1 = new JScrollPane();
      scrollPane1.setHorizontalScrollBarPolicy(31);
      menuPanel.add(scrollPane1, new GridConstraints(2, 1, 1, 2, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints
            .SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
            false));
      allIngredientJList.setFixedCellWidth(150);
      allIngredientJList.setLayoutOrientation(0);
      allIngredientJList.setSelectionMode(0);
      allIngredientJList.setVisibleRowCount(10);
      scrollPane1.setViewportView(allIngredientJList);
      final JLabel label1 = new JLabel();
      label1.setText("All Ingredients");
      menuPanel.add(label1, new GridConstraints(1, 1, 1, 2, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
      final Spacer spacer4 = new Spacer();
      menuPanel.add(spacer4, new GridConstraints(2, 3, 1, 1, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints
            .SIZEPOLICY_FIXED, 1, null, new Dimension(25, -1), null, 0, false));
      final JScrollPane scrollPane2 = new JScrollPane();
      scrollPane2.setHorizontalScrollBarPolicy(31);
      menuPanel.add(scrollPane2, new GridConstraints(2, 4, 1, 2, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints
            .SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
            false));
      selectIngredientJList.setFixedCellWidth(150);
      selectIngredientJList.setRequestFocusEnabled(false);
      selectIngredientJList.setVisible(true);
      selectIngredientJList.setVisibleRowCount(10);
      scrollPane2.setViewportView(selectIngredientJList);
      findButton = new JButton();
      findButton.setText("Find Recipes");
      menuPanel.add(findButton, new GridConstraints(3, 5, 1, 1, GridConstraints
            .ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK
                  | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints
            .SIZEPOLICY_FIXED, null, null, null, 0, false));
      final JLabel label2 = new JLabel();
      label2.setText("Selected Ingredients");
      menuPanel.add(label2, new GridConstraints(1, 4, 1, 2, GridConstraints
            .ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
      redoButton.setText("");
      redoButton.setToolTipText("redo");
      menuPanel.add(redoButton, new GridConstraints(3, 4, 1, 1, GridConstraints
            .ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
      undoButton.setText("");
      undoButton.setToolTipText("undo");
      menuPanel.add(undoButton, new GridConstraints(3, 2, 1, 1, GridConstraints
            .ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints
            .SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null,
            0, false));
   }

   /** @noinspection ALL */
   public JComponent $$$getRootComponent$$$() { return menuPanel; }
}
