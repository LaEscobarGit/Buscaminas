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

    Menu menu;
    
    Cell cells[][] = new Cell[ROWS][COLS];
    boolean[][] isMined = new boolean[ROWS][COLS];
    boolean[][] marcado;
    int numMines = 15;
    int vidas = 3;
    int clicks = 0;
    int bvclicks = 0;
    double bvs = 0;
    int puntos = 0;
    
    int seg = 0;

    public GameBoardPanel(Menu menu) {
        this.menu = menu;
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
                
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(clicks==0){
                        newGame(cell.getRow(),cell.getCol());
                    }
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
                clicks++;
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
    public void newGame(int fRow, int fCol) {
        //Hacer el mapa de minas
        MineMap mineMap = new MineMap(fRow, fCol);
        mineMap.newMineMap(numMines);

        // Resetear todo
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].newGame(mineMap.isMined[row][col]);
            }
        }
        menu.setCrono();
        calcular3BV();
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
            System.out.println("Perdio una vida!");
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

    //Verdadero si ha ganado
    public boolean hasWon() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(cells[row][col].isMined()==false){
                    if(cells[row][col].isRevealed()==false){
                        return false;
                    }
                }
            }
        }
        System.out.println("ganaste guapo");
        seg = menu.pararCrono();
        calcularPuntaje();
        return true;
    }
    
    public void lost() {
        System.out.println("perdedor bish");
        seg = menu.pararCrono();
        calcularPuntaje();
    }

    public void calcular3BV(){
        marcado = new boolean[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(!cells[row][col].isMined() && !marcado[row][col]){
                    if(getSurroundingMines(row,col)==0){
                        marcado[row][col] = true;
                        flood(row,col);
                        bvclicks++;
                    }
                    if(!alrededorCero(row,col)){
                        marcado[row][col] = true;
                        bvclicks++;
                    }
                }
            }
        }
    }
    
    public void flood(int row, int col){
        for(int r=row-1 ; r<=row+1 ; r++){
            for(int c=col-1 ; c<=col+1 ; c++){
                if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
                    if(!cells[r][c].isMined() && !marcado[r][c]){
                        marcado[r][c] = true;
                        if(getSurroundingMines(r,c)==0){
                            flood(r,c);
                        }
                    }
                }
            }
        }
    }
    
    public boolean alrededorCero(int row, int col){
        for(int r=row-1 ; r<=row+1 ; r++){
            for(int c=col-1 ; c<=col+1 ; c++){
                if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
                    if(getSurroundingMines(r,c)==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void calcularPuntaje(){
        bvs = (double)bvclicks/(double)seg;
        puntos = (int)Math.ceil(bvs*1000);
        if(vidas==2){
            puntos = (int)Math.ceil(puntos*0.95);
        }else if(vidas==1){
            puntos = (int)Math.ceil(puntos*0.85);
        }else if(vidas==0){
            puntos = (int)Math.ceil(puntos*0.50);
        }
        System.out.println(bvclicks);
        System.out.println(seg);
        System.out.println(bvs);
        System.out.println(puntos);
    }
}