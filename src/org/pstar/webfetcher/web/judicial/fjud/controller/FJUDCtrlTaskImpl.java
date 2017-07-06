package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.util.HashMap;
import java.util.Map;

public interface FJUDCtrlTaskImpl {
	public HashMap<String, String> getFetchParameters();

	public long getFetchTimeIntervel();

	public int getIntFetchTimeIntervel();

	public HashMap<String, String> getCookies();

	public void setCookies(Map<String, String> cookies);
}
