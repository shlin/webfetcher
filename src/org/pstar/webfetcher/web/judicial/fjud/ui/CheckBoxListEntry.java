package org.pstar.webfetcher.web.judicial.fjud.ui;

import javax.swing.JCheckBox;

public class CheckBoxListEntry extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4462667917117195848L;

	private Object value = null;

	private boolean red = false;

	public CheckBoxListEntry(Object itemValue, boolean selected) {
		super(itemValue == null ? "" : "" + itemValue, selected);
		setValue(itemValue);
	}

	public boolean isSelected() {
		return super.isSelected();
	}

	public void setSelected(boolean selected) {
		super.setSelected(selected);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isRed() {
		return red;
	}

	public void setRed(boolean red) {
		this.red = red;
	}
}
