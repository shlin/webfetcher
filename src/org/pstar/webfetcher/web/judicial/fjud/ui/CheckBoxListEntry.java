package org.pstar.webfetcher.web.judicial.fjud.ui;

import javax.swing.JCheckBox;

public class CheckBoxListEntry extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4462667917117195848L;

	private boolean red = false;

	private Object value = null;

	public CheckBoxListEntry(Object itemValue, boolean selected) {
		super(itemValue == null ? "" : "" + itemValue, selected);
		setValue(itemValue);
	}

	public Object getValue() {
		return value;
	}

	public boolean isRed() {
		return red;
	}

	public boolean isSelected() {
		return super.isSelected();
	}

	public void setRed(boolean red) {
		this.red = red;
	}

	public void setSelected(boolean selected) {
		super.setSelected(selected);
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
