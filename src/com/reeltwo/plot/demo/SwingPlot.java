package com.reeltwo.plot.demo;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.reeltwo.plot.Axis2D;
import com.reeltwo.plot.AxisSide;
import com.reeltwo.plot.Box2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.Circle2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.FillablePlot2D.FillStyle;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.GraphLine;
import com.reeltwo.plot.GraphLine.LineOrientation;
import com.reeltwo.plot.GraphLine.LineStyle;
import com.reeltwo.plot.KeyPosition;
import com.reeltwo.plot.Point2D;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.ScatterPoint2D;
import com.reeltwo.plot.StringFormatter;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.TextPoint2D;
import com.reeltwo.plot.patterns.BW8x8PatternGroup;
import com.reeltwo.plot.ui.ZoomPlotDialog;

/**
 * Starts a new Swing window for displaying Graph2Ds in. The window has
 * zooming and picture in picture functionality enabled.
 *
 * @author Richard Littin
 */

public class SwingPlot {
  /** the dialog for this window */
  private final ZoomPlotDialog mDialog;


  /** Creates a new swing plot. */
  public SwingPlot() {
    mDialog = new ZoomPlotDialog();
    mDialog.validate();

    mDialog.setPatterns(new BW8x8PatternGroup().getPatterns());

    mDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(1);
      }
    });

    final JPopupMenu popup = mDialog.getPopupMenu();

    popup.addSeparator();
    popup.add(new AbstractAction("Exit") {
      private static final long serialVersionUID = 1926431490479372450L;

      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    mDialog.setVisible(true);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        mDialog.setVisible(true);
      }
    });
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
    final Graph2D graph;
    if (args.length != 0) {
      graph = genTextureTest();
    } else {
      graph = genTest();
    }
    final SwingPlot sp = new SwingPlot("A Plot");
    sp.setGraph(graph);
  }


  /**
   * Renders a 'test' plot showing what lines styles are available.
   *
   * @return a test Graph2D
   */
  public static Graph2D genTest() {

    final Graph2D graph = new Graph2D();
    graph.setTitle("Title");
    graph.setLabel(Axis2D.X, AxisSide.ONE, "X label");
    graph.setLabel(Axis2D.X, AxisSide.TWO, "X1 label");
    graph.setLabel(Axis2D.Y, AxisSide.ONE, "Y label");
    graph.setLabel(Axis2D.Y, AxisSide.TWO, "Y2 label");
    graph.setKeyTitle("Key title");
    graph.setGrid(true);
    graph.setRange(Axis2D.Y, AxisSide.ONE, 0.0f, 1.0f);
    graph.setRange(Axis2D.X, AxisSide.ONE, 0.0f, 10.0f);

    final Point2D[] xys1 = new Point2D[11];
    final Point2D[] xys2 = new Point2D[11];
    final Point2D[] xys3 = new Point2D[11];
    final Point2D[] xys4 = new Point2D[11];
    for (int i = 0; i <= 10; i++) {
      xys1[i] = new Point2D(i, 1.0f - i * i / 153.0f);
      xys2[i] = new Point2D(i / 10.0f, 0.13f + i * i / 153.0f);
      xys3[i] = new Point2D(i, 0.1f + i * i / 90.0f);
      xys4[i] = new Point2D(i, 0.95f - i * i / 153.0f);
    }

    PointPlot2D lplot = new PointPlot2D();
    final Point2D[] fpts = new Point2D[]{new Point2D(0.0f, 0.2f),
        new Point2D(4f, 0.65f),
        new Point2D(7f, 0.85f),
        new Point2D(10f, 0.0f)};
    lplot.setData(fpts);
    lplot.setTitle("filled lines");
    lplot.setFill(FillStyle.COLOR);
    lplot.setBorder(true);

    graph.addPlot(lplot);

    final Box2D[] boxes1 = new Box2D[10];
    final Box2D[] boxes2 = new Box2D[10];
    for (int i = 0; i < 10; i++) {
      boxes1[i] = new Box2D(i, (10 - i) / 10.0f, i + 0.5f, (10 - i) / 20.0f);
      boxes2[i] = new Box2D(i - 0.25f, (i + 1) / 10.0f, i + 0.25f, i / 20.0f);
    }

    BoxPlot2D bplot = new BoxPlot2D();
    bplot.setData(boxes2);
    bplot.setTitle("filled box");
    bplot.setFill(FillStyle.PATTERN);
    bplot.setBorder(true);

    graph.addPlot(bplot);

    bplot = new BoxPlot2D();
    bplot.setData(boxes1);
    bplot.setTitle("box");
    bplot.setFill(FillStyle.NONE);

    graph.addPlot(bplot);

    final TextPlot2D tplot = new TextPlot2D();
    final TextPoint2D[] tps = new TextPoint2D[10];
    final CirclePlot2D cplot = new CirclePlot2D();
    final Circle2D[] cps = new Circle2D[10];
    final ScatterPlot2D splot = new ScatterPlot2D();
    final ScatterPoint2D[] sps = new ScatterPoint2D[10];
    for (int i = 0; i < 10; i++) {
      tps[i] = new TextPoint2D(i, (i + 1) / 13.3f, "T" + i);
      cps[i] = new Circle2D(i, (i + 1) / 13.3f, i + 1);
      sps[i] = new ScatterPoint2D(i, (i + 1) / 13.3f, i * 5 + 1);
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
    lplot = new PointPlot2D(AxisSide.TWO, AxisSide.TWO);
    lplot.setData(xys2);
    lplot.setTitle("lines");
    lplot.setLines(true);
    lplot.setPoints(false);

    graph.addPlot(lplot);
    graph.setLo(Axis2D.Y, AxisSide.TWO, -0.15f);

    lplot = new PointPlot2D();
    lplot.setData(xys3);
    lplot.setTitle("lines points");
    lplot.setLines(true);

    graph.addPlot(lplot);

    final Point2D[] cps2 = new Point2D[] {
        new Point2D(0, 0),
        new Point2D(0, 0),
        new Point2D(2.5f, 0.3f),
        new Point2D(5f, 0.9f),
        new Point2D(7.5f, 0.6f),
        new Point2D(10, 0),
        new Point2D(10, 0),
    };
    final CurvePlot2D cplot2 = new CurvePlot2D();
    cplot2.setData(cps2);
    cplot2.setTitle("curve");
    //cplot2.setFill(true);
    cplot2.setType(CurvePlot2D.CUBIC_BEZIER);
    //cplot2.setType(CurvePlot2D.BEZIER);
    cplot2.setLineWidth(5);

    graph.addPlot(cplot2);

    //graph.setVerticalLine(0.43f);
    final GraphLine vline = new GraphLine(4.3f, LineOrientation.VERTICAL);
    graph.addPlot(vline);

    final GraphLine hline = new GraphLine(4.3f, LineOrientation.HORIZONTAL);
    vline.setStyle(LineStyle.DOTS);
    graph.addPlot(hline);

    graph.setTic(Axis2D.X, AxisSide.ONE, 2);
    graph.setTicLabelFormatter(Axis2D.X, AxisSide.ONE, new StringFormatter(new String[]{"The", "quick", "brown", "fox", "jumped"}));

    graph.setKeyVerticalPosition(KeyPosition.BELOW);
    //graph.setColoredKey(false);

    //System.err.println(graph.getXLo(0) + " -- " + graph.getXHi(0));

    return graph;
  }

  /**
   * Returns a graph showing the black and white textures that are
   * available.
   *
   * @return a <code>Graph2D</code>
   */
  public static Graph2D genTextureTest() {
    final Graph2D graph = new Graph2D();

    final ArrayList<TextPoint2D> text = new ArrayList<TextPoint2D>();
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < 9; x++) {
        final int index = y * 9 + x;
        final int y2 = 5 - y;
        final BoxPlot2D plot = new BoxPlot2D();
        plot.setData(new Box2D[] {new Box2D(x + 0.05f, y2 + 0.05f, x + 0.95f, y2 + 0.95f)});
        plot.setFill(FillStyle.PATTERN);
        plot.setColor(index);
        plot.setBorder(true);

        graph.addPlot(plot);

        text.add(new TextPoint2D(x + 0.5f, y2 + 0.5f, "" + index));
      }
    }
    final TextPlot2D plot = new TextPlot2D();
    plot.setData(text.toArray(new TextPoint2D[text.size()]));
    plot.setInvert(true);

    graph.addPlot(plot);

    return graph;
  }
}
