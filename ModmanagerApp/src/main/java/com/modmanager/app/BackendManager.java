package com.modmanager.app;

import java.util.HashMap;
import java.util.Map;

import com.modmanager.backend.impl.ProfileServiceImpl;
import com.modmanager.backend.impl.games.Fallout4BetaServiceImpl;
import com.modmanager.backend.impl.games.Fallout4ServiceImpl;
import com.modmanager.backend.impl.games.SkyrimServiceImpl;
import com.modmanager.backend.services.GameService;
import com.modmanager.backend.services.ProfileService;
import com.modmanager.objects.Game;

public class BackendManager {
	
	
	private static Map<Game, GameService> mapServices = new HashMap<Game, GameService>();
	private static ProfileService profilesService;
	private static GameService currentService;

	public static void initializeForGame(final Game game) {
		
		if (!mapServices.containsKey(game)) {
			if (game.equals(Game.FALLOUT4)) {
				mapServices.put(game, createFallout4Service());
			}
			else if (game.equals(Game.FALLOUT4_BETA)) {
				mapServices.put(game, createFallout4BetaService());
			}
			else if (game.equals(Game.SKYRIM)) {
				mapServices.put(game, createSkyrimService());
			}
		}
		currentService = mapServices.get(game);
	}

	public static GameService getGameService() {
		return currentService;
	}
	
	public static ProfileService getProfilesService() {
		if (profilesService == null) {
			profilesService = new ProfileServiceImpl();
		}
		return profilesService;
	}
	
	private static GameService createFallout4Service() {
		GameService fallout4Service = new Fallout4ServiceImpl();
		fallout4Service.setGame(Game.FALLOUT4);
		fallout4Service.setInstallDir(getProfilesService().getActiveProfile().getInstalldir());
		fallout4Service.setAppDataDir(getProfilesService().getActiveProfile().getAppdatadir());
		return fallout4Service;
	}

	private static GameService createFallout4BetaService() {
		GameService fallout4Service = new Fallout4BetaServiceImpl();
		fallout4Service.setGame(Game.FALLOUT4_BETA);
		fallout4Service.setInstallDir(getProfilesService().getActiveProfile().getInstalldir());
		fallout4Service.setAppDataDir(getProfilesService().getActiveProfile().getAppdatadir());
		return fallout4Service;
	}

	private static GameService createSkyrimService() {
		GameService skyrimService = new SkyrimServiceImpl();
		skyrimService.setGame(Game.SKYRIM);
		skyrimService.setInstallDir(getProfilesService().getActiveProfile().getInstalldir());
		skyrimService.setAppDataDir(getProfilesService().getActiveProfile().getAppdatadir());
		return skyrimService;
	}
}