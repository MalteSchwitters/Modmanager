package com.modmanager.ui.installer;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

import com.components.TexturedPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.modmanager.app.IconSet;

public class Installer_dlg extends JDialog {
	
	
	private JLabel lblBackground;
	private JPanel pnlbackground;
	private JPanel pnlContent;
	private TexturedPanel panel;
	private JPanel panel_1;
	private JButton btnContinue;
	private JButton btnBack;
	private JButton button;
	
	public Installer_dlg() {
		initialize();
	}
	
	private void initialize() {
		setTitle("Mod Installer Wizard");
		getContentPane().setLayout(new OverlayLayout(getContentPane()));
		getContentPane().add(getPnlContent());
		getContentPane().add(getPnlbackground());
	}
	
	public Installer_dlg(final Window owner) {
		super(owner);
	}

	private JPanel getPnlbackground() {
		if (pnlbackground == null) {
			pnlbackground = new JPanel();
			pnlbackground.setLayout(new BorderLayout());
			pnlbackground.add(getLblBackground(), BorderLayout.CENTER);
		}
		return pnlbackground;
	}
	
	private JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setVerticalAlignment(SwingConstants.TOP);
			lblBackground.setHorizontalAlignment(SwingConstants.LEFT);
			lblBackground.setIcon(new ImageIcon(IconSet.getWizardBackground()));
		}
		return lblBackground;
	}
	
	private JPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new JPanel();
			pnlContent.setBorder(null);
			pnlContent.setOpaque(false);
			pnlContent.setLayout(new FormLayout(
					new ColumnSpec[] { ColumnSpec.decode("150dlu"),
							ColumnSpec.decode("default:grow"), },
					new RowSpec[] { RowSpec.decode("50dlu:grow"), }));
			pnlContent.add(getPanel(), "2, 1, fill, fill");
		}
		return pnlContent;
	}

	private TexturedPanel getPanel() {
		if (panel == null) {
			panel = new TexturedPanel();
			panel.setOpaque(false);
			panel.setTextureIcon(IconSet.getDarkenedPane());
			panel.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC,
							ColumnSpec.decode("right:default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
							FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
							FormSpecs.DEFAULT_COLSPEC, FormSpecs.UNRELATED_GAP_COLSPEC, },
					new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.RELATED_GAP_ROWSPEC, }));
			panel.add(getPanel_1(), "2, 2, 5, 1, fill, fill");
			panel.add(getBtnBack(), "2, 4");
			panel.add(getButton(), "4, 4");
			panel.add(getBtnContinue(), "6, 4");
		}
		return panel;
	}
	
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setOpaque(false);
			panel_1.setLayout(new BorderLayout(0, 0));
		}
		return panel_1;
	}
	
	private JButton getBtnContinue() {
		if (btnContinue == null) {
			btnContinue = new JButton("Cancel");
			btnContinue.setOpaque(false);
		}
		return btnContinue;
	}
	
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.setOpaque(false);
		}
		return btnBack;
	}
	
	private JButton getButton() {
		if (button == null) {
			button = new JButton("Continue");
			button.setOpaque(false);
		}
		return button;
	}
}
