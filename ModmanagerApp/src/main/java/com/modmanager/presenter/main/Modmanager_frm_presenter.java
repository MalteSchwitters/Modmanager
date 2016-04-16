package com.modmanager.presenter.main;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import com.components.AbstractPresenter;
import com.components.Fonts;
import com.modmanager.app.BackendManager;
import com.modmanager.app.IconSet;
import com.modmanager.app.Modmanager_app;
import com.modmanager.objects.Profile;
import com.modmanager.presenter.modlist.Modlist_pnl_presenter;
import com.modmanager.presenter.settings.Settings_pnl_presenter;
import com.modmanager.ui.main.Modmanager_frm;
import com.modmanager.ui.modlist.Modlist_pnl;
import com.modmanager.ui.settings.Settings_pnl;

public class Modmanager_frm_presenter extends AbstractPresenter {
	
	
	private static final Logger logger = Logger.getLogger(Modmanager_frm_presenter.class);
	private Image background;
	private Modlist_pnl_presenter preModlist;
	private Settings_pnl_presenter preSettings;

	public Modmanager_frm_presenter(final Modmanager_frm objViewContainer) {
		super(objViewContainer);
	}
	
	@Override
	public Modmanager_frm getView() {
		return (Modmanager_frm) getObjViewContainer();
	}
	
	@Override
	public void initialize() {
		// Not yet implemented
		getView().getMniDelete().setVisible(false);
		getView().getMniInstall().setVisible(false);
		getView().getPnlView().add(getPreModlist().getView(), BorderLayout.CENTER);
		super.initialize();
	}

	@Override
	public void registerActions() {
		getView().getMniPlay().addActionListener(e -> startGame());
		getView().getMniBackground().addActionListener(e -> editProfile());
		// TODO Delete profile
		// getView().getMniDelete().addActionListener(e ->
		// deleteActiveProfile());
		getView().getBtnStartGame().addActionListener(e -> startGame());
		getView().getBtnOpenGameFolder().addActionListener(e -> openInstallationFolder());
		getView().getBtnBrowseNexusmods().addActionListener(e -> browseModStore());
		getView().getBtnBrowseComunity().addActionListener(e -> browseComunity());
		getView().getBtnRefresh().addActionListener(e -> getPreModlist().refresh(true));
		getView().getBtnChangeProfile().addActionListener(e -> showProfiles());
		getView().getBtnOptions().addActionListener(e -> editProfile());
		getView().getMniNewProfile().addActionListener(e -> createProfile());
		getPreSettings().addPropertyChangeListener(e -> loadProfile((Profile) e.getNewValue()));
	}

	private Modlist_pnl_presenter getPreModlist() {
		if (preModlist == null) {
			preModlist = new Modlist_pnl_presenter(new Modlist_pnl());
			preModlist.initialize();
		}
		return preModlist;
	}
	
	private Settings_pnl_presenter getPreSettings() {
		if (preSettings == null) {
			preSettings = new Settings_pnl_presenter(new Settings_pnl());
			preSettings.initialize();
		}
		return preSettings;
	}

	private void loadProfileList(final Profile activeProfile) {
		getView().getPopChangeProfile().removeAll();
		for (Profile prof : BackendManager.getProfilesService().getProfiles()) {
			if (!prof.equals(activeProfile)) {
				JMenuItem mni = new JMenuItem(prof.getProfileName());
				mni.setFont(Fonts.Medium.getFont());
				mni.addActionListener(e -> loadProfile(prof));
				getView().getPopChangeProfile().add(mni);
			}
		}
		getView().getPopChangeProfile().add(getView().getMniNewProfile());
	}
	
	private void showProfiles() {
		// When first showing the popup it does not have a width. We open it and
		// close it again, so that we have a width next.
		if (getView().getPopChangeProfile().getWidth() == 0) {
			getView().getPopChangeProfile().show(getView().getBtnChangeProfile(), 0, 0);
			getView().getPopChangeProfile().setVisible(false);
		}
		int w = getView().getPopChangeProfile().getWidth();
		int x = getView().getBtnChangeProfile().getWidth() - w;
		int y = getView().getBtnChangeProfile().getHeight();
		getView().getPopChangeProfile().show(getView().getBtnChangeProfile(), x, y);
	}
	
