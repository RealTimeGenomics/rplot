package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.TextRenderer;

/**
 * TextPlot.java
 *
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class TextPlot {
  public TextPlot() {
    
  }
  
  public static void main(String [] args) {
    Graph2D graph = SwingPlot.genTest();
    String str = new TextRenderer().drawGraph(graph, 90, 40, true);
    System.out.println(str);
  }

}
