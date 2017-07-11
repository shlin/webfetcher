package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pstar.webfetcher.web.judicial.fjud.model.Judicial;

public class FJUDFetchTask extends Thread {
	private boolean enable;
	private FJUDCtrlTaskImpl fjudFetchCtrl;
	private int totalCount;

	public FJUDFetchTask(FJUDCtrlTaskImpl ctrl) {
		this.enable = true;
		this.fjudFetchCtrl = ctrl;
		this.totalCount = 0;
	}

	private void doFetchTotalCount(Document doc) {
		String strTotalSrc = doc.select("span:contains(筆 / 現在第)").text();
		String strTotal = strTotalSrc.replace("共", "").replaceAll("筆.+?$", "").trim();

		this.totalCount = Integer.parseInt(strTotal);
	}

	@Override
	public void run() {
		for (String court : this.fjudFetchCtrl.getCourtList()) {
			if (enable) {
				try {
					for (int i = 1; i <= 500; i++) {
						if (enable) {
							Connection courtConn = Jsoup.connect("http://djirs.judicial.gov.tw/fjud/FJUDQRY03_1.aspx");

							courtConn.method(Method.GET);
							courtConn.referrer(this.fjudFetchCtrl.getReferrer());
							courtConn.cookies(this.fjudFetchCtrl.getCookies());
							courtConn.data(this.fjudFetchCtrl.getFetchParameters());

							courtConn.data("v_court", court);
							courtConn.data("id", String.format("%d", i));

							Document doc = courtConn.get();
							Elements table = doc.select("td#jfull");

							if (table.isEmpty())
								break;

							// fetch total count
							this.doFetchTotalCount(doc);

							// result data
							String date = table.parents().select("td>span:contains(【裁判日期】)").text()
									.replaceAll("【裁判日期】", "").trim();
							String judId = table.parents().select("td>span:contains(【裁判字號】)").text()
									.replaceAll("【裁判字號】", "").trim();
							String judTitle = table.parents().select("td>span:contains(【裁判案由】)").text()
									.replaceAll("【裁判案由】", "").trim();
							String content = String.format("【裁判字號】 %s\n【裁判日期】 %s\n【裁判案由】 %s\n【裁判全文】\n%s", judId, date,
									judTitle, table.text());

							this.fjudFetchCtrl
									.addRecord(new Judicial(court, date, judId, judTitle, content, this.totalCount));

							this.sleep();
						} else
							break;
					}

					this.fjudFetchCtrl.doFinishOneCourt();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			} else
				break;
		}
		this.fjudFetchCtrl.doFinishTask();
	}

	public void sleep() throws InterruptedException {
		Thread.sleep(this.fjudFetchCtrl.getFetchTimeIntervel());
	}

	public void stopTask() {
		this.enable = false;
	}
}
