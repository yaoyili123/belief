package com.example.belief.data.network.model;

public class RecipeAtCollect extends Recipe{

    private boolean collected;

    public RecipeAtCollect() {
        super();
    }

    public RecipeAtCollect(Recipe recipe) {
        super();
        super.setRid(recipe.getRid());
        super.setTid(recipe.getTid());
        super.setName(recipe.getName());
        super.setDetail(recipe.getDetail());
        super.setPhotoUrl(recipe.getPhotoUrl());
        super.setIngredient(recipe.getIngredient());

    }

    public boolean getCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}