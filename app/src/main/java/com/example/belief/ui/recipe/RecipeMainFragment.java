package com.example.belief.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.belief.R;
import com.example.belief.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMainFragment extends BaseFragment implements RecipeMvpView{

    @BindView(R.id.recipe_type)
    public ImageView mImageRecipeType;

    @BindView(R.id.recipe_ku)
    public ImageView mImageRecipeKu;

    public static RecipeMainFragment newInstance() {
        Bundle args = new Bundle();
        RecipeMainFragment fragment = new RecipeMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_search_amain, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        setUp(view);
        return view;
    }

    @Override
    protected void setUp(View view) {
        OnClick onClick = new OnClick();
        mImageRecipeType.setOnClickListener(onClick);
        mImageRecipeKu.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.recipe_type:
                    intent = new Intent(RecipeMainFragment.this.getContext(), RecipeTypeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.recipe_ku:
                    intent = new Intent(RecipeMainFragment.this.getContext(), FoodListActivity.class);
                    startActivity(intent);
                default:
                    break;
            }
        }
    }

    @Override
    public void setData(List data) {

    }
}
