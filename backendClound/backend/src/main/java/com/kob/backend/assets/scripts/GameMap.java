package com.kob.backend.assets.scripts;

import java.util.Arrays;
import java.util.Random;

public class GameMap {
    final private Integer rows;
    final private Integer cols;
    final private Integer inerWallsCount;
    final private int[][] g;
    final private static int[] dx = {-1,0,1,0},dy = {0,1,0,-1};

    public GameMap(Integer rows,Integer cols,Integer inerWallsCount){
        this.rows = rows;
        this.cols = cols;
        this.inerWallsCount = inerWallsCount;
        g = new int[rows][cols];
    }
    private boolean checkConnectivity(int sx,int sy,int tx,int ty){
        if(sx == tx && sy == ty)return true;
        g[sx][sy] = 1;
        for(int i = 0;i<4;i++){
            int x = sx+dx[i],y = sy+dy[i];
            if(x>=rows || x<0 || y>=cols || y<0)continue;
            if(g[x][y] == 0){
                if(checkConnectivity(x,y,tx,ty)){
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }

    private boolean createWalls(){
        for(int i = 0;i<rows;i++){
            g[i][0] = g[i][cols-1] = 1;
        }

        for(int i = 0;i<cols;i++){
            g[0][i] = g[rows-1][i] = 1;
        }
        Random random = new Random();
        for(int i = 0;i<inerWallsCount/2;i++){
            for(int j = 0;j<1000;j++){
                int r = random.nextInt(rows);
                int c = random.nextInt(cols);
                if(g[r][c] == 1 || g[rows-1-r][cols-1-c] == 1)continue;
                if(r == rows-2 && c == 1 || r == 1 && c == cols-2)continue;
                g[r][c] = g[rows-1-r][cols-1-c] = 1;
                break;
            }
        }
        return checkConnectivity(rows-2,1,1,cols-2);
    }
    public void createMap(){
        for(int i = 0;i<1000;i++){
            if(createWalls())break;
        }
    }
    public int[][] getMap(){
        return g;
    }
}

