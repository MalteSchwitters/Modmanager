package com.modmanager.backend.impl.games;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.modmanager.backend.impl.AbstractBethesdaService;
import com.modmanager.backend.impl.AbstractGameService;
import com.modmanager.exception.LoggedException;
import com.modmanager.objects.Mod;

public class Fallout4BetaServiceImpl extends AbstractGameService {
	
	
	private static final Logger logger = Logger.getLogger(AbstractBethesdaService.class);
	private long pluginsTxtReadAt = 0L;
	private List<String> officialFiles;
	private List<String> activemods;
	private List<Mod> installedMods;
	protected File installDir;
	protected File dataDir;
	protected File pluginstxt;

	// ==============================================
	// Methods that access the data
	// ==============================================

	@Override
	public void setInstallDir(final String installDir) {
		this.installDir = new File(installDir);
		this.dataDir = new File(installDir + "Data\\");
	}

	@Override
	public void setAppDataDir(final String appDataDir) {
		this.pluginstxt = new File(appDataDir + "/plugins.txt");
	}

	@Override
	public List<Mod> getInstalledMods() {
		if (installedMods == null) {
			installedMods = readInstalledMods();
		}
		return installedMods;
	}

	@Override
	public List<String> getActiveMods() {
		if (activemods == null) {
			activemods = readActiveMods();
		}
		return activemods;
	}

	public List<String> readActiveMods() {
		List<String> mods = new ArrayList<String>();
		for (String mod : readPluginsTxt()) {
			if (mod.startsWith("*")) {
				mods.add(mod.substring(1));
			}
		}
		return mods;
	}
	
	@Override
	public int getLoadIndex(final Mod mod) {
		return getActiveMods().indexOf(mod.getFile().getName());
	}

	@Override
	public void clearCache() {
		getEditLock();
		activemods = null;
		installedMods = null;
		setActiveModsLastRead(0L);
		releaseEditLock();
	}

	// ==============================================
	// Methods that change loadorder
	// ==============================================

	@Override
	public Mod setLoadIndex(final Mod mod, final int newindex) {
		getEditLock();
		if (hasActiveModsChanged()) {
			activemods = readActiveMods();
		}
		int index = activemods.indexOf(mod.getFile().getName());
		activemods.remove(index);
		activemods.add(newindex, mod.getFile().getName());
		mod.setLoadindex(newindex);
		updateLoadorder();
		savePluginsTxt(calcualteNewSettingsTxt());
		releaseEditLock();
		return mod;
	}

	@Override
	public Mod moveUpInLoadorder(final Mod mod) {
		return setLoadIndex(mod, activemods.indexOf(mod.getFile().getName()) - 1);
	}

	@Override
	public Mod moveDownInLoadorder(final Mod mod) {
		return setLoadIndex(mod, activemods.indexOf(mod.getFile().getName()) + 1);
	}

	@Override
	public Mod activateMod(final Mod mod, final boolean active) {
		getEditLock();
		if (hasActiveModsChanged()) {
			activemods = readActiveMods();
		}
		if (active) {
			activemods.add(mod.getFile().getName());
			mod.setLoadindex(activemods.size() - 1);
			mod.setActive(true);
		}
		else {
			List<String> temp = new ArrayList<String>();
			temp.addAll(activemods);
			activemods.clear();

			for (String line : temp) {
				if (!line.equals(mod.getFile().getName())) {
					activemods.add(line);
				}
			}
			mod.setActive(false);
			mod.setLoadindex(-1);
		}
		updateLoadorder();
		savePluginsTxt(calcualteNewSettingsTxt());
		releaseEditLock();
		return mod;
	}

	private List<String> calcualteNewSettingsTxt() {
		List<String> lisReturn = new ArrayList<String>();
		lisReturn.add("# This file is used by Fallout4 to keep track of your downloaded content.");
		lisReturn.add("# Please do not modify this file.");
		for (String l : activemods) {
			lisReturn.add("*" + l);
		}
		for (Mod mod : getInstalledMods()) {
			if (!activemods.contains(mod.getFile().getName())) {
				lisReturn.add(mod.getFile().getName());
			}
		}
		return lisReturn;
	}
	
	// ==============================================
	// Methods that read the files
	// ==============================================

	protected List<Mod> readInstalledMods() {
		List<Mod> mods = new ArrayList<Mod>();
		try {
			for (File modfile : dataDir.listFiles()) {
				if (modfile.getName().endsWith(".esp") || modfile.getName().endsWith(".esm")) {
					Mod mod = readDataFromFile(modfile);
					mod.setLoadindex(getLoadIndex(mod));
					mod.setActive(mod.getLoadindex() >= 0);
					if (!getOfficialFiles().contains(modfile.getName())) {
						mods.add(getExtraData(mod));
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("Could not read installed mods from " + dataDir.getPath(), e);
			throw new LoggedException(e);
		}
		return mods;
	}

	protected List<String> readPluginsTxt() {
		List<String> list = new ArrayList<String>();
		try {
			InputStreamReader inreader = new InputStreamReader(new FileInputStream(pluginstxt));
			BufferedReader bfreader = new BufferedReader(inreader);
			String line = bfreader.readLine();
			while (line != null) {
				if (!line.startsWith("#")) {
					list.add(line);
				}
				line = bfreader.readLine();
			}
			bfreader.close();
			setActiveModsLastRead(System.currentTimeMillis());
		}
		catch (Exception e) {
			logger.error("Could not read active mods from " + pluginstxt.getPath(), e);
			throw new LoggedException(e);
		}
		return list;
	}

	protected void savePluginsTxt(final List<String> plugins) {
		try {
			pluginstxt.delete();
			pluginstxt.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(pluginstxt));
			for (String l : plugins) {
				writer.write(l);
				writer.newLine();
			}
			writer.flush();
			writer.close();

			setActiveModsLastRead(System.currentTimeMillis());
		}
		catch (Exception e) {
			logger.error("Could not save file " + pluginstxt.getPath(), e);
			throw new LoggedException(e);
		}
	}

	protected boolean hasActiveModsChanged() {
		long l1 = pluginsTxtReadAt;
		long l2 = new File(pluginstxt.getPath()).lastModified();
		return l1 > l2;
	}

	protected void setActiveModsLastRead(final long millis) {
		pluginsTxtReadAt = millis;
	}

	protected Mod readDataFromFile(final File f) {
		Mod mod = new Mod();
		mod.setFile(f);
		mod.setDescription("");
		mod.setAuthor(Mod.UNKNOWNAUTHOR);
		mod.setLastModified(DateFormat.getDateTimeInstance().format(new Date(f.lastModified())));
		try {
			BufferedReader bfreader = new BufferedReader(
					new InputStreamReader(new FileInputStream(f)));
			String esp = bfreader.readLine() + bfreader.readLine();
			if (esp.contains("SNAM")) {
				int snamstart = esp.indexOf(0, esp.indexOf("SNAM")) + 1;
				int snamstop = esp.indexOf(0, snamstart);
				mod.setDescription(esp.substring(snamstart, snamstop));
			}
			if (esp.contains("CNAM")) {
				int cnamstart = esp.indexOf(0, esp.indexOf("CNAM")) + 1;
				int cnamstop = esp.indexOf(0, cnamstart);
				String author = esp.substring(cnamstart, cnamstop);
				mod.setAuthor(author != null && !author.isEmpty() ? author : Mod.UNKNOWNAUTHOR);
			}
			bfreader.close();
		}
		catch (Exception e) {
			logger.warn("Could not read data from file " + f.getName(), e);
		}
		return mod;
	}

	private void updateLoadorder() {
		for (Mod mod : getInstalledMods()) {
			mod.setLoadindex(getLoadIndex(mod));
		}
	}

	protected List<String> getOfficialFiles() {
		if (officialFiles == null) {
			officialFiles = new ArrayList<String>();
			officialFiles.add("Fallout4.esm");
			officialFiles.add("DLCRobot.esm");
			officialFiles.add("DLCworkshop01.esm");
		}
		return officialFiles;
	}

	@Override
	public void deleteMod(final Mod mod) {
		// TODO Auto-generated method stub

	}
}