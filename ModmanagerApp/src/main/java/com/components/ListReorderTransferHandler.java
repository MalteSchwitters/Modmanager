package com.components;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class ListReorderTransferHandler extends TransferHandler {
	
	
	private int[] indices = null;
	private int addIndex = -1;
	private int addCount = 0;

	private final DataFlavor localObjectFlavor;
	private Object[] transferedObjects = null;

	public ListReorderTransferHandler() {
		localObjectFlavor = new ActivationDataFlavor(Object[].class,
				DataFlavor.javaJVMLocalObjectMimeType, "Mod");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Transferable createTransferable(final JComponent c) {
		JList<?> list = (JList<?>) c;
		indices = list.getSelectedIndices();
		transferedObjects = list.getSelectedValues();
		return new DataHandler(transferedObjects, localObjectFlavor.getMimeType());
	}

	@Override
	public boolean canImport(final TransferSupport info) {
		return info.isDrop() && info.isDataFlavorSupported(localObjectFlavor);
	}

	@Override
	public int getSourceActions(final JComponent c) {
		return MOVE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importData(final TransferSupport info) {
		if (!canImport(info)) {
			return false;
		}
		JList<Object> target = (JList<Object>) info.getComponent();
		JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
		DefaultListModel<Object> listModel = (DefaultListModel<Object>) target.getModel();
		int index = dl.getIndex();
		int max = listModel.getSize();
		if (index < 0 || index > max) {
			index = max;
		}
		addIndex = index;
		try {
			Object[] values = (Object[]) info.getTransferable().getTransferData(localObjectFlavor);
			addCount = values.length;
			for (int i = 0; i < values.length; i++) {
				int idx = index++;
				listModel.add(idx, values[i]);
				target.addSelectionInterval(idx, idx);
			}
			return true;
		}
		catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void exportDone(final JComponent c, final Transferable data, final int action) {
		cleanup(c, action == MOVE);
	}

	private void cleanup(final JComponent c, final boolean remove) {
		if (remove && indices != null) {
			JList<?> source = (JList<?>) c;
			DefaultListModel<?> model = (DefaultListModel<?>) source.getModel();
			if (addCount > 0) {
				for (int i = 0; i < indices.length; i++) {
					if (indices[i] >= addIndex) {
						indices[i] += addCount;
					}
				}
			}
			for (int i = indices.length - 1; i >= 0; i--) {
				model.remove(indices[i]);
			}
		}
		indices = null;
		addCount = 0;
		addIndex = -1;
	}
}
