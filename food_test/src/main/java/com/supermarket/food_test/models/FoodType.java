package com.supermarket.food_test.models;

public enum FoodType {
    BEBIDA("bebida"),
    CARNE("carne"),
    MASSA("massa"),
    DOCE("doce");

    private String type;

    FoodType(String type) {
        this.type=type;
    }

    String getType(){
        return this.type;
    }
}
