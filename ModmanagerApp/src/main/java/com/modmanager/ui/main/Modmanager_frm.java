package com.modmanager.ui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.components.ComponentFactory;
import com.components.Fonts;
import com.components.TexturedPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.app.IconSet;
import com.modmanager.app.UISettings;

public class Modmanager_frm extends JFrame {
	
	
	private JPanel pnlBackground;
	private JLabel lblBackground;
	private JPanel pnlMain;
	private TexturedPanel pnlContent;
	private TexturedPanel pnlToolbar;
	private JButton btnStartGame;
	private JButton btnOpenGameFolder;
	private JButton btnBrowseNexusmods;
	private JButton btnOptions;
	private JPopupMenu popModmanager;
	private JMenuItem mniBackground;
	private JMenuItem mniPlay;
	private JMenuItem mniInstall;
	private JPanel pnlView;
	private JButton btnRefresh;
	private JButton btnChangeProfile;
	private JLabel lblCurrentProfile;
	private JPopupMenu popChangeProfile;
	private JMenuItem mniNewProfile;
	private JButton btnBrowseComunity;
	private JMenuItem mniDelete;

	public Modmanager_frm() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Fallout 4 Mod Manager");
		setIconImage(IconSet.getAppIcon());
		getContentPane().setLayout(new OverlayLayout(getContentPane()));
		getContentPane().add(getPnlMain());
		getContentPane().add(getPnlBackground());
		setSize(UISettings.width, UISettings.higth);
		setLocationRelativeTo(null);
	}

	public JPanel getPnlMain() {
		if (pnlMain == null) {
			pnlMain = new JPanel();
			pnlMain.setOpaque(false);
			addPopup(this, getPopModmanager());
			pnlMain.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("10dlu:grow"),
							ColumnSpec.decode("300dlu:grow(3)"), ColumnSpec.decode("10dlu:grow"), },
					new RowSpec[] { RowSpec.decode("10dlu"), RowSpec.decode("50px"),
							RowSpec.decode("15dlu"), RowSpec.decode("default:grow"),
							RowSpec.decode("5dlu"), }));
			pnlMain.add(getPnlToolbar(), "2, 2, fill, fill");
			pnlMain.add(getPnlContent(), "2, 4, fill, fill");
		}
		return pnlMain;
	}
	
	private JPanel getPnlBackground() {
		if (pnlBackground == null) {
			pnlBackground = new JPanel();
			pnlBackground.setLayout(new BorderLayout());
			pnlBackground.add(getLblBackground(), BorderLayout.CENTER);
		}
		return pnlBackground;
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setVerticalAlignment(SwingConstants.TOP);
			lblBackground.setHorizontalAlignment(SwingConstants.CENTER);
			lblBackground.setIcon(new ImageIcon(IconSet.getDefaultBackground()));
		}
		return lblBackground;
	}

	private TexturedPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new TexturedPanel();
			pnlContent.setTextureIcon(IconSet.getDarkenedPane());
			pnlContent.setOpaque(false);
			pnlContent.setBorder(new DropShadowBorder());
			pnlContent.setLayout(new BorderLayout(0, 0));
			pnlContent.add(getPnlView());
		}
		return pnlContent;
	}
	
	private TexturedPanel getPnlToolbar() {
		if (pnlToolbar == null) {
			pnlToolbar = new TexturedPanel();
			pnlToolbar.setOpaque(false);
			pnlToolbar.setBorder(new DropShadowBorder());
			pnlToolbar.setTextureIcon(IconSet.getDarkenedPane());
			pnlToolbar.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("10dlu"), ColumnSpec.decode("25dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
							FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("25dlu"),
							ColumnSpec.decode("4dlu:grow"), FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.UNRELATED_GAP_COLSPEC, },
					new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormSpecs.RELATED_GAP_ROWSPEC, }));
			pnlToolbar.add(getBtnStartGame(), "2, 2, fill, fill");
			pnlToolbar.add(getBtnRefresh(), "4, 2, fill, fill");
			pnlToolbar.add(getBtnBrowseNexusmods(), "6, 2, fill, fill");
			pnlToolbar.add(getBtnBrowseComunity(), "8, 2, fill, fill");
			pnlToolbar.add(getBtnOpenGameFolder(), "10, 2, fill, fill");
			pnlToolbar.add(getBtnOptions(), "12, 2, fill, fill");
			pnlToolbar.add(getLblCurrentProfile(), "14, 2, default, fill");
			pnlToolbar.add(getBtnChangeProfile(), "16, 2, fill, fill");
		}
		return pnlToolbar;
	}

	public JButton getBtnStartGame() {
		if (btnStartGame == null) {
			btnStartGame = ComponentFactory.createToolbarButton("tooltip.play");
			btnStartGame.setBorder(null);
			btnStartGame.setUI(new BasicButtonUI());
			btnStartGame.setOpaque(false);
		}
		return btnStartGame;
	}

	public JButton getBtnOpenGameFolder() {
		if (btnOpenGameFolder == null) {
			btnOpenGameFolder = ComponentFactory.createToolbarButton("tooltip.folder");
			btnOpenGameFolder.setBorder(null);
			btnOpenGameFolder.setUI(new BasicButtonUI());
			btnOpenGameFolder.setOpaque(false);
		}
		return btnOpenGameFolder;
	}

	public JButton getBtnBrowseNexusmods() {
		if (btnBrowseNexusmods == null) {
			btnBrowseNexusmods = ComponentFactory.createToolbarButton("tooltip.search");
			btnBrowseNexusmods.setBorder(null);
			btnBrowseNexusmods.setUI(new BasicButtonUI());
			btnBrowseNexusmods.setOpaque(false);
		}
		return btnBrowseNexusmods;
	}
	
	public JButton getBtnOptions() {
		if (btnOptions == null) {
			btnOptions = ComponentFactory.createToolbarButton("tooltip.settings");
			btnOptions.setBorder(null);
			btnOptions.setUI(new BasicButtonUI());
			btnOptions.setOpaque(false);
		}
		return btnOptions;
	}
	
	public JPopupMenu getPopModmanager() {
		if (popModmanager == null) {
			popModmanager = new JPopupMenu();
			popModmanager.add(getMniPlay());
			popModmanager.add(getMniInstall());
			popModmanager.add(getMniBackground());
			popModmanager.add(getMniDelete());
		}
		return popModmanager;
	}
	
	private static void addPopup(final Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mousePressed(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			@Override
			public void mouseReleased(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			private void showMenu(final MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public JMenuItem getMniBackground() {
		if (mniBackground == null) {
			mniBackground = ComponentFactory.createMenuItem("change_background", null);
		}
		return mniBackground;
	}
	
	public JMenuItem getMniPlay() {
		if (mniPlay == null) {
			mniPlay = ComponentFactory.createMenuItem("play_game", null);
		}
		return mniPlay;
	}
	
	public JMenuItem getMniInstall() {
		if (mniInstall == null) {
			mniInstall = ComponentFactory.createMenuItem("install_mod", null);
		}
		return mniInstall;
	}

	public JPanel getPnlView() {
		if (pnlView == null) {
			pnlView = new JPanel();
			pnlView.setOpaque(false);
			pnlView.setLayout(new BorderLayout(0, 0));
		}
		return pnlView;
	}
	
	public JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = ComponentFactory.createToolbarButton("tooltip.refresh");
			btnRefresh.setBorder(null);
			btnRefresh.setUI(new BasicButtonUI());
			btnRefresh.setOpaque(false);
		}
		return btnRefresh;
	}
	
	public JButton getBtnChangeProfile() {
		if (btnChangeProfile == null) {
			btnChangeProfile = ComponentFactory.createToolbarButton("tooltip.changeprofile");
			btnChangeProfile.setUI(new BasicButtonUI());
			btnChangeProfile.setHorizontalAlignment(SwingConstants.LEFT);
			btnChangeProfile.setHorizontalTextPosition(SwingConstants.LEADING);
			btnChangeProfile.setMargin(new Insets(0, 0, 0, 0));
			btnChangeProfile.setText("Profilename");
			btnChangeProfile.setFont(Fonts.Medium.getFont());
			btnChangeProfile.setForeground(Color.ORANGE);
			btnChangeProfile.setIconTextGap(0);
			btnChangeProfile.setBorder(null);
			btnChangeProfile.setOpaque(false);
			btnChangeProfile.setIcon(IconSet.getDarkIcon("down.png", 16));
			btnChangeProfile.setRolloverIcon(IconSet.getRolloverIcon("down.png", 16));
		}
		return btnChangeProfile;
	}
	
	private JLabel getLblCurrentProfile() {
		if (lblCurrentProfile == null) {
			lblCurrentProfile = ComponentFactory.createLabel("current_profile");
			lblCurrentProfile.setForeground(Color.WHITE);
		}
		return lblCurrentProfile;
	}

	public JPopupMenu getPopChangeProfile() {
		if (popChangeProfile == null) {
			popChangeProfile = new JPopupMenu();
			popChangeProfile.add(getMniNewProfile());
		}
		return popChangeProfile;
	}
	
	public JMenuItem getMniNewProfile() {
		if (mniNewProfile == null) {
			mniNewProfile = ComponentFactory.createMenuItem("create_profile", null);
		}
		return mniNewProfile;
	}
	
	public JButton getBtnBrowseComunity() {
		if (btnBrowseComunity == null) {
			btnBrowseComunity = ComponentFactory.createToolbarButton("tooltip.comunity");
			btnBrowseComunity.setBorder(null);
			btnBrowseComunity.setUI(new BasicButtonUI());
			btnBrowseComunity.setOpaque(false);
		}
		return btnBrowseComunity;
	}
	
	public JMenuItem getMniDelete() {
		if (mniDelete == null) {
			mniDelete = ComponentFactory.createMenuItem("delete_profile", null);
		}
		return mniDelete;
	}
}