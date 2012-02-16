package com.reeltwo.plot.patterns;

import java.awt.Paint;

/**
 * Interface for different groups of patterns.
 *
 * @author Richard Littin
 */
public interface PatternGroup {
  /**
   * Returns an array of patterns.  Patterns are any java.awt.Paint
   * objects.
   *
   * @return an array of patterns
   */
  Paint[] getPatterns();

  /**
   * Name of this pattern group.
   *
   * @return a <code>String</code>
   */
  String getName();

  /**
   * Short one line description of the pattern group.
   *
   * @return a <code>String</code>
   */
  String getDescription();
}
