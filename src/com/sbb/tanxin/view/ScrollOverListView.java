package com.sbb.tanxin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sbb.tanxin.R;
import com.sbb.tanxin.utils.DateUtils;
import com.sbb.tanxin.utils.UniversalImageLoadTool;

public class ScrollOverListView extends ListView implements OnScrollListener {

	private int mLastY;

	private int mBottomPosition;
	public static boolean isScroll = false;
	private static final String TAG = "listview";

	/** �ɿ����� **/
	private final static int RELEASE_To_REFRESH = 0;
	/** �������� **/
	private final static int PULL_To_REFRESH = 1;
	/** ������ **/
	private final static int REFRESHING = 2;
	/** �� **/
	private final static int DONE = 3;
	/** ������ **/
	private final static int LOADING = 4;
	/** ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı��� **/
	private final static int RATIO = 3;

	private LayoutInflater inflater;
	/** ͷ��ˢ�µĲ��� **/
	private LinearLayout headView;
	private LinearLayout parent;
	/** ͷ����ʾ����ˢ�µȵĿؼ� **/
	private TextView tipsTextview;
	/** ˢ�¿ؼ� **/
	private TextView lastUpdatedTextView;

	/** ͷ�������� **/
	private ProgressBar progressBar;
	/** ��ʾ���� **/
	private RotateAnimation animation;
	/** ͷ��������ʾ���� **/
	private RotateAnimation reverseAnimation;
	/** ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ�� **/
	private boolean isRecored;
	/** ͷ���߶� **/
	private int headContentHeight;
	/** ��ʼ��Y���� **/
	private int startY;
	/** ��һ��item **/
	private int firstItemIndex;
	/** ״̬ **/
	private int state;

	private boolean isBack;
	/** �Ƿ�Ҫʹ������ˢ�¹��� **/
	public boolean showRefresh = true;
	private boolean showLastTime = true;
	public static boolean canRefleash = false;

	public void setShowRefresh(boolean showRefresh) {
		this.showRefresh = showRefresh;
	}

	public ScrollOverListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ScrollOverListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ScrollOverListView(Context context) {
		super(context);
		init(context);
	}

