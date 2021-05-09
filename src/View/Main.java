package View;

import Controller.CreateBoard;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Main{



  public static void main(String[] args) {

    CreateBoard board=new CreateBoard(10,10); //Controller

    Minesweeper minesweeper=new Minesweeper(board); //View

  }


}

class Minesweeper extends MouseAdapter implements ActionListener {
  JFrame mainB;
  JPanel f;
  JPanel panel2;
  CreateBoard board;
  JButton restart;
  JButton[] buttons;
  JPanel panel1;
  JLabel game_sta;


  JLabel flags;
  Minesweeper(CreateBoard bo){
    mainB=new JFrame();
    mainB.setTitle("Minesweeper");
    board=bo;

/////////////////////////////////////////////////////////////

    JMenuBar jMenuBar=new JMenuBar();
    JMenu jMenu=new JMenu("Level");
    JMenuItem easy=new JMenuItem("Easy");
    JMenuItem medium=new JMenuItem("Medium");
    JMenuItem hard=new JMenuItem("Hard");
    jMenu.add(easy,0);
    jMenu.add(medium,1);
    jMenu.add(hard,2);
    jMenuBar.add(jMenu);

    JMenu About=new JMenu("About");
    JMenuItem About_con=new JMenuItem("This is simple 3 level minesweeper game. Version: 1.0 Developer: Khwaja Bilkhis");
    About.add(About_con, 0);
    jMenuBar.add(About);

    easy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        board=new  CreateBoard(10,10);
        JPanel newPanel=newBoard();
        mainB.remove(f);
        f=newPanel;

        mainB.add(f,BorderLayout.CENTER);
        mainB.setSize(300, 400);
        mainB.setVisible(true);
        game_sta.setText("Welcome to Minesweeper! Flags:");
        flags.setText(""+board.mines);
      }
    });
    medium.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        board=new  CreateBoard(18,40);
        JPanel newPanel=newBoard();
        mainB.remove(f);
        f=newPanel;

        mainB.add(f,BorderLayout.CENTER);
        mainB.setSize(400, 500);
        mainB.setVisible(true);
        game_sta.setText("Welcome to Minesweeper! Flags:");
        flags.setText(""+board.mines);




      }
    });
    hard.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // reset(24, 99);
        board=new  CreateBoard(24,99);
        JPanel newPanel=newBoard();
        mainB.remove(f);
        f=newPanel;

        mainB.add(f,BorderLayout.CENTER);
        mainB.setSize(500, 600);
        mainB.setVisible(true);
        game_sta.setText("Welcome to Minesweeper! Flags:");
        flags.setText(""+board.mines);
      }
    });
//////////////////////////////////////////
    panel1=new JPanel();
    game_sta=new JLabel("Welcome to Minesweeper! Flags:");
    flags=new JLabel();
    flags.setText(""+board.mines);
    panel1.add(game_sta,BorderLayout.EAST);
    panel1.add(flags,BorderLayout.WEST);



/////////////////////////////////////////////////////////////

    f=newBoard();  // create game area

    /////////////////////////////////////////////////////
    panel2=new JPanel();
    restart=new JButton();

    restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        reset(board.overlap.length,board.mines);
      }
    });

    restart.setText("Restart");
    panel2.add(restart);
    ////////////////////////////////////////////////////


    BorderLayout borderLayout=new BorderLayout();
    borderLayout.setHgap(0);
    borderLayout.setVgap(0);


    mainB.setLayout(borderLayout);

    mainB.add(panel1,BorderLayout.NORTH);
    mainB.add(f,BorderLayout.CENTER);
    mainB.add(panel2,BorderLayout.SOUTH);

    mainB.setJMenuBar(jMenuBar);

    mainB.setSize(300, 400);

    mainB.setVisible(true);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btn_pressed=(JButton)e.getSource();

    if(btn_pressed.getBackground().equals(Color.red)){
      return;
    }

    int btn_num=Integer.parseInt(btn_pressed.getName());
    int n=board.overlap.length;
    int i=btn_num/n;
    int j=btn_num%n;
    int r=board.play(i, j); ////call the controller to make the change


    //display the change from controller to the view
    if(r==-1){
      board.showBombs();
      game_sta.setText("You Lost!");
      flags.setText("");
      for (int p = 0; p < n; p++) {
        for (int q = 0; q < n; q++) {

          if(board.overlap[p][q]=='B'){
            buttons[p * n + q].setBackground(Color.YELLOW);
            buttons[p * n + q].setText("" + board.overlap[p][q]);
          }
          buttons[p * n + q].setEnabled(false);


        }
      }






    }else {
      for (int p = 0; p < n; p++) {
        for (int q = 0; q < n; q++) {
          if (board.overlap[p][q] != '*') {
            buttons[p * n + q].setText("" + board.overlap[p][q]);
            buttons[p * n + q].setEnabled(false);
            buttons[p * n + q].setBackground(Color.lightGray);

          }
        }
      }
    }
    if(board.checkWin()==1){
      board.showBombs();
      for (int p = 0; p < n; p++) {
        for (int q = 0; q < n; q++) {

          if(board.overlap[p][q]=='B'){

            buttons[p * n + q].setText("" + board.overlap[p][q]);
          }
          buttons[p * n + q].setEnabled(false);

        }
      }
      game_sta.setText("Congos! You Won. Flags:");
      f.setEnabled(false);


    }


  }
  @Override
  public void mouseClicked(MouseEvent e) {

    JButton btn_pressed=(JButton)e.getSource();

      if (SwingUtilities.isRightMouseButton(e)&&btn_pressed.isEnabled()==true) {


        if (btn_pressed.getBackground().equals(Color.gray)) {
          btn_pressed.setBackground(Color.red);
          int num = Integer.parseInt(flags.getText());
          flags.setText("" + (num - 1));
          ;
        } else {
          btn_pressed.setBackground(Color.gray);

          int num = Integer.parseInt(flags.getText());
          flags.setText("" + (num + 1));
        }

    }
  }

  void reset(int n,int m){
    board=new CreateBoard(n,m);
    for(int b=0;b<n*n;b++){
      buttons[b].setText("");
      buttons[b].setBackground(Color.gray);
      buttons[b].setEnabled(true);

    }
    game_sta.setText("Welcome to Minesweeper!");
    flags.setText(""+board.mines);
    //board.showGrid();
  }

  JPanel newBoard(){



    int n=board.overlap.length;
    int m=board.mines;
    JPanel myBoard=new JPanel();

    buttons=new JButton[n*n];
    for(int b=0;b<n*n;b++){
      buttons[b]=new JButton();

      buttons[b].setName(""+(b));
      buttons[b].addActionListener(this);
      buttons[b].addMouseListener(this);
      buttons[b].setBackground(Color.gray);
      buttons[b].setMargin(new Insets(0, 0, 0, 0));
    }

    for(int b=0;b<n*n;b++){
      myBoard.add(buttons[b]);
    }
    GridLayout gridLayout= new GridLayout(n,n);
    gridLayout.setHgap(3);
    gridLayout.setVgap(3);
    myBoard.setLayout(gridLayout);
    return myBoard;

  }

}
