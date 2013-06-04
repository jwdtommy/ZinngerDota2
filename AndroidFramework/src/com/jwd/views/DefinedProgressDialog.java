package com.jwd.views;
import com.jwd.androidframework.R;
import com.jwd.utils.StrUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * @author yanyi
 */
public class DefinedProgressDialog extends Dialog {
	private Context mContext;
	private ProgressBar progressbar;
	private TextView message;
	private ImageView result_info;
	private final static int DISSMIS_FLAG = 10;
	private final static int DISSMIS_HOLDING = 1000;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DISSMIS_FLAG:
				if (mContext != null && this != null && isShowing()) {
					dismiss();
				}
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	public DefinedProgressDialog(Context context, String messageStr) {
		super(context,R.style.dialog);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.lx_progressdialog, null);
		progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
		result_info = (ImageView) view.findViewById(R.id.result_info);
		message = (TextView) view.findViewById(R.id.dialog_text);
		message.setText(messageStr);
		setContentView(view);
	}

	public void showSuccess(String str) {
		if (mContext != null && !this.isShowing()) {
			this.show();
		}
		progressbar.setVisibility(View.GONE);
		result_info.setImageResource(R.drawable.dialog_success);
		result_info.setVisibility(View.VISIBLE);
		if (StrUtil.isEmpty(str)) {
			message.setText("操作成功");
		} else {
			message.setText(str);
		}
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(DISSMIS_FLAG);
			}
		}, DISSMIS_HOLDING);
	}

	public void showFailed(String str) {
		if (mContext != null && !this.isShowing()) {
			this.show();
		}
		progressbar.setVisibility(View.GONE);
		result_info.setImageResource(R.drawable.dialog_failed);
		result_info.setVisibility(View.VISIBLE);
		if (StrUtil.isEmpty(str)) {
			message.setText("操作失败");
		} else {
			message.setText(str);
		}
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(DISSMIS_FLAG);
			}
		}, DISSMIS_HOLDING);
	}

	public void showInfo(String str) {
		if (mContext != null && !this.isShowing()) {
			this.show();
		}
		progressbar.setVisibility(View.GONE);
		result_info.setImageResource(R.drawable.dialog_info);
		result_info.setVisibility(View.VISIBLE);
		message.setText(str);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(DISSMIS_FLAG);
			}
		}, DISSMIS_HOLDING);
	}
}
