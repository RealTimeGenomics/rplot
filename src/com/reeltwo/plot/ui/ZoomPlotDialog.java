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
 * @author Richard Littin
 */
public class ZoomPlotDialog extends PlotDialog {
  /** an id */
  private static final long serialVersionUID = -6438674742380059110L;
  /** the zoom panel for the glass pane */
  private final ZoomPlotPanel mZoomPanel;

  /** Creates the plot dialog. */
  public ZoomPlotDialog() {
    this(false);
  }

  /**
   * Creates a plot dialog setting weather zooming on negative locations is allowed.
   *
   * @param originIsMin whether the origin is the smallest value.
   */
  public ZoomPlotDialog(boolean originIsMin) {
    super();

    mZoomPanel = new ZoomPlotPanel(mPlotPanel, getContentPane());
    mZoomPanel.setOriginIsMin(originIsMin);

    setGlassPane(mZoomPanel);
    getGlassPane().setVisible(true);

    // set up a popup menu with zoom and plot controls
    final JPopupMenu popup = getPopupMenu();
    popup.addSeparator();
    popup.add(mZoomPanel.getZoomOutAction());
    popup.add(mZoomPanel.getPNPAction());
  }

  /** {@inheritDoc} */
  @Override
  public void setColors(Color[] colors) {
    super.setColors(colors);
    mZoomPanel.setColors(colors);
  }

  /** {@inheritDoc} */
  @Override
  public void setPatterns(Paint[] patterns) {
    super.setPatterns(patterns);
    mZoomPanel.setPatterns(patterns);
  }

  /** {@inheritDoc} */
  @Override
  public void setGraph(Graph2D graph) {
    mZoomPanel.setGraph(graph); // sets graph in underlying plotPanel;
  }
}
