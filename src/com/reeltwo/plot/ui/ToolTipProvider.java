package com.reeltwo.plot.ui;

import com.reeltwo.plot.renderer.Mapping;

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
  
  /**
   * Provides the map between screen and world co-ordinates.
   * Some implementations will need this to find what tool tip to provide
   * others may ignore this (do nothing).
   * @param xmap map for the x co-ordinate.
   * @param ymap map for the y co-ordinate.
   */
  void setMaps(Mapping xmap, Mapping ymap);
}
