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
	private static double nLatitude = 0;// ά��
	private static double nLontitude = 0;// ����
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

	// ���Activity��������
	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// ��������Activity��finish
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
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("gcj02");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		option.setScanSpan(1000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
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
	 * ʵ��ʵλ�ص�����
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
