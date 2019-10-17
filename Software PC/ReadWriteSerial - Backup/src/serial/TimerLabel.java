package serial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JLabel;


public class TimerLabel implements ActionListener {
    
    private Timer timer;
    private final JLabel label;
    private final int delay;

    public TimerLabel(final int delay, final JLabel label) {
        this.delay = delay;
        this.label = label;
    }

    public void init_timer() {
        timer = new Timer(delay, this);
        timer.start();
    }

 
    public void actionPerformed(final ActionEvent e) {
        // faça aqui sua consulta
        label.setText("" + Math.random());
        label.updateUI();
    }

}