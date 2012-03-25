/**
 * 
 */
package nc.uap.lfw.window.view.context;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.uimodel.conf.Model;
import nc.uap.lfw.design.itf.MdClassVO;
import nc.uap.lfw.design.itf.MdComponnetVO;
import nc.uap.lfw.design.itf.MdModuleVO;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;

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
 * Model引用数据集对话框类
 * @author chouhl
 *
 */
public class ContextMDDatasetDialog extends DialogWithTitle  {

	/**
	 * 树视图
	 */
	private TreeViewer treeViewer;
	/**
	 * Model已引用的Module
	 */
//	private List<MdModuleVO> existModuels;
	/**
	 * Model已引用的组件
	 */
	private List<MdComponnetVO> existComponents;
	/**
	 * 所有类
	 */
	private List<MdClassVO> allClasses;
	/**
	 * View元素
	 */
	private LfwWidget widget;
	/**
	 * 所选组件ID
	 */
	private String componentId;
	/**
	 * 所选组件显示名称
	 */
	private String displayName;
	
	public ContextMDDatasetDialog(String title) {
		super(null, title);
	}
	
	public ContextMDDatasetDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}
	
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
//		treeViewer.setInput(getExistModules());
		treeViewer.setInput(getExistComponents());
		treeViewer.getTree().addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				Tree tree = (Tree) e.getSource();
				TreeItem treeItem = tree.getSelection()[0];
				if(treeItem.getData() instanceof MdComponnetVO){
					okPressed();
				}
			}

			public void mouseDown(MouseEvent e) {}

			public void mouseUp(MouseEvent e) {}
			
		});
		
		return container;
	}
	
	protected void okPressed() {
		TreeItem[] treeItems = treeViewer.getTree().getSelection();
		if(treeItems == null || treeItems.length == 0){
			MessageDialog.openError(null, "元数据选择", "请选择元数据!");
			return;
		}
		TreeItem treeItem = treeViewer.getTree().getSelection()[0];
		if(!(treeItem.getData() instanceof MdComponnetVO)){
			MessageDialog.openError(null, "元数据选择", "请选择元数据!");
			return;
		}
		MdComponnetVO vo = (MdComponnetVO)treeItem.getData();
		this.componentId = vo.getId();
		this.displayName = vo.getDisplayname();

		widget = LFWAMCPersTool.getCurrentWidget();
		LfwWidget clone = (LfwWidget)widget.clone();
		clone = NCConnector.getMdDsFromComponent(clone, vo.getId());
		LFWTool.mergeWidget(widget, clone);
		List<Model> modelList = widget.getModels();
		boolean isModelExist = false;
		if(modelList != null){
			for(Model model : modelList){
				if(model.getRefId().equals(vo.getId())){
					model.setCaption(vo.getDisplayname());
					model.setTs(vo.getTs());
					model.setVersion(vo.getVersion());
					isModelExist = true;
				}
			}
		}
		if(!isModelExist){
			Model model = new Model();
			model.setId(vo.getId());
			model.setCaption(vo.getDisplayname());
			model.setRefId(vo.getId());
			model.setTs(vo.getTs());
			model.setVersion(vo.getVersion());
			widget.addModel(model);
		}
		setWidget(widget);
		
		super.okPressed();
	}
	/**
	 * 通过条件筛选节点
	 * @param filter
	 */
	private void buildTree(String filter){
		if(filter == null || filter.trim().length() == 0){
			treeViewer.setInput(getExistComponents());
			return;
		}
		filter = filter.trim();
		List<MdComponnetVO> components = getExistComponents();
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
				MdModuleVO vo = (MdModuleVO) element;
				return  vo.getDisplayname();
			}else if(element instanceof MdComponnetVO){
				MdComponnetVO comvo = (MdComponnetVO)element;
				return comvo.getDisplayname() + "[" + comvo.getDisplayId() + "]";
			}else if(element instanceof MdClassVO){
				MdClassVO classvo = (MdClassVO)element;
				return classvo.getDisplayname();
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
		
//		List<MdModuleVO> modules = getExistModules();
		List<MdComponnetVO> components = getExistComponents();
		List<MdClassVO> classes = getAllClasses();

		public Object[] getChildren(Object parentElement) {
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

		public Object getParent(Object childElement) {
//			if(childElement instanceof MdComponnetVO){
//				MdComponnetVO vo = (MdComponnetVO)childElement;
//				if(modules != null && modules.size() > 0){
//					for(MdModuleVO module : modules){
//						if(vo.getOwnmodule() != null && vo.getOwnmodule().equals(module.getId())){
//							return module;
//						}
//					}
//				}
//			}else 
			if(childElement instanceof MdClassVO){
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

		public boolean hasChildren(Object element) {
			if(element instanceof MdModuleVO){
				return true;
			}else if(element instanceof MdComponnetVO){
				return false;
			}else{
				return false;
			}
		}

		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof List)
				return ((List<?>) inputElement).toArray();
			else{
				return new Object[0];
			}
		}

		public void dispose() {}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
	}

	/**
	 * 获得Model中已存在的Module
	 * @return
	 */
//	public List<MdModuleVO> getExistModules(){
//		if(existModuels == null){
//			existModuels = LFWAMCConnector.getExistModules();
//		}
//		return existModuels;
//	}
	
	/**
	 * 获得Model中已存在的组件
	 * @return
	 */
	private List<MdComponnetVO> getExistComponents(){
		if(existComponents == null){
			existComponents = LFWAMCConnector.getExistComponents();
		}
		return existComponents;
	}
	
	/**
	 * 获得所有类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MdClassVO> getAllClasses(){
		if(allClasses == null){
			allClasses =  LFWAMCConnector.getAllClasses();
		}
		return allClasses;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
}
