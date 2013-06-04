package com.jwd.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.jwd.androidframework.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;

public class ImageUtil {
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff000000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

		return baos.toByteArray();
	}

	// drawableת��Ϊbitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getMinimumHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565; // ȡdrawable����ɫ��ʽ
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	// zoomͼ��
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newbmp);
	}

	/**
	 * �ڸ��ͼƬ�����ϽǼ�����ϵ�������������ú�ɫ��ʾ
	 * 
	 * @param icon
	 *            ���ͼƬ
	 * @return ����ϵ��������ͼƬ
	 */
	public static Bitmap generatorContactCountIcon(Context context,
			Bitmap icon, int count, int iconSize, int imageSourceId) {
		// ��ʼ������
		Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(contactIcon);

		// ����ͼƬ
		Paint iconPaint = new Paint();
		iconPaint.setDither(true);// ������
		iconPaint.setFilterBitmap(true);// ������Bitmap�����˲����?������ѡ��Drawableʱ�����п���ݵ�Ч��
		Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
		Rect dst = new Rect(0, 0, iconSize, iconSize);
		canvas.drawBitmap(icon, src, dst, iconPaint);

		// ��ͼƬ�ϴ���һ�����ǵ���ϵ�˸���
		// ���ÿ���ݺ�ʹ���豸���ı��־�
		Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);
		countPaint.setColor(Color.WHITE);
		countPaint.setTextSize(20f);
		countPaint.setTypeface(Typeface.DEFAULT_BOLD);
		Bitmap bg = BitmapFactory.decodeResource(context.getResources(),
				imageSourceId);
		canvas.drawBitmap(bg, iconSize - bg.getWidth(),
				iconSize - bg.getHeight(), new Paint());
		canvas.drawText(String.valueOf(count), iconSize - 20, iconSize - 8,
				countPaint);
		return contactIcon;
	}

	// ͼƬ���
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	// drawble zhuan bitmap
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * ��ȡͼƬ����ͼ
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap getThumbil(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸ߣ�ע��˴���bitmapΪnull
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // ��Ϊ false
		// �������ű�
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// ���¶���ͼƬ����ȡ���ź��bitmap��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// ����ThumbnailUtils����������ͼ������Ҫָ��Ҫ�����ĸ�Bitmap����
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * ��ȡ��Ƶ������ͼ ��ͨ��ThumbnailUtils������һ����Ƶ������ͼ��Ȼ��������ThumbnailUtils�����ָ����С������ͼ��
	 * �����Ҫ������ͼ�Ŀ�͸߶�С��MICRO_KIND��������Ҫʹ��MICRO_KIND��Ϊkind��ֵ��������ʡ�ڴ档
	 * 
	 * @param videoPath
	 *            ��Ƶ��·��
	 * @param width
	 *            ָ�������Ƶ����ͼ�Ŀ��
	 * @param height
	 *            ָ�������Ƶ����ͼ�ĸ߶ȶ�
	 * @param kind
	 *            ����MediaStore.Images.Thumbnails���еĳ���MINI_KIND��MICRO_KIND��
	 *            ���У�MINI_KIND: 512 x 384��MICRO_KIND: 96 x 96
	 * @return ָ����С����Ƶ����ͼ
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width,
			int height) {
		System.out.println("videoPath" + videoPath);
		Bitmap bitmap = null;
		// ��ȡ��Ƶ������ͼ
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath,
				Thumbnails.MICRO_KIND);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 
	 * @param background
	 *            ����ͼ
	 * @param foreground
	 *            ǰ��ͼ
	 * @param bgWidth
	 *            ����ͼ��
	 * @param bgHeight
	 *            ����ͼ��
	 * @param fgWidth
	 *            ǰ��ͼ��
	 * @param fgHeight
	 *            ǰ��ͼ��
	 * @param type
	 *            ���Ƿ�ʽ 1:center 2 ���� 3 ���� 4���� 5����
	 * @return ���Ǻ�bitmap
	 */
	public static Bitmap toConformBitmap(Bitmap background, Bitmap foreground,
			int bgWidth, int bgHeight, int fgWidth, int fgHeight, int type) {
		if (background == null) {
			return null;
		}
		// create the new blank bitmap ����һ���µĺ�SRC���ȿ��һ���λͼ
		Bitmap newbmp = Bitmap
				.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Bitmap newFGbmp = Bitmap.createBitmap(foreground, 0, 0, fgWidth,
				fgHeight);
		Canvas cv = new Canvas(newbmp);
		// cv.drawColor(Color.WHITE);//������ɫ
		// draw bg into
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		cv.drawBitmap(background, 0, 0, paint);// �� 0��0��꿪ʼ����bg
		// draw fg into
		if (type == 1) {
			cv.drawBitmap(newFGbmp, 0, 0, paint);
		} else if (type == 2) {
			cv.drawBitmap(newFGbmp, bgWidth - fgWidth, 0, paint);
		} else if (type == 3) {
			cv.drawBitmap(newFGbmp, 0, bgHeight - fgHeight, paint);
		} else if (type == 4) {
			cv.drawBitmap(newFGbmp, bgWidth - fgWidth, bgHeight - fgHeight,
					paint);
		} else {
			cv.drawBitmap(newFGbmp, bgWidth / 2 - fgWidth / 2, bgHeight / 2
					- fgHeight / 2, paint);
		}
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newbmp;
	}

	/**
	 * 
	 * @param background
	 *            ����ͼ
	 * @param foreground
	 *            ǰ��ͼ
	 * @param type
	 *            ���Ƿ�ʽ 0:center 1 ���� 2 ���� 3���� 4���� Ĭ��Ϊ0
	 * @return ���Ǻ�bitmap
	 */
	public static Bitmap toConformBitmap(final Bitmap background,
			final Bitmap foreground) {
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		return toConformBitmap(background, foreground, bgWidth, bgHeight,
				fgWidth, fgHeight, 0);
	}

	/**
	 * 
	 * @param background
	 *            ����ͼ
	 * @param foreground
	 *            ǰ��ͼ
	 * @param type
	 *            ���Ƿ�ʽ 0:center 1 ���� 2 ���� 3���� 4���� Ĭ��Ϊ0
	 * @return ���Ǻ�bitmap
	 */
	public static Bitmap toConformBitmap(final Bitmap background,
			final Bitmap foreground, int type) {
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		return toConformBitmap(background, foreground, bgWidth, bgHeight,
				fgWidth, fgHeight, type);
	}

	/**
	 * �ӱ߿�
	 * @param bitmap
	 * @return
	 */
	public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap,int color) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight()));
			final float roundPx = 14;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			final Rect src = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}
	/**
	 * 图片变灰
	 * @param context
	 * @param resid
	 * @return
	 */
	public static Drawable toGrayDrawable (Context context ,int resid){
		 Drawable drawable = context.getResources().getDrawable(resid);  
	     drawable.mutate();  
	     ColorMatrix cm = new ColorMatrix();  
	     cm.setSaturation(0);       
	     ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);       
	     drawable.setColorFilter(cf);  
     return drawable ;
	}
	
	public static Drawable getDrawableByPicName(String picName, Context context,String packageName) {
        int id = context.getResources().getIdentifier(
                picName == null ? "no_picture" : picName, "drawable",packageName
                );
        Drawable image = null;
        if (id <= 0) {
            image = context.getResources().getDrawable(R.drawable.ic_launcher);
        } else {
            image = context.getResources().getDrawable(id);
        }
        return image;
    }

}
