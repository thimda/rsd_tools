/**
 * 
 */
package nc.uap.lfw.publicview;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.ref.RefDatasetData;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.resources.IProject;

/**
 * @author chouhl
 *
 */
public class RefreshPublicViewNodeAction extends NodeAction {

	private LFWDirtoryTreeItem parent;
	
	private File file;
	
	private IProject project;
	
	public RefreshPublicViewNodeAction(LFWDirtoryTreeItem parent, File file, IProject project){
		super(WEBProjConstants.REFRESH, WEBProjConstants.REFRESH);
		setImageDescriptor(PaletteImage.getRefreshImgDescriptor());
		this.parent = parent;
		this.file = file;
		this.project = project;
	}
	
	@Override
	public void run() {
		initPubWidgetSubTree(parent, file, project);
	}
	
	public void initPubWidgetSubTree(LFWDirtoryTreeItem parent, File file,
			IProject project) {
		try {
			String ctx = LFWUtility.getContextFromResource(project);
			Map<String, Map<String, LfwWidget>> allWidget = RefDatasetData
					.getPoolWidgets("/" + ctx);
			Map<String, LfwWidget> widgetMap = allWidget.get("/" + ctx);
			LFWWidgetTreeItem pubWidgetTreeItem = null;
			if (widgetMap != null) {
				String msg = LFWPerspectiveNameConst.WIDGET_CN;
				if (LFWTool.NEW_VERSION.equals(parent.getLfwVersion())) {
					msg = WEBProjConstants.PUBLIC_VIEW_SUB;
				}
				String parentFilePath = file.getPath();
				parent.removeAll();
				Iterator<LfwWidget> it = widgetMap.values().iterator();
				while (it.hasNext()) {
					LfwWidget widget = it.next();
					File widgetFile = new File(parentFilePath + "/"	+ widget.getId());
					if(widgetFile.exists()){
						pubWidgetTreeItem = new LFWWidgetTreeItem(parent, widgetFile, widget, "[" + msg + "] " + widgetFile.getName());
						pubWidgetTreeItem.setType(LFWDirtoryTreeItem.POOLWIDGETFOLDER);
						pubWidgetTreeItem.setLfwVersion(parent.getLfwVersion());
						LFWExplorerTreeView.getLFWExploerTreeView(null).detalWidgetTreeItem(pubWidgetTreeItem, pubWidgetTreeItem.getFile(), widget);
					}
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}
	
}
