/**
 * 
 */
package nc.uap.lfw.model;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.design.itf.MdClassVO;
import nc.uap.lfw.design.itf.MdComponnetVO;
import nc.uap.lfw.design.itf.MdModuleVO;
import nc.uap.lfw.design.view.LFWAMCConnector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * 新建Model选择元数据对话框类
 * @author chouhl
 *
 */
public class ModelMDDatsetDialog extends DialogWithTitle {

	private String modelRefId;
	
	private String modelName;
	
	private String ts;
	
	private String version;
	/**
	 * 树视图
	 */
	private TreeViewer treeViewer;
	/**
	 * 所有Module
	 */
	private List<MdModuleVO> allModuels;
	/**
	 * 所有组件
	 */
	private List<MdComponnetVO> allComponents;
	/**
	 * 所有类
	 */
	private List<MdClassVO> allClasses;
	
	public ModelMDDatsetDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}
	
	public ModelMDDatsetDialog(String title) {
		super(null, title);
	}

	/**
	 * 对话框初始大小
	 */
	protected Point getInitialSize() {
		return new Point(400, 400); 
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		//查找条件
		Label label = new Label(grouId, SWT.NONE);
		label.setText("查找指定数据集:");
		Text searchText = new Text(grouId, SWT.NONE);
		searchText.setLayoutData(new GridData(220,15));
		searchText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				Text t =(Text) e.widget;
				buildTree(t.getText());
			}
		});
		//数据显示
		treeViewer = new TreeViewer(container,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		treeViewer.setContentProvider(new FuncContentProvider());
		treeViewer.setLabelProvider(new LabelContentProvider());
		treeViewer.setInput(getALlModules());
		treeViewer.getTree().addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e){
				Tree tree = (Tree)e.getSource();
				TreeItem treeItem = tree.getSelection()[0];
				if(treeItem.getData() instanceof MdComponnetVO){
					okPressed();
				}
			}

			public void mouseDown(MouseEvent e){}

			public void mouseUp(MouseEvent e){}
			
		});
		
		return container;
	}
	
	protected void okPressed() {
		TreeItem[] treeItems = treeViewer.getTree().getSelection();
		if(treeItems == null || treeItems.length == 0){
			MessageDialog.openError(null, "元数据选择", "请选择元数据!");
			return;
		}
		TreeItem treeItem = treeItems[0];
		if(!(treeItem.getData() instanceof MdComponnetVO)){
			MessageDialog.openError(null, "元数据选择", "请选择元数据!");
			return;
		}
		MdComponnetVO componentVO = (MdComponnetVO)treeItem.getData();
		modelRefId = componentVO.getId();
		modelName = componentVO.getDisplayname();
		ts = componentVO.getTs();
		version = componentVO.getVersion();
		super.okPressed();
	}
	/**
	 * 通过条件筛选节点
	 * @param filter
	 */
	private void buildTree(String filter){
		if(filter == null || filter.trim().length() == 0){
			treeViewer.setInput(getALlModules());
			return;
		}
		filter = filter.trim();
		List<MdComponnetVO> components = getAllComponents();
		List<MdComponnetVO> showComponents = new ArrayList<MdComponnetVO>();
		if(components != null && components.size() > 0){
			for (MdComponnetVO component : components) {
				if(component.getDisplayname().toLowerCase().matches(".{0,}" + filter.toLowerCase() + ".{0,}")){
					showComponents.add(component);
				}
			}
		}
		treeViewer.setInput(showComponents);
	}
	/**
	 * 
	 * 树视图显示内容提供类	
	 * @author chouhl
	 *
	 */
	class LabelContentProvider extends LabelProvider{
		
		public Image getImage(Object element) {
			ImageDescriptor imageDes = null;
			if(element instanceof MdModuleVO){
				imageDes = 	WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "module.gif");
			}else if(element instanceof MdComponnetVO){
				imageDes = 	WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "component.gif");
			}else if(element instanceof MdClassVO){
				imageDes = 	WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "mdclass.gif");
			}
			if(imageDes != null){
				return imageDes.createImage();
			}else{ 
				return super.getImage(element);
			}
		}

		public String getText(Object element) {
			if(element instanceof MdModuleVO){
				MdModuleVO vo = (MdModuleVO)element;
				return  vo.getDisplayname();
			}else if(element instanceof MdComponnetVO){
				MdComponnetVO vo = (MdComponnetVO)element;
				return vo.getDisplayname();
			}else if(element instanceof MdClassVO){
				MdClassVO vo = (MdClassVO)element;
				return vo.getDisplayname();
			}else{
				return "未知";
			}
		}
		
	}
	/**
	 * 
	 * 树视图结构内容提供类
	 * @author chouhl
	 *
	 */
	class FuncContentProvider implements ITreeContentProvider{
		
		List<MdModuleVO> modules = getALlModules();
		List<MdComponnetVO> components = getAllComponents();
		List<MdClassVO> classes = getAllClasses();

		public Object[] getChildren(Object parentElement){
			if(parentElement instanceof MdModuleVO){
				MdModuleVO vo = (MdModuleVO)parentElement;
				List<MdComponnetVO> list = new ArrayList<MdComponnetVO>();
				if(components != null && components.size() > 0){
					for(MdComponnetVO component : components){
						if(component.getOwnmodule() != null && component.getOwnmodule().equals(vo.getId())){
							list.add(component);
						}
					}
				}
				return list.toArray();
			}else if(parentElement instanceof MdComponnetVO){
				MdComponnetVO vo = (MdComponnetVO)parentElement;
				List<MdClassVO> list = new ArrayList<MdClassVO>();
				if(classes != null && classes.size() > 0){
					for(MdClassVO clazz : classes){
						if(clazz.getComponentid() != null && clazz.getComponentid().equals(vo.getId())){
							list.add(clazz);
						}
					}
				}
				return list.toArray();
			}else{
				return new Object[]{};
			}
		}

		public Object getParent(Object childElement){
			if(childElement instanceof MdComponnetVO){
				MdComponnetVO vo = (MdComponnetVO)childElement;
				if(modules != null && modules.size() > 0){
					for(MdModuleVO module : modules){
						if(vo.getOwnmodule() != null && vo.getOwnmodule().equals(module.getId())){
							return module;
						}
					}
				}
			}else if(childElement instanceof MdClassVO){
				MdClassVO vo = (MdClassVO)childElement;
				if(components != null && components.size() > 0){
					for(MdComponnetVO component : components){
						if(vo.getComponentid() != null && vo.getComponentid().equals(component.getId())){
							return component;
						}
					}
				}
			}
			return null;
		}

		public boolean hasChildren(Object element){
			if(element instanceof MdModuleVO){
				return true;
			}else if(element instanceof MdComponnetVO){
				return false;
			}else{
				return false;
			}
		}

		public Object[] getElements(Object inputElement){
			if(inputElement instanceof List){
				return ((List<?>)inputElement).toArray();
			}else{ 
				return new Object[0];
			}
		}

		public void dispose(){}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput){}
		
	}

	/**
	 * 获得所有Module
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<MdModuleVO> getALlModules(){
		if(allModuels == null){
			allModuels = LFWAMCConnector.getAllModulse();
		}
		return allModuels;
	}
	
	/**
	 * 获得所有组件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private synchronized List<MdComponnetVO> getAllComponents(){
		if(allComponents == null){
			allComponents = LFWAMCConnector.getAllComponents();
		}
		return allComponents;
	}
	
	/**
	 * 获得所有类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private synchronized List<MdClassVO> getAllClasses(){
		if(allClasses == null){
			allClasses =  LFWAMCConnector.getAllClasses();
		}
		return allClasses;
	}

	public String getModelRefId() {
		return modelRefId;
	}

	public void setModelRefId(String modelRefId) {
		this.modelRefId = modelRefId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
