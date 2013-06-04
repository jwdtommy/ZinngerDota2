
package com.jwd.api;

public class LXHTTPException extends Exception {

	private static final long serialVersionUID = 475022994858770424L;
	private int statusCode = -1;
	
    public LXHTTPException(String msg) {
        super(msg);
    }
    public LXHTTPException(Exception cause) {
        super(cause);
    }

    public LXHTTPException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public LXHTTPException(String msg, Exception cause) {
        super(msg, cause);
    }

    public LXHTTPException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
    
    
	public LXHTTPException() {
		super(); 
	}

	public LXHTTPException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public LXHTTPException(Throwable throwable) {
		super(throwable);
	}

	public LXHTTPException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
