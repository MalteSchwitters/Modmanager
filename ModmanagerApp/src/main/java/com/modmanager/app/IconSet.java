package com.modmanager.app;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

public class IconSet {
	
	
	public static final int SIZE_SMALL = 16;
	public static final int SIZE_MEDIUM = 24;
	public static final int SIZE_LARGE = 32;
	
	private static final Logger logger = Logger.getLogger(IconSet.class);

	public static ImageIcon getIcon(final String path, final int size) {
		String icoset = BackendManager.getProfilesService().getActiveProfile().getIconset();
		return new ImageIcon(getScaledImage("/Icons/" + icoset + "/" + path, size));
	}

	public static ImageIcon getDarkIcon(final String path, final int size) {
		String icoset = BackendManager.getProfilesService().getActiveProfile().getIconset();
		return new ImageIcon(getScaledImage("/Icons/" + icoset + "/Dark/" + path, size));
	}

	public static ImageIcon getRolloverIcon(final String path, final int size) {
		String icoset = BackendManager.getProfilesService().getActiveProfile().getIconset();
		return new ImageIcon(getScaledImage("/Icons/" + icoset + "/Rollover/" + path, size));
	}
	
	public static Image getBackground(final String path) {
		Image img = getScaledImage("/Icons/Backgrounds/" + path, 0);
		return img != null ? img : getDefaultBackground();
	}

	public static Image getDefaultBackground() {
		return getScaledImage("/Icons/DefaultBackground.jpg", 0);
	}

	public static Image getWizardBackground() {
		return getScaledImage("/Icons/WizardBackground.jpg", 0);
	}

	public static Image getAppIcon() {
		return getScaledImage("/Icons/ModManager.png", 0);
	}
	
	public static ImageIcon getDarkenedPane() {
		return new ImageIcon(getScaledImage("/Icons/halfopague.png", 0));
	}
	
	private static Image getScaledImage(final String path, final int size) {
		try {
			Image ico = new ImageIcon(IconSet.class.getResource(path)).getImage();
			return size != 0 ? ico.getScaledInstance(size, size, Image.SCALE_SMOOTH) : ico;
		}
		catch (Exception e) {
			logger.error("Icon not found: " + path, e);
			return null;
		}
	}
}