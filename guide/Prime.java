package com.youhone.yjsboilingmachine.guide;

/**
 * Created by Glen Luengen on 4/13/2018.
 */

public class Prime {
    private String primary_ingredient;
    private String name;

    public Prime(String primary_ingredient, String name){
        this.primary_ingredient = primary_ingredient;
        this.name = name;
    }

    public String getPrimary_ingredient() {
        return primary_ingredient;
    }

    public String getName() {
        return name;
    }
}
