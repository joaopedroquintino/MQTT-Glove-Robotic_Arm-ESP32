package object_3d;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import static javax.media.j3d.PolygonAttributes.*;
import javax.media.j3d.RotationInterpolator;

public class Object_3D extends javax.swing.JFrame {

    private Transform3D rotate1 = new Transform3D();
    private Transform3D rotate2 = new Transform3D();

    public Object_3D() {
        //super("Object_3D");
//        Canvas3D canvas3D = createCanvas3D();
//        BranchGroup scene = createSceneGraph();
//        connect(canvas3D, scene);
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private Canvas3D createCanvas3D() {
        setSize(300, 300);
        setLayout(new BorderLayout());
        GraphicsConfiguration config
                = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        setSize(300, 300);
        getContentPane().add(canvas3D);
        return canvas3D;
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        //TransformGroup rotator = new TransformGroup(rotateCube());
        //objRoot.addChild(rotator);
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(new ColoringAttributes(0.2f, 0.2f, 0.4f, 1));
        PolygonAttributes pa = new PolygonAttributes();
        pa.setPolygonMode(2);
        appearance.setPolygonAttributes(pa);
        //rotator.addChild(new Box(0.3f,0.1f,0.3f, appearance));
        TransformGroup spinner = new TransformGroup();
        spinner.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(spinner);
        spinner.addChild(new Box(0.3f, 0.1f, 0.3f, appearance));
        spinner.addChild(makeSpin(spinner));
        objRoot.compile();

        return objRoot;
    }

    private RotationInterpolator makeSpin(TransformGroup spinner) {
        RotationInterpolator rotator = 
                new RotationInterpolator(new Alpha(-1,3000), spinner);
        rotator.setAxisOfRotation(rotateCube());
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        return rotator;
    }

    private Transform3D rotateCube() {
        rotate1.rotX(Math.PI /6.0d );
        rotate2.rotY(Math.PI /6.0d );
        rotate1.mul(rotate2);
        return rotate1;
    }

    public void connect(Canvas3D canvas3D, BranchGroup scene) {
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
    }
    
    private void initComponents() {

        jPanel1 = new jPanel1();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        //jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pack();
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Object_3D().setVisible(true);
            }
        });
    }
    
    private javax.swing.JPanel jPanel1;
}
