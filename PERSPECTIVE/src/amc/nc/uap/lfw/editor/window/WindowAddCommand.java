/**
 * 
 */
package nc.uap.lfw.editor.window;

import java.util.List;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.application.ApplicationEditor;
import nc.uap.lfw.editor.application.ApplicationGraph;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * Application业务组件工具箱——选择Window命令处理类
 * @author chouhl
 *
 */
public class WindowAddCommand extends Command {

	private WindowObj obj;

	private ApplicationGraph graph;
	
	private Rectangle rect;

	public WindowAddCommand(WindowObj obj, ApplicationGraph graph, Rectangle rect) {
		super();
		this.obj = obj;
		this.graph = graph;
		this.rect = rect;
		setLabel("add exist Window");
	}

	public boolean canExecute() {
		return obj != null && graph != null && rect != null;
	}

	public void execute() {
		WindowObj windowObj = (WindowObj) obj;
		WindowListDialog dialog = new WindowListDialog("Window列表", graph.getProject(), graph.getCurrentTreeItem());
		int result = dialog.open();
		if(result == IDialogConstants.OK_ID){
			PageMeta window = new PageMeta();
			window.setId(dialog.getWindowId());
			window.setCaption(dialog.getWindowName());
			windowObj.setWindow(window);
		}else{
			return;
		}
		boolean isNotExist = true;
		List<PageMeta> windowList = graph.getApplication().getWindowList();
		if(windowList != null && windowList.size() > 0){
			for(PageMeta win : windowList){
				if(obj.getWindow().getId().equals(win.getId())){
					isNotExist = false;
					break;
				}
			}
		}
		if(isNotExist){
			ApplicationEditor editor = ApplicationEditor.getActiveEditor();
			editor.repaintWindowObj(obj);
			redo();
			editor.setDirtyTrue();
		}else{
			MessageDialog.openInformation(null, WEBProjConstants.ADD_WINDOW, "已存在ID为："+obj.getWindow().getId()+"的Window");
		}
	}

	public void redo() {
		graph.addWindowCell(obj);
		graph.getApplication().addWindow(obj.getWindow());
	}

}
