package com.sbb.tanxin.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sbb.tanxin.R;

public class MyEditTextWatcher implements TextWatcher {
	private EditText edit;
	private Context mContext;
	private OnTextLengthChange mOnTextLengthChange;
	private int beforeLen = 0;
	private int afterLen = 0;

	public MyEditTextWatcher(EditText edit, Context context,
			OnTextLengthChange mOnTextLengthChange) {
		this.edit = edit;
		mContext = context;
		this.mOnTextLengthChange = mOnTextLengthChange;

	}

	public String removeAllSpace(String str) {
		String tmpstr = str.replace(" ", "");
		return tmpstr;
	}

	@Override
	public void afterTextChanged(Editable s) {
		String str = s.toString();
		if (str.length() > 0) {
			Drawable del = mContext.getResources().getDrawable(R.drawable.del);
			del.setBounds(0, 0, del.getMinimumWidth(), del.getMinimumHeight());
			edit.setCompoundDrawables(null, null, del, null);
			if (mOnTextLengthChange != null) {
				mOnTextLengthChange.onTextLengthChanged(false);
			}
		} else {
			edit.setCompoundDrawables(null, null, null, null);
			if (mOnTextLengthChange != null) {
				mOnTextLengthChange.onTextLengthChanged(true);
			}
		}
		if ("phone_num".equals((String) edit.getTag()))
			afterLen = str.length();
		if (afterLen > beforeLen) {
			if (str.length() == 4 || str.length() == 9) {
				edit.setText(new StringBuffer(str)
						.insert(str.length() - 1, " ").toString());
				edit.setSelection(edit.getText().length());
			}
		} else {
			if (str.startsWith(" ")) {
				edit.setText(new StringBuffer(str).delete(afterLen - 1,
						afterLen).toString());
				edit.setSelection(edit.getText().length());
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		beforeLen = s.length();

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	public OnTextLengthChange getmOnTextLengthChange() {
		return mOnTextLengthChange;
	}

	public void setmOnTextLengthChange(OnTextLengthChange mOnTextLengthChange) {
		this.mOnTextLengthChange = mOnTextLengthChange;
	}

	public interface OnTextLengthChange {
		void onTextLengthChanged(boolean isBlank);
	}
}
