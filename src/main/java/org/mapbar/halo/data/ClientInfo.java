package org.mapbar.halo.data;

import java.net.HttpRetryException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class ClientInfo {
 
    public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getAcceptCharset() {
		return acceptCharset;
	}
	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}
	public String getAcceptEncoding() {
		return acceptEncoding;
	}
	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	public String getCacheControl() {
		return cacheControl;
	}
	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}

	public Cookie[] getCookies() {
		return cookies;
	}
	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getRefer() {
		return refer;
	}
	public void setRefer(String refer) {
		this.refer = refer;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public ClientInfo(HttpServletRequest request){
	    acceptEncoding=	request.getCharacterEncoding();
	    cookies= request.getCookies();
	    accept=request.getHeader("Accept");
	    acceptCharset=request.getHeader("Accept-Charset");
	    acceptLanguage=request.getHeader("Accept-Language");
	    cacheControl=request.getHeader("Cache-Control");
	    connection=request.getHeader("Connection");
	    host=request.getHeader("Host");
	    refer=request.getHeader("Refer");
	    userAgent=request.getHeader("User-Agent");
	}
	String accept;
	String acceptCharset;
    String acceptEncoding;
    String acceptLanguage;
    String cacheControl;
    String connection;
    Cookie[] cookies;
    String host;
    String refer;
    String userAgent;
}
