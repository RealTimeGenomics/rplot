package com.reeltwo.plot.patterns;

import java.awt.Paint;

/**
 * PatternGroup.java
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public interface PatternGroup {
  int getPatternCount();

  Paint [] getPatterns();

  Paint getPattern(int index);
}
