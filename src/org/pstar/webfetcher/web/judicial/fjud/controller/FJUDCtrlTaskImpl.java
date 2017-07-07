package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.pstar.webfetcher.web.judicial.fjud.model.Judical;

public interface FJUDCtrlTaskImpl {
	public HashMap<String, String> getFetchParameters();

	public HashSet<String> getCourtList();

	public void doFinishTask();

	public void doFinishOneCourt();

	public String getReferrer();

	public long getFetchTimeIntervel();

	public int getIntFetchTimeIntervel();

	public HashMap<String, String> getCookies();

	public void setCookies(Map<String, String> cookies);

	public void addRecord(Judical record);
}
