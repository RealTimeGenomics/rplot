package com.reeltwo.plot.patterns;

import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Pattern group for excel-like patterns.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class ExcelPatternGroup implements PatternGroup {
  private static final String PATTERN_PATH = "com/reeltwo/plot/patterns/t";
  private static final String PATTERN_EXTENSION = ".png";

  private final ArrayList mPatterns = new ArrayList();

  public ExcelPatternGroup() {
    try {
      ClassLoader loader = Pattern.class.getClassLoader();
      for (int i = 1; i <= 48; i++) {
        final BufferedImage bi = ImageIO.read(loader.getResource(PATTERN_PATH + i + PATTERN_EXTENSION));
        final Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        mPatterns.add(new TexturePaint(bi, r));
      }
    } catch (IOException ioe) {
      System.err.println("Exception loading pattern images: " + ioe.getMessage());
      ioe.printStackTrace();
    }    
  }

  public int getPatternCount() {
    return mPatterns.size();
  }

  public Paint [] getPatterns() {
    return (TexturePaint []) mPatterns.toArray(new TexturePaint[mPatterns.size()]);
  }

  public Paint getPattern(int index) {
    return (TexturePaint) mPatterns.get(index);
  }
}
