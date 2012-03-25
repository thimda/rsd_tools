package nc.lfw.editor.widget;

import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PluginDescElementPart;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementPart;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.parts.LFWConnectinPart;
import nc.uap.lfw.perspective.listener.ListenerElementObj;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * @author guoweic
 *
 */
public class WidgetEditPartFactory implements EditPartFactory {

	private WidgetEditor editor = null;

	public WidgetEditPartFactory(WidgetEditor editor) {
		super();
		this.editor = editor;
	}

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart = null;
		if (model instanceof WidgetElementObj || model instanceof ListenerElementObj) {
			editPart = new WidgetElementPart(editor);
		} else if (model instanceof WidgetGraph) {
			editPart = new WidgetGraphPart(editor);
		} else if (model instanceof PlugoutDescElementObj) {
			editPart = new PlugoutDescElementPart();
		} else if (model instanceof PluginDescElementObj) {
			editPart = new PluginDescElementPart();
		} else if (model instanceof ChildConnection) {
			editPart = new LFWConnectinPart();
		} else
			throw new RuntimeException("illegal param");
		editPart.setModel(model);
		return editPart;
	}

}
