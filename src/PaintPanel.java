import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class PaintPanel extends JPanel implements Cloneable{
    private int coordX;
    private Brush brush;
    public PaintPanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        changeBrush();
        setLayout(new OverlayLayout(this));
        createMouseListener();
    }
    private int getPower(){
        return ((MainFrame)JFrame.getFrames()[0]).getBar().getPowerSlider();
    }
    private void changeBrush(){ //Проверка выбранного стиля пера с последующим изменением
        MenuToolStrip menuToolStrip = ((MainFrame)MainFrame.getFrames()[0]).getMenuToolStrip();
        switch (menuToolStrip.getStyle()){
            case "Solid"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER),
                    menuToolStrip.getBrushColor());
            case "Dot"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,1.0f,
                    new float[]{1f,20f,1f,20f},0.0f),menuToolStrip.getBrushColor());
            case "DashDotDot"->brush = new Brush(new BasicStroke(getPower(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f,
                    new float[]{30f,30f,1f,30f,1f,30f},0.0f),menuToolStrip.getBrushColor());
        }
    }

    public void createMouseListener(){
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                brush.update(e.getPoint());
                add(brush,0);
                repaint();
                revalidate();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                ((MainFrame) MainFrame.getFrames()[0]).getBar().changeCoords(e.getX(), e.getY());
            }
        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                changeBrush();
                brush.start(e.getPoint());
            }

            @Override
            public void mouseClicked(MouseEvent e) {    // В случае если пользователь нажал двойным кликом по полю
                changeBrush();
                brush.start(new Point(e.getX(),e.getY()));
                brush.update(new Point(e.getX()+1,e.getY()+1));
                add(brush,0);
                repaint();
                revalidate();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((MainFrame)MainFrame.getFrames()[0]).addBrushHistory(brush);
            }
        });
    }

}
class Brush extends JComponent{         //Класс имитирующий поведение кисти
    private BufferedImage image;
    private final Path2D path;
    private final Stroke stroke;
    private final Color color;
    public Brush(Stroke stroke, Color color){
        this.path = new Path2D.Double();
        this.stroke = stroke;
        this.color = color;
    }
    public JComponent getComponent(){return this;}
    public void start(Point2D point){
        path.moveTo(point.getX(), point.getY());
    }
    public void update(Point2D point){
        path.lineTo(point.getX(), point.getY());
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(this.stroke);
        g2d.setColor(color);
        g2d.draw(path);
    }
}
/*
Container c = PaintPanel.this;
                BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
                c.paint(image.getGraphics());
                try {
                    ImageIO.write(image, "PNG", new File("result.png"));
                } catch (IOException exception) {
                    System.out.println("Ошибка");
                }
 */