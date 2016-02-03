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

    @OnClick(R.id.togglePaneTwo)
    public void togglePaneTwo() {
        ((MainActivity) getActivity()).togglePane(1);
    }

    @OnClick(R.id.togglePaneThree)
    public void togglePaneThree() {
        ((MainActivity) getActivity()).togglePane(2);
    }
}
