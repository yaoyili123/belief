package com.example.belief.data.db;

import io.reactivex.Observable;

public interface DbHelper {

    <T> Observable<Object> getAllData(Class<T> type);

    <T> Observable<Object> getDataById(Class<T> type, Long id);

//    Observable<List<SportClass>> getAllSportClasses();
//
//    Observable<SportClass> getSportClass(Long cid);
//
//    Observable<List<Food>> getAllFoods();
//
//    Observable<Food> getFood(Long fid);
//
//    Observable<List<Recipe>> getAllRecipes();
//
//    Observable<Recipe> getRecipe(Long rid);
//
//    Observable<List<Recipe>> getRecipesByType(Long tid);
//
//    Observable<List<RecipeType>> getAllRecipeType();

//    Observable<RecipeType> getRecipeType(Long tid);
}
