package com.reeltwo.plot.patterns;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Pattern group for black and white patterns.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class BW8x8PatternGroup implements PatternGroup {
  private static final String PATTERN_PATH = "com/reeltwo/plot/patterns/t";
  private static final String PATTERN_EXTENSION = ".png";

  private ArrayList mPatterns = null;

  public BW8x8PatternGroup() {
  }

  private Paint loadPattern(String resourceName) throws IOException {
    ClassLoader loader = getClass().getClassLoader();
    final BufferedImage bi = ImageIO.read(loader.getResource(resourceName));
    final Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
    return new TexturePaint(bi, r);
  }

  public Paint [] getPatterns() {
    if (mPatterns == null) {
      ArrayList patterns = new ArrayList();
      try {
        patterns.add(Color.WHITE);
        for (int i = 1; i <= 12; i++) {
          patterns.add(loadPattern(PATTERN_PATH + i + PATTERN_EXTENSION));
        }
        patterns.add(Color.BLACK);
        for (int i = 13; i <= 48; i++) {
          patterns.add(loadPattern(PATTERN_PATH + i + PATTERN_EXTENSION));
        }
        mPatterns = patterns;
      } catch (IOException ioe) {
        System.err.println("Exception loading pattern images: " + ioe.getMessage());
        ioe.printStackTrace();
      }
    }
    return (Paint []) mPatterns.toArray(new Paint[mPatterns.size()]);
  }

  public String getName() {
    return "Black and White 8x8";
  }

  public String getDescription() {
    return "Black and white patterns";
  }
}
