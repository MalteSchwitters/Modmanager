package com.modmanager.presenter.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.components.AbstractPresenter;
import com.components.ComponentFactory;
import com.modmanager.app.BackendManager;
import com.modmanager.objects.Game;
import com.modmanager.objects.Profile;
import com.modmanager.ui.settings.Settings_pnl;

public class Settings_pnl_presenter extends AbstractPresenter {
	
	
	private static final Color invalidBackground = new Color(255, 102, 51);
	private static final Color defaultBackground = new JTextField().getBackground();
	private static final Logger logger = Logger.getLogger(Settings_pnl_presenter.class);
	private static final DecimalFormat df00 = new DecimalFormat("00");
	private Map<String, JButton> backgrounds = new HashMap<String, JButton>();
	private Profile profile;
	private String selectedBackground;
	private boolean uivalid;

	public Settings_pnl_presenter(final Settings_pnl view) {
		super(view);
	}

	@Override
	public Settings_pnl getView() {
		return (Settings_pnl) getObjViewContainer();
	}

	@Override
	public void registerActions() {
		getView().getBtnCancel().addActionListener(e -> firePropertyChange_Refresh(
				BackendManager.getProfilesService().getActiveProfile()));
		getView().getBtnSave().addActionListener(e -> saveProfile());

		getView().getMniUseBackground().addActionListener(e -> selectBackground_Popup());
		getView().getMniRemoveBackground().addActionListener(e -> deleteBackground_Popup());

		getView().getTxfInstallDir().addFocusListener(new ValidationListener());
		getView().getTxfExe().addFocusListener(new ValidationListener());
		getView().getBtnBrowseBackgrounds().addActionListener(e -> browseBackground());
		getView().getBtnBrowseExe().addActionListener(e -> browseExe(getView().getTxfExe()));
		getView().getBtnBrowseInstallDir()
				.addActionListener(e -> browseInstallFolder(getView().getTxfInstallDir()));
		getView().getCmbGame().addActionListener(e -> loadDefaults());
	}
	
	public void createProfile() {
		profile = new Profile();
		// This will set the cmb value and also trigger the ActionListener
		// calling the loadDefaults Method
		getView().getCmbGame().setSelectedIndex(0);
	}

	private void loadDefaults() {
		Game selectedGame = (Game) getView().getCmbGame().getSelectedItem();
		if (!selectedGame.equals(profile.getGame())) {
			Profile prof = BackendManager.getProfilesService().createDefaultProfile(selectedGame);
			if (prof != null) {
				loadProfile(prof);
			}
			else {
				getView().getTxfProfilename().setText("");
				getView().getTxfModDownloadPage().setText("");
				getView().getTxfComunity().setText("");
				getView().getTxfInstallDir().setText("");
				getView().getTxfExe().setText("");
				selectedBackground = "";
				loadBackgrounds(null);
				selectBackground(null);
			}
		}
	}

	public void loadProfile(final Profile prof) {
		profile = prof;
		getView().getTxfProfilename().setText(profile.getProfileName());
		getView().getTxfModDownloadPage().setText(profile.getModstore().toString());
		getView().getTxfComunity().setText(profile.getComunity().toString());
		getView().getTxfInstallDir().setText(profile.getInstalldir());
		getView().getTxfExe().setText(profile.getExe().getAbsolutePath());
		getView().getCmbGame().setSelectedItem(profile.getGame());
		selectedBackground = profile.getBackground();
		loadBackgrounds(profile.getBackground());
		validateAll();
	}
	
	private void saveProfile() {
		if (uivalid) {
			try {
				// TODO delete old profile, if name was changed
				profile.setProfileName(getView().getTxfProfilename().getText());
				profile.setModstore(new URI(getView().getTxfModDownloadPage().getText()));
				profile.setComunity(new URI(getView().getTxfComunity().getText()));
				profile.setInstalldir(getView().getTxfInstallDir().getText());
				profile.setExe(new File(getView().getTxfExe().getText()));
				profile.setBackground(selectedBackground);
				BackendManager.getProfilesService().saveProfile(profile);
				firePropertyChange_Refresh(profile);
			}
			catch (URISyntaxException e) {
				logger.error("Failed to save profile", e);
			}
		}
	}

	private void loadBackgrounds(final String selected) {
		try {
			getView().getPnlBackgrounds().removeAll();
			File backgrounddir = new File(getClass().getResource("/Icons/Backgrounds").toURI());
			backgrounds.clear();
			for (File file : backgrounddir.listFiles()) {
				if (file.getName().endsWith("_thumb.png") || file.getName().endsWith("_thumb.jpg")
						|| file.getName().endsWith("_thumb.jpeg")
						|| file.getName().endsWith("_thumb.gif")) {
					addImageThumb(file);
				}
				else {
					createImageThumb(file);
				}
			}
			getView().getPnlBackgrounds().repaint();
			getView().getPnlBackgrounds().revalidate();
			if (selected != null) {
				selectBackground(selected);
			}
		}
		catch (URISyntaxException e) {
			logger.error("Failed to load Background thumbs.", e);
		}
	}
	
