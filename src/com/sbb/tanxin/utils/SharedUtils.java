package com.sbb.tanxin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sbb.tanxin.applation.MyApplation;

/**
 * * SharedPreferences 的公具类
 * 
 * 
 */
public class SharedUtils {
	private static final String SP_NAME = "tanxin";
	private static SharedPreferences sharedPreferences = MyApplation
			.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
	private static Editor editor = sharedPreferences.edit();
	public static final String SP_UID = "uid";
	private static String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
	private static String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
	private static String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
	private static final String HUANXIN_USERNAME = "huanxin_username";
	private static final String HUANXIN_PWD = "huanxin_pwd";

	public static String getString(String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	public static int getInt(String key, int defaultValue) {
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();

	}

	public static long getLong(String key, long defaultValue) {
		return sharedPreferences.getLong(key, defaultValue);

	}

	public static void setLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public static void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setUid(String uid) {
		setString(SP_UID, uid);
	}

	public static String getUid() {
		return getString(SP_UID, "0");
	}

	public static int getIntUid() {
		String uid = getUid();
		if (uid.length() > 0) {
			return Integer.parseInt(uid);
		}
		return 0;
	}

	public static void setSettingMsgNotification(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgNotification() {
		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION,
				true);
	}

	public static void setSettingMsgSound(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SOUND, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgSound() {

		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_SOUND, false);
	}

	public static void setSettingMsgVibrate(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_VIBRATE, paramBoolean);
		editor.commit();
	}

	public static boolean getSettingMsgVibrate() {
		return sharedPreferences.getBoolean(SHARED_KEY_SETTING_VIBRATE, true);
	}

	public static void settingNewVersion(boolean isNewVersion) {
		editor.putBoolean("new_version", isNewVersion);
		editor.commit();
	}

	public static boolean getNewVersion() {
		return sharedPreferences.getBoolean("new_version", false);

	}

	public static void setUserName(String value) {
		editor.putString("username", value);
		editor.commit();
	}

	public String getUserName() {
		return sharedPreferences.getString("username", "");

	}

	public static String getAPPUserName() {
		return sharedPreferences.getString("app_user_name", "");

	}

	public static void setAPPUserName(String value) {
		editor.putString("app_user_name", value);
		editor.commit();
	}

	public static String getAPPUserAvatar() {
		return sharedPreferences.getString("app_user_avatar", "");

	}

	public static void setAPPUserAvatar(String value) {
		editor.putString("app_user_avatar", value);
		editor.commit();
	}

	public static String getAPPUserGender() {
		return sharedPreferences.getString("app_user_gender", "");

	}

	public static void setAPPUserGender(String value) {
		editor.putString("app_user_gender", value);
		editor.commit();
	}

	public static String getAPPUserBirthday() {
		return sharedPreferences.getString("app_user_birthday", "");

	}

	public static void setAPPUserBirthday(String value) {
		editor.putString("app_user_birthday", value);
		editor.commit();
	}

	public static String getAPPUserRegisterTime() {
		return sharedPreferences.getString("app_user_rigistertime", "");

	}

	public static void setAPPUserRegisterTime(String value) {
		editor.putString("app_user_rigistertime", value);
		editor.commit();
	}

	public static String getAPPUserDeclaration() {
		return sharedPreferences.getString("app_user_declaration", "");

	}

	public static void setAPPUserDeclaration(String value) {
		editor.putString("app_user_declaration", value);
		editor.commit();
	}

	public static String getAPPUserDescription() {
		return sharedPreferences.getString("app_user_description", "");

	}

	public static void setAPPUserChatID(String value) {
		editor.putString("app_user_chat_id", value);
		editor.commit();
	}

	public static String getAPPUserChatID() {
		return sharedPreferences.getString("app_user_chat_id", "");

	}

	public static void setAPPUserSortKey(String value) {
		editor.putString("app_user_sortkey", value);
		editor.commit();
	}

	public static String getAPPUserSortKey() {
		return sharedPreferences.getString("app_user_sortkey", "");

	}

	public static void setAPPUserDescription(String value) {
		editor.putString("app_user_description", value);
		editor.commit();
	}

	public static void setCircleMemberLastReqTime(int cid, long lastReqTime) {
		editor.putLong("circle_member_last_req_time" + cid, lastReqTime);
		editor.commit();
	}

	public static long getCircleMemberLastReqTime(int cid) {
		return sharedPreferences.getLong("circle_member_last_req_time" + cid,
				0l);

	}

	public static void setCircleMemberLocalLastReqTime(int cid, long lastReqTime) {
		editor.putLong("circle_member_local_last_req_time" + cid, lastReqTime);
		editor.commit();
	}

	public static long getCircleMemberLocalLastReqTime(int cid) {
		return sharedPreferences.getLong("circle_member_local_last_req_time"
				+ cid, 0l);

	}

	public static void setCircleLastRequestTime(long lastReqTime) {
		editor.putLong("circle_last_request_time", lastReqTime);
		editor.commit();
	}

	public static long getCircleLastRequestTime() {
		return sharedPreferences.getLong("circle_last_request_time", 0l);

	}

	public static void setGrowthLastRequestTime(String lastReqTime) {
		editor.putString("growth_last_request_time", lastReqTime);
		editor.commit();
	}

	public static String getGrowthLastRequestTime() {
		return sharedPreferences.getString("growth_last_request_time", "0");

	}

	public static void saveHXId(String hxId) {
		editor.putString(HUANXIN_USERNAME, hxId);
		editor.commit();
	}

	public static String getHXId() {
		return sharedPreferences.getString(HUANXIN_USERNAME, null);
	}

	public static void saveHuanXinPassword(String pwd) {
		editor.putString(HUANXIN_PWD, pwd);
		editor.commit();
	}

	public static String getHuanXinPwd() {
		return sharedPreferences.getString(HUANXIN_PWD, null);
	}

	public static void clearData() {
		editor.clear();
		editor.commit();
	}
}
