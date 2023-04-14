import javax.swing.*;
import java.awt.*;

public class ToolStrip extends JPanel {
    JButton[] buttons = new JButton[5];
    public ToolStrip(){
        setLayout(new GridLayout(5,1));
        createButtons();

    }
    private void createButtons(){
        for(int x =0; x != buttons.length; x ++){
            buttons[x] = new JButton(new ImageIcon(new ImageIcon(getPathImage(x)).getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            add(buttons[x]);
            buttons[x].setContentAreaFilled(false);
            buttons[x].setBorder(null);
        }
    }
    private String getPathImage(int index){
        return switch (index) {
            case 0 -> "src/resources/PIC.PNG";
            case 1 -> "src/resources/save.jpg";
            case 2 -> "src/resources/folder.png";
            case 3 -> "src/resources/Palitra.jpg";
            case 4 -> "src/resources/exit.png";
            default -> null;
        };
    }
}
