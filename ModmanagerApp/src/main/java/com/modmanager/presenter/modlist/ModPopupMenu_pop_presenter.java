package com.modmanager.presenter.modlist;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JFrame;

import com.components.AbstractPresenter;
import com.modmanager.app.BackendManager;
import com.modmanager.objects.Mod;
import com.modmanager.ui.modlist.Category_dlg;
import com.modmanager.ui.modlist.ModPopupMenu_pop;

public class ModPopupMenu_pop_presenter extends AbstractPresenter {
	
	
	private Window owner;
	private Category_dlg_presenter preCategory;
	private Mod mod;
	
	public ModPopupMenu_pop_presenter(final ModPopupMenu_pop objViewContainer) {
		super(objViewContainer);
	}

	@Override
	public ModPopupMenu_pop getView() {
		return (ModPopupMenu_pop) getObjViewContainer();
	}

	@Override
	public void registerActions() {
		getView().getMniActivate().addActionListener(e -> activated(true));
		getView().getMniDeactivate().addActionListener(e -> activated(false));
		getView().getMniShowMore().addActionListener(e -> showMoreMods());
		getView().getMniCopyName().addActionListener(e -> copyFilename());
		getView().getMniSetCategories().addActionListener(e -> chooseCategory());
		getView().getMniMoveUp().addActionListener(e -> moveUp());
		getView().getMniMoveDown().addActionListener(e -> moveDown());
		getPreCategory().addPropertyChangeListener(e -> firePropertyChange_Refresh());
	}
	
	public void show(final Component invoker, final int x, final int y, final Mod mod) {
		this.mod = mod;
		owner = getOwnerwindow(invoker);
		getView().getMniActivate().setVisible(!mod.isActive());
		getView().getMniDeactivate().setVisible(mod.isActive() && mod.getLoadindex() != 0);
		getView().getMniSetCategories().setEnabled(!mod.isOfficialFile());
		getView().getMniShowMore().setEnabled(!mod.getAuthor().equals(Mod.UNKNOWNAUTHOR));
		
		List<String> loadOrder = BackendManager.getGameService().getActiveMods();
		boolean canMoveUp = mod.getLoadindex() > 1;
		boolean canMoveDown = mod.getLoadindex() > 0 && mod.getLoadindex() < loadOrder.size() - 1;
		boolean isEsm = mod.getFile().getName().endsWith(".esm");
		boolean nextIsEsm = canMoveDown && loadOrder.get(mod.getLoadindex() + 1).endsWith(".esm");
		boolean prevIsEsm = canMoveUp && loadOrder.get(mod.getLoadindex() - 1).endsWith(".esm");
		
		getView().getMniMoveUp().setEnabled(isEsm == prevIsEsm && canMoveUp);
		getView().getMniMoveDown().setEnabled(isEsm == nextIsEsm && canMoveDown);
		getView().show(invoker, x, y);
	}
	
	private Window getOwnerwindow(final Component comp) {
		if (comp instanceof Window) {
			return (Window) comp;
		}
		else if (comp.getParent() != null) {
			return getOwnerwindow(comp.getParent());
		}
		return new JFrame();
	}

	private void activated(final boolean act) {
		mod = BackendManager.getGameService().activateMod(mod, act);
		firePropertyChange_Refresh(mod);
	}

	private void showMoreMods() {
		try {
			StringBuilder stbURI = new StringBuilder();
			stbURI.append("http://www.nexusmods.com/fallout4/mods/searchresults/?src_order");
			stbURI.append("=2&src_sort=0&src_view=1&src_tab=1&src_language=0&src_auth=");
			stbURI.append(mod.getAuthor());
			stbURI.append("&page=1&pUp=1");
			String uri = stbURI.toString().replace(" ", "%20");
			Desktop.getDesktop().browse(new URI(uri));
		}
		catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void copyFilename() {
		StringSelection selection = new StringSelection(mod.getFile().getName());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}
	
	private void moveUp() {
		mod = BackendManager.getGameService().moveUpInLoadorder(mod);
		firePropertyChange_Refresh(mod);
	}

	private void moveDown() {
		mod = BackendManager.getGameService().moveDownInLoadorder(mod);
		firePropertyChange_Refresh(mod);
	}
	
	private void chooseCategory() {
		getPreCategory().setMod(mod);
		getPreCategory().getView().setLocationRelativeTo(owner);
		getPreCategory().setVisible(true);
	}
	
	private Category_dlg_presenter getPreCategory() {
		if (preCategory == null) {
			preCategory = new Category_dlg_presenter(new Category_dlg(owner));
			preCategory.initialize();
		}
		return preCategory;
	}
}