import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrame extends JFrame {
    private TrackBar bar;
    private PaintPanel paintPanel;
    private ToolStrip toolStrip;
    private MenuToolStrip menuToolStrip;
    public MainFrame(String title){
        setTitle(title);
        menuToolStrip = new MenuToolStrip();
        toolStrip = new ToolStrip();
        paintPanel = new PaintPanel();
        bar = new TrackBar();
        setJMenuBar(menuToolStrip.getBar());
        setLayout(new GridBagLayout());
        add(toolStrip, new GridBagConstraints(0,0,1,1,0,0,
                GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        add(paintPanel, new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0,0,0,0),0,0));
        add(bar, new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.EAST, GridBagConstraints.BOTH,
        new Insets(5,0,5,0),0,0));
        setSize(903,616);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public TrackBar getBar() {
        return bar;
    }
    public PaintPanel getPaintPanel(){
        return paintPanel;
    }
    public ToolStrip getToolStrip(){
        return toolStrip;
    }
    public MenuToolStrip getMenuToolStrip(){
        return menuToolStrip;
    }
}
