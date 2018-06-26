package com.youhone.yjsboilingmachine.guide;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.james602152002.floatinglabelspinner.FloatingLabelSpinner;
import com.youhone.yjsboilingmachine.R;
import com.youhone.yjsboilingmachine.base.GosBaseActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class MeatView extends LinearLayout {

    //private Paint gradientPaint;
    //private int[] currentGradient;

    private TextView prime, primeTxt, cook, cookTxt, settings, temp, tempmin, tempmintxt, tempmax,
            tempmaxtxt, time, timemin, timemintxt, timemax, timemaxtxt, info;
    private Spinner primespin, cookspin;
    //private FloatingLabelSpinner primespin, cookspin;
    private ArrayList<String> primary, cooking;
    private List<Meat> displayvalue;
    private List<Meat> meatInfo;
    int hours, mins;
    private boolean isCel;
    Context ctx;
    private int htime, mtime, temper;
    private Transfer transfer;

    //private ArgbEvaluator evaluator;

    public MeatView(Context context) {
        super(context);
        ctx = context;
    }

    public MeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
    }

    public MeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MeatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ctx = context;
    }

    {
        //evaluator = new ArgbEvaluator();

       // gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //setWillNotDraw(false);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(getContext(), R.layout.view_meat, this);

        prime = findViewById(R.id.textView4);
        primeTxt = findViewById(R.id.guide_primary_ingredient);
        cook = findViewById(R.id.textView7);
        cookTxt = findViewById(R.id.guide_cook_level);
        settings = findViewById(R.id.othertextview);
        temp = findViewById(R.id.textView8);
        tempmin = findViewById(R.id.textView10);
        tempmintxt = findViewById(R.id.guide_min_temp);
        tempmax = findViewById(R.id.textView12);
        tempmaxtxt = findViewById(R.id.guide_max_temp);
        time = findViewById(R.id.textView9);
        timemin = findViewById(R.id.textView14);
        timemintxt = findViewById(R.id.guide_min_time);
        timemax = findViewById(R.id.textView16);
        timemaxtxt = findViewById(R.id.guide_max_time);
        info = findViewById(R.id.textView18);
        primespin = findViewById(R.id.prime_spinner);

        primespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                primary = populatespinnercook(primespin.getSelectedItem().toString());
                ArrayAdapter<String> dataadapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, primary);
                cookspin.setAdapter(dataadapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cookspin = findViewById(R.id.cook_spinner);
        cookspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayvalue = findMeatTemp(primespin.getSelectedItem().toString(), cookspin.getSelectedItem().toString());
                //primeTxt.setText(displayvalue.get(0).getPrimary_ingredient()+"");
                // cookTxt.setText(displayvalue.get(0).getCook_level()+"");
                //Implement Celsius and Farenheit later
                if(isCel){
                    tempmintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\">" + celcius(displayvalue.get(0).getMin_temp())+"</font>"+"° C"));
                    temper = celcius(displayvalue.get(0).getMin_temp());
                    tempmaxtxt.setText(Html.fromHtml("Max: "+ "<font color=\"#9a1f29\">"  +celcius(displayvalue.get(0).getMax_temp())+"</font>"+"° C"));
                }
                else {
                    tempmintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\">" + displayvalue.get(0).getMin_temp()+"</font>"+"° F"));
                    temper = displayvalue.get(0).getMin_temp();
                    tempmaxtxt.setText(Html.fromHtml("Max: "+ "<font color=\"#9a1f29\">"  +displayvalue.get(0).getMax_temp()+"</font>"+"° F"));
                }
                hours = displayvalue.get(0).getMin_time() / 60;
                htime = hours;
                mins = displayvalue.get(0).getMin_time() % 60;
                mtime = mins;
                timemintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\"><b>" + hours + "H "  + mins + "M"+"</b></font>"));

                hours = displayvalue.get(0).getMax_time() / 60;
                mins = displayvalue.get(0).getMax_time() % 60;
                timemaxtxt.setText(Html.fromHtml("Max: " + "<font color=\"#9a1f29\"><b>" + hours + "H "  + mins + "M"+"</b></font>"));
                info.setText(displayvalue.get(0).getInfo()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meatInfo = parseXml();
    }

    private List<Meat> parseXml (){
        ctx = getContext().getApplicationContext();
        List<Meat>meats = new ArrayList<>();
        XMLparser parser = new XMLparser(ctx);

        try {
            meats = parser.parse();
        }
        catch (Exception e){
            Log.w ("MEATVIEW", "Exception In Meat View Parse: " + e.getMessage().toString());
        }

        return meats;

    }

    //private void initGradient() {
        //float centerX = getWidth() * 0.5f;
        //Shader gradient = new LinearGradient(
        //        centerX, 0, centerX, getHeight(),
        //        currentGradient, null,
        //        Shader.TileMode.MIRROR);
        //gradientPaint.setShader(gradient);
    //}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //if (currentGradient != null) {
        //    initGradient();
        //}
    }

    //@Override
    //protected void onDraw(Canvas canvas) {
        //canvas.drawRect(0, 0, getWidth(), getHeight(), gradientPaint);
        //super.onDraw(canvas);
    //}

    public void setForecast(Meattype meat, boolean cel) {
        Guide guide = meat.getGuide();
        //ctx = context;
        //currentGradient = weatherToGradient(guide);
        //if (getWidth() != 0 && getHeight() != 0) {
        //    initGradient();
        //}
        isCel = cel;
        primary = populatespinnerprime(meat.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, primary);
        primespin.setAdapter(adapter);

        primary = populatespinnercook(primespin.getSelectedItem().toString());
        ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, primary);
        cookspin.setAdapter(dataadapter);

        displayvalue = findMeatTemp(primespin.getSelectedItem().toString(), cookspin.getSelectedItem().toString());
        //primeTxt.setText(displayvalue.get(0).getPrimary_ingredient()+"");
       // cookTxt.setText(displayvalue.get(0).getCook_level()+"");
        //Implement Celsius and Farenheit later
        if(cel){
            tempmintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\">" + celcius(displayvalue.get(0).getMin_temp())+"</font>"+"° C"));
            temper = celcius(displayvalue.get(0).getMin_temp());
            tempmaxtxt.setText(Html.fromHtml("Max: "+ "<font color=\"#9a1f29\">"  +celcius(displayvalue.get(0).getMax_temp())+"</font>"+"° C"));
        }
        else {
            tempmintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\">" + displayvalue.get(0).getMin_temp()+"</font>"+"° F"));
            temper = displayvalue.get(0).getMin_temp();
            tempmaxtxt.setText(Html.fromHtml("Max: "+ "<font color=\"#9a1f29\">"  +displayvalue.get(0).getMax_temp()+"</font>"+"° F"));
        }
        hours = displayvalue.get(0).getMin_time() / 60;
        htime = hours;
        mins = displayvalue.get(0).getMin_time() % 60;
        mtime = mins;
        timemintxt.setText(Html.fromHtml("Min: " + "<font color=\"#258496\">"+ hours+"</font>" + "H "+ "<font color=\"#258496\">" + mins+"</font>" + "M"));

        hours = displayvalue.get(0).getMax_time() / 60;
        mins = displayvalue.get(0).getMax_time() % 60;
        timemaxtxt.setText(Html.fromHtml("Max: " + "<font color=\"#9a1f29\">" + hours+"</font>" + "H "+ "<font color=\"#9a1f29\">"  + mins+"</font>" + "M"));
        info.setText(displayvalue.get(0).getInfo()+"");
        //invalidate();

        //weatherImage.animate()
        //        .scaleX(1f).scaleY(1f)
        //        .setInterpolator(new AccelerateDecelerateInterpolator())
        //        .setDuration(300)
        //        .start();
    }

    public void onScroll(float fraction, Meat oldF, Meat newF) {
        //weatherImage.setScaleX(fraction);
        //weatherImage.setScaleY(fraction);
        //currentGradient = mix(fraction,
        //        weatherToGradient(newF.getGuide()),
        //        weatherToGradient(oldF.getGuide()));
        //initGradient();
        //invalidate();
    }

    //private int[] mix(float fraction, int[] c1, int[] c2) {
    //    return new int[]{
    //            (Integer) evaluator.evaluate(fraction, c1[0], c2[0]),
    //            (Integer) evaluator.evaluate(fraction, c1[1], c2[1]),
    //            (Integer) evaluator.evaluate(fraction, c1[2], c2[2])
    //    };
    //}

    //private int[] weatherToGradient(Guide guide) {
    //    switch (guide) {
    //        case BEEF:
    //            return colors(R.array.gradientPeriodicClouds);
    //        case PORK:
    //            return colors(R.array.gradientCloudy);
    //        case POULTRY:
    //            return colors(R.array.gradientMostlyCloudy);
    //        default:
    //            throw new IllegalArgumentException();
    //    }
    //}

    /*private int weatherToIcon(Guide guide) {
        switch (guide) {
            case PERIODIC_CLOUDS:
                return R.drawable.periodic_clouds;
            case CLOUDY:
                return R.drawable.cloudy;
            case MOSTLY_CLOUDY:
                return R.drawable.mostly_cloudy;
            case PARTLY_CLOUDY:
                return R.drawable.partly_cloudy;
            case CLEAR:
                return R.drawable.clear;
            default:
                throw new IllegalArgumentException();
        }
    }*/

    private int[] colors(@ArrayRes int res) {
        return getContext().getResources().getIntArray(res);
    }

    public List<Meat> findMeatTemp (String val1, String val2){
        List<Meat> cull = new ArrayList<>();
       // List<Meat> info = csvparser(ctx);
        List<Meat> info = meatInfo;

        int count = info.size();
        for(int i = 0;i<count;i++){
            if (val1.equals(info.get(i).getPrimary_ingredient())){
                cull.add(info.get(i));
            }
        }
        count = cull.size();
        List<Meat> value = new ArrayList<>();
        for(int i = 0;i<count;i++){
            if (val2.equals(cull.get(i).getCook_level())){
                value.add(cull.get(i));
            }
        }
        return value;
    }

    public ArrayList<String> populatespinnerprime(String meatname){
        ArrayList<String> values = new ArrayList<>();
        //List<Prime> info = PrimeStation.get().getMeattypes();
        //List<Meat> info = getprime(csvparser(ctx));
        List<Meat> info = getprime(meatInfo);

        int count = info.size();
        for(int i = 0;i<count;i++){
            if (meatname.equals(info.get(i).getMeat_name())){
                values.add(info.get(i).getPrimary_ingredient());
            }
        }
        return values;
    }

    public ArrayList<String> populatespinnercook(String prime){
        ArrayList<String> values = new ArrayList<>();
        //List<Meat> info = csvparser(ctx);
        List<Meat> info = meatInfo;

        int count = info.size();
        for(int i = 0;i<count;i++){
            if (prime.equals(info.get(i).getPrimary_ingredient())){
                values.add(info.get(i).getCook_level());
            }
        }
        if(values.get(0).equals("Select Cook Level")){
            values = new ArrayList<>();
            values.add("Select Cook Level");
        }
        return values;
    }

    public int celcius(int far){
        double cel = ((far - 32)/1.8);
        int cel2 = (int) cel;
        return cel2;
    }

    //public boolean itIsCel(boolean isit){
    //    isCel = isit;
    //    return isit;
    //}

    public List<Meat> csvparser(Context context){
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
        Meat line;
        List<Meat> store = new ArrayList<>();
        List<String[]> list = new ArrayList<String[]>();
        InputStream is = getResources().openRawResource(R.raw.values);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\\|");
                list.add(row);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
       // CSVFile csvFile = new CSVFile(is);
        //List data = csvFile.read();
        int length = list.size();
        for(int i = 0;i< length;i++){
            group = list.get(i)[1];
            ingredient = list.get(i)[3];
            level = list.get(i)[5];
            timemin = Integer.parseInt(list.get(i)[8].replace(" ",""));
            timemax = Integer.parseInt(list.get(i)[9].replace(" ",""));
            tempmin = Integer.parseInt(removeLastChar(list.get(i)[6]));
            tempmax = Integer.parseInt(removeLastChar(list.get(i)[7]));
            if(list.get(i).length == 11) {
                info = list.get(i)[10];
            }
            else {
                info = "";
            }
            store.add(new Meat(group,ingredient,level,tempmin,tempmax,timemin,timemax,info));
        }
        return store;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private List<Meat> getprime(List<Meat> meats){
        int length = meats.size();
        int count = 0;
        int next;
        List<Meat> getmeat = new ArrayList<>();
        for(int i = 0; i<length;i++){
            if(count == 0){
                getmeat.add(meats.get(i));
                count++;
            }
            next = i+1;
            if(next < length) {
                if (!meats.get(i).getPrimary_ingredient().equals(meats.get(next).getPrimary_ingredient())) {
                    count = 0;
                }
            }
        }
        return getmeat;
    }

    public Transfer getdata(){
        transfer = new Transfer(htime, mtime, temper);
        return transfer;
    }


}
