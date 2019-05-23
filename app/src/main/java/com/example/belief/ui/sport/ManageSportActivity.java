package com.example.belief.ui.sport;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageSportActivity extends BaseActivity {

    @BindView(R.id.list_tab)
    public TabLayout mTab;

    @BindView(R.id.list_title)
    public Toolbar mTitle;

    @BindView(R.id.list_content)
    public ViewPager mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @OnClick(R.id.list_title)
    public void backHome() {
        this.finish();
    }

    @Override
    protected void setUp() {
        mTitle.setTitle(R.string.top_tile_class_list);

        mTab.addTab(mTab.newTab().setText(R.string.tab_allclass));
        mTab.addTab(mTab.newTab().setText(R.string.tab_doing));
        mTab.addTab(mTab.newTab().setText(R.string.tab_done));
    }
}
