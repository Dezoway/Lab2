import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.security.Key;

public class MenuToolStrip {
    /*
        Класс осщуествляет поведение меню-бара расположенного в верхней части фрейма
     */
    private String style="Solid";   //Стиль пера, по умолчанию Solid
    private Color brushColor;   //Цвет кисти
    private final JMenuBar bar;
    private ToolStrip toolStrip;
    public MenuToolStrip(){
         bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        brushColor = new Color(0,0,0);  //Установить начальный цвет пера - чёрный
        createFileSubMenu(file);
        createEditSubMenu(edit);
        createHelpSubMenu(help);
        toolStrip = ((MainFrame)MainFrame.getFrames()[0]).getToolStrip();
        bar.add(file);
        bar.add(edit);
        bar.add(help);
    }
    public JMenuBar getBar(){return bar;}
    public String getStyle(){return style;} //Метод получения выбранного стиля пера
    public Color getBrushColor(){return brushColor;}    //Метод получения выбранного цвета пера
    private void createFileSubMenu(JMenu file){ //Метод создания компонентов подменю
        JMenuItem newF = new JMenuItem("New");
        newF.setIcon(new ImageIcon(new ImageIcon("src/resources/NoteBook.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        newF.addActionListener(e -> {
            toolStrip.ButtonClick(0);   //Прослушиватель активирущий имитацию нажатия кнопки на панели инструментов
        });
        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new ImageIcon(new ImageIcon("src/resources/folder.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        open.addActionListener(e->{
            toolStrip.ButtonClick(2);
        });
        JMenuItem save = new JMenuItem("Save");
        save.setIcon(new ImageIcon(new ImageIcon("src/resources/save.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        open.addActionListener(e->{
            toolStrip.ButtonClick(2);
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.setIcon(new ImageIcon(new ImageIcon("src/resources/exit.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        newF.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke("F3"));  //Добавление горячих клавиш
        save.setAccelerator(KeyStroke.getKeyStroke("F2"));
        exit.setAccelerator(KeyStroke.getKeyStroke('X',KeyEvent.ALT_DOWN_MASK));
        file.add(newF);
        file.add(open);
        file.add(save);
        file.add(exit);
    }
    private void createEditSubMenu(JMenu edit){     //Метод реализующий историю изменений панели рисования с последующей делегацией в главный фрейм
        JMenuItem undo = new JMenuItem("Undo");
        undo.setIcon(new ImageIcon(new ImageIcon("src/resources/Undo.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        undo.addActionListener(new ActionListener() {   //Добавление прослушивателя к кнопкам
            @Override
            public void actionPerformed(ActionEvent e) {
                ((MainFrame)MainFrame.getFrames()[0]).undo();
            }
        });
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((MainFrame)MainFrame.getFrames()[0]).redo();
            }
        });
        /*
        Добавление иконки осуществляется загрузкой изображения по адресу с последующим изменением размера изображения
         */
        redo.setIcon(new ImageIcon(new ImageIcon("src/resources/Redo.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        JMenu pen = new JMenu("Pen");
        pen.setIcon(new ImageIcon(new ImageIcon("src/resources/Pen.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke('Z',KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK));
        setPenSubMenu(pen);
        edit.add(undo);
        edit.add(redo);
        edit.add(pen);
    }
    private void setPenSubMenu(JMenu pen){  //Метод создания подменю выбора стиля пера
        JMenu style = new JMenu("Style");
        JCheckBoxMenuItem solid = new JCheckBoxMenuItem("Solid",true);  //Установить выбранный стиль по умолчнию
        JCheckBoxMenuItem dot = new JCheckBoxMenuItem("Dot");
        JCheckBoxMenuItem ddd = new JCheckBoxMenuItem("DashDotDot");
        solid.addActionListener(e ->{   //Прослушиватель к каждому компоненту для обновления выбора пользователем
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
        color.setIcon(new ImageIcon(new ImageIcon("src/resources/Palitra.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        color.addActionListener(e->{    //При нажатии подменю выбора цвета - осуществить имитацию нажатия на кнопку
            toolStrip.ButtonClick(3);
        });
        pen.add(style);
        pen.add(color);

    }
    public void setBrushColor(Color color){ //Метод для установки цвета пера из других классов
     brushColor = color;
    }
    private void createHelpSubMenu(JMenu help){ //Метод вывода информации о программе
        JMenuItem about = new JMenuItem("About");
        about.setIcon(new ImageIcon(new ImageIcon("src/resources/About.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT)));
        about.setAccelerator(KeyStroke.getKeyStroke("F1"));
        about.addActionListener(e->{
            JOptionPane.showConfirmDialog((MainFrame)MainFrame.getFrames()[0],
                    "PaintLite v.1.0","О программе",
                    JOptionPane.CLOSED_OPTION);
        });
        help.add(about);
    }
}
