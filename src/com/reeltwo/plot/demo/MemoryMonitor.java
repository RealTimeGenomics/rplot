package com.reeltwo.plot.demo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * <code>MemoryMonitor</code> provides regular updates as to the
 * available free memory. <code>MemoryListeners</code> can receive
 * updates as to the amount of free memory, or when free memory
 * approaches certain levels.
 *
 * @author <a href="mailto:len@reeltwo.com">Len Trigg</a>
 * @version $Revision$
 */
public class MemoryMonitor extends Thread {

  /**
   * <code>MemoryListener</code> specifies the API for objects wanting
   * to get notified of changes to free memory.
   *
   * @author <a href="mailto:len@reeltwo.com">len</a>
   * @version $Revision$
   */
  public interface MemoryListener {

    /**
     * Called whenever free memory goes below 10%
     *
     * @param kbMax TODO Description.
     * @param kbUsed TODO Description.
     * @param fractionUsed TODO Description.
     */
    void lowMemory(long kbMax, long kbUsed, final float fractionUsed);


    /**
     * Called about once per second with the amount of available memory
     *
     * @param kbMax TODO Description.
     * @param kbUsed TODO Description.
     * @param fractionUsed TODO Description.
     */
    void memoryUpdate(long kbMax, long kbUsed, final float fractionUsed);
  }


  /** The <code>MemoryMonitor</code> singleton. */
  private static MemoryMonitor INSTANCE = null;


  /**
   * Get the <code>MemoryMonitor</code> singleton.
   *
   * @return The instance value.
   */
  public static MemoryMonitor getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MemoryMonitor();
      INSTANCE.start();
      INSTANCE.addMemoryListener(
        new MemoryListener() {
          public void lowMemory(long kbMax, long kbUsed, final float fractionUsed) {
            System.err.println("Within 10% of memory limit: "
                               + FORMATER.format(kbMax / 1024) + ", "
                               + FORMATER.format(kbUsed / 1024));
          }


          public void memoryUpdate(long kbMax, long kbUsed, final float fraction) { }
        });
    }
    return INSTANCE;
  }


  private static final NumberFormat FORMATER = new DecimalFormat("0.0 MB");

  /** Store those wanting event notification */
  protected ArrayList mMemoryListeners = new ArrayList();

  private long mMaxMemory;

  private long mUsedMemory;

  private long mTotalMemory;

  private float mFractionUsed;

  /** whether to subtract 64MB off max memory value - JVM problem */
  private boolean mSub64M = false;


  /** These should only created by the singleton accessor. */
  private MemoryMonitor() {
    super("Memory Monitor");
    setDaemon(true);
    final String version = System.getProperty("java.version");
    mSub64M = version == null || (version.compareTo("1.4.0") >= 0 && version.compareTo("1.4.2") < 0);
  }


  public synchronized long getMaxMemory() {
    return mMaxMemory;
  }


  private synchronized void setMaxMemory(long max) {
    mMaxMemory = max;
  }


  public synchronized long getTotalMemory() {
    return mTotalMemory;
  }


  private synchronized void setTotalMemory(long total) {
    mTotalMemory = total;
  }


  public synchronized long getUsedMemory() {
    return mUsedMemory;
  }


  private synchronized void setUsedMemory(long used) {
    mUsedMemory = used;
  }


  public synchronized float getFractionUsed() {
    return mFractionUsed;
  }


  private synchronized void setFractionUsed(final float used) {
    mFractionUsed = used;
  }


  public String toString() {
    return "Max " + FORMATER.format(getMaxMemory() / 1024) + "MB , Used " + FORMATER.format(getUsedMemory() / 1024) + "MB";
  }


  /**
   * Add a <code>MemoryListener</code> to receive memory updates.
   *
   * @param listener a <code>MemoryListener</code>.
   */
  public void addMemoryListener(MemoryListener listener) {
    synchronized (mMemoryListeners) {
      if (!mMemoryListeners.contains(listener)) {
        mMemoryListeners.add(listener);
      }
    }
  }


  /**
   * Removes a <code>MemoryListener</code> from receiving memory
   * updates.
   *
   * @param listener a <code>MemoryListener</code>.
   * @return TODO Description.
   */
  public boolean removeMemoryListener(MemoryListener listener) {
    synchronized (mMemoryListeners) {
      return mMemoryListeners.remove(listener);
    }
  }


  protected void notifyLowMemory(long kbMax, long kbUsed, final float fraction) {
    synchronized (mMemoryListeners) {
      for (int i = 0; i < mMemoryListeners.size(); i++) {
        try {
          ((MemoryListener) mMemoryListeners.get(i)).lowMemory(kbMax, kbUsed, fraction);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
    }
  }


  protected void notifyMemoryUpdate(long kbMax, long kbUsed, final float fraction) {
    synchronized (mMemoryListeners) {
      for (int i = 0; i < mMemoryListeners.size(); i++) {
        try {
          ((MemoryListener) mMemoryListeners.get(i)).memoryUpdate(kbMax, kbUsed, fraction);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
    }
  }


  public void run() {
    Runtime r = Runtime.getRuntime();
    long max = (r.maxMemory() / 1024) - (mSub64M ? (64 * 1024) : 0);
    setMaxMemory(max);
    long used = 0;
    long total = 0;
    float fraction = 0.7f;
    boolean lowMem = false;

    while (true) {
      total = r.totalMemory() / 1024;
      setTotalMemory(total);
      used = (r.totalMemory() - r.freeMemory()) / 1024;
      setUsedMemory(used);

      fraction = (float) used / max;
      setFractionUsed(fraction);

      if (fraction >= 0.9f) {
        if (!lowMem) {
          notifyLowMemory(max, used, fraction);
        }
        lowMem = true;
      } else {
        lowMem = false;
      }
      notifyMemoryUpdate(max, used, fraction);
      try {
        sleep(1000);
      } catch (InterruptedException ie) {
        ; // ok
      }
    }
    //System.err.println("thread finishing");
  }
}