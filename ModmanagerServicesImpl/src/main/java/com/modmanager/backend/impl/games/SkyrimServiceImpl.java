package com.modmanager.backend.impl.games;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.modmanager.backend.impl.AbstractBethesdaService;
import com.modmanager.objects.Mod;

public class SkyrimServiceImpl extends AbstractBethesdaService {
	
	
	private List<String> officialFiles;

	@Override
	protected List<String> getOfficialFiles() {
		if (officialFiles == null) {
			officialFiles = new ArrayList<String>();
			officialFiles.add("Skyrim.esm");
			officialFiles.add("Update.esm");
			officialFiles.add("Dawnguard.esm");
			officialFiles.add("Dragonborn.esm");
			officialFiles.add("Hearthfire.esm");
			officialFiles.add("HighResTexturePack01.esp");
			officialFiles.add("HighResTexturePack02.esp");
			officialFiles.add("HighResTexturePack03.esp");
		}
		return officialFiles;
	}

	@Override
	protected Mod readDataFromFile(final File f) {
		Mod mod = super.readDataFromFile(f);
		if (mod.getAuthor().equals("DEFAULT")) {
			mod.setAuthor(Mod.UNKNOWNAUTHOR);
		}
		return mod;
	}
	
	@Override
	public void deleteMod(final Mod mod) {
		// TODO
	}
}