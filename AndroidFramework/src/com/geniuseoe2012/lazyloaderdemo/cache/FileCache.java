package com.geniuseoe2012.lazyloaderdemo.cache;

import java.io.File;

import com.geniuseoe2012.lazyloaderdemo.util.FileManager;

import android.content.Context;

public class FileCache extends AbstractFileCache{

	public FileCache(Context context) {
		super(context);
	
	}


	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url);
		return getCacheDir() + filename;
	}

	@Override
	public String getCacheDir() {
		
		return FileManager.getSaveFilePath();
	}

}
