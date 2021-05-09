package Controller;

import Model.MSLogic;

import java.util.Arrays;
import java.util.Random;

public class CreateBoard {
  static int X[]={0,0,-1,+1,+1,-1,+1,-1};
  static int Y[]={-1,+1,0,0,-1,-1,+1,+1};
  int[][] grid;
  public char[][] overlap;
  public int mines;
 MSLogic msLogic;
  public CreateBoard(int n,int m){
    grid=new int[n][n];
    overlap=new char[n][n];
    for(int k=0;k<n;k++)
    Arrays.fill(overlap[k], '*');
    mines=m;
    dropMines();
    fillNeighbours();
    msLogic=new MSLogic();
  }
  void dropMines(){
    int count=0;
    Random random = new Random();
    while(count<mines){
        int i=random.nextInt(grid.length);
        int j=random.nextInt(grid.length);
        if(grid[i][j]!=Integer.MAX_VALUE){
          grid[i][j]=Integer.MAX_VALUE;
          count++;
        }
    }

  }
  void fillNeighbours(){
    for (int i = 0; i <grid.length ; i++) {

      for (int j = 0; j < grid.length ; j++) {
        int count_mines=0;
        if(grid[i][j]==Integer.MAX_VALUE) continue;
        else
        for(int k = 0;k<8;k++){
          if(i+X[k]>=0&&i+X[k]<grid.length&&j+Y[k]>=0&&j+Y[k]<grid.length&&grid[i+X[k]][j+Y[k]]==Integer.MAX_VALUE){
            count_mines++;
          }
        }
        grid[i][j]=count_mines;
      }

    }
  }
   void showGrid(){
    for (int i = 0; i <grid.length ; i++) {
      for (int j = 0; j < grid.length ; j++) {
        if(grid[i][j]==Integer.MAX_VALUE)
          System.out.print("X ");
        else
          System.out.print(grid[i][j]+" ");
      }
      System.out.println();
    }
  }
  public void showGame(){
    for (int i = 0; i <grid.length ; i++) {
      for (int j = 0; j < grid.length ; j++) {


          System.out.print(overlap[i][j]+" ");
      }
      System.out.println();
    }
  }
  public int play(int i, int j){
      int c=msLogic.openTile(i,j,grid,overlap);
      return c;
  }
  public int checkWin(){
    int c=0;
    for (int i = 0; i <grid.length ; i++) {
      for (int j = 0; j < grid.length ; j++) {
       if(overlap[i][j]=='*') c++;
      }

    }
    if(c==mines)
    return 1;
    else return 0;
  }
  public  void showBombs(){

      for (int i = 0; i <grid.length ; i++) {
        for (int j = 0; j < grid.length ; j++) {
          if(grid[i][j]==Integer.MAX_VALUE) {overlap[i][j]='B';}

        }

      }
  }
}
