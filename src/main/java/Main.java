import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int FRAME_WIDTH = 1150;
    private static final int FRAME_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new ChartFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - FRAME_WIDTH) / 2;
        int locationY = (screenSize.height - FRAME_HEIGHT) / 2;
        frame.setBounds(locationX, locationY, FRAME_WIDTH, FRAME_HEIGHT);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
