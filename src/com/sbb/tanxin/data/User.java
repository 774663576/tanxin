package com.sbb.tanxin.data;

import java.io.File;
import java.util.HashMap;

import com.sbb.tanxin.data.enums.RetError;
import com.sbb.tanxin.data.enums.RetStatus;
import com.sbb.tanxin.data.result.ApiRequest;
import com.sbb.tanxin.data.result.Result;
import com.sbb.tanxin.parser.IParser;
import com.sbb.tanxin.parser.SimpleParser;
import com.sbb.tanxin.utils.BitmapUtils;

public class User {
	private static final String VERIFY_CELLPHONE_API = "VerifyCellPhoneServlet";
	private static final String USER_REGISTER_API = "UserRegisterServlet";
	private static final String USER_LOGIN_API = "UserLoginServlet";
	private static final String GET_USER_INFO = "GetUserInfoServlet";
	private static final String FIND_PASSWORD_VERIFY_CODE = "FindPasswordGetVerifyCode";
	private static final String UPDATE_USER_LOGIN_PASSWORD = "UpdateUserLoginPassword";
	private static final String CHECK_VERIFY_CODE = "CheckVerifyCodeServlet";

	private String user_cellphone = "";// 用户注册电话
	private String user_name = "";// 用户注册姓名
	private String user_avatar = "";// 用户注册头像
	private String user_gender = "";// 用户注册性别
	private String user_birthday = "";// 用户注册生日
	private String user_password = "";// 用户注册密码
	private int user_id = 0;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_cellphone() {
		return user_cellphone;
	}

	public void setUser_cellphone(String user_cellphone) {
		this.user_cellphone = user_cellphone;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	/**
	 * 验证手机号手机号是否已被注册
	 * 
	 * @return
	 */
	public RetError vefifyCellPhone() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		Result ret = ApiRequest.request(VERIFY_CELLPHONE_API, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	public RetError getFindPasswordVerifyCode() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		Result ret = ApiRequest.request(FIND_PASSWORD_VERIFY_CODE, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	public RetError userRegister() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_name", user_name);
		params.put("user_cellphone", user_cellphone);
		params.put("user_password", user_password);
		params.put("user_gender", user_gender);
		params.put("user_birthday", user_birthday);
		File file = BitmapUtils.getImageFile(user_avatar);
		Result ret = ApiRequest.requestWithFile(USER_REGISTER_API, params,
				file, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}

	}

	public RetError updateUserLoginPassword() {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_password", user_password);
		params.put("cell_phone", user_cellphone);
		Result ret = ApiRequest.request(UPDATE_USER_LOGIN_PASSWORD, params,
				parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}

	/**
	 * 用户 登录
	 * 
	 * @return
	 */
	// public RetError userLogin() {
	// IParser parser = new MemberSelfPaser();
	// HashMap<String, Object> params = new HashMap<String, Object>();
	// params.put("user_cellphone", user_cellphone);
	// params.put("user_password", user_password);
	// Result ret = ApiRequest.request(USER_LOGIN_API, params, parser);
	// if (ret.getStatus() == RetStatus.SUCC) {
	// CircleMember member = (CircleMember) ret.getData();
	// SharedUtils.setAPPUserName(member.getUser_name());
	// SharedUtils.setAPPUserAvatar(member.getUser_avatar());
	// SharedUtils.setAPPUserBirthday(member.getUser_birthday());
	// SharedUtils.setAPPUserDeclaration(member.getUser_declaration());
	// SharedUtils.setAPPUserDescription(member.getUser_description());
	// SharedUtils.setAPPUserGender(member.getUser_gender());
	// SharedUtils.setAPPUserRegisterTime(member.getUser_register_time());
	// SharedUtils.setAPPUserSortKey(member.getSortkey());
	// SharedUtils.setAPPUserChatID(member.getUser_chat_id());
	// SharedUtils.setUid(member.getUser_id() + "");
	// this.user_id = member.getUser_id();
	// return RetError.NONE;
	// } else {
	// return ret.getErr();
	// }
	//
	// }

	//
	// public RetError userLogin() {
	// IParser parser = new MemberSelfPaser();
	// HashMap<String, Object> params = new HashMap<String, Object>();
	// params.put("user_cellphone", user_cellphone);
	// params.put("user_password", user_password);
	// Result ret = ApiRequest.request(USER_LOGIN_API, params, parser);
	// if (ret.getStatus() == RetStatus.SUCC) {
	// StringResult sr = (StringResult) ret;
	// this.user_id = Integer.valueOf(sr.getStr());
	// return RetError.NONE;
	// } else {
	// return ret.getErr();
	// }

	// }
	// public RetError getUserInfo() {
	// IParser parser = new MemberSelfPaser();
	// HashMap<String, Object> params = new HashMap<String, Object>();
	// Result ret = ApiRequest.request(GET_USER_INFO, params, parser);
	// if (ret.getStatus() == RetStatus.SUCC) {
	// CircleMember member = (CircleMember) ret.getData();
	// user_avatar = member.getUser_avatar();
	// user_name = member.getUser_name();
	// SharedUtils.setAPPUserBirthday(member.getUser_birthday());
	// SharedUtils.setAPPUserDeclaration(member.getUser_declaration());
	// SharedUtils.setAPPUserDescription(member.getUser_description());
	// SharedUtils.setAPPUserGender(member.getUser_gender());
	// SharedUtils.setAPPUserRegisterTime(member.getUser_register_time());
	// SharedUtils.setAPPUserSortKey(member.getSortkey());
	// SharedUtils.setAPPUserChatID(member.getUser_chat_id());
	// return RetError.NONE;
	// } else {
	// return ret.getErr();
	// }
	// }

	/**
	 * 验证验证码是否正确
	 * 
	 * @param code
	 */
	public RetError checkVerifyCode(String code) {
		IParser parser = new SimpleParser();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_cellphone", user_cellphone);
		params.put("sms_code", code);
		Result ret = ApiRequest.request(CHECK_VERIFY_CODE, params, parser);
		if (ret.getStatus() == RetStatus.SUCC) {
			return RetError.NONE;
		} else {
			return ret.getErr();
		}
	}
}
