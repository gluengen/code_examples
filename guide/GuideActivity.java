package com.youhone.yjsboilingmachine.guide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
//import com.yarolegovich.discretescrollview.sample.DiscreteScrollViewOptions;
import com.youhone.yjsboilingmachine.DialogActivity;
import com.youhone.yjsboilingmachine.MessageCenter;
import com.youhone.yjsboilingmachine.MyApplication;
import com.youhone.yjsboilingmachine.R;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.youhone.yjsboilingmachine.Swipe;
import com.youhone.yjsboilingmachine.SwipeAdapter;
import com.youhone.yjsboilingmachine.base.GosConfigBaseActivity;
import com.youhone.yjsboilingmachine.base.GosUserBaseActivity;
import com.youhone.yjsboilingmachine.base.IBaseListener;
import com.youhone.yjsboilingmachine.device.ConnectWifiActivity;
import com.youhone.yjsboilingmachine.device.DeviceContentActivity;
import com.youhone.yjsboilingmachine.device.DisplayDevicesActivity;
import com.youhone.yjsboilingmachine.dialog.circledialog.CircleDialog;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigButton;
import com.youhone.yjsboilingmachine.dialog.circledialog.callback.ConfigDialog;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.ButtonParams;
import com.youhone.yjsboilingmachine.dialog.circledialog.params.DialogParams;
import com.youhone.yjsboilingmachine.entity.RecipeDetail;
import com.youhone.yjsboilingmachine.view.CustomToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class GuideActivity extends GosConfigBaseActivity implements IBaseListener,
        DiscreteScrollView.ScrollStateChangeListener<MeatAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<MeatAdapter.ViewHolder>,
        View.OnClickListener {

    private List<Meattype> meats;
    //private Context context;
    private MeatView meatView;
    private DiscreteScrollView cityPicker;
    private static final String TAG = "DEBUG";
    private CustomToolBar mToolBar;
    private RelativeLayout mLeftBtn;
    private boolean isCel;
    private Button guidebtn;
    private Transfer transfer;

    // New Data
    private List<GizWifiDevice> mDeviceList;
    private List<String> deviceName;
    private List<String> devicer;
    private DialogFragment chooseDeviceDialog;
    private int DeviceMenuPadding;
    private TextView manage;
    //Recycle view
    private List<Swipe> swipeitem = new ArrayList<>();
    private SwipeAdapter swipeAdapter;
    //private RecyclerView devicelist;
    private TextView todevices;
    private String conn;
    private RecipeDetail content;
    private TextView devicelist;
    private int count;
    private String token;
    private String uid;
    private int numberOfBoundDevices;
    private boolean isStop = false;
    public static final int GETBOUNDLIST = 001;
    public static final int TIMEOUT = 002;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETBOUNDLIST:
                    GizWifiSDK.sharedInstance().getBoundDevices(uid, token, MessageCenter.getInstance(MyApplication.getINSTANCE()).getProductKeyList());
                    mHandler.removeMessages(TIMEOUT);
                    mHandler.sendEmptyMessageDelayed(TIMEOUT, 8000);
                    break;
                case TIMEOUT:
                    mHandler.removeMessages(TIMEOUT);
//                    showToast(R.string.toast_connect_timeout);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //setStatusBar(true);
        Intent mDialogIntent = getIntent();
        if (!TextUtils.isEmpty(mDialogIntent.getStringExtra("AlertType")) && !TextUtils.isEmpty(mDialogIntent.getStringExtra("AlertDatas"))) {
            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("AlertType", mDialogIntent.getStringExtra("AlertType"));
            intent.putExtra("AlertDatas", mDialogIntent.getStringExtra("AlertDatas"));
            startActivity(intent);
        }
        initView();
        initListener();
        initEvent();
    }

    @Override
    public void initView() {
        isCel = spf.getBoolean("isCel", false);

        uid = spf.getString("Uid", "");
        token = spf.getString("Token", "");

        meatView = (MeatView) findViewById(R.id.meat_view);
        meatView.ctx = getApplicationContext();

        //isCel = meatView.itIsCel(isCel);
        mToolBar = bindView(R.id.toolBar_guide);
        mLeftBtn = mToolBar.getLeftButton();
        mLeftBtn.setOnClickListener(this);
        meats = MeattypeStation.get().getMeattypes();
        cityPicker = (DiscreteScrollView) findViewById(R.id.meat_picker);
        cityPicker.setSlideOnFling(true);
        cityPicker.setAdapter(new MeatAdapter(meats));
        cityPicker.addOnItemChangedListener(this);
        cityPicker.addScrollStateChangeListener(this);
        cityPicker.scrollToPosition(2);
        cityPicker.setItemTransitionTimeMillis(150);
        guidebtn = bindView(R.id.guidebutton);
        guidebtn.setOnClickListener(this);
        //cityPicker.setItemTransformer(new ScaleTransformer.Builder()
        //        .setMinScale(0.8f)
        //        .build());
        deviceName = new ArrayList<>();
        mDeviceList = new ArrayList<>();
        devicer = new ArrayList<>();
        meatView.setForecast(meats.get(0), isCel);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onCurrentItemChanged(@Nullable MeatAdapter.ViewHolder holder, int position) {
        //viewHolder will never be null, because we never remove items from adapter's list
        if (holder != null) {
            if (meats.get(position) != null) {
                meatView.setForecast(meats.get(position), isCel);
                holder.showText(position);
            }
        }
    }

    @Override
    public void onScrollStart(@NonNull MeatAdapter.ViewHolder holder, int position) {
        holder.hideText(position);
    }

    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable MeatAdapter.ViewHolder currentHolder,
            @Nullable MeatAdapter.ViewHolder newHolder) {
        Meattype current = meats.get(currentIndex);
        if (newIndex >= 0 && newIndex < cityPicker.getAdapter().getItemCount()) {
            Meattype next = meats.get(newIndex);
            //meatView.onScroll(1f - Math.abs(position), current, next);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_left_button:
                finish();
                break;
            case R.id.guidebutton:
                if(numberOfBoundDevices != 0) {
                    transfer = meatView.getdata();
                    createDeviceMenu();
                }
                else {
                    Intent intent = new Intent(GuideActivity.this, ConnectWifiActivity.class);
                    startActivity(intent);
                }
                break;
            //case R.id.home:
            //    finish();
            //    break;
            //case R.id.btn_transition_time:
             //   DiscreteScrollViewOptions.configureTransitionTime(cityPicker);
            //    break;
            //case R.id.btn_smooth_scroll:
            //    DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(cityPicker, v);
            //    break;
        }
    }

    @Override
    public void onScrollEnd(@NonNull MeatAdapter.ViewHolder holder, int position) {

    }

    /* This is the new code For going to device */

     @Override
    protected void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
        //if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS)
        //    hideDialog();
        count = deviceList.size();
        //listofdevices = bindView(R.id.device_manager);
        todevices = bindView(R.id.connect_device);
        if (deviceList != null && deviceList.size() > 0) {
            mDeviceList.clear();
            deviceName.clear();
            devicer.clear();
            for (int i = 0; i < deviceList.size(); i++) {
                String name2 = deviceList.get(i).getAlias();
                if (name2 == null || name2.length() < 1)
                    name2 = deviceList.get(i).getProductName();
                if (name2.equalsIgnoreCase("智能水煮机") || name2.equalsIgnoreCase("SousVideCooker"))
                    name2 = "Sous Vide Cooker" + "(" + deviceList.get(i).getMacAddress().substring(deviceList.get(i).getMacAddress().length() - 3, deviceList.get(i).getMacAddress().length()) + ")";
                devicer.add(name2);
                if (deviceList.get(i).isBind() && deviceList.get(i).isOnline()) {
                    mDeviceList.add(deviceList.get(i));
                    String name = deviceList.get(i).getAlias();
                    //String temp = deviceList.get(i)
                    if (name == null || name.length() < 1)
                        name = deviceList.get(i).getProductName();
                    if (name.equalsIgnoreCase("智能水煮机") || name.equalsIgnoreCase("SousVideCooker"))
                        name = "Sous Vide Cooker" + "(" + deviceList.get(i).getMacAddress().substring(deviceList.get(i).getMacAddress().length() - 3, deviceList.get(i).getMacAddress().length()) + ")";
                    deviceName.add(name);
                }
                if(deviceList.get(i).isBind()) {
                    numberOfBoundDevices++;
                }
            }
        }
        if(deviceList.size() == 0){
            numberOfBoundDevices = 0;
        }
        /*else{
            numberOfBoundDevices = 0;
            for(int i = 0; i < deviceList.size(); i++){
                if(deviceList.get(i).isBind()){
                    numberOfBoundDevices++;
                }
            }
        } */
        if(numberOfBoundDevices == 0){
            guidebtn.setText(getString(R.string.add_devices));
        } else {
            guidebtn.setText(getString(R.string.send_to));
        }
        conn  = "Connected Devices:\n";
        preparedevicedata();
    }

    private void createDeviceMenu() {
        if (mDeviceList.size() == 0) {
            chooseDeviceDialog = new CircleDialog.Builder(this)
                    .configDialog(new ConfigDialog() {
                        @Override
                        public void onConfig(DialogParams params) {
                            params.animStyle = R.style.BottomPop;
                            params.gravity = Gravity.CENTER;
                            params.backgroundColor = R.color.default_blue_light;
                            params.mPadding = new int[]{DeviceMenuPadding, 0, DeviceMenuPadding, 0};
                        }
                    })
                    .setText(getString(R.string.toast_data_empty))
                    .setNegative(getString(R.string.base_cancel), null)
                    .configNegative(new ConfigButton() {
                        @Override
                        public void onConfig(ButtonParams params) {
                            params.textColor = Color.parseColor("#9a1f29");
                        }
                    })
                    .show();
        } else {
            chooseDeviceDialog = new CircleDialog.Builder(this)
                    .configDialog(new ConfigDialog() {
                        @Override
                        public void onConfig(DialogParams params) {
                            params.animStyle = R.style.BottomPop;
                            params.gravity = Gravity.CENTER;
                            params.mPadding = new int[]{DeviceMenuPadding, 0, DeviceMenuPadding, 0};
                        }
                    })
                    .setItems(deviceName, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            doChooseDevice(position);
                        }

                    })
                    .setNegative(getString(R.string.base_cancel), null)
                    .configNegative(new ConfigButton() {
                        @Override
                        public void onConfig(ButtonParams params) {
                            params.textColor = Color.parseColor("#9a1f29");
                        }
                    })
                    .show();
        }

    }

    private void doChooseDevice(int position) {
        transfer = new Transfer(0,0,0);
        transfer = meatView.getdata();
        GizWifiDevice gizWifiDevice = mDeviceList.get(position);
        Intent intent = new Intent(this, DeviceContentActivity.class);
        intent.putExtra("device", gizWifiDevice);
        intent.putExtra("guide", transfer);
        startActivity(intent);
        chooseDeviceDialog.dismiss();
    }

    private void preparedevicedata(){
        //  Swipe swipe;
        for(int i = 0; i < devicer.size();i++){
            // swipe = new Swipe(devicer.get(i));
            //swipeitem.add(swipe);
            conn = conn + devicer.get(i) + "\n";
        }
       // devicelist.setText(conn);
        // swipeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStop = false;
        mHandler.sendEmptyMessage(GETBOUNDLIST);
        if(numberOfBoundDevices != 0){
            guidebtn.setText(getString(R.string.send_to));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
