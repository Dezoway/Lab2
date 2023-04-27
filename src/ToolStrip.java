import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.stream.Stream;

public class ToolStrip extends JPanel {
    /*
        Класс реализует наследует JPanel в виде кнопок мению на главном фрейме
     */
    private JButton[] buttons = new JButton[5];
    private ToolsTripButtonsListener listener;
    public ToolStrip(){
        setLayout(new GridLayout(5,1));
        listener = new ToolsTripButtonsListener((MainFrame)MainFrame.getFrames()[0]);   //Создание прослушивателя кнопок
        createButtons();

    }

    private void createButtons(){   // Метод создания кнопок
        for(int x =0; x != buttons.length; x ++){
            buttons[x] = new JButton(new ImageIcon(new ImageIcon(getPathImage(x)).getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            buttons[x].addActionListener(listener);
            buttons[x].setName(String.valueOf(x));
            buttons[x].setToolTipText(getDescriptionButton(x));
            add(buttons[x]);
            buttons[x].setContentAreaFilled(false);
            buttons[x].setBorder(null);
        }
    }
    public void ButtonClick(int index){ //Метод программной имитации нажатия кнопки
        buttons[index].doClick();
    }
    private String getPathImage(int index){ //Метод получения адреса изображения
        return switch (index) {
            case 0 -> "src/resources/PIC.PNG";
            case 1 -> "src/resources/save.jpg";
            case 2 -> "src/resources/folder.png";
            case 3 -> "src/resources/Palitra.jpg";
            case 4 -> "src/resources/exit.png";
            default -> null;
        };
    }
    private String getDescriptionButton(int index){ //Метод получения описания для кнопок
        return switch(index){
            case 0 ->"Создать новый файл";
            case 1 ->"Сохранить файл";
            case 2 ->"Открыть файл";
            case 3 -> "Изменить цвет кисти";
            case 4 ->"Очистить холст";
            default -> null;
        };
    }
    class ToolsTripButtonsListener implements ActionListener {
        private MainFrame mainFrame;

        public ToolsTripButtonsListener(MainFrame frame) {
            mainFrame = frame;  //Сохранение ссылки на объект главного фрейма
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(buttons[0])) {     //Обработка нажатия на кнопку создания нового слоя
                if (mainFrame.getPaintPanel().isDrawing()) {
                    int choice = JOptionPane.showConfirmDialog(mainFrame, "Сохранить текущее изображение перед созданием нового рисунка?");
                    if (choice == 0) {
                        buttons[1].doClick();
                        mainFrame.getPaintPanel().removeAll();
                    } else if (choice == 1) {
                        mainFrame.getPaintPanel().clearArea();
                    } else return;
                } else {
                    mainFrame.getPaintPanel().setDrawing(true);
                }
            } else if (e.getSource().equals(buttons[1])) {  //Обработка нажатия на создание нового файла
                if (!mainFrame.getPaintPanel().isDrawing()) {
                    JOptionPane.showMessageDialog(mainFrame, "Сначала создайте новый файл",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainFrame.getPaintPanel().savePanelAtFile(FileOperations.showWindowSaveFile(mainFrame));
            } else if (e.getSource().equals(buttons[2])) {  //Обработка нажатия на кнопку открытия файла
                if (!mainFrame.getPaintPanel().isDrawing()) {
                    JOptionPane.showMessageDialog(mainFrame, "Сначала создайте новый файл",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainFrame.getPaintPanel().openFileAtPanel(FileOperations.openFile(mainFrame));
            }else if (e.getSource().equals(buttons[3])){    //Обработка нажатия на кнопку выбора цвета кисти
                mainFrame.getMenuToolStrip().setBrushColor(JColorChooser.showDialog(mainFrame,"Выбор цвета",new Color(0,0,0)));
            }
            else if (e.getSource().equals(buttons[4])){//Обработка нажатия на кнопку выход из программы
                if(mainFrame.getPaintPanel().isDrawing()){
                    int choice = JOptionPane.showConfirmDialog(mainFrame,"Сохранить текущее изображение перед закрытием программы?",
                            "Закрытие программы",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(choice == 0)buttons[1].doClick();
                    else if(choice == 1){
                        System.exit(0);
                    }else return;
                }
                else{
                    int choice = JOptionPane.showConfirmDialog(mainFrame,"Выйти из программы?","Выход",
                            JOptionPane.YES_NO_OPTION);
                    if(choice ==0)System.exit(0);
                    return;
                }
            }



        }
    }
}
class FileOperations{
    /*
    Класс представляет работу всплывающего диалогового окна для сохранения/открытия файла с последующим возвращением
    объекта класса File для дальнейшей работы с данным объектом(чтение/запись)
     */
    public static File showWindowSaveFile(MainFrame frame){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("PNG","png"));
        jFileChooser.showSaveDialog(frame);
        return jFileChooser.getSelectedFile();
    }
    public static File openFile(MainFrame frame){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("PNG","png"));
        jFileChooser.showOpenDialog(frame);
        return jFileChooser.getSelectedFile();
    }

}
