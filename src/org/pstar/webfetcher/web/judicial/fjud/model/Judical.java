package org.pstar.webfetcher.web.judicial.fjud.model;

import java.util.UUID;

public class Judical {

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
	public Judical(String court, String date, String judId, String judTitle, String content) {
		super();
		this.category = "刑事";
		this.court = court;
		this.date = date;
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
