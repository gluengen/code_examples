package com.youhone.yjsboilingmachine.device;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexfu.countdownview.CountDownView;
import com.alexzaitsev.meternumberpicker.MeterNumberPicker;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.triggertrap.seekarc.SeekArc;
import com.youhone.yjsboilingmachine.MessageCenter;
import com.youhone.yjsboilingmachine.MyApplication;
import com.youhone.yjsboilingmachine.R;
import com.youhone.yjsboilingmachine.base.IBaseListener;
import com.youhone.yjsboilingmachine.dialog.DeviceMenuDialog;
import com.youhone.yjsboilingmachine.dialog.circledialog.CircleDialog;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigButton;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigDialog;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigInput;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigText;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigTitle;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.ButtonParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.DialogParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.InputParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.TextParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.TitleParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.view.listener.OnInputClickListener;
import com.youhone.yjsboilingmachine.entity.RecipeDetail;
import com.youhone.yjsboilingmachine.entity.RecipesTemperature;
import com.youhone.yjsboilingmachine.entity.RecipesTime;
import com.youhone.yjsboilingmachine.guide.Transfer;
import com.youhone.yjsboilingmachine.util.DisplayUtils;
import com.youhone.yjsboilingmachine.util.Logger;
import com.youhone.yjsboilingmachine.util.UnitUtils;
import com.youhone.yjsboilingmachine.view.CustomToolBar;
import com.youhone.yjsboilingmachine.view.RecipesTimeMenu;
import com.youhone.yjsboilingmachine.view.RoundProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import pl.droidsonroids.gif.GifImageView;

import static com.youhone.yjsboilingmachine.device.DeviceContentActivity.Type.ANIM;
import static com.youhone.yjsboilingmachine.device.DeviceContentActivity.Type.TIMER;
import static com.youhone.yjsboilingmachine.device.DeviceContentActivity.Type.WORKTIMEOUT;
import static com.youhone.yjsboilingmachine.device.DeviceContentActivity.Type.WRITE_RECIPE;

//import com.sdsmdg.harjot.crollerTest.Croller;
//import com.sdsmdg.harjot.crollerTest.*;
//import com.dualcores.swagpoints.*;
//import com.medialablk.easygifview.EasyGifView;
//import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by AmatorLee on 2017/8/22.
 */

public class DeviceContentActivity extends DeviceControlBaseActivity implements IBaseListener, View.OnClickListener {

    private View mStatusBar;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalListener;
    private CustomToolBar mToolBar;
    private RelativeLayout mLeftBtn, mRightBtn, mRlSetTemp, mRlSetTime;
    private RoundProgressBar mProgressBar;
    private TextView mTxtLeftTime, mTxtLeftTimeContent, mTxtCardSetTemp, mTxtCardCurrentTemp, mTxtSetTemp, mTxtSetTime, mTxtStartTemp;
    private ImageView imgFire, imgTimer, otherclickable;
    private GizWifiDevice targetDevice;
    private RecipeDetail mRecipe;
    private CardView mCardView;
    private ImageView mBtnControlDevice;
    //private CircleProgressBar circleProgressBar;
    private boolean isCel;
    int value = 9;
    private HashMap<String, Object> mCommandDatas;
    private Timer mProgresTimer;
    public static final String START = "start";
    public static final String STOP = "stop";
    private boolean isStart = true;
    private long startTime;
    private long countTime;
    private int setTimeHour = 0;
    private int setTimeMin = 0;
    private int setTempInteger = 5;
    private int setTempDecimal = 0;
    private String uid;
    private String token;
    private String temperature;
    public static final int REQUEST_ZXINGCODE_SETTING = 200;
    public static final int GOTOSETIING = 400;
    private boolean cleanModel;
    private GifImageView gif;
    //private Croller croller;
    //private SwagPoints range;
    private SeekArc arcing;
    private Button starttime;
    public static final String TAG = DeviceContentActivity.class.getSimpleName();
    private int recipesDecimalTemperature;
    private int recipeIntegerTemperature;
    private boolean isFirstBindDevice;
    private boolean shouldShowToast = false;
    private boolean isStartTime = false;
    private RecipesTime timehand;
    //private CountDownView countdown;
    private boolean isOn = false;
    private boolean isTime = false;
    private boolean hasreachedtemp = false;
    private boolean isCooked = false;
    private String devicename;
    private TextView ChangeSetTemp;
    private Transfer mTransfer;
    boolean isheated = false;
    private ImageView whitefire;
    AnimatorSet animatorSet;

