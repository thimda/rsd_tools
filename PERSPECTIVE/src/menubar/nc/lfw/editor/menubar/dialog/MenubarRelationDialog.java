package nc.lfw.editor.menubar.dialog;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * 设置Menubar连接对话框
 * 
 * @author guoweic
 *
 */
public class MenubarRelationDialog extends DialogWithTitle {
	
	private Combo sourceItemCombo;
	private LfwElementObjWithGraph source;
	private MenuElementObj target;
	
	private MenuItem sourceItem = null;
	
	private final String SELECT_SENTENCE = "请选择";
	
	public MenubarRelationDialog(Shell parentShell, String title) {
		super(parentShell, title);

	}

	protected void okPressed() {
		if ("".equals(sourceItemCombo.getText()) || SELECT_SENTENCE.equals(sourceItemCombo.getText())) {
			MessageDialog.openInformation(this.getShell(), "提示", "请选择触发项");
			sourceItemCombo.setFocus();
			return;
		}
		sourceItem = (MenuItem) sourceItemCombo.getData(sourceItemCombo.getText());
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(450, 250);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(container, SWT.NONE).setText("触发项:");
		sourceItemCombo = new Combo(container, SWT.READ_ONLY);
		sourceItemCombo.setLayoutData(createGridData(135, 1));
		sourceItemCombo.add(SELECT_SENTENCE);
		sourceItemCombo.select(0);
		
		// 构造选项列表
		sourceItem = target.getMenuItem();
		List<MenuItem> sourceItemList = getUnusedItemList();
		for (int i = 0, n = sourceItemList.size(); i < n; i++) {
			sourceItemCombo.add(sourceItemList.get(i).getText());
			sourceItemCombo.setData(sourceItemList.get(i).getText(), sourceItemList.get(i));
		}
		if (null != sourceItem.getText())
			sourceItemCombo.select(sourceItemCombo.indexOf(sourceItem.getText()));
		
		return container;
	}
	
	/**
	 * 获取父菜单中的所有未使用项
	 * @return
	 */
	private List<MenuItem> getUnusedItemList() {
		List<MenuItem> list = new ArrayList<MenuItem>();
		List<MenuItem> itemList = null;
		if (source instanceof MenubarElementObj) {
			itemList = ((MenubarElementObj)source).getMenubar().getMenuList();
		} else if(source instanceof ContextMenuElementObj){
			itemList = ((ContextMenuElementObj)source).getMenubar().getItemList();
		}
		else if (source instanceof MenuElementObj) {
			itemList = ((MenuElementObj)source).getMenuItem().getChildList();
			
		}
		if (itemList.size() > 0) {
			for (int i = 0, n = itemList.size(); i < n; i++) {
				if ((null != itemList.get(i).getChildList() && itemList.get(i).getChildList().size() == 0) || sourceItem == itemList.get(i) || null == itemList.get(i).getChildList()) {
					list.add(itemList.get(i));
				}
			}
		}
		return list;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public MenuItem getSourceItem() {
		return sourceItem;
	}

	public void setSourceItem(MenuItem sourceItem) {
		this.sourceItem = sourceItem;
	}

	public void setSource(LfwElementObjWithGraph source) {
		this.source = source;
	}

	public void setTarget(MenuElementObj target) {
		this.target = target;
	}


}
