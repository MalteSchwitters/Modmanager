package com.modmanager.objects;

import java.io.File;

public class Mod {
	
	
	public static final String UNKNOWNAUTHOR = "Unknown Author";

	private boolean officialFile;
	private boolean active;
	private int loadindex;
	private String name;
	private String description;
	private String category;
	private String author;
	private String lastModified;
	private File file;
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(final boolean active) {
		this.active = active;
	}
	
	public boolean isOfficialFile() {
		return officialFile;
	}
	
	public void setOfficialFile(final boolean officialFile) {
		this.officialFile = officialFile;
	}
	
	public int getLoadindex() {
		return loadindex;
	}
	
	public void setLoadindex(final int loadindex) {
		this.loadindex = loadindex;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(final String author) {
		this.author = author;
	}
	
	public String getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(final String lastModified) {
		this.lastModified = lastModified;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(final File file) {
		this.file = file;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(final String category) {
		this.category = category;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + (officialFile ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Mod other = (Mod) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		}
		else if (!author.equals(other.author)) {
			return false;
		}
		if (file == null) {
			if (other.file != null) {
				return false;
			}
		}
		else if (!file.equals(other.file)) {
			return false;
		}
		if (officialFile != other.officialFile) {
			return false;
		}
		return true;
	}
}