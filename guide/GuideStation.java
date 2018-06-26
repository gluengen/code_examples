package com.youhone.yjsboilingmachine.guide;

//import com.yarolegovich.discretescrollview.sample.R;
import android.content.Context;
import android.content.res.AssetManager;

import com.youhone.yjsboilingmachine.R;
//import com.opencsv.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class GuideStation {

    public static GuideStation get() {
        return new GuideStation();
    }

    private GuideStation() {
    }

    public List<Meat> getForecasts() {
        int grouprank;
        String group;
        int ingrank;
        String ingredient;
        int levelrank;
        String level;
        int tempmin;
        int tempmax;
        int timemin;
        int timemax;
        String info;
        InputStream iS;
        return Arrays.asList(
                new Meat("Beef","Steak Strip","Rare",120,128, 90,150,""),
                new Meat("Beef","Steak Strip","Medium-Rare",129,134,60,240,"If cooking under 130°F(54°C), 2 1/2 hours is maximum "),
                new Meat("Beef","Steak Strip","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Strip","Medium-Well",145,155 ,60,210,""),
                new Meat("Beef","Steak Strip","Well Done",156, 156 ,60,210,""),
                new Meat("Beef","Steak Rib-Eye","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Rib-Eye","Medium-Rare",129,134,60,240,"If cooking under 130°F(54°C), 2 1/2 hours is maximum "),
                new Meat("Beef","Steak Rib-Eye","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Rib-Eye","Medium-Well",145,155,60,210, ""),
                new Meat("Beef","Steak Rib-Eye","Well Done",156,156,60,210,""),
                new Meat("Beef","Steak Porterhouse","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Porterhouse","Medium-Rare",129,134,60,240,"If cooking under 130°F(54°C), 2 1/2 hours is maximum "),
                new Meat("Beef","Steak Porterhouse","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Porterhouse","Medium-Well",145,155,60,210,""),
                new Meat("Beef","Steak Porterhouse","Well Done",156,156,60,210,""),
                new Meat("Beef","Steak Sirloin","Rare",120,128,45,150,""),
                new Meat("Beef","Steak Sirloin","Medium-Rare",129,134,45,240,"If cooking under 130°F(54°C), 2 1/2 hours is maximum "),
                new Meat("Beef","Steak Sirloin","Medium",135,144,45,240,""),
                new Meat("Beef","Steak Sirloin","Medium-Well",145,155,45,210,""),
                new Meat("Beef","Steak Sirloin","Well Done",156,156,60,180,""),
                new Meat("Beef","Steak Tenderloin/Filet","Rare",120,128,45,150,""),
                new Meat("Beef","Steak Tenderloin/Filet","Medium-Rare",129,134,45,240,"If cooking under 130°F(54°C), 2 1/2 hours is maximum "),
                new Meat("Beef","Steak Tenderloin/Filet","Medium",135,144,45,240,""),
                new Meat("Beef","Steak Tenderloin/Filet","Medium-Well",145,155,45,210,""),
                new Meat("Beef","Steak Tenderloin/Filet","Well Done",156,156,60,180,""),
                new Meat("Beef","Steak Hanger","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Hanger","Medium-Rare",129,134,60,240,""),
                new Meat("Beef","Steak Hanger","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Hanger","Medium-Well",145,155,60,210,""),
                new Meat("Beef","Steak Hanger","Well Done",156,156,60,210,""),
                new Meat("Beef","Steak Flank","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Flank","Medium-Rare",129,134,60,240,""),
                new Meat("Beef","Steak Flank","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Flank","Medium-Well",145,155,60,210,""),
                new Meat("Beef","Steak Flank","Well Done",156,156,60,210,""),
                new Meat("Beef","Steak Skirt","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Skirt","Medium-Rare",129,134,60,240,""),
                new Meat("Beef","Steak Skirt","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Skirt","Medium-Well",145,155,60,210,""),
                new Meat("Beef","Steak Skirt","Well Done",156,156,60,210,""),
                new Meat("Beef","Steak Flat Iron","Rare",120,128,90,150,""),
                new Meat("Beef","Steak Flat Iron","Medium-Rare",129,134,60,240,""),
                new Meat("Beef","Steak Flat Iron","Medium",135,144,60,240,""),
                new Meat("Beef","Steak Flat Iron","Medium-Well",145,155,60,210,""),
                new Meat("Beef","Steak Flat Iron","Well Done",156,156,60,210,""),
                new Meat("Beef","Ground Hamburger","Rare",115,123,45,150,""),
                new Meat("Beef","Ground Hamburger","Medium-Rare",124,129,45,150,""),
                new Meat("Beef","Ground Hamburger","Medium",130,137,45,150,""),
                new Meat("Beef","Ground Hamburger","Medium-Well",138,144,45,210,""),
                new Meat("Beef","Ground Hamburger","Well Done",145,155,45,150,""),
                new Meat("Beef","Roast Prime Rib","Medium-Rare",133,138,420,960,""),
                new Meat("Beef","Roast Prime Rib","Medium",140,155,360,840,""),
                new Meat("Beef","Roast Prime Rib","Well Done",158,158,300,660,""),
                new Meat("Beef","Roast Chuck","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Roast Chuck","Medium",149,155,1440,1440,""),
                new Meat("Beef","Roast Chuck","Well Done",158,158,300,660,""),
                new Meat("Beef","Roast Rump","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Roast Rump","Medium",149,155,1440,1440,""),
                new Meat("Beef","Roast Rump","Well Done",158,158,300,660,""),
                new Meat("Beef","Roast Round","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Roast Round","Medium",149,155,1440,1440,""),
                new Meat("Beef","Roast Round","Well Done",158,158,300,660,""),
                new Meat("Beef","Other Brisket","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Other Brisket","Medium",149,155,1440,1440,""),
                new Meat("Beef","Other Brisket","Well Done",158, 158,300,660,""),
                new Meat("Beef","Other Short Ribs","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Other Short Ribs","Medium",149,155,1440,1440,""),
                new Meat("Beef","Other Short Ribs","Well Done",158,158,300,660,""),
                new Meat("Beef","Other Oxtails","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Other Oxtails","Medium",149,155,1440,1440,""),
                new Meat("Beef","Other Oxtails","Well Done",158,158,300,660,""),
                new Meat("Beef","Other Shank","Medium-Rare",131,138,1440,2880,""),
                new Meat("Beef","Other Shank","Medium",149,155,1440,1440,""),
                new Meat("Beef","Other Shank","Well Done",158,158,300,660,""),
                new Meat("Beef","Other Cheeks","Medium-Rare",140,143,2880,2880,""),
                new Meat("Beef","Other Cheeks","Medium",176,176,540,540,""),
                new Meat("Pork","Bone-In Ribs","Tender and succulent",145,145,1440,1440,""),
                new Meat("Pork","Bone-In Ribs","Traditional BBQ",165,165,720,720,""),
                new Meat("Pork","Bone-In Hock/Knuckle","Fall off the bone tender",135,135,4320,4320,"Smoke first in a smoker box for 15 - 20 minutes"),
                new Meat("Pork","Bone-In Shoulder/Butt","Sliceable and still moist",145,145,1080,1440,""),
                new Meat("Pork","Bone-In Shoulder/Butt","Shreddable and moist",170,170,1080,1440,""),
                new Meat("Pork","Bone-In Ham/Shank","Fork tender",149,149,2880,2880,""),
                new Meat("Pork","Bone-In Ham/Shank","Traditional sliced",149,149,600,600,""),
                new Meat("Pork","Bone-In Ham, Precooked","Traditional sliced",140,140,180,480,""),
                new Meat("Pork","Bone-In Chop","Medium-Rare",140,145,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Bone-In Chop","Medium-Well",150,155,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Bone-In Chop","Well Done",160,160,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Boneless Chop","Medium-Rare",140,145,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Boneless Chop","Medium-Well",150,155,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Boneless Chop","Well Done",160,160,60,240,"Add 15 minutes of time for each 1/2 inch of thickness"),
                new Meat("Pork","Boneless Loin","Medium-Rare",130,135,60,240,""),
                new Meat("Pork","Boneless Loin","Medium",140,145,60,240,""),
                new Meat("Pork","Boneless Loin","Medium-Well",150,155,60,240,""),
                new Meat("Pork","Boneless Loin","Well Done",160,160,60,240,""),
                new Meat("Pork","Boneless Shoulder/Butt","Sliceable and still moist",145,145,1080,1440,""),
                new Meat("Pork","Boneless Shoulder/Butt","Shreddable and moist",170,170,1080,1440,""),
                new Meat("Pork","Boneless Belly","Traditional texture, yet moist",165,165,600,600,""),
                new Meat("Pork","Boneless Ham","Traditional sliced",149,149,720,720,""),
                new Meat("Pork","Boneless Ham Precooked","Traditional sliced",149,149,600,600,""),
                new Meat("Pork","Other Sausage","Medium-Rare",131,131,90,390,"Depends on thickness; add approximately 20 minutes for every 1/4 inch in thickness"),
                new Meat("Pork","Other Sausage","Medium",140,140,30,240,"Depends on thickness; add approximately 20 minutes for every 1/4 inch in thickness"),
                new Meat("Poultry","Chicken Breast","Tender and juicy for cold chicken salad",150,150,60,240,""),
                new Meat("Poultry","Chicken Breast","Very soft and juicy, served hot",140,140,90,240,""),
                new Meat("Poultry","Chicken Breast","Juicy, tender and slightly stringy, served hot",150,150,60,240,""),
                new Meat("Poultry","Chicken Drumstick","Firm, juicy, slightly tough",150,150,60,240,""),
                new Meat("Poultry","Chicken Drumstick","Tender and very juicy",165,165,60,240,""),
                new Meat("Poultry","Chicken Drumstick","Fall off the bone tender",165,165,240,480,""),
                new Meat("Poultry","Chicken Thighs","Firm, juicy, slightly tough",150,150,60,240,""),
                new Meat("Poultry","Chicken Thighs","Tender and very juicy",165,165,60,240,""),
                new Meat("Poultry","Chicken Thighs","Fall off the bone tender",165,165,240,480,""),
                new Meat("Poultry","Chicken Eggs","Soft cooked, runny yolk",140,140,45,45,""),
                new Meat("Poultry","Chicken Eggs","Soft cooked, sauce like yolk",145,145,45,45,""),
                new Meat("Poultry","Chicken Eggs","Soft cooked, firmer yolk",150,150,45,45,""),
                new Meat("Poultry","Chicken Eggs","Hard cooked, solid but tender",160,160,45,45,""),
                new Meat("Poultry","Duck Breast","Medium-Rare",130,135,120,120,""),
                new Meat("Poultry","Duck Leg","For confit, chill and rest in rendered fat after coof before searing. ",170,170,720,720,""),
                new Meat("Poultry","Goose Breast","Tender and juicy, steak-like",135,135,120,120,""),
                new Meat("Poultry","Goose Leg","Melt in your mouth and juicy",155,155,1440,1440,""),
                new Meat("Poultry","Goose Leg","A bit more toothsome and juicy",170,170,720,720,""),
                new Meat("Poultry","Turkey Breast","Very pink, soft, extra moist",132,132,240,240,""),
                new Meat("Poultry","Turkey Breast","Pale pink, soft, moist",138,138,180,180,""),
                new Meat("Poultry","Turkey Breast","White, tender, moist",145,145,150,150,""),
                new Meat("Poultry","Turkey Breast","White, traditional roast texture",152,152,120,120,""),
                new Meat("Seafood","Fish Fillet","Very rare, firm texture like sashimi",105,105,10,10,""),
                new Meat("Seafood","Fish Fillet","Rare, soft and buttery",115,115,15,15,""),
                new Meat("Seafood","Fish Fillet","Medium rare, translucent and starting to flake",126,126,20,20,""),
                new Meat("Seafood","Fish Fillet","Medium, very moist, tender, and flaky",128,128,30,30,""),
                new Meat("Seafood","Fish Fillet","Firm, moist and flaky",132,132,40,40,""),
                new Meat("Vegetables","Carrots","Cooked",183,185,30,45,""),
                new Meat("Grains/Pasta","White Rice","Cooked",200,200,25,25,""),
                new Meat("Grains/Pasta","Brown Rice","Cooked",200,200,65,65,""),
                new Meat("Grains/Pasta","Farro","Cooked",183,183,60,60,""),
                new Meat("Grains/Pasta","French Lentils","Cooked",190,190,60,60, ""));
    }
}
