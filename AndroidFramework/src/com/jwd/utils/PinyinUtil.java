package com.jwd.utils;

import java.util.ArrayList;

import com.jwd.utils.HanziToPinyin.Token;

import android.content.Context;

/**
 * ƴ��������
 * @author yy
 *
 */
public class PinyinUtil {
	public static Context context;
 public static String getSortKey(String displayName) {  
     if(AndroidUtil.getSDKVersionNumber() < 14){
    	 ArrayList<HanziToPinyin.Token>  tokens  = HanziToPinyin.getInstance().get(displayName);
    	 if (tokens != null && tokens.size() > 0) {  
             StringBuilder sb = new StringBuilder(); 
             for (HanziToPinyin.Token token : tokens) {  
                 if (Token.PINYIN == token.type) {  
                     if (sb.length() > 0) {  
                         sb.append(' ');  
                     }
                     
                     sb.append(token.target); 
                      
                     sb.append(' '); 
                    
                     sb.append(token.source);
                   
                 }else{
                	 sb.append(token.source.toUpperCase()); 
                 }  
             }  
             return sb.toString();  

         }  
     }else{
    	 ArrayList<HanziToPinyinForICS.Token> tokens  = HanziToPinyinForICS.getInstance().get(displayName); 
    	 if (tokens != null && tokens.size() > 0) {  
             StringBuilder sb = new StringBuilder(); 
             for (HanziToPinyinForICS.Token token : tokens) {  
                 if (Token.PINYIN == token.type) {  
                     if (sb.length() > 0) {  
                         sb.append(' ');  
                     }
                     
                     sb.append(token.target); 
                      
                     sb.append(' '); 
                    
                     sb.append(token.source);
                   
                 }else{
                	 sb.append(token.source.toUpperCase()); 
                 }  
             }  
             return sb.toString();  

         }  
     }
    
     return "";
} 
 public static String getFA(String sortKey){
	 if(StrUtil.isEmpty(sortKey))return "";
	 String result = "";
	 for (int i = 0; i < sortKey.split(" ").length; i=i+2) {
		 if(StrUtil.isNotEmpty(sortKey.split(" ")[i]) && sortKey.split(" ")[i].length()>=1){
			 result += sortKey.split(" ")[i].charAt(0);
		 }
	 }
	 return result;
 }
 public static String[] formatSortKey(String sortKey){
	 String result = "";
	 String fa = "";
	 String[] arr = new String[2];
	 for (int i = 0; i < sortKey.split(" ").length-1; i++) {
		 char c = sortKey.split(" ")[i].charAt(0);
		 if((c>='a' && c<= 'z') || (c>='A' && c<= 'z')){
			 if(!result.equals(""))result += " ";
		 result += sortKey.split(" ")[i] +  " " +sortKey.split(" ")[i+1];
		 fa += c;
		 }
	}
	 arr[0] = result;
	 arr[1] = fa;
	 return arr;
 }
 public static String getAllPinyin (String sortKey){
	 if(sortKey==null || sortKey.equals(""))return "";
	 String result = "";
	 for (int i = 0; i < sortKey.split(" ").length; i++) {
		 char c = sortKey.split(" ")[i].charAt(0);
			 
		 if((c>='a' && c<= 'z') || (c>='A' && c<= 'z')){
			 result += sortKey.split(" ")[i];
		 }
	}
	 return result;
 }
}
