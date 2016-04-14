package com.components;

import java.awt.Font;

public enum Fonts {
	
	Small(12), Medium(16), Large(22);
	
	private static final String defaultFont = "Calibri";
	private String fontname;
	private int size;

	private Fonts(final int size) {
		this.size = size;
		if (fontname == null) {
			fontname = defaultFont;
		}
	}

	public Font getFont() {
		return new Font(fontname, Font.PLAIN, size);
	}

	public Font getFontBold() {
		return new Font(fontname, Font.BOLD, size);
	}
	
	public Font getFontItalic() {
		return new Font(fontname, Font.ITALIC, size);
	}
}