package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class FJUDInitFetchTask implements Runnable {
	private FJUDCtrlTaskImpl fjudFetchCtrl;

	/**
	 * @param fjudFetchCtrl
	 */
	public FJUDInitFetchTask(FJUDCtrlTaskImpl fjudFetchCtrl) {
		super();
		this.fjudFetchCtrl = fjudFetchCtrl;
	}

	@Override
	public void run() {
		try {
			Connection conn = Jsoup.connect("http://jirs.judicial.gov.tw/FJUD/FJUDQRY01M_1.aspx");
			Response respon;

			conn.method(Method.GET);

			respon = conn.execute();
			this.fjudFetchCtrl.setCookies(respon.cookies());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
