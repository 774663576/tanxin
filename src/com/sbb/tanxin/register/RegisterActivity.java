package com.sbb.tanxin.register;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sbb.tanxin.R;
import com.sbb.tanxin.activity.BaseActivity;
import com.sbb.tanxin.data.User;
import com.sbb.tanxin.register.RegisterStep.onNextListener;
import com.sbb.tanxin.utils.FileUtils;
import com.sbb.tanxin.utils.PhotoUtils;
import com.sbb.tanxin.utils.ToastUtil;

public class RegisterActivity extends BaseActivity implements onNextListener,
		OnClickListener {
	private ViewFlipper mVfFlipper;
	private ImageView back;
	private TextView txt_title;
	private TextView txt_page;

	private RegisterStep reStep;
	private RegisterUserName reUserName;
	private RegisterBasicInfo reBasicInfo;
	private RegisterPhone rePhone;
	private RegisterSetPassword reSetPasswd;
	private RegisterCheckVerifyCode checkCode;

	private User mRegister;

	private int mCurrentStepIndex = 1;

	private int second = 60;// 用于重新获取验证码时间倒计时

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				second--;
				if (second < 0) {
					second = 60;
					removeCallbacksAndMessages(null);
					return;
				}
				checkCode.setText(second);
				this.sendEmptyMessageDelayed(0, 1000);
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_activity);
		mRegister = new User();
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.back);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("注册");
		txt_page = (TextView) findViewById(R.id.txt_page);
		mVfFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		mVfFlipper.setDisplayedChild(0);
		reStep = initStep();
		setListener();
	}

	private void setListener() {
		reStep.setmOnNextListener(this);
		back.setOnClickListener(this);
	}

	protected User getmRegister() {
		return mRegister;
	}

	public void setmRegister(User mRegister) {
		this.mRegister = mRegister;
	}

	private RegisterStep initStep() {
		txt_page.setText(mCurrentStepIndex + "/5");
		switch (mCurrentStepIndex) {
		case 1:
			if (reUserName == null) {
				reUserName = new RegisterUserName(this,
						mVfFlipper.getChildAt(0));
			}
			return reUserName;
		case 2:
			if (reBasicInfo == null) {
				reBasicInfo = new RegisterBasicInfo(this,
						mVfFlipper.getChildAt(1));
			}
			return reBasicInfo;
		case 3:
			if (rePhone == null) {
				rePhone = new RegisterPhone(this, mVfFlipper.getChildAt(2));
			}
			return rePhone;
		case 4:
			if (checkCode == null) {
				checkCode = new RegisterCheckVerifyCode(this,
						mVfFlipper.getChildAt(3));
			}
			mHandler.sendEmptyMessage(0);
			return checkCode;
		case 5:
			if (reSetPasswd == null) {
				reSetPasswd = new RegisterSetPassword(this,
						mVfFlipper.getChildAt(4));
			}
			return reSetPasswd;
		default:
			break;
		}
		return null;
	}

	@Override
	public void next() {
		mCurrentStepIndex++;
		reStep = initStep();
		reStep.setmOnNextListener(this);
		mVfFlipper.showNext();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case PhotoUtils.INTENT_REQUEST_CODE_ALBUM:
			if (data == null) {
				return;
			}
			if (resultCode == RESULT_OK) {
				if (data.getData() == null) {
					return;
				}
				if (!FileUtils.isSdcardExist()) {
					ToastUtil.showToast("SD卡不可用,请检查", Toast.LENGTH_SHORT);
					return;
				}
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						String path = cursor.getString(column_index);
						Bitmap bitmap = BitmapFactory.decodeFile(path);
						if (PhotoUtils.bitmapIsLarge(bitmap)) {
							PhotoUtils.cropPhoto(this, this, path);
						} else {
							reBasicInfo.setUserPhoto(bitmap, path);
						}
					}
				}
			}
			break;

		case PhotoUtils.INTENT_REQUEST_CODE_CAMERA:

			if (resultCode == RESULT_OK) {

				String path = reBasicInfo.getTakePicturePath();
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				if (PhotoUtils.bitmapIsLarge(bitmap)) {
					PhotoUtils.cropPhoto(this, this, path);
				} else {
					reBasicInfo.setUserPhoto(bitmap, path);
				}
			}
			break;

		case PhotoUtils.INTENT_REQUEST_CODE_CROP:
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra("path");
				if (path != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					if (bitmap != null) {
						reBasicInfo.setUserPhoto(bitmap, path);
					}
				}
			}
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return false;
	}

	private void pre() {
		mCurrentStepIndex--;
		reStep = initStep();
		reStep.setmOnNextListener(this);
		mVfFlipper.showPrevious();
	}

	private void back() {
		if (mCurrentStepIndex == 1) {
			finishThisActivity();
		} else {
			mHandler.removeCallbacksAndMessages(null);
			second = 60;
			pre();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			back();
			break;

		default:
			break;
		}
	}

	protected void postHandler() {
		second = 60;
		mHandler.sendEmptyMessage(0);
	}

	protected void removeHandlerMessage() {
		mHandler.removeCallbacksAndMessages(null);
		second = 60;
		checkCode.setEnable();
	}
}
