package com.modmanager.backend.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.modmanager.backend.services.ProfileService;
import com.modmanager.exception.LoggedException;
import com.modmanager.objects.Game;
import com.modmanager.objects.Profile;

public class ProfileServiceImpl implements ProfileService {
	
	
	private static final Logger logger = Logger.getLogger(ProfileServiceImpl.class);
	private List<Profile> profiles;
	private Profile activeProfile;
	private String appPath;

	@Override
	public void setActiveProfile(final String name) {
		for (Profile profile : getProfiles()) {
			if (profile.getProfileName().equals(name)) {
				setActiveProfile(profile);
				return;
			}
		}
		logger.error("The profile " + name + " could not be found.");
	}

	@Override
	public void setActiveProfile(final Profile profile) {
		activeProfile = profile;
	}
	
	@Override
	public Profile getActiveProfile() {
		return activeProfile;
	}
	
	@Override
	public Profile getProfile(final String name) {
		for (Profile profile : getProfiles()) {
			if (profile.getProfileName().equals(name)) {
				return profile;
			}
		}
		return null;
	}

	@Override
	public List<Profile> getProfiles() {
		if (profiles == null) {
			profiles = new ArrayList<Profile>();
			try {
				File profdir = new File(getAppPath() + "Profiles/");
				for (File f : profdir.listFiles()) {
					if (!f.isDirectory()) {
						profiles.add(readProfile(f.getName()));
					}
				}
			}
			catch (Exception e) {
				logger.error("Failed to load profiles", e);
				throw new LoggedException(e);
			}
		}
		return profiles;
	}

	private Profile readProfile(final String profile) {
		try {
			File f = new File(getAppPath() + "Profiles/" + profile);
			if (f.exists()) {
				Element e = new SAXBuilder().build(f).getRootElement();
				Profile p = new Profile();

				p.setProfileName(f.getName().substring(0, f.getName().lastIndexOf('.')));
				p.setGame(Game.fromString(e.getChild("game").getText()));
				p.setInstalldir(e.getChild("installdir").getText());
				p.setAppdatadir(e.getChild("appdatadir").getText());
				p.setExe(new File(p.getInstalldir() + e.getChild("exe").getText()));
				p.setModstore(new URI(e.getChild("modstore").getText()));
				p.setComunity(new URI(e.getChild("comunity").getText()));
				p.setIconset(e.getChild("iconset").getText());
				p.setBackground(e.getChild("background").getText());

				return p;
			}
		}
		catch (Exception e) {
			logger.error("Failed to load profile " + profile, e);
			throw new LoggedException(e);
		}
		return null;
	}

	@Override
	public void saveProfile(final Profile profile) {
		try {
			logger.debug("Saving profile " + profile.getProfileName());
			Element root = new Element("profile");
			root.addContent(new Element("game").setText(profile.getGame().toString()));
			root.addContent(new Element("exe").setText(profile.getExe().getName()));
			root.addContent(new Element("installdir").setText(profile.getInstalldir()));
			root.addContent(new Element("appdatadir").setText(profile.getAppdatadir()));
			root.addContent(new Element("modstore").setText(profile.getModstore().toString()));
			root.addContent(new Element("comunity").setText(profile.getComunity().toString()));
			root.addContent(new Element("iconset").setText(profile.getIconset()));
			root.addContent(new Element("background").setText(profile.getBackground()));
			
			File outdir = new File(getAppPath() + "Profiles/");
			File outfile = new File(outdir.getPath() + "/" + profile.getProfileName() + ".xml");
			outfile.createNewFile();
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			FileWriter writer = new FileWriter(outfile);
			xmlOutput.output(new Document(root), writer);
			writer.close();
		}
		catch (Exception e) {
			logger.error("Failed to save profile " + profile.getProfileName(), e);
			throw new LoggedException(e);
		}
		// Force a reload next time the profiles list is used
		profiles = null;
	}

	@Override
	public Profile createDefaultProfile(final Game game) {
		String path = getAppPath() + "Profiles/Defaults/" + game.toString().replace(" ", "")
				+ "Defaults.xml";
		System.out.println(path);
		try {
			File f = new File(path);
			if (f.exists()) {
				Profile profile = new Profile();
				Element e = new SAXBuilder().build(f).getRootElement();
				profile.setProfileName("new " + game.toString() + " profile");
				profile.setGame(game);
				profile.setInstalldir(e.getChild("installdir").getText());
				profile.setAppdatadir(
						System.getProperty("user.home") + "/" + e.getChild("appdatadir").getText());
				profile.setExe(new File(profile.getInstalldir() + e.getChild("exe").getText()));
				profile.setModstore(new URI(e.getChild("modstore").getText()));
				profile.setComunity(new URI(e.getChild("comunity").getText()));
				profile.setIconset(e.getChild("iconset").getText());
				profile.setBackground(e.getChild("background").getText());
				return profile;
			}
		}
		catch (Exception e) {
			logger.error("Failed to load default profile for game " + game, e);
			throw new LoggedException(e);
		}
		return null;
	}
	
	@Override
	public Profile createStartupProfile() {
		Profile p = new Profile();
		p.setIconset("Default");
		p.setBackground("Background01.jpg");
		return p;
	}
	
	private String getAppPath() {
		if (appPath == null) {
			try {
				appPath = URLDecoder.decode(
						ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UFT-8");
				appPath = appPath.substring(1);
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return appPath;
	}
}
