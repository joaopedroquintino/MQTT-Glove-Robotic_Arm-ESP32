/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object_3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

/**
 *
 * @author joaoq
 */
public class jPanel1 extends javax.swing.JPanel {

    private Transform3D rotate1 = new Transform3D();
    private Transform3D rotate2 = new Transform3D();

    /**
     * Creates new form jPanel1
     */
    public jPanel1() {
        super();
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Canvas3D canvas3D = createCanvas3D();
        BranchGroup scene = createSceneGraph();
        connect(canvas3D, scene);
    }

    private Canvas3D createCanvas3D() {
        setSize(300, 300);
        setLayout(new BorderLayout());
        GraphicsConfiguration config
                = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        setSize(300, 300);
        this.add(canvas3D);
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
        RotationInterpolator rotator
                = new RotationInterpolator(new Alpha(-1, 3000), spinner);
        rotator.setAxisOfRotation(rotateCube());
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        return rotator;
    }

    private Transform3D rotateCube() {
        rotate1.rotX(Math.PI / 6.0d);
        rotate2.rotY(Math.PI / 6.0d);
        rotate1.mul(rotate2);
        return rotate1;
    }

    public void connect(Canvas3D canvas3D, BranchGroup scene) {
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}