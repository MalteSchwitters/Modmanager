package com.modmanager.ui.installer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Moddetails_pnl extends JPanel {
	
	
	private JLabel lblFileName;
	private JLabel lblCategory;
	private JTextField textField;

	public Moddetails_pnl() {
		initialize();
	}

	private void initialize() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));
		add(getLblFileName(), "2, 2, right, default");
		add(getTextField(), "4, 2, fill, default");
		add(getLblCategory(), "2, 4");
	}

	private JLabel getLblFileName() {
		if (lblFileName == null) {
			lblFileName = new JLabel("File Name");
		}
		return lblFileName;
	}

	private JLabel getLblCategory() {
		if (lblCategory == null) {
			lblCategory = new JLabel("Category");
		}
		return lblCategory;
	}
	
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
}
