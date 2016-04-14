package com.modmanager.presenter.message;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;

import com.modmanager.ui.message.Message_dlg;

public class MessageDialog {
	
	
	private Message_dlg_presenter preDialog;
	private Window owner;

	private MessageDialog(final Component comp) {
		this.owner = getNextWindow(comp);
		preDialog = new Message_dlg_presenter(new Message_dlg(owner));
		preDialog.getView().setLocationRelativeTo(owner);
		preDialog.initialize();
	}

	public static MessageDialog createInfoDialog(final Component owner) {
		// TODO set Info Icon
		return new MessageDialog(owner);
	}
	
	public static MessageDialog createErrorDialog(final Component owner) {
		// TODO set Error Icon
		return new MessageDialog(owner);
	}
	
	private Window getNextWindow(final Component comp) {
		if (comp != null) {
			if (comp instanceof Window) {
				return (Window) comp;
			}
			return getNextWindow(comp.getParent());
		}
		return null;
	}

	public MessageDialog title(final String title) {
		preDialog.setTitle(title);
		return this;
	}

	public MessageDialog message(final String message) {
		preDialog.setMessage(message);
		return this;
	}

	public MessageDialog details(final String details) {
		preDialog.setDetails(details);
		return this;
	}

	public MessageDialog details(final Exception e) {
		StringBuilder stbDetails = new StringBuilder();
		stbDetails.append(e.getMessage());
		for (StackTraceElement element : e.getStackTrace()) {
			if (element.getLineNumber() >= 0) {
				stbDetails.append("\n      at ");
				stbDetails.append(element.getClassName() + ".");
				stbDetails.append(element.getMethodName() + " (");
				stbDetails.append(element.getLineNumber() + ")");
			}
		}
		preDialog.setDetails(stbDetails.toString());
		preDialog.getView().getTpnDetails().setForeground(Color.RED);
		return this;
	}
	
	public Message_dlg_presenter show() {
		preDialog.setVisible(true);
		preDialog.getView().requestFocus();
		if (owner == null) {
			preDialog.getView().setAlwaysOnTop(true);
		}
		return preDialog;
	}
}
