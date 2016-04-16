package com.modmanager.ui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.components.TexturedPanel;
import com.modmanager.app.IconSet;

public class Loading_frm extends JFrame {
	
	
	private JLabel lblModmanager;
	private JProgressBar progressbar;
	private JPanel pnlContent;
	private JPanel pnlBackground;
	private JLabel lblBackground;
	private TexturedPanel panel;
	
	public Loading_frm() throws HeadlessException {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(509, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		toFront();
		getContentPane().setLayout(new OverlayLayout(getContentPane()));
		getContentPane().add(getPnlContent());
		getContentPane().add(getPnlBackground());
	}
	
	private JLabel getLblModmanager() {
		if (lblModmanager == null) {
			lblModmanager = new JLabel("Modmanager");
			lblModmanager.setHorizontalAlignment(SwingConstants.CENTER);
			lblModmanager.setForeground(Color.WHITE);
			lblModmanager.setFont(new Font("Calibri", Font.BOLD, 40));
		}
		return lblModmanager;
	}

	public JProgressBar getProgressbar() {
		if (progressbar == null) {
			progressbar = new JProgressBar();
			progressbar.setForeground(Color.ORANGE);
			progressbar.setOpaque(false);
			progressbar.setBorder(new EmptyBorder(5, 5, 5, 5));
			progressbar.setPreferredSize(new Dimension(146, 30));
			progressbar.setValue(60);
		}
		return progressbar;
	}

	private JPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new JPanel();
			pnlContent.setOpaque(false);
			pnlContent.setLayout(new BorderLayout(0, 0));
			pnlContent.add(getProgressbar(), BorderLayout.SOUTH);
			pnlContent.add(getPanel(), BorderLayout.NORTH);
		}
		return pnlContent;
	}

	private JPanel getPnlBackground() {
		if (pnlBackground == null) {
			pnlBackground = new JPanel();
			pnlBackground.setLayout(new BorderLayout(0, 0));
			pnlBackground.add(getLblBackground(), BorderLayout.CENTER);
		}
		return pnlBackground;
	}

	private JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setHorizontalAlignment(SwingConstants.CENTER);
			lblBackground.setIcon(new ImageIcon(getClass().getResource("/Icons/Loadscreen.jpg")));
		}
		return lblBackground;
	}
	
	private TexturedPanel getPanel() {
		if (panel == null) {
			panel = new TexturedPanel();
			panel.setOpaque(false);
			panel.setTextureIcon(IconSet.getDarkenedPane());
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getLblModmanager(), BorderLayout.NORTH);
		}
		return panel;
	}
}
