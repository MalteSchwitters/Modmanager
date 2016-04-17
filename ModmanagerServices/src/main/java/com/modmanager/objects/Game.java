package com.modmanager.objects;

public enum Game {
	// FALLOUT3("Fallout 3"),
	// FALLOUTNV("Fallout New Vegas"),
	FALLOUT4("Fallout 4"), FALLOUT4_BETA("Fallout 4 Beta"), SKYRIM("Skyrim");

	private String name;
	
	private Game(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static Game fromString(final String name) {
		for (Game g : values()) {
			if (g.toString().equalsIgnoreCase(name)) {
				return g;
			}
		}
		return null;
	}
}