package com.youhone.yjsboilingmachine.guide;

/**
 * Created by Glen Luengen on 4/13/2018.
 */

public class Meattype {
    private final Guide guide;
    private final String name;
    private final int meat_icon;
    private final int meat_icon_active;

    public Meattype(Guide guide,String name, int meat_icon, int meat_icon_active){
        this.guide = guide;
        this.name = name;
        this.meat_icon = meat_icon;
        this.meat_icon_active = meat_icon_active;
    }

    public Guide getGuide() {
        return guide;
    }

    public String getName() {
        return name;
    }

    public int getMeat_icon() {
        return meat_icon;
    }

    public int getMeat_icon_active() {
        return meat_icon_active;
    }
}
