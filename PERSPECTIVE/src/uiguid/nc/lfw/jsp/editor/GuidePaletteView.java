package nc.lfw.jsp.editor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.jsp.uimeta.UIConstant;

import org.eclipse.gef.Tool;
import org.eclipse.gef.internal.ui.palette.editparts.ToolEntryEditPart;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * PaletteView.
 * <p>
 * When HTMLSourceEditor or IPaletteTarget actives,
 * inserts a tag that is selected in the palette to the calet position.
 * </p>
 */
@SuppressWarnings("restriction")
public class GuidePaletteView extends ViewPart implements UIConstant{
	
	private PaletteViewer viewer;
	private TreeMap<String, List<IPaletteItem>> items = new TreeMap<String, List<IPaletteItem>>();
	private HashMap<HTMLPaletteEntry, IPaletteItem> tools = new HashMap<HTMLPaletteEntry, IPaletteItem>();
	private String[] defaultCategories;
	
	/**
	 * Constructor
	 * @param iswidget 
	 */
	public GuidePaletteView(boolean iswidget) {
		defaultCategories = new String[3];
//		defaultCategories[0] = "选择";
		defaultCategories[0] = "布局";
		defaultCategories[1] = "控件";
		
		addPaletteItem("选择",new DefaultPaletteItem("",
				MainPlugin.getImageDescriptor(MainPlugin.ICON_HTML),
				""));
		
		if(!iswidget){
			defaultCategories = new String[2];
//			defaultCategories[0] = "选择";
			defaultCategories[0] = "布局";
			defaultCategories[1] = "控件";
			addPaletteItem("控件",new DefaultPaletteItem(DWIDGET,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DMENUBAR,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
		}
		else{
			defaultCategories = new String[3];
//			defaultCategories[0] = "选择";
			defaultCategories[0] = "布局";
			defaultCategories[1] = "控件";
			defaultCategories[2] = "数据绑定控件";
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DGRID,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DFORM,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			//**************** add by limingf
			addPaletteItem("数据绑定控件",new DefaultPaletteItem("自定义表单",
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DCHARTCOMP,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));	
			//***************
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DTREE,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DTREETABLE,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			addPaletteItem("数据绑定控件",new DefaultPaletteItem(DExcel,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_BINDDATA),
					""));
			
			addPaletteItem("控件",new DefaultPaletteItem(DTEXT,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DBUTTON,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DLABEL,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DIMAGE,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DMENUBAR,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DIFRAME,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
//			addPaletteItem("控件",new DefaultPaletteItem(DIMAGEMAP,
//					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
//					""));
			addPaletteItem("控件",new DefaultPaletteItem(DTOOLBAR,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DLINKCOMP,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
			addPaletteItem("控件",new DefaultPaletteItem(DSELFDEFCOMP,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_FORM),
					""));
					
		}
			addPaletteItem("布局",new DefaultPaletteItem(DBORDERL,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			addPaletteItem("布局",new DefaultPaletteItem(DFLOWH,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			addPaletteItem("布局",new DefaultPaletteItem(DFLOWV,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			addPaletteItem("布局",new DefaultPaletteItem(DCARD,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			addPaletteItem("布局",new DefaultPaletteItem(DTAB,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			addPaletteItem("布局",new DefaultPaletteItem(DSPLIT,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			
			addPaletteItem("布局",new DefaultPaletteItem(DBORDER,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			
			addPaletteItem("布局",new DefaultPaletteItem(DMENUGROUP,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			
			addPaletteItem("布局",new DefaultPaletteItem(DPANEL,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			
			addPaletteItem("布局",new DefaultPaletteItem(DGROUP,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
			
			addPaletteItem("布局",new DefaultPaletteItem(DSHUTTER,
					MainPlugin.getImageDescriptor(MainPlugin.ICON_LIST),
					""));
//		addPaletteItem("JSP",new DefaultPaletteItem("jsp:useBean",
//				MainPlugin.getImageDescriptor(MainPlugin.ICON_TAG),
//				"<jsp:useBean id=\"\" class=\"\" scope=\"\" />"));
//		addPaletteItem("JSP",new DefaultPaletteItem("jsp:include",
//				MainPlugin.getImageDescriptor(MainPlugin.ICON_TAG),
//				"<jsp:include />"));
//		addPaletteItem("JSP",new DefaultPaletteItem("jsp:forward",
//				MainPlugin.getImageDescriptor(MainPlugin.ICON_TAG),
//				"<jsp:forward />"));
//		
//		// add items contributed from other plugins
//		String[] groups = MainPlugin.getDefault().getPaletteContributerGroups();
//		for(int i=0;i<groups.length;i++){
//			IPaletteContributer contributer = MainPlugin.getDefault().getPaletteContributer(groups[i]);
//			IPaletteItem[] items = contributer.getPaletteItems();
//			for(int j=0;j<items.length;j++){
//				addPaletteItem(groups[i],items[j]);
//			}
//		}
//		
		// save default categories
	}

	
	/**
	 * create controls and apply configurations.
	 */
	public void createPartControl(Composite parent) {
		viewer = new PaletteViewer();
		viewer.createControl(parent);
		
		PaletteRoot root = new PaletteRoot();
		
		String[] category = defaultCategories;
		for(int i = 0; i < category.length; i++){
			PaletteDrawer group = new PaletteDrawer(category[i]);
			IPaletteItem[] items = getPaletteItems(category[i]);
			for(int j = 0; j < items.length; j++){
				HTMLPaletteEntry entry = new HTMLPaletteEntry(items[j].getLabel(), null, items[j].getImageDescriptor());
				tools.put(entry, items[j]);
				group.add(entry);
			}
			root.add(group);
		}
		PaletteTransferDragSourceListener dropListener = new PaletteTransferDragSourceListener(viewer);
		viewer.addDragSourceListener(dropListener);
		viewer.setPaletteRoot(root);
	}
	
	/**
	 * Adds PaletteItem to the specified category.
	 * 
	 * @param category the category
	 * @param item the item
	 */
	private void addPaletteItem(String category,IPaletteItem item){
		if(items.get(category)==null){
			List<IPaletteItem> list = new ArrayList<IPaletteItem>();
			items.put(category,list);
		}
		List<IPaletteItem> list = items.get(category);
		list.add(item);
	}
	
	
	
	/**
	 * Returns PaletteItems which are contained by the specified category.
	 * 
	 * @param category the category
	 * @return the array of items which are contained by the category
	 */
	private IPaletteItem[] getPaletteItems(String category){
		List<IPaletteItem> list = items.get(category);
		if(list==null){
			return new IPaletteItem[0];
		}
		return list.toArray(new IPaletteItem[list.size()]);
	}
	

	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private final class PaletteTransferDragSourceListener implements
			TransferDragSourceListener {
		private PaletteViewer viewer = null;
		public PaletteTransferDragSourceListener(PaletteViewer viewer) {
			this.viewer = viewer;
		}

		public Transfer getTransfer() {
			return TextTransfer.getInstance();
		}

		public void dragFinished(DragSourceEvent event) {
		}

		public void dragSetData(DragSourceEvent event) {
			List list = viewer.getSelectedEditParts();
			ToolEntryEditPart part = (ToolEntryEditPart) list.get(0);
			HTMLPaletteEntry entry = (HTMLPaletteEntry) part.getModel();
			String id = entry.getLabel();
			event.data = id;
		}

		public void dragStart(DragSourceEvent event) {
		}
	}

	/** ToolEntry for HTML tag palette */
	private class HTMLPaletteEntry extends ToolEntry {
		
		public HTMLPaletteEntry(String label, String shortDescription, ImageDescriptor icon) {
			super(label, shortDescription, icon, icon);
		}
		
		public Tool createTool() {
			return null;
		}
	}
}