package com.example.belief.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;

public class RecipeTypeActivity extends BaseActivity implements RecipeMvpView, View.OnClickListener {

    public ImageView mImageBf;
    public ImageView mImageLa;
    public ImageView mImageDi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_type_list);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        mImageBf = findViewById(R.id.recipe_type_1);
        mImageLa = findViewById(R.id.recipe_type_2);
        mImageDi = findViewById(R.id.recipe_type_3);
        mImageBf.setOnClickListener(this::onClick);
        mImageLa.setOnClickListener(this::onClick);
        mImageDi.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RecipeTypeActivity.this, RecipeListActivity.class);
        switch (view.getId()) {
            case R.id.recipe_type_1: {
                intent.putExtra("type", 1);
                break;
            }
            case R.id.recipe_type_2: {
                intent.putExtra("type", 2);
                break;
            }
            case R.id.recipe_type_3: {
                intent.putExtra("type", 3);
                break;
            }
        }
        startActivity(intent);
    }

    @Override
    public void setData(List data) {

    }
}
