package poo.proyecto;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static poo.proyecto.MineSweeperConstants.ROWS;
import static poo.proyecto.MineSweeperConstants.COLS;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;
    public static final int CANVAS_WIDTH  = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    Cell cells[][] = new Cell[ROWS][COLS];
    boolean[][] isMined = new boolean[ROWS][COLS];
    int numMines = 10;
    int vidas = 3;
    int clicks = 0;

    public GameBoardPanel() {
        super.setLayout(new GridLayout(ROWS, COLS, 2, 2));
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        // Listener para todos los botones
        MouseListener listener = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e){
                Cell cell = (Cell) e.getSource();
                clicks++;
                if(SwingUtilities.isLeftMouseButton(e)){
                    revealCell(cell.getRow(),cell.getCol());
                }else if(SwingUtilities.isRightMouseButton (e)){
                    if(cell.isRevealed()==false){
                        if(cell.isFlagged()==true){
                            cell.setText("");
                            cell.setFlagged(false);
                        }else{
                            cell.setText("flag");
                            cell.setFlagged(true);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };

        //Dar el listener a todos los botoncitos
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                cells[i][j].addMouseListener(listener);
            }
        }

        super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
   }

    // Initialize and re-initialize a new game
    public void newGame() {
        //Hacer el mapa de minas
        MineMap mineMap = new MineMap();
        mineMap.newMineMap(numMines);

        // Resetear todo
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].newGame(mineMap.isMined[row][col]);
            }
        }
    }

    //Minas alrededor
    private int getSurroundingMines(int srcRow, int srcCol) {
        int countMines = 0;
        for (int row = srcRow - 1; row <= srcRow + 1; row++) {
            for (int col = srcCol - 1; col <= srcCol + 1; col++) {
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                    if (cells[row][col].isMined)
                        countMines++;
                }
            }
        }
        return countMines;
    }

    //Revelar las celdas
    //NOTA: si se revela una que no tiene minas alrededor se revelan todos los de alrededor
    private void revealCell(int srcRow, int srcCol) {
        cells[srcRow][srcCol].setRevealed(true);
        cells[srcRow][srcCol].paint();
        if (cells[srcRow][srcCol].isMined){
            cells[srcRow][srcCol].setText("mina");
            vidas--;
            if(vidas==0){
                lost();
            }
        }
        else{
            int countMines = getSurroundingMines(srcRow, srcCol);
            if(countMines!=0){
                cells[srcRow][srcCol].setText(countMines + "");
            }else{
                cells[srcRow][srcCol].setText("");
            }
            if (countMines == 0) {
                for (int row = srcRow - 1; row <= srcRow + 1; row++) {
                    for (int col = srcCol - 1; col <= srcCol + 1; col++) {
                        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                            if (!cells[row][col].isRevealed)
                                revealCell(row, col);
                        }
                    }
                }
            }
            hasWon();
        }
    }

    // Return true if the player has won (all cells have been revealed or were mined)
    public boolean hasWon() {
        /*boolean lost;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(cells[row][col].isMined()){
                    cells[row][col].isRevealed();
                    lost=true;
                }
            }
        }*/
        return true;
    }
    
    public void lost() {
        System.out.println("perdedor");
    }

    // [TODO 2] Define a Listener Inner Class
    // .........
}