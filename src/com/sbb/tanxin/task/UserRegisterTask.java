package com.sbb.tanxin.task;

import com.sbb.tanxin.data.User;
import com.sbb.tanxin.data.enums.RetError;

public class UserRegisterTask extends BaseAsyncTask<User, Void, RetError> {
	private User register;

	@Override
	protected RetError doInBackground(User... params) {
		register = params[0];
		RetError ret = register.userRegister();
		return ret;
	}

}
