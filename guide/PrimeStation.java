package com.youhone.yjsboilingmachine.guide;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Glen Luengen on 4/13/2018.
 */

public class PrimeStation {
    public static PrimeStation get() {return new PrimeStation();}

    private PrimeStation(){}

    public List<Prime> getMeattypes(){
        return Arrays.asList(
                new Prime("Steak Strip","Beef"),
                new Prime("Steak Rib-Eye","Beef"),
                new Prime("Steak Porterhouse","Beef"),
                new Prime("Steak Sirloin","Beef"),
                new Prime("Steak Tenderloin/Filet","Beef"),
                new Prime("Steak Hanger","Beef"),
                new Prime("Steak Flank","Beef"),
                new Prime("Steak Skirt","Beef"),
                new Prime("Steak Flat Iron","Beef"),
                new Prime("Ground Hamburger","Beef"),
                new Prime("Roast Prime Rib","Beef"),
                new Prime("Roast Chuck","Beef"),
                new Prime("Roast Rump","Beef"),
                new Prime("Roast Round","Beef"),
                new Prime("Other Brisket","Beef"),
                new Prime("Other Short Ribs","Beef"),
                new Prime("Other Oxtails","Beef"),
                new Prime("Other Shank","Beef"),
                new Prime("Other Cheeks","Beef"),
                new Prime("Bone-In Ribs","Pork"),
                new Prime("Bone-In Hock/Knuckle","Pork"),
                new Prime("Bone-In Shoulder/Butt","Pork"),
                new Prime("Bone-In Ham/Shank","Pork"),
                new Prime("Bone-In Ham, Precooked","Pork"),
                new Prime("Boneless Chop","Pork"),
                new Prime("Boneless Loin","Pork"),
                new Prime("Boneless Shoulder/Butt","Pork"),
                new Prime("Boneless Belly","Pork"),
                new Prime("Boneless Ham","Pork"),
                new Prime("Boneless Ham Precooked","Pork"),
                new Prime("Other Sausage","Pork"),
                new Prime("Chicken Breast","Poultry"),
                new Prime("Chicken Drumstick","Poultry"),
                new Prime("Chicken Thighs","Poultry"),
                new Prime("Chicken Eggs","Poultry"),
                new Prime("Duck Breast","Poultry"),
                new Prime("Duck Leg","Poultry"),
                new Prime("Goose Breast","Poultry"),
                new Prime("Goose Leg","Poultry"),
                new Prime("Turkey Breast","Poultry"),
                new Prime("Fish Fillet","Seafood"),
                new Prime("Carrots","Vegetables"),
                new Prime("White Rice","Grains/Pasta"),
                new Prime("Brown Rice","Grains/Pasta"),
                new Prime("Farro","Grains/Pasta"),
                new Prime("French Lentils","Grains/Pasta")
        );
    }
}
