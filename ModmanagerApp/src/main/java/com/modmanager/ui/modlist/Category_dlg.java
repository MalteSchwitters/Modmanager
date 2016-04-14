package com.modmanager.ui.modlist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import com.components.ComponentFactory;
import com.components.Fonts;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Category_dlg extends JDialog {
	
	
	private JPanel pnlButtons;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel pnlContent;
	private JList<String> lstCategories;
	private JTextField txfNewCategory;
	private JTextPane tpnMessageUpper;
	private JTextPane tpnMessageLower;
	private JScrollPane scrollPane;

	public Category_dlg() {
		initialize();
	}
	
	public Category_dlg(final Window owner) {
		super(owner);
		initialize();
	}
	
	private void initialize() {
		setTitle("Set mod category");
		setSize(374, 381);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnlButtons(), BorderLayout.SOUTH);
		getContentPane().add(getPnlContent(), BorderLayout.CENTER);
	}
	
	private JPanel getPnlButtons() {
		if (pnlButtons == null) {
			pnlButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnlButtons.getLayout();
			flowLayout.setVgap(10);
			flowLayout.setHgap(10);
			pnlButtons.add(getBtnSave());
			pnlButtons.add(getBtnCancel());
		}
		return pnlButtons;
	}

	public JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = ComponentFactory.createButton("save");
		}
		return btnSave;
	}

	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = ComponentFactory.createButton("cancel");
		}
		return btnCancel;
	}
	
	private JPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new JPanel();
			pnlContent.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("20dlu"), ColumnSpec.decode("50dlu:grow"),
							ColumnSpec.decode("20dlu"), },
					new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));
			pnlContent.add(getTpnMessageUpper(), "2, 2, fill, fill");
			pnlContent.add(getScrollPane(), "2, 4, fill, fill");
			pnlContent.add(getTpnMessageLower(), "2, 6, fill, fill");
			pnlContent.add(getTxfNewCategory(), "2, 8, fill, fill");
		}
		return pnlContent;
	}
	
	public JList<String> getLstCategories() {
		if (lstCategories == null) {
			lstCategories = new JList<String>();
			lstCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstCategories.setFont(Fonts.Medium.getFont());
			lstCategories.setModel(new DefaultListModel<String>());
			lstCategories.setCellRenderer(new DefaultListCellRenderer());
		}
		return lstCategories;
	}

	public JTextField getTxfNewCategory() {
		if (txfNewCategory == null) {
			txfNewCategory = new JTextField();
			txfNewCategory.setFont(Fonts.Medium.getFont());
			txfNewCategory.setColumns(10);
		}
		return txfNewCategory;
	}
	
	private JTextPane getTpnMessageUpper() {
		if (tpnMessageUpper == null) {
			tpnMessageUpper = new JTextPane();
			tpnMessageUpper.setBorder(null);
			tpnMessageUpper.setOpaque(false);
			tpnMessageUpper.setText(
					"Select a category for the mod {modname}. Each mod can only be in one category at the same time. You can either choose an already existing category...");
			tpnMessageUpper.setEditable(false);
			tpnMessageUpper.setFont(Fonts.Medium.getFont());
		}
		return tpnMessageUpper;
	}
	
	private JTextPane getTpnMessageLower() {
		if (tpnMessageLower == null) {
			tpnMessageLower = new JTextPane();
			tpnMessageLower.setBorder(null);
			tpnMessageLower.setText("...or create a new one");
			tpnMessageLower.setOpaque(false);
			tpnMessageLower.setFont(new Font("Calibri", Font.PLAIN, 16));
			tpnMessageLower.setEditable(false);
		}
		return tpnMessageLower;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getLstCategories());
		}
		return scrollPane;
	}
}
