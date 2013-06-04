package com.jwd.api;



public interface RequestListener {
    /**
     * get response from services 
     * @param response
     */
	public void onComplete(String response);
 
	public void onError(LXHTTPException e);

}
