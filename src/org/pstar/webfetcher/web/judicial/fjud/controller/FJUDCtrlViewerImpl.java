package org.pstar.webfetcher.web.judicial.fjud.controller;

import javax.swing.DefaultListModel;

import org.pstar.webfetcher.web.judicial.fjud.ui.CheckBoxListEntry;

public interface FJUDCtrlViewerImpl {

	/**
	 * @param actionCommand
	 *            "0" for sql , "1" for mysql
	 */
	public void doChangeOutputType(boolean type);

	public void doFetch();

	public void doStop();
	
	public void doPaintShowContent();

	public DefaultListModel<CheckBoxListEntry> getCourtListModel();
}
