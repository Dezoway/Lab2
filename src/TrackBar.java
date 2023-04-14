import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TrackBar extends JPanel {
    private JLabel label;
    private JSlider slider;
    private int powerSlider = 10;
    public TrackBar(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        createComponents();
    }
    public void changeCoords(int a, int b){
        label.setText(a+", "+b);
    }
    public int getPowerSlider(){
        return powerSlider;
    }
    private void createComponents(){
        label = new JLabel();
        label.setText("0, 0");
        slider = new JSlider();
        slider.setMinimum(5);
        slider.setMaximum(15);
        slider.setValue(10);
        slider.addChangeListener(e -> powerSlider = slider.getValue());
        add(label, BorderLayout.WEST);
        add(slider, BorderLayout.EAST);
    }

}
