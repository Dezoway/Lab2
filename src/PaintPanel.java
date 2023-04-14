import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PaintPanel extends JPanel {
    private int coordX;
    private int coordY;
    boolean flag = true;
    public PaintPanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        createMouseListener();

    }
    public void createMouseListener(){;
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int power = ((MainFrame)MainFrame.getFrames()[0]).getBar().getPowerSlider();
                //PaintPanel.this.getGraphics().fillOval(e.getX(), e.getY(),power,power);
                if (flag) {
                    PaintPanel.this.getGraphics().setPaintMode();
                    System.out.println(PaintPanel.this.getGraphics().getColor().getRed());
                    PaintPanel.this.getGraphics().fillRect(e.getX(), e.getY(), power, power);
                    flag = false;
                    return;
                }
                else {
                    System.out.println("j");
                    PaintPanel.this.getGraphics().setColor(Color.RED);
                    PaintPanel.this.getGraphics().fillRect(e.getX(), e.getY(), power, power);
                    flag = true;
                    return;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                ((MainFrame) MainFrame.getFrames()[0]).getBar().changeCoords(e.getX(), e.getY());
            }
        });
    }

}
