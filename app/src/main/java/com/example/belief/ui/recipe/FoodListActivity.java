package com.example.belief.ui.recipe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.Food;
import com.example.belief.ui.TypeVpAdapter;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.FPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodListActivity extends BaseActivity implements RecipeMvpView {

    @Inject
    public RecipeMvpPresenter<RecipeMvpView> recipeMvpPresenter;

    public List<Food> foodList = new ArrayList<>();

    @BindView(R.id.tablayout_tab)
    public TabLayout tabLayout;

    @BindView(R.id.tablayout_title)
    public Toolbar mTitle;

    @BindView(R.id.tablayout_content)
    public ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_view);
        getActivityComponent().inject(this);
        recipeMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        recipeMvpPresenter.getFoods();
        mTitle.setTitle(R.string.food_library);
    }

    @Override
    public void setData(List data) {
        foodList = data;
        //构建类型列表
        Map typeList = MvpApp.get(this).foodType;
        List<Integer> types = foodList.stream()
                .filter(FPUtils.distinctByKey(Food::getTid))
                .map(item -> item.getTid())
                .collect(Collectors.toList());

        //填数据
        titles.add("全部");
        fragments.add(FoodListFragment.newInstance(-1));
        types.forEach((key) -> {
            titles.add((String) typeList.get(key));
            fragments.add(FoodListFragment.newInstance((int) key));
        });
        tabLayout.setupWithViewPager(viewPager,false);
        viewPager.setAdapter(new TypeVpAdapter(fragments, titles, getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void onDestroy() {
        recipeMvpPresenter.onDetach();
        super.onDestroy();
    }
}
