package com.youhone.yjsboilingmachine.guide;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;


import com.youhone.yjsboilingmachine.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Created by draco on 5/11/2018.
 */

public class XMLparser {
    private static final String ns = null;
    private String TAG = "XMLParser";
    private Context mContext;

    XMLparser (Context context){
        mContext = context;
    }

    public List<Meat> parse () throws XmlPullParserException, IOException{

        List<Meat>meats = new ArrayList<>();

        try {

            XmlPullParserFactory factory;
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            // See if we have downloaded any XML
            String filename = Environment.getExternalStorageDirectory() + "/XML/values.xml";

            File xmlTest = new File (filename);

            FileReader rdr = null;
            InputStream tmpRdr = null;


            if (!xmlTest.exists()){
                Log.i(TAG, "Could not find Downloaded XML. Using Default. Starting to parse.");
                tmpRdr = mContext.getResources().openRawResource(R.raw.item);
                parser.setInput(tmpRdr, null);
            }
            else {
                rdr = new FileReader(filename);
                Log.i(TAG, "Starting to parse. Here is file name " + filename);
                parser.setInput(rdr);
            }

            // So we can get past the document tag
            parser.nextTag();
            meats = readItems(parser);

            Log.i (TAG, "Finished Parsing");

            return meats;
        }
        catch (Exception e){
            Log.w (TAG, "Exception in XMLParser: " + e.getMessage().toString());
            return null;
        }

    }

    public List parseWithInput (InputStream in) throws XmlPullParserException, IOException{
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readItems(parser);

        } finally {
            in.close();
        }
    }

    private List readItems(XmlPullParser parser) throws XmlPullParserException, IOException{
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "items");
        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("item")){
                entries.add(readItem(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Meat readItem(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String meat = "";
        String ingredient = "";
        String cook = "";
        int minTemp = 0;
        int maxTemp = 0;
        int minTime = 0;
        int maxTime = 0;
        String info = "";
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("meat")){
                meat = readMeat(parser);
            } else if (name.equals("ingredient")){
                ingredient = readIngredient(parser);
            }else if (name.equals("cook")){
                cook = readCook(parser);
            }else if (name.equals("mintemp")){
                minTemp = readMinTemp(parser);
            }else if (name.equals("maxtemp")){
                maxTemp = readMaxTemp(parser);
            }else if (name.equals("mintime")){
                minTime = readMinTime(parser);
            }else if (name.equals("maxtime")){
                maxTime = readMaxTime(parser);
            }else if (name.equals("info")){
                info = readInfo(parser);
            } else {
                skip(parser);
            }

        }
        return new Meat(meat, ingredient,cook, minTemp, maxTemp, minTime, maxTime, info);

    }

    private String readMeat(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "meat");
        String meat = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "meat");
        return meat;
    }

    private String readIngredient(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "ingredient");
        String ingredient = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "ingredient");
        return ingredient;
    }

    private String readCook(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "cook");
        String cook = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "cook");
        return cook;
    }

    private int readMinTemp(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "mintemp");
        int minTemp = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "mintemp");
        return minTemp;
    }

    private int readMaxTemp(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "maxtemp");
        int maxTemp = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "maxtemp");
        return maxTemp;
    }

    private int readMinTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "mintime");
        int minTime = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "mintime");
        return minTime;
    }

    private int readMaxTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "maxtime");
        int maxTime = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "maxtime");
        return maxTime;
    }

    private String readInfo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "info");
        String info = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "info");
        return info;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
