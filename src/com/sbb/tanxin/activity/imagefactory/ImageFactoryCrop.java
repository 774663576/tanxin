package com.sbb.tanxin.activity.imagefactory;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.sbb.tanxin.R;
import com.sbb.tanxin.utils.PhotoUtils;
import com.sbb.tanxin.view.CropImage;
import com.sbb.tanxin.view.CropImageView;

public class ImageFactoryCrop extends ImageFactory {
	public static final int SHOW_PROGRESS = 0;
	public static final int REMOVE_PROGRESS = 1;
	private CropImageView mCivDisplay;

	private String mPath;
	private Bitmap mBitmap;
	private CropImage mCropImage;

	public ImageFactoryCrop(ImageFactoryActivity activity, View contentRootView) {
		super(activity, contentRootView);
	}

	@Override
	public void initViews() {
		mCivDisplay = (CropImageView) findViewById(R.id.imagefactory_crop_civ_display);
	}

	@Override
	public void initEvents() {

	}

	public void init(String path, int w, int h) {
		mPath = path;
		mBitmap = PhotoUtils.createBitmap(mPath, w, h);
		if (mBitmap != null) {
			resetImageView(mBitmap);
		}
	}

	private void resetImageView(Bitmap b) {
		mCivDisplay.clear();
		mCivDisplay.setImageBitmap(b);
		mCivDisplay.setImageBitmapResetBase(b, true);
		mCropImage = new CropImage(mContext, mCivDisplay, handler);
		mCropImage.crop(b);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_PROGRESS:
				break;
			case REMOVE_PROGRESS:
				handler.removeMessages(SHOW_PROGRESS);
				break;
			}
		}

	};

	public void Rotate() {
		if (mCropImage != null) {
			mCropImage.startRotate(90.f);
		}
	}

	public Bitmap cropAndSave() {
		return mCropImage.cropAndSave();
	}

}
