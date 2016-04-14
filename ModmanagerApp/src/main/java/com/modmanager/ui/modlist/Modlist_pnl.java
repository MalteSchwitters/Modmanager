package com.modmanager.ui.modlist;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jdesktop.swingx.VerticalLayout;

import com.components.ComponentFactory;
import com.components.Fonts;
import com.components.LightScrollPane;
import com.components.TexturedPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.app.IconSet;

public class Modlist_pnl extends JPanel {
	
	
	private JPanel pnlToolbar;
	private LightScrollPane spnModlist;
	private JLabel lblGroupBy;
	private JPanel pnlMods;
	private JLabel lblManageYourInstalled;
	private JPanel pnlSummary;
	private JLabel lblActive;
	private JLabel lblInstalled;
	private JLabel lblCountActive;
	private JLabel lblCountInstalled;
	private Mod_pnl pnlHeader;
	private TexturedPanel pnlDarker;
	private JButton btnGroupBy;
	private JPopupMenu popGrouping;
	private JMenuItem mniNone;
	private JMenuItem mniCategory;
	private JMenuItem mniAuthor;
	
	public Modlist_pnl() {
		initialize();
	}
	
	private void initialize() {
		setOpaque(false);
		setBackground(Color.GRAY);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("12dlu"), ColumnSpec.decode("default:grow"),
						ColumnSpec.decode("10dlu"), ColumnSpec.decode("2dlu"), },
				new RowSpec[] { RowSpec.decode("10dlu"), FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("16dlu"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("10dlu"), }));
		add(getPnlToolbar(), "2, 2, fill, top");
		add(getPnlDarker(), "2, 3, fill, fill");
		add(getSpnModlist(), "2, 5, 2, 1, fill, fill");
		add(getPnlSummary(), "2, 6, fill, fill");
	}
	
	private JPanel getPnlToolbar() {
		if (pnlToolbar == null) {
			pnlToolbar = new JPanel();
			pnlToolbar.setOpaque(false);
			pnlToolbar.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.DEFAULT_COLSPEC, ColumnSpec.decode("20dlu:grow"),
							FormSpecs.DEFAULT_COLSPEC, FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
							FormSpecs.DEFAULT_COLSPEC, },
					new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("23px"),
							FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));
			pnlToolbar.add(getLblManageYourInstalled(), "1, 2");
			pnlToolbar.add(getLblGroupBy(), "3, 2, right, default");
			pnlToolbar.add(getBtnGroupBy(), "5, 2");
		}
		return pnlToolbar;
	}

	public LightScrollPane getSpnModlist() {
		if (spnModlist == null) {
			spnModlist = new LightScrollPane(getPnlMods());
			spnModlist.getScrollPane().setOpaque(false);
			spnModlist.getScrollPane().getViewport().setOpaque(false);
			spnModlist.setBorder(null);
			spnModlist.setOpaque(false);
		}
		return spnModlist;
	}

	private JLabel getLblGroupBy() {
		if (lblGroupBy == null) {
			lblGroupBy = ComponentFactory.createLabel("group_by");
			lblGroupBy.setForeground(Color.WHITE);
		}
		return lblGroupBy;
	}
	
	public JPanel getPnlMods() {
		if (pnlMods == null) {
			pnlMods = new JPanel();
			pnlMods.setOpaque(false);
			pnlMods.setLayout(new VerticalLayout());
		}
		return pnlMods;
	}

	private JLabel getLblManageYourInstalled() {
		if (lblManageYourInstalled == null) {
			lblManageYourInstalled = ComponentFactory.createLabel("manage_your_mods");
			lblManageYourInstalled.setFont(Fonts.Large.getFontBold());
			lblManageYourInstalled.setForeground(Color.WHITE);
		}
		return lblManageYourInstalled;
	}
	
	private JPanel getPnlSummary() {
		if (pnlSummary == null) {
			pnlSummary = new JPanel();
			pnlSummary.setOpaque(false);
			pnlSummary.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("default:grow"), FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
							ColumnSpec.decode("20dlu"), FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
					new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("20dlu"), }));
			pnlSummary.add(getLblCountActive(), "2, 2");
			pnlSummary.add(getLblActive(), "4, 2, fill, fill");
			pnlSummary.add(getLblCountInstalled(), "6, 2");
			pnlSummary.add(getLblInstalled(), "8, 2");
		}
		return pnlSummary;
	}
	
	private JLabel getLblActive() {
		if (lblActive == null) {
			lblActive = ComponentFactory.createLabel("mods_active");
			lblActive.setForeground(Color.WHITE);
		}
		return lblActive;
	}
	
	private JLabel getLblInstalled() {
		if (lblInstalled == null) {
			lblInstalled = ComponentFactory.createLabel("mods_installed");
			lblInstalled.setForeground(Color.WHITE);
		}
		return lblInstalled;
	}
	
	public JLabel getLblCountActive() {
		if (lblCountActive == null) {
			lblCountActive = new JLabel("0");
			lblCountActive.setFont(Fonts.Medium.getFont());
			lblCountActive.setForeground(Color.WHITE);
		}
		return lblCountActive;
	}
	
	public JLabel getLblCountInstalled() {
		if (lblCountInstalled == null) {
			lblCountInstalled = new JLabel("0");
			lblCountInstalled.setFont(Fonts.Medium.getFont());
			lblCountInstalled.setForeground(Color.WHITE);
		}
		return lblCountInstalled;
	}
	
	public Mod_pnl getPnlHeader() {
		if (pnlHeader == null) {
			pnlHeader = new Mod_pnl(true);
		}
		return pnlHeader;
	}
	
	private TexturedPanel getPnlDarker() {
		if (pnlDarker == null) {
			pnlDarker = new TexturedPanel();
			pnlDarker.setOpaque(false);
			pnlDarker.setTextureIcon(IconSet.getDarkenedPane());
			pnlDarker.setLayout(
					new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
							new RowSpec[] { RowSpec.decode("default:grow"), }));
			pnlDarker.add(getPnlHeader(), "1, 1, fill, center");
		}
		return pnlDarker;
	}
	
	public JButton getBtnGroupBy() {
		if (btnGroupBy == null) {
			btnGroupBy = ComponentFactory.createToolbarButton("tooltip.changeprofile");
			btnGroupBy.setHorizontalAlignment(SwingConstants.LEFT);
			btnGroupBy.setHorizontalTextPosition(SwingConstants.LEADING);
			btnGroupBy.setMargin(new Insets(0, 0, 0, 0));
			btnGroupBy.setText("group");
			btnGroupBy.setFont(Fonts.Medium.getFont());
			btnGroupBy.setForeground(Color.ORANGE);
			btnGroupBy.setIconTextGap(0);
			btnGroupBy.setBorder(null);
			btnGroupBy.setUI(new BasicButtonUI());
			btnGroupBy.setOpaque(false);
			btnGroupBy.setIcon(IconSet.getDarkIcon("down.png", 16));
			btnGroupBy.setRolloverIcon(IconSet.getRolloverIcon("down.png", 16));
		}
		return btnGroupBy;
	}

	public JPopupMenu getPopGrouping() {
		if (popGrouping == null) {
			popGrouping = new JPopupMenu();
			popGrouping.add(getMniNone());
			popGrouping.add(getMniCategory());
			popGrouping.add(getMniAuthor());
		}
		return popGrouping;
	}
	
	public JMenuItem getMniNone() {
		if (mniNone == null) {
			mniNone = ComponentFactory.createMenuItem("none", "none");
		}
		return mniNone;
	}

	public JMenuItem getMniCategory() {
		if (mniCategory == null) {
			mniCategory = ComponentFactory.createMenuItem("category", "category");
		}
		return mniCategory;
	}

	public JMenuItem getMniAuthor() {
		if (mniAuthor == null) {
			mniAuthor = ComponentFactory.createMenuItem("authorname", "authorname");
		}
		return mniAuthor;
	}
}