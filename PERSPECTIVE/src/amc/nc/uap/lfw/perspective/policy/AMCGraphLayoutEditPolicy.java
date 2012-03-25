/**
 * 
 */
package nc.uap.lfw.perspective.policy;

import nc.uap.lfw.editor.application.ApplicationCreateCommand;
import nc.uap.lfw.editor.application.ApplicationGraph;
import nc.uap.lfw.editor.application.ApplicationObj;
import nc.uap.lfw.editor.window.WindowAddCommand;
import nc.uap.lfw.editor.window.WindowObj;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 
 * 布局编辑命令处理类
 * @author chouhl
 *
 */
public class AMCGraphLayoutEditPolicy extends LFWGraphLayoutEditPolicy {

	protected Command getCreateCommand(CreateRequest request) {
		Object obj = request.getNewObject();
		if(ApplicationObj.class.isInstance(obj)){
			return new ApplicationCreateCommand((ApplicationObj) obj, (ApplicationGraph) getHost().getModel(), (Rectangle) getConstraintFor(request));
		}else if(WindowObj.class.isInstance(obj)){
			return new WindowAddCommand((WindowObj) obj, (ApplicationGraph) getHost().getModel(), (Rectangle) getConstraintFor(request));
		}
		return null;
	}
	
}
