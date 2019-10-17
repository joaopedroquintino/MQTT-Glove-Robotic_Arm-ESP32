/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaoq
 */
public class CameraHandler implements Runnable {

    private final OpenCVFrameGrabber grabber;
    private final CamView mPanel;

    public CameraHandler(OpenCVFrameGrabber grabber, CamView mPanel) {
        this.grabber = grabber;
        this.mPanel = mPanel;
    }

    @Override
    public void run() {
        try {
            IplImage iPimage = grabber.grab();

            while (iPimage != null) {
                iPimage = grabber.grab();
                BufferedImage convertedImage = IplImage2BufferedImage(iPimage);
                mPanel.refresh(convertedImage);
            }
        } catch (Exception ex) {
            Logger.getLogger(CameraHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage IplImage2BufferedImage(IplImage img) {
        BufferedImage convertedImage = img.getBufferedImage();

        return convertedImage;
    }
}
