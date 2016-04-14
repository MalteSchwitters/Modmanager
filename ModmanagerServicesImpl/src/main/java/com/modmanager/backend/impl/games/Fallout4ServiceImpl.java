package com.modmanager.backend.impl.games;

import java.util.ArrayList;
import java.util.List;

import com.modmanager.backend.impl.AbstractBethesdaService;
import com.modmanager.objects.Mod;

public class Fallout4ServiceImpl extends AbstractBethesdaService {
	
	
	private List<String> officialFiles;
	
	@Override
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