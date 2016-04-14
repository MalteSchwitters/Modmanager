package com.modmanager.backend.services;

import java.util.List;

import com.modmanager.objects.Game;
import com.modmanager.objects.Profile;

public interface ProfileService {
	
	
	public List<Profile> getProfiles();

	public Profile getProfile(String name);
	
	public void saveProfile(Profile profile);

	public void setActiveProfile(String string);
	
	public void setActiveProfile(Profile profile);

	public Profile getActiveProfile();
	
	public Profile createDefaultProfile(Game game);
	
	public Profile createStartupProfile();
	
}
