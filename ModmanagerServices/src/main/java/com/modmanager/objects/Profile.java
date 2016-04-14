package com.modmanager.objects;

import java.io.File;
import java.net.URI;

public class Profile {
	
	
	private String profileName;
	private Game game;
	private File exe;
	private URI modstore;
	private URI comunity;
	private String installdir;
	private String appdatadir;
	private String iconset;
	private String background;
	
	public Profile() {
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(final String profileName) {
		this.profileName = profileName;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

	public File getExe() {
		return exe;
	}

	public void setExe(final File exe) {
		this.exe = exe;
	}

	public URI getModstore() {
		return modstore;
	}

	public void setModstore(final URI modstore) {
		this.modstore = modstore;
	}

	public String getInstalldir() {
		return installdir;
	}

	public void setInstalldir(final String installdir) {
		this.installdir = installdir;
	}

	public String getAppdatadir() {
		return appdatadir;
	}

	public void setAppdatadir(final String appdatadir) {
		this.appdatadir = appdatadir;
	}

	public String getIconset() {
		return iconset;
	}

	public void setIconset(final String iconset) {
		this.iconset = iconset;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(final String background) {
		this.background = background;
	}

	public URI getComunity() {
		return comunity;
	}

	public void setComunity(final URI comunity) {
		this.comunity = comunity;
	}
	
}
