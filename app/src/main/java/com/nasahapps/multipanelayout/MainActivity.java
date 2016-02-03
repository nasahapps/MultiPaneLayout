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

    public void togglePane() {
        if (mMultiPaneLayout.isPaneExpanded(1)) {
            mMultiPaneLayout.hideFragmentInPane(1);
        } else {
            mMultiPaneLayout.showFragmentInPane(1, new ListFragment());
        }
    }
}
