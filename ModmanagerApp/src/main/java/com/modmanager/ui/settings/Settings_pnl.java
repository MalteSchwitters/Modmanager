package com.modmanager.ui.settings;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.components.ComponentFactory;
import com.components.Flowlayout4Scrollpane;
import com.components.Fonts;
import com.components.LightScrollPane;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.objects.Game;

public class Settings_pnl extends JPanel {
	
	
	private JLabel lblBackground;
	private JLabel lblInstallDir;
	private JLabel lblModDownloadPage;
	private JLabel lblGame;
	private JLabel lblProfilename;
	private JLabel lblExe;
	private JTextField txfProfilename;
	private JTextField txfModDownloadPage;
	private JTextField txfInstallDir;
	private JTextField txfExe;
	private JButton btnBrowseInstallDir;
	private JButton btnBrowseExe;
	private JComboBox<Game> cmbGame;
	private JTextField txfComunity;
	private JLabel lblCommunity;
	private JLabel lblProfilDetails;
	private JPanel pnlContent;
	private LightScrollPane spnContent;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel pnlBackgrounds;
	private JButton btnBrowseBackgrounds;
	private JPopupMenu popBackgrounds;
	private JMenuItem mniRemoveBackground;
	private JMenuItem mniUseBackground;
	
	public Settings_pnl() {
		initialize();
	}