    //New Decimal Picker
   private NumberPicker mPicker;
   private TextView increment;
   private int incVal;
    //End New
    ObjectAnimator objectAnimator;
    private TextView header;
    private GifImageView circle;
    private RecipesTemperature update = new RecipesTemperature(0, 0);
    public enum Type {
        QUERY, STOP, START, WORKTIMEOUT, PROGRESS, RENAME, DELETE, WASH, SHARE, NOMAL, RESET, UNBIND, TIMER, UNIT, ANIM, MAC, WRITE_RECIPE
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
//            super.handleMessage(msg);
            Type type = Type.values()[msg.what];
            switch (type) {
                case ANIM:
                    //imgFire.setVisibility(imgFire.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    handler.sendEmptyMessageDelayed(ANIM.ordinal(), 1000);
                    break;
                case PROGRESS:
                    Logger.d(TAG, "progress excute:" + "startTime=" + startTime + " countTime=" + countTime);
                    if (startTime < countTime) {
                        mProgressBar.setProgress(100-(int) (startTime * 100 / countTime));
                    } else if (startTime == countTime) {
                        if (mProgresTimer != null) {
                            mProgresTimer.cancel();
                        }
                    } else {
                    }

                    break;
                case QUERY:
                    handler.removeMessages(WORKTIMEOUT.ordinal());
                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.setMessage(getString(R.string.base_loadingtext));
                        progressDialog.show();
                    }
                    targetDevice.setListener(gizWifiDeviceListener);
                    if (!targetDevice.isSubscribed()) {
                        targetDevice.setSubscribe(MessageCenter.getInstance(MyApplication.getINSTANCE()).getProductSceret(), true);
                    } else {
                        targetDevice.getDeviceStatus();
                    }
                    break;
                case START:
                    handler.removeMessages(WORKTIMEOUT.ordinal());
                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
//                    for (Map.Entry<String, Object> entry : mCommandDatas.entrySet()) {
//                        if (entry.getKey().equals("set_time_hour")) {
//                            setTimeHour = (int) entry.getValue();
//                        }
//                        if (entry.getKey().equals("set_time_min")) {
//                            setTimeMin = (int) entry.getValue();
//                        }
//                        command.put(entry.getKey(), entry.getValue());
//                    }
                    command.put("onoff", true);
                    targetDevice.write(command, 0);
                    handler.removeMessages(WORKTIMEOUT.ordinal());
                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    countTime = setTimeHour * 60 + setTimeMin;
                    startTime = 0;
//                    startTimer();
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.setMessage(getString(R.string.device_start));
                        progressDialog.show();
                    }
                    break;
                case STOP:
                    handler.removeMessages(WORKTIMEOUT.ordinal());
                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    new CircleDialog.Builder(DeviceContentActivity.this)
                            .setText(getString(R.string.device_stop))
                            .setTextSize(45)
                            .setCancelable(false)
                            .configText(new ConfigText() {
                                @Override
                                public void onConfig(TextParams params) {
                                    params.textColor = Color.BLACK;
                                }
                            })
                            .configNegative(new ConfigButton() {
                                @Override
                                public void onConfig(ButtonParams params) {
                                    params.textColor = getResources().getColor(R.color.main_color);
                                }
                            })
                            .setNegative(getString(R.string.base_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    handler.removeMessages(WORKTIMEOUT.ordinal());
                                }
                            })
                            .configPositive(new ConfigButton() {
                                @Override
                                public void onConfig(ButtonParams params) {
                                    params.textColor = getResources().getColor(R.color.main_color);
                                }
                            })
                            .setPositive(getString(R.string.base_confirm), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
                                    command.put("onoff", false);
                                    targetDevice.write(command, 0);
                                    handler.removeMessages(WORKTIMEOUT.ordinal());
                                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                                    mProgressBar.setProgress(0);
                                    if (progressDialog != null && !progressDialog.isShowing()) {
                                        progressDialog.setMessage(getString(R.string.device_control_off));
                                        progressDialog.show();
                                    }
                                    if (mProgresTimer != null) {
                                        mProgresTimer.cancel();
                                    }
                                }
                            })
                            .show();
                    break;
                case WORKTIMEOUT:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
