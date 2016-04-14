package com.modmanager.presenter.modlist;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.components.AbstractPresenter;
import com.components.Actions;
import com.components.ComponentFactory;
import com.modmanager.app.BackendManager;
import com.modmanager.app.IconSet;
import com.modmanager.app.Modmanager_app;
import com.modmanager.exception.LoggedException;
import com.modmanager.objects.Mod;
import com.modmanager.presenter.message.MessageDialog;
import com.modmanager.ui.modlist.Modgroup_pnl;
import com.modmanager.ui.modlist.Modlist_pnl;

public class Modlist_pnl_presenter extends AbstractPresenter {
	
	
	private static final Logger logger = Logger.getLogger(Modlist_pnl_presenter.class);
	private static final ImageIcon icoDown = IconSet.getIcon("/down.png", 20);
	private static final ImageIcon icoUp = IconSet.getIcon("/drop.png", 20);

	private Sorting sortedBy;
	private Grouping groupedBy = Grouping.NONE;
	private List<Mod> mods;
	private Map<Sorting, JButton> mapSortButtons;
	private String unknownAuthor;
	private String officialFiles;
	private String activeFiles;
	private String inactiveFiles;
	private String noCategory;

	public Modlist_pnl_presenter(final Modlist_pnl view) {
		super(view);
	}

	@Override
	public Modlist_pnl getView() {
		return (Modlist_pnl) getObjViewContainer();
	}

