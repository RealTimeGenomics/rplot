package com.reeltwo.plot.ui;

/**
 * Implement this interface to provide location dependent tool tips in a PlotPanel.
 *
 * @author <a href="mailto:richard@reeltwo.com">Richard Littin</a>
 * @version $Revision$
 */
public interface ToolTipProvider {
  String getToolTipText(int x, int y);
}
