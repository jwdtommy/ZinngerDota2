package com.jwd.views;

import java.io.File;
import java.util.Date;

import com.jwd.androidframework.R;
import com.jwd.utils.DateUtil;
import com.jwd.utils.StrUtil;
import com.jwd.utils.UserUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉ListView
 * 
 * @author yanyi
 * 
 */
public class PushListView extends ListView implements OnScrollListener {
	public final static int RELEASE_To_REFRESH = 0;
	public final static int PULL_To_REFRESH = 1;
	public final static int REFRESHING = 2;
	public final static int DONE = 3;
	public final static int LOADING = 4;

	public final static int RATIO = 3;
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isRecored;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	public int state;
	private boolean isBack;
	private View headerEmptyView;
	private OnRefreshListener refreshListener;
	private OnRefreshCompleteListener refreshCompleteListener;
	private boolean isRefreshable;
	private final String formatStr = "yyyy-MM-dd HH:mm";
	private LinearLayout loadingLayout;
	private TextView loadView;
	int i = 1;
	private int lastItem;
	private ImageView emptyShoutList;
	// 防止footer点击多次后发送请求
	private boolean isFooterRuning = false;
	private ProgressBar fprogressBar;

	public PushListView(Context context) {
		super(context);
		init(context);
	}

	public PushListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		setCacheColorHint(context.getResources().getColor(R.color.transparent));
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(
				R.layout.layout_push_listview_header, null);
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
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
		isRefreshable = false;
	}

	/**
	 * 显示footer
	 */
	public void showFooter() {
		if (loadingLayout != null)
			loadingLayout.setVisibility(View.VISIBLE);
	}

	/**
	 * 去除footer
	 */
	public void displayFooter() {
		if (loadingLayout != null)
			loadingLayout.setVisibility(View.GONE);
	}

	public void initAllModule() {
		initEmptyHeader();
		initFooter();
	}

	/**
	 * 添加提示emptyView
	 */
	public void initEmptyHeader() {
		headerEmptyView = LayoutInflater.from(context).inflate(
				R.layout.list_empty_header, null);
		emptyShoutList = (ImageView) headerEmptyView
				.findViewById(R.id.emptyimgview);
		if (emptyShoutList != null) {
			UserUtil.loadingView(emptyShoutList, true);
			addHeaderView(headerEmptyView, null, false);
		}
	}

	/**
	 * 添加底部footer
	 */
	public void initFooter() {
		setFooterDividersEnabled(false);
		loadingLayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.seemorelayout, null);
		loadingLayout.setVisibility(View.GONE);
		fprogressBar = (ProgressBar) loadingLayout
				.findViewById(R.id.seeMore_progressBar);
		loadView = (TextView) loadingLayout.findViewById(R.id.seeMoreText);
		loadView.setText("更多");
		loadingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (onFooterClickListener != null && !isFooterRuning) {
					isFooterRuning = true;
					onFooterRefreshing();
					onFooterClickListener.onFooterClick(v);
				}
			}
		});
		addFooterView(loadingLayout);
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem,
			int visibleItemCount, int arg3) {
		firstItemIndex = firstVisiableItem;
		lastItem = firstVisiableItem + visibleItemCount
				- this.getHeaderViewsCount() - this.getFooterViewsCount();
	}

	public interface OnScrollStateChangListener {
		public void onScrollStateChanged(AbsListView arg0, int scrollState,
				int lastItem);
	}

	public interface OnFooterClickListener {
		public void onFooterClick(View view);
	}

	private OnScrollStateChangListener onScrollStateChangListener;
	private OnFooterClickListener onFooterClickListener;

	public OnScrollStateChangListener getOnScrollStateChangListener() {
		return onScrollStateChangListener;
	}

	public void setOnScrollStateChangListener(
			OnScrollStateChangListener onScrollStateChangListener) {
		this.onScrollStateChangListener = onScrollStateChangListener;
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		if (onScrollStateChangListener != null) {
			onScrollStateChangListener.onScrollStateChanged(arg0, arg1,
					lastItem);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}
				isRecored = false;
				isBack = false;
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						} else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}
					if (state == PULL_To_REFRESH) {
						setSelection(0);
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						} else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);
					}
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	public void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);
			tipsTextview.setText("松开刷新");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			break;
		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("刷新中...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			setSelection(0);
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			tipsTextview.setText("刷新完成");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public interface OnRefreshIngListener {
		public void onRefreshIng();
	}

	public interface OnRefreshCompleteListener {
		public void onRefreshCompleter(boolean isLocal, int newCount);
	}

	public void setOnRefreshCompleteListener(
			OnRefreshCompleteListener refreshCompleteListener) {
		this.refreshCompleteListener = refreshCompleteListener;
	}

	public void onRefreshComplete() {
		// if(getAdapter() != null)
		// Log.v("list", "onRefreshComplete coun = "+
		// (getAdapter().getCount()-getHeaderViewsCount() -
		// getFooterViewsCount() > 0));
		if (getAdapter() != null
				&& (getAdapter().getCount() - getHeaderViewsCount()
						- getFooterViewsCount() > 0)) {
			if(progressBar.getVisibility() == View.VISIBLE){
				TranslateAnimation ta = new TranslateAnimation(0, 0,
						headContentHeight, Animation.RELATIVE_TO_SELF);
				ta.setDuration(300);
				ta.setInterpolator(new DecelerateInterpolator(2.5f));
				ta.setFillAfter(true);
				startAnimation(ta);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			removeHeaderView(headerEmptyView);
		} else {
			UserUtil.loadingView(emptyShoutList, false);
		}
		onRefreshComplete(true, 0);
	}

	public void onRefreshComplete(boolean isLocal, int newCount) {
		state = DONE;
		lastUpdatedTextView.setText("上次刷新于: "
				+ DateUtil.formatDate(new Date(), formatStr));
		changeHeaderViewByState();
		if (refreshCompleteListener != null) {
			refreshCompleteListener.onRefreshCompleter(isLocal, newCount);
		}
	}

	public void onRefreshing() {
		state = REFRESHING;
		lastUpdatedTextView.setText("上次刷新于: "
				+ DateUtil.formatDate(new Date(), formatStr));
		changeHeaderViewByState();
	}

	public void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * 底部更新完成
	 */
	public void onFooterRefreshComplete() {
		isFooterRuning = false;
		if (fprogressBar != null)
			fprogressBar.setVisibility(View.GONE);
		if (loadView != null)
			loadView.setText("更多");
	}

	/**
	 * 底部正在更新
	 */
	public void onFooterRefreshing() {
		if (fprogressBar != null)
			fprogressBar.setVisibility(View.VISIBLE);
		if (loadView != null)
			loadView.setText("加载中...");
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
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

	public void setAdapter(BaseAdapter adapter) {
		setAdater(adapter, "");
	}

	public void setAdater(BaseAdapter adapter, String filePath) {
		if (StrUtil.isNotEmpty(filePath)) {
			try {
				File file = new File(filePath);
				long modifiTime = file.lastModified();
				String time = DateUtil.formatDate(new Date(modifiTime),
						formatStr);
				lastUpdatedTextView.setText("上次刷新于:" + time);
			} catch (Exception e) {
			}
		}
		super.setAdapter(adapter);
	}

	public OnFooterClickListener getOnFooterClickListener() {
		return onFooterClickListener;
	}

	public void setOnFooterClickListener(
			OnFooterClickListener onFooterClickListener) {
		this.onFooterClickListener = onFooterClickListener;
	}

}