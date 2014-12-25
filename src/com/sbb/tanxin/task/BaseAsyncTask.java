package com.sbb.tanxin.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sbb.tanxin.data.enums.RetError;
import com.sbb.tanxin.interfaces.AbstractTaskPostCallBack;
import com.sbb.tanxin.utils.ToastUtil;

public abstract class BaseAsyncTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {
	private AbstractTaskPostCallBack<Result> mCallBack;

	public void setmCallBack(AbstractTaskPostCallBack<Result> mCallBack) {
		this.mCallBack = mCallBack;
	}

	@SuppressLint("NewApi")
	public void executeParallel(Params... params) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			super.execute(params);
		} else {
			super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		}
	}

	@Override
	protected void onPostExecute(Result result) {
		if (result instanceof RetError) {
			if (result != RetError.NONE) {
				ToastUtil.showToast(RetError.toText((RetError) result),
						Toast.LENGTH_SHORT);
			}
		}
		if (mCallBack != null) {
			mCallBack.taskFinish(result);
		}
	}
}