	@Override
	public void initialize() {
		try {
			String ini = readIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
					"Application Properties", "groupby");
			groupedBy = Grouping.valueOf(ini);
			ini = readIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
					"Application Properties", "sortby");
			sortedBy = Sorting.valueOf(ini);
			ini = readIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
					"Application Properties", "sortdownwards");
			sortedBy.setDownwards(Boolean.valueOf(ini));
			ini = readIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
					"Application Properties", "updownwards");
			sortedBy.setUpwards(Boolean.valueOf(ini));
		}
		catch (Exception e) {
			logger.warn("Some ini preferences for sorting and grouping could not be read.", e);
		}

		unknownAuthor = ComponentFactory.getInstance().getString("unknown_author");
		activeFiles = ComponentFactory.getInstance().getString("active_files");
		inactiveFiles = ComponentFactory.getInstance().getString("inactive_files");
		noCategory = ComponentFactory.getInstance().getString("no_category");
		officialFiles = ComponentFactory.getInstance().getString("official_files");
		super.initialize();
	}

	@Override
	public void registerActions() {
		// Grouping
		getView().getMniNone().addActionListener(e -> refresh(Grouping.NONE));
		getView().getMniCategory().addActionListener(e -> refresh(Grouping.CATEGORY));
		getView().getMniAuthor().addActionListener(e -> refresh(Grouping.AUTHOR));
		getView().getBtnGroupBy().addActionListener(e -> showGroupingPopup());
		// Sorting
		getView().getPnlHeader().getBtnAuthor().addActionListener(e -> sortBy(Sorting.AUTHOR));
		getView().getPnlHeader().getBtnModName().addActionListener(e -> sortBy(Sorting.FILENAME));
		getView().getPnlHeader().getBtnLoadOrder()
				.addActionListener(e -> sortBy(Sorting.LOADORDER));
		getView().getPnlHeader().getBtnInstallationDate()
				.addActionListener(e -> sortBy(Sorting.INSTALLDATE));
	}

	private void refresh(final Grouping groupBy) {
		this.groupedBy = groupBy;
		saveIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
				"Application Properties", "groupby", groupBy.getValue());
		SwingUtilities.invokeLater(() -> scrollToTop());
		refresh(true);
	}
	
	public void refresh(final boolean clearCash) {
		try {
			if (clearCash) {
				BackendManager.getGameService().clearCache();
			}
			long t = System.currentTimeMillis();
			Rectangle rec = getView().getPnlMods().getVisibleRect();
			
			getView().getPnlMods().removeAll();
			getMods().clear();
			getMods().addAll(BackendManager.getGameService().getInstalledMods());
			Collections.sort(getMods(), new ModSorter());
			ComponentFactory.getInstance().updateText(getView().getBtnGroupBy(),
					groupedBy.toString());
			
			if (groupedBy.equals(Grouping.AUTHOR)) {
				fillModList(groupByAuthor(getMods()));
			}
			else if (groupedBy.equals(Grouping.CATEGORY)) {
				fillModList(groupByCategory(getMods()));
			}
			else if (groupedBy.equals(Grouping.NONE)) {
				fillModList(groupByNone(getMods()));
			}
			getView().getLblCountActive().setText(String.valueOf(calculateActiveMods(getMods())));
			getView().getLblCountInstalled().setText(String.valueOf(getMods().size()));

			SwingUtilities.invokeLater(() -> getView().getPnlMods().scrollRectToVisible(rec));
			logger.debug("Refresh Duration: " + (System.currentTimeMillis() - t) + " millis");
		}
		catch (LoggedException e) {
			MessageDialog dlg = MessageDialog.createErrorDialog(getView());
			dlg.details(e).title("Error whiel refreshing modlist");
			dlg.message(ComponentFactory.getInstance().getString("message_cant_load_modlist"));
			dlg.show();
		}
	}
	
	private void showGroupingPopup() {
		// When first showing the popup it does not have a width. We open it and
		// close it again, so that we have a width next.
		if (getView().getPopGrouping().getWidth() == 0) {
			getView().getPopGrouping().show(getView().getBtnGroupBy(), 0, 0);
			getView().getPopGrouping().setVisible(false);
		}
		int w = getView().getPopGrouping().getWidth();
		int x = getView().getBtnGroupBy().getWidth() - w;
		int y = getView().getBtnGroupBy().getHeight();
		getView().getPopGrouping().show(getView().getBtnGroupBy(), x, y);
	}
	
	private void scrollToTop() {
		getView().getSpnModlist().getVerticalScrollBar().setValue(0);
	}

	private void fillModList(final Map<String, List<Mod>> groups) {
		Modgroup_pnl_presenter first = null;
		List<Modgroup_pnl_presenter> middle = new ArrayList<Modgroup_pnl_presenter>();
		Modgroup_pnl_presenter last = null;
		
		List<String> categories = new ArrayList<String>(groups.keySet());
		Collections.sort(categories);
		for (String groupname : categories) {
			if (groupname.equals(officialFiles) || groupname.equals(activeFiles)) {
				first = createModGroup(groupname, groups.get(groupname));
				first.setEnableLoadOrderSorting(sortedBy.equals(Sorting.LOADORDER)
						&& sortedBy.isDownwards() && groupedBy.equals(Grouping.NONE));
			}
			else if (groupname.equals(unknownAuthor) || groupname.equals(inactiveFiles)
					|| groupname.equals(noCategory)) {
				last = createModGroup(groupname, groups.get(groupname));
			}
			else {
				middle.add(createModGroup(groupname, groups.get(groupname)));
			}
		}
		if (first != null && first.hasMods()) {
			getView().getPnlMods().add(first.getView());
		}
		for (Modgroup_pnl_presenter pre : middle) {
			if (pre.hasMods()) {
				getView().getPnlMods().add(pre.getView());
			}
		}
		if (last != null && last.hasMods()) {
			getView().getPnlMods().add(last.getView());
		}
		getView().getPnlMods().repaint();
		getView().getPnlMods().revalidate();
	}
	
	private Map<String, List<Mod>> groupByAuthor(final List<Mod> mods) {
		Map<String, List<Mod>> groups = new HashMap<String, List<Mod>>();
		groups.put(unknownAuthor, new ArrayList<Mod>());

		for (Mod mod : mods) {
			if (mod.getAuthor().equals(Mod.UNKNOWNAUTHOR)) {
				groups.get(unknownAuthor).add(mod);
			}
			else {
				if (!groups.containsKey(mod.getAuthor())) {
					groups.put(mod.getAuthor(), new ArrayList<Mod>());
				}
				groups.get(mod.getAuthor()).add(mod);
			}
		}
		return groups;
	}

	private Map<String, List<Mod>> groupByCategory(final List<Mod> mods) {
		Map<String, List<Mod>> groups = new HashMap<String, List<Mod>>();
		groups.put(officialFiles, new ArrayList<Mod>());
		groups.put(noCategory, new ArrayList<Mod>());

		for (Mod mod : mods) {
			if (mod.isOfficialFile()) {
				groups.get(officialFiles).add(mod);
			}
			else if (mod.getCategory() != null && !mod.getCategory().isEmpty()) {
				if (!groups.containsKey(mod.getCategory())) {
					groups.put(mod.getCategory(), new ArrayList<Mod>());
				}
				groups.get(mod.getCategory()).add(mod);
			}
			else {
				groups.get(noCategory).add(mod);
			}
		}
		return groups;
	}

	private Map<String, List<Mod>> groupByNone(final List<Mod> mods) {
		Map<String, List<Mod>> groups = new HashMap<String, List<Mod>>();
		groups.put(activeFiles, new ArrayList<Mod>());
		groups.put(inactiveFiles, new ArrayList<Mod>());
		for (Mod mod : mods) {
			if (mod.isActive()) {
				groups.get(activeFiles).add(mod);
			}
			else {
				groups.get(inactiveFiles).add(mod);
			}
		}
		return groups;
	}

	private Modgroup_pnl_presenter createModGroup(final String title, final List<Mod> mods) {
		Modgroup_pnl_presenter preGroup = new Modgroup_pnl_presenter(new Modgroup_pnl());
		preGroup.getView().getTpnTitle().setText(title);
		preGroup.initialize();
		preGroup.addPropertyChangeListener(Actions.REFRESH, e -> refresh(false));
		for (Mod mod : mods) {
			preGroup.addMod(mod);
		}
		return preGroup;
	}

	private int calculateActiveMods(final List<Mod> allmods) {
		int i = 0;
		for (Mod mod : allmods) {
			if (mod.isActive()) {
				i++;
			}
		}
		return i;
	}
	
	private void sortBy(final Sorting sort) {
		if (sort.equals(sortedBy)) {
			// Down -> Up
			if (sortedBy.isDownwards()) {
				sortedBy.setDownwards(false);
				sortedBy.setUpwards(true);
				getMapSortButtons().get(sortedBy).setIcon(icoUp);
			}
			// Up -> Default
			else if (sortedBy.isUpwards()) {
				sortedBy.setDownwards(false);
				sortedBy.setUpwards(false);
				getMapSortButtons().get(sortedBy).setIcon(null);
			}
			// Default -> Down
			else {
				sortedBy.setDownwards(true);
				sortedBy.setUpwards(false);
				getMapSortButtons().get(sortedBy).setIcon(icoDown);
			}
		}
		else {
			// other -> Down
			if (sortedBy != null) {
				getMapSortButtons().get(sortedBy).setIcon(null);
			}
			sortedBy = sort;
			sortedBy.setDownwards(true);
			sortedBy.setUpwards(false);
			getMapSortButtons().get(sortedBy).setIcon(icoDown);
		}
		saveIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
				"Application Properties", "sortby", sort.toString());
		saveIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
				"Application Properties", "sortdownwards", String.valueOf(sort.isDownwards()));
		saveIniValue(new File(Modmanager_app.getAppPath() + "/Modmanager.ini"),
				"Application Properties", "updownwards", String.valueOf(sort.isUpwards()));
		refresh(true);
	}

	private List<Mod> getMods() {
		if (mods == null) {
			mods = new ArrayList<Mod>();
		}
		return mods;
	}

	private Map<Sorting, JButton> getMapSortButtons() {
		if (mapSortButtons == null) {
			mapSortButtons = new HashMap<Sorting, JButton>();
			mapSortButtons.put(Sorting.AUTHOR, getView().getPnlHeader().getBtnAuthor());
			mapSortButtons.put(Sorting.FILENAME, getView().getPnlHeader().getBtnModName());
			mapSortButtons.put(Sorting.INSTALLDATE,
					getView().getPnlHeader().getBtnInstallationDate());
			mapSortButtons.put(Sorting.LOADORDER, getView().getPnlHeader().getBtnLoadOrder());
		}
		return mapSortButtons;
	}
	
	public enum Sorting {
		AUTHOR, FILENAME, LOADORDER, INSTALLDATE;
		
		private boolean upwards;
		private boolean downwards;

		private Sorting() {
			downwards = false;
			upwards = false;
		}

		public boolean isUpwards() {
			return upwards;
		}

		public boolean isDownwards() {
			return downwards;
		}
		
		public void setUpwards(final boolean upwards) {
			this.upwards = upwards;
		}
		
		public void setDownwards(final boolean downwards) {
			this.downwards = downwards;
		}
	}
	
	private class ModSorter implements Comparator<Mod> {
		
		
		@Override
		public int compare(final Mod mod1, final Mod mod2) {
			
			// Default
			if (sortedBy == null || !sortedBy.isDownwards() && !sortedBy.isUpwards()) {
				if (mod1.isOfficialFile() != mod2.isOfficialFile()) {
					return mod1.isOfficialFile() ? -1 : 1;
				}
				else if (mod1.isActive() != mod2.isActive()) {
					return mod1.isActive() ? -1 : 1;
				}
				else if (mod1.isOfficialFile() && mod2.isOfficialFile()) {
					return new Integer(mod1.getLoadindex()).compareTo(mod2.getLoadindex());
				}
			}
			else {
				// Authorname
				int a = 0;
				if (sortedBy.equals(Sorting.AUTHOR)) {
					a = mod1.getAuthor().toLowerCase().compareTo(mod2.getAuthor().toLowerCase());
				}
				// File name
				if (sortedBy.equals(Sorting.FILENAME)) {
					a = mod1.getFile().getName().toLowerCase()
							.compareTo(mod2.getFile().getName().toLowerCase());
				}
				// Instalation Date
				if (sortedBy.equals(Sorting.INSTALLDATE)) {
					a = new Long(mod1.getFile().lastModified())
							.compareTo(mod2.getFile().lastModified());
				}
				// Load order
				if (sortedBy.equals(Sorting.LOADORDER)) {
					if (mod1.getLoadindex() < 0 && mod2.getLoadindex() < 0) {
						return 0;
					}
					else if (mod1.getLoadindex() < 0) {
						return 1;
					}
					else if (mod2.getLoadindex() < 0) {
						return -1;
					}
					a = new Integer(mod1.getLoadindex()).compareTo(mod2.getLoadindex());
				}
				return sortedBy.isDownwards() ? a : (a * -1);
			}
			return mod1.getFile().getName().toLowerCase()
					.compareTo(mod2.getFile().getName().toLowerCase());
		}
	}
}