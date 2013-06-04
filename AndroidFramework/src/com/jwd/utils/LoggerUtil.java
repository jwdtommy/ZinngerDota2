package com.jwd.utils;

import com.jwd.Constant.Constant;

import android.util.Log;

/**
 * ��־������
 * @author yanyi
 *
 */
public class LoggerUtil {
	public static void log(String tag , String msg  , boolean isDebug , int type) {
		if(isDebug){
			switch(type) {
			case Log.VERBOSE:
				Log.v(tag, msg);
				break;
			case Log.DEBUG :
				Log.d(tag, msg);
				break;
			case Log.INFO :
				Log.i(tag, msg);
				break;
			case Log.WARN :
				Log.w(tag, msg);
				break;	
			case Log.ERROR :
				Log.e(tag, msg);
				break;	
			default:
				Log.v(tag, msg);
				break;
			}
		}
	}
	public static void log(String tag , String msg , int type) {
		log(tag, msg, Constant.DebugMode, type);
	}
	public static void log(String tag , String msg) {
		log(tag, msg, Constant.DebugMode, Log.VERBOSE);
	}
	public static void log(String msg) {
		log("test", msg, Constant.DebugMode, Log.VERBOSE);
	}
}
