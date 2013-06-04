package com.jwd.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
/**
 * Image processing
 * @author yanyi
 *
 */
public class AsyncImageLoader {
	public HashMap<String, SoftReference<Drawable>> imageCache;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	public static String DIRPATH = Environment.getExternalStorageDirectory()
			+ "/tixa/pre/image/";
	private Context mContext ; 
	public AsyncImageLoader(Context context) {
		mContext =  context ;
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		File file = new File(DIRPATH + ".nomedia");
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		File file = new File(DIRPATH + ".nomedia");
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ImageCallback imageCallback) {
		if (StrUtil.isEmpty(imageUrl)) {
			return null;
		}
		if (imageCache.containsKey(imageUrl)) {
			// get from cache
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference != null) {
				Drawable drawable = softReference.get();
				if (drawable != null) {
					return drawable;
				}
			}
		} else if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// get from SDcard
			final String filePath = DIRPATH + URLEncoder.encode(imageUrl);
			File file = new File(filePath);
			if (file.exists()) {
				final Drawable drawable = Drawable.createFromPath(filePath);
				if (drawable != null) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					return drawable;
				}
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				switch (message.what) {
				case 0:
					final Drawable drawable = (Drawable) message.obj;
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					imageCallback.imageLoaded(drawable,
							imageView, imageUrl);
					new Thread() {

						@Override
						public void run() {
							saveImage(drawable, URLEncoder.encode(imageUrl));
						}

					}.start();
					break;
				case -1:
					imageCallback.imageLoaded(null, imageView, imageUrl);
					break;
				}

			}
		};
		// new a Thread to download image
		synchronized (imageCache) {
			executorService.submit(new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// get failed
					if (drawable == null) {
						handler.sendEmptyMessage(-1);
						return;
					}
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					Message msg = new Message();
					msg.what = 0;
					msg.obj = drawable;
					handler.sendMessage(msg);
				}
			});
		}
		return null;
	}
	public Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		if (StrUtil.isEmpty(url))
			return null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			if (i != null) {
				Drawable d = Drawable.createFromStream(i, "src");
				i.close();
				return d;
			} else {
				return null;
			}
		} catch (Exception e1) {
			return null;
		}
	}

	// interface Callback
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String imageUrl);
	}

	public boolean saveImage(Drawable drawable, String fileName) {
		if (drawable == null)
			return false;
		try {
			File file = new File(DIRPATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			BitmapDrawable bd = (BitmapDrawable) drawable;
			Bitmap bm = bd.getBitmap();
			File myCaptureFile = new File(DIRPATH + fileName);
			BufferedOutputStream bos;

			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			//  compression ratio
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
