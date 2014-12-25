package com.sbb.tanxin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sbb.tanxin.R;
import com.sbb.tanxin.interfaces.MyEditTextWatcher;
import com.sbb.tanxin.interfaces.MyEditTextWatcher.OnTextLengthChange;
import com.sbb.tanxin.interfaces.OnEditFocusChangeListener;
import com.sbb.tanxin.register.RegisterActivity;
import com.sbb.tanxin.utils.DialogUtil;
import com.sbb.tanxin.utils.ToastUtil;
import com.sbb.tanxin.utils.Utils;
import com.sbb.tanxin.view.MyEditTextDeleteImg;

public class LoginActivity extends BaseActivity implements OnClickListener,
		OnTextLengthChange {
	private MyEditTextDeleteImg edit_telphone;
	private MyEditTextDeleteImg edit_password;
	private Button btn_login;
	private Button btn_find_password;
	private ImageView back;
	private TextView txt_title;

	private Dialog dialog;

	private int user_id;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// ToastUtil.showToast("µÇÂ¼³É¹¦", Toast.LENGTH_SHORT);
				// startActivity(new Intent(LoginActivity.this,
				// HomeActivity.class));
				// finish();
				break;
			case -1:
				ToastUtil.showToast("ÊÖ»úºÅ²»´æÔÚ", Toast.LENGTH_SHORT);
				break;
			case -2:
				ToastUtil.showToast("ÃÜÂë´íÎó", Toast.LENGTH_SHORT);
				break;
			case -3:
				ToastUtil.showToast("µÇÂ¼Ê§°Ü", Toast.LENGTH_SHORT);
				break;
			case 2:
				if (dialog != null) {
					dialog.dismiss();
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.back);
		txt_title = (TextView) findViewById(R.id.title_txt);
		txt_title.setText("µÇÂ¼");
		edit_password = (MyEditTextDeleteImg) findViewById(R.id.edit_password);
		edit_telphone = (MyEditTextDeleteImg) findViewById(R.id.edit_telphone);
		edit_telphone.setTag("phone_num");
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_find_password = (Button) findViewById(R.id.btn_findPasswrod);
		setListener();
	}

	private void setListener() {
		edit_telphone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_telphone, this));
		edit_telphone.addTextChangedListener(new MyEditTextWatcher(
				edit_telphone, this, this));
		edit_password.addTextChangedListener(new MyEditTextWatcher(
				edit_password, this, this));
		edit_password.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_password, this));
		btn_login.setOnClickListener(this);
		btn_find_password.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onTextLengthChanged(boolean isBlank) {
		if (!isBlank) {
			if (edit_password.getText().toString().length() != 0
					&& edit_telphone.getText().toString().length() != 0) {
				btn_login.setEnabled(true);
				btn_login.setBackgroundResource(R.drawable.btn_selector);
				return;
			}
		}
		btn_login.setEnabled(false);
		btn_login.setBackgroundResource(R.drawable.btn_disenable_bg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			String user_cellphone = edit_telphone.getText().toString()
					.replaceAll(" ", "");
			String user_password = edit_password.getText().toString();
			if (!Utils.isPhoneNum(user_cellphone)) {
				ToastUtil.showToast("ÊÖ»úºÅ¸ñÊ½²»ÕýÈ·", Toast.LENGTH_SHORT);
				return;
			}
			if (!Utils.isNetworkAvailable()) {
				ToastUtil.showToast("ÍøÂç´íÎó,Çë¼ì²éÍøÂç", Toast.LENGTH_SHORT);

				return;
			}
			dialog = DialogUtil.createLoadingDialog(this, "ÇëÉÔºò");
			dialog.setCancelable(false);
			dialog.show();
			login(user_cellphone, user_password);
			break;
		case R.id.btn_findPasswrod:
			startActivity(new Intent(this, RegisterActivity.class));
			Utils.leftOutRightIn(this);
			break;
		case R.id.back:
			finishThisActivity();
		default:
			break;
		}
	}

	private void login(final String user_cellphone, final String user_password) {
		new Thread() {
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}
}
