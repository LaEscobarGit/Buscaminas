package buscaminas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Cell extends JButton {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JButton's colors and fonts
    //  to be chosen based on cell's state
    public static final Color BG_NOT_REVEALED = new Color(255, 191, 222);
    public static final Color FG_NOT_REVEALED = Color.RED;    // flag, mines
    public static final Color BG_REVEALED = new Color(235, 150, 190);
    public static final Color FG_REVEALED = Color.YELLOW; // number of mines
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    int row, col;
    boolean isRevealed;
    boolean isMined;
    boolean isFlagged;

    public Cell(int row, int col) {
       super();
       this.row = row;
       this.col = col;
       super.setFont(FONT_NUMBERS);
       super.setBackground(BG_NOT_REVEALED);
    }

    public void newGame(boolean isMined) {
       this.isRevealed = false;
       this.isFlagged = false;
       this.isMined = isMined;
       super.setEnabled(true);
       super.setText("");
       paint();
    }

    public void paint() {
       super.setForeground(isRevealed? FG_REVEALED: FG_NOT_REVEALED);
       super.setBackground(isRevealed? BG_REVEALED: BG_NOT_REVEALED);
    }

    
    //getters y setters
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public boolean isRevealed() {
        return isRevealed;
    }
    public boolean isMined() {
        return isMined;
    }
    public boolean isFlagged() {
        return isFlagged;
    }
    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
    public void setMined(boolean isMined) {
        this.isMined = isMined;
    }
    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }
}