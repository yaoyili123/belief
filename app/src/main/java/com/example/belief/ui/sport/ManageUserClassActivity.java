package com.example.belief.ui.sport;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.SportClass;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.FPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageUserClassActivity extends BaseActivity implements SportMvpView {

    @BindView(R.id.classList_tab)
    public TabLayout tabLayout;

    @BindView(R.id.classList_title)
    public Toolbar mTitle;

    @BindView(R.id.classList_content)
    public ViewPager viewPager;

    @Inject
    public SportMvpPresenter<SportMvpView> sportMvpPresenter;

    //数据缓存
    public List<SportClass> classList;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sport_class_list);
        getActivityComponent().inject(this);
        sportMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void onDestroy() {
        sportMvpPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.classList_title)
    public void backHome() {
        this.finish();
    }

    @Override
    protected void setUp() {
        //获取数据
        sportMvpPresenter.getJoinedClasses(MvpApp.get(this).getCurUser().getUid());
        mTitle.setTitle(R.string.top_tile_class_list);
    }

    public void setData(List data) {
        classList = data;
        Map typeList = MvpApp.get(this).classType;
        List<Integer> types = classList.stream()
                .filter(FPUtils.distinctByKey(SportClass::getType))
                .map(item -> item.getType())
                .collect(Collectors.toList());

        titles.add("全部");
        fragments.add(ShowClassListFragment.newInstance(-1));
        types.forEach((key) -> {
            titles.add((String) typeList.get(key));
            fragments.add(ShowClassListFragment.newInstance((int) key));
        });
        tabLayout.setupWithViewPager(viewPager,false);
        viewPager.setAdapter(new SportClassVpAdapter(fragments, titles, getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
    }
}
