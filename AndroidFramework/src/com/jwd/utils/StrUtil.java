package com.jwd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class StrUtil {
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		String regex = "^((0|[1-9]|[1-9][0-9]+))$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}

	public static String cutLastlyComma(String s) {
		String str = s;
		if (s.indexOf(",") != -1) {
			int size = s.length();
			String comma = s.substring(size - 1, size);
			if (comma.equals(",")) {
				str = s.substring(0, size - 1);
			}
		}
		return str;
	}

	public static String cutFirstLyComma(String s) {
		String str = s;
		if (s.indexOf(",") == 0) {
			int size = s.length();
			String comma = s.substring(0, 1);
			if (comma.equals(",")) {
				str = s.substring(1, size - 1);
			}
		}
		return str;
	}
	
	public static String cutLastlyCharacter(String s , String character) {
		String str = s;
		
		if(character == null || character.equals("")) {
			return str;
		}
		
		if (s.indexOf(character) != -1) {
			int size = s.length();
			String comma = s.substring(size - 1, size);
			if (comma.equals(character)) {
				str = s.substring(0, size - 1);
			}
		}
		
		return str;
	}
	
	
	public static void main(String[] args) {
		String str = "";
		boolean flag = isNumber(str);
		System.out.println(flag);
	}

	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ϸ��ֻ��ʽ��֤
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoblie(String str) {
		if (StrUtil.isEmpty(str))
			return false;
		String regex = "^((\\+{0,1}86){0,1})1[0-9]{10}$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}

	public static String processMobile(String mobile) {
		String result = "";
		if (!isNotEmpty(mobile)) {
			return result;
		}
		String tempM = mobile.trim();
		tempM.replaceAll("-", "").replaceAll("_", "");
		if (tempM.startsWith("+86")) {
			result = tempM.substring(3);
		} else if (tempM.startsWith("86")) {
			result = tempM.substring(2);
		} else if (tempM.startsWith("+086")) {
			result = tempM.substring(4);
		} else {
			result = mobile;
		}
		return result;

	}

	public static String lineHight(String str) {
		SpannableString s = new SpannableString(str);

		Pattern p = Pattern.compile("abc");

		Matcher m = p.matcher(s);

		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(Color.RED), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return s.toString();
	}

	/**
<<<<<<< StrUtil.java
=======
	 * �滻����
>>>>>>> 1.2
	 * 
	 * @param args
	 */
	public static String replaceLink(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String repalceRex = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return str.replaceAll(repalceRex, "<a target=_blank href=$0>$0</a>");
	}

	/**
<<<<<<< StrUtil.java
=======
	 * ��ʽ������
>>>>>>> 1.2
	 * 
	 * @param args
	 */
	public static String formatLink(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String repalceRex = "(http://|www\\.)([\\w-]+.)+[\\w-]+(/[\\w- .%&=]*)?";

		return str.replaceAll(repalceRex, " $0 ").replaceAll("<br>", "\r\n")
				.replaceAll("<br/>", "\r\n").replaceAll("<br />", "\r\n");
	}

	/**
	 * ת��&quot;&prime;Ϊ" '
	 * 
	 * @param str
	 * @return
	 */
	public static String parseQuotOrPrime(String str) {
		return str.replaceAll("&quot;", "\"").replaceAll("&prime;", "'");
	}

	/**
	 * �Ƿ���HTTP�쳣
	 * 
	 * @param result
	 * @return
	 */
	public static boolean isHttpException(String result) {
		if (isEmpty(result) || "-1".equals(result) || "-2".equals(result)
				|| "-6".equals(result) || "-7".equals(result)) {
			return true;
		}
		return false;
	}

	/**
<<<<<<< StrUtil.java
=======
	 * ����
>>>>>>> 1.2
	 */
	public static String replaceLineFeed(String content) {
		if (isEmpty(content))
			return "";
		return content.replaceAll("\r\n", "\n");
	}

	/*
<<<<<<< StrUtil.java
=======
	 * �ֻ�����ʽ��
>>>>>>> 1.2
	 */
	public static String getRealMoblie(String str) {
		if (str == null || str.length() < 11) {
			return "";
		}
		StringBuffer strTmp = new StringBuffer();
		if (str.length() == 11) {
			if (isMoblie(str)) {
				return str;
			}
			return "";
		} else {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) <= '9' && str.charAt(i) >= '0') {
					strTmp.append(str.charAt(i));
				}
			}
		}
		String resultStr = strTmp.toString();
		if (resultStr.startsWith("86")) {
			resultStr = resultStr.substring(2);
		}
		if (resultStr.startsWith("086")) {
			resultStr = resultStr.substring(3);
		}
		if (isMoblie(resultStr)) {
			return resultStr;
		}
		return "";
	}

	public static String FilterHtml(String str) {
		str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
		return str;
	}

	// ת��
	public static String UnicodeToGBK2(String s) {
		String[] k = s.split(";");
		String rs = "";
		for (int i = 0; i < k.length; i++) {
			String newstr = k[i];
			int strIndex = newstr.indexOf("&#");
			if (strIndex > -1) {
				String kstr = "";
				if (strIndex > 0) {
					kstr = newstr.substring(0, strIndex);
					rs += kstr;
					newstr = newstr.substring(strIndex);
				}
				int m = Integer.parseInt(newstr.replace("&#", ""));
				char c = (char) m;
				rs += c;
			} else {
				rs += k;
			}
		}
		return rs;
	}
	public static String formatTraffic(long trafficSize){
		if(trafficSize>=1024 && trafficSize<1024*1024){
			return (int)trafficSize/1024 + "KB";
		}else if(trafficSize>=1024*1024 && trafficSize<1024*1024*1024){
			return (int)trafficSize/1024/1024 +"MB";
		}else{
			return (int)trafficSize +"B";
		}
	}
	public static String formatNameLen(String name){
		if(isEmpty(name)){
			return "";
		}else if(name.length()<=3){
			return name ;
		}else{
			return name.substring(0,2)+"...";
		}
	}
	public static String parseDistance(double distance){
		String result = "";
			if(distance<1000){
				result =  Math.round(distance/100)+1 +"00米以内";
			}else{
				result = Math.round(distance/1000) +"公里";
			}
		return result ;
	}
	/**
<<<<<<< StrUtil.java
=======
	 * ȥ������
>>>>>>> 1.2
	 * @param str
	 * @return
	 */
	public static String replaceWrap(String str){
		if(isEmpty(str))return str;
		return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
	}
	/**
	 * @return
	 */
	public static String formatPic(String pic ,String domin){
		if(isEmpty(pic))return "";
		if(pic.startsWith("http://")){
			return pic;
		}else {
			return domin + pic.replace("/opt", "");
		}
	}
	public static String formatFileSize(int size){
		String fileSize = "";
		if(size>1000 && size<1000*1000){
			fileSize=size/1000+"K";
		}else if(size>1000*1000){
			fileSize= size/1000000+"M";
		}else if(size <= 0){
			size = 1;
			fileSize = size + "Byte";
		}else{
			fileSize = size + "Byte";
		}
		return fileSize ;
	}
	public static String formatFileSize(long size){
		String fileSize = "";
		if(size>1000 && size<1000*1000){
			fileSize=(Math.round( size /1000)*100)/100+"K";
		}else if(size>1000*1000){
			fileSize=(Math.round( size /1000000)*100)/100+"M";
		}else if(size <= 0){
			size = 1;
			fileSize = size + "Byte";
		}else{
			fileSize = size + "Byte";
		}
		return fileSize ;
	}
	/**
<<<<<<< StrUtil.java
=======
	 * �Ա�
>>>>>>> 1.2
	 * @param optString
	 * @return
	 */
	public static String formatGender(String genderStr) {
		try {
			return formatGender(Integer.parseInt(genderStr));
		} catch (NumberFormatException e) {
			return  "保密" ;
		}
	}
	public static String formatGender(int gender) {
		if(gender  == 1){
			return "男";
		}else if(gender == 2){
			return "女";
		}else{
			return "保密";
		}
	}
}
