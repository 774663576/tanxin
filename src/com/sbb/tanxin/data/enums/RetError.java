package com.sbb.tanxin.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum RetError {
	NONE, INVALID, UNKOWN, NOT_EXIST_USER, USER_ALREADY_EXIST, WRONG_PASSWORD, NETWORK_ERROR, CIRCLE_ALERADY_DISSOLVE, SMS_CODE_ERR, KICKOUT_CIRCLE, ALERADY_IN_CIRCLE;

	public static Map<String, RetError> str2Error = new HashMap<String, RetError>();
	static {
		for (RetError err : RetError.values()) {
			str2Error.put(err.name(), err);
		}
	}
	public static Map<String, String> s2t = new HashMap<String, String>();
	static {
		s2t.put("NOT_EXIST_USER", "用户不存在");
		s2t.put("USER_ALREADY_EXIST", "用户已存在");
		s2t.put("WRONG_PASSWORD", "密码不正确");
		s2t.put("NETWORK_ERROR", "网络错误,请检查网络");
		s2t.put("CIRCLE_ALERADY_DISSOLVE", "该圈子已经解散");
		s2t.put("SMS_CODE_ERR", "验证码错误");
		s2t.put("KICKOUT_CIRCLE", "您已经被踢出该圈子");
		s2t.put("ALERADY_IN_CIRCLE", "您已经在该圈子中,请勿重复加入");

	}

	public static RetError convert(String err) {
		if (!str2Error.containsKey(err)) {
			return RetError.UNKOWN;
		} else {
			return str2Error.get(err);
		}
	}

	public static String toText(RetError err) {
		if (s2t.containsKey(err.toString())) {
			return s2t.get(err.toString());
		}
		return "操作失败";
	}

}
