package com.reeltwo.plot.ui;

/**
 * Implement this interface to provide location dependent tool tips in a PlotPanel.
 *
 * @author <a href="mailto:richard@reeltwo.com">Richard Littin</a>
 * @version $Revision$
 */
public interface ToolTipProvider {
  /**
   * Implement this to return position dependent tool tip text.
   *
   * @param x x screen position on graph
   * @param y y screen position on graph
   * @return tool tip text
   */
  String getToolTipText(int x, int y);
}
