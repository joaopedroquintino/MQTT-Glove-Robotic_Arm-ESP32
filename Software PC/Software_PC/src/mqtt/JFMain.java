package mqtt;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.io.UnsupportedEncodingException;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JSlider;

/**
 *
 * @author Joao Pedro Quintino
 */
public class JFMain extends javax.swing.JFrame {

    static ClienteMQTT clienteMQTT = new ClienteMQTT("tcp://broker.mqttdashboard.com:1883", "iatrqott", "gW7u8bMQzjOm");
    private static View objeto = new View(0);
    private static Integer y = 0, x = 0, z=0, ga = 0;
    private static char aux;

    public static void setLabel( int c[]) throws UnsupportedEncodingException {
//        aux = (char) c[0];
        x = (int) c[0];  // Valor de angulo no eixo x
        x += (90 - x) * 2;  //Ajuste para corrigir espelhamento

//        aux = (char)c[1];
        y = (int)c[1];   // Valor de angulo no eixo y
        y += 180;       // Ajuste para corrigir orientacao
        
//        aux = (char)c[2];
        z = (int)c[2];   // Valor de angulo no eixo z
        
//        aux = (char) c[3];
        ga = (int) c[3];   // Valor de abertura da garra
    
        objeto.rotate(x, y, z); // Rotaciona objeto 3D
    }
    TimerToLabel timer_label;

    public JFMain() {
        initComponents();
        this.timer_label = new TimerToLabel(100, jSGarra);//Atualiza Slider a cada 100ms
        this.timer_label.init_timer();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPMain = new javax.swing.JPanel();
        panel3D = new javax.swing.JScrollPane(objeto);
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSGarra = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 270, 800));
        setMinimumSize(new java.awt.Dimension(280, 527));
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPMain.setBackground(java.awt.SystemColor.textInactiveText);
        jPMain.setAlignmentX(0.0F);
        jPMain.setAlignmentY(0.0F);
        jPMain.setMaximumSize(new java.awt.Dimension(270, 1000));
        jPMain.setPreferredSize(new java.awt.Dimension(270, 527));

        panel3D.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel3D.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panel3D.setMaximumSize(new java.awt.Dimension(240, 240));
        panel3D.setMinimumSize(new java.awt.Dimension(240, 240));
        panel3D.setPreferredSize(new java.awt.Dimension(240, 240));

        jLabel4.setText("Movimentação:");

        jPanel2.setMaximumSize(new java.awt.Dimension(240, 90));
        jPanel2.setPreferredSize(new java.awt.Dimension(240, 90));

        jLabel2.setText("Aberta");

        jLabel3.setText("Fechada");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSGarra, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSGarra, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(32, 32, 32))
        );

        jBExit.setText("Sair");
        jBExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBExitMouseClicked(evt);
            }
        });
        jBExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExitActionPerformed(evt);
            }
        });

        jLabel1.setText("Garra:");

        javax.swing.GroupLayout jPMainLayout = new javax.swing.GroupLayout(jPMain);
        jPMain.setLayout(jPMainLayout);
        jPMainLayout.setHorizontalGroup(
            jPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMainLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBExit)
                    .addGroup(jPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(jPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel3D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel4)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPMainLayout.setVerticalGroup(
            jPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMainLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jBExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        clienteMQTT.finalizar(); // Finaliza a comunicacao com o Broker
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void jBExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExitActionPerformed

    }//GEN-LAST:event_jBExitActionPerformed

    private void jBExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExitMouseClicked
        clienteMQTT.finalizar(); // Finaliza a comunicacao com o Broker
        System.exit(0);
    }//GEN-LAST:event_jBExitMouseClicked

    public static void main(String args[]) throws Exception {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        try {
            //Executa o CMSClient
            Runtime r = Runtime.getRuntime();
            r.exec("C://Program Files (x86)/CMSClient/CMSClient.exe");

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFMain().setVisible(true);//Inicia a interface
            }
        });
        
        clienteMQTT.iniciar();
        new Ouvinte(clienteMQTT, "b_1jpsq", 0); //Assina Topico no Broker
    }

    //Classe para atualizar o Slider constantemente
    private class TimerToLabel implements ActionListener {

        private Timer timer;
        private final JSlider sliderGarra;
        private final int delay;

        public TimerToLabel(final int delay, final JSlider garra) {
            this.delay = delay;
            this.sliderGarra = garra;
        }

        public void init_timer() {
            timer = new Timer(delay, this);
            timer.start();
        }
        
        // Atualiza os valores do slider no tempo configurado
        public void actionPerformed(final ActionEvent e) {
            sliderGarra.setValue(ga);
            sliderGarra.updateUI();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPMain;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider jSGarra;
    private static javax.swing.JScrollPane panel3D;
    // End of variables declaration//GEN-END:variables
}

class View extends JPanel {

    private Transform3D rotate1 = new Transform3D();//
    private Transform3D rotate2 = new Transform3D();// Objetos auxiliares para rotacao do objeto 3D
    private Transform3D rotate3 = new Transform3D();//
    private BranchGroup objRoot = new BranchGroup();
    private TransformGroup rotator;
    int s = 0, count = 0;

    public View(int x) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(240, 240));//
        setMinimumSize(new Dimension(240, 240));//  Configuracao de tamanho da cena
        setPreferredSize(new Dimension(240, 240));//
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(gc); 
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraph(x);
        scene.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        //Permite que os objetos sejam vistos
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    }

    public BranchGroup createSceneGraph(int x) {
        rotator = new TransformGroup(rotateCube(x));
        objRoot.addChild(rotator);
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(new ColoringAttributes(0.7f, 0.7f, 0.7f, 1));
        PolygonAttributes pa = new PolygonAttributes();
        pa.setPolygonMode(2);
        appearance.setPolygonAttributes(pa);
        rotator.addChild(new Box(0.3f, 0.1f, 0.3f, appearance));
        rotator.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return objRoot;
    }

    public Transform3D rotateCube(int degX) {
        rotate1.rotX(degX * Math.PI / 180.0d);
        rotate2.rotY(Math.PI / 6.0d);
        rotate3.rotZ(0);
        rotate1.mul(rotate2);
        return rotate1;
    }
    
    //Funcao para fazer a rotacao do objeto
    public void rotate(long x, long y, long z) {
        rotate1.rotX(Math.toRadians(x - 90));
        rotate2.rotY(Math.toRadians(z - 90));
        rotate3.rotZ(Math.toRadians(y - 90));
        rotate1.mul(rotate2);
        rotate1.mul(rotate3);
        rotator.setTransform(rotate1);
    }

}
