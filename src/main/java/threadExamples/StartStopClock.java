package threadExamples;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class StartStopClock extends JFrame implements ActionListener
{
    private static final String START = "START";
    private static final String STOP = "STOP";

    private final JLabel clockLabel = new JLabel();
    private ClockThread clockThread = null;

    public StartStopClock() {
        setTitle("ClockThread");
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = new Font("Default", Font.BOLD + Font.ITALIC, 24);
        clockLabel.setFont(f);
        add(clockLabel);

        JButton start = new JButton(START);
        start.setActionCommand(START);
        start.addActionListener(this);
        add(start, BorderLayout.NORTH);

        JButton stop = new JButton(STOP);
        stop.setActionCommand(STOP);
        stop.addActionListener(this);
        add(stop, BorderLayout.SOUTH);

        setBounds(400, 300, 300, 200);

    }

    public void setTime() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                clockLabel.setText(df.format(Calendar.getInstance().getTime()));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(START.equals(ae.getActionCommand())) {
            if(clockThread == null) {
                clockThread = new ClockThread(this);
                clockThread.start();
            }
        }
        if(STOP.equals(ae.getActionCommand())) {
            if(clockThread != null) {
                clockThread.stopClock();
                clockThread = null;
            }
        }
    }

    public static void main(String[] args) {
        StartStopClock cl = new StartStopClock();
        cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cl.setVisible(true);
    }
}


class ClockThread extends Thread {

    private StartStopClock clock;
    private volatile boolean isRunning = true;

    public ClockThread(StartStopClock clock) {
        this.clock = clock;
    }

    public void stopClock() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            clock.setTime();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}