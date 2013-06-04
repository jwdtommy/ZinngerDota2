package com.jwd.api;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.jwd.utils.StrUtil;

import android.text.TextUtils;
import android.util.Log;


public class LXParameters {

	private ArrayList<String> mKeys = new ArrayList<String>();
	private ArrayList<String> mValues=new ArrayList<String>();
	
	
	public LXParameters(){
		
	}
	
	
	public void add(String key, String value){
	    if(!TextUtils.isEmpty(key)&&!TextUtils.isEmpty(value)){
	        this.mKeys.add(key);
	        mValues.add(value);
	    }
	   
	}
	
	public void add(String key, int value){
	    this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
	}
	public void add(String key, long value){
	    this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }
	
	public void remove(String key){
	    int firstIndex=mKeys.indexOf(key);
	    if(firstIndex>=0){
	        this.mKeys.remove(firstIndex);
	        this.mValues.remove(firstIndex);
	    }
	  
	}
	
	public void remove(int i){
	    if(i<mKeys.size()){
	        mKeys.remove(i);
	        this.mValues.remove(i);
	    }
	}
	
	
	private int getLocation(String key){
		if(this.mKeys.contains(key)){
			return this.mKeys.indexOf(key);
		}
		return -1;
	}
	
	public String getKey(int location){
		if(location >= 0 && location < this.mKeys.size()){
			return this.mKeys.get(location);
		}
		return "";
	}
	
	
	public String getValue(String key){
	    int index=getLocation(key);
	    if(index>=0 && index < this.mKeys.size()){
	        return  this.mValues.get(index);
	    }
	    else{
	        return null;
	    }
		
		
	}
	
	public String getValue(int location){
	    if(location>=0 && location < this.mKeys.size()){
	        String rlt = this.mValues.get(location);
	        return rlt;
	    }
	    else{
	        return null;
	    }
	}
	
	
	public int size(){
		return mKeys.size();
	}
	
	public void addAll(LXParameters parameters){
		for(int i = 0; i < parameters.size(); i++){
			this.add(parameters.getKey(i), parameters.getValue(i));
		}
		
	}
	/**
	 * change lxParameter to  HTTPParam (doPost)
	 * @return
	 */
	public ArrayList<NameValuePair> toHttpParam (){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		for(int i = 0; i < size(); i++){
			if(StrUtil.isNotEmpty(getKey(i))){
				params.add(new BasicNameValuePair(getKey(i), getValue(i)));
			}
		}
		return params ; 
	}
	/**
	 * change  lxParameter  to HTTPParamString(doGet)
	 * @return
	 */
	public String toHttpParamString () {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0; loc < size(); loc++) {
			if (first){
			    first = false;
			}
			else{
			    sb.append("&");
			}
			String _key=getKey(loc);
			String _value=getValue(_key);
			if(_value==null){
			    Log.i("encodeUrl", "key:"+_key+" 's value is null");
			}
			else{
			    sb.append(_key + "=" +_value);
			}
			
		}
		return sb.toString();
	}
	public void clear(){
		this.mKeys.clear();
		this.mValues.clear();
	}
 }
