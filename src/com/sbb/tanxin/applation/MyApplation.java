package com.sbb.tanxin.applation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sbb.tanxin.utils.CheckImageLoaderConfiguration;

public class MyApplation extends Application {
	private static MyApplation instance;
	private static List<Activity> activityList = new ArrayList<Activity>();
	private static double nLatitude = 0;// 维度
	private static double nLontitude = 0;// 经度
	private static String address = "";

	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;

	@Override
	public void onCreate() {
		super.onCreate();
		CheckImageLoaderConfiguration.checkImageLoaderConfiguration(this);
		instance = this;
		initBaiduLocation();

	}

	public static MyApplation getInstance() {
		return instance;
	}

	// 添加Activity到容器中
	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public static void exit(boolean flag) {
		for (int i = 0; i < activityList.size(); i++) {
			Activity activity = activityList.get(i);
			if (activity != null) {
				activity.finish();
			}
		}
		activityList.clear();
		if (flag) {
			System.exit(0);
		}

	}

	private void initBaiduLocation() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();

	}

	public static double getnLatitude() {
		return nLatitude;
	}

	public static void setnLatitude(double nLatitude) {
		MyApplation.nLatitude = nLatitude;
	}

	public static double getnLontitude() {
		return nLontitude;
	}

	public static void setnLontitude(double nLontitude) {
		MyApplation.nLontitude = nLontitude;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		MyApplation.address = address;
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			nLatitude = location.getLatitude();
			nLontitude = location.getLongitude();
			address = location.getAddrStr();
		}

	}

}
