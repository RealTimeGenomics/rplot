package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit tests for the Graph2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class Graph2DTest extends TestCase {

  private static final float PRECISION = 0.0001f;

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Graph2DTest(String name) {
    super(name);
  }


  public void setUp() {
  }


  public void tearDown() {
  }


  public void test1() {
    Graph2D graph = new Graph2D();
    // check default/assummed values
    assertTrue(graph.getTitle().equals(""));
    assertTrue(graph.getKeyTitle().equals(""));
    assertTrue(graph.isBorder());

    for (int i = 0; i < 2; i++) {
      assertTrue(graph.getXLabel(i).equals(""));
      assertTrue(graph.getYLabel(i).equals(""));
      assertTrue(!graph.isXGrid(i));
      assertTrue(!graph.isYGrid(i));

      assertEquals(-1.0f, graph.getXLo(i), PRECISION);
      assertEquals(1.0f, graph.getXHi(i), PRECISION);
      assertEquals(-1.0f, graph.getYLo(i), PRECISION);
      assertEquals(1.0f, graph.getYHi(i), PRECISION);

      assertEquals(0.2f, graph.getXTic(i), PRECISION);
      assertEquals(0.2f, graph.getYTic(i), PRECISION);

      assertTrue(graph.isShowXTics(i));
      assertTrue(graph.isShowYTics(i));

      assertNotNull(graph.getXTicLabelFormatter(i));

      assertTrue(!graph.usesX(i));
      assertTrue(!graph.usesY(i));
    }

    // check sets/gets
    graph.setTitle(null);
    assertTrue(graph.getTitle().equals(""));
    graph.setTitle("title");
    assertTrue(graph.getTitle().equals("title"));

    graph.setKeyTitle(null);
    assertTrue(graph.getKeyTitle().equals(""));
    graph.setKeyTitle("key title");
    assertTrue(graph.getKeyTitle().equals("key title"));

    for (int i = 0; i < 2; i++) {
      graph.setXLabel(i, null);
      assertTrue(graph.getXLabel(i).equals(""));
      graph.setXLabel(i, "xlabel");
      assertTrue(graph.getXLabel(i).equals("xlabel"));

      graph.setYLabel(i, null);
      assertTrue(graph.getYLabel(i).equals(""));
      graph.setYLabel(i, "ylabel");
      assertTrue(graph.getYLabel(i).equals("ylabel"));

      graph.setXGrid(i, true);
      assertTrue(graph.isXGrid(i));
      graph.setXGrid(i, false);
      assertTrue(!graph.isXGrid(i));

      graph.setXGrid(i, true);
      assertTrue(graph.isXGrid(i));
      graph.setXGrid(i, false);
      assertTrue(!graph.isXGrid(i));

      graph.setXLo(i, 0.0f);
      assertEquals(0.0f, graph.getXLo(i), PRECISION);
      assertEquals(1.0f, graph.getXHi(i), PRECISION);
      graph.setAutoScaleX(i);
      graph.setXHi(i, 0.0f);
      assertEquals(-1.0f, graph.getXLo(i), PRECISION);
      assertEquals(0.0f, graph.getXHi(i), PRECISION);
      graph.setXRange(i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getXLo(i), PRECISION);
      assertEquals(2.0f, graph.getXHi(i), PRECISION);
      graph.setAutoScaleX(i);
      assertEquals(-1.0f, graph.getXLo(i), PRECISION);
      assertEquals(1.0f, graph.getXHi(i), PRECISION);

      graph.setYLo(i, 0.0f);
      assertEquals(0.0f, graph.getYLo(i), PRECISION);
      assertEquals(1.0f, graph.getYHi(i), PRECISION);
      graph.setAutoScaleY(i);
      graph.setYHi(i, 0.0f);
      assertEquals(-1.0f, graph.getYLo(i), PRECISION);
      assertEquals(0.0f, graph.getYHi(i), PRECISION);
      graph.setYRange(i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getYLo(i), PRECISION);
      assertEquals(2.0f, graph.getYHi(i), PRECISION);
      graph.setAutoScaleY(i);
      assertEquals(-1.0f, graph.getYLo(i), PRECISION);
      assertEquals(1.0f, graph.getYHi(i), PRECISION);

      graph.setXTic(i, 0.34f);
      assertEquals(0.34f, graph.getXTic(i), PRECISION);
      graph.setAutoScaleXTic(i);
      assertEquals(0.2f, graph.getXTic(i), PRECISION);

      graph.setYTic(i, 0.34f);
      assertEquals(0.34f, graph.getYTic(i), PRECISION);
      graph.setAutoScaleYTic(i);
      assertEquals(0.2f, graph.getYTic(i), PRECISION);

      graph.setShowXTics(i, false);
      assertTrue(!graph.isShowXTics(i));
      graph.setShowXTics(i, true);
      assertTrue(graph.isShowXTics(i));

      graph.setShowYTics(i, false);
      assertTrue(!graph.isShowYTics(i));
      graph.setShowYTics(i, true);
      assertTrue(graph.isShowYTics(i));
    }

    graph.setBorder(false);
    assertTrue(!graph.isBorder());
    graph.setBorder(true);
    assertTrue(graph.isBorder());

    String[] labels = new String[]{"one", "two"};
    for (int i = 0; i < 2; i++) {
      graph.setXTicLabelFormatter(i, new StringFormatter(labels));
      assertTrue(graph.getXTicLabelFormatter(i) instanceof StringFormatter);

      graph.setYTicLabelFormatter(i, new StringFormatter(labels));
      assertTrue(graph.getYTicLabelFormatter(i) instanceof StringFormatter);
    }
  }

  public void test2() {
    Graph2D graph = new Graph2D();
    PointPlot2D pplot = new PointPlot2D();
    pplot.setData(new Point2D[]{new Point2D(1.3f, 3.1f), new Point2D(-1.2f, 5.1f)});
    graph.addPlot(pplot);
    PointPlot2D pplot2 = new PointPlot2D(1, 1);
    pplot2.setData(new Point2D[]{new Point2D(2.0f, 4.0f), new Point2D(-4.4f, 7.3f)});
    graph.addPlot(pplot2);

    assertNotNull(graph.getPlots());
    assertTrue(graph.getPlots().length == 2);
    assertTrue(graph.usesX(0));
    assertTrue(graph.usesX(1));
    assertTrue(graph.usesY(0));
    assertTrue(graph.usesY(1));

    assertEquals(-1.5f, graph.getXLo(0), PRECISION);
    assertEquals(1.5f, graph.getXHi(0), PRECISION);
    assertEquals(0.5f, graph.getXTic(0), PRECISION);

    assertEquals(3.0f, graph.getYLo(0), PRECISION);
    assertEquals(5.2f, graph.getYHi(0), PRECISION);
    assertEquals(0.5f, graph.getYTic(0), PRECISION);

    assertEquals(-5.0f, graph.getXLo(1), PRECISION);
    assertEquals(2.0f, graph.getXHi(1), PRECISION);
    assertEquals(1.0f, graph.getXTic(1), PRECISION);

    assertEquals(4.0f, graph.getYLo(1), PRECISION);
    assertEquals(7.5f, graph.getYHi(1), PRECISION);
    assertEquals(0.5f, graph.getYTic(1), PRECISION);
  }


  public static Test suite() {
    return new TestSuite(Graph2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
