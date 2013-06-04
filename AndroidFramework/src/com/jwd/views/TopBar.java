package com.jwd.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jwd.androidframework.R;
import com.jwd.utils.StrUtil;

public class TopBar extends RelativeLayout implements OnClickListener {
	private LayoutInflater inflater;
	private Context mContext;
	private Button button1;
	private Button button2;
	private Button button3;
	private ImageView rMessage ; 
	private ImageView i1, i2;
	private ImageButton i3;
	private static int BTN_TEXT_SIZE = 16;

	public Button getButton1() {
		return button1;
	}

	public Button getButton2() {
		return button2;
	}

	public Button getButton3() {
		return button3;
	}

	private TextView titleView;
	private ImageView star;
	private ProgressBar progress;
	private String title;

	public TopBar(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.mContext = context;
		init();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void init() {
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.layout_cloud_topbar_1, this);
		i1 = (ImageView) findViewById(R.id.button_image1);
		i2 = (ImageView) findViewById(R.id.button_image2);
		i3 = (ImageButton) findViewById(R.id.button_image3);
		titleView = (TextView) findViewById(R.id.cloud_top_text1);
		button1 = (Button) findViewById(R.id.cloud_top_left_botton1);
		button2 = (Button) findViewById(R.id.cloud_top_left_second_botton);
		button3 = (Button) findViewById(R.id.cloud_top_right_botton1);
		rMessage = (ImageView) findViewById(R.id.cloud_top_right_newmessage);
		
		button1.setTextSize(BTN_TEXT_SIZE);
		button2.setTextSize(BTN_TEXT_SIZE);
		button3.setTextSize(BTN_TEXT_SIZE);
		button1.setPadding(0, 0, 5, 0);
		button1.setGravity(Gravity.CENTER);
		progress = (ProgressBar) findViewById(R.id.cloud_top_right_progressBar);
		star = (ImageView) findViewById(R.id.star);
		titleView.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		progress.setOnClickListener(this);
		setBackgroundImage(0);
	}

