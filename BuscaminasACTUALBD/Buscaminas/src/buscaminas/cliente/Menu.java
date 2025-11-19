package buscaminas.cliente;

import buscaminas.comunes.BaseDatosBuscaminas;
import buscaminas.comunes.ConfiguracionJuego;
import buscaminas.comunes.GameBoardPanel;
import buscaminas.comunes.Mensaje;
import buscaminas.recursos.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;


public class Menu extends javax.swing.JFrame {
    CardLayout cardLayout;
    CardLayout cardLayoutSmall;
    GameBoardPanel board;
    
    //socket
    Socket socket;
    ObjectInputStream entradaDatos;
    ObjectOutputStream salidaDatos;
    
    //tiempo
    private Thread hiloCrono;
    private boolean cronoCorre=false;
    int seg = 0;
    
    //usuario
    String nombre;
    int jugador;
    int vidas;
    boolean oListo;
    boolean jListo;
    String personaje = null;
    
    //oponente
    String personajeOP = null;
    int vidasOP;
    
    //Tablero
    String tableroStr = null;
    Tablero tablero;
    int filas;
    int columnas;
    int minas;
    
    //GUI
    private String tarjetaActual = "Roberto";
    
    public Menu() {
        initComponents();
        
        // Chara select stuff //
        
        charaSelectPanel.add(chara1Panel, "Roberto");
        charaSelectPanel.add(chara2Panel, "AntiRoberto");
        
        //
        
        smallPane.setLayout(new CardLayout());
        GraphicsEnvironment en = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = en.getDefaultScreenDevice();
        
        mainPane.add(menuPane, "menuPane");
        mainPane.add(optionPane, "optionPane");
        mainPane.add(perPane, "perPane");
        mainPane.add(modePane, "modePane");
        mainPane.add(gamePane, "gamePane");
        mainPane.add(rankPane, "rankPane");
        Component[] components = this.getContentPane().getComponents();
        for(Component component : components){
            if(component instanceof JButton){
                ((JButton) component).setUI(new BasicButtonUI());
                ((JButton) component).setFocusPainted(false);
            }
        }
        cardLayout = (CardLayout)(mainPane.getLayout());
        
        smallPane.add(botPane, "botPane");
        smallPane.add(logPane, "logPane");
        Component[] componentsSmall = menuPane.getComponents();
        for(Component component : componentsSmall){
            if(component instanceof JButton){
                ((JButton) component).setUI(new BasicButtonUI());
                ((JButton) component).setFocusPainted(false);
            }
        }
        cardLayoutSmall = (CardLayout)(smallPane.getLayout());
        cardLayoutSmall.show(smallPane, "logPane");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JPanel();
        perPane = new buscaminas.recursos.Fondo("/buscaminas/recursos/charaSelScreen.png");
        jLabel3 = new javax.swing.JLabel();
        charaSelectPanel = new javax.swing.JPanel();
        chara1Panel = new javax.swing.JPanel();
        chara1 = new javax.swing.JLabel();
        chara2Panel = new javax.swing.JPanel();
        chara2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        flechaDer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        flechaIzq = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jugarBot2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        modePane = new buscaminas.recursos.Fondo("/buscaminas/recursos/menuscreen.png");
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        clasicBot = new javax.swing.JButton();
        backBot = new javax.swing.JButton();
        batBot = new javax.swing.JButton();
        optionPane = new buscaminas.recursos.Fondo("/buscaminas/recursos/menuscreen.png");
        jPanel5 = new javax.swing.JPanel();
        sigBot = new javax.swing.JButton();
        norBot = new javax.swing.JButton();
        ezBot = new javax.swing.JButton();
        hardBot = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        gamePane = new buscaminas.recursos.Fondo("/buscaminas/recursos/gamescreen.png");
        boardPane = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        tiempoLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        menuPane = new buscaminas.recursos.Fondo("/buscaminas/recursos/menuscreen.png");
        jLabel1 = new javax.swing.JLabel();
        smallPane = new javax.swing.JPanel();
        logPane = new javax.swing.JPanel();
        conectarBot = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        logField = new javax.swing.JTextField();
        botPane = new javax.swing.JPanel();
        jugarBot = new javax.swing.JButton();
        topBot = new javax.swing.JButton();
        salirBot = new javax.swing.JButton();
        rankPane = new buscaminas.recursos.Fondo("/buscaminas/recursos/menuscreen.png");
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lovesweeper");
        setMinimumSize(new java.awt.Dimension(1024, 576));
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 576));

        mainPane.setLayout(new java.awt.CardLayout());

        perPane.setMaximumSize(new java.awt.Dimension(1024, 576));
        perPane.setMinimumSize(new java.awt.Dimension(1024, 576));
        perPane.setPreferredSize(new java.awt.Dimension(1024, 576));

        jLabel3.setFont(new java.awt.Font("DinkieBitmap 7px Demo", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CHOOSE YOUR FIGHTER");

        charaSelectPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        charaSelectPanel.setOpaque(false);
        charaSelectPanel.setLayout(new java.awt.CardLayout());

        chara1Panel.setOpaque(false);

        chara1.setFont(new java.awt.Font("PixelMplus10", 0, 10)); // NOI18N
        chara1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chara1.setText("roberto full body pic 1");

        javax.swing.GroupLayout chara1PanelLayout = new javax.swing.GroupLayout(chara1Panel);
        chara1Panel.setLayout(chara1PanelLayout);
        chara1PanelLayout.setHorizontalGroup(
            chara1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chara1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chara1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );
        chara1PanelLayout.setVerticalGroup(
            chara1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chara1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chara1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );

        charaSelectPanel.add(chara1Panel, "card2");

        chara2Panel.setMaximumSize(new java.awt.Dimension(276, 392));
        chara2Panel.setMinimumSize(new java.awt.Dimension(276, 392));
        chara2Panel.setOpaque(false);
        chara2Panel.setPreferredSize(new java.awt.Dimension(276, 392));

        chara2.setFont(new java.awt.Font("PixelMplus10", 0, 10)); // NOI18N
        chara2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chara2.setText("roberto full body pic 2");

        javax.swing.GroupLayout chara2PanelLayout = new javax.swing.GroupLayout(chara2Panel);
        chara2Panel.setLayout(chara2PanelLayout);
        chara2PanelLayout.setHorizontalGroup(
            chara2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chara2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chara2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );
        chara2PanelLayout.setVerticalGroup(
            chara2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chara2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chara2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        charaSelectPanel.add(chara2Panel, "card3");

        jPanel2.setOpaque(false);

        flechaDer.setFont(new java.awt.Font("PixelMplus10", 0, 10)); // NOI18N
        flechaDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/arrowRight.png"))); // NOI18N
        flechaDer.setBorderPainted(false);
        flechaDer.setContentAreaFilled(false);
        flechaDer.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/arrowRightPressed.png"))); // NOI18N
        flechaDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaDerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaDer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaDer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setOpaque(false);

        flechaIzq.setFont(new java.awt.Font("PixelMplus10", 0, 10)); // NOI18N
        flechaIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/arrowLeft.png"))); // NOI18N
        flechaIzq.setBorderPainted(false);
        flechaIzq.setContentAreaFilled(false);
        flechaIzq.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/arrowLeftPressed.png"))); // NOI18N
        flechaIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flechaIzqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flechaIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setOpaque(false);

        jugarBot2.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        jugarBot2.setText("♥ SELECT ♥");
        jugarBot2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jugarBot2ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Backstory ♥", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("PixelMplus10", 0, 10))); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("ロベルトはプログラマーで、マインスイーパが大好きで、愛を見つけるためにここにいます。");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel15.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ROBERTO AZUL");

        jPanel8.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("PixelMplus10", 0, 10)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("roberto face");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jugarBot2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jugarBot2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout perPaneLayout = new javax.swing.GroupLayout(perPane);
        perPane.setLayout(perPaneLayout);
        perPaneLayout.setHorizontalGroup(
            perPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, perPaneLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(perPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(perPaneLayout.createSequentialGroup()
                        .addComponent(charaSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        perPaneLayout.setVerticalGroup(
            perPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(perPaneLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(perPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(perPaneLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(perPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(charaSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        mainPane.add(perPane, "card2");

        modePane.setMaximumSize(new java.awt.Dimension(1024, 576));
        modePane.setMinimumSize(new java.awt.Dimension(1024, 576));
        modePane.setPreferredSize(new java.awt.Dimension(1024, 576));

        jLabel12.setFont(new java.awt.Font("DinkieBitmap 7px Demo", 1, 48)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("LOVESWEEPER");

        jPanel4.setOpaque(false);

        clasicBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        clasicBot.setText("Classic");
        clasicBot.setEnabled(false);
        clasicBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasicBotActionPerformed(evt);
            }
        });

        backBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        backBot.setText("Back");
        backBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBotActionPerformed(evt);
            }
        });

        batBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        batBot.setText("Battle");
        batBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batBotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(279, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(batBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clasicBot, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addGap(64, 64, 64))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(clasicBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(batBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout modePaneLayout = new javax.swing.GroupLayout(modePane);
        modePane.setLayout(modePaneLayout);
        modePaneLayout.setHorizontalGroup(
            modePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modePaneLayout.createSequentialGroup()
                .addGroup(modePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(modePaneLayout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(391, 391, 391)))
                .addContainerGap())
        );
        modePaneLayout.setVerticalGroup(
            modePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modePaneLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel12)
                .addGap(49, 49, 49)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        mainPane.add(modePane, "card3");

        optionPane.setMaximumSize(new java.awt.Dimension(1024, 576));
        optionPane.setMinimumSize(new java.awt.Dimension(1024, 576));
        optionPane.setPreferredSize(new java.awt.Dimension(1024, 576));

        jPanel5.setOpaque(false);

        sigBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        sigBot.setText("NEXT");
        sigBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigBotActionPerformed(evt);
            }
        });

        norBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        norBot.setText("Normal");
        norBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                norBotActionPerformed(evt);
            }
        });

        ezBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        ezBot.setText("EZ");
        ezBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ezBotActionPerformed(evt);
            }
        });

        hardBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        hardBot.setText("♥ VERY HARD ♥");
        hardBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardBotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(sigBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hardBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(norBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ezBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(ezBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(norBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hardBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sigBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("DinkieBitmap 7px Demo", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOVESWEEPER");

        javax.swing.GroupLayout optionPaneLayout = new javax.swing.GroupLayout(optionPane);
        optionPane.setLayout(optionPaneLayout);
        optionPaneLayout.setHorizontalGroup(
            optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(optionPaneLayout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(330, 330, 330))
        );
        optionPaneLayout.setVerticalGroup(
            optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionPaneLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPane.add(optionPane, "card2");

        gamePane.setMaximumSize(new java.awt.Dimension(1024, 576));
        gamePane.setMinimumSize(new java.awt.Dimension(1024, 576));

        boardPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        boardPane.setOpaque(false);

        javax.swing.GroupLayout boardPaneLayout = new javax.swing.GroupLayout(boardPane);
        boardPane.setLayout(boardPaneLayout);
        boardPaneLayout.setHorizontalGroup(
            boardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );
        boardPaneLayout.setVerticalGroup(
            boardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(192, 272));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        tiempoLabel.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        tiempoLabel.setForeground(new java.awt.Color(102, 102, 255));
        tiempoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tiempoLabel.setText("000");

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(192, 272));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        jPanel1.setOpaque(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setOpaque(false);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setOpaque(false);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setOpaque(false);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setOpaque(false);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setOpaque(false);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscaminas/recursos/lives.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout gamePaneLayout = new javax.swing.GroupLayout(gamePane);
        gamePane.setLayout(gamePaneLayout);
        gamePaneLayout.setHorizontalGroup(
            gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePaneLayout.createSequentialGroup()
                .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePaneLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gamePaneLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(boardPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(gamePaneLayout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(gamePaneLayout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(tiempoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        gamePaneLayout.setVerticalGroup(
            gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePaneLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePaneLayout.createSequentialGroup()
                        .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(gamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(boardPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tiempoLabel)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        mainPane.add(gamePane, "card2");

        menuPane.setMaximumSize(new java.awt.Dimension(1024, 576));
        menuPane.setMinimumSize(new java.awt.Dimension(1004, 576));
        menuPane.setPreferredSize(new java.awt.Dimension(1024, 576));

        jLabel1.setFont(new java.awt.Font("DinkieBitmap 7px Demo", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOVESWEEPER");

        smallPane.setOpaque(false);
        smallPane.setLayout(new java.awt.CardLayout());

        logPane.setOpaque(false);

        conectarBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        conectarBot.setText("♥ Enter ♥");
        conectarBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarBotActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("User:");

        javax.swing.GroupLayout logPaneLayout = new javax.swing.GroupLayout(logPane);
        logPane.setLayout(logPaneLayout);
        logPaneLayout.setHorizontalGroup(
            logPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPaneLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(logPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logField, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conectarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        logPaneLayout.setVerticalGroup(
            logPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPaneLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(logField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(conectarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        smallPane.add(logPane, "card2");

        botPane.setOpaque(false);

        jugarBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        jugarBot.setText("Play");
        jugarBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jugarBotActionPerformed(evt);
            }
        });

        topBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        topBot.setText("Ranking");
        topBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topBotActionPerformed(evt);
            }
        });

        salirBot.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        salirBot.setText("Exit");
        salirBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirBotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botPaneLayout = new javax.swing.GroupLayout(botPane);
        botPane.setLayout(botPaneLayout);
        botPaneLayout.setHorizontalGroup(
            botPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botPaneLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(botPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jugarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topBot, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salirBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(147, 147, 147))
        );
        botPaneLayout.setVerticalGroup(
            botPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botPaneLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jugarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(topBot, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(salirBot, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(115, 115, 115))
        );

        smallPane.add(botPane, "card3");

        javax.swing.GroupLayout menuPaneLayout = new javax.swing.GroupLayout(menuPane);
        menuPane.setLayout(menuPaneLayout);
        menuPaneLayout.setHorizontalGroup(
            menuPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPaneLayout.createSequentialGroup()
                .addGroup(menuPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPaneLayout.createSequentialGroup()
                        .addGap(301, 301, 301)
                        .addComponent(smallPane, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuPaneLayout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)))
                .addGap(217, 217, 217))
        );
        menuPaneLayout.setVerticalGroup(
            menuPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPaneLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(smallPane, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        mainPane.add(menuPane, "card3");

        rankPane.setMaximumSize(new java.awt.Dimension(1024, 576));
        rankPane.setMinimumSize(new java.awt.Dimension(1024, 576));

        jButton1.setFont(new java.awt.Font("PixelMplus10", 0, 18)); // NOI18N
        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("DinkieBitmap 7px Demo", 1, 48)); // NOI18N
        jLabel4.setText("TOP 10");

        javax.swing.GroupLayout rankPaneLayout = new javax.swing.GroupLayout(rankPane);
        rankPane.setLayout(rankPaneLayout);
        rankPaneLayout.setHorizontalGroup(
            rankPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rankPaneLayout.createSequentialGroup()
                .addGap(454, 454, 454)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(455, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rankPaneLayout.setVerticalGroup(
            rankPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankPaneLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        mainPane.add(rankPane, "card7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBotActionPerformed
        cardLayout.show(mainPane, "menuPane");
    }//GEN-LAST:event_backBotActionPerformed

    private void clasicBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasicBotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clasicBotActionPerformed

    private void batBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batBotActionPerformed
        try {
            socket = new Socket("localhost",35558);
            salidaDatos = new ObjectOutputStream(socket.getOutputStream());
            salidaDatos.flush();
            entradaDatos = new ObjectInputStream(socket.getInputStream());
            
            //PRUEBAS
            System.out.println("salida y entrada ready");
            
            try{
                Mensaje msj = (Mensaje) entradaDatos.readObject();
                if("RECHAZADO".equals(msj.getTipo())){
                    JOptionPane.showMessageDialog(this, "Lleno, intenta mas tarde");
                    salidaDatos.close();
                    entradaDatos.close();
                    socket.close();
                    return;
                }
            }catch(HeadlessException | IOException | ClassNotFoundException e){
                //
            }
            
            escucharMensaje();
            enviarMensaje("NOMBRE",nombre);
            enviarMensaje("CONEXION",null);
            
            if(oListo==false){
                System.out.println("Buscando jugador");
            }
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_batBotActionPerformed

    private void jugarBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jugarBotActionPerformed
        cardLayout.show(mainPane, "modePane");
    }//GEN-LAST:event_jugarBotActionPerformed

    private void sigBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sigBotActionPerformed
        if(jugador==1){
            enviarMensaje("TABLERO", tableroStr);
            enviarMensaje("OPCIONES", true);
        }
        cardLayout.show(mainPane, "perPane");
    }//GEN-LAST:event_sigBotActionPerformed

    private void conectarBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarBotActionPerformed
        nombre = logField.getText().trim();
        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingresa un nombre");
            return;
        }
        cardLayoutSmall.show(smallPane, "botPane");
    }//GEN-LAST:event_conectarBotActionPerformed

    private void ezBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ezBotActionPerformed
        tableroStr = "Principiante";
    }//GEN-LAST:event_ezBotActionPerformed

    private void norBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_norBotActionPerformed
        tableroStr = "Intermedio";
    }//GEN-LAST:event_norBotActionPerformed

    private void hardBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardBotActionPerformed
        tableroStr = "Avanzado";
    }//GEN-LAST:event_hardBotActionPerformed

    private void salirBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirBotActionPerformed
       System.exit(0);
    }//GEN-LAST:event_salirBotActionPerformed

    private void jugarBot2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jugarBot2ActionPerformed
        personaje = tarjetaActual;
        enviarMensaje("PERSONAJE", personaje);
        
        if (jugador == 1) {
            jugarBot2.setEnabled(true);
        }
        
        enviarMensaje("SELECCION", true);
        if(jListo==false){
            System.out.println("Eligiendo personaje");
        }
        
    }//GEN-LAST:event_jugarBot2ActionPerformed

    private void flechaIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaIzqActionPerformed
        CardLayout cl = (CardLayout) charaSelectPanel.getLayout();
        cl.previous(charaSelectPanel);

        if (tarjetaActual.equals("Roberto")) {
            tarjetaActual = "AntiRoberto";
        } else {
            tarjetaActual = "Roberto";
        }
    }//GEN-LAST:event_flechaIzqActionPerformed

    private void flechaDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flechaDerActionPerformed
        CardLayout cl = (CardLayout) charaSelectPanel.getLayout();
        cl.next(charaSelectPanel);

        // Cambiar valor según el orden
        if (tarjetaActual.equals("Roberto")) {
            tarjetaActual = "AntiRoberto";
        } else {
            tarjetaActual = "Roberto";
        }
    }//GEN-LAST:event_flechaDerActionPerformed

    private void topBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topBotActionPerformed
        cardLayout.show(mainPane, "rankPane");
    }//GEN-LAST:event_topBotActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cardLayout.show(mainPane, "menuPane");
    }//GEN-LAST:event_jButton1ActionPerformed

    //tiempo
    public void setCrono(){
        cronoCorre=true;
        seg=0;
        tiempoLabel.setText("000");
        hiloCrono = new Thread(() -> {
            try {
                while(cronoCorre){
                    Thread.sleep(1000);
                    seg++;
                    String tiempo = String.format("%02d",seg);
                    tiempoLabel.setText(tiempo);
                }
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        });
        hiloCrono.start();
    }
    public int pararCrono(){
        hiloCrono.interrupt();
        cronoCorre=false;
        return seg;
    }
    
    public void terminarJuego(boolean vivir){
        if(vivir){
            enviarMensaje("VIVIR",true);
            //mostrar pantalla ganadora
        }else{
            enviarMensaje("VIVIR",false);
            //mostrar pantalla perdedora
        }
    }
    
    public void perderVida(int vidas){
        this.vidas = vidas;
        enviarMensaje("VIDAS",vidas);
    }
    
    public void escucharMensaje(){
        new Thread(()->{
            try{
                while(true){
                    Object obj = entradaDatos.readObject();
                    Mensaje msj = (Mensaje) obj;
                    System.out.println("Servidor dice:" + msj);
                    decodificar(msj);
                }
            }catch(IOException | ClassNotFoundException e){
                //
            }
        }).start();
    }
    
    public void decodificar(Mensaje msj){
        switch(msj.getTipo()){
            case "CONEXION":if((Integer) msj.getContenido()==1){
                                oListo = false;
                            }else if((Integer) msj.getContenido()==2){
                                oListo = true;
                                if(jugador==1){
                                    cardLayout.show(mainPane, "optionPane");
                                }else if(jugador==2){
                                    System.out.println("Jugador 1 esta eligiendo");
                                }
                            }   
            break;
            case "JUGADOR": jugador = ((Integer) msj.getContenido())+1;
            break;
            case "TABLERO": tableroStr = String.valueOf(msj.getContenido());
                            try(InputStream input = getClass().getResourceAsStream("/buscaminas/recursos/tableros.json")){
                                InputStreamReader reader = new InputStreamReader(input);
                                Gson gson = new Gson();
                                TablerosConfig config = gson.fromJson(reader, TablerosConfig.class);
                                tablero = config.buscarPorNombre(tableroStr);
                               
                                filas = tablero.getFilas();
                                columnas = tablero.getColumnas();
                                minas = tablero.getMinas();
                            }catch(Exception e){
                                //
                            }      
            break;
            case "OPCIONES":if((Boolean) msj.getContenido()){
                                cardLayout.show(mainPane, "perPane");
                            }
            break;
            case "OPONENTE": personajeOP = String.valueOf(msj.getContenido());
            break;
            case "SELECCION":if(!(Boolean) msj.getContenido()){
                                jListo = false;
                            }else if((Boolean) msj.getContenido()){
                                jListo = true;
                                        BaseDatosBuscaminas db = new BaseDatosBuscaminas();
                                        db.guardarConfiguracion(new ConfiguracionJuego(dificultad, filas, columnas, minas));
                                        db.cerrar();
                                board = new GameBoardPanel(this, filas, columnas, minas);
                                cardLayout.show(mainPane, "gamePane");
                                boardPane.setLayout(new BorderLayout());
                                boardPane.add(board, BorderLayout.CENTER);
                                boardPane.revalidate();
                                boardPane.repaint();
                            }   
            break;
            case "VIDAS": vidasOP = (Integer) msj.getContenido();
            break;
            case "DESCONECTADO": 
            break;
            case "EXIT": 
            break;
        }
    }
    
    public void enviarMensaje(String tipo, Object contenido){
        try{
            Mensaje msj = new Mensaje(tipo, contenido);
            salidaDatos.writeObject(msj);
            salidaDatos.flush();
            System.out.println(msj);
        }catch(IOException e){
            
        }
    }
    
    // Métodos para la la estética de la GUI //
    
    private String obtenerPanelActual() {
    for (Component c : charaSelectPanel.getComponents()) {
        if (c.isVisible()) {
            return ((CardLayout) charaSelectPanel.getLayout()).toString();
        }
    }
    return null;
}
    
    ///////////////////////////////////////////////////////////////////////
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    FlatLightLaf.registerCustomDefaultsSource( "Buscaminas.recursos" );
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize Look and Feel" );
                }
                
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBot;
    private javax.swing.JButton batBot;
    private javax.swing.JPanel boardPane;
    private javax.swing.JPanel botPane;
    private javax.swing.JLabel chara1;
    private javax.swing.JPanel chara1Panel;
    private javax.swing.JLabel chara2;
    private javax.swing.JPanel chara2Panel;
    private javax.swing.JPanel charaSelectPanel;
    private javax.swing.JButton clasicBot;
    private javax.swing.JButton conectarBot;
    private javax.swing.JButton ezBot;
    private javax.swing.JButton flechaDer;
    private javax.swing.JButton flechaIzq;
    private javax.swing.JPanel gamePane;
    private javax.swing.JButton hardBot;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jugarBot;
    private javax.swing.JButton jugarBot2;
    private javax.swing.JTextField logField;
    private javax.swing.JPanel logPane;
    private javax.swing.JPanel mainPane;
    private javax.swing.JPanel menuPane;
    private javax.swing.JPanel modePane;
    private javax.swing.JButton norBot;
    private javax.swing.JPanel optionPane;
    private javax.swing.JPanel perPane;
    private javax.swing.JPanel rankPane;
    private javax.swing.JButton salirBot;
    private javax.swing.JButton sigBot;
    private javax.swing.JPanel smallPane;
    private javax.swing.JLabel tiempoLabel;
    private javax.swing.JButton topBot;
    // End of variables declaration//GEN-END:variables
}
