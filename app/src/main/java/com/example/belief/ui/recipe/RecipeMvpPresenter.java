package com.example.belief.ui.recipe;

import com.example.belief.ui.base.MvpPresenter;
import com.example.belief.ui.base.MvpView;

public interface RecipeMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void getRecipesByType(int tid, int uid);

    void getRecipeDetail(int rid);

    void addRecipe(int uid, int rid);

    void deleteRecipe(int uid, int rid);

    void getFoods();

    void getFoodDetail(int fid);
}
