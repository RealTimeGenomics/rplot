package com.reeltwo.plot.demo;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.TextRenderer;

/**
 * A demo rendering a graph to the terminal.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class TextPlot {
  
  public static void main(String [] args) {
    Graph2D graph = SwingPlot.genTest();
    String str = new TextRenderer().drawGraph(graph, 90, 40, true);
    System.out.println(str);
  }

}
