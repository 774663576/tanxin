package com.sbb.tanxin.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.sbb.tanxin.R;

/**
 * 选择图片 拍照 选择框
 * 
 * @author teeker_bin
 * 
 */
public class SelectPicPopwindow implements OnClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private Button btn_menu_1;
	private Button btn_menu_2;
	private Button btnCancle;
	private View view;
	private String fileName = "";
	private SelectOnclick mSelectOnclick;

	private RelativeLayout layout_parent;

	public void setmSelectOnclick(SelectOnclick mSelectOnclick) {
		this.mSelectOnclick = mSelectOnclick;
	}

	public SelectPicPopwindow(Context context, View v, String txt_menu_1,
			String txt_menu_2) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.select_pic_popowinow_layout, null);
		initView();
		initPopwindow();
		btn_menu_1.setText(txt_menu_1);
		btn_menu_2.setText(txt_menu_2);

	}

	private void initView() {
		layout_parent = (RelativeLayout) view.findViewById(R.id.layout_parent);
		layout_parent.getBackground().setAlpha(150);
		btnCancle = (Button) view.findViewById(R.id.btn_cancel);
		btn_menu_1 = (Button) view.findViewById(R.id.btn_menu_1);
		btn_menu_2 = (Button) view.findViewById(R.id.btn_menu_2);
		btnCancle.setOnClickListener(this);
		btn_menu_1.setOnClickListener(this);
		btn_menu_2.setOnClickListener(this);
		layout_parent.setOnClickListener(this);

	}

	/**
	 * 初始化popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// popupWindow.setAnimationStyle(R.style.AnimBottom);
	}

	/**
	 * popwindow的显示
	 */
	public void show() {
		popupWindow.showAtLocation(v, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏
	public void dismiss() {
		popupWindow.dismiss();
	}

	/**
	 * 返回拍照之后保存路径
	 */
	public String getTakePhotoPath() {
		return fileName;
	}

	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
		case R.id.btn_cancel:
			break;
		case R.id.btn_menu_1:
			mSelectOnclick.menu1_select();
			break;
		case R.id.btn_menu_2:
			mSelectOnclick.menu2_select();
			break;
		case R.id.layout_parent:
			dismiss();
			break;
		default:
			break;
		}

	}

	public interface SelectOnclick {
		void menu1_select();

		void menu2_select();

	}
}
