package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;

/**
 * Swing dialog for displaying a single graph.  Has a pop up menu to
 * allow saving and printing of the graph.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */
public class PlotDialog extends JDialog {
  protected final PlotPanel mPlotPanel;

  private final JPopupMenu mPopup;

  /** Creates the plot dialog. */
  public PlotDialog() {
    mPlotPanel = new PlotPanel(true); // buffer image
    mPlotPanel.setBackground(Color.WHITE);
    mPlotPanel.setGraphBGColor(new Color(0.8f, 0.9f, 1.0f), Color.WHITE);
    mPlotPanel.setBufferGraphs(true);
    mPlotPanel.setGraphShadowWidth(4);
    
    setContentPane(mPlotPanel);
    
    // set up a popup menu with zoom and plot controls
    mPopup = new JPopupMenu();
    mPopup.setLightWeightPopupEnabled(false);
    mPopup.add(mPlotPanel.getPrintAction());
    mPopup.add(mPlotPanel.getSaveImageAction());
    mPopup.add(mPlotPanel.getSnapShotAction());

    // listener to show popup
    mPlotPanel.addMouseListener(new PopupListener());

    setSize(640, 480);
  }

  /**
   * Sets the colors to use in plots.
   *
   * @param colors an array of colors
   */
  public void setColors(Color [] colors) {
    mPlotPanel.setColors(colors);
  }

  /**
   * Sets the patterns to use in plots.
   *
   * @param patterns an array of patterns
   */
  public void setPatterns(Paint [] patterns) {
    mPlotPanel.setPatterns(patterns);
  }

  /**
   * Returns the dialog's popup menu to allow more actions to be added
   * to it.
   *
   * @return a <code>JPopupMenu</code>
   */
  public JPopupMenu getPopupMenu() {
    return mPopup;
  }
    
  /**
   * Plots the given graph.
   *
   * @param graph a Graph2D to plot.
   */
  public void setGraph(Graph2D graph) {
    mPlotPanel.setGraph(graph);
  }


  /**
   * Returns the underlying plot panel.
   *
   * @return a <code>PlotPanel</code>
   */
  public PlotPanel getPlotPanel() {
    return mPlotPanel;
  }
  
  /**
   * Sets a tool tip provider to allow position dependent tool tip
   * text.  A value of null turns tool tips off.
   *
   * @param provider a <code>ToolTipProvider</code>
   */
  public void setToolTipProvider(ToolTipProvider provider) {
    mPlotPanel.setToolTipProvider(provider);
  }

  /**
   * A class required to listen for right-clicks
   */
  private class PopupListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        mPopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }

}
