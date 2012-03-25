package nc.uap.lfw.editor.application.plug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.core.ObjectComboCellEditor;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.core.page.PluginDescItem;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.core.page.PlugoutDescItem;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * plugin plugout 关联窗口
 * 
 * @author dingrf
 *
 */

public class ConnectorMappingDialog extends DialogWithTitle{
	
	 class AddDSCommand extends Command{
		public AddDSCommand(){
			super("增加属性");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}
	
	public class PlugRelation{
		private String outValue;
		private String inValue;
		
		public String getOutValue() {
			return outValue;
		}
		public void setOutValue(String outValue) {
			this.outValue = outValue;
		}
		public String getInValue() {
			return inValue;
		}
		public void setInValue(String inValue) {
			this.inValue = inValue;
		}
	}

	private TreeViewer tv = null;
	private Connector conn;
	private List<PlugRelation> plugRelation = new ArrayList<PlugRelation>();
	private String[] inValues;
	private boolean noError = true;
	/**
	 * conn ID
	 */
//	private Text idText;
	/**
	 * conn ID
	 */
//	private String id;
	
	
	protected Point getInitialSize() {
		return new Point(500,250); 
	}
	
	public ConnectorMappingDialog(Shell parentShell, String title, Connector conn) {
		super(parentShell, title);
		this.conn = conn;
		
//		ApplicationEditor editor = ApplicationEditor.getActiveEditor();
//		ApplicationConf app = editor.getGraph().getApplication();
//		WindowConf sourceWindow = app.getWindowConf(conn.getSourceWindow());
		PageMeta sourceWindow = LFWAMCPersTool.getPageMetaById(conn.getSourceWindow());
		if (sourceWindow == null){
			MessageDialog.openError(this.getShell(), "错误", "未找到输出Window：" + conn.getSourceWindow());
			noError = false;
			return;
		}
		LfwWidget sourceView = sourceWindow.getWidget(conn.getSource());
		if (sourceView == null){
			MessageDialog.openError(this.getShell(), "错误", "未找到输出View：" + conn.getSource());
			noError = false;
			return;
		}
		PlugoutDesc plugout = sourceView.getPlugoutDesc(conn.getPlugoutId());  
		if (plugout == null){ 
			MessageDialog.openError(this.getShell(), "错误", "未找到plugout：" + conn.getPlugoutId());
			noError = false;
			return;
		}
//		WindowConf targetWindow = app.getWindowConf(conn.getTargetWindow());
		PageMeta targetWindow = LFWAMCPersTool.getPageMetaById(conn.getTargetWindow());		
		if (targetWindow == null){
			MessageDialog.openError(this.getShell(), "错误", "未找到输入Window：" + conn.getTargetWindow());
			noError = false;
			return;
		}
		LfwWidget targetView = targetWindow.getWidget(conn.getTarget());
		if (targetView == null){
			MessageDialog.openError(this.getShell(), "错误", "未找到输入View：" + conn.getTarget());
			noError = false;
			return;
		}
		PluginDesc plugin = targetView.getPluginDesc(conn.getPluginId());  
		if (plugin == null){
			MessageDialog.openError(this.getShell(), "错误", "未找到plugin：" + conn.getPluginId());
			noError = false;
			return;
		}
		
//			PageMeta pagemeta = editor.getGraph().getPagemeta();
		
		Map<String, String > plugMap = this.conn.getMapping();
		if (plugMap == null)
			this.conn.setMapping(new HashMap<String, String>());
		for (PlugoutDescItem item : plugout.getDescItemList()){
			PlugRelation r = new PlugRelation();
			r.setOutValue(item.getName());
			if (plugMap.containsKey(item.getName()))
				r.setInValue(plugMap.get(item.getName()));
			plugRelation.add(r);
		}
		List<PluginDescItem> pluginDescItem = plugin.getDescItemList();
		this.inValues = new String[pluginDescItem.size()];
		for (int i=0; i<pluginDescItem.size();i++){
			this.inValues[i]=pluginDescItem.get(i).getId();
		}
			
	}
	
	public TreeViewer getTv() {
		return tv;
	}

	public void setTv(TreeViewer tv) {
		this.tv = tv;
	}

	
	public Connector getConn() {
		return conn;
	}

	protected void okPressed() {
		this.conn.getMapping().clear();
		for (PlugRelation r : plugRelation){
			if (r.getInValue() != null && !r.getInValue().equals(""))
				this.conn.getMapping().put(r.getOutValue(), r.getInValue());
		}
		
		super.okPressed();
	}
	 
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gd);
		container.setLayout(new GridLayout(1, false));

		
		ViewForm vf = new ViewForm(container, SWT.NONE);
		vf.setLayoutData(gd);
//		TableViewer tv = new TableViewer(vf, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tv = new TreeViewer(vf,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree tree = tv.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		createColumn(tree, ConnectorMappingCellModifier.colNames[0], 100, SWT.LEFT, 0);
		createColumn(tree, ConnectorMappingCellModifier.colNames[1], 100, SWT.LEFT, 1);
		ConnectorMappingProvider provider = new ConnectorMappingProvider();
		tv.setLabelProvider(provider);
		tv.setContentProvider(provider);
		tv.setColumnProperties(ConnectorMappingCellModifier.colNames);
		//每个Cell的编辑Editor，todo
		CellEditor[] cellEditors = new CellEditor[ConnectorMappingCellModifier.colNames.length];
		//id,field,text,i18nname
		cellEditors[0] = new TextCellEditor(tree);;
//		cellEditors[1] = new TextCellEditor(tree);
		cellEditors[1] = new ObjectComboCellEditor(tree, this.inValues);
		tv.setCellEditors(cellEditors);
		tv.setCellModifier(new ConnectorMappingCellModifier(this));
		vf.setContent(tv.getControl());
		
		tv.setInput(plugRelation);
		return container;
	}
	
	private TreeColumn createColumn(Tree tree, String colName , int width, int align, int index){
		TreeColumn col = new TreeColumn(tree, SWT.None, index);
		col.setText(colName);
		col.setWidth(width);
		col.setAlignment(align);
		return col;
	}

	public boolean isNoError() {
		return noError;
	}
	
}
