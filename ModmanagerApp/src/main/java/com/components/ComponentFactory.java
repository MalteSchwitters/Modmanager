package com.components;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

public class ComponentFactory {
	
	
	private static final Logger logger = Logger.getLogger(ComponentFactory.class);
	private static ComponentFactory instance;
	private final Map<JComponent, String> mapTooltips = new HashMap<JComponent, String>();
	private final Map<JComponent, String> mapTexts = new HashMap<JComponent, String>();
	private Properties properties = new Properties();
	
	private ComponentFactory() {
		changeLanguage(new Locale("en"));
	}
	
	public void changeLanguage(final Locale language) {
		loadPropertiesForLanguage(language.getLanguage().toLowerCase());
		for (JComponent comp : mapTooltips.keySet()) {
			updateComponentText(comp);
		}
		for (JComponent comp : mapTexts.keySet()) {
			updateComponentTooltip(comp);
			comp.repaint();
		}
	}
	
	private void loadPropertiesForLanguage(final String language) {
		try {
			String filename = "/Localisation/Strings_" + language + ".properties";
			InputStream resourceStream = ComponentFactory.class.getResourceAsStream(filename);
			properties.load(resourceStream);
			resourceStream.close();
		}
		catch (Exception e) {
			logger.error("Could not read properties for language " + language
					+ ". Using default language en", e);
			loadPropertiesForLanguage("en");
		}
	}
	
	public String getString(final String key) {
		String value = properties.getProperty(key);
		return value != null ? value : key;
	}

	public void updateText(final JComponent comp, final String text) {
		mapTexts.put(comp, text);
		updateComponentText(comp);
	}
	
	public void updateTooltip(final JComponent comp, final String tooltip) {
		mapTooltips.put(comp, tooltip);
		updateComponentTooltip(comp);
	}
	
	public void updateComponentTooltip(final JComponent comp) {
		try {
			Method meth = comp.getClass().getMethod("setTooltipText");
			meth.invoke(comp, mapTooltips.get(comp));
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// ignore
		}
	}
	
	public void updateComponentText(final JComponent comp) {
		try {
			Method method = comp.getClass().getMethod("setText", String.class);
			method.invoke(comp, mapTexts.get(comp));
			comp.repaint();
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// ignore
		}
	}

	public static ComponentFactory getInstance() {
		if (instance == null) {
			instance = new ComponentFactory();
		}
		return instance;
	}
	
	public static JButton createButton(final String text) {
		return createButton(text, text);
	}
	
	public static JButton createButton(final String text, final String tooltip) {
		JButton btn = new JButton();
		btn.setFont(Fonts.Medium.getFont());
		if (text != null) {
			btn.setText(getInstance().getString(text));
			getInstance().mapTexts.put(btn, text);
		}
		if (tooltip != null) {
			btn.setToolTipText(getInstance().getString(tooltip));
			getInstance().mapTooltips.put(btn, tooltip);
		}
		return btn;
	}
	
	public static JButton createToolbarButton(final String tooltip) {
		JButton btn = new JButton();
		btn.setToolTipText(getInstance().getString(tooltip));
		getInstance().mapTooltips.put(btn, tooltip);
		return btn;
	}
	
	public static JLabel createLabel(final String text) {
		JLabel lbl = new JLabel();
		lbl.setFont(Fonts.Medium.getFont());
		lbl.setText(getInstance().getString(text));
		getInstance().mapTexts.put(lbl, text);
		return lbl;
	}
	
	public static JTextPane createTextpane(final String text) {
		JTextPane tpn = new JTextPane();
		tpn.setFont(Fonts.Medium.getFont());
		tpn.setText(getInstance().getString(text));
		getInstance().mapTexts.put(tpn, text);
		return tpn;
	}
	
	public static JMenuItem createMenuItem(final String text, final String tooltip) {
		JMenuItem mni = new JMenuItem();
		mni.setFont(Fonts.Medium.getFont());
		if (text != null) {
			mni.setText(getInstance().getString(text));
			getInstance().mapTexts.put(mni, text);
		}
		if (tooltip != null) {
			mni.setToolTipText(getInstance().getString(tooltip));
			getInstance().mapTooltips.put(mni, tooltip);
		}
		return mni;
	}
	
	public static JCheckBox createCheckbox(final String text, final String tooltip) {
		JCheckBox chkbox = new JCheckBox();
		chkbox.setFont(Fonts.Medium.getFont());
		if (text != null) {
			chkbox.setText(getInstance().getString(text));
			getInstance().mapTexts.put(chkbox, text);
		}
		if (tooltip != null) {
			chkbox.setToolTipText(getInstance().getString(tooltip));
			getInstance().mapTooltips.put(chkbox, tooltip);
		}
		return chkbox;
	}
}