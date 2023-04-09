import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class MenuToolStrip {
    private String style="Solid";   //Стиль пера, по умолчанию Solid
    private final JMenuBar bar;
    public MenuToolStrip(){
         bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        createFileSubMenu(file);
        createEditSubMenu(edit);
        createHelpSubMenu(help);
        bar.add(file);
        bar.add(edit);
        bar.add(help);
    }
    public JMenuBar getBar(){
        return bar;
    }
    private void createFileSubMenu(JMenu file){
        JMenuItem newF = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        newF.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke("F3"));
        save.setAccelerator(KeyStroke.getKeyStroke("F2"));
        exit.setAccelerator(KeyStroke.getKeyStroke('X',KeyEvent.ALT_DOWN_MASK));
        file.add(newF);
        file.add(open);
        file.add(save);
        file.add(exit);
    }
    private void createEditSubMenu(JMenu edit){
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem reno = new JMenuItem("Reno");
        //JMenuItem pen = new JMenuItem("Pen");
        JMenu pen = new JMenu("Pen");
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
        reno.setAccelerator(KeyStroke.getKeyStroke('Z',KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK));
        setPenSubMenu(pen);
        edit.add(undo);
        edit.add(reno);
        edit.add(pen);
    }
    private void setPenSubMenu(JMenu pen){
        JMenu style = new JMenu("Style");
        JCheckBoxMenuItem solid = new JCheckBoxMenuItem("Solid",true);
        JCheckBoxMenuItem dot = new JCheckBoxMenuItem("Dot");
        JCheckBoxMenuItem ddd = new JCheckBoxMenuItem("DashDotDot");
        solid.addActionListener(e ->{
            MenuToolStrip.this.style ="Solid";
            dot.setState(false);
            ddd.setState(false);
        });
        dot.addActionListener(e->{
            MenuToolStrip.this.style ="Dot";
            solid.setState(false);
            ddd.setState(false);
        });
        ddd.addActionListener(e->{
            MenuToolStrip.this.style ="DashDotDot";
            solid.setState(false);
            dot.setState(false);
        });
        style.add(solid);
        style.add(dot);
        style.add(ddd);
        JMenuItem color = new JMenuItem("Color");
        pen.add(style);
        pen.add(color);

    }
    private void createHelpSubMenu(JMenu help){
        JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke("F1"));
        help.add(about);
    }
}
