package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;

/**
 *
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class PlotDialog extends JDialog {
  private final PlotPanel mPlotPanel;

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
    mPopup.addSeparator();
    Action [] actions = mPlotPanel.getPlotActions();
    for (int i = 0; i < actions.length; i++) {
      mPopup.add(actions[i]);
    }
    mPopup.addSeparator();
    mPopup.add(new AbstractAction("Exit") {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });
    // listener to show popup
    mPlotPanel.addMouseListener(new PopupListener());

    setSize(640, 480);
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
