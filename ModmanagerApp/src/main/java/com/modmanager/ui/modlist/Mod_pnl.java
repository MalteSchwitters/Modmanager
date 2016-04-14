package com.modmanager.ui.modlist;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import com.components.ComponentFactory;
import com.components.Fonts;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.app.UISettings;

public class Mod_pnl extends JPanel {
	
	
	private boolean header;
	private JCheckBox chkActive;
	private JButton btnModName;
	private JButton btnInstallationDate;
	private JButton btnLoadOrder;
	private JButton btnAuthor;

	public Mod_pnl() {
		this(false);
	}
	
	public Mod_pnl(final boolean isHeader) {
		header = isHeader;
		initialize();
	}

	private void initialize() {
		setOpaque(false);
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("60dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("20dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("20dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("right:50dlu"),
						FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, }));
		setBackground(UISettings.selectedFontBackground);
		add(getChkActive(), "2, 1");
		add(getBtnModName(), "4, 1, left, bottom");
		add(getBtnAuthor(), "6, 1, left, default");
		add(getBtnInstallationDate(), "8, 1, left, bottom");
		add(getBtnLoadOrder(), "10, 1, default, bottom");
	}

	public JButton getBtnModName() {
		if (btnModName == null) {
			btnModName = new JButton("<name>");
			if (isHeader()) {
				btnModName = ComponentFactory.createButton("name", "sort_by_name");
				btnModName.setFont(Fonts.Small.getFont());
			}
			btnModName.setIconTextGap(0);
			btnModName.setUI(new BasicButtonUI());
			btnModName.setHorizontalTextPosition(SwingConstants.LEADING);
			btnModName.setHorizontalAlignment(SwingConstants.LEADING);
			btnModName.setFont(Fonts.Medium.getFont());
			btnModName.setForeground(Color.LIGHT_GRAY);
			btnModName.setOpaque(false);
			btnModName.setMargin(new Insets(0, 0, 0, 0));
			btnModName.setBorder(null);
		}
		return btnModName;
	}

	public JButton getBtnInstallationDate() {
		if (btnInstallationDate == null) {
			btnInstallationDate = new JButton("<installed>");
			if (isHeader()) {
				btnInstallationDate = ComponentFactory.createButton("installdate",
						"sort_by_installdate");
				btnInstallationDate.setFont(Fonts.Small.getFont());
			}
			btnInstallationDate.setIconTextGap(0);
			btnInstallationDate.setUI(new BasicButtonUI());
			btnInstallationDate.setHorizontalTextPosition(SwingConstants.LEADING);
			btnInstallationDate.setHorizontalAlignment(SwingConstants.LEADING);
			btnInstallationDate.setFont(Fonts.Medium.getFont());
			btnInstallationDate.setForeground(Color.LIGHT_GRAY);
			btnInstallationDate.setOpaque(false);
			btnInstallationDate.setMargin(new Insets(0, 0, 0, 0));
			btnInstallationDate.setBorder(null);
		}
		return btnInstallationDate;
	}

	public JButton getBtnLoadOrder() {
		if (btnLoadOrder == null) {
			btnLoadOrder = new JButton("<order>");
			if (isHeader()) {
				btnLoadOrder = ComponentFactory.createButton("loadorder", "sort_by_loadorder");
				btnLoadOrder.setFont(Fonts.Small.getFont());
			}
			btnLoadOrder.setIconTextGap(0);
			btnLoadOrder.setUI(new BasicButtonUI());
			btnLoadOrder.setHorizontalTextPosition(SwingConstants.LEADING);
			btnLoadOrder.setHorizontalAlignment(SwingConstants.LEADING);
			btnLoadOrder.setFont(Fonts.Medium.getFont());
			btnLoadOrder.setForeground(Color.LIGHT_GRAY);
			btnLoadOrder.setOpaque(false);
			btnLoadOrder.setMargin(new Insets(0, 0, 0, 0));
			btnLoadOrder.setBorder(null);
		}
		return btnLoadOrder;
	}

	public JCheckBox getChkActive() {
		if (chkActive == null) {
			chkActive = new JCheckBox();
			chkActive.setBorder(new EmptyBorder(2, 0, 0, 0));
			chkActive.setOpaque(false);
			chkActive.setVisible(!isHeader());
		}
		return chkActive;
	}
	
	public JButton getBtnAuthor() {
		if (btnAuthor == null) {
			btnAuthor = new JButton("<author>");
			if (isHeader()) {
				btnAuthor = ComponentFactory.createButton("author", "sort_by_author");
				btnAuthor.setFont(Fonts.Small.getFont());
			}
			btnAuthor.setUI(new BasicButtonUI());
			btnAuthor.setIconTextGap(0);
			btnAuthor.setHorizontalTextPosition(SwingConstants.LEADING);
			btnAuthor.setHorizontalAlignment(SwingConstants.LEADING);
			btnAuthor.setFont(Fonts.Medium.getFont());
			btnAuthor.setForeground(Color.LIGHT_GRAY);
			btnAuthor.setOpaque(false);
			btnAuthor.setMargin(new Insets(0, 0, 0, 0));
			btnAuthor.setBorder(null);
		}
		return btnAuthor;
	}
	
	public boolean isHeader() {
		return header;
	}
}
