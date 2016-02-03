package com.nasahapps.multipanelayout;

import com.nasahapps.nasahutils.app.BaseFragment;

import butterknife.OnClick;

/**
 * Created by hhasan on 2/3/16.
 */
public class ControlFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_control;
    }

    @OnClick(R.id.twoToOneHorizontal)
    public void showTwoToOneHorizontal() {
        ((MainActivity) getActivity()).togglePane();
    }

    @OnClick(R.id.oneToTwoHorizontal)
    public void showOneToTwoHorizontal() {

    }

    @OnClick(R.id.oneToOneHorizontal)
    public void showOneToOneHorizontal() {

    }

    @OnClick(R.id.oneToOneToOneHorizontal)
    public void showThreeSideBySideHorizontal() {

    }

    @OnClick(R.id.oneToOneVertical)
    public void showOneToOneVertical() {

    }

    @OnClick(R.id.twoVerticalToOneHorizontal)
    public void showTwoEvenVerticalNextToOneHorizontal() {

    }

    @OnClick(R.id.oneHorizontalToTwoVertical)
    public void showOneHorizontalNextToTwoEvenVertical() {

    }
}
