package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class FJUDFetchTask extends Thread {
	private boolean enable;
	private FJUDCtrlTaskImpl fjudFetchCtrl;

	public FJUDFetchTask(FJUDCtrlTaskImpl ctrl) {
		this.enable = true;
		this.fjudFetchCtrl = ctrl;
	}

	public void stopTask() {
		this.enable = false;
		this.interrupt();
	}

	public void sleep() throws InterruptedException {
		Thread.sleep(this.fjudFetchCtrl.getFetchTimeIntervel());
	}

	@Override
	public void run() {
		while (enable) {
			try {
				Response indexResponse;
				Connection indexConn = Jsoup.connect("http://jirs.judicial.gov.tw/FJUD/FJUDQRY02H_1.aspx");

				indexConn.method(Method.GET);
				indexConn.cookies(this.fjudFetchCtrl.getCookies());
				indexConn.data(this.fjudFetchCtrl.getFetchParameters());

				System.out.println(indexConn.get().body());

				this.sleep();
				this.enable = false;
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
