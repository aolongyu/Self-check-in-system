package com.example.demo11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.demo11.myClass.SingletonUserInfo;

import org.apache.log4j.chainsaw.Main;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class SignInActivity extends AppCompatActivity implements AMapLocationListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    private String now_lon;
    private String now_lat;
    private String now_address;
    private String now_time;

    private TextView mTxt_addrss;
    private TextView mTxt_lat;
    private TextView mTxt_lon;
    private TextView mTxt_time;
    private Button mBtn_signin_do;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"权限已申请";
                showLocation();
            }
        }
    }

    // TODO:
    private void showLocation() {
        try {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener((AMapLocationListener) this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    //获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i("定位类型", amapLocation.getLocationType() + "");
                    Log.i("获取纬度", amapLocation.getLatitude() + "");
                    Log.i("获取经度", amapLocation.getLongitude() + "");
                    Log.i("获取精度信息", amapLocation.getAccuracy() + "");

                    //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i("地址", amapLocation.getAddress());
                    Log.i("国家信息", amapLocation.getCountry());
                    Log.i("省信息", amapLocation.getProvince());
                    Log.i("城市信息", amapLocation.getCity());
                    Log.i("城区信息", amapLocation.getDistrict());
                    Log.i("街道信息", amapLocation.getStreet());
                    Log.i("街道门牌号信息", amapLocation.getStreetNum());
                    Log.i("城市编码", amapLocation.getCityCode());
                    Log.i("地区编码", amapLocation.getAdCode());
                    Log.i("获取当前定位点的AOI信息", amapLocation.getAoiName());
                    Log.i("获取当前室内定位的建筑物Id", amapLocation.getBuildingId());
                    Log.i("获取当前室内定位的楼层", amapLocation.getFloor());
                    Log.i("获取GPS的当前状态", amapLocation.getGpsAccuracyStatus() + "");

                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());

                    Log.i("获取定位时间", df.format(date));

                    //提取数据
                    now_lat = String.valueOf(amapLocation.getLatitude());
                    now_lon = String.valueOf(amapLocation.getLongitude());
                    now_address = amapLocation.getAddress();
                    now_time = df.format(date);

                    //控件绑定
                    mTxt_lon = findViewById(R.id.mTxt_lon);
                    mTxt_lat = findViewById(R.id.mTxt_lat);
                    mTxt_addrss = findViewById(R.id.mTxt_address);
                    mTxt_time = findViewById(R.id.mTxt_time);
                    mBtn_signin_do = findViewById(R.id.mBtn_signin_do);

                    mTxt_lon.setText(now_lon);
                    mTxt_lat.setText(now_lat);
                    mTxt_addrss.setText(now_address);
                    mTxt_time.setText(now_time);

                    mBtn_signin_do.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Bundle bundle=getIntent().getExtras();
                            String cid=bundle.getString("cid");

                            String sqlStr = "0 select * from signin, elective where signin.cid = elective.cid and signin.stuid = '"+SingletonUserInfo.getUid()+"' and elective.cid = '"+cid+"' and signin.ctime between date_add(elective.ctime, interval-10 minute) and elective.ctime and now() between date_add(elective.ctime, interval-10 minute) and elective.ctime";
                            sqlStr = sqlStr.replace(" ", "!");
                            MainActivity.CC.setSendMsg(sqlStr);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String recvMsg = MainActivity.CC.getRecvMsg();
                            if(recvMsg.equals("not_exist")) {
                                sqlStr = "1 insert into signin values('" + cid + "',now(),'" + SingletonUserInfo.getUid() + "','" + now_address + "')";
                                sqlStr = sqlStr.replace(" ", "!");
                                MainActivity.CC.setSendMsg(sqlStr);
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                recvMsg = MainActivity.CC.getRecvMsg();
                                new AlertDialog.Builder(
                                        SignInActivity.this)
                                        .setTitle("提示")
                                        .setMessage("签到成功")
                                        .setPositiveButton("确定", null)
                                        .show();
                                Intent intent = new Intent(SignInActivity.this, IndexActivity.class);
                                startActivity(intent);
                            } else {
                                new AlertDialog.Builder(
                                        SignInActivity.this)
                                        .setTitle("提示")
                                        .setMessage("您已签到，请勿重复")
                                        .setPositiveButton("确定", null)
                                        .show();
                            }
                        }
                    });

                    // 停止定位
                    mlocationClient.stopLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
        }
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    @Override
    protected void onDestroy() {
        destroyLocation();
        super.onDestroy();
    }
}