	public void loadProfile(final Profile prof) {
		if (prof != null) {
			logger.debug("Loading profile " + prof.getProfileName());
			// Backend stuff
			BackendManager.getProfilesService().setActiveProfile(prof);
			BackendManager.initializeForGame(prof.getGame());
			saveIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
					"Application Properties", "profile", prof.getProfileName());
			// Update UI
			getView().getPnlView().removeAll();
			getView().getPnlView().add(getPreModlist().getView());
			getView().getBtnChangeProfile().setText(prof.getProfileName());
			background = IconSet.getBackground(prof.getBackground());
			if (background != null) {
				getView().getLblBackground().setIcon(new ImageIcon(background));
			}
			getView().setTitle(prof.getGame().toString() + " Modmanager");
			// Update Icons
			getView().getBtnStartGame().setIcon(IconSet.getSetDarkIcon("play.png", 24));
			getView().getBtnStartGame().setRolloverIcon(IconSet.getSetRolloverIcon("play.png", 24));
			getView().getBtnBrowseComunity().setIcon(IconSet.getSetDarkIcon("forum.png", 24));
			getView().getBtnBrowseComunity()
					.setRolloverIcon(IconSet.getSetRolloverIcon("forum.png", 24));
			getView().getBtnBrowseNexusmods().setIcon(IconSet.getSetDarkIcon("search.png", 24));
			getView().getBtnBrowseNexusmods()
					.setRolloverIcon(IconSet.getSetRolloverIcon("search.png", 24));
			getView().getBtnOpenGameFolder().setIcon(IconSet.getSetDarkIcon("folder.png", 24));
			getView().getBtnOpenGameFolder()
					.setRolloverIcon(IconSet.getSetRolloverIcon("folder.png", 24));
			getView().getBtnRefresh().setIcon(IconSet.getSetDarkIcon("refresh.png", 24));
			getView().getBtnRefresh()
					.setRolloverIcon(IconSet.getSetRolloverIcon("refresh.png", 24));
			getView().getBtnOptions().setIcon(IconSet.getSetDarkIcon("settings.png", 24));
			getView().getBtnOptions()
					.setRolloverIcon(IconSet.getSetRolloverIcon("settings.png", 24));
			
			getView().getBtnBrowseComunity().setVisible(prof.getComunity() != null);
			getView().getBtnBrowseNexusmods().setVisible(prof.getModstore() != null);
			
			getPreModlist().refresh(true);
			getView().repaint();
			getView().revalidate();
			loadProfileList(prof);
		}
	}
	
	private void editProfile() {
		getView().getPnlView().removeAll();
		getView().getPnlView().add(getPreSettings().getView());
		getPreSettings().loadProfile(BackendManager.getProfilesService().getActiveProfile());
		getView().getPnlView().repaint();
		getView().getPnlView().revalidate();
	}
	
	public void createProfile() {
		getView().getPnlView().removeAll();
		getView().getPnlView().add(getPreSettings().getView());
		getPreSettings().createProfile();
		getView().getPnlView().repaint();
		getView().getPnlView().revalidate();
		
	}
	
	private void startGame() {
		Profile profile = BackendManager.getProfilesService().getActiveProfile();
		try {
			logger.debug("Starting " + profile.getGame().toString());
			Desktop.getDesktop().open(profile.getExe());
		}
		catch (IOException e) {
			logger.error("Failed to start " + profile.getGame().toString(), e);
		}
	}
	
	private void openInstallationFolder() {
		Profile profile = BackendManager.getProfilesService().getActiveProfile();
		try {
			Desktop.getDesktop().open(new File(profile.getInstalldir()));
		}
		catch (IOException e) {
			logger.error("Failed to open " + profile.getInstalldir(), e);
		}
	}

	private void browseModStore() {
		Profile profile = BackendManager.getProfilesService().getActiveProfile();
		try {
			Desktop.getDesktop().browse(profile.getModstore());
		}
		catch (IOException e) {
			logger.error("Failed to open " + profile.getModstore(), e);
		}
	}
	
	private void browseComunity() {
		Profile profile = BackendManager.getProfilesService().getActiveProfile();
		try {
			Desktop.getDesktop().browse(profile.getComunity());
		}
		catch (IOException e) {
			logger.error("Failed to open " + profile.getComunity(), e);
		}
	}
}