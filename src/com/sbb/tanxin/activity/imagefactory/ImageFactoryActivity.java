package com.sbb.tanxin.activity.imagefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sbb.tanxin.R;
import com.sbb.tanxin.activity.BaseActivity;
import com.sbb.tanxin.utils.PhotoUtils;
import com.sbb.tanxin.utils.Utils;
import com.sbb.tanxin.view.CropImageView;

public class ImageFactoryActivity extends BaseActivity {
	private Button mBtnLeft;
	private Button mBtnRight;
	private CropImageView cropImageView;
	private ImageFactoryCrop mImageFactoryCrop;

	private String mPath;
	private String mNewPath;
	private int mIndex = 0;
	private String mType;
	public static final String TYPE = "type";
	public static final String CROP = "crop";
	public static final String FLITER = "fliter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagefactory);
		initViews();
		initEvents();
		init();
	}

	protected void initViews() {
		cropImageView = (CropImageView) findViewById(R.id.imagefactory_crop_civ_display);
		mBtnLeft = (Button) findViewById(R.id.imagefactory_btn_left);
		mBtnRight = (Button) findViewById(R.id.imagefactory_btn_right);
	}

	protected void initEvents() {
		mBtnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIndex == 0) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					if (FLITER.equals(mType)) {
						setResult(RESULT_CANCELED);
						finish();
					} else {
						mIndex = 0;
						initImageFactory();
					}
				}
			}
		});
		mBtnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mNewPath = PhotoUtils.savePhotoToSDCard(mImageFactoryCrop
						.cropAndSave());
				Intent intent = new Intent();
				intent.putExtra("path", mNewPath);
				setResult(RESULT_OK, intent);
				finish();

			}
		});
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();

	}

	private void init() {
		mPath = getIntent().getStringExtra("path");
		mType = getIntent().getStringExtra(TYPE);
		mNewPath = new String(mPath);
		if (CROP.equals(mType)) {
		}
		initImageFactory();
	}

	private void initImageFactory() {
		if (mImageFactoryCrop == null) {
			mImageFactoryCrop = new ImageFactoryCrop(this, cropImageView);
		}
		mImageFactoryCrop.init(mPath, Utils.getSecreenWidth(this),
				Utils.getSecreenHeight(this));
		mBtnLeft.setText("取    消");
		mBtnRight.setText("确    认");

	}

}
