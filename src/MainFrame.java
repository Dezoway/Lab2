import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {
    /*
        Класс наследующие JFrame в виде главного фрейма на котором располагаются все компоненты
     */
    private TrackBar bar;
    private PaintPanel paintPanel;
    private ArrayList<Brush> sheets;    //Список хранения слоёв для истории изменений
    private int sheetsCount = 0;    //Начальное количество слоёв
    private boolean isUndo = false; //Переменная флаг для определения продвижения по истории назад
    private ToolStrip toolStrip;
    private MenuToolStrip menuToolStrip;
    public MainFrame(String title){
        setTitle(title);
        toolStrip = new ToolStrip();
        menuToolStrip = new MenuToolStrip();
        bar = new TrackBar();
        paintPanel = new PaintPanel();
        paintPanel.setDrawing(false);
        sheets = new ArrayList<>();
        setJMenuBar(menuToolStrip.getBar());    //Установка меню-бара
        setLayout(new GridBagLayout()); //Установка табличного менеджера расположения компонентов
        /*
            Объект класса GridBagConstraints отвечает за конфигурацию расположения и размера компонента
            в табличном менеджере расположения компонентов
         */
        add(toolStrip, new GridBagConstraints(0,0,1,1,0,0,
                GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        add(new JScrollPane(paintPanel), new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0,0,0,0),0,0));
        add(bar, new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.EAST, GridBagConstraints.BOTH,
        new Insets(5,0,5,0),0,0));
        setSize(903,616);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void addSheet(Brush brush){
        /***
         * Метод добавляет слой в список хранения истории
         * В случае превышения размера истории - удаляется последний элемент из списка
         * В случае добавления слоя в виде пера после отката изменений удаляются все последующие слои
         * @see Brush
         * @see PaintPanel
         */
        if(sheets.size() == 9)sheets.remove(0);
        if(isUndo){
            sheets.removeIf(x-> !Arrays.stream(paintPanel.getComponents()).anyMatch(n->n.equals(x)));
            isUndo = false;
        }
        sheets.add(brush);
        sheetsCount=sheets.size();
    }

    public void undo(){ //Метод отката изменений
        if(sheetsCount != 0 && paintPanel.getComponentCount() != 0){
            paintPanel.remove(0);
            paintPanel.repaint();
            sheetsCount--;
            isUndo = true;
        }
        else{   //Вывод сообщения если история пуста
            JOptionPane.showMessageDialog(this,
                    "История пуста",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void redo(){ //Метод отмены отката изменений
        if(sheetsCount < sheets.size()){
            isUndo = false;
            paintPanel.add(sheets.get(sheetsCount++),0);
            paintPanel.repaint();
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "История пуста",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

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
