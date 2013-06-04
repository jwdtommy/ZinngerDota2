package com.jwd.utils;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import com.jwd.androidframework.R;
import com.jwd.utils.AsyncImageLoader.ImageCallback;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class UserUtil {

	/**
	 * 新版防错位的加载
	 * 
	 * @param listView
	 * @param view
	 * @param url
	 * @param loader
	 * @param defaultLogo
	 */
//	public static void setImage(ImageView view,
//			final String url, AsyncImageLoader loader, final int defaultLogo) {
//		setImage( view, url, loader, defaultLogo, false);
//	}

	public static void setImage1(ImageView view,
			final String url, AsyncImageLoader loader, final int defaultLogo,
			final boolean isBusy) {
		if (view == null)
			return;
		if (StrUtil.isEmpty(url)) {
			view.setImageResource(defaultLogo);
			return;
		}
		Drawable cachedImage = loader.loadDrawable(url, view,
				new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						if (imageDrawable != null) {
							imageView.setImageDrawable(imageDrawable);
							if (!isBusy) {
								AlphaAnimation aa = new AlphaAnimation(0.3f,
										1.0f);
								aa.setDuration(500);
								aa.setFillBefore(true);
								// imageView.startAnimation(aa);
							}
						} else {
							imageView.setImageResource(defaultLogo);
						}
					}

				});
		if (cachedImage == null) {
			view.setImageResource(defaultLogo);
		} else {
			view.setImageDrawable(cachedImage);
		}
	}

	public static void setImage(final ImageView view, String url,
			AsyncImageLoader loader, final int defaultLogo) {
		if (view == null)
			return;
		if (StrUtil.isEmpty(url)) {
			view.setImageResource(defaultLogo);
			return;
		}
		Drawable cachedImage = loader.loadDrawable(url, view,
				new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						if (imageDrawable != null)
							imageView.setImageDrawable(imageDrawable);
						else
							imageView.setImageResource(defaultLogo);
					}

				});
		if (cachedImage == null) {
			view.setImageResource(defaultLogo);
		} else {
			view.setImageDrawable(cachedImage);
		}
	}

	public static void setImage(ImageView view, String url,
			AsyncImageLoader loader) {
		if (view == null)
			return;
		try {
			URL uRL = new URL(url);
			if (StrUtil.isEmpty(url)) {
				view.setImageResource(R.drawable.ic_launcher);
				return;
			}
			setImage(view, url, loader, R.drawable.ic_launcher);
		} catch (MalformedURLException e) {
			try {
				view.setImageResource(Integer.parseInt(url));
			} catch (NumberFormatException e1) {
				view.setImageResource(R.drawable.ic_launcher);
			}
		}

	}
	
	
	public static void loadingView(ImageView imgview, boolean loading) {
		if (imgview != null) {
			if (loading) {
				imgview.setImageResource(R.drawable.emptyview_loading);
			} else {
				imgview.setImageResource(R.drawable.emptyview);
			}
		}
	}
	
	
	
	

	@SuppressLint("NewApi")
	public static void copyText(Context context, String text) {
		ClipboardManager cm = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cm.setText(text);
		Toast.makeText(context, "已复制到剪切板", Toast.LENGTH_SHORT).show();
	}

	public static Bitmap spliceDrawable(Drawable[] drawable) {
		if (drawable == null) {
			return null;
		}
		Bitmap[] bitmaps = new Bitmap[drawable.length];
		for (int i = 0; i < drawable.length; i++) {
			bitmaps[i] = Bitmap.createBitmap(ImageUtil
					.drawableToBitmap(drawable[i]));
		}
		return SpliceBitmaps(bitmaps);
	}

	public static Bitmap SpliceBitmaps(Bitmap[] bitmaps) {
		if (bitmaps.length == 0 || bitmaps == null) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		int cn = (int) Math.ceil(Math.sqrt(bitmaps.length));
		int rn = (int) Math.ceil(Math.sqrt(bitmaps.length));
		return SpliceBitmaps(bitmaps, cn, rn);
	}

	private static Bitmap SpliceBitmaps(Bitmap[] bitmaps, int colnum, int rownum) {
		if (bitmaps.length != 0 && bitmaps.length < colnum) {
			return bitmaps[0];
		}
		Bitmap resultBitmap = null;
		if (bitmaps.length > 0) {
			for (int i = 0; i < rownum; i++) {// 列拼接
				Bitmap curBitmap = bitmaps[colnum * i];// 每行初始bitmap
				for (int j = 0; j < colnum - 1; j++) {// 行拼接
					if (bitmaps.length - i * colnum == 1) {// 如果是最后一个并且是单独一行
															// 则不进行行拼接
						curBitmap = bitmaps[bitmaps.length - 1];
						break;
					} else {// 执行拼接
						curBitmap = Splice2BitmapH(curBitmap, bitmaps[i
								* colnum + j + 1]);
					}
				}
				if (resultBitmap != null) {
					resultBitmap = Splice2BitmapV(resultBitmap, curBitmap);
				} else {// resultBitmap 存储拼接完成的
					resultBitmap = curBitmap;
				}
			}
		}
		return resultBitmap;
	}

	/**
	 * 水平拼接Bitmap
	 * 
	 * @param firstBitmap
	 * @param secondBitmap
	 * @return
	 */
	public static Bitmap Splice2BitmapH(Bitmap firstBitmap, Bitmap secondBitmap) {
		int width = (firstBitmap.getWidth() + secondBitmap.getWidth());
		int height = Math
				.max(firstBitmap.getHeight(), secondBitmap.getHeight());
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(firstBitmap, 0, 0, null);
		canvas.drawBitmap(secondBitmap, firstBitmap.getWidth(), 0, null);
		LoggerUtil.log("h result getWidth= " + result.getWidth());
		return result;
	}

	/**
	 * 垂直拼接Bitmap
	 * 
	 * @param firstBitmap
	 * @param secondBitmap
	 * @return
	 */
	public static Bitmap Splice2BitmapV(Bitmap firstBitmap, Bitmap secondBitmap) {
		int height = firstBitmap.getHeight() + secondBitmap.getHeight();
		int width = Math.max(firstBitmap.getWidth(), secondBitmap.getWidth());
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(firstBitmap, 0, height - firstBitmap.getHeight(),
				null);
		canvas.drawBitmap(secondBitmap,
				width / 2 - secondBitmap.getWidth() / 2, 0, null);
		LoggerUtil.log("h result getHeight= " + result.getHeight());
		return result;
	}

	/**
	 * 本地文件压缩后显示
	 * 
	 */
	public static Bitmap FileCompressToBitmap(String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		File file = new File(path);
		BitmapFactory.decodeFile(path, opts);
		int inSampleSize = 2;
		if (file.length() > 120 * 1024) {
			inSampleSize = 8;
		}
		opts.inSampleSize = inSampleSize;
		opts.inJustDecodeBounds = false;
		try {
			Bitmap orginalBitmap = BitmapFactory.decodeFile(path, opts);
			return orginalBitmap;
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		return null;
	}
}
