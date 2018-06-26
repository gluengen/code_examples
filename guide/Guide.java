package com.youhone.yjsboilingmachine.guide;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public enum Guide {

    PORK("Pork"),
    POULTRY("Poultry"),
    BEEF("Beef"),
    EGGS("Eggs"),
    SEAFOOD("Fish and Seafood"),
    LAMB("Lamb"),
    VEGETABLES("Fruits and Vegetables"),
    GRAINPASTA("Grains and Legumes"),
    DESSERTS("Desserts"),
    SAUCES("Sauces and Syrups"),
    INFUSIONS("Infusions");

    private String displayName;

    Guide(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