	public void setBackgroundImage(int type) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.top_bg_space);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		drawable.setDither(true);
		this.setBackgroundDrawable(drawable);
		if (button1 != null) {
			button1.setBackgroundResource(R.drawable.button_default_back);
		}
		if (button2 != null) {
			button2.setBackgroundResource(R.drawable.button_default);
		}
		if (button3 != null) {
			button3.setBackgroundResource(R.drawable.button_default);
		}
	}
	/**
	 * 是否显示新消息
	 * @param visibile
	 */
	public void showNewMessage(int visibile){
		if(rMessage !=null){
			if(visibile == View.VISIBLE){
				rMessage.setVisibility(View.VISIBLE);
			}else{
				rMessage.setVisibility(View.GONE);
			}
		}
	}
	public void showConfig(String title, boolean enable1, boolean enable2,
			boolean enable3, boolean enable4) {
		titleView.setText(title);
		if (enable1) {
			button1.setVisibility(View.VISIBLE);
		} else {
			button1.setVisibility(View.GONE);
		}
		if (enable2) {
			button2.setVisibility(View.VISIBLE);
		} else {
			button2.setVisibility(View.GONE);
		}
		if (enable3) {
			button3.setVisibility(View.VISIBLE);
		} else {
			button3.setVisibility(View.GONE);
		}
		if (enable4) {
			progress.setVisibility(View.VISIBLE);
		} else {
			progress.setVisibility(View.GONE);
		}
	}

	public void showButtonImage(int res1, int res2, int res3, boolean withBtnBg) {
		if (withBtnBg) {
			i3.setBackgroundResource(R.drawable.button_default);
		}else{
			i3.setBackgroundColor(Color.TRANSPARENT);
		}
		showButtonImage(res1, res2, res3);
	}
	public void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
                        brightness,// 改变亮度
                        0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
	}
	public void showButtonImage(int res1, int res2, int res3) {
		if (res1 > 0) {
			i1.setOnClickListener(this);
			i1.setImageResource(res1);
			i1.setVisibility(VISIBLE);
			button1.setVisibility(View.GONE);
		}
		if (res2 > 0) {
			i2.setOnClickListener(this);
			i2.setImageResource(res2);
			i2.setVisibility(VISIBLE);
			button2.setVisibility(View.GONE);
		}
		if (res3 > 0) {
			i3.setOnClickListener(this);
			i3.setVisibility(View.VISIBLE);
			i3.setImageResource(res3);
			button3.setVisibility(View.GONE);
		}
		if (res1 == 0 && res2 == 0 && res3 == 0) {
			i1.setVisibility(View.GONE);
			i2.setVisibility(View.GONE);
			i3.setVisibility(View.GONE);
			if(button1.getText() != ""){
				button1.setVisibility(VISIBLE);
			}
			if(button2.getText() != ""){
				button2.setVisibility(VISIBLE);
			}
			if(button3.getText() != ""){
				button3.setVisibility(VISIBLE);
			}
		}
	}

	public void showButtonText(String text1, String text2, String text3) {
		if (StrUtil.isNotEmpty(text1)) {
			button1.setText(text1);
		}
		if (StrUtil.isNotEmpty(text2)) {
			button2.setText(text2);
		}
		if (StrUtil.isNotEmpty(text3)) {
			button3.setText(text3);
		}
	}

	public void showStar(boolean enable) {
		if (enable) {
			star.setVisibility(View.VISIBLE);
		} else {
			star.setVisibility(View.GONE);
		}
	}

	public void showButton1(boolean enable) {
		if (enable) {
			button1.setVisibility(View.VISIBLE);
		} else {
			button1.setVisibility(View.GONE);
		}
	}

	public TopBarListener getmListener() {
		return mListener;
	}

	public void setmListener(TopBarListener mListener) {
		this.mListener = mListener;
	}

	public void showButton2(boolean enable) {
		if (enable) {
			button2.setVisibility(View.VISIBLE);
		} else {
			button2.setVisibility(View.GONE);
		}
	}

	public void showButton3(boolean enable) {
		if (enable) {
			button3.setVisibility(View.VISIBLE);
		} else {
			button3.setVisibility(View.GONE);
		}
	}

	public void showProgressBar(boolean enable) {
		if (enable) {
			progress.setVisibility(View.VISIBLE);
			button3.setVisibility(View.GONE);
			i3.setVisibility((View.GONE));
		} else {
			progress.setVisibility(View.GONE);
			if (i3.getDrawable() != null) {
				i3.setVisibility((View.VISIBLE));
			} else if (StrUtil.isNotEmpty(button3.getText().toString())) {
				button3.setVisibility(View.VISIBLE);
			}
		}
	}

	public void imageConfig(int res1, int res2, int res3) {
		if (res1 > 0)
			button1.setBackgroundResource(res1);
		if (res2 > 0)
			button2.setBackgroundResource(res2);
		if (res3 > 0)
			button3.setBackgroundResource(res3);
	}

	public void imageConfig(String info1, String info2, String info3) {
		if (!"".equals(info1))
			button1.setText(info1);
		if (!"".equals(info2))
			button2.setText(info2);
		if (!"".equals(info3))
			button3.setText(info3);
	}

	public void performButton1Click(View view) {
		if (mListener != null) {
			mListener.onButton1Click(view);
		}
	}

	public void performButton2Click(View view) {
		if (mListener != null) {
			mListener.onButton2Click(view);
		}
	}

	public void performButton3Click(View view) {
		if (mListener != null) {
			mListener.onButton3Click(view);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == titleView.getId()) {
		} else if (v.getId() == i1.getId()) {
			performButton1Click(i1);
		} else if (v.getId() == i2.getId()) {
			performButton2Click(i2);
		} else if (v.getId() == i3.getId()) {
			performButton3Click(i3);
		} else if (v.getId() == button1.getId()) {
			performButton1Click(button1);
		} else if (v.getId() == button2.getId()) {
			performButton2Click(button2);
		} else if (v.getId() == button3.getId()) {
			performButton3Click(button3);
		}
	}

	private TopBarListener mListener;

	public interface TopBarListener {
		public void onButton1Click(View view);

		public void onButton2Click(View view);

		public void onButton3Click(View view);
	}

}
