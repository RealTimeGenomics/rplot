package com.reeltwo.plot.ui;

import com.reeltwo.plot.patterns.DefaultColorGroup;
import java.awt.Color;


/**
 * AWT default settings useful in graph plotting.
 * DO NOT RELY ON THIS CLASS BEING AROUND IN THE FUTURE.  
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */

public class AWTDefaults {
  private AWTDefaults() {
  }
  

  /** default colors used to plot lines */
  public static final Color[] COLORS = (Color []) new DefaultColorGroup().getPatterns();

}
