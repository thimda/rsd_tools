package nc.uap.lfw.funnode;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.design.itf.FuncNodeVO;
import nc.uap.lfw.design.itf.TypeNodeVO;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

/**
 * lfw自己的功能注册
 * @author zhangxya
 *
 */
public class LFWFuncRegisterDialog extends DialogWithTitle {
	
	private String nodeid;
	private Text funcodeText = null;
	private Text funnameText = null;
	private Text funUrlText = null;
	private Text funDesText = null;
	private Combo orgtypeComp = null;
	private Combo functionNodeComp = null;
	private TypeNodeVO[] orgtypevos = null;
//	private PageMeta pm;
	private FuncNodeVO[] funvos = null;
	private TreeViewer treeViewer;
	private FuncNodeVO[] funvosExfun = null;

	public LFWFuncRegisterDialog(Shell parentShell, String title, String nodeid, PageMeta pm) {
		super(parentShell, title);
		this.nodeid = nodeid;
//		this.pm = pm;
	}
	
	public FuncNodeVO[] getFuncVOs() {
		return NCConnector.getFuncRegisterVOs().toArray(new FuncNodeVO[0]);
	}
	
	public FuncNodeVO[] getMainFunvos(){
		if(funvosExfun != null){
			List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
			for (int i = 0; i < funvosExfun.length; i++) {
				FuncNodeVO vo = funvosExfun[i];
				if(vo.getPk_parent() == null)
					list.add(vo);
			}
			return list.toArray(new FuncNodeVO[0]);
		}
		return null;
	}
	
	
	protected Control createDialogArea(Composite parent) {
		if(funvos == null)
			funvos = getFuncVOs();
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		treeViewer = new TreeViewer(container,SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		treeViewer.setContentProvider(new FuncContentProvider());
		treeViewer.setLabelProvider(new LabelContentProvider());
		treeViewer.setInput(getMainFunvos());
		
		Canvas canvas = new Canvas(parent, SWT.NONE);
		canvas.setLayout(new GridLayout(4, false));
		canvas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
		Label funcodeLabel = new Label(canvas, SWT.NONE);
		funcodeLabel.setText("功能节点编码:");
		funcodeText = new Text(canvas, SWT.NONE);
		funcodeText.setLayoutData(new GridData(120,15));
		funcodeText.setText("");
		
		Label funnameLabel = new Label(canvas, SWT.NONE);
		funnameLabel.setText("功能节点名称:");
		funnameText = new Text(canvas, SWT.NONE);
		funnameText.setLayoutData(new GridData(120,15));
		funnameText.setText("");
		
		Label orgtypeLabel = new Label(canvas, SWT.NONE);
		orgtypeLabel.setText("组织类型:");
		orgtypeComp = new Combo(canvas, SWT.READ_ONLY);
		orgtypeComp.setLayoutData(new GridData(95,15));
		if(orgtypevos == null)
			orgtypevos = getAllMainOrgType();
		//List<String> list = new ArrayList();
		for (int i = 0; i < orgtypevos.length; i++) {
			String id = orgtypevos[i].getPk_typenode();
			String name = orgtypevos[i].getTypename();
			orgtypeComp.add(name);
			orgtypeComp.setData(name, id);
			}
		//orgtypeComp.setItems((String[])list.toArray(new String[list.size()]));
		orgtypeComp.select(0);
		
		Label funtionNodeLabel = new Label(canvas, SWT.NONE);
		funtionNodeLabel.setText("功能节点类型:");
		functionNodeComp = new Combo(canvas, SWT.READ_ONLY);
		functionNodeComp.setLayoutData(new GridData(95,15));
		String[] functionitems = {"业务类型","管理类型","系统类型"};
		functionNodeComp.setItems(functionitems);
		functionNodeComp.select(0);
		
		Label funURLLabel = new Label(canvas, SWT.NONE);
		funURLLabel.setText("功能类名:");
		funUrlText = new Text(canvas, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.heightHint = 15;
		gridData.widthHint = 330;
		funUrlText.setLayoutData(gridData);
		funUrlText.setText("uimeta.um?pageId=" + nodeid + "&model=nc.uap.lfw.ncadapter.LicensePageModel.LicensePageModel");
	
		Label funDesLabel = new Label(canvas, SWT.NONE);
		funDesLabel.setText("功能描述:");
		funDesText = new Text(canvas, SWT.NONE);
		funDesText.setLayoutData(new GridData(120,15));
		funDesText.setText("");
		
		return canvas;
	}
	
	protected void okPressed() {
		String funnode = funcodeText.getText();
		if(funnode == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能编码");
			funcodeText.setFocus();
		}
		String funname = funnameText.getText();
		if(funname == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能名称");
			funnameText.setFocus();
		}
		String funurl = funUrlText.getText();
		if(funurl == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能类名");
			funUrlText.setFocus();
		}
//		String orgtype = (String)orgtypeComp.getData(orgtypeComp.getText());
//		String functype = functionNodeComp.getText();
//		int functypen = 0;
//		if(functype.equals("业务类型"))
//			functypen = 0;
//		if(functype.equals("管理类型"))
//			functypen = 1;
//		if(functype.equals("系统类型"))
//			functypen = 2;
//		String funcdes = funDesText.getText();
//		pm.setExtendAttribute(ExtAttrConstants.FUNC_CODE, funnode);
		// 获取项目路径
//		LFWConnector dataProvider = new LFWConnector();
//		// 保存Widget到pagemeta.pm中
//		String filePath = folderPath;
//		String pmPath = filePath + "/pagemeta.pm";
//		LFWPersTool.checkOutFile(pmPath);
//		dataProvider.savePagemetaToXml(filePath, "pagemeta.pm", projectPath, pm);
//		FuncNodeVO funnodeVO = null;
//		funvos = getFuncVOs();
//		funvosOnlyfun = getFuncVOsOnlyFun();
//		for (int i = 0; i < funvosOnlyfun.length; i++) {
//			if(funvosOnlyfun[i].getFuncCode().equals(funnode)){
//				funnodeVO = funvosOnlyfun[i];
//				break;
//			}
//		}
//		if(funnodeVO == null)
//			funnodeVO = new FuncNodeVO();
//		funnodeVO.setFuncCode(funnode);
//		funnodeVO.setFuncName(funname);
//		funnodeVO.setFunurl(funurl);
//		funnodeVO.setOrgTypeCode(orgtype);
//		funnodeVO.setFunType(functypen);
//		funnodeVO.setFunDesc(funcdes);
//		//funnodeVO.seto
//		int parentnode = parentNode.getNodeType();
//		String version = NCConnector.getVersion();
//		if(version.equals(ExtAttrConstants.VERSION5X)){
//			funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//			funnodeVO.setPk_parent(parentNode.getPk_func());
//		}
//		else{
//			if(parentnode == FuncNodeVO.MODULE_FUNC_NODE){
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(null);
//			}
//			else{
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(parentNode.getPk_func());
//			}
//		}
//		NCConnector.updateSysTemplate(funnodeVO, tempvos);
		super.okPressed();
		
	}
	
	class LabelContentProvider extends LabelProvider{

		public Image getImage(Object element) {
			return super.getImage(element);
		}

		public String getText(Object element) {
			FuncNodeVO vo = (FuncNodeVO) element;
			return "[" + vo.getFuncCode() + "]" + vo.getFuncName();
		}
		
	}
	
	class FuncContentProvider implements ITreeContentProvider{

		public FuncContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
			FuncNodeVO vo = (FuncNodeVO) parentElement;
			String pk = vo.getPk_func();
			for (int i = 0; i < funvosExfun.length; i++) {
				if(pk.equals(funvosExfun[i].getPk_parent()))
					list.add(funvosExfun[i]);
			}
			return list.toArray(list.toArray(new FuncNodeVO[0]));
		}

		public Object getParent(Object element) {
			FuncNodeVO vo = (FuncNodeVO) element;
			String pk_parent = vo.getPk_parent();
			if(pk_parent == null)
				return null;
			for (int i = 0; i < funvosExfun.length; i++) {
				if(pk_parent.equals(funvosExfun[i].getPk_func()))
					return funvosExfun[i];
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			FuncNodeVO vo = (FuncNodeVO) element;
			String pk = vo.getPk_func();
			for (int i = 0; i < funvosExfun.length; i++) {
				if(pk.equals(funvosExfun[i].getPk_parent())){
					return true;
				}
			}
			return false;
		}

		public Object[] getElements(Object inputElement) {
			if(inputElement != null){
				return (Object[]) inputElement;
			}
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	private  TypeNodeVO[] getAllMainOrgType(){
		return NCConnector.getAllMainOrgType();
	}
}
