package com.example.belief.ui.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.belief.R;
import com.example.belief.ui.base.BaseFragment;

public class RecipeMainFragment extends BaseFragment {

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
        return view;
    }

    @Override
    protected void setUp(View view) {

    }
}
