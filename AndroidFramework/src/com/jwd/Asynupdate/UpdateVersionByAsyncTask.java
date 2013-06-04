package com.jwd.Asynupdate;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Formatter;
import org.json.JSONObject;

import com.jwd.Constant.Constant;
import com.jwd.androidframework.R;
import com.jwd.model.LXApp;
import com.jwd.utils.DateUtil;
import com.jwd.utils.HttpUtil;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 自动更新异步任务
 * 
 * @author jiangweidong
 */
public class UpdateVersionByAsyncTask {
	private final static int UPDATE_NOTIFY = 0;
	private final static int DOWN_ERROR = 1;
	private final static int GET_UNDATAINFO_ERROR = 2;
	private final static int INSTALL_ERROR = 3;
	private final static int NO_SDCARD_ERROR = 4;
	private LXApp appInfo = null;
	private String versionName = "";
	private String versionCode = "";
	private ProgressDialog progressDialog;// 进度条对话框
	private File file;
	private Context mContext;
	private String serverVersionUrl = "";
	private int currentSize = 0;
	private int maxSize = 0;
	private String versionCodeNow = "";
	private String haftVersionCode = "-1";// 续传的版本号
	private NotificationManager nm;
	private boolean isManual;
	private boolean isSevenDayUpdate = false;
	private boolean isDownload = true;
	DownloadTask downloadTask;
	private final String DOWNLOAD_APK_NAME="temp";
	// private String dic = "";
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATE_NOTIFY: // 对话框通知用户升级程序
				showUpdataDialog();
				break;
			case GET_UNDATAINFO_ERROR:
				// 服务器超时
				Toast.makeText(mContext, "获取服务器更新信息失败", Toast.LENGTH_SHORT)
						.show();
				delTempApkWhileError();
				LoginMain();
				break;
			case DOWN_ERROR:
				// 下载apk失败
				Toast.makeText(mContext, "下载新版本失败", Toast.LENGTH_SHORT).show();
				delTempApkWhileError();
				LoginMain();
				break;
			case INSTALL_ERROR:
				Toast.makeText(mContext, "安装新版本失败", Toast.LENGTH_SHORT).show();
				delTempApkWhileError();
				LoginMain();
			case NO_SDCARD_ERROR:
				Toast.makeText(mContext, "SD卡不可用", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	// private AndroidVersionBean androidVersionBean = new AndroidVersionBean();
	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param isManual
	 *            是否是手动检测 (若是，且是最新版本则会弹出toast提示已是最新版本)
	 */
	public UpdateVersionByAsyncTask(Context context, boolean isManual) {
		this.mContext = context;
		versionCodeNow = getVersionCode();
		this.isManual = isManual;
		// dic = mContext.getFilesDir().getPath() + "/" + "file" + "/";
		// reciever=new CompeleteInstallReciever();
		// IntentFilter filter=new
		// IntentFilter("android.intent.action.PACKAGE_ADDED");
		// context.registerReceiver(reciever, filter);
	}

	// 创建完构造函数后需调用此方法检测并执行更新
	public void executeAsyncTask() {
		nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		downloadTask	 = new DownloadTask();
		// downloadTask.execute(getRequestContent());
		downloadTask.execute("http://lianxi.com/api/standard/checkUpdate.jsp");

	}

	// 服务器端获取APK信息
	public File getFileFromServer(String path, ProgressDialog pd) {
		InputStream is = null;
		// FileOutputStream fos=null;
		BufferedInputStream bis = null;
		HttpURLConnection conn = null;
		RandomAccessFile oSavedFile = null;
		try {
			// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File file = new File(Environment.getExternalStorageDirectory(),
						DOWNLOAD_APK_NAME + ".apk");
			       if (!file.exists())
                   {
			    	   file.createNewFile();
                   }
				oSavedFile = new RandomAccessFile(file, "rw");
				URL url = new URL(path);
				long start = oSavedFile.length();
				Log.i("update", "lenth:" + start);
				SharedPreferences mSharedPreferences = mContext
						.getSharedPreferences(DOWNLOAD_APK_NAME,
								Context.MODE_PRIVATE);
				haftVersionCode = mSharedPreferences.getString(
						"haftVersionCode", "-1");
				Log.i("update", "当前版本:" + versionCodeNow);
				Log.i("update", "断点版本:" + haftVersionCode);
				Log.i("update", "服务器版本:" + versionCode);
//				   conn.setConnectTimeout(5*1000);
//		            conn.setRequestMethod("GET");
//		            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//		            conn.setRequestProperty("Accept-Language", "zh-CN");
//		            conn.setRequestProperty("Charset", "UTF-8");
//		            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
//		            conn.setRequestProperty("Connection", "Keep-Alive");
				if (file.length() == 0) {
			//		 oSavedFile.seek(0);
					conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					if (conn != null) {
						maxSize = conn.getContentLength();
						Log.i("update", "maxSize1:" + maxSize);
					} else {
						return null;
					}
					if (maxSize <= -1) {
						return null;
					}
				}

				else if (haftVersionCode.equals(versionCode))// 若上次未下载完成，且本地的续传版本和服务器版本号一致，则断点续传
				{
					oSavedFile.seek(start);
					conn = (HttpURLConnection) url.openConnection();
					
					conn.setConnectTimeout(5000);
					conn.setRequestProperty("RANGE", "bytes=" + start + "-");
					conn.connect();
					// 自带获取到文件的大小
					if (conn != null) {
						maxSize = conn.getContentLength();
						Log.i("update", "maxSize2:" + maxSize);
					} else {
						return null;
					}
					if (maxSize <= -1) {
						return null;
					}
				} else {// 若上次未下载完成，且服务器版本又有更新，则重新下载
					oSavedFile.seek(0);
					conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.connect();
					if (conn != null) {
						maxSize = conn.getContentLength();
						Log.i("update", "maxSize3:" + maxSize);
					} else {
						return null;
					}
					if (maxSize <= -1) {
						return null;
					}
					Log.i("update", "(Version maxSize:" + maxSize);
				}
				pd.setMax(maxSize);
				is = conn.getInputStream();
				// 新建一个文件路径以及保存的软件名称
				// fos = new FileOutputStream(file);
				bis = new BufferedInputStream(is);
             // DataInputStream dis=new DataInputStream(is);
				byte[] buffer = new byte[512];
				int len=0;
				boolean isSaveHalfVersionCode = true;
				while ((isDownload == true) && (len = bis.read(buffer)) != -1) {
					// fos.write(buffer,0, len);
					oSavedFile.write(buffer,0, len);
					
					if (isSaveHalfVersionCode) // 确定开始下载了再保存所下载程序的版本号
					{
						mSharedPreferences.edit()
								.putString("haftVersionCode", versionCode)
								.commit();
						isSaveHalfVersionCode = false;
					}
					currentSize += len;
					// 获取当前下载量
					Log.i("jwd", "totalDown:" + currentSize);
					pushToNotifition();
					pd.setProgress(currentSize);
				}
				if (isDownload == false) {
					return null;
				}
				// fos.close();
				// bis.close();
				// is.close();
				// conn.disconnect();
				Log.i("update", "saveInSD");
				return file;
			} else {
				handler.sendEmptyMessage(NO_SDCARD_ERROR);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			handler.sendEmptyMessage(GET_UNDATAINFO_ERROR);
			return null;
		} finally {
			try {
				// if(fos!=null)
				// fos.close();
				if (oSavedFile != null)
					oSavedFile.close();
				if (bis != null)
					bis.close();
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
				if(oSavedFile!=null)
				{
					oSavedFile.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	// 弹出对话框通知用户更新程序 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性,
	// 对话框的内容,样式,按钮 3.通过builder 创建一个对话框 4.对话框show()出来
	protected void showUpdataDialog() {
		Log.i("update", "dialog");
		final UpdateDialog dialog = new UpdateDialog(mContext);
		dialog.setCancelable(false);
		dialog.getTv_title().setText("发现新版本啦，是否升级?");
		dialog.getTv_name().setText("程序名称:"+"联系多多"+appInfo.getTitle());
		dialog.getTv_currentVersion().setText("当前版本:V" + getVersionCode());
		dialog.getTv_newVersion().setText("新版本:V" + appInfo.getVersion());
		dialog.getTv_time().setText("更新时间:" + appInfo.getLatestVersionTime());
		dialog.getTv_fileSize().setText("大小:" + appInfo.getLatestVersionSize());
		dialog.getTv_function().setText(appInfo.getDes());
		if (isManual == true) {
			dialog.getCheckBox().setVisibility(View.GONE);
		}
		dialog.getCheckBox().setOnCheckedChangeListener(
				new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							isSevenDayUpdate = true;
						} else {
							isSevenDayUpdate = false;

						}
					}
				});
		dialog.getBtn1().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DownloadAPK dAPK = new DownloadAPK();
				dAPK.execute(serverVersionUrl);
				if (isSevenDayUpdate) {
					SharedPreferences mSharedPreferences = mContext
							.getSharedPreferences(Constant.APPKEY,
									Context.MODE_PRIVATE);
					mSharedPreferences
							.edit()
							.putString("SevenDaysUpdate",
									DateUtil.getNowDate("yyyyMMdd")).commit();
				}
				dialog.dismiss();
			}
		});
		dialog.getBtn2().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isSevenDayUpdate) {
					SharedPreferences mSharedPreferences = mContext
							.getSharedPreferences(Constant.APPKEY,
									Context.MODE_PRIVATE);
					mSharedPreferences
							.edit()
							.putString("SevenDaysUpdate",
									DateUtil.getNowDate("yyyyMMdd")).commit();
				}
				dialog.dismiss();
			}
		});
		// TextView tv_title = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_title);
		// TextView tv_name = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_name);
		// TextView tv_currentVersion = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_versionNow);
		// TextView tv_newVersion = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_versionNew);
		// TextView tv_time = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_versionTime);
		// TextView tv_fileSize = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_versionSize);
		// TextView tv_function = (TextView) dialog
		// .findViewById(R.id.textView_updateDialog_versionFuction);
		// tv_title.setText("发现新版本啦，是否升级?");
		dialog.show();
		// AlertDialog.Builder builer = new Builder(mContext);
		// builer.setTitle("发现新版本啦，是否升级?");
		// builer.setMessage("程序名称:" + versionName + "\n\n" + "当前版本:V"
		// + getVersionCode() + "\n\n" + "新版本:V" + versionCode + "\n\n"
		// + "大小:" + versionSize + "\n\n" + versionFuction);
		// // 当点确定按钮时从服务器上下载 新的apk 然后安装װ
		// builer.setPositiveButton("立即更新", new OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// // APK下载
		// DownloadAPK dAPK = new DownloadAPK();
		// dAPK.execute(serverVersionUrl);
		// }
		// });
		// // 当点取消按钮时返回
		// builer.setNegativeButton("以后再说", new OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// // 返回原activity
		// // LoginMain();
		// }
		// });
		// AlertDialog dialog = builer.create();
		// dialog.setCancelable(true);
		// dialog.show();
	}

	// 获取当前APK的版本号
	private String getVersionCode() {
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = new PackageInfo();
		try {
			packInfo = packageManager.getPackageInfo(mContext.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(packInfo.versionCode);
	}

	// 到后台获取版本信息
	private class DownloadTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			String response = "";
			response = getResponse(params); // response是从服务器返回的版本号信息

			Log.i("update", "response:" + response);

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
			// 这里的result就是上面doInBackground执行后的返回值
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(GET_UNDATAINFO_ERROR);
				return;
			}
			JSONObject jo;
			try {
				jo = new JSONObject(result);
				appInfo = new LXApp();
				appInfo.setVersion(jo.optString("latestVersionId"));
				appInfo.setDownloadUrl(jo.optString("updateUrl"));
				appInfo.setTitle(jo.optString("latestVersionName"));
				appInfo.setDes(jo.optString("updateLog"));
				appInfo.setLatestVersionSize(jo.optString("latestVersionSize"));
				appInfo.setLatestVersionTime(jo.optString("latestVersionTime"));
				appInfo.setExpiry(jo.optBoolean("isExpiry"));
			} catch (Exception e) {
				handler.sendEmptyMessage(GET_UNDATAINFO_ERROR);
				return;
			}


			Log.i("update", "ServerVersionCode:" + versionCode);
			Log.i("update", "LocalVersionCode:" + versionCodeNow);
			if (appInfo.getVersion().equals(versionCodeNow)) {
				if (isManual) // 若是手动检测且是最新版本则会提示
				{
					Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_LONG)
							.show();
				}
				// LoginMain();
			} else {
				Message msg = new Message();
				msg.what = UPDATE_NOTIFY;
				handler.sendMessage(msg);
			}
			// } catch (JSONException e) {
			// Message msg = new Message();
			// msg.what = GET_UNDATAINFO_ERROR;
			// handler.sendMessage(msg);
			// e.printStackTrace();
			// }
			super.onPostExecute(result);
		}
	}

	// 到服务器后台下载最新APK
	class DownloadAPK extends AsyncTask<String, Integer, File> {
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("下载更新");
			progressDialog.setTitle("联系多多");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.setButton("暂停", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					isDownload = false;
					dialog.dismiss();
				}
			});
			progressDialog.setButton2("隐藏", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected File doInBackground(String... params) {
			try {
				Log.i("update", "getUrl:" + appInfo.getDownloadUrl());
				file = getFileFromServer("http://118.186.9.91/files/4022000001014036/gdown.baidu.com/data/wisegame/ae6f0191da6a627f/weibo.apk",
						progressDialog);
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = DOWN_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
				return null;
			}
			return file;
		}

		@Override
		protected void onPostExecute(File result) {
			// doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
			// 这里的result就是上面doInBackground执行后的返回值
			if (result != null) {
				installApk(result);
			}
			progressDialog.dismiss(); // 结束掉进度条对话框
			super.onPostExecute(result);
		}
	}

	// 调用安装apk
	protected void installApk(File file) {
		if (file.toString().endsWith(".apk")) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			mContext.startActivity(intent);
		} else {
			Message msg = new Message();
			msg.what = INSTALL_ERROR;
			handler.sendMessage(msg);
		}
	}

	public void pushToNotifition() {
		Intent intent = new Intent();
		// String notiTickStr =
		// mContext.getString(R.string.notifi_noti_receive);
		// Notification notification = new Notification(R.drawable.lianxiicon,
		// notiTickStr, System.currentTimeMillis());
		Notification notification = new Notification();
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.update_notify);
		contentView.setImageViewResource(R.id.imageView_update,
				R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.textView_update_appname, versionName);
		contentView.setProgressBar(R.id.progressBar_update, maxSize,
				currentSize, false);

		double percent = (currentSize * 1.0) / (maxSize * 1.0);

		contentView.setTextViewText(R.id.textView_update_percent,
				(int) (getNumber2(percent) * 100) + "%");
		// contentView.setOnClickPendingIntent(viewId, pendingIntent)
		// contentView.setOnClickPendingIntent(R.id.btn_notify_pause,
		// pendingIntent)
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FILL_IN_DATA);
		notification.icon = R.drawable.ic_launcher; // 必须设置icon否则不显示（估计是google的bug吧..）
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = contentView;
		// notification.contentView.setProgressBar(R.id.progressBar_update,maxSize,currentSize,false);
		// notification.contentView.setTextViewText(R.id.textView_update_appname,versionName);
		//intent.setClass(mContext, MainAct.class);设置跳转页
		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent = pIntent;
		// notification.setLatestEventInfo(mContext,"总大小:"+maxSize,
		// "当前大小:"+currentSize, pIntent);
		nm.notify(0, notification);
	}

	// 设置向后台发送的JSON 获取版本信息
	public String getRequestContent() {
		try {
			JSONObject jsonData = new JSONObject();
			jsonData.put("..", "...");// 省略了参数与值ֵ
			return "http://..../radio/design-order!getAndroidVersion?"
					+ jsonData.toString();// 省略了IP地址ַ
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 发送请求
	public String getResponse(String... urls) {
		String result = "";
		Log.i("update", "urls[0]:" + urls[0]);
		try {
			result = HttpUtil.doGet(urls[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 返回主界面
	private void LoginMain() {
		// Intent intent = new Intent(mContext, UpdateDemoActivity.class);
		// mContext.startActivity(intent);
		// 结束掉当前的activity
	}

	private void delTempApkWhileError() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				File delFile = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/" + Constant.APPKEY + ".apk");
				if (delFile.exists()) {
					delFile.delete();
					Log.i("update","delete");
				}
				file=null;
				downloadTask.cancel(true);
			}
		}).start();

	}

	/**
	 * 保留两位小数
	 * 
	 * @param pDouble
	 * @return
	 */
	public double getNumber2(double pDouble) {
		try {
			BigDecimal bd = new BigDecimal(pDouble);
			BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
			pDouble = bd1.doubleValue();
			long ll = Double.doubleToLongBits(pDouble);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pDouble;
	}

	private class UpdateDialog extends AlertDialog {
		private View view;
		private TextView tv_title;
		private TextView tv_name;
		private TextView tv_currentVersion;
		private TextView tv_newVersion;
		private TextView tv_time;
		private TextView tv_fileSize;
		private TextView tv_function;
		private Button btn1;
		private Button btn2;
		private CheckBox checkBox;

		public UpdateDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.dialog_update, null);
			setView(view);
			init();
		}

		public UpdateDialog(Context context, int theme) {
			super(context, theme);
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.dialog_update, null);
			setView(view);
			init();
		}

		public void init() {
			tv_title = (TextView) view
					.findViewById(R.id.textView_updateDialog_title);
			tv_name = (TextView) view
					.findViewById(R.id.textView_updateDialog_name);
			tv_currentVersion = (TextView) view
					.findViewById(R.id.textView_updateDialog_versionNow);
			tv_newVersion = (TextView) view
					.findViewById(R.id.textView_updateDialog_versionNew);
			tv_time = (TextView) view
					.findViewById(R.id.textView_updateDialog_versionTime);
			tv_fileSize = (TextView) view
					.findViewById(R.id.textView_updateDialog_versionSize);
			tv_function = (TextView) view
					.findViewById(R.id.textView_updateDialog_versionFuction);
			btn1 = (Button) view.findViewById(R.id.button_updateDialog_1);
			btn2 = (Button) view.findViewById(R.id.button_updateDialog_2);
			checkBox = (CheckBox) view.findViewById(R.id.checkBox_updateDialog);
		}

		public TextView getTv_title() {
			return tv_title;
		}

		public TextView getTv_name() {
			return tv_name;
		}

		public TextView getTv_currentVersion() {
			return tv_currentVersion;
		}

		public TextView getTv_newVersion() {
			return tv_newVersion;
		}

		public TextView getTv_time() {
			return tv_time;
		}

		public TextView getTv_fileSize() {
			return tv_fileSize;
		}

		public TextView getTv_function() {
			return tv_function;
		}

		public Button getBtn1() {
			return btn1;
		}

		public Button getBtn2() {
			return btn2;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

	}

}
