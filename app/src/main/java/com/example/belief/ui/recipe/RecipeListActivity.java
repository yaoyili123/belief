package com.example.belief.ui.recipe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.RecipeAtCollect;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.ui.user.LoginActivity;
import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RecipeListActivity extends BaseActivity implements RecipeMvpView {

    @Inject
    public RecipeMvpPresenter<RecipeMvpView> recipeMvpPresenter;

    public List<RecipeAtCollect> recipeList = new ArrayList<>();

    private RecyclerView mLv1;

    private TypeRecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        getActivityComponent().inject(this);
        recipeMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUp();
    }

    @Override
    protected void setUp() {
        int tid =  getIntent().getIntExtra("type", 1);
        if (MvpApp.get(this).isLogined(this)) {
            recipeMvpPresenter.getRecipesByType(tid, MvpApp.get(this).getCurUser().getUid());
        }
        else {
            recipeMvpPresenter.getRecipesByType(tid, 0);
        }

        adapter = new TypeRecipeListAdapter();
        mLv1 = findViewById(R.id.lv_1);
        mLv1.setAdapter(adapter);
        mLv1.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setData(List data) {
        recipeList = data;
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        recipeMvpPresenter.onDetach();
        super.onDestroy();
    }

    class TypeRecipeListAdapter extends RecyclerView.Adapter<TypeRecipeListAdapter.RecipeViewHolder> {


        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            RecipeViewHolder holder = new RecipeViewHolder(LayoutInflater.from(RecipeListActivity.this)
                    .inflate(R.layout.item_recipe, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecipeViewHolder holder, int position)
        {
            RecipeAtCollect item = recipeList.get(position);
            //下载图片
            recipeMvpPresenter.downPic(item.getPhotoUrl(), holder.foodphoto);
            holder.tvfoodname.setText(item.getName());
            holder.foodphoto.setOnClickListener(view -> {
                Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                intent.putExtra("rid", recipeList.get(position).getRid());
                RecipeListActivity.this.startActivity(intent);
            });
            holder.mThumbUpView.setUnLikeType(ThumbUpView.LikeType.broken);
            holder.mThumbUpView.setCracksColor(Color.rgb(22, 33, 44));
            holder.mThumbUpView.setFillColor(Color.rgb(220, 20, 60));
            holder.mThumbUpView.setEdgeColor(Color.rgb(112, 128, 144));
            holder.mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    if (!MvpApp.get(RecipeListActivity.this).isLogined(RecipeListActivity.this)) {
                        Intent intent = LoginActivity.getStartIntent(RecipeListActivity.this);
                        startActivity(intent);
                        holder.mThumbUpView.stopAnim();
                        return;
                    }
                    int uid = MvpApp.get(RecipeListActivity.this).getCurUser().getUid();
                    //收藏
                    if (like) {
                        recipeMvpPresenter.addRecipe(uid, item.getRid());
                    }
                    else{
                        recipeMvpPresenter.deleteRecipe(uid, item.getRid());
                    }
                }
            });
//            holder.mThumbUpView.
            holder.mThumbUpView.setUnlike();
            if (item.getCollected())
                holder.mThumbUpView.setLike();
        }

        @Override
        public int getItemCount()
        {
            return recipeList.size();
        }


        class RecipeViewHolder extends RecyclerView.ViewHolder{

            private ImageView foodphoto;
            private TextView tvfoodname;
            private ThumbUpView mThumbUpView;

            public RecipeViewHolder(View itemView) {
                super(itemView);
                tvfoodname = (TextView) itemView.findViewById(R.id.food_name);
                foodphoto = (ImageView) itemView.findViewById(R.id.food_photo);
                mThumbUpView = (ThumbUpView) itemView.findViewById(R.id.icon_demo);
            }
        }
    }
}
