package com.jwd.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class FileUtil {
	//�ļ���׺��MIME�Ķ�Ӧ��ϵ����䣩
	public final static String[][] MIME_MapTable={
		    //{��׺��    MIME����}
			{".amr",    "audio/amr" },
		    {".3gp",    "video/3gpp"},
		    {".apk",    "application/vnd.android.package-archive"},
		    {".asf",    "video/x-ms-asf"},
		    {".avi",    "video/x-msvideo"},
		    {".bin",    "application/octet-stream"},
		    {".bmp",      "image/bmp"},
		    {".c",        "text/plain"},
		    {".class",    "application/octet-stream"},
		    {".conf",    "text/plain"},
		    {".cpp",    "text/plain"},
		    {".doc",    "application/msword"},
		    {".exe",    "application/octet-stream"},
		    {".gif",    "image/gif"},
		    {".gtar",    "application/x-gtar"},
		    {".gz",        "application/x-gzip"},
		    {".h",        "text/plain"},
		    {".htm",    "text/html"},
		    {".html",    "text/html"},
		    {".jar",    "application/java-archive"},
		    {".java",    "text/plain"},
		    {".jpeg",    "image/jpeg"},
		    {".jpg",    "image/jpeg"},
		    {".js",        "application/x-javascript"},
		    {".log",    "text/plain"},
		    {".m3u",    "audio/x-mpegurl"},
		    {".m4a",    "audio/mp4a-latm"},
		    {".m4b",    "audio/mp4a-latm"},
		    {".m4p",    "audio/mp4a-latm"},
		    {".m4u",    "video/vnd.mpegurl"},
		    {".m4v",    "video/x-m4v"},    
		    {".mov",    "video/quicktime"},
		    {".mp2",    "audio/x-mpeg"},
		    {".mp3",    "audio/x-mpeg"},
		    {".mp4",    "video/mp4"},
		    {".mpc",    "application/vnd.mpohun.certificate"},        
		    {".mpe",    "video/mpeg"},    
		    {".mpeg",    "video/mpeg"},    
		    {".mpg",    "video/mpeg"},    
		    {".mpg4",    "video/mp4"},    
		    {".mpga",    "audio/mpeg"},
		    {".msg",    "application/vnd.ms-outlook"},
		    {".ogg",    "audio/ogg"},
		    {".pdf",    "application/pdf"},
		    {".png",    "image/png"},
		    {".pps",    "application/vnd.ms-powerpoint"},
		    {".ppt",    "application/vnd.ms-powerpoint"},
		    {".prop",    "text/plain"},
		    {".rar",    "application/x-rar-compressed"},
		    {".rc",        "text/plain"},
		    {".rmvb",    "audio/x-pn-realaudio"},
		    {".rtf",    "application/rtf"},
		    {".sh",        "text/plain"},
		    {".tar",    "application/x-tar"},    
		    {".tgz",    "application/x-compressed"}, 
		    {".txt",    "text/plain"},
		    {".wav",    "audio/x-wav"},
		    {".wma",    "audio/x-ms-wma"},
		    {".wmv",    "audio/x-ms-wmv"},
		    {".wps",    "application/vnd.ms-works"},
		    //{".xml",    "text/xml"},
		    {".xml",    "text/plain"},
		    {".z",        "application/x-compress"},
		    {".zip",    "application/zip"},
		    {"",        "*/*"}    
		};
	/**
	 * �ֻ��ܷ������ļ�
	 * @param file
	 * @return
	 */
	public static boolean canOpenFile(String fName){
		    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		    int dotIndex = fName.lastIndexOf(".");
		    if(dotIndex < 0){
		        return false ;
		    }
		    /* ��ȡ�ļ��ĺ�׺�� */
		    String end=fName.substring(dotIndex,fName.length()).toLowerCase();
		    if(end=="")return false ;
		    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
		    for(int i=0;i<MIME_MapTable.length;i++){
		        if(end.equals(MIME_MapTable[i][0])){
		        	return true ;
		        }
		    }
		    return false ;
	}
	public static boolean canOpenFile(File file){
	    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		String  fName = file.getName() ;
	    int dotIndex = fName.lastIndexOf(".");
	    if(dotIndex < 0){
	        return false ;
	    }
	    /* ��ȡ�ļ��ĺ�׺�� */
	    String end=fName.substring(dotIndex,fName.length()).toLowerCase();
	    if(end=="")return false ;
	    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
	    for(int i=0;i<MIME_MapTable.length;i++){
	        if(end.equals(MIME_MapTable[i][0])){
	        	return true ;
	        }
	    }
	    return false ;
}
	/**
	 * ��ȡ�ļ�����
	 * @param file
	 * @return
	 */
	public static int getFileType(File file){
		String mime = getMIMEType(file);
		if(mime.indexOf("image")>=0){
			return 1;
		}else if(mime.indexOf("audio")>=0){
			return 3;
		}else if(mime.indexOf("video")>=0){
			return 4;
		}else if(mime.indexOf("application")>=0){
			return 10;		
		}else if(mime.indexOf("text")>=0){
				return 11;	
		}else{
			return 0;
		}
	}
	public static int getFileType(String fName){
		String mime = getMIMEType(fName);
		if(mime.indexOf("image")>=0){
			return 1;
		}else if(mime.indexOf("audio")>=0){
			return 3;
		}else if(mime.indexOf("video")>=0){
			return 4;
		}else if(mime.indexOf("application")>=0){
			return 10;	
		}else if(mime.indexOf("text")>=0){
			return 11;		
		}else{
			return 0;
		}
	}
	/**
	 * ����ļ���׺���ö�Ӧ��MIME���͡�
	 * @param file
	 */
	public static String getMIMEType(String fName)
	{
	    String type="*/*";
	    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
	    int dotIndex = fName.lastIndexOf(".");
	    if(dotIndex < 0){
	        return type;
	    }
	    /* ��ȡ�ļ��ĺ�׺�� */
	    String end=fName.substring(dotIndex,fName.length()).toLowerCase();
	    if(end=="")return type;
	    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
	    for(int i=0;i<MIME_MapTable.length;i++){
	        if(end.equals(MIME_MapTable[i][0]))
	            type = MIME_MapTable[i][1];
	    }
	    return type;
	}
	/**
	 * ����ļ���׺���ö�Ӧ��MIME���͡�
	 * @param file
	 */
	public static String getMIMEType(File file)
	{
		String fName = file.getName();
	    String type="*/*";
	    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
	    int dotIndex = fName.lastIndexOf(".");
	    if(dotIndex < 0){
	        return type;
	    }
	    /* ��ȡ�ļ��ĺ�׺�� */
	    String end=fName.substring(dotIndex,fName.length()).toLowerCase();
	    if(end=="")return type;
	    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
	    for(int i=0;i<MIME_MapTable.length;i++){
	        if(end.equals(MIME_MapTable[i][0]))
	            type = MIME_MapTable[i][1];
	    }
	    return type;
	}
	/**
	 * ��ȡ�ļ���׺
	 * @param filePath
	 * @return
	 */
	public static String getSuffix(String filePath){
		if(StrUtil.isEmpty(filePath)){
			return "";
		}else if(filePath.lastIndexOf(".")<=-1){
			return "";
		}else{
			return filePath.substring(filePath.lastIndexOf("."));
		}
	}
	/**
	 * ���ļ�
	 * @param file
	 */
	public static void openFile(File file,Context context ){
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    //����intent��Action����
	    intent.setAction(Intent.ACTION_VIEW);
	    //��ȡ�ļ�file��MIME����
	    String type = getMIMEType(file);
	    //����intent��data��Type���ԡ�
	    intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
	    //��ת
	    context.startActivity(intent);    
	}
	public static void saveFile(String dic, String fileName, Object obj)
			throws Exception {
		Log.v("file", "saveFile path = " + dic + fileName );
		File file = new File(dic);
		if (!file.exists()) {
			file.mkdirs();
		}
		File newFile = new File(dic + fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				newFile));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Object getFile(String fileName) {
		Log.v("file", "getFile path = " + fileName);
		try {
			File file = new File(fileName);
			if (file.exists() && !file.isDirectory()) {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file));
				Object obj =  ois.readObject();
				ois.close();
				return obj;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static int copyfile(File fromFile, File toFile, Boolean rewrite) {
		if (!fromFile.exists()) {
			return -1;
		}
		if (!fromFile.isFile()) {
			return -2;
		}
		if (!fromFile.canRead()) {
			return -3;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}
		try {

			java.io.FileInputStream fosfrom = new java.io.FileInputStream(
					fromFile);

			java.io.FileOutputStream fosto = new FileOutputStream(toFile);

			byte bt[] = new byte[1024];

			int c;

			while ((c = fosfrom.read(bt)) > 0) {

				fosto.write(bt, 0, c); // ������д�����ļ�����

			}

			fosfrom.close();

			fosto.close();
			return 1;
		} catch (Exception ex) {
			Log.e("file", ex.getMessage());
			return -4 ;

		}

	}
	/**
     * ɾ���ļ���
     * @param filePathAndName String �ļ���·������� ��c:/fqf
     * @param fileContent String
     * @return boolean
     */
    public static void delFolder(String folderPath) {
            try {
                    delAllFile(folderPath); //ɾ����������������
                    String filePath = folderPath;
                    filePath = filePath.toString();
                    java.io.File myFilePath = new java.io.File(filePath);
                    myFilePath.delete(); //ɾ����ļ���

            }
            catch (Exception e) {
                    System.out.println("ɾ���ļ��в�������");
                    e.printStackTrace();

            }
    }

    /**
     * ɾ���ļ�������������ļ�
     * @param path String �ļ���·�� �� c:/fqf
     */
    public static void delAllFile(String path) {
            File file = new File(path);
            if (!file.exists()) {
                    return;
            }
            if (!file.isDirectory()) {
           return;
            }
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                    if (path.endsWith(File.separator)) {
                            temp = new File(path + tempList[i]);
                    }
                    else {
                            temp = new File(path + File.separator + tempList[i]);
                    }
                    if (temp.isFile()) {
                            temp.delete();
                    }
                    if (temp.isDirectory()) {
                            delAllFile(path+"/"+ tempList[i]);//��ɾ���ļ���������ļ�
                            delFolder(path+"/"+ tempList[i]);//��ɾ����ļ���
                    }
            }
    } 
    /**
     * �ϴ�ͼƬ��������
     * @param actionUrl
     * @param accountId
     * @param fileName
     * @param uploadContent
     * @return
     */
    public static String uploadFile(String actionUrl, String fileName , byte[] uploadContent) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			Log.v("file","uploadFile actionUrl is  "+actionUrl);
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* ����Input��Output����ʹ��Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* ����DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file\";filename=\"" + fileName + "\"" + end);
			ds.writeBytes(end);
			//���ϴ�����д��
			ds.write(uploadContent);
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			Log.v("file","uploadFile result is  "+b.toString().trim());
			return b.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * �ϴ�ͼƬ��������
     * @param actionUrl
     * @param accountId
     * @param fileType
     * @param fileName
     * @param uploadContent
     * @return
     */
    public static String uploadFile(String actionUrl,long accountId , int fileType, String fileName , byte[] uploadContent) {
    	String url = actionUrl + "?accountId="+accountId+"&uploadType="+fileType;
    	return uploadFile(url, fileName, uploadContent);
    }
    public static String uploadFile(String actionUrl,long accountId , int fileType, String fileName , String filePath) {
    	byte[] uploadContent = readFile(filePath);
    	return uploadFile(actionUrl, accountId, fileType, fileName, uploadContent);
    }
	/**
	 * ��ȡ�ļ�ת��Ϊ�����
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFile(String filename) {
		try {
			// ���������
			FileInputStream inStream = new FileInputStream(new File(filename));
			// newһ��������
			byte[] buffer = new byte[1024 ];
			int len = 0;
			// ʹ��ByteArrayOutputStream�������������
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((len = inStream.read(buffer)) != -1) {
				// д�����
				outStream.write(buffer, 0, len);
			}
			// �õ��ļ��Ķ��������
			byte[] data = outStream.toByteArray();
			// �ر���
			outStream.close();
			inStream.close();
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��bitmapת��byte����
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap){
		if(bitmap!=null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// ��Bitmap�����ļ���ȥ��ע�������ѹ�����Ǹ�100��ѹ���ȣ�0-100��Խ������Խ��
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] mContent = baos.toByteArray();
			return mContent ;
		}else{
			return null;
		}
	}
}
