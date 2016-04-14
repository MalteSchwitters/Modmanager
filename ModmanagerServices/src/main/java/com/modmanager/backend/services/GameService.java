package com.modmanager.backend.services;

import java.util.List;

import com.modmanager.objects.Game;
import com.modmanager.objects.Mod;

public interface GameService {
	
	
	public void setInstallDir(final String installDir);
	
	public void setAppDataDir(final String appDataDir);
	
	public void setGame(final Game game);
	
	/**
	 * Reads extra data from a xml file in the installation folder of the game.
	 *
	 * @param mod
	 * @return mod filled with extra data
	 */
	public Mod getExtraData(final Mod mod);
	
	/**
	 * Saves user defined extra data to a xml file in the installation folder of
	 * the game. Extra data are 'Author name', 'Mod name' and 'Category'.
	 *
	 * @param mod
	 */
	public void saveExtraData(final Mod mod);
	
	public List<Mod> getInstalledMods();
	
	public List<String> getActiveMods();

	public Mod setLoadIndex(Mod mod, int newindex);

	public int getLoadIndex(final Mod modfile);

	public Mod moveUpInLoadorder(final Mod modfile);
	
	public Mod moveDownInLoadorder(final Mod modfile);
	
	public Mod activateMod(final Mod mod, final boolean active);
	
	public void deleteMod(final Mod mod);
	
	public void clearCache();
	
}
