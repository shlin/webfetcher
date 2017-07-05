package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.util.ResourceBundle;

import org.pstar.webfetcher.core.FetchCore;
import org.pstar.webfetcher.web.judicial.fjud.ui.AppWindow;

public class FJUDFetchCore extends FetchCore {
	private AppWindow appWindow;
	private ResourceBundle resource;

	public FJUDFetchCore() {
		this.resource = ResourceBundle.getBundle("org.pstar.webfetcher.web.judicial.fjud.config");
		this.appWindow = new AppWindow(this.resource);
	}
}
