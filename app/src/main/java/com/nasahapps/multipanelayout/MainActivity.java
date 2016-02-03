package com.nasahapps.multipanelayout;

import android.os.Bundle;

import com.nasahapps.nasahutils.app.BaseActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.multiPaneLayout)
    MultiPaneLayout mMultiPaneLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMultiPaneLayout.setFragmentManager(getSupportFragmentManager());

        if (savedInstanceState == null) {
            mMultiPaneLayout.showFragmentInPane(0, new ControlFragment());
        }
    }

    public void togglePane(int index) {
        if (mMultiPaneLayout.isPaneExpanded(index)) {
            mMultiPaneLayout.hideFragmentInPane(index);
        } else {
            mMultiPaneLayout.showFragmentInPane(index, index == 1 ? new ListFragment() : new GridFragment());
        }
    }
}
