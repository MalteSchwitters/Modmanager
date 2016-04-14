package com.modmanager.ui.modlist;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.components.ComponentFactory;
import com.components.Fonts;
import com.components.TexturedPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.app.IconSet;
import com.modmanager.app.UISettings;
import com.modmanager.objects.Mod;

public class Modgroup_pnl extends JPanel {
	
	
	private ModPopupMenu_pop popup;
	private JTextPane tpnTitle;
	private JList<Mod> lstMods;
	private TexturedPanel pnlGroupHeader;
	private JCheckBox chkActivateAll;

	public Modgroup_pnl() {
		initialize();
	}

	private void initialize() {
		setOpaque(false);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("10dlu"), },
				new RowSpec[] { RowSpec.decode("20dlu"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));
		add(getPnlGroupHeader(), "1, 1, fill, fill");
		add(getLstMods(), "1, 3");
	}
	
	public ModPopupMenu_pop getPopup() {
		if (popup == null) {
			popup = new ModPopupMenu_pop();
		}
		return popup;
	}

	public JTextPane getTpnTitle() {
		if (tpnTitle == null) {
			tpnTitle = new JTextPane();
			tpnTitle.setForeground(UISettings.fontForegroundHighlight);
			tpnTitle.setFont(Fonts.Medium.getFont());
			tpnTitle.setOpaque(false);
			tpnTitle.setEditable(false);
			tpnTitle.setText("<groupname>");
		}
		return tpnTitle;
	}

	public JList<Mod> getLstMods() {
		if (lstMods == null) {
			lstMods = new JList<Mod>(new DefaultListModel<Mod>());
			lstMods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstMods.setOpaque(false);
		}
		return lstMods;
	}

	private TexturedPanel getPnlGroupHeader() {
		if (pnlGroupHeader == null) {
			pnlGroupHeader = new TexturedPanel();
			pnlGroupHeader.setTextureIcon(IconSet.getDarkenedPane());
			pnlGroupHeader.setOpaque(false);
			pnlGroupHeader.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("20dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
					new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, }));
			pnlGroupHeader.add(getChkActivateAll(), "2, 1, default, fill");
			pnlGroupHeader.add(getTpnTitle(), "4, 1, left, fill");
		}
		return pnlGroupHeader;
	}
	
	public JCheckBox getChkActivateAll() {
		if (chkActivateAll == null) {
			chkActivateAll = ComponentFactory.createCheckbox(null, "activate_all");
			chkActivateAll.setSelected(true);
			chkActivateAll.setBorder(new EmptyBorder(4, 0, 0, 0));
			chkActivateAll.setOpaque(false);
		}
		return chkActivateAll;
	}
}