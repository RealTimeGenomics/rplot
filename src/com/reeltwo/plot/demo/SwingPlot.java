package com.reeltwo.plot.demo;

import com.reeltwo.plot.Box2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.Circle2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.FillablePlot2D;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.GraphLine;
import com.reeltwo.plot.Point2D;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.ScatterPoint2D;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.TextPoint2D;
import com.reeltwo.plot.patterns.BW8x8PatternGroup;
import com.reeltwo.plot.ui.ZoomPlotDialog;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

/**
 * Starts a new Swing window for displaying Graph2Ds in. The window has
 * zooming and picture in picture functionality enabled.
 *
 * @author <a href=mailto:rlittin@secondreel.com>Richard Littin</a>
 * @version $Revision$
 */

public class SwingPlot {
  /** the dialog for this window */
  private ZoomPlotDialog mDialog;


  /** Creates a new swing plot. */
  public SwingPlot() {
    mDialog = new ZoomPlotDialog();
    mDialog.validate();

    mDialog.setPatterns(new BW8x8PatternGroup().getPatterns());

    mDialog.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(1);
        }
      });

    JPopupMenu popup = mDialog.getPopupMenu();

    popup.addSeparator();
    popup.add(new AbstractAction("Exit") {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });

    mDialog.setVisible(true);
  }


  /**
   * Creates a new swing plot with the given window title.
   *
   * @param title A title for the window.
   */
  public SwingPlot(String title) {
    this();
    setTitle(title);
  }


  /**
   * Creates a new swing plot rendering the given graph.
   *
   * @param graph A graph to render.
   */
  public SwingPlot(Graph2D graph) {
    this();
    setGraph(graph);
  }


  /**
   * Creates a new swing plot with the given window title and renders
   * the given graph.
   *
   * @param title A title for the window.
   * @param graph A graph to render.
   */
  public SwingPlot(String title, Graph2D graph) {
    this();
    setTitle(title);
    setGraph(graph);
  }



  /**
   * Renders the given graph in the swing window.
   *
   * @param graph A graph to render.
   */
  public void setGraph(Graph2D graph) {
    mDialog.setGraph(graph);
  }


  /**
   * Sets the windows title.
   *
   * @param title A title for the window.
   */
  public void setTitle(String title) {
    mDialog.setTitle(title);
  }


  /**
   * Main loop for testing purposes. Renders two plots.
   *
   * @param args not used.
   * @exception Exception TODO Description.
   */
  public static void main(String[] args) throws Exception {
    Graph2D graph = genTest();
    SwingPlot sp = new SwingPlot("A Plot");
    sp.setGraph(graph);
  }


  /**
   * Renders a 'test' plot showing what lines styles are available.
   *
   * @return a test Graph2D
   */
  public static Graph2D genTest() {

    Graph2D graph = new Graph2D();
    graph.setTitle("Title");
    graph.setXLabel(0, "X label");
    graph.setXLabel(1, "X1 label");
    graph.setYLabel(0, "Y label");
    graph.setYLabel(1, "Y2 label");
    graph.setKeyTitle("Key title");
    graph.setGrid(true);
    graph.setYRange(0, 0.0f, 1.0f);
    graph.setXRange(0, 0.0f, 1.0f);

    Point2D[] xys1 = new Point2D[11];
    Point2D[] xys2 = new Point2D[11];
    Point2D[] xys3 = new Point2D[11];
    Point2D[] xys4 = new Point2D[11];
    for (int i = 0; i <= 10; i++) {
      xys1[i] = new Point2D(i / 10.0f, 1.0f - i * i / 153.0f);
      xys2[i] = new Point2D(i / 10.0f, 0.13f + i * i / 153.0f);
      xys3[i] = new Point2D(i / 10.0f, 0.1f + i * i / 90.0f);
      xys4[i] = new Point2D(i / 10.0f, 0.95f - i * i / 153.0f);
    }

    PointPlot2D lplot = new PointPlot2D();
    Point2D[] fpts = new Point2D[]{new Point2D(0.0f, 0.2f),
        new Point2D(0.4f, 0.65f),
        new Point2D(0.7f, 0.85f),
        new Point2D(1.0f, 0.0f)};
    lplot.setData(fpts);
    lplot.setTitle("filled lines");
    lplot.setFill(FillablePlot2D.COLOR_FILL);
    lplot.setBorder(true);

    graph.addPlot(lplot);

    Box2D[] boxes1 = new Box2D[10];
    Box2D[] boxes2 = new Box2D[10];
    for (int i = 0; i < 10; i++) {
      boxes1[i] = new Box2D(i / 10.0f, (10 - i) / 10.0f, i / 10.0f + 0.05f, (10 - i) / 20.0f);
      boxes2[i] = new Box2D(i / 10.0f - 0.025f, (i + 1) / 10.0f, i / 10.0f + 0.025f, i / 20.0f);
    }

    BoxPlot2D bplot = new BoxPlot2D();
    bplot.setData(boxes2);
    bplot.setTitle("filled box");
    bplot.setFill(FillablePlot2D.PATTERN_FILL);
    bplot.setBorder(true);

    graph.addPlot(bplot);

    bplot = new BoxPlot2D();
    bplot.setData(boxes1);
    bplot.setTitle("box");
    bplot.setFill(FillablePlot2D.NO_FILL);

    graph.addPlot(bplot);

    TextPlot2D tplot = new TextPlot2D();
    TextPoint2D[] tps = new TextPoint2D[10];
    CirclePlot2D cplot = new CirclePlot2D();
    Circle2D[] cps = new Circle2D[10];
    ScatterPlot2D splot = new ScatterPlot2D();
    ScatterPoint2D[] sps = new ScatterPoint2D[10];
    for (int i = 0; i < 10; i++) {
      tps[i] = new TextPoint2D(i / 10.0f, (i + 1) / 13.3f, "T" + i);
      cps[i] = new Circle2D(i / 10.0f, (i + 1) / 13.3f, i + 1);
      sps[i] = new ScatterPoint2D(i / 10.0f, (i + 1) / 13.3f, i * 5 + 1);
    }
    tplot.setData(tps);
    tplot.setTitle("text");
    tplot.setInvert(true);
    //tplot.setUseFGColor(false);
    cplot.setData(cps);
    cplot.setTitle("circles");

    splot.setData(sps);
    splot.setScatterFactor(10);
    splot.setTitle("scatter");

    graph.addPlot(cplot);
    graph.addPlot(splot);
    graph.addPlot(tplot);

    lplot = new PointPlot2D();
    lplot.setData(xys4);
    lplot.setPoints(false);
    lplot.setTitle("dots");

    graph.addPlot(lplot);

    lplot = new PointPlot2D();
    lplot.setData(xys1);
    lplot.setTitle("points");

    graph.addPlot(lplot);
    lplot = new PointPlot2D(1, 1);
    lplot.setData(xys2);
    lplot.setTitle("lines");
    lplot.setLines(true);
    lplot.setPoints(false);

    graph.addPlot(lplot);
    graph.setYLo(1, -0.15f);

    lplot = new PointPlot2D();
    lplot.setData(xys3);
    lplot.setTitle("lines points");
    lplot.setLines(true);

    graph.addPlot(lplot);

    Point2D [] cps2 = new Point2D[] {
      new Point2D(0, 0),
      new Point2D(0, 0),
      new Point2D(0.25f, 0.3f),
      new Point2D(0.5f, 0.9f),
      new Point2D(0.75f, 0.6f),
      new Point2D(1, 0),
      new Point2D(1, 0),
    };
    CurvePlot2D cplot2 = new CurvePlot2D();
    cplot2.setData(cps2);
    cplot2.setTitle("curve");
    //cplot2.setFill(true);
    cplot2.setType(CurvePlot2D.CUBIC_BEZIER);
    //cplot2.setType(CurvePlot2D.BEZIER);
    cplot2.setLineWidth(5);

    graph.addPlot(cplot2);

    //graph.setVerticalLine(0.43f);
    GraphLine vline = new GraphLine(0.43f, GraphLine.VERTICAL);
    vline.setType(GraphLine.DOTS);
    graph.addPlot(vline);

    graph.setXTic(0, 0.15f);
    graph.setXTicLabels(0, new String[]{"The", "quick", "brown", "fox", "jumped"});

    graph.setKeyVerticalPosition(Graph2D.BELOW);
    //graph.setColoredKey(false);

    return graph;
  }

  public static Graph2D genTextureTest() {
    Graph2D graph = new Graph2D();

    ArrayList text = new ArrayList();
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < 9; x++) {
        final int index = y * 9 + x;
        final int y2 = 5 - y;
        BoxPlot2D plot = new BoxPlot2D();
        plot.setData(new Box2D[] {new Box2D(x + 0.05f, y2 + 0.05f, x + 0.95f, y2 + 0.95f)});
        plot.setFill(FillablePlot2D.PATTERN_FILL);
        plot.setColor(index);
        plot.setBorder(true);
        
        graph.addPlot(plot);

        text.add(new TextPoint2D(x + 0.5f, y2 + 0.5f, "" + index));
      }
    }
    TextPlot2D plot = new TextPlot2D();
    plot.setData((TextPoint2D []) text.toArray(new TextPoint2D[text.size()]));
    plot.setInvert(true);

    graph.addPlot(plot);

    return graph;
  }
}
