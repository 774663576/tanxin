package com.sbb.tanxin.task;

import com.sbb.tanxin.data.User;
import com.sbb.tanxin.data.enums.RetError;

public class CheckVerifyCodeTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;
	private String code = "";

	public CheckVerifyCodeTask(String code) {
		this.code = code;
	}

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.checkVerifyCode(code);
		return ret;
	}

}
