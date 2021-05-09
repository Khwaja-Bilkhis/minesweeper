package Model;

import Controller.CreateBoard;

public class MSLogic {
  static int X[]={0,0,-1,+1,+1,-1,+1,-1};
  static int Y[]={-1,+1,0,0,-1,-1,+1,+1};
  public int openTile(int i, int j, int[][] grid,char[][] overlap){
    int gridValue= grid[i][j];


    if(gridValue==Integer.MAX_VALUE){
       overlap[i][j]='B';      
      return -1;
    }
    if(gridValue>0) {
       overlap[i][j]=(""+ grid[i][j]).charAt(0);
      return 0;
    }
     overlap[i][j]=' ';
    for(int k = 0;k<8;k++){
    int ni=i+X[k];
    int nj=j+Y[k];
        if(ni>=0&&ni< grid.length&&nj>=0&&nj< grid.length&& grid[ni][nj]!=Integer.MAX_VALUE&& overlap[ni][nj]=='*'){
          openTile(ni,nj,grid,overlap
          );
        }

    }
    return 0;

  }

}
