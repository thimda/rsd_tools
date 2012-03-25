package nc.uap.lfw.perspective.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.data.Dataset;
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
 * 从公共池中得到所有的ds
 * @author zhangxya
 *
 */
public class DatasetsFromPoolDialog extends DialogWithTitle {

	private Tree tree = null;
	private TreeViewer tv = null;
	private Dataset dataset = null;
		
	public DatasetsFromPoolDialog(Shell parentShell, String title) {
		super(parentShell,title);
	}
	
	public Dataset getSelectedDataset(){
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	protected void okPressed() {
		TreeItem[] items = tree.getSelection();
		if(items != null && items.length > 0){
			TreeItem item = items[0];
			Dataset ds = (Dataset)item.getData();
			setDataset(ds);
			if(item != null){
				super.okPressed();
			}
		}else{
			MessageDialog.openConfirm(this.getShell(), "提示", "请选择一个引用的数据集");
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
		PoolDatasetContentProvider poolDatasetContent = new PoolDatasetContentProvider();
		tv.setContentProvider(poolDatasetContent);
		tv.setLabelProvider(new LabelContentProvider());
		//String ctx =LFWPersTool.getProjectPath();
		Map<String, Map<String, Dataset>> input = getAllPoolDs();
		List<String> parentList = new ArrayList<String>();
		for (Iterator<String> it = input.keySet().iterator(); it.hasNext();) {
			parentList.add(it.next());
		}
		tv.setInput(parentList);
		return container;
	}

	private Map<String, Map<String, Dataset>> input;
	
	private Map<String, Map<String, Dataset>> getAllPoolDs(){
		if(input == null){
			String ctx = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
			//String ctx = LFWPersTool.getProjectModuleName(LFWPersTool.getCurrentProject());
			input = RefDatasetData.getDatasets("/" + ctx);
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
			else if(element instanceof Dataset){
				Dataset ds = (Dataset) element;
				return ds.getId();
			}
			return "";
		}
		
	}
	
	class PoolDatasetContentProvider implements ITreeContentProvider{

		public PoolDatasetContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			List<Dataset> datasetList = new ArrayList<Dataset>();
			Map<String, Map<String, Dataset>> input = getAllPoolDs();
			Map<String, Dataset> children = input.get(parentElement);
			for (Iterator<String> itchild = children.keySet().iterator(); itchild.hasNext();) {
					Dataset ds = children.get(itchild.next());
					datasetList.add(ds);
			}
			Dataset[] transdss = (Dataset[])datasetList.toArray(new Dataset[datasetList.size()]);
			for (int i = 0; i < transdss.length; i++) {
				for (int j = transdss.length - 1; j > i; j--) {
					if(transdss[i].getId().compareTo(transdss[j].getId()) > 0){
						Dataset temp = transdss[i];
						transdss[i] = transdss[j];
						transdss[j] = temp;
					}
			
				}
			}
			return transdss;
		}

		public Object getParent(Object element) {
			return null;
//			FuncNodeVO vo = (FuncNodeVO) element;
//			String pk_parent = vo.getPk_parent();
//			if(pk_parent == null)
//				return null;
//			for (int i = 0; i < funvosExfun.length; i++) {
//				if(pk_parent.equals(funvosExfun[i].getPk_func()))
//					return funvosExfun[i];
//			}
//			return null;
		}

		public boolean hasChildren(Object element) {
			if(element instanceof String)
				return true;
			return false;
//			if(version.equals(ExtAttrConstants.VERSION60)){
//				FuncNodeVO vo = (FuncNodeVO) element;
//				String pk = vo.getPk_func();
//				for (int i = 0; i < funvosExfun.length; i++) {
//					if(pk.equals(funvosExfun[i].getPk_parent())){
//						return true;
//					}
//				}
//				return false;
//			}
//			else{
//				FuncNodeVO vo = (FuncNodeVO) element;
//				String pk = vo.getPk_func();
//				for (int i = 0; i < funvos.length; i++) {
//					if(pk.equals(funvos[i].getPk_parent())){
//						return true;
//					}
//				}
//				return false;
//			}
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
	

