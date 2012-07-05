package com.reeltwo.plot;

/**
 * Information about a graph axis.
 * @author Richard Littin
 */
class GraphAxis implements Cloneable {
  private String mTitle = "";
  private float mLo;
  private float mHi;
  private boolean mLoAuto = true;
  private boolean mHiAuto = true;
  private float mTic;
  private float mMinorTic;
  private boolean mTicAuto = true;
  private boolean mShowGrid = false;
  private boolean mShowTics = true;
  private boolean mLogScale = false;
  // axis formatter
  private LabelFormatter mLabelFormatter = null;

  GraphAxis() {
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * @param title title to set
   */
  public void setTitle(String title) {
    mTitle = title == null ? "" : title;
  }

  /**
   * @return lo
   */
  public float getLo() {
    return mLo;
  }

  /**
   * @param lo set lo
   */
  public void setLo(float lo) {
    if (lo <= mHi) {
      mLo = lo;
      mLoAuto = false;
    }
  }

  /**
   * @return hi
   */
  public float getHi() {
    return mHi;
  }

  /**
   * @param hi set hi
   */
  public void setHi(float hi) {
    if (hi >= mLo) {
      mHi = hi;
      mHiAuto = false;
    }
  }

  /**
   * @return whether loAuto is set
   */
  public boolean isLoAuto() {
    return mLoAuto;
  }

  /**
   * @param loAuto set loAuto
   */
  public void setLoAuto(boolean loAuto) {
    mLoAuto = loAuto;
  }

  /**
   * @return whether hiAuto is set
   */
  public boolean isHiAuto() {
    return mHiAuto;
  }

  /**
   * @param hiAuto set hiAuto
   */
  public void setHiAuto(boolean hiAuto) {
    mHiAuto = hiAuto;
  }

  public void setRange(float lo, float hi) {
    mLo = lo <= hi ? lo : hi;
    mHi = lo <= hi ? hi : lo;
    mLoAuto = false;
    mHiAuto = false;
  }

  public void setAutoRange(float low, float high) {
    float lo = low;
    float hi = high;
    if (mLogScale) {
      if (mLoAuto) {
        mLo = (lo <= 0.0f) ? 1.0f : (float) PlotUtils.floor10(lo);
      }
      if (mHiAuto) {
        mHi = (hi <= 0.0f) ? 1.0f : (float) PlotUtils.ceil10(hi);
      }
      if (mHi <= mLo) {
        mHi = mLo * 10.0f;
      }
      mTic = 1.0f;
    } else {
      if (!mLoAuto) {
        lo = mLo;
      }
      if (!mHiAuto) {
        hi = mHi;
      }
      float tic = mTic;
      if (mTicAuto) {
        tic = makeTics(lo, hi);
      }
      lo = tic * (float) Math.floor(lo / tic);
      hi = tic * (float) Math.ceil(hi / tic);
      boolean same = false;
      if (lo == hi) { // same so do some autoscaling
        if (mLoAuto) {
          lo -= tic;
        }
        if (mHiAuto) {
          hi += tic;
        }
        if (lo == hi) { // still the same
          lo -= tic;
          hi += tic;
          same = true;
        }
      }
      if (mLoAuto || same) {
        mLo = lo;
      }
      if (mHiAuto || same) {
        mHi = hi;
      }
      if (mTicAuto || same) {
        mTic = makeTics(mLo, mHi);
      }
    }
  }

  /**
   * Calculates the distance between tics given the range of data
   * values.
   *
   * @param tmin range minimum
   * @param tmax range maximum
   * @return tic size
   */
  private float makeTics(float tmin, float tmax) {
    final float xr = Math.abs(tmax - tmin);
    if (xr <= 1.0e-20) {
      return 1.0f;
    }

    final float l10 = (float) (Math.log(xr) / PlotUtils.L10);
    final float xnorm = (float) Math.pow(10.0f, l10 - ((l10 >= 0.0f) ? (int) l10 : ((int) l10 - 1)));
    final float tics;
    if (xnorm <= 2) {
      tics = 0.2f;
    } else if (xnorm <= 5) {
      tics = 0.5f;
    } else {
      tics = 1.0f;
    }
    return tics * PlotUtils.pow(10.0f, (l10 >= 0.0f) ? (int) l10 : ((int) l10 - 1));
  }

  
  /**
   * @return tic
   */
  public float getTic() {
    return mTic;
  }

  /**
   * @param tic the tic to set
   */
  public void setTic(float tic) {
    mTic = tic;
    mTicAuto = false;
  }

  /**
   * @return the minorTic
   */
  public float getMinorTic() {
    return mMinorTic;
  }

  /**
   * @param minorTic the minorTic to set
   */
  public void setMinorTic(float minorTic) {
    mMinorTic = minorTic;
  }

  /**
   * @return ticAuto
   */
  public boolean isTicAuto() {
    return mTicAuto;
  }

  /**
   * @param ticAuto the mTicAuto to set
   */
  public void setTicAuto(boolean ticAuto) {
    mTicAuto = ticAuto;
  }

  /**
   * @return ShowGrid
   */
  public boolean isShowGrid() {
    return mShowGrid;
  }

  /**
   * @param showGrid the mShowGrid to set
   */
  public void setShowGrid(boolean showGrid) {
    mShowGrid = showGrid;
  }

  /**
   * @return whether to show tics
   */
  public boolean isShowTics() {
    return mShowTics;
  }

  /**
   * @param showTics show tics 
   */
  public void setShowTics(boolean showTics) {
    mShowTics = showTics;
  }

  /**
   * @return whether log scale is set
   */
  public boolean isLogScale() {
    return mLogScale;
  }

  /**
   * @param logScale the logScale to set
   */
  public void setLogScale(boolean logScale) {
    mLogScale = logScale;
  }

  /**
   * @return the LabelFormatter
   */
  public LabelFormatter getLabelFormatter() {
    return mLabelFormatter;
  }

  /**
   * @param labelFormatter the LabelFormatter to set
   */
  public void setLabelFormatter(LabelFormatter labelFormatter) {
    mLabelFormatter = labelFormatter;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    final GraphAxis ga = (GraphAxis) super.clone();
    ga.mTitle = mTitle;
    ga.mLo = mLo;
    ga.mHi = mHi;
    ga.mLoAuto = mLoAuto;
    ga.mHiAuto = mHiAuto;
    ga.mTic = mTic;
    ga.mMinorTic = mMinorTic;
    ga.mShowGrid = mShowGrid;
    ga.mShowTics = mShowTics;
    ga.mLogScale = mLogScale;
    ga.mLabelFormatter = mLabelFormatter;
    return ga;
  }

}
