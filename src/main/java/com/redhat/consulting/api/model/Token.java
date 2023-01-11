package com.redhat.consulting.api.model;

import java.io.Serializable;

public class Token implements Serializable {
	
	private static final long serialVersionUID = 6846401953708890994L;
	
	private String id="";
	private String expires = "";
	private Long expiresDate=0l;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public Long getExpiresDate() {
		return expiresDate;
	}
	public void setExpiresDate(Long expiresDate) {
		this.expiresDate = expiresDate;
	}
	
	@Override
	public String toString() {
		return "Token [id=" + id + ", expires=" + expires + ", expiresDate=" + expiresDate + "]";
	}
	
	

}
