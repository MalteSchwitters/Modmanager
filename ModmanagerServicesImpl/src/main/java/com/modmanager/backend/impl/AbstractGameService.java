package com.modmanager.backend.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.modmanager.backend.services.GameService;
import com.modmanager.objects.Game;
import com.modmanager.objects.Mod;

public abstract class AbstractGameService implements GameService {
	
	
	private static final Logger logger = Logger.getLogger(AbstractGameService.class);
	private String appPath;
	protected Game game;
	private boolean editLock;
	
	@Override
	public void setGame(final Game game) {
		this.game = game;
	}
	
	/**
	 * Reads extra data from a xml file in the installation folder of the game.
	 *
	 * @param mod
	 * @return mod filled with extra data
	 */
	@Override
	public Mod getExtraData(final Mod mod) {
		String filename = mod.getFile().getName();
		filename = filename.substring(0, filename.lastIndexOf('.')) + ".xml";
		String path = "/Games/" + game.toString() + "/" + filename;
		try {
			InputStream stream = getClass().getResourceAsStream(path);
			if (stream != null) {
				Element e = new SAXBuilder().build(stream).getRootElement();
				mod.setAuthor(e.getChild("author").getText());
				mod.setCategory(e.getChild("category").getText());
				mod.setName(e.getChild("name").getText());
				stream.close();
			}
			else {
				saveExtraData(mod, false);
			}
		}
		catch (IOException | JDOMException e) {
			// TODO Exceptionhandling
			e.printStackTrace();
		}
		return mod;
	}
	
	/**
	 * Saves user defined extra data to a xml file in the installation folder of
	 * the game. Extra data are 'Author name', 'Mod name' and 'Category'.
	 *
	 * @param mod
	 */
	@Override
	public void saveExtraData(final Mod mod) {
		saveExtraData(mod, true);
	}

	public void saveExtraData(final Mod mod, final boolean updatelist) {
		try {
			Element root = new Element("filedata");
			root.addContent(new Element("file").setText(mod.getFile().getName()));
			root.addContent(new Element("author").setText(mod.getAuthor()));
			root.addContent(new Element("name").setText(mod.getName()));
			root.addContent(new Element("category").setText(mod.getCategory()));

			String filename = mod.getFile().getName().substring(0,
					mod.getFile().getName().length() - 4) + ".xml";
			File outfile = new File(getAppPath() + "/Games/" + game.toString());
			outfile.mkdirs();
			outfile = new File(outfile.getAbsolutePath() + "/" + filename);

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			FileWriter writer = new FileWriter(outfile);
			xmlOutput.output(new Document(root), writer);
			writer.close();
			
			if (updatelist) {
				for (Mod m : getInstalledMods()) {
					if (m.equals(mod)) {
						m.setCategory(mod.getCategory());
						m.setName(mod.getName());
					}
				}
			}
		}
		catch (IOException e) {
			// TODO Exceptionhandling
			e.printStackTrace();
		}
	}

	protected String calculateModnameFromFile(final File modfile) {
		String filename = modfile.getName().substring(0, modfile.getName().indexOf('.'));
		try {
			StringBuilder stbName = new StringBuilder();

			int index = 0;
			char lastChar = '0';
			char currentChar = filename.charAt(index);
			stbName.append(Character.toUpperCase(currentChar));

			while (index + 1 < filename.length()) {
				index++;
				lastChar = currentChar;
				currentChar = filename.charAt(index);
				if (currentChar == '_') {
					stbName.append(" ");
				}
				else if (Character.isLowerCase(lastChar) && Character.isUpperCase(currentChar)) {
					stbName.append(" " + currentChar);
				}
				else {
					stbName.append(currentChar);
				}
			}
			return stbName.toString();
		}
		catch (Exception e) {
			// TODO Exceptionhandling
			e.printStackTrace();
		}
		return filename;
	}
	
	protected String getAppPath() {
		if (appPath == null) {
			try {
				appPath = URLDecoder.decode(
						ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UFT-8");
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return appPath;
	}

	protected void getEditLock() {
		while (editLock) {
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException e) {
				logger.warn("Error while geting edit lock", e);
			}
		}
		editLock = true;
	}
	
	protected void releaseEditLock() {
		editLock = false;
	}
}