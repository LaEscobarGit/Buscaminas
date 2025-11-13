package buscaminas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Celda extends JButton implements MouseListener{
    private int fila;
    private int columna;
    private boolean revelado; //esta revelado o no
    private boolean bandera; //tiene bandera o no
    private boolean mina; //es una mina o no
    
    private Color bgRevelado = new Color(242, 172, 206); //fondo revelado
    private Color bgNoRevelado = new Color(255, 191, 222); //fondo no revelado
    private Color fgRevelado = new Color(214, 0, 104); //letras reveladas
    private Color fgNoRevelado = new Color(214, 0, 104); //letras no reveladas
    
    public Celda(int fila, int columna){
        super();
        this.fila = fila;
        this.columna = columna;
        this.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            if(revelado==false){
                setForeground(fgRevelado);
                setBackground(bgRevelado);
                super.setText("");
            }else{
                //nada
            }
        }else if(SwingUtilities.isRightMouseButton (e)){
            if(bandera){
                //quitar bandera
            }else{
                 //poner bandera
            }
        }
    }

    public void inicio(boolean mina) {
       this.revelado = false;
       this.bandera = false;
       this.mina = mina;
       super.setEnabled(true);
       super.setText("");
       setForeground(fgNoRevelado);
       setBackground(bgNoRevelado);
    }
    
    
    
    @Override
    public void mousePressed(MouseEvent e) {
        //
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }
    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
    
}
