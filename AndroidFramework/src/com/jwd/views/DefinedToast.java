package com.jwd.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class DefinedToast extends TextView {
	public final static int TYPE_SUCCESS = 1;
	public final static int TYPE_FAILED = -1;
	public final static int TYPE_NOTHING = 0;
	private boolean isShowing = false;
	private Context context;
	private int A_duration = 400;
	private int show_duration = 2000;
	private final static int SHOW_POP = 7070;
	private final static int CLOSE_POPUP = 7071;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_POP:
				setVisibility(View.VISIBLE);
				break;
			case CLOSE_POPUP:
				disToast();
				break;
			default:
				break;
			}
		}

	};

	public DefinedToast(Context context) {
		super(context);
		this.context = context;
	}

	public DefinedToast(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void toastResult(String msg, int type) {
		if (isShowing) {
			return;
		} else {
			isShowing = true;
		}
		setText(msg);
		if (type == TYPE_FAILED) {
			setBackgroundColor(Color.argb(200, 187, 23, 23));
		} else if (type == 0) {
			setBackgroundColor(Color.argb(200, 0, 0, 0));
		} else if (type == 1) {
			setBackgroundColor(Color.argb(200, 11, 88, 11));
		}
		setTextSize(13);
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
				0f);
		ta.setInterpolator(new DecelerateInterpolator(2.0F));
		ta.setDuration(A_duration);
		startAnimation(ta);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(SHOW_POP);
			}
		}, A_duration);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(CLOSE_POPUP);
			}
		}, show_duration + A_duration);
	}

	private void disToast() {
		if (this != null)
			setVisibility(View.GONE);
		int A_duration = 400;
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-1.0f);
		ta.setInterpolator(new AccelerateInterpolator(2.0F));//new AccelerateInterpolator()
		ta.setDuration(A_duration);
		startAnimation(ta);
		try {
			Thread.sleep(A_duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isShowing = false;
	}
}
