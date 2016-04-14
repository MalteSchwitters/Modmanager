package com.modmanager.ui.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;

import com.components.Fonts;
import com.components.LightScrollPane;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Message_dlg extends JDialog {
	
	
	private JPanel pnlButtons;
	private JButton btnOk;
	private JPanel pnlMessage;
	private JTextPane tpnMessage;
	private JTextPane tpnDetails;
	private JLabel lblIcon;
	private JTextPane tpnDetailsTitle;
	private JButton btnShowHideDetails;
	private LightScrollPane spnDetails;
	private JPanel pnlDetails;
	
	public Message_dlg() {
		initialize();
	}
	
	public Message_dlg(final Window owner) {
		super(owner);
		initialize();
	}
	
	private void initialize() {
		setTitle("Your Title will be here!");
		setSize(520, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnlButtons(), BorderLayout.SOUTH);
		getContentPane().add(getPnlMessage(), BorderLayout.CENTER);
	}
	
	private JPanel getPnlButtons() {
		if (pnlButtons == null) {
			pnlButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnlButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setVgap(10);
			pnlButtons.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
			pnlButtons.add(getBtnOk());
			pnlButtons.add(getBtnShowHideDetails());
		}
		return pnlButtons;
	}
	
	public JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setFont(Fonts.Medium.getFont());
		}
		return btnOk;
	}

	private JPanel getPnlMessage() {
		if (pnlMessage == null) {
			pnlMessage = new JPanel();
			pnlMessage.setBackground(Color.WHITE);
			pnlMessage.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
							FormSpecs.UNRELATED_GAP_COLSPEC, },
					new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							RowSpec.decode("default:grow"), FormSpecs.UNRELATED_GAP_ROWSPEC, }));
			pnlMessage.add(getLblIcon(), "2, 2, default, top");
			pnlMessage.add(getTpnMessage(), "4, 2, fill, fill");
			pnlMessage.add(getSpnDetails(), "1, 3, 5, 1, fill, fill");
		}
		return pnlMessage;
	}

	public JTextPane getTpnMessage() {
		if (tpnMessage == null) {
			tpnMessage = new JTextPane();
			tpnMessage.setText("Your Message will be here!");
			tpnMessage.setFont(Fonts.Medium.getFont());
			tpnMessage.setEditable(false);
			tpnMessage.setOpaque(false);
		}
		return tpnMessage;
	}

	public JTextPane getTpnDetails() {
		if (tpnDetails == null) {
			tpnDetails = new JTextPane();
			tpnDetails.setText("Your details, like exception codes, will be here!");
			tpnDetails.setOpaque(false);
			tpnDetails.setFont(Fonts.Small.getFont());
			tpnDetails.setEditable(false);
		}
		return tpnDetails;
	}
	
	private JLabel getLblIcon() {
		if (lblIcon == null) {
			lblIcon = new JLabel("");
		}
		return lblIcon;
	}
	
	private JTextPane getTpnDetailsTitle() {
		if (tpnDetailsTitle == null) {
			tpnDetailsTitle = new JTextPane();
			tpnDetailsTitle.setFont(Fonts.Small.getFont());
			tpnDetailsTitle.setEditable(false);
			tpnDetailsTitle.setOpaque(false);
			tpnDetailsTitle.setText("Details:");
		}
		return tpnDetailsTitle;
	}

	public JButton getBtnShowHideDetails() {
		if (btnShowHideDetails == null) {
			btnShowHideDetails = new JButton("Show details");
			btnShowHideDetails.setFont(Fonts.Medium.getFont());
		}
		return btnShowHideDetails;
	}
	
	public LightScrollPane getSpnDetails() {
		if (spnDetails == null) {
			spnDetails = new LightScrollPane();
			spnDetails.setBackground(Color.WHITE);
			spnDetails.setViewportView(getPnlDetails());
		}
		return spnDetails;
	}
	
	private JPanel getPnlDetails() {
		if (pnlDetails == null) {
			pnlDetails = new JPanel();
			pnlDetails.setBackground(Color.WHITE);
			pnlDetails.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC,
							ColumnSpec.decode("50dlu:grow"), FormSpecs.UNRELATED_GAP_COLSPEC, },
					new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));
			pnlDetails.add(getTpnDetailsTitle(), "2, 2");
			pnlDetails.add(getTpnDetails(), "2, 4");
		}
		return pnlDetails;
	}
}