package com.reeltwo.plot.patterns;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 * Helper class for loading pattern images.
 *
 * @author Richard Littin
 * @version $Revision$
 */
public class Pattern {
  
  private static final ArrayList IMAGES = new ArrayList();

  private static final String PATTERN_PATH = "com/reeltwo/plot/patterns/t";
  private static final String PATTERN_EXTENSION = ".png";

  private Pattern() {
  }

  private static void loadPatterns() {
    if (IMAGES.size() == 0) {
      try {
        ClassLoader loader = Pattern.class.getClassLoader();
        for (int i = 1; i <= 48; i++) {
          IMAGES.add(ImageIO.read(loader.getResource(PATTERN_PATH + i + PATTERN_EXTENSION)));
        }
      } catch (IOException ioe) {
        System.err.println("Exception loading pattern images: " + ioe.getMessage());
        ioe.printStackTrace();
      }
    }
  }

  public static int getPatternCount() {
    loadPatterns();
    return IMAGES.size();
  }

  public static BufferedImage [] getPatterns() {
    loadPatterns();
    return (BufferedImage []) IMAGES.toArray(new BufferedImage[IMAGES.size()]);
  }

  public static BufferedImage getPattern(int index) {
    loadPatterns();
    return (BufferedImage) IMAGES.get(index);
  }

  public static void addPattern(BufferedImage pattern) {
    if (pattern == null) {
      throw new NullPointerException("null pattern given.");
    }
    loadPatterns();
    IMAGES.add(pattern);
  }
}
