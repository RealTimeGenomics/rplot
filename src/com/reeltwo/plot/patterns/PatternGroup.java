package com.reeltwo.plot.patterns;

import java.awt.Paint;

/**
 * PatternGroup.java
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public interface PatternGroup {
  Paint [] getPatterns();

  String getName();
  
  String getDescription();
}
