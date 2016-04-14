package com.modmanager.presenter.modlist;

import javax.swing.DefaultListModel;

import com.components.AbstractPresenter;
import com.modmanager.app.BackendManager;
import com.modmanager.objects.Mod;
import com.modmanager.ui.modlist.Category_dlg;

public class Category_dlg_presenter extends AbstractPresenter {
	
	
	private Mod mod;
	
	public Category_dlg_presenter(final Category_dlg view) {
		super(view);
	}

	@Override
	public Category_dlg getView() {
		return (Category_dlg) getObjViewContainer();
	}

	@Override
	public void registerActions() {
		getView().getBtnSave().addActionListener(e -> saveCategory());
		getView().getBtnCancel().addActionListener(e -> cancel());
		getView().getTxfNewCategory().addActionListener(e -> saveCategory());
	}
	
	private void saveCategory() {
		if (!getView().getTxfNewCategory().getText().isEmpty()) {
			mod.setCategory(getView().getTxfNewCategory().getText());
			BackendManager.getGameService().saveExtraData(mod);
			firePropertyChange_Refresh(mod);
			getView().dispose();
		}
		else if (getView().getLstCategories().getSelectedValue() != null) {
			mod.setCategory(getView().getLstCategories().getSelectedValue());
			BackendManager.getGameService().saveExtraData(mod);
			firePropertyChange_Refresh(mod);
			getView().dispose();
		}
	}
	
	private void cancel() {
		getView().dispose();
	}

	private DefaultListModel<String> getListModel() {
		return (DefaultListModel<String>) getView().getLstCategories().getModel();
	}

	public void setMod(final Mod mod) {
		this.mod = mod;
		for (Mod m : BackendManager.getGameService().getInstalledMods()) {
			if (!getListModel().contains(m.getCategory())) {
				getListModel().addElement(m.getCategory());
			}
		}
		getView().getLstCategories().setSelectedValue(mod.getCategory(), true);
		getView().getTxfNewCategory().requestFocusInWindow();
	}
}