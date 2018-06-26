package com.youhone.yjsboilingmachine.guide;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class Meat {

    private final String meat_name;
    private final String primary_ingredient;
    private final String cook_level;
    private final int min_temp;
    private final int max_temp;
    //private final int meat_icon;
    private final int min_time;
    private final int max_time;
    private final String info;

    public Meat(String meat_name,String primary_ingredient, String cook_level, int min_temp, int max_temp, int min_time, int max_time, String info) {
        this.meat_name = meat_name;
        this.primary_ingredient = primary_ingredient;
        this.cook_level = cook_level;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        //this.meat_icon = meat_icon;
        this.min_time = min_time;
        this.max_time = max_time;
        this.info = info;
    }

    public String getMeat_name(){return meat_name;}

    public String getPrimary_ingredient(){return primary_ingredient;}

    public String getCook_level(){return cook_level;}

    public int getMax_temp(){return max_temp;}

    public int getMin_temp(){return min_temp;}

    //public int getMeat_icon(){return meat_icon;}

    public int getMin_time(){return min_time;}

    public int getMax_time(){return max_time;}

    public String getInfo() {
        return info;
    }
}