	/** ���»��ؼ� **/
	private void init(Context context) {
		mBottomPosition = 0;
		setCacheColorHint(0);
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.head, null);
		parent = (LinearLayout) headView.findViewById(R.id.parent);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);

		headContentHeight = headView.getMeasuredHeight();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		/** �б�����ͷ�� **/
		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);

		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
	}

	public void setParentLayBg(int id) {
		parent.setBackgroundColor(id);
	}

	/** �����¼��Ĵ��� **/
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		final int y = (int) ev.getRawY();
		cancelLongPress();
		switch (action) {
		case MotionEvent.ACTION_DOWN: { // ���µ�ʱ��
			if (firstItemIndex == 0 && !isRecored) {
				isRecored = true;
				startY = (int) ev.getY();
				Log.v(TAG, "��downʱ���¼��ǰλ�á�");
			}
			// ===========================
			mLastY = y;
			final boolean isHandled = mOnScrollOverListener.onMotionDown(ev);
			if (isHandled) {
				mLastY = y;
				return isHandled;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: { // ��ָ�����ƶ���ʱ��
			int tempY = (int) ev.getY();
			if (showRefresh) {

				if (!isRecored && firstItemIndex == 0) {
					Log.v(TAG, "��moveʱ���¼��λ��");
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {

					// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�������Ļ�Ļ����������Ƶ�ʱ���б���ͬʱ���й���

					// ��������ȥˢ����
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

						}
						// һ�����Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							Log.v(TAG, "���ɿ�ˢ��״̬ת�䵽done״̬");
						}
						// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
						else {
							// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
						}
					}
					// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
					if (state == PULL_To_REFRESH) {
						setSelection(0);

						// ���������Խ���RELEASE_TO_REFRESH��״̬
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

							Log.v(TAG, "��done��������ˢ��״̬ת�䵽�ɿ�ˢ��");
						}
						// ���Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							Log.v(TAG, "��DOne��������ˢ��״̬ת�䵽done״̬");
						}
					}

					// done״̬��
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// ����headView��size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);
					}

					// ����headView��paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}
			}
			// ==============================================
			final int childCount = getChildCount();
			if (childCount == 0)
				return super.onTouchEvent(ev);
			final int itemCount = getAdapter().getCount() - mBottomPosition;
			final int deltaY = y - mLastY;
			final int lastBottom = getChildAt(childCount - 1).getBottom();
			final int end = getHeight() - getPaddingBottom();

			final int firstVisiblePosition = getFirstVisiblePosition();

			final boolean isHandleMotionMove = mOnScrollOverListener
					.onMotionMove(ev, deltaY);

			if (isHandleMotionMove) {
				mLastY = y;
				return true;
			}
			/** ����ײ� * ����ײ����¼�������һ����ִ�� **/
			if (firstVisiblePosition + childCount >= itemCount
					&& lastBottom <= end && deltaY < 0) {
				final boolean isHandleOnListViewBottomAndPullDown;
				isHandleOnListViewBottomAndPullDown = mOnScrollOverListener
						.onListViewBottomAndPullUp(deltaY);
				if (isHandleOnListViewBottomAndPullDown) {
					mLastY = y;
					return true;
				}
			}
			break;
		}

		case MotionEvent.ACTION_UP: { // ��ָ̧������ʱ��
			if (state != REFRESHING && state != LOADING) {
				if (state == DONE) {
					// ʲô������
				}
				if (state == PULL_To_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					Log.v(TAG, "������ˢ��״̬����done״̬");
				}

				if (state == RELEASE_To_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					canRefleash = true;
					Log.v(TAG, "���ɿ�ˢ��״̬����done״̬");
				}
			}

			isRecored = false;
			isBack = false;
			boolean isHandlerMotionUp = false;
			// /======================
			if (canRefleash) {
				isHandlerMotionUp = mOnScrollOverListener.onMotionUp(ev);
			}
			if (isHandlerMotionUp) {
				mLastY = y;
				return true;
			}
			break;
		}
		}
		mLastY = y;
		return super.onTouchEvent(ev);
	}

	/** �յ� */
	private OnScrollOverListener mOnScrollOverListener = new OnScrollOverListener() {

		@Override
		public boolean onListViewTopAndPullDown(int delta) {
			return false;
		}

		@Override
		public boolean onListViewBottomAndPullUp(int delta) {
			return false;
		}

		@Override
		public boolean onMotionDown(MotionEvent ev) {
			return false;
		}

		@Override
		public boolean onMotionMove(MotionEvent ev, int delta) {
			return false;
		}

		@Override
		public boolean onMotionUp(MotionEvent ev) {
			return false;
		}

	};

	// =============================== public method
	/**
	 * �����Զ�������һ����ĿΪͷ����ͷ���������¼��������Ϊ׼��Ĭ��Ϊ��һ��
	 * 
	 * @param index
	 *            �����ڼ�������������Ŀ����Χ֮��
	 */
	public void setTopPosition(int index) {
		if (getAdapter() == null)
			throw new NullPointerException(
					"You must set adapter before setTopPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Top position must > 0");
	}

	/**
	 * �����Զ�������һ����ĿΪβ����β���������¼��������Ϊ׼��Ĭ��Ϊ���һ��
	 * 
	 * @param index
	 *            �����ڼ�������������Ŀ����Χ֮��
	 */
	public void setBottomPosition(int index) {
		if (getAdapter() == null)
			throw new NullPointerException(
					"You must set adapter before setBottonPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Bottom position must > 0");

		mBottomPosition = index;
	}

	/**
	 * �������Listener���Լ����Ƿ񵽴ﶥ�ˣ������Ƿ񵽴�Ͷ˵��¼�</br>
	 * 
	 * @see OnScrollOverListener
	 */
	public void setOnScrollOverListener(
			OnScrollOverListener onScrollOverListener) {
		mOnScrollOverListener = onScrollOverListener;
	}

	/**
	 * ���������ӿ�
	 * 
	 * @see ScrollOverListView#setOnScrollOverListener(OnScrollOverListener)
	 * 
	 */
	public interface OnScrollOverListener {
		/**
		 * �����������
		 * 
		 * @param delta
		 *            ��ָ����ƶ�������ƫ����
		 * @return
		 */
		boolean onListViewTopAndPullDown(int delta);

		/**
		 * ������ײ�����
		 * 
		 * @param delta
		 *            ��ָ����ƶ�������ƫ����
		 * @return
		 */
		boolean onListViewBottomAndPullUp(int delta);

		/**
		 * ��ָ�������´������൱��{@link MotionEvent#ACTION_DOWN}
		 * 
		 * @return ����true��ʾ�Լ�����
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionDown(MotionEvent ev);

		/**
		 * ��ָ�����ƶ��������൱��{@link MotionEvent#ACTION_MOVE}
		 * 
		 * @return ����true��ʾ�Լ�����
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionMove(MotionEvent ev, int delta);

		/**
		 * ��ָ���������𴥷����൱��{@link MotionEvent#ACTION_UP}
		 * 
		 * @return ����true��ʾ�Լ�����
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionUp(MotionEvent ev);

	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
			isScroll = false;
			UniversalImageLoadTool.resume();
		} else {
			isScroll = true;
			UniversalImageLoadTool.pause();
		}
	}

	// �˷���ֱ���հ��������ϵ�һ������ˢ�µ�demo���˴��ǡ����ơ�headView��width�Լ�height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void onRefreshComplete() {
		state = DONE;
		if (showLastTime) {
			lastUpdatedTextView.setText("������:" + DateUtils.getCurrDateStr());
		}
		changeHeaderViewByState();
	}

	public void hideLastUpdateTime() {
		showLastTime = false;
	}

	// ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			if (showLastTime) {
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			tipsTextview.setText("�ɿ�ˢ��");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			if (showLastTime) {
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			// ����RELEASE_To_REFRESH״̬ת������
			if (isBack) {
				isBack = false;

				tipsTextview.setText("����ˢ��");
			} else {
				tipsTextview.setText("����ˢ��");
			}
			Log.v(TAG, "��ǰ״̬������ˢ��");
			break;

		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			tipsTextview.setText("����ˢ��...");
			if (showLastTime) {
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}

			Log.v(TAG, "��ǰ״̬,����ˢ��...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setText("����ˢ��");
			if (showLastTime) {
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}

			Log.v(TAG, "��ǰ״̬��done");
			break;
		}
	}

	public void setRefush() {
		state = REFRESHING;
		changeHeaderViewByState();

	}
}