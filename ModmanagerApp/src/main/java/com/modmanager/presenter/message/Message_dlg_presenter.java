package com.modmanager.presenter.message;

import com.components.AbstractPresenter;
import com.components.ComponentFactory;
import com.modmanager.ui.message.Message_dlg;

public class Message_dlg_presenter extends AbstractPresenter {
	
	
	public Message_dlg_presenter(final Message_dlg view) {
		super(view);
	}

	@Override
	public Message_dlg getView() {
		return (Message_dlg) getObjViewContainer();
	}

	@Override
	public void initialize() {
		getView().getSpnDetails().setVisible(false);
		super.initialize();
	}

	@Override
	public void registerActions() {
		getView().getBtnOk().addActionListener(e -> getView().dispose());
		getView().getBtnShowHideDetails().addActionListener(e -> showDetails());
	}
	
	private void showDetails() {
		boolean show = getView().getSpnDetails().isVisible();
		if (show) {
			getView().getBtnShowHideDetails().setText("Hide details");
		}
		else {
			getView().getBtnShowHideDetails().setText("Show details");
		}
		getView().getSpnDetails().setVisible(!show);
	}

	public void setTitle(final String title) {
		getView().setTitle(ComponentFactory.getInstance().getString(title));
	}
	
	public void setMessage(final String message) {
		getView().getTpnMessage().setText(ComponentFactory.getInstance().getString(message));
	}

	public void setDetails(final String details) {
		getView().getTpnDetails().setText(ComponentFactory.getInstance().getString(details));
	}
}