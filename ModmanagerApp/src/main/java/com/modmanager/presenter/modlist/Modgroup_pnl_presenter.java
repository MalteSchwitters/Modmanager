package com.modmanager.presenter.modlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.components.AbstractPresenter;
import com.modmanager.app.BackendManager;
import com.modmanager.app.UISettings;
import com.modmanager.objects.Mod;
import com.modmanager.ui.modlist.ModPopupMenu_pop;
import com.modmanager.ui.modlist.Mod_pnl;
import com.modmanager.ui.modlist.Modgroup_pnl;

public class Modgroup_pnl_presenter extends AbstractPresenter {
	
	
	private boolean enableLoadOrderSorting = false;
	private ModPopupMenu_pop_presenter prePopup;

	public Modgroup_pnl_presenter(final Modgroup_pnl objViewContainer) {
		super(objViewContainer);
	}

	@Override
	public Modgroup_pnl getView() {
		return (Modgroup_pnl) getObjViewContainer();
	}

	@Override
	public void initialize() {
		getView().getLstMods().setCellRenderer(new ModListCellRenderer<Mod>());
		// getView().getLstMods().setTransferHandler(new ModTransferHandler());
		super.initialize();
	}

	@Override
	public void registerActions() {
		ActionModClick mouseActions = new ActionModClick();
		getView().getLstMods().addMouseListener(mouseActions);
		getView().getLstMods().addMouseMotionListener(mouseActions);
		getView().getChkActivateAll().addActionListener(e -> activateAll());
		getPrePopup().addPropertyChangeListener(e -> firePropertyChange_Refresh(e.getNewValue()));
	}

	public void addMod(final Mod mod) {
		getListmodel().addElement(mod);
		getView().getChkActivateAll()
				.setSelected(getView().getChkActivateAll().isSelected() && mod.isActive());
	}

	public boolean hasMods() {
		return !getListmodel().isEmpty();
	}

	public void setEnableLoadOrderSorting(final boolean enableLoadOrderSorting) {
		this.enableLoadOrderSorting = enableLoadOrderSorting;
	}

	private void activateModAtPoint(final Mod mod) {
		boolean activating = !mod.isActive();
		BackendManager.getGameService().activateMod(mod, activating);
		if (activating && mod.getFile().getName().endsWith(".esm")) {
			setEsmLoadorderIndex(mod);
		}
		firePropertyChange_Refresh();
	}

	private void activateAll() {
		boolean activating = getView().getChkActivateAll().isSelected();
		for (Mod mod : Collections.list(getListmodel().elements())) {
			BackendManager.getGameService().activateMod(mod, activating);
			// move after last esm in load order
			if (activating && mod.getFile().getName().endsWith(".esm")) {
				setEsmLoadorderIndex(mod);
			}
		}
		firePropertyChange_Refresh();
	}

	private void setEsmLoadorderIndex(final Mod mod) {
		int esmindex = 0;
		for (String filename : BackendManager.getGameService().getActiveMods()) {
			if (!filename.endsWith(".esm")) {
				BackendManager.getGameService().setLoadIndex(mod, esmindex);
				break;
			}
			esmindex++;
		}
	}

	private DefaultListModel<Mod> getListmodel() {
		return (DefaultListModel<Mod>) getView().getLstMods().getModel();
	}

	private ModPopupMenu_pop_presenter getPrePopup() {
		if (prePopup == null) {
			prePopup = new ModPopupMenu_pop_presenter(new ModPopupMenu_pop());
			prePopup.initialize();
		}
		return prePopup;
	}

	private void showPopup(final Point p) {
		Mod mod = getListmodel().getElementAt(getView().getLstMods().locationToIndex(p));
		getPrePopup().show(getView().getLstMods(), (int) p.getX(), (int) p.getY(), mod);
	}

	private class ActionModClick extends MouseAdapter implements MouseMotionListener {
		
		
		private int startIndex = -1;
		private int dragedToIndex = -1;
		private Mod draggedMod;
		private boolean draging = false;

		@Override
		public void mouseReleased(final MouseEvent evt) {
			if (!hasLoadOrderChangedByDrag()) {
				if (evt.getButton() == MouseEvent.BUTTON1
						&& (evt.getX() < 30 || evt.getClickCount() >= 2)) {
					activateModAtPoint(getListmodel()
							.getElementAt(getView().getLstMods().locationToIndex(evt.getPoint())));
				}
				else if (evt.getButton() == MouseEvent.BUTTON3) {
					showPopup(evt.getPoint());
				}
			}
		}

		@Override
		public void mouseExited(final MouseEvent evt) {
			if (!getPrePopup().getView().isVisible()) {
				getView().getLstMods().clearSelection();
			}
		}

		@Override
		public void mouseDragged(final MouseEvent evt) {
			if (draging) {
				updateDrag(evt.getPoint());
			}
			else if (enableLoadOrderSorting) {
				startDrag(evt.getPoint());
			}
		}

		@Override
		public void mouseMoved(final MouseEvent evt) {
			if (!getPrePopup().getView().isVisible() && !draging) {
				Point p = new Point(evt.getX(), evt.getY());
				getView().getLstMods().setSelectedIndex(getView().getLstMods().locationToIndex(p));
			}
		}

		private void updateDrag(final Point p) {
			getView().getLstMods().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			int index = getView().getLstMods().locationToIndex(p);
			if (index != dragedToIndex) {
				boolean selfIsESM = draggedMod.getFile().getName().endsWith(".esm");
				boolean otherIsESM = getListmodel().getElementAt(index).getFile().getName()
						.endsWith(".esm");
				if (selfIsESM == otherIsESM) {
					getListmodel().removeElement(draggedMod);
					getListmodel().add(index, draggedMod);
					getView().getLstMods().setSelectedIndex(index);
					dragedToIndex = index;
				}
				else {
					getView().getLstMods().setSelectedIndex(dragedToIndex);
				}
			}
		}

		private void startDrag(final Point p) {
			startIndex = getView().getLstMods().locationToIndex(p);
			if (startIndex != 0) {
				draging = true;
				draggedMod = getListmodel().getElementAt(startIndex);
				dragedToIndex = startIndex;
			}
		}

		private boolean hasLoadOrderChangedByDrag() {
			boolean moved = false;
			if (draging) {
				if (dragedToIndex != startIndex) {
					draggedMod = BackendManager.getGameService().setLoadIndex(draggedMod,
							dragedToIndex);
					firePropertyChange_Refresh(draggedMod);
					moved = true;
				}
				draging = false;
				dragedToIndex = -1;
				startIndex = -1;
				getView().getLstMods().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			return moved;
		}
	}

	private class ModListCellRenderer<T> implements ListCellRenderer<Mod> {
		
		
		@Override
		public Component getListCellRendererComponent(final JList<? extends Mod> list,
				final Mod mod, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			
			Mod_pnl pnlMod = new Mod_pnl();

			pnlMod.getBtnLoadOrder()
					.setText(mod.getLoadindex() >= 0 ? String.valueOf(mod.getLoadindex()) : "");
			pnlMod.getBtnModName().setText(mod.getFile().getName());
			pnlMod.getBtnAuthor().setText(mod.getAuthor());
			pnlMod.getBtnInstallationDate().setText(mod.getLastModified());
			pnlMod.getChkActive().setSelected(mod.isActive());

			if (mod.isActive()) {
				Color foreground = UISettings.fontForeground;
				pnlMod.getBtnLoadOrder().setForeground(foreground);
				pnlMod.getBtnModName().setForeground(foreground);
				pnlMod.getBtnAuthor().setForeground(foreground);
				pnlMod.getBtnInstallationDate().setForeground(foreground);
			}
			pnlMod.setOpaque(isSelected);
			return pnlMod;
		}
	}
}