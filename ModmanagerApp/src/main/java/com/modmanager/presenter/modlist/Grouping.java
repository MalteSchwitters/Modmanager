package com.modmanager.presenter.modlist;

import com.components.ComponentFactory;

public enum Grouping {
	NONE("none"), CATEGORY("category"), AUTHOR("authorname");

	private String key;
	
	private Grouping(final String key) {
		this.key = key;
	}

	public String getValue() {
		return super.toString();
	}

	@Override
	public String toString() {
		return ComponentFactory.getInstance().getString(key);
	}
}
