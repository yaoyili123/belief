package com.example.belief.ui.recipe;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.Food;
import com.example.belief.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends BaseActivity implements RecipeMvpView {

    @Inject
    public RecipeMvpPresenter<RecipeMvpView> recipeMvpPresenter;

    @BindView(R.id.top_toolbar)
    public Toolbar vTitle;

    @BindView(R.id.food_d_pic)
    public ImageView vPic;

    @BindView(R.id.food_d_name)
    public TextView vName;

    @BindView(R.id.food_d_detail)
    public TextView vDetail;

    @BindView(R.id.food_d_ts)
    public TextView vTs;

    @BindView(R.id.food_d_dbz)
    public TextView vDbz;

    @BindView(R.id.food_d_kcal)
    public TextView vKcal;

    @BindView(R.id.food_d_fat)
    public TextView vFat;

    public Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        getActivityComponent().inject(this);
        recipeMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        int fid = getIntent().getIntExtra("fid", 1);
        recipeMvpPresenter.getFoodDetail(fid);
    }

    public void setData(Food data) {
        food = data;
        vTitle.setTitle(food.getName());
        recipeMvpPresenter.downPic(food.getPhotoUrl(), vPic);
        vName.setText(food.getName());
        vDetail.setText(food.getDetail());
        Gson gson = new Gson();
        Map<String, String> nutritions = gson.fromJson(food.getIngredient(),
                new TypeToken<Map<String, String>>(){}.getType());
        vKcal.setText(nutritions.get("热量"));
        vTs.setText(nutritions.get("碳水"));
        vDbz.setText(nutritions.get("蛋白质"));
        vFat.setText(nutritions.get("脂肪"));
    }

    @Override
    public void setData(List data) {

    }

    @Override
    protected void onDestroy() {
        recipeMvpPresenter.onDetach();
        super.onDestroy();
    }
}
