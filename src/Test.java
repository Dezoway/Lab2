import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Test {
 /*   public static void main(String[] args) {
        var frame = Factory.newFrame();
        var panel = new JPanel();
        /*var panels = Stream.generate(JPanel::new)
                .limit(9)
                .peek(p -> p.setLayout(new GridLayout(1, 1)))
                .peek(frame::add)
                .toArray(JPanel[]::new);


        var sheet = Factory.newSheet();
        var controller = Factory.addControllerSheet(sheet);

        controller.setFactoryDrawable(() -> new Brush(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 20.0f,
                new float[]{8f, 2f, 8f}, 0.0f), Color.BLACK));
        panel.setBorder(new LineBorder(Color.BLACK));
        panel.setLayout(new GridLayout(1,1));
        panel.add(sheet);
        frame.add(panel);
        //panels[4].setBorder(new LineBorder(Color.BLACK));
        //panels[4].add(sheet);
        frame.setLayout(new GridLayout(3, 3));
        frame.setVisible(true);
    }
}

class Factory {
    public static DefaultSheetController addControllerSheet(Sheet sheet) {
        var controller = new DefaultSheetController(() -> new Brush(new BasicStroke(20), Color.BLACK));
        sheet.addMouseMotionListener(controller);
        sheet.addMouseListener(controller);
        return controller;
    }

    public static JFrame newFrame() {
        JFrame frame = new JFrame("Test");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    public static Sheet newSheet() {
        return new Sheet();
    }

    public static Graphics2D factoryGraphics2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        //g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2d;
    }
}

class DefaultSheetController extends MouseAdapter {
    private Drawable drawable;
    private Supplier<Drawable> factoryDrawable;

    DefaultSheetController(Supplier<Drawable> factoryDrawable) {
        this.factoryDrawable = factoryDrawable;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() instanceof Sheet sheet) {
            drawable = factoryDrawable.get();
            if (drawable.start(e.getPoint())) {
                sheet.add(drawable.getComponentDraw());
                sheet.revalidate();
                sheet.repaint();
            } else {
                drawable = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drawable != null && e.getComponent() instanceof Sheet sheet) {
            drawable.end(e.getPoint());
            sheet.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (drawable != null && e.getComponent() instanceof Sheet sheet) {
            drawable.update(e.getPoint());
            sheet.repaint();
        }
    }

    public void setFactoryDrawable(Supplier<Drawable> factoryDrawable) {
        this.factoryDrawable = factoryDrawable;
    }
}

interface Drawable {
    boolean start(Point2D p);

    boolean update(Point2D p);

    boolean end(Point2D p);

    JComponent getComponentDraw();
}

class Brush2 extends JComponent implements Drawable {
    private final Path2D path;
    private final Stroke stroke;
    private final Color color;
    private boolean isEnd;

    public Brush2(Stroke stroke, Color color) {
        this.path = new Path2D.Double();
        this.stroke = stroke;
        this.color = color;
        isEnd = false;
    }

    @Override
    public boolean start(Point2D p) {
        if (isEnd) return false;
        path.moveTo(p.getX(), p.getY());
        return true;
    }

    @Override
    public boolean update(Point2D p) {
        if (isEnd) return false;
        path.lineTo(p.getX(), p.getY());
        return false;
    }

    @Override
    public boolean end(Point2D p) {
        isEnd = true;
        return true;
    }

    @Override
    public JComponent getComponentDraw() {
        return this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var g2D = Factory.factoryGraphics2D(g);
        //System.out.println(((BasicStroke)((Graphics2D)getGraphics()).getStroke()).);

        g2D.setColor(color);
        g2D.setStroke(stroke);
        g2D.draw(path);
    }
}

class Sheet extends JPanel {
    Sheet() {
        setLayout(new OverlayLayout(this));
    }
}

  */

        }