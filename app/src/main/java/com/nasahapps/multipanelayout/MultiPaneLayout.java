package com.nasahapps.multipanelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by hhasan on 2/3/16.
 */
public class MultiPaneLayout extends LinearLayout {

    public static final int ORIENTATION_HORIZONTAL_TWO_TO_ONE = 0;
    public static final int ORIENTATION_HORIZONTAL_ONE_TO_TWO = 1;
    public static final int ORIENTATION_HORIZONTAL_TWO_EVEN = 2;
    public static final int ORIENTATION_HORIZONTAL_THREE_EVEN = 3;
    public static final int ORIENTATION_VERTICAL_TWO_EVEN = 4;
    public static final int ORIENTATION_RIGHT_T = 5;
    public static final int ORIENTATION_LEFT_T = 6;

    private int mLayoutOrientation;
    private FragmentManager mFragmentManager;
    private boolean mPaneTwoExpanded, mPaneThreeExpanded;

    public MultiPaneLayout(Context context, FragmentManager fm) {
        super(context);
        mFragmentManager = fm;
        init(null);
    }

    public MultiPaneLayout(Context context) {
        super(context);
        init(null);
    }

    public MultiPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiPaneLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MultiPaneLayout, 0, 0);

            mLayoutOrientation = a.getInteger(R.styleable.MultiPaneLayout_orientation, ORIENTATION_HORIZONTAL_TWO_TO_ONE);

            a.recycle();
        }

        if (mLayoutOrientation > 4) {
            throw new IllegalArgumentException("Invalid layout orientation!");
        }

        if (mLayoutOrientation == ORIENTATION_VERTICAL_TWO_EVEN) {
            setOrientation(VERTICAL);
        } else {
            setOrientation(HORIZONTAL);
        }

        switch (mLayoutOrientation) {
            case ORIENTATION_HORIZONTAL_ONE_TO_TWO:
                FrameLayout fl1 = createFrameLayout(1);
                fl1.setId(R.id.pane_one);
                addView(fl1);

                FrameLayout fl2 = createFrameLayout(2);
                fl2.setVisibility(GONE);
                fl2.setId(R.id.pane_two);
                addView(fl2);
                break;
            case ORIENTATION_HORIZONTAL_TWO_EVEN:
            case ORIENTATION_VERTICAL_TWO_EVEN:
                FrameLayout fl5 = createFrameLayout(1);
                fl5.setId(R.id.pane_one);
                addView(fl5);

                FrameLayout fl6 = createFrameLayout(1);
                fl6.setVisibility(GONE);
                fl6.setId(R.id.pane_two);
                addView(fl6);
                break;
            case ORIENTATION_HORIZONTAL_THREE_EVEN:
                FrameLayout fl7 = createFrameLayout(1);
                fl7.setId(R.id.pane_one);
                addView(fl7);

                FrameLayout fl8 = createFrameLayout(1);
                fl8.setVisibility(GONE);
                fl8.setId(R.id.pane_two);
                addView(fl8);

                FrameLayout fl9 = createFrameLayout(1);
                fl9.setVisibility(GONE);
                fl9.setId(R.id.pane_three);
                addView(fl9);
                break;
            case ORIENTATION_RIGHT_T:
                LinearLayout ll = new LinearLayout(getContext());
                ll.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
                ll.setOrientation(VERTICAL);
                ll.setId(R.id.inner_linear);
                addView(ll);

                FrameLayout fl10 = createFrameLayout(1);
                fl10.setId(R.id.pane_one);
                ll.addView(fl10);

                FrameLayout fl11 = createFrameLayout(1);
                fl11.setId(R.id.pane_two);
                fl11.setVisibility(GONE);
                ll.addView(fl11);

                FrameLayout fl12 = createFrameLayout(1);
                fl12.setId(R.id.pane_three);
                fl12.setVisibility(GONE);
                addView(fl12);
                break;
            case ORIENTATION_LEFT_T:
                FrameLayout fl15 = createFrameLayout(1);
                fl15.setId(R.id.pane_one);
                addView(fl15);

                LinearLayout ll2 = new LinearLayout(getContext());
                ll2.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
                ll2.setOrientation(VERTICAL);
                ll2.setId(R.id.inner_linear);
                ll2.setVisibility(GONE);
                addView(ll2);

                FrameLayout fl13 = createFrameLayout(1);
                fl13.setId(R.id.pane_two);
                fl13.setVisibility(GONE);
                ll2.addView(fl13);

                FrameLayout fl14 = createFrameLayout(1);
                fl14.setId(R.id.pane_three);
                fl14.setVisibility(GONE);
                ll2.addView(fl14);
                break;
            case ORIENTATION_HORIZONTAL_TWO_TO_ONE:
            default:
                FrameLayout fl3 = createFrameLayout(2);
                fl3.setId(R.id.pane_one);
                addView(fl3);

                FrameLayout fl4 = createFrameLayout(1);
                fl4.setVisibility(GONE);
                fl4.setId(R.id.pane_two);
                addView(fl4);
                break;
        }
    }

    private FrameLayout createFrameLayout(int weight) {
        return createFrameLayout(weight, getOrientation());
    }

    private FrameLayout createFrameLayout(int weight, int orientation) {
        int width = orientation == HORIZONTAL ? 0 : ViewGroup.LayoutParams.MATCH_PARENT;
        int height = orientation == HORIZONTAL ? ViewGroup.LayoutParams.MATCH_PARENT : 0;
        FrameLayout fl = new FrameLayout(getContext());
        fl.setLayoutParams(new LayoutParams(width, height, weight));
        return fl;
    }

    public void showFragmentInPane(int paneIndex, Fragment fragment) {
        assertPaneIndex(paneIndex);

        View pane;
        int paneId = getPaneIdForIndex(paneIndex);
        pane = getPaneById(paneId);

        if (pane != null) {
            // Show the pane if it wasn't already visible
            if (pane.getVisibility() == GONE) {
                // If the orientation is LEFT_T, make sure the LinearLayout container
                // is visible first
                if (mLayoutOrientation == ORIENTATION_LEFT_T) {
                    findViewById(R.id.inner_linear).setVisibility(VISIBLE);
                }

                pane.setVisibility(VISIBLE);
            }

            // If a fragment already exists here, replace it
            // Else, add it
            if (mFragmentManager.findFragmentById(paneId) != null) {
                mFragmentManager.beginTransaction()
                        .replace(paneId, fragment)
                        .commit();
            } else {
                mFragmentManager.beginTransaction()
                        .add(paneId, fragment)
                        .commit();
            }

            if (paneIndex == 1) {
                mPaneTwoExpanded = true;
            } else if (paneIndex == 2) {
                mPaneThreeExpanded = true;
            }
        }
    }

    public void hideFragmentInPane(int paneIndex) {
        assertPaneIndex(paneIndex);

        View pane;
        int paneId = getPaneIdForIndex(paneIndex);
        pane = getPaneById(paneId);

        if (pane != null) {
            // Remove the fragment first, if it exists
            Fragment fragment = mFragmentManager.findFragmentById(paneId);
            if (fragment != null) {
                mFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit();
            }

            pane.setVisibility(GONE);

            // If in a LEFT_T orientation, hide the LinearLayout container
            // if both its children are hidden
            if (mLayoutOrientation == ORIENTATION_LEFT_T
                    && getPaneById(R.id.pane_two).getVisibility() == GONE
                    && getPaneById(R.id.pane_three).getVisibility() == GONE) {
                findViewById(R.id.inner_linear).setVisibility(GONE);
            }

            if (paneIndex == 1) {
                mPaneTwoExpanded = false;
            } else if (paneIndex == 2) {
                mPaneThreeExpanded = false;
            }
        }
    }

    public boolean isPaneExpanded(int paneIndex) {
        int id = getPaneIdForIndex(paneIndex);
        View pane = getPaneById(id);
        return pane != null && pane.getVisibility() == VISIBLE;
    }

    private void assertPaneIndex(int index) {
        if (index > 2 || index < 0) {
            throw new IllegalArgumentException("There's only three panes!");
        }
    }

    private int getPaneIdForIndex(int index) {
        if (index == 0) {
            return R.id.pane_one;
        } else if (index == 1) {
            return R.id.pane_two;
        } else {
            return R.id.pane_three;
        }
    }

    private View getPaneById(int id) {
        if ((mLayoutOrientation == ORIENTATION_RIGHT_T && (id == R.id.pane_one || id == R.id.pane_two))
                || (mLayoutOrientation == ORIENTATION_LEFT_T && (id == R.id.pane_two || id == R.id.pane_three))) {
            return findViewById(R.id.inner_linear).findViewById(id);
        } else {
            return findViewById(id);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();
        MultiPaneLayoutState savedState = new MultiPaneLayoutState(state);
        savedState.mLayoutOrientation = mLayoutOrientation;
        savedState.mPaneTwoExpanded = mPaneTwoExpanded;
        savedState.mPaneThreeExpanded = mPaneThreeExpanded;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof MultiPaneLayoutState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        MultiPaneLayoutState savedState = (MultiPaneLayoutState) state;
        super.onRestoreInstanceState(savedState.mViewParcelable);

        mLayoutOrientation = savedState.mLayoutOrientation;
        mPaneTwoExpanded = savedState.mPaneTwoExpanded;
        mPaneThreeExpanded = savedState.mPaneThreeExpanded;

        if (mPaneTwoExpanded) {
            findViewById(R.id.pane_two).setVisibility(VISIBLE);
        }

        if (mPaneThreeExpanded) {
            findViewById(R.id.pane_three).setVisibility(VISIBLE);
        }

        invalidate();
        requestLayout();
    }

    public int getLayoutOrientation() {
        return mLayoutOrientation;
    }

    public void setLayoutOrientation(int layoutOrientation) {
        if (layoutOrientation > 6) {
            throw new IllegalArgumentException("Invalid layout orientation!");
        }

        mLayoutOrientation = layoutOrientation;
        invalidate();
        requestLayout();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    private static class MultiPaneLayoutState implements Parcelable {

        public static final Parcelable.Creator<MultiPaneLayoutState> CREATOR = new Parcelable.Creator<MultiPaneLayoutState>() {
            public MultiPaneLayoutState createFromParcel(Parcel source) {
                return new MultiPaneLayoutState(source);
            }

            public MultiPaneLayoutState[] newArray(int size) {
                return new MultiPaneLayoutState[size];
            }
        };
        public Parcelable mViewParcelable;
        public int mLayoutOrientation;
        public boolean mPaneTwoExpanded, mPaneThreeExpanded;

        public MultiPaneLayoutState(Parcelable viewParcelable) {
            mViewParcelable = viewParcelable;
        }

        protected MultiPaneLayoutState(Parcel in) {
            this.mViewParcelable = in.readParcelable(Parcelable.class.getClassLoader());
            this.mLayoutOrientation = in.readInt();
            this.mPaneTwoExpanded = in.readByte() != 0;
            this.mPaneThreeExpanded = in.readByte() != 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mViewParcelable, 0);
            dest.writeInt(this.mLayoutOrientation);
            dest.writeByte(mPaneTwoExpanded ? (byte) 1 : (byte) 0);
            dest.writeByte(mPaneThreeExpanded ? (byte) 1 : (byte) 0);
        }
    }
}
