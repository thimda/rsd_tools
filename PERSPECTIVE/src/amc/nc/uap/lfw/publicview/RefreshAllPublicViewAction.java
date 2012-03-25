/**
 * 
 */
package nc.uap.lfw.publicview;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.editor.LFWPVTreeItem;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.ref.RefDatasetData;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author chouhl
 * 2011-11-18
 */
public class RefreshAllPublicViewAction extends NodeAction {
	
	private TreeItem ti = null;

	public RefreshAllPublicViewAction(TreeItem ti){
		super(WEBProjConstants.REFRESH, WEBProjConstants.REFRESH);
		setImageDescriptor(PaletteImage.getRefreshImgDescriptor());
		this.ti = ti;
	}
	
	@Override
	public void run() {
		refresh();
	}
	
	private void refresh(){
		Map<String, String> ctxMap = new HashMap<String, String>();
		
		IProject[] projects = LFWPersTool.getOpenedLFWNotBCPJavaProjects();
		if(projects != null && projects.length > 0){
			for(IProject project : projects){
				ctxMap.put(LFWUtility.getContextFromResource(project), "/" + LFWUtility.getContextFromResource(project));
			}
		}
		
		IProject[] bcpProjects = LFWPersTool.getOpenedBcpJavaProjects();
		if(bcpProjects != null && bcpProjects.length > 0){
			for(IProject project : bcpProjects){
				ctxMap.put(LFWUtility.getContextFromResource(project), "/" + LFWUtility.getContextFromResource(project));
			}
		}
		
		if(ctxMap.size() > 0){
			ti.removeAll();
			Iterator<String> keys = ctxMap.keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				Map<String, Map<String, LfwWidget>> allWidget = RefDatasetData.getPoolWidgets(ctxMap.get(key));
				if(allWidget != null){
					Map<String, LfwWidget> widgetMap = allWidget.get(ctxMap.get(key));
					if(widgetMap != null){
						LFWPVTreeItem ctxTreeItem = new LFWPVTreeItem(ti, SWT.NONE, 1);
						ctxTreeItem.setId(key);
						ctxTreeItem.setItemName(key);
						ctxTreeItem.setText(key);
						ctxTreeItem.setData(key);
						Iterator<LfwWidget> widgets = widgetMap.values().iterator();
						while (widgets.hasNext()) {
							LfwWidget widget = widgets.next();
							LFWPVTreeItem widgetTreeItem = new LFWPVTreeItem(ctxTreeItem, SWT.NONE, 2);
							widgetTreeItem.setId(widget.getId());
							widgetTreeItem.setItemName(widget.getId());
							widgetTreeItem.setText("[" + WEBProjConstants.PUBLIC_VIEW_SUB + "] " + widget.getId());
							widgetTreeItem.setData(widget);
						}
					}
				}
			}
		}
	}
	
}
