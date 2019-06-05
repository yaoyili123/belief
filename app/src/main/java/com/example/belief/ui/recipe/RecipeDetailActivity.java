package com.example.belief.ui.recipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.RecipeDetail;
import com.example.belief.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends BaseActivity implements RecipeMvpView {

    @Inject
    public RecipeMvpPresenter<RecipeMvpView> recipeMvpPresenter;

    @BindView(R.id.recipe_title)
    public Toolbar vTitle;

    @BindView(R.id.recipe_d_pic)
    public ImageView vPic;

    @BindView(R.id.recipe_d_name)
    public TextView vName;

    @BindView(R.id.recipe_d_detail)
    public TextView vDetail;

    @BindView(R.id.recipe_d_ts)
    public TextView vTs;

    @BindView(R.id.recipe_d_dbz)
    public TextView vDbz;

    @BindView(R.id.recipe_d_kcal)
    public TextView vKcal;

    @BindView(R.id.recipe_d_fat)
    public TextView vFat;

    @BindView(R.id.lv_2)
    public RecyclerView vRv;

    public RecipeDetail recipe;

    public List<String> ingredient = new ArrayList<>();

    private RecipeIngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        getActivityComponent().inject(this);
        recipeMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        int rid =  getIntent().getIntExtra("rid", 1);
        recipeMvpPresenter.getRecipeDetail(rid);
        adapter = new RecipeIngredientAdapter();
        vRv.setAdapter(adapter);
        vRv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setData(RecipeDetail data) {
        recipe = data;
        vTitle.setTitle(recipe.getName());
        recipeMvpPresenter.downPic(recipe.getPhotoUrl(), vPic);
        vName.setText(recipe.getName());
        vDetail.setText(recipe.getDetail());
        Map nutritions = recipe.getNutritions();
        vKcal.setText(nutritions.get("热量").toString());
        vTs.setText(nutritions.get("碳水").toString());
        vDbz.setText(nutritions.get("蛋白质").toString());
        vFat.setText(nutritions.get("脂肪").toString());
        Map<String, String> tmp = recipe.getIngredient();
        recipe.getIngredient().forEach((key, value) -> {
            ingredient.add(key);
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        recipeMvpPresenter.onDetach();
        super.onDestroy();
    }

    class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.IngredientViewHolder>{

        @Override
        public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            IngredientViewHolder holder = new IngredientViewHolder(LayoutInflater.from(RecipeDetailActivity.this)
                    .inflate(R.layout.item_food, parent,
                            false));
            return holder;
        }

        @Override
        public void onBindViewHolder(IngredientViewHolder holder, int position)
        {
            String foodName = ingredient.get(position);
            //下载图片
            holder.foodname.setText(foodName);
            holder.foodkcal.setText(recipe.getIngredient().get(foodName));
        }

        @Override
        public int getItemCount() {
            return ingredient.size();
        }

        class IngredientViewHolder extends RecyclerView.ViewHolder{

            private TextView foodname;
            private TextView foodkcal;

            public IngredientViewHolder(View itemView) {
                super(itemView);
                foodname = (TextView) itemView.findViewById(R.id.food_name);
                foodkcal = (TextView) itemView.findViewById(R.id.food_kcal);
            }
        }
    }

    @Override
    public void setData(List data) {

    }
}
