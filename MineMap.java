package poo.proyecto;
import java.util.Random;
import static poo.proyecto.MineSweeperConstants.ROWS;
import static poo.proyecto.MineSweeperConstants.COLS;

//esta clase define donde se encuentran las minas
public class MineMap {
    int numMines;
    boolean[][] isMined = new boolean[ROWS][COLS];
    Random random = new Random();

    public MineMap() {
        super();
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
            }while(isMined[row][col]==true);
            isMined[row][col] = true;
        }
    }
}