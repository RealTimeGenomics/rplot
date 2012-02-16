package com.reeltwo.plot.demo;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.TextRenderer;

/**
 * A demo rendering a graph to the terminal.
 *
 * @author Richard Littin
 */
public final class TextPlot {

  // Prevent instantiation.
  private TextPlot() { }

  /**
   * Main loop.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    final Graph2D graph = SwingPlot.genTest();
    final String str = new TextRenderer().drawGraph(graph, 90, 40, true);
    System.out.println(str);
  }

}
