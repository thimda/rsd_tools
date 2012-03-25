package nc.uap.lfw.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.ref.RefDatasetData;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 从公共池中引用widget
 * @author zhangxya
 *
 */
public class WidgetFromPoolDialog extends DialogWithTitle {

	private Tree tree = null;
	private TreeViewer tv = null;
	private LfwWidget widget = null;
		
	public WidgetFromPoolDialog(Shell parentShell, String title) {
		super(parentShell,title);
	}
	
	public LfwWidget getSelectedWidget(){
		return widget;
	}
	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
	protected void okPressed() {
		TreeItem[] items = tree.getSelection();
		if(items != null && items.length > 0){
			TreeItem item = items[0];
			LfwWidget widget = (LfwWidget)item.getData();
			setWidget(widget);
			if(item != null){
				super.okPressed();
			}
		}else{
			MessageDialog.openConfirm(this.getShell(), "提示", "请选择一个引用的" + LFWPerspectiveNameConst.WIDGET_CN);
			tree.setFocus();
		}
	}
	
	protected Point getInitialSize() {
		return new Point(350,500); 
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		tv = new TreeViewer(container, SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		tree = tv.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		PoolWidgetContentProvider poolWidgetContent = new PoolWidgetContentProvider();
		tv.setContentProvider(poolWidgetContent);
		tv.setLabelProvider(new LabelContentProvider());
		//String ctx =LFWPersTool.getProjectPath();
		Map<String, Map<String, LfwWidget>> input = getAllPoolWidgets();
		input.remove(null);
		List<String> parentList = new ArrayList<String>();
		for (Iterator<String> it = input.keySet().iterator(); it.hasNext();) {
			parentList.add(it.next());
		}
		tv.setInput(parentList);
		return container;
	}

	private Map<String, Map<String, LfwWidget>> input;
	
	private Map<String, Map<String, LfwWidget>> getAllPoolWidgets(){
		if(input == null){
			String ctx = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
			//String ctx = LFWPersTool.getProjectModuleName(LFWPersTool.getCurrentProject());
			if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
				input = LFWAMCConnector.getAllPublicViews();
			}else{
				input = RefDatasetData.getPoolWidgets("/" + ctx);
			}
		}
		return input;
	}
	
	
	class LabelContentProvider extends LabelProvider{

		public Image getImage(Object element) {
			return super.getImage(element);
		}

		public String getText(Object element) {
			if(element instanceof String)
				return element.toString();
			else if(element instanceof LfwWidget){
				LfwWidget widget = (LfwWidget) element;
				return widget.getId();
			}
			return "";
		}
		
	}
	
	class PoolWidgetContentProvider implements ITreeContentProvider{

		public PoolWidgetContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			List<LfwWidget> widgetList = new ArrayList<LfwWidget>();
			Map<String, Map<String, LfwWidget>> input = getAllPoolWidgets();
			Map<String, LfwWidget> children = input.get(parentElement);
			for (Iterator<String> itchild = children.keySet().iterator(); itchild.hasNext();) {
				LfwWidget widget = children.get(itchild.next());
				widgetList.add(widget);
			}
			LfwWidget[] transWidget = (LfwWidget[])widgetList.toArray(new LfwWidget[widgetList.size()]);
			for (int i = 0; i < transWidget.length; i++) {
				for (int j = transWidget.length - 1; j > i; j--) {
					if(transWidget[i].getId().compareTo(transWidget[j].getId()) > 0){
						LfwWidget temp = transWidget[i];
						transWidget[i] = transWidget[j];
						transWidget[j] = temp;
					}
				}
			}
			return transWidget;
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			if(element instanceof String)
				return true;
			return false;
		}

		public Object[] getElements(Object inputElement) {
			if(inputElement != null){
				if(inputElement instanceof List){
					return ((List) inputElement).toArray();
				}
			}
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

}
	

