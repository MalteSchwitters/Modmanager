package com.modmanager.ui.modlist;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.components.ComponentFactory;

public class ModPopupMenu_pop extends JPopupMenu {
	
	
	private JMenuItem mniActivate;
	private JMenuItem mniDeactivate;
	private JMenuItem mniShowMore;
	private JMenuItem mniMoveUp;
	private JMenuItem mniMoveDown;
	private JMenuItem mniCopyName;
	private JMenuItem mniSetCategories;
	
	public ModPopupMenu_pop() {
		initialize();
	}

	private void initialize() {
		add(getMniActivate());
		add(getMniDeactivate());
		add(getMniMoveUp());
		add(getMniMoveDown());
		add(getMniSetCategories());
		add(getMniCopyName());
		add(getMniShowMore());
	}

	public JMenuItem getMniActivate() {
		if (mniActivate == null) {
			mniActivate = ComponentFactory.createMenuItem("activate", null);
		}
		return mniActivate;
	}

	public JMenuItem getMniDeactivate() {
		if (mniDeactivate == null) {
			mniDeactivate = ComponentFactory.createMenuItem("deactivate", null);
		}
		return mniDeactivate;
	}

	public JMenuItem getMniShowMore() {
		if (mniShowMore == null) {
			mniShowMore = ComponentFactory.createMenuItem("show_more_from_author", null);
		}
		return mniShowMore;
	}

	public JMenuItem getMniMoveUp() {
		if (mniMoveUp == null) {
			mniMoveUp = ComponentFactory.createMenuItem("move_up", null);
		}
		return mniMoveUp;
	}

	public JMenuItem getMniMoveDown() {
		if (mniMoveDown == null) {
			mniMoveDown = ComponentFactory.createMenuItem("move_down", null);
		}
		return mniMoveDown;
	}

	public JMenuItem getMniSetCategories() {
		if (mniSetCategories == null) {
			mniSetCategories = ComponentFactory.createMenuItem("set_category", null);
		}
		return mniSetCategories;
	}
	
	public JMenuItem getMniCopyName() {
		if (mniCopyName == null) {
			mniCopyName = ComponentFactory.createMenuItem("copy_name", null);
		}
		return mniCopyName;
	}
}
