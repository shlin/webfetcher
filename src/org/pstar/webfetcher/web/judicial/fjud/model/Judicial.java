package org.pstar.webfetcher.web.judicial.fjud.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Judicial {

	private String uuid;
	private String court;
	private String category;
	private String date;
	private String judId;
	private String judTitle;
	private String content;

	/**
	 * @param court
	 * @param date
	 * @param judId
	 * @param judTitle
	 * @param content
	 */
	public Judicial(String court, String date, String judId, String judTitle, String content) {
		super();
		this.category = "刑事";
		this.court = court;
		this.date = date.trim();
		this.judId = judId;
		this.judTitle = judTitle;
		this.content = content;

		this.uuid = UUID.nameUUIDFromBytes(this.getSummary().getBytes()).toString();
	}

	public String getSummary() {
		return String.format("%s %s %s", court, judId, judTitle);
	}

	/**
	 * @return the uuid
	 */
	public String getUUID() {
		return uuid;
	}

	/**
	 * @return the court
	 */
	public String getCourt() {
		return court;
	}

	/**
	 * @param court
	 *            the court to set
	 */
	public void setCourt(String court) {
		this.court = court;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	public String getGeneralDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Pattern p = Pattern.compile("(\\d{1,3})(\\d{2})(\\d{2})$");
		Matcher m = p.matcher(this.date);

		if (m.find()) {
			try {
				cal.setTime(sdf.parse(String.format("%s-%s-%s", m.group(1), m.group(2), m.group(3))));
				cal.add(Calendar.YEAR, 1911);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return sdf.format(cal.getTime());
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the judId
	 */
	public String getJudId() {
		return judId;
	}

	/**
	 * @param judId
	 *            the judId to set
	 */
	public void setJudId(String judId) {
		this.judId = judId;
	}

	/**
	 * @return the judTitle
	 */
	public String getJudTitle() {
		return judTitle;
	}

	/**
	 * @param judTitle
	 *            the judTitle to set
	 */
	public void setJudTitle(String judTitle) {
		this.judTitle = judTitle;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