//                    showToast(getString(R.string.toast_connect_timeout));
                    break;
                case DELETE:
                    ubBindDevice();
                    break;
                case WASH:
                    if (!cleanModel) {
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        shouldShowToast = true;
                        handler.removeMessages(WORKTIMEOUT.ordinal());
                        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
                        map.put("clean_model", true);
                        targetDevice.write(map, 0);
                        handler.removeMessages(WORKTIMEOUT.ordinal());
                        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    } else {
                        showToast(getString(R.string.device_control_cleaning));
                    }
                    break;
                case SHARE:
                    Intent intent = new Intent(DeviceContentActivity.this, DeviceShareActivity.class);
                    intent.putExtra("device_id", targetDevice.getDid());
                    startActivity(intent);
                    break;
                case RESET:
                    if (!progressDialog.isShowing()) {
                        progressDialog.setMessage(getString(R.string.base_loadingtext));
                        progressDialog.show();
                    }
                    shouldShowToast = true;
                    ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
                    map.put("dev_reset", true);
                    targetDevice.write(map, 0);
                    handler.removeMessages(WORKTIMEOUT.ordinal());
                    handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                    break;
                case RENAME:
                    new CircleDialog.Builder(DeviceContentActivity.this)
                            .setTitle(getString(R.string.device_rename_device))
                            .configInput(new ConfigInput() {
                                @Override
                                public void onConfig(InputParams params) {
                                    params.max = 28;
                                    params.inputHeight = DisplayUtils.dp2px(DeviceContentActivity.this, 40);
                                    params.text = TextUtils.isEmpty(targetDevice.getAlias()) ? "Sous Vide Cooker" + "(" + targetDevice.getMacAddress().substring(targetDevice.getMacAddress().length() - 3, targetDevice.getMacAddress().length()) + ")" : targetDevice.getAlias();
                                    //params.hintText = TextUtils.isEmpty(targetDevice.getAlias()) ? targetDevice.getProductName() : targetDevice.getAlias();
                                }
                            })
                            .configPositive(new ConfigButton() {
                                @Override
                                public void onConfig(ButtonParams params) {
                                    params.textColor = getResources().getColor(R.color.main_color);
                                }
                            })
                            .setPositiveInput(getString(R.string.base_confirm), new OnInputClickListener() {
                                @Override
                                public void onClick(String text, View v) {
                                    if (!TextUtils.isEmpty(text)) {
                                        if (text.length() > 28) {
                                            Toast.makeText(DeviceContentActivity.this, R.string.rename_failed, Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (!progressDialog.isShowing()) {
                                            progressDialog.setMessage(getString(R.string.device_rename_now));
                                            progressDialog.show();
                                        }
                                        handler.removeMessages(WORKTIMEOUT.ordinal());
                                        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                                        targetDevice.setCustomInfo(targetDevice.getRemark(), text);
                                    }
                                }
                            })
                            .configNegative(new ConfigButton() {
                                @Override
                                public void onConfig(ButtonParams params) {
                                    params.textColor = getResources().getColor(R.color.main_color);
                                }
                            })
                            .setNegative(getString(R.string.base_cancel), null)
                            .show();

                    break;
                case UNBIND:
                    if (!progressDialog.isShowing()) {
                        progressDialog.setMessage(getString(R.string.device_control_unbind_now));
                        progressDialog.show();
                    }
                    if (!uid.isEmpty() && !token.isEmpty()) {
                        handler.removeMessages(WORKTIMEOUT.ordinal());
                        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                        GizWifiSDK.sharedInstance().unbindDevice(uid, token, targetDevice.getDid());
                    }
                    break;
                case TIMER:
                    handler.removeMessages(TIMER.ordinal());
                    handler.sendEmptyMessageDelayed(TIMER.ordinal(), 8000);
                    if (targetDevice != null) {
                        targetDevice.setListener(gizWifiDeviceListener);
                        if (!targetDevice.isSubscribed()) {
                            targetDevice.setSubscribe(MessageCenter.getInstance(MyApplication.getINSTANCE()).getProductSceret(), true);
                        } else
                            targetDevice.getDeviceStatus();
                    }
                    break;
                case UNIT:
                    showUnitDialog(true, isCel);
                    break;
                case MAC:
                    Toast.makeText(DeviceContentActivity.this, targetDevice.getMacAddress(), Toast.LENGTH_LONG).show();
                    break;
                case WRITE_RECIPE:
                    ConcurrentHashMap<String, Object> sendMap = (ConcurrentHashMap<String, Object>) msg.obj;
                    targetDevice.write(sendMap, 0);
                    break;
            }
        }
    };

    private void ubBindDevice() {
        new CircleDialog.Builder(this)
                .setText(getString(R.string.device_unbind_device))
                .setCancelable(true)
                .configText(new ConfigText() {
                    @Override
                    public void onConfig(TextParams params) {
                        params.textColor = Color.BLACK;
                        params.textSize = DisplayUtils.dp2px(DeviceContentActivity.this, 16);
                    }
                })
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = getResources().getColor(R.color.main_color);
                    }
                })
                .setNegative(getString(R.string.base_cancel), null)
                .configPositive(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = getResources().getColor(R.color.main_color);
                    }
                })
                .setPositive(getString(R.string.base_confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.sendEmptyMessage(Type.UNBIND.ordinal());
                    }
                })
                .show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_content);
        setStatusBar(true);
        initView();
        initListener();
        initEvent();

    }

    @Override
    public void initView() {
        token = spf.getString("Token", "");
        uid = spf.getString("Uid", "");
        if (getIntent() != null) {
            this.targetDevice = (GizWifiDevice) getIntent().getParcelableExtra("device");
            //this.mRecipe = (RecipeDetail) getIntent().getSerializableExtra("recipe");
            this.mTransfer = (Transfer) getIntent().getSerializableExtra("guide");
        }
        isFirstBindDevice = spf.getBoolean("isFirstBindDevice", true);
        isCel = spf.getBoolean(targetDevice.getDid() + "isCel", true);
        mCommandDatas = new HashMap<>();
        //mCardView = bindView(R.id.card_device_temp_container);
        mBtnControlDevice = bindView(R.id.btn_device_content_control); //Start Button
        mToolBar = bindView(R.id.toolBar_device_content);
        mStatusBar = bindView(R.id.status_bar); //Status Bar
        mLeftBtn = mToolBar.getLeftButton(); //Button Top Bar
        mRightBtn = mToolBar.getRightButton(); //Button Top Bar
        mRightBtn.setVisibility(View.GONE);
        whitefire = bindView(R.id.img_fire_white);
        mProgressBar = bindView(R.id.device_work_progress); //Circle Progress Bar
        arcing = findViewById(R.id.seekArc);
        otherclickable = findViewById(R.id.imageView16);
        if(isCel){
            arcing.setMax(95);
        }
        else {
            arcing.setMax(203);
        }
        arcing.setTouchInSide(false);
        ChangeSetTemp = findViewById(R.id.textView2);
        circle = findViewById(R.id.whitecircle);
        header = findViewById(R.id.top_header);
        mTxtCardSetTemp = bindView(R.id.txt_device_content_cart_setTemp); //Text of the Set Temp
        mTxtCardCurrentTemp = bindView(R.id.txt_device_content_card_currentTemp); //Text of the current Temperature
        mTxtSetTime = bindView(R.id.txt_device_content_setTime); //Set time
        gif = findViewById(R.id.img_fire_heat);
        starttime = bindView(R.id.start_temp);
        mTxtStartTemp = bindView(R.id.txt_start_temp);
        mPicker = bindView(R.id.meterPicker);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(9);
        increment = bindView(R.id.incrementingtext);
       // dSpinner = bindView(R.id.decimal_spinner);
       // dSpinner.setItems(".0", ".1",".2",".3",".4",".5",".6",".7",".8",".9");
       // dSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
        //    @Override
        //    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
       //         Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
       //     }
       // });
        if (this.mRecipe != null) {
            setRecipe(mRecipe);
        }
        if (this.mTransfer != null){
            setGuide(mTransfer);
        }
    }


    private void setRecipe(RecipeDetail detail) {
        boolean isCel = spf.getBoolean("isCel", false);
        mTxtSetTime.setText(detail.getHeatTime());
        String[] temp;
        String[] time = detail.getHeatTime().split(":");
        if(isCel){
            arcing.setMax(95);
        }
        else {
            arcing.setMax(203);
        }
        if (isCel) {
            mTxtSetTemp.setText(UnitUtils.fahrenheit2Celsius(detail.getHeatTemp()) + "");
            //arcing.setProgress(Integer.parseInt(UnitUtils.fahrenheit2Celsius(detail.getHeatTemp())));
            temperature = UnitUtils.fahrenheit2Celsius(detail.getHeatTemp()) + "";
            temperature.replace(" ", "");
            mTxtCardSetTemp.setText(temperature);
            //mTxtCardSetTemp.setText(UnitUtils.fahrenheit2Celsius(detail.getHeatTemp()) + "°");
            temp = UnitUtils.fahrenheit2Celsius(detail.getHeatTemp()).split("\\.");
        } else {
            mTxtSetTemp.setText(Integer.parseInt(detail.getHeatTemp()) + "");
            //arcing.setProgress(Integer.parseInt(detail.getHeatTemp()));
            temperature = UnitUtils.fahrenheit2Celsius(detail.getHeatTemp()) + "";
            temperature.replace(" ", "");
            mTxtCardSetTemp.setText(temperature);
            //mTxtCardSetTemp.setText(Integer.parseInt(detail.getHeatTemp()) + "°");
            temp = detail.getHeatTemp().split("\\.");
        }
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("temp_unit", isCel ? 0 : 1);
        map.put("set_time_hour", time[0]);
        map.put("set_time_min", time.length < 2 ? 0 : time[1]);
        map.put("set_temp_integer", temp[0]);
        map.put("set_temp_decimal", temp.length < 2 ? 0 : temp[1]);
        Message msg = new Message();
        msg.obj = map;
        msg.what = WRITE_RECIPE.ordinal();
        handler.sendMessageDelayed(msg, 2000);
    }

    private void doRetTemperature(String s, int position) {
        //需要先做一个假的转换
        String setTemp = mTxtCardSetTemp.getText().toString();
        String currentTemp = mTxtCardCurrentTemp.getText().toString();
        ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
        if (position == 0) {
//            if (!isCel) {//原来是华氏度
//                mTxtCardSetTemp.setText(UnitUtils.fahrenheit2Celsius(setTemp.substring(0, setTemp.length() - 2)) + " ℃");
//                mTxtCardCurrentTemp.setText(UnitUtils.fahrenheit2Celsius(currentTemp.substring(0, currentTemp.length() - 2)) + " ℃");
//                mTxtSetTemp.setText(UnitUtils.fahrenheit2Celsius(setTemp.substring(0, setTemp.length() - 2)) + " ℃");
//                setTempInteger = Integer.valueOf(UnitUtils.fahrenheit2Celsius(setTempInteger + "").split("\\.")[0]);
//                setTempDecimal = Integer.valueOf(UnitUtils.fahrenheit2Celsius(setTempDecimal + "").split("\\.")[1]);
//            }
            spf.edit().putBoolean(targetDevice.getDid() + "isCel", true).apply();
            isCel = true;
            command.put("temp_unit", 0);
        } else if (position == 1) {
//            if (isCel) {//原来是华氏度
//                mTxtCardSetTemp.setText(UnitUtils.celsius2Fahrenheit(setTemp.substring(0, setTemp.length() - 2)) + " ℉");
//                mTxtCardCurrentTemp.setText(UnitUtils.celsius2Fahrenheit(currentTemp.substring(0, currentTemp.length() - 2)) + " ℉");
//                mTxtSetTemp.setText(UnitUtils.celsius2Fahrenheit(setTemp.substring(0, setTemp.length() - 2)) + " ℉");
//                Logger.e(TAG, "setTempInteger[0]=" + UnitUtils.celsius2Fahrenheit(setTempInteger + "").split("\\.")[0]);
//                Logger.e(TAG, "setTempInteger[1]=" + UnitUtils.celsius2Fahrenheit(setTempInteger + "").split("\\.")[1]);
//                setTempInteger = Integer.valueOf(UnitUtils.celsius2Fahrenheit(setTempInteger + "").split("\\.")[0]);
//                setTempDecimal = Integer.valueOf(UnitUtils.celsius2Fahrenheit(setTempDecimal + "").split("\\.")[1]);
//            }
            spf.edit().putBoolean(targetDevice.getDid() + "isCel", false).apply();
            isCel = false;
            command.put("temp_unit", 1);
        }
        spf.edit().putBoolean("isFirstBindDevice", false).apply();
        isFirstBindDevice = false;
        targetDevice.write(command, 0);
        handler.removeMessages(WORKTIMEOUT.ordinal());
        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
    }

    @Override
    public void initEvent() {
        mStatusBar.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalListener);
        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
        //countdown.setOnClickListener(this);
        //mRlSetTemp.setOnClickListener(this);
        //mRlSetTime.setOnClickListener(this);
        mBtnControlDevice.setOnClickListener(this);
        starttime.setOnClickListener(this);
        increment.setOnClickListener(this);
        otherclickable.setOnClickListener(this);
    }

    @Override
    public void initListener() {
        mGlobalListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mStatusBar.getLayoutParams().height = getStatusBarHeight(DeviceContentActivity.this);
                    mStatusBar.requestLayout();
                    //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mCardView.getLayoutParams();
                    //params.setMargins(DisplayUtils.dp2px(DeviceContentActivity.this, 20),
                    //        DisplayUtils.dp2px(DeviceContentActivity.this, 300), DisplayUtils.dp2px(DeviceContentActivity.this, 20), 0);
                    //mCardView.requestLayout();
                }
                mStatusBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };
        /*range.setOnSwagPointsChangeListener(new SwagPoints.OnSwagPointsChangeListener() {
            @Override
            public void onPointsChanged(SwagPoints swagPoints, int points, boolean fromUser) {
                mTxtCardSetTemp.setText(points + " F");
            }

            @Override
            public void onStartTrackingTouch(SwagPoints swagPoints) {

            }

            @Override
            public void onStopTrackingTouch(SwagPoints swagPoints) {
                int water = swagPoints.getPoints();
                update.setIntegerValue(water);
                update.setDecimalValue(0);
                handleTemp(update);
            }
        });*/


        arcing.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc var1, int var2, boolean var3) {
                mTxtCardSetTemp.setText(var2 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekArc var1) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc var1) {
                int water = var1.getProgress();
                update.setIntegerValue(water);
                update.setDecimalValue(Integer.parseInt(increment.getText().toString()));
                handleTemp(update);
            }
        });


         /*croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                mTxtCardSetTemp.setText(progress + " F");
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                // tracking started
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                // tracking stopped
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.rl_left_button:
                finish();
                break;
            case R.id.incrementingtext:
                //Change Decimal Place
                if(!isOn) {
                    value = Integer.parseInt(increment.getText().toString());
                    value++;
                    if (value > 9) {
                        value = 0;
                    }
                    increment.setText(Integer.toString(value));
                    update.setIntegerValue(arcing.getProgress());
                    update.setDecimalValue(Integer.parseInt(increment.getText().toString()));
                    handleTemp(update);
                }
                break;
            case R.id.imageView16:
                if(!isOn) {
                    value = Integer.parseInt(increment.getText().toString());
                    value++;
                    if (value > 9) {
                        value = 0;
                    }
                    increment.setText(Integer.toString(value));
                    update.setIntegerValue(arcing.getProgress());
                    update.setDecimalValue(Integer.parseInt(increment.getText().toString()));
                    handleTemp(update);
                }

                break;
            case R.id.rl_right_button:
                DeviceMenuDialog dialog = new DeviceMenuDialog(this);
                dialog.show(getStatusBarHeight(this));
                dialog.setMenuDismissListener(new DeviceMenuDialog.OnDeviceMenuDismissListener() {
                    @Override
                    public void handleChooseMethod(String method) {
                        Logger.d(TAG, "method=" + method);
                        Type type = Type.NOMAL;
                        switch (method) {
                            case DeviceMenuDialog.DELETE:
                                type = Type.DELETE;
                                break;
                            case DeviceMenuDialog.WASH:
                                type = Type.WASH;
                                break;
                            case DeviceMenuDialog.SHARE:
                                type = Type.SHARE;
                                break;
                            case DeviceMenuDialog.RESET:
                                type = Type.RESET;
                                break;
                            case DeviceMenuDialog.RENAME:
                                type = Type.RENAME;
                                break;
                            case DeviceMenuDialog.UNIT:
                                type = Type.UNIT;
                                break;
                            case DeviceMenuDialog.MAC:
                                type = Type.MAC;
                                break;
                        }
                        handler.sendEmptyMessage(type.ordinal());
                    }
                });
                break;
            /*case R.id.rl_device_content_set_temp:
                RecipesTempMenu tempMenu = new RecipesTempMenu(DeviceContentActivity.this, isCel);
                tempMenu.setData(setTempInteger, setTempDecimal);
                tempMenu.showAtLocation(findViewById(R.id.device_content_container), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;*/
            case R.id.txt_device_content_setTime:
                Calendar c = Calendar.getInstance();
                RecipesTimeMenu timeMenu = new RecipesTimeMenu(DeviceContentActivity.this);
                timeMenu.setData(setTimeHour, setTimeMin);
                timeMenu.showAtLocation(findViewById(R.id.contains), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.count_down:
                RecipesTimeMenu timeMenus = new RecipesTimeMenu(DeviceContentActivity.this);
                timeMenus.setData(setTimeHour, setTimeMin);
                timeMenus.showAtLocation(findViewById(R.id.contains), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_device_content_control:
                if (mBtnControlDevice.getTag() == null) {
                    handler.sendEmptyMessage(Type.STOP.ordinal());
                } else {
                    if (mBtnControlDevice.getTag().equals(START)) {
                        //mTxtStartTemp.setText("Heating");
                        //Breaks here
                        mTxtStartTemp.setTextColor(getResources().getColor(R.color.album_White));
                        //why
                        mBtnControlDevice.setBackgroundResource(R.drawable.blank_red_button);
                        ChangeSetTemp.setText(R.string.heating_to);
                        gif.setVisibility(View.VISIBLE);
                        starttime.setVisibility(View.VISIBLE);
                        circle.setImageResource(R.drawable.light_off);
                        //mBtnControlDevice.setBackground(@drawable/blank_red_button);
                        //mBtnControlDevice.setText("Stop");
                        //mBtnControlDevice.setBackgroundResource(R.mipmap.icon_device_content_stop);
                        mBtnControlDevice.setTag(STOP);
                        isOn = true;
                        handler.sendEmptyMessage(Type.START.ordinal());
                    } else if (mBtnControlDevice.getTag().equals(STOP)) {
                        handler.sendEmptyMessage(Type.STOP.ordinal());
                        mTxtStartTemp.setText(R.string.start);
                        starttime.setText(R.string.start_time);
                        ChangeSetTemp.setText(R.string.set_temperature);
                        isOn = false;
                        hasreachedtemp = false;
                        if(isStartTime){
                            circle.setImageResource(R.drawable.clock_small_tiny);
                            //animatorSet.cancel();
                        }
                        isStartTime = false;
                        starttime.setVisibility(View.GONE);
                        mTxtStartTemp.setTextColor(Color.parseColor("#29414E"));
                        gif.setVisibility(View.INVISIBLE);
                        whitefire.setImageResource(R.drawable.flame_icon_light);
                        mBtnControlDevice.setBackgroundResource(R.drawable.blank_yellow_button);
                        //mBtnControlDevice.setText("Start");
                        //mBtnControlDevice.setBackgroundResource(R.mipmap.icon_device_content_start);
                        mBtnControlDevice.setTag(START);

                    } else {
                        return;
                    }
                }
                break;
            case R.id.start_temp:
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage(getString(R.string.base_loadingtext));
                    progressDialog.show();
                }
                starttime.setVisibility(View.GONE);
                ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
                command.put("res1", 0);
                targetDevice.write(command, 0);
                handler.removeMessages(WORKTIMEOUT.ordinal());
                handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
                if(isOn) {
                    isStartTime = true;
                    /*AlphaAnimation fadeIn = new AlphaAnimation(0.5f , 1.0f ) ;
                    AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.5f ) ;
                    circle.startAnimation(fadeIn);
                    circle.startAnimation(fadeOut);
                    fadeIn.setDuration(200);
                    fadeOut.setDuration(500);
                    fadeOut.setStartOffset(500+fadeIn.getStartOffset()+500);
                    fadeIn.setRepeatCount(Animation.INFINITE);
                    fadeOut.setRepeatCount(Animation.INFINITE);*/
                    circle.setImageResource(R.drawable.flash_green_blue);
                    //animateIt(mTxtSetTime);
                    //countdown.start();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleTime(RecipesTime recipesTime) {
        Logger.d(TAG, "handleTime excute");
        setTimeHour = recipesTime.getIntegerValue();
        setTimeMin = recipesTime.getDecimalValue();
        mTxtSetTime.setText(addZeros(setTimeHour)+ "h "+ addZeros(setTimeMin)+"m");
        ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
        command.put("set_time_hour", recipesTime.getIntegerValue());
        command.put("set_time_min", recipesTime.getDecimalValue());
//        command.put("onoff",true);
        targetDevice.write(command, 0);
        handler.removeMessages(WORKTIMEOUT.ordinal());
        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
    }

    public void animateIt(TextView tv){
        objectAnimator = ObjectAnimator.ofInt(tv, "textColor", Color.WHITE, Color.parseColor("#FFE867"));
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(30000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator);
        animatorSet.start();
    }

    private void setGuide(Transfer transfer){
        boolean isCel = spf.getBoolean("isCel",false);
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
        map.put("temp_unit", isCel ? 0 : 1);
        map.put("set_time_hour", transfer.getHtime()+"");
        map.put("set_time_min", transfer.getMtime()+"");
        map.put("set_temp_integer", transfer.getTemper()+"");
        map.put("set_temp_decimal", "0");
        if(isCel){
            arcing.setMax(95);
            arcing.setProgress(transfer.getTemper());

        }
        else {
            arcing.setMax(203);
            arcing.setProgress(transfer.getTemper());

        }
        Message msg = new Message();
        msg.obj = map;
        msg.what = WRITE_RECIPE.ordinal();
        handler.sendMessageDelayed(msg,2000);
    }

    @Override
    protected void didSetSubscribe(GizWifiErrorCode result, GizWifiDevice device, boolean isSubscribed) {
        Logger.d(TAG, "device:" + device.getMacAddress() + "  isSubscribed:" + isSubscribed);
//        super.didSetSubscribe(result, device, isSubscribed);
        handler.removeMessages(WORKTIMEOUT.ordinal());
        if (result != GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            Logger.d(TAG, "toastError==" + toastError(result));
            showToast(toastError(result));
            return;
        }
        this.targetDevice = device;
        targetDevice.getDeviceStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (targetDevice != null) {
            handler.sendEmptyMessage(Type.QUERY.ordinal());
            handler.sendEmptyMessageDelayed(TIMER.ordinal(), 8000);
        }

    }

    //private int sTimeHour, sTimeMin, sTempInt, sTempDec, rTimeHour, rTimeMin, eTimeHour, eTimeMin;
    //private boolean cook_finish, water_heated, no_water, low_water_level, loss_power;


    @Override
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> map, int sn) {
        handler.removeMessages(WORKTIMEOUT.ordinal());
        if(result != GizWifiErrorCode.GIZ_SDK_SUCCESS){
            showToast(toastError(result));
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        if(map.size() > 0 && map.get("data") != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (shouldShowToast) {
                shouldShowToast = false;
                showToast(R.string.toast_execute);
            }
            ConcurrentHashMap<String, Object> dataMap = (ConcurrentHashMap<String, Object>) map.get("data");
            //Set Time
            int sTimeHour = (int) dataMap.get("set_time_hour");
            int sTimeMin = (int) dataMap.get("set_time_min");
            //Time Remaining
            int rTimeHour = (int) dataMap.get("remaining_time_hour");
            int rTimeMin = (int) dataMap.get("remaining_time_min");
            //Time Elapsed
            int eTimeHour = (int) dataMap.get("runnning_time_hour");
            int eTimeMin = (int) dataMap.get("runnning_time_min");
            //Set Temperature
            int sTempInt = (int) dataMap.get("set_temp_integer");
            int sTempDec = (int) dataMap.get("set_temp_decimal");
            //Current Temperature
            int cTempInt = (int) dataMap.get("real_temp_integer");
            int cTempDec = (int) dataMap.get("real_temp_decimal");
            //Is it On
            boolean isOn = (boolean) dataMap.get("onoff");
            if (dataMap.get("temp_unit") != null) {
                if ((int) dataMap.get("temp_unit") == 0) {
                    isCel = true;
                } else if ((int) dataMap.get("temp_unit") == 1) {
                    isCel = false;
                }
            }
            setTimeHour = sTimeHour;
            setTimeMin = sTimeMin;
            //Alerts
            ConcurrentHashMap<String, Object> alertsMap = (ConcurrentHashMap<String, Object>) map.get("alerts");
            //Is Cooking Finished
            boolean cookFinished = (boolean) alertsMap.get("cooking_finish");
            //Is Water Finished
            boolean waterHeated = (boolean) alertsMap.get("water_hated");
            //Is Power Lost
            boolean powerLost = (boolean) alertsMap.get("loss_power");
            if(powerLost){
                showToast(R.string.error_loss_power);
                finish();
            }
            //Set Values
            if(waterHeated){
                isheated = true;
            }
            if(sTempInt == cTempInt){
                if(sTempDec == cTempDec) {
                    isheated = true;
                }
            }
            if(cookFinished){
                isCooked = true;
            }
            if (rTimeHour == 0 && rTimeMin == 0) {
                isCooked = true;
            }
            if(eTimeHour !=0 || eTimeMin != 0){
                isStartTime = true;
            }
            //Set Header Unit
            if(isCel){
                header.setText(getString(R.string.set_temperature) + " | "+getString(R.string.celsius));
            }
            else {
                header.setText(getString(R.string.set_temperature) + " | "+getString(R.string.fahrenheit));
            }
            //Set Device Name in top Bar
             devicename = device.getAlias();
            if(devicename == null){
                devicename = "Sous Vide Cooker" + "(" + device.getMacAddress().substring(device.getMacAddress().length() - 3, device.getMacAddress().length()) + ")";
            }
            mToolBar.setTitle(devicename);
            //Set If device is celsius or Fahrenheit
            spf.edit().putBoolean(device.getDid() + "isCel", isCel).apply();
            //Set Arc Progress and Size
            if(isCel){
                arcing.setMax(95);
                arcing.setProgress(sTempInt);

            }
            else {
                arcing.setMax(203);
                arcing.setProgress(sTempInt);

            }
            //Set Set Temperature
            temperature = Integer.toString(sTempInt);
            mTxtCardSetTemp.setText(temperature+"");
            //int decimalTemp = Integer.toString(sTempDec);
            increment.setText(Integer.toString(sTempDec));
            mPicker.setValue(sTempDec);
            //Set Current Temperature
            String cTemp = getString(R.string.curr_temp)+" "+cTempInt + "." + cTempDec + "°";
            mTxtCardCurrentTemp.setText(cTemp);
            //Set time
            if(isStartTime && isCooked){
                circle.setImageResource(R.drawable.light_off);
                mTxtSetTime.setText(addZeros(rTimeHour)+ "h "+ addZeros(rTimeMin)+"m");
            }
            else if(isStartTime){
                circle.setImageResource(R.drawable.flash_green_blue);
                mTxtSetTime.setText(addZeros(rTimeHour)+ "h "+ addZeros(rTimeMin)+"m");
            }
            else {
                circle.setImageResource(R.drawable.light_off);
                mTxtSetTime.setText(addZeros(sTimeHour)+ "h "+ addZeros(sTimeMin)+"m");
            }
            arcing.setEnabled(true);
            //Device Changes
            if(isOn){ //Device Is On
                mBtnControlDevice.setBackgroundResource(R.drawable.blank_red_button);
                mTxtStartTemp.setText(R.string.stop_heating);
                ChangeSetTemp.setText(R.string.heating_to);
                mBtnControlDevice.setTag(STOP);
                mTxtStartTemp.setTextColor(Color.parseColor("#ffffff"));
                gif.setVisibility(View.VISIBLE);
                whitefire.setVisibility(View.INVISIBLE);
                whitefire.setImageResource(R.drawable.flames_orange); // Change to orange
                if (!handler.hasMessages(ANIM.ordinal())) {
                    handler.sendEmptyMessageDelayed(ANIM.ordinal(), 1000);
                }
                handler.sendEmptyMessage(Type.PROGRESS.ordinal());
                starttime.setVisibility(View.GONE);
                arcing.setEnabled(false);
                if(isheated){
                    ChangeSetTemp.setText(R.string.holding);
                    gif.setVisibility(View.INVISIBLE);
                    whitefire.setVisibility(View.VISIBLE);
                    mTxtStartTemp.setText(R.string.stop);
                    if(isStartTime){
                        starttime.setVisibility(View.GONE);
                    }
                    else {
                        starttime.setVisibility(View.VISIBLE);
                    }
                }
                if(isCooked){
                    gif.setVisibility(View.INVISIBLE);
                    whitefire.setImageResource(R.drawable.flame_icon_green);
                }
            }
            else { //Device Is Off
                isheated = false;
                isCooked = false;
                mBtnControlDevice.setBackgroundResource(R.drawable.blank_yellow_button);
                mTxtStartTemp.setText(R.string.start);
                ChangeSetTemp.setText(R.string.set_temperature);
                mTxtStartTemp.setTextColor(Color.parseColor("#29414E"));
                mBtnControlDevice.setTag(START);
                whitefire.setImageResource(R.drawable.flame_icon_light);
                circle.setImageResource(R.drawable.clock_small_tiny);
                gif.setVisibility(View.INVISIBLE);
                handler.removeMessages(ANIM.ordinal());
            }
        }

    }
    private String addZeros(int input){
        String output;
        if(input == 0){
            output = "00";
        }
        else if(input > 0 && input < 10){
            output = "0" + input;
        }
        else {
            output = input + "";
        }
        return output;
    }

    private void startTimer() {
        mProgresTimer = new Timer();
        mProgresTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startTime++;
                handler.sendEmptyMessage(Type.PROGRESS.ordinal());
            }
        }, 1000 * 60, 1000 * 60);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        handler.removeCallbacksAndMessages(null);
        if (mProgresTimer != null) {
            mProgresTimer.cancel();
            mProgresTimer = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleTemp(RecipesTemperature recipesTemperature) {
        mCommandDatas.put("set_temp_integer", recipesTemperature.getIntegerValue());
        mCommandDatas.put("set_temp_decimal", recipesTemperature.getDecimalValue());
        this.recipesDecimalTemperature = recipesTemperature.getDecimalValue();
        this.recipeIntegerTemperature = recipesTemperature.getIntegerValue();
        setTempDecimal = recipesTemperature.getDecimalValue();
        setTempInteger = recipesTemperature.getIntegerValue();
        ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<>();
        if (isCel) {
            //mTxtSetTemp.setText(recipesTemperature.getIntegerValue() + "." + recipesTemperature.getDecimalValue() + " ℃");
            temperature = recipesTemperature.getIntegerValue() + "";
            temperature.replace(" ", "");
            mTxtCardSetTemp.setText(temperature);
            increment.setText(Integer.toString(recipesTemperature.getDecimalValue()));
            mPicker.setValue(recipesTemperature.getDecimalValue());
            //mTxtCardSetTemp.setText(recipesTemperature.getIntegerValue() + "°");
            command.put("set_temp_integer", recipesTemperature.getIntegerValue());
            command.put("set_temp_decimal", recipesTemperature.getDecimalValue());
        } else {
            String tempF = recipesTemperature.getIntegerValue() + "." + recipesTemperature.getDecimalValue();
            Double mFTemp = Double.valueOf(tempF);
            mFTemp = (mFTemp - 32) / 1.8;
            String tempC = new BigDecimal(Double.toString(mFTemp)).setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString();
            //mTxtSetTemp.setText(recipesTemperature.getIntegerValue() + "." + recipesTemperature.getDecimalValue() + " ℉"); // breaks here
            temperature = recipesTemperature.getIntegerValue() + "";
            temperature.replace(" ", "");
            mTxtCardSetTemp.setText(temperature);
            increment.setText(Integer.toString(recipesTemperature.getDecimalValue()));
            mPicker.setValue(recipesTemperature.getDecimalValue());
            //mTxtCardSetTemp.setText(recipesTemperature.getIntegerValue() + "°");
//            command.put("set_temp_integer", Integer.valueOf(tempC.split("[.]")[0]));
//            command.put("set_temp_decimal", Integer.valueOf(tempC.split("[.]")[1]));
            Logger.d(TAG, "set_temp_integer:" + recipesTemperature.getIntegerValue() + ",set_temp_decimal:" + recipesTemperature.getDecimalValue());
            command.put("set_temp_integer", recipesTemperature.getIntegerValue());
            command.put("set_temp_decimal", recipesTemperature.getDecimalValue());
        }


//        for (Map.Entry<String, Object> entry : mCommandDatas.entrySet()) {
//            command.put(entry.getKey(), entry.getValue());
//        }
//        command.put("onoff",true);

        targetDevice.write(command, 0);
        handler.removeMessages(WORKTIMEOUT.ordinal());
        handler.sendEmptyMessageDelayed(WORKTIMEOUT.ordinal(), 5000);
    }


    @Override
    protected void didUnbindDevice(GizWifiErrorCode result, String did) {
//        super.didUnbindDevice(result, did);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        handler.removeMessages(WORKTIMEOUT.ordinal());
        if (result != GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            showToast(toastError(result));
            return;
        }
        Log.e(TAG, "didUnbindDevice: " + targetDevice.getDid());
        showToast(R.string.device_unbind_success);
        finish();
    }

    @Override
    protected void didSetCustonInfo(GizWifiErrorCode result, GizWifiDevice device) {
//        super.didSetCustonInfo(result, device);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        handler.removeMessages(WORKTIMEOUT.ordinal());
        if (result != GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            showToast(toastError(result));
            return;
        }
        showToast(R.string.device_control_renmame_success);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post("getDevice");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: isCel==" + isCel);
        //spf.edit().putBoolean("isCel", isCel).apply();
    }

    private CircleDialog.Builder mUnitDialog;

    private void showUnitDialog(boolean canCancel, boolean unit) {
        final String[] isCel = {getResources().getString(R.string.user_celsius), getResources().getString(R.string.user_fehrenheit)};
        mUnitDialog = new CircleDialog.Builder(this)
                .setCancelable(canCancel)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.gravity = Gravity.CENTER;
                    }
                })
                .setTitle(getString(R.string.toast_reset_units))
                .configTitle(new ConfigTitle() {
                    @Override
                    public void onConfig(TitleParams params) {
                        params.textSize = DisplayUtils.sp2px(DeviceContentActivity.this, 15);
                        params.textColor = getResources().getColor(R.color.main_color);
                    }
                })
                .setItems(isCel, unit ? 0 : 1, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = isCel[position];
//                            mUnitDialog = null;
                        doRetTemperature(s, position);/*0摄氏度，1华氏度*/
                    }
                });
        mUnitDialog.show();
    }
}
