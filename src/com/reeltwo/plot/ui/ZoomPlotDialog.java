package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import java.awt.Color;
import java.awt.Paint;
import javax.swing.JPopupMenu;

/**
 * A plot dialog that allows the user to select and zoom in on arbitrary
 * regions of the plot.  Provides a pop up menu to allow the plot to be
 * printed and saved as well as controlling zoom actions.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class ZoomPlotDialog extends PlotDialog {
  /** the zoom panel for the glass pane */
  private final ZoomPlotPanel mZoomPanel;
  
  /** Creates the plot dialog. */
  public ZoomPlotDialog() {
    super();

    mZoomPanel = new ZoomPlotPanel(mPlotPanel);
    
    setGlassPane(mZoomPanel);
    getGlassPane().setVisible(true);
    
    // set up a popup menu with zoom and plot controls
    JPopupMenu popup = getPopupMenu();
    popup.addSeparator();
    popup.add(mZoomPanel.getZoomOutAction());
    popup.add(mZoomPanel.getPNPAction());
  }

  public void setColors(Color [] colors) {
    super.setColors(colors);
    mZoomPanel.setColors(colors);
  }

  public void setPatterns(Paint [] patterns) {
    super.setPatterns(patterns);
    mZoomPanel.setPatterns(patterns);
  }
    
  /**
   * Plots the given graph.
   *
   * @param graph a Graph2D to plot.
   */
  public void setGraph(Graph2D graph) {
    mZoomPanel.setGraph(graph); // sets graph in underlying plotPanel;
  }
}
