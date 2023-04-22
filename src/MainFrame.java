import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private TrackBar bar;
    private int countHistory = 0;
    private PaintPanel paintPanel;
    private ArrayList<Brush> brushes;
    private ToolStrip toolStrip;
    private MenuToolStrip menuToolStrip;
    public MainFrame(String title){
        setTitle(title);
        menuToolStrip = new MenuToolStrip();
        toolStrip = new ToolStrip();
        bar = new TrackBar();
        paintPanel = new PaintPanel();
        brushes = new ArrayList<>();
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
    public void addBrushHistory(Brush brush){
        if(brushes.size() == 4)brushes.remove(0);
        brushes.add(brush);
        countHistory += countHistory==4?0:1;
    }
    public void undo(){
        if(countHistory<1)return;
        paintPanel.remove(0);
        countHistory--;
        paintPanel.repaint();

    }
    public void redo(){
        if(countHistory>brushes.size()-1)return;
        if(Arrays.stream(paintPanel.getComponents()).anyMatch(x->((Brush)x).equals(brushes.get(countHistory))))return;
        paintPanel.add(brushes.get(countHistory));
        paintPanel.repaint();
        countHistory += 1;
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
