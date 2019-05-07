package com.example.belief.data.db;

import io.reactivex.Observable;

public interface DbHelper {

    <T> Observable<Object> getAllData(Class<T> type);
    <T> Observable<Object> getDataById(Class<T> type, Long id);

//    Observable<RecipeType> getRecipeType(Long tid);
}
