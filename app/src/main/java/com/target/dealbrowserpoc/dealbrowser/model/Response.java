package com.target.dealbrowserpoc.dealbrowser.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Response implements Serializable {

	private int statusCode;
	private String responseBody;
	private String requestString;
	private String requestUrl;
	private String errorMsg;
	private String optionalMsg;
	private int rRequestTypes;
	private Map<String, List<String>> headers;

	public  int getRequestTypes() {
		return rRequestTypes;
	}

	public void setRequestTypes(int rRequestTypes) {
		this.rRequestTypes = rRequestTypes;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOptionalMsg() {
		return optionalMsg;
	}

	public void setOptionalMsg(String optionalMsg) {
		this.optionalMsg = optionalMsg;
	}

	public void setHeaders(Map<String, List<String>> responseHeaders) {
		headers = responseHeaders;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}
}
