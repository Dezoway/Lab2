import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TrackBar extends JPanel {
    /*
        Класс наследующий JPanel в виде ползунка изменения толщины пера и показании координат положения курсора на
        панели для рисования
     */
    private JLabel label;   //Объект для хранения координа положения курсора
    private JSlider slider; //Объект-ползунок
    private int powerSlider = 10;   //Начальное положение ползунка
    public TrackBar(){
        setLayout(new BorderLayout());  //Установка менеджера расположения
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));   //Установка рамки
        createComponents();
    }
    public void changeCoords(int a, int b){ //Метод изменяющий координаты при изменении положения курсора
        label.setText(a+", "+b);
    }
    public int getPowerSlider(){    //Метод получения пооложения ползунка
        return powerSlider;
    }
    private void createComponents(){
        label = new JLabel();
        label.setText("0, 0");  // Установка начальных координат положения курсора
        slider = new JSlider();
        /*
            Установка минимального-максимального значения для ползунка, установка начального значения ползунка
         */
        slider.setMinimum(5);
        slider.setMaximum(15);
        slider.setValue(10);
        slider.addChangeListener(e -> powerSlider = slider.getValue()); //Добавление прослушивателя для изменений значения ползунка
        add(label, BorderLayout.WEST);  //Добавить компоненты
        add(slider, BorderLayout.EAST);
    }

}