	private void addImageThumb(final File file) {
		// Filename of the large image, thumbs are for better
		// performance and precreated
		StringBuilder stbImg = new StringBuilder();
		stbImg.append(file.getName().substring(0, file.getName().indexOf("_thumb")));
		stbImg.append(file.getName().substring(file.getName().lastIndexOf('.')));

		JButton btn = new JButton(new ImageIcon(file.getPath()));
		btn.setUI(new BasicButtonUI());
		btn.setToolTipText(stbImg.toString());
		btn.addActionListener(e -> selectBackground(stbImg.toString()));
		btn.setComponentPopupMenu(getView().getPopBackgrounds());
		getView().getPnlBackgrounds().add(btn, 0);
		backgrounds.put(stbImg.toString(), btn);
	}
	
	private void createImageThumb(final File file) {
		try {
			int suffixindex = file.getPath().lastIndexOf('.');
			String suffix = file.getPath().substring(suffixindex);
			String fname = file.getPath().substring(0, suffixindex);
			File outputfile = new File(fname + "_thumb" + suffix);
			if (!outputfile.exists()) {
				// read and scale the large image
				BufferedImage imgIn = ImageIO.read(file);
				double h = 100;
				double w = 180;
				Image img = imgIn.getScaledInstance((int) w, (int) h, Image.SCALE_SMOOTH);

				BufferedImage imgOut = new BufferedImage((int) w, (int) h, imgIn.getType());
				imgOut.getGraphics().drawImage(img, 0, 0, null);

				ImageIO.write(imgOut, suffix.substring(1), outputfile);
				addImageThumb(outputfile);
			}
		}
		catch (Exception e) {
			logger.error("Could not create Thumbnail for Image: " + file.getPath(), e);
		}
	}

	private void selectBackground(final String name) {
		for (JButton btn : backgrounds.values()) {
			btn.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		}
		if (name != null && backgrounds.containsKey(name)) {
			backgrounds.get(name).setBorder(new LineBorder(Color.ORANGE, 2));
			selectedBackground = name;
		}
	}

	private void selectBackground_Popup() {
		Component inv = getView().getPopBackgrounds().getInvoker();
		for (String bgname : backgrounds.keySet()) {
			if (inv.equals(backgrounds.get(bgname))) {
				selectBackground(bgname);
				break;
			}
		}
	}

	private void deleteBackground_Popup() {
		Component inv = getView().getPopBackgrounds().getInvoker();
		for (String bgname : backgrounds.keySet()) {
			if (inv.equals(backgrounds.get(bgname))) {
				if (!bgname.equals(selectedBackground)) {
					String path = getClass().getResource("/Icons/Backgrounds").getPath();
					String suffix = bgname.substring(bgname.indexOf('.'));
					String thumbname = bgname.substring(0, bgname.indexOf('.')) + "_thumb" + suffix;
					File flarge = new File(path + "/" + bgname);
					File fthumb = new File(path + "/" + thumbname);
					flarge.delete();
					fthumb.delete();
					loadBackgrounds(null);
					selectBackground(selectedBackground);
					break;
				}
				else {
					JOptionPane.showMessageDialog(getView(), ComponentFactory.getInstance()
							.getString("message_cant_delete_background"));
				}
			}
		}
	}

	private void browseInstallFolder(final JTextComponent target) {
		File f = new File(target.getText());
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(ComponentFactory.getInstance().getString("select_installdir"));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (f.exists()) {
			fc.setCurrentDirectory(f);
		}
		if (fc.showOpenDialog(getView()) == JFileChooser.APPROVE_OPTION) {
			target.setText(fc.getSelectedFile().getPath());
		}
	}
	
	private void browseExe(final JTextComponent target) {
		File f = new File(target.getText());
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(ComponentFactory.getInstance().getString("select_exe"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (f.exists()) {
			fc.setCurrentDirectory(f);
		}
		if (fc.showOpenDialog(getView()) == JFileChooser.APPROVE_OPTION) {
			target.setText(fc.getSelectedFile().getPath());
		}
	}

	private void browseBackground() {
		try {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(ComponentFactory.getInstance().getString("select_background"));
			fc.setFileFilter(
					new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes()));
			if (fc.showOpenDialog(getView()) == JFileChooser.APPROVE_OPTION) {
				int i = 1;
				String suffix = fc.getSelectedFile().getName();
				suffix = suffix.substring(suffix.lastIndexOf('.'));
				String path = getClass().getResource("/Icons/Backgrounds/").toURI().toString();
				path = path.substring(path.indexOf(":") + 1);
				File out = new File(path + "background" + df00.format(i) + suffix);
				while (out.exists()) {
					out = new File(path + "background" + df00.format(i++) + suffix);
				}
				Files.copy(fc.getSelectedFile().toPath(), out.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				loadBackgrounds(out.getName());
			}
		}
		catch (Exception e) {
			logger.error("Failed to copy add new background", e);
		}
	}
	
	private boolean validateAll() {
		uivalid = true;
		if (!validateFileExists(getView().getTxfExe())) {
			uivalid = false;
		}
		if (!validateFileExists(getView().getTxfInstallDir())) {
			uivalid = false;
		}
		return uivalid;
	}

	private boolean validateFileExists(final JTextComponent comp) {
		if (new File(comp.getText()).exists()) {
			comp.setBackground(defaultBackground);
			return true;
		}
		comp.setBackground(invalidBackground);
		return false;
	}

	private class ValidationListener extends FocusAdapter {
		
		
		@Override
		public void focusLost(final FocusEvent e) {
			validateAll();
		}
	}
}