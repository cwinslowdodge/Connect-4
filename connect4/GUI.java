package cis163.connect4;


import javafx.scene.control.ToggleGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Charles Dodge on 10/3/15.
 */
public class GUI implements ActionListener {

 ConnectFour gameModel;
 private Disk disksHolder[][];
 private JButton dropHolder[];
 private int row = 6;
 private int col = 8;
 private int p1Wins = 0;
 private int p2Wins = 0;
 private Boolean gameStarted = false;
 private Color p1Color, p2Color, colorSelector;
 private JFrame gameFrame;
 private JMenuBar jMenuBar;
 private JMenu jMenu;
 private JMenuItem resetGame, exitGame;
 private JPanel container, gameBoardPanel, controlPanel, dropPanel;
 private JLabel p1NameLabel, p2NameLabel, p1WinCountLabel, p2WinCountLabel;
 private JButton resizeBoardButton, chooseColorsButton, undoButton;
 private JTextField p1NameInput, p2NameInput;
 private final Color RED_COLOR = new Color(255, 0, 0);
 private final Color  BLUE_COLOR = new Color(0, 0, 255);
 private final Color  BLACK_COLOR = new Color(0, 0, 0);
 private final Color  GREEN_COLOR = new Color(0, 255, 0);
 private final Color  GREY_COLOR = new Color(150, 150, 150);
 private final Color  ORANGE_COLOR = new Color(200, 165, 0);
 private final Color  VIOLET_COLOR = new Color(130, 51, 175);
 private final Color  DEFAULT_COLOR = new Color(255, 125, 75);
 private final Color  DEFAULT1_COLOR = new Color(0, 150, 175);

 /**
  * @constructor
  */
 public GUI(){
  initButtons();
  initLabels();
  initTextFields();
  initControlPanel();
  initFrame();
  initMenuBar();
  drawBoard(row, col);
  getPlayerNames();
  setP1Color(DEFAULT_COLOR);
  setP2Color(DEFAULT1_COLOR);
  resizeBoardButton.setEnabled(true);
  chooseColorsButton.setEnabled(true);
 }

 /**
  * @desc - creates game frame
  */
 public void initFrame(){

  gameFrame = new JFrame("Connect Four");
  gameFrame.setJMenuBar(jMenuBar);
  gameFrame.setResizable(false);
  gameFrame.add(controlPanel, BorderLayout.NORTH);
  gameFrame.setVisible(true);
  gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 }

 /**
  * @desc - creates menu bar and items
  */
 public void initMenuBar(){

  jMenu = new JMenu("Menu");
  resetGame = new JMenuItem("Reset Game");
  exitGame = new JMenuItem("Exit Game");
  resetGame.addActionListener(this);
  exitGame.addActionListener(this);

  jMenu.add(resetGame);
  jMenu.add(exitGame);

  jMenuBar = new JMenuBar();
  jMenuBar.add(jMenu);
  gameFrame.setJMenuBar(jMenuBar);
 }

 /**
  * @desc - creates top frame labels
  */
 public void initLabels(){
  p1NameLabel = new JLabel();
  p2NameLabel = new JLabel();
  p1WinCountLabel = new JLabel(Integer.toString(0));
  p2WinCountLabel = new JLabel(Integer.toString(0));
 }

 /**
  * @desc - creates player names text fields
  */
 public void initTextFields(){
  p1NameInput = new JTextField();
  p2NameInput = new JTextField();
 }

 /**
  * @desc - create game control buttons
  */
 public void initButtons(){
  resizeBoardButton = new JButton("Resize Board");
  chooseColorsButton = new JButton("Choose Colors");
  undoButton = new JButton("Undo");

  resizeBoardButton.addActionListener(this);
  chooseColorsButton.addActionListener(this);
  undoButton.addActionListener(this);
 }

 /**
  * @desc - creates top panel pane and adds game controls
  */
 public void initControlPanel(){
  controlPanel = new JPanel();
  controlPanel.setLayout(new GridLayout(1, 6));
  controlPanel.add(p1NameLabel);
  controlPanel.add(p1WinCountLabel);
  controlPanel.add(p2NameLabel);
  controlPanel.add(p2WinCountLabel);
  controlPanel.add(resizeBoardButton);
  controlPanel.add(chooseColorsButton);
  controlPanel.add(undoButton);
 }

 public void setColor(Color c){
  colorSelector = c;
 }
 /**
  * @desc - updates player 1 color
  * @param c
  */
 public void setP1Color(Color c){
  p1Color = c;
 }

 /**
  * @desc - updates player 2 color
  * @param c
  */
 public void setP2Color(Color c){
  p2Color = c;
 }

 /**
  * @desc - updates player 1 win count and label
  */
 public void updateP1WinCount(){
  p1Wins++;
  p1WinCountLabel.setText(Integer.toString(p1Wins));
 }

 /**
  * @desc - updates player 2 win count and label
  */
 public void updateP2WinCount(){
  p2Wins++;
  p2WinCountLabel.setText(Integer.toString(p2Wins));
 }

 /**
  * @desc - show user name input dialog
  */
 public void getPlayerNames(){
  Object[] nameFields = {
          "First Player", p1NameInput,
          "Second Player", p2NameInput
  };

  JOptionPane.showConfirmDialog(null, nameFields, "Enter Player Names", JOptionPane.OK_CANCEL_OPTION);

  p1NameLabel.setText(p1NameInput.getText());
  p2NameLabel.setText(p2NameInput.getText());
 }

 /**
  * @desc - create new modal, draws game board, and disks
  * @param row
  * @param col
  */
 public void drawBoard(int row, int col){
  //Model
  gameModel = new ConnectFour(row,col);

  //Container
  container = new JPanel();

  //Drop Panel
  dropPanel = new JPanel();
  dropHolder = new JButton[col];
  dropPanel.setLayout(new GridLayout(1, col));
  for (int _col = 0; _col < col; _col++) {
   JButton button = new JButton();
   button.setText(Integer.toString(_col));
   button.addActionListener(this);
   dropHolder[_col] = button;
   dropPanel.add(button);
  }

  //Grid
  disksHolder = new Disk[row][col];
  gameBoardPanel = new JPanel();
  gameBoardPanel.setLayout(new GridLayout(row, col));
  gameBoardPanel.setBorder(BorderFactory.createEmptyBorder());
  gameBoardPanel.setBackground(Color.yellow);


  for (int _row = 0; _row < row; _row++) {
   for (int _col = 0; _col < col; _col++) {
    Disk aDisk = new Disk();
    disksHolder[_row][_col] = aDisk;
    gameBoardPanel.add(disksHolder[_row][_col]);
   }
  }

  container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
  container.add(dropPanel);
  container.add(gameBoardPanel);

  gameFrame.add(container, BorderLayout.CENTER);
  gameFrame.pack();
  gameFrame.repaint();
 }

 /**
  * @desc - shows input dialog to update board dimensions
  */
 public void resizeBoardClickHandler(){

  // do nothing if game has started
  if(gameStarted){
   return;
  }

  JTextField col = new JTextField();
  JTextField row = new JTextField();

  Object[] nameFields = {
          "Number of Rows", row,
          "Number of Columns", col
  };

  JOptionPane.showConfirmDialog(null, nameFields, "Enter Board Dimensions", JOptionPane.OK_CANCEL_OPTION);

  if(row.getText().length() > 0 && col.getText().length() > 0){
   int _row =  Integer.parseInt(row.getText());
   int _col =  Integer.parseInt(col.getText());
   gameFrame.remove(container);

   gameFrame.pack();

   drawBoard(_row, _col);
  }
 }

 /**
  * @desc - shows input dialog to select player colors
  */
 public void chooseColorClickHandler() {

  final ButtonGroup group = new ButtonGroup();

  final JPanel panel = new JPanel();
  final JRadioButton redButton = new JRadioButton("Red");
  final JRadioButton blueButton = new JRadioButton("Blue");
  final JRadioButton blackButton = new JRadioButton("Black");
  final JRadioButton greyButton = new JRadioButton("Grey");
  final JRadioButton orangeButton = new JRadioButton("Orange");
  final JRadioButton greenButton = new JRadioButton("Green");
  final JRadioButton violetButton = new JRadioButton("Violet");
  group.add(redButton);
  group.add(blueButton);
  group.add(blackButton);
  group.add(greyButton);
  group.add(orangeButton);
  group.add(greenButton);
  group.add(violetButton);

  panel.add(redButton);
  panel.add(orangeButton);
  panel.add(greenButton);
  panel.add(blueButton);
  panel.add(violetButton);
  panel.add(greyButton);
  panel.add(blackButton);


  JOptionPane.showConfirmDialog(null, panel, "What color would you like?", JOptionPane.OK_CANCEL_OPTION);

  if (redButton.isSelected()) {
   colorSelector = RED_COLOR;
  } else if (orangeButton.isSelected()) {
   colorSelector = ORANGE_COLOR;
  } else if (greenButton.isSelected()) {
   colorSelector = GREEN_COLOR;
  } else if (blueButton.isSelected()) {
   colorSelector = BLUE_COLOR;
  } else if (violetButton.isSelected()) {
   colorSelector = VIOLET_COLOR;
  } else if (greyButton.isSelected()) {
   colorSelector = GREY_COLOR;
  } else if (blackButton.isSelected()) {
   colorSelector = BLACK_COLOR;
  }

  if (gameModel.discTotal == 1) {
   setP2Color(colorSelector);
  }
  else if(gameModel.discTotal == 0){
   setP1Color(colorSelector);
  }
 }

 public void clearDiscColor(){

  int[] position = (int[]) gameModel.history.peek();

  int _row = position[0];
  int _col = position[1];

  disksHolder[_row][_col].diskColor = Color.WHITE;
  gameBoardPanel.revalidate();
  gameBoardPanel.repaint();
 }

 /**
  * @desc - listens for button click events
  * @param e
  */
 public void actionPerformed(ActionEvent e) {
  Object button = e.getSource();

  if(button == resizeBoardButton){
   resizeBoardClickHandler();
  }
  if(button == chooseColorsButton) {
   chooseColorClickHandler();
  }
  if ( button == undoButton){

   clearDiscColor();
   gameModel.undo();


  }else{
   // Game Started
   gameStarted = true;
   resizeBoardButton.setEnabled(false);
   if(gameModel.discTotal == 1){
    chooseColorsButton.setEnabled(false);
   }
   if(button == exitGame){
    System.exit(1);
   }
   if(button == resetGame){
    gameFrame.dispose();
    new GUI();
   }


   String dropTo = e.getActionCommand();
   Color c;

   c = (gameModel.currentPlayer == 1) ? p1Color : p2Color;


   gameModel.dropDiskAt(Integer.parseInt(dropTo));

   int[] position = (int[]) gameModel.history.peek();

   int _row = position[0];
   int _col = position[1];

   disksHolder[_row][_col].diskColor = c;
   gameBoardPanel.revalidate();
   gameBoardPanel.repaint();


   //Check For Winner
   if(gameModel.getStatus() == GameState.PLAYER_ONE_WON){
    gameWin(p1NameLabel.getText());
    updateP1WinCount();
   }else if(gameModel.getStatus() == GameState.PLAYER_TWO_WON){
    gameWin(p2NameLabel.getText());
    updateP2WinCount();
   }
  }
 }
 public void gameWin(String playerName){
  JPanel panel = new JPanel();
  JLabel label = new JLabel(playerName + " Wins!");
  panel.add(label);
  JOptionPane.showConfirmDialog(null, panel, "Winner!", JOptionPane.OK_CANCEL_OPTION);
  gameFrame.remove(container);
  drawBoard(row, col);
 }


 /**
  * @main
  * @param args
  */
 public static void main(String[] args) {
  GUI game = new GUI();


 }
}




