package com.sbb.tanxin.register;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sbb.tanxin.R;
import com.sbb.tanxin.data.User;
import com.sbb.tanxin.data.enums.RetError;
import com.sbb.tanxin.interfaces.AbstractTaskPostCallBack;
import com.sbb.tanxin.interfaces.MyEditTextWatcher;
import com.sbb.tanxin.interfaces.MyEditTextWatcher.OnTextLengthChange;
import com.sbb.tanxin.interfaces.OnEditFocusChangeListener;
import com.sbb.tanxin.task.CheckVerifyCodeTask;
import com.sbb.tanxin.utils.DialogUtil;
import com.sbb.tanxin.utils.ToastUtil;
import com.sbb.tanxin.view.MyEditTextDeleteImg;

public class RegisterCheckVerifyCode extends RegisterStep implements
		OnClickListener, OnTextLengthChange {
	private Button btn_next;
	private MyEditTextDeleteImg edit_code;
	private Button btn_get_code;
	private Dialog dialog;

	public RegisterCheckVerifyCode(RegisterActivity activity,
			View contentRootView) {
		super(activity, contentRootView);
	}

	@Override
	public void initView() {
		btn_next = (Button) findViewById(R.id.btnNext);
		edit_code = (MyEditTextDeleteImg) findViewById(R.id.edit_verify_code);
		btn_get_code = (Button) findViewById(R.id.btn_get_code);
		btn_get_code.setEnabled(true);

	}

	public void setText(int second) {
		btn_get_code.setText("(" + second + ")秒后重新获取验证码");
		if (second <= 0) {
			btn_get_code.setText("重新获取验证码");
			btn_get_code.setEnabled(true);
			btn_get_code.setBackgroundResource(R.drawable.btn_selector);
		}
	}

	public void setEnable() {
		btn_get_code.setEnabled(false);
		btn_get_code.setBackgroundResource(R.drawable.btn_disenable_bg);
	}

	@Override
	public void setListener() {
		btn_get_code.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		edit_code.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_code, mContext));
		edit_code.addTextChangedListener(new MyEditTextWatcher(edit_code,
				mContext, this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNext:
			String code = edit_code.getText().toString().trim();
			if (code.length() == 0) {
				ToastUtil.showToast("输入验证码", Toast.LENGTH_SHORT);
				return;
			}
			checkCode(code);
			break;
		case R.id.btn_get_code:
			btn_get_code.setEnabled(false);
			btn_get_code.setBackgroundResource(R.drawable.btn_disenable_bg);
			mActivity.postHandler();
			break;
		default:
			break;
		}
	}

	private void checkCode(String code) {
		dialog = DialogUtil.createLoadingDialog(mContext, "请稍候");
		dialog.show();
		CheckVerifyCodeTask task = new CheckVerifyCodeTask(code);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				mOnNextListener.next();
			}
		});
		User user = new User();
		user.setUser_cellphone(mActivity.getmRegister().getUser_cellphone());
		task.executeParallel(user);
	}

	@Override
	public void onTextLengthChanged(boolean isBlank) {
		if (!isBlank) {
			if (edit_code.getText().toString().length() != 0) {
				btn_next.setEnabled(true);
				btn_next.setBackgroundResource(R.drawable.btn_selector);
				return;
			}
		}
		btn_next.setEnabled(false);
		btn_next.setBackgroundResource(R.drawable.btn_disenable_bg);
	}
}
