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

  private ArrayList mPatterns = null;

  public ExcelPatternGroup() {
  }

  public Paint [] getPatterns() {
    if (mPatterns == null) {
      ArrayList patterns = new ArrayList();
      try {
        ClassLoader loader = getClass().getClassLoader();
        for (int i = 1; i <= 48; i++) {
          final BufferedImage bi = ImageIO.read(loader.getResource(PATTERN_PATH + i + PATTERN_EXTENSION));
          final Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
          patterns.add(new TexturePaint(bi, r));
        }
        mPatterns = patterns;
      } catch (IOException ioe) {
        System.err.println("Exception loading pattern images: " + ioe.getMessage());
        ioe.printStackTrace();
      }
    }
    return (TexturePaint []) mPatterns.toArray(new TexturePaint[mPatterns.size()]);
  }

  public String getName() {
    return "Excel-like";
  }

  public String getDescription() {
    return "Black and white patterns similar to those produced by Excel";
  }
}