	private void initialize() {
		setOpaque(false);
		setBackground(Color.DARK_GRAY);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("10dlu:grow"), ColumnSpec.decode("500px:grow"),
						FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						ColumnSpec.decode("10dlu"), ColumnSpec.decode("10dlu:grow"), },
				new RowSpec[] { RowSpec.decode("15dlu"), RowSpec.decode("default:grow"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("29px"),
						RowSpec.decode("10dlu"), }));
		add(getSpnContent(), "2, 2, 4, 1, fill, fill");
		add(getBtnSave(), "2, 4, right, top");
		add(getBtnCancel(), "4, 4, left, top");
	}

	private JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = ComponentFactory.createLabel("background");
			lblBackground.setFont(Fonts.Large.getFont());
			lblBackground.setForeground(Color.WHITE);
		}
		return lblBackground;
	}
	
	private JLabel getLblInstallDir() {
		if (lblInstallDir == null) {
			lblInstallDir = ComponentFactory.createLabel("installdir");
			lblInstallDir.setForeground(Color.WHITE);
		}
		return lblInstallDir;
	}

	private JLabel getLblModDownloadPage() {
		if (lblModDownloadPage == null) {
			lblModDownloadPage = ComponentFactory.createLabel("modstore");
			lblModDownloadPage.setForeground(Color.WHITE);
		}
		return lblModDownloadPage;
	}

	private JLabel getLblGame() {
		if (lblGame == null) {
			lblGame = ComponentFactory.createLabel("game");
			lblGame.setForeground(Color.WHITE);
		}
		return lblGame;
	}

	private JLabel getLblProfilename() {
		if (lblProfilename == null) {
			lblProfilename = ComponentFactory.createLabel("profilename");
			lblProfilename.setForeground(Color.WHITE);
		}
		return lblProfilename;
	}

	private JLabel getLblExe() {
		if (lblExe == null) {
			lblExe = ComponentFactory.createLabel("exe");
			lblExe.setForeground(Color.WHITE);
		}
		return lblExe;
	}
	
	public JTextField getTxfProfilename() {
		if (txfProfilename == null) {
			txfProfilename = new JTextField();
			txfProfilename.setColumns(10);
			txfProfilename.setFont(Fonts.Medium.getFont());
		}
		return txfProfilename;
	}
	
	public JTextField getTxfModDownloadPage() {
		if (txfModDownloadPage == null) {
			txfModDownloadPage = new JTextField();
			txfModDownloadPage.setColumns(10);
			txfModDownloadPage.setFont(Fonts.Medium.getFont());
		}
		return txfModDownloadPage;
	}
	
	public JTextField getTxfInstallDir() {
		if (txfInstallDir == null) {
			txfInstallDir = new JTextField();
			txfInstallDir.setColumns(10);
			txfInstallDir.setFont(Fonts.Medium.getFont());
		}
		return txfInstallDir;
	}
	
	public JTextField getTxfExe() {
		if (txfExe == null) {
			txfExe = new JTextField();
			txfExe.setColumns(10);
			txfExe.setFont(Fonts.Medium.getFont());
		}
		return txfExe;
	}
	
	public JButton getBtnBrowseInstallDir() {
		if (btnBrowseInstallDir == null) {
			btnBrowseInstallDir = ComponentFactory.createButton("...", "browse");
			btnBrowseInstallDir.setMargin(new Insets(2, 2, 2, 2));
			btnBrowseInstallDir.setOpaque(false);
			btnBrowseInstallDir.setFont(Fonts.Medium.getFont());
		}
		return btnBrowseInstallDir;
	}
	
	public JButton getBtnBrowseExe() {
		if (btnBrowseExe == null) {
			btnBrowseExe = ComponentFactory.createButton("...", "browse");
			btnBrowseExe.setMargin(new Insets(2, 2, 2, 2));
			btnBrowseExe.setOpaque(false);
			btnBrowseExe.setFont(Fonts.Medium.getFont());
		}
		return btnBrowseExe;
	}

	public JComboBox<Game> getCmbGame() {
		if (cmbGame == null) {
			cmbGame = new JComboBox<Game>();
			cmbGame.setModel(new DefaultComboBoxModel<Game>(Game.values()));
			cmbGame.setFont(Fonts.Medium.getFont());
		}
		return cmbGame;
	}
	
	public JTextField getTxfComunity() {
		if (txfComunity == null) {
			txfComunity = new JTextField();
			txfComunity.setColumns(10);
			txfComunity.setFont(Fonts.Medium.getFont());
		}
		return txfComunity;
	}
	
	private JLabel getLblCommunity() {
		if (lblCommunity == null) {
			lblCommunity = ComponentFactory.createLabel("community");
			lblCommunity.setForeground(Color.WHITE);
		}
		return lblCommunity;
	}

	private JLabel getLblProfilDetails() {
		if (lblProfilDetails == null) {
			lblProfilDetails = ComponentFactory.createLabel("profiledata");
			lblProfilDetails.setForeground(Color.WHITE);
			lblProfilDetails.setFont(Fonts.Large.getFont());
		}
		return lblProfilDetails;
	}
	
	private JPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new JPanel();
			pnlContent.setOpaque(false);
			pnlContent.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("10dlu"),
							ColumnSpec.decode("right:default"), FormSpecs.UNRELATED_GAP_COLSPEC,
							ColumnSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("20dlu"), ColumnSpec.decode("10dlu"), },
					new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC,
							FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
							FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("20dlu:grow"),
							FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), FormSpecs.UNRELATED_GAP_ROWSPEC,
							RowSpec.decode("20dlu"), RowSpec.decode("20dlu:grow"), }));
			pnlContent.add(getLblBackground(), "2, 1, left, default");
			pnlContent.add(getPnlBackgrounds(), "2, 3, 5, 1, fill, fill");
			pnlContent.add(getBtnBrowseBackgrounds(), "2, 5, 3, 1, left, default");
			pnlContent.add(getLblProfilDetails(), "2, 7, 5, 1, left, default");
			pnlContent.add(getLblProfilename(), "2, 9");
			pnlContent.add(getTxfProfilename(), "4, 9, 3, 1");
			pnlContent.add(getLblGame(), "2, 11");
			pnlContent.add(getCmbGame(), "4, 11, 3, 1, fill, default");
			pnlContent.add(getLblModDownloadPage(), "2, 13");
			pnlContent.add(getTxfModDownloadPage(), "4, 13, 3, 1");
			pnlContent.add(getLblCommunity(), "2, 15");
			pnlContent.add(getTxfComunity(), "4, 15, 3, 1");
			pnlContent.add(getLblInstallDir(), "2, 17");
			pnlContent.add(getTxfInstallDir(), "4, 17");
			pnlContent.add(getBtnBrowseInstallDir(), "6, 17");
			pnlContent.add(getLblExe(), "2, 19");
			pnlContent.add(getTxfExe(), "4, 19");
			pnlContent.add(getBtnBrowseExe(), "6, 19");
		}
		return pnlContent;
	}
	
	private LightScrollPane getSpnContent() {
		if (spnContent == null) {
			spnContent = new LightScrollPane();
			spnContent.setViewportView(getPnlContent());
			spnContent.getScrollPane().setOpaque(false);
			spnContent.getScrollPane().getViewport().setOpaque(false);
			spnContent.getScrollPane()
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return spnContent;
	}
	
	public JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = ComponentFactory.createButton("save_profile");
			btnSave.setFont(Fonts.Medium.getFont());
			btnSave.setOpaque(false);
		}
		return btnSave;
	}
	
	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = ComponentFactory.createButton("cancel");
			btnCancel.setFont(Fonts.Medium.getFont());
			btnCancel.setOpaque(false);
		}
		return btnCancel;
	}
	
	public JPanel getPnlBackgrounds() {
		if (pnlBackgrounds == null) {
			pnlBackgrounds = new JPanel();
			pnlBackgrounds.setOpaque(false);
			pnlBackgrounds.setLayout(new Flowlayout4Scrollpane(FlowLayout.LEFT, 5, 7));
		}
		return pnlBackgrounds;
	}

	public JButton getBtnBrowseBackgrounds() {
		if (btnBrowseBackgrounds == null) {
			btnBrowseBackgrounds = ComponentFactory.createButton("browse", "browse");
			btnBrowseBackgrounds.setFont(Fonts.Medium.getFont());
			btnBrowseBackgrounds.setOpaque(false);
		}
		return btnBrowseBackgrounds;
	}
	
	public JPopupMenu getPopBackgrounds() {
		if (popBackgrounds == null) {
			popBackgrounds = new JPopupMenu();
			popBackgrounds.add(getMniUseBackground());
			popBackgrounds.add(getMniRemoveBackground());
		}
		return popBackgrounds;
	}
	
	public JMenuItem getMniRemoveBackground() {
		if (mniRemoveBackground == null) {
			mniRemoveBackground = ComponentFactory.createMenuItem("delete_background", null);
		}
		return mniRemoveBackground;
	}
	
	public JMenuItem getMniUseBackground() {
		if (mniUseBackground == null) {
			mniUseBackground = ComponentFactory.createMenuItem("use_background", null);
		}
		return mniUseBackground;
	}
}
