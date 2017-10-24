package com.capternal.test.model;

/**
 * Created by jupitor on 24/10/17.
 */

public class FoodListModel {

    private String FoodName = "";
    private String FoodImage = "";

    public FoodListModel(String foodName, String foodImage) {
        FoodName = foodName;
        FoodImage = foodImage;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }
}
