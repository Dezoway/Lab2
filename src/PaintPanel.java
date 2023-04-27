import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintPanel extends JPanel{
    /*
        Класс наследует JPanel в виде панели для рисования
     */
    private boolean isDrawing = false;  //Переменная-флаг для определения изменений на панели
    private Brush brush;    //Объект для хранения текущей кисти
    public PaintPanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));   //Создание рамки
        changeBrush();  //Вызов метода изменения пера
        setLayout(new OverlayLayout(this)); //Установка менеджера расположения компонентов
        createMouseListener();  // Вызов метода для создания прослушивателей действий мышью
    }
    public void setDrawing(boolean drawing){    //Установка фона панели при изменении флага isDrawing
        isDrawing = drawing;
        setBackground(isDrawing?Color.WHITE:null);
    }
    public boolean isDrawing(){
        return isDrawing;
    }
    private int getPower(){ //Метод получения значения трек-бара(ползунка)
        return ((MainFrame)JFrame.getFrames()[0]).getBar().getPowerSlider();
    }
    private void changeBrush(){ //Проверка выбранного стиля пера с последующим изменением
        MenuToolStrip menuToolStrip = ((MainFrame)MainFrame.getFrames()[0]).getMenuToolStrip();
        switch (menuToolStrip.getStyle()){  //Оператор выбора стиля пера
            case "Solid"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER),
                    menuToolStrip.getBrushColor());
            case "Dot"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,1.0f,
                    new float[]{1f,20f,1f,20f},0.0f),menuToolStrip.getBrushColor());
            case "DashDotDot"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f,
                    new float[]{30f,30f,1f,30f,1f,30f},0.0f),menuToolStrip.getBrushColor());
        }
    }

    public void createMouseListener(){  //Метод для добавления прослушивателя действий мышью
        addMouseMotionListener(new MouseInputAdapter() {    //Добавление прослушивателя движений мышью
            @Override
            public void mouseDragged(MouseEvent e) {    //Метод отвечающий за перетаскиванию мышью
                if(!isDrawing){ //Если панель не создана
                    JOptionPane.showMessageDialog(((MainFrame)MainFrame.getFrames()[0]),
                            "Сначала создайте новый файл",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                brush.update(e.getPoint()); //Обновить координаты текущего пера
                add(brush,0);   //Добавить след пера на панель
                repaint();     //Перерисовать панель
                revalidate();   //Пересчитать компоненты добавленные на панель для рисования
            }
            @Override
            public void mouseMoved(MouseEvent e) {  //Метод отвечающий за движение мышью
                ((MainFrame) MainFrame.getFrames()[0]).getBar().changeCoords(e.getX(), e.getY());   //Обновление координат курсора
            }
        });
        addMouseListener(new MouseAdapter() {   //Добавление прослушивателя действий мышью
            @Override
            public void mousePressed(MouseEvent e) {    //Метод отвечающий за зажатую мышь на месте
                if(!isDrawing){
                    JOptionPane.showMessageDialog(((MainFrame)MainFrame.getFrames()[0]),
                            "Сначала создайте новый файл",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                changeBrush();   //Метод изменяющий перо
                brush.setEraser(e.getButton()==3?true:false);   //Включить ластик если нажата правая кнопка мыши
                brush.start(e.getPoint());  //Задать начальные координаты для последующего рисования
            }


            @Override
            public void mouseClicked(MouseEvent e) {    // Метод отвечающий за одиночное нажатие
                if(!isDrawing){
                    JOptionPane.showMessageDialog(((MainFrame)MainFrame.getFrames()[0]),
                            "Сначала создайте новый файл",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                changeBrush();
                brush.setEraser(e.getButton()==3?true:false);
                brush.start(new Point(e.getX(),e.getY()));  //Задать начальные координаты
                brush.update(new Point(e.getX()+1,e.getY()+1)); //Обновить координаты со смищением на 1 пиксель в обеих ориентациях
                add(brush,0);   //Добавление "кляксы" на панель
                repaint();  //Перерисовать панель
                revalidate();//Пересчитать добавленные компоненты
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((MainFrame)MainFrame.getFrames()[0]).addSheet(brush);
            }
        });
    }
    public void clearArea(){    //Метод реализующий очистку панели для рисования
        this.removeAll();   //Удалить все компоненты
        this.repaint(); //Перерисовать панель
    }
    public void savePanelAtFile(File pathName){ //Сохранение текущего рисунка в файл
        Container c = this;  //Создание объекта класса контейнер,путём восходящего преобразования(Up Casting) объекта панели
        BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);   //Создание объекта для хранения буфферизированного изображения из панели
        c.paint(image.getGraphics());   //Передача текщуего изображения в объект image
        try {
            /**
             * Запись изображения в файл с обработчиком исключений
             * @see IOException
             * @see ImageIO
             */
            ImageIO.write(image, "PNG", new File(pathName+".png"));
        } catch (IOException exception) {
            System.out.println("Ошибка");
        }
    }
    public void openFileAtPanel(File pathName){ //Метод для открытия файла
        try {
            JLabel label = new JLabel(new ImageIcon(ImageIO.read(pathName)));//Создание слоя с готовым изображением
            add(label); //Добавить слой на панель
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
class Brush extends JComponent{         //Класс имитирующий поведение пера
    private  Path2D path;   //Объект класса Path2D отвечающие за построение геометрического пути
    private  Stroke stroke; //Объект класса Stroke отвечающий за контур линии
    private boolean isEraser;   //Переменная-флаг отвечающая за включение/выключение ластика
    private  Color brushColor;  //Переменная отвечающая за цвет пера
    public Brush(Stroke stroke, Color color){
        this.path = new Path2D.Double();
        this.stroke = stroke;
        this.brushColor = color;
    }

    public JComponent getComponent(){return this;}
    public void start(Point2D point){
        path.moveTo(point.getX(), point.getY());
    }   //Установка начальных координат
    public void update(Point2D point){path.lineTo(point.getX(), point.getY());} // Прорисовка линии
    public void setEraser(boolean flag){        //Установка пера в качестве стирательной резинки
        this.isEraser = flag;
    }
    @Override
    public void paint(Graphics g){  //Метод отрисовки компонента
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   //Установить более сглаженные контуры линии
        g2d.setStroke(this.stroke);//Установка контура линии
        g2d.setColor(isEraser?Color.WHITE:brushColor);  //Установка цвета пера в тернарном операторе
        g2d.draw(path); //Отрисовать линию
    }
}