import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        setTitle(title);
        setVisible(true);
        setJMenuBar(new MenuToolStrip().getBar());

    }
}
