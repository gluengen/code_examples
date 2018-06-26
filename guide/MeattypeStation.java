package com.youhone.yjsboilingmachine.guide;

import com.youhone.yjsboilingmachine.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Glen Luengen on 4/13/2018.
 */

public class MeattypeStation {

    public static MeattypeStation get() {return new MeattypeStation();}

    private MeattypeStation(){}

    public List<Meattype> getMeattypes(){
        return Arrays.asList(
                new Meattype(Guide.BEEF,"Beef", R.drawable.beef_light, R.drawable.beef),
                new Meattype(Guide.PORK, "Pork", R.drawable.pork_light, R.drawable.pork),
                new Meattype(Guide.POULTRY, "Poultry", R.drawable.poultry_light, R.drawable.poultry),
                new Meattype(Guide.EGGS, "Eggs", R.drawable.eggsy_light, R.drawable.eggsy),
                new Meattype(Guide.SEAFOOD, "Fish and Seafood", R.drawable.seafood_light, R.drawable.seafood),
                new Meattype(Guide.LAMB, "Lamb", R.drawable.lamb_light, R.drawable.lamb),
                new Meattype(Guide.VEGETABLES,"Fruits and Vegetables", R.drawable.vegetable_light, R.drawable.vegetable),
                new Meattype(Guide.GRAINPASTA, "Grains and Legumes", R.drawable.grains_light, R.drawable.grains),
                new Meattype(Guide.DESSERTS, "Desserts", R.drawable.dessert_light, R.drawable.dessert),
                new Meattype(Guide.SAUCES, "Sauces and Syrups", R.drawable.sauces_light, R.drawable.sauces),
                new Meattype(Guide.INFUSIONS, "Infusions", R.drawable.infusions_light, R.drawable.infusions));
    }
}
