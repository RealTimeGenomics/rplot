package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import javax.swing.JPopupMenu;

/**
 *
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

    
  /**
   * Plots the given graph.
   *
   * @param graph a Graph2D to plot.
   */
  public void setGraph(Graph2D graph) {
    mZoomPanel.setGraph(graph); // sets graph in underlying plotPanel;
  }
}
