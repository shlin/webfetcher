package org.pstar.webfetcher.web.judicial.fjud.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class CheckBoxList extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 474069806000820940L;

	public CheckBoxList() {
		super();

		setModel(new DefaultListModel());
		setCellRenderer(new CheckboxCellRenderer());

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());

				if (index != -1) {
					Object obj = getModel().getElementAt(index);
					if (obj instanceof JCheckBox) {
						JCheckBox checkbox = (JCheckBox) obj;

						checkbox.setSelected(!checkbox.isSelected());
						repaint();
					}
				}
			}
		}

		);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@SuppressWarnings("unchecked")
	public int[] getCheckedIdexes() {
		java.util.List<Integer> list = new java.util.ArrayList<Integer>();
		DefaultListModel dlm = (DefaultListModel) getModel();
		for (int i = 0; i < dlm.size(); ++i) {
			Object obj = getModel().getElementAt(i);
			if (obj instanceof JCheckBox) {
				JCheckBox checkbox = (JCheckBox) obj;
				if (checkbox.isSelected()) {
					list.add(new Integer(i));
				}
			}
		}

		int[] indexes = new int[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			indexes[i] = list.get(i).intValue();
		}

		return indexes;
	}

	@SuppressWarnings("unchecked")
	public java.util.List<JCheckBox> getCheckedItems() {
		java.util.List<JCheckBox> list = new java.util.ArrayList<JCheckBox>();
		DefaultListModel dlm = (DefaultListModel) getModel();
		for (int i = 0; i < dlm.size(); ++i) {
			Object obj = getModel().getElementAt(i);
			if (obj instanceof JCheckBox) {
				JCheckBox checkbox = (JCheckBox) obj;
				if (checkbox.isSelected()) {
					list.add(checkbox);
				}
			}
		}
		return list;
	}
}