package sample;


//import java.awt.Canvas;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class CellMap{
    private Canvas cvs;
    private GraphicsContext gc;
    private boolean[][] cells;
    private short[][] cellsSts;
    private int cellRow;
    private int cellCol;

    private final int CELLSIZE = 10;
    private final int CELLPAD = 2;

    CellMap(int row, int column){
        this.cvs = new Canvas(row, column);
        this.gc = cvs.getGraphicsContext2D();
        this.cellRow = 1 + (row - CELLSIZE)/(CELLSIZE + CELLPAD);
        this.cellCol = 1 + (column - CELLSIZE)/(CELLSIZE + CELLPAD);
        this.cells = new boolean[cellRow][cellCol];
        this.cellsSts = new short[cellRow][cellCol];
        setCells((boolean[][])null);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, row, column);
        paint();
    }

    public Canvas getCvs(){
        return this.cvs;
    }

    public void setCells(boolean[][] data){
        if(data == null){ //If data is null, this.cells fill false(0).
            for(int i = 0; i < cellRow; i++){
                for(int j = 0; j < cellCol; j++){
                    cells[i][j] = false;
                }
            }
        }else{
            for(int i = 0; i < data.length; i++){
                for(int j = 0; j < data[0].length; j++){
                    this.cells[i][j] = data[i][j];
                }
            }
//            this.cells = data;
        }
        loadCellsSts();
    }

    public void setCells(int[][] data){
        int dataWidth = data.length;
        int dataHeight = data[0].length;
        boolean[][] d = new boolean[dataWidth][dataHeight];
        for(int i = 0; i < dataWidth; i++){
            for(int j = 0; j < dataHeight; j++){
                if(data[i][j] == 0){
                    d[i][j] = false;
                }else{
                    d[i][j] = true;
                }
            }
        }

        setCells(d);
    }

    public void randomSetCells(){
        setCells(this.randomMap());
    }

    public void updateCells(){
        //a cell is born <- living cells exist 3 around the cell.
        //a cell alive <- living cells exist 2~3 around the cell.
        //a cell die <- living cells exist 0~1 or 4~8 around the cell.
        for(int i = 0; i < cellRow; i++){
            for(int j = 0; j < cellCol; j++){
                short status = cellsSts[i][j];
                if(!cells[i][j] && status == 3){
                    cells[i][j] = true;
                }else if(cells[i][j] && (status == 2 || status == 3)){
                    cells[i][j] = true; //cell alive.
                }else{
                    cells[i][j] = false;//cell die.
                }
            }
        }
        // next cells status update.
        loadCellsSts();

    }

    public void paint(){
        gc.setFill(Color.LIME);

        int p = CELLSIZE + CELLPAD;
        for(int i = 0; i < cellRow; i++){
            for(int j = 0; j < cellCol; j++){
                if(cells[i][j]){ //cells[i][j] == true
                    gc.setFill(Color.LIMEGREEN);
                }else{ //cells[i][j] == false
                    gc.setFill(Color.DIMGRAY);
                }
                gc.fillRect(i*p, j*p, CELLSIZE, CELLSIZE);
            }
        }
    }

//----------------------------------------------------------------------

    private void loadCellsSts(){
        clearCellsSts();

        for(int i = 0; i < cellRow; i++){
            for(int j = 0; j < cellCol; j++){
                // a cell is false -> status = 0
                // a cell is true -> status = 1
//                short status = 0;
//                if(cells[i][j]) status++;

                if(cells[i][j]) {
                    // cells[i][j] effect area.
                    for (int p = -1; p <= 1; p++) {
                        for (int q = -1; q <= 1; q++) {
                            if (p == 0 && q == 0) continue;
                            int posr = i + p;
                            int posc = j + q;
                            //cells end is loop.
                            if (posr < 0) posr += cellRow;
                            else if (posr >= cellRow) posr -= cellRow;
                            if (posc < 0) posc += cellCol;
                            else if (posc >= cellCol) posc -= cellCol;

                            cellsSts[posr][posc]++;
                        }
                    }
                }
            }
        }

        //debug
//        for(short[] status : this.cellsSts){
//            for(short s : status){
//                System.out.print(s + ", ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    private void clearCellsSts(){
        for(int i = 0; i < cellsSts.length; i++){
            for(int j = 0; j < cellsSts[0].length; j++){
                cellsSts[i][j] = 0;
            }
        }
    }

    private boolean[][] randomMap(){
        boolean[][] randMap = new boolean[cellRow][cellCol];
        int probability = 50;// percent

        Random rnd = new Random();
        for(int i = 0; i < cellRow; i++){
            for(int j = 0; j < cellCol; j++){
                if(rnd.nextInt(100) < probability){
                    randMap[i][j] = true;
                }else{
                    randMap[i][j] = false;
                }
            }
        }

        return randMap;
    }




}