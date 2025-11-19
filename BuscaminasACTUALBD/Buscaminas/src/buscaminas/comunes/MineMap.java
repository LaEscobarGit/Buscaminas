package buscaminas.comunes;
import java.util.Random;

//esta clase define donde se encuentran las minas
public class MineMap {
    int numMines;
    int fRow;
    int fCol;
    int ROWS;
    int COLS;
    boolean[][] isMined;
    Random random = new Random();

    public MineMap(int fRow, int fCol, int ROWS, int COLS) {
        super();
        this.fRow = fRow;
        this.fCol = fCol;
        this.ROWS = ROWS;
        this.COLS = COLS;
        isMined = new boolean[ROWS][COLS];
    }

    //Donde estan las minas
    public void newMineMap(int numMines) {
        this.numMines = numMines;
        
        for(int i=0;i<numMines;i++){
            int row;
            int col;
            do{
                row = random.nextInt(ROWS);
                col = random.nextInt(COLS);
            }while(isMined[row][col]==true || (fRow==row && fCol==col) || (fRow-1==row && fCol-1==col)
                    || (fRow-1==row && fCol==col) || (fRow-1==row && fCol+1==col) || (fRow==row && fCol-1==col)
                    || (fRow==row && fCol+1==col) || (fRow+1==row && fCol-1==col) || (fRow+1==row && fCol==col)
                    || (fRow+1==row && fCol+1==col));
            isMined[row][col] = true;
        }
    }
}