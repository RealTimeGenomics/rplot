package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;

/**
 *
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
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

  public void setColors(Color [] colors) {
    mPlotPanel.setColors(colors);
  }

  public void setPatterns(Paint [] patterns) {
    mPlotPanel.setPatterns(patterns);
  }

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
