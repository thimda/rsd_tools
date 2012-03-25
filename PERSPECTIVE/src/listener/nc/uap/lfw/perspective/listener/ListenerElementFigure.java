package nc.uap.lfw.perspective.listener;

import java.io.File;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class ListenerElementFigure extends LFWBaseRectangleFigure {

	public static final String LISTENER_TYPE = "LISTENER_TYPE";

	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 215, 153, 205);

	public ListenerElementFigure(LfwElementObjWithGraph ele) {
		super(ele);
		ListenerElementObj listenerObj = (ListenerElementObj) ele;
		setTitleText(listenerObj.getId(), listenerObj.getId());

		setTypeLabText("<<Listener>>");

		setBackgroundColor(bgColor);

		listenerObj.setFigure(this);

		markError(listenerObj.validate());

		// ���ô�С��λ��
		Point point = listenerObj.getLocation();
		dimen = listenerObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width,
				dimen.height < this.height ? this.height : dimen.height));

		addListeners();

	}

	protected String getTypeText() {
		return "<<Listener>>";
	}

	/**
	 * ˢ��Listenerͼ��
	 */
	public void refreshListeners() {
		getContentFigure().getChildren().clear();
		this.height = 3 * LINE_HEIGHT;
		addListeners();
	}

	/**
	 * ��ʾ����Listener
	 */
	private void addListeners() {
		Map<String, JsListenerConf> listenerMap = ((ListenerElementObj) getElementObj())
				.getListenerMap();
		for (String id : listenerMap.keySet()) {
			JsListenerConf listener = listenerMap.get(id);
			String className = listener.getClass().getName();
			String name = className.substring(className.lastIndexOf(".") + 1);
			String text = "[" + name + "]" + id;
			JsListenerLabel label = new JsListenerLabel(text, listener);
			addToContent(label);
			this.height += LINE_HEIGHT;
			addListenerLabelListener(label);
			// ����Eventͼ��
			addEvents(listener);
		}
		resizeHeight();
	}

	/**
	 * ��ʾlistener�е�����event
	 * 
	 * @param jsListener
	 */
	private void addEvents(JsListenerConf jsListener) {
		Map<String, EventHandlerConf> eventMap = jsListener
				.getEventHandlerMap();
		for (String id : eventMap.keySet()) {
			EventHandlerConf event = eventMap.get(id);
			if (event.getScript() != null || event.isOnserver() || event.getSubmitRule() != null) {
				JsEventLabel eventLabel = new JsEventLabel(id, event);
				addToContent(eventLabel);
				this.height += LINE_HEIGHT;
				addEventLabelListener(jsListener, eventLabel);
			}
		}

	}

	/**
	 * ����event��label �¼�
	 * 
	 * @param eventLabel
	 */
	public void addEventLabelListener(JsListenerConf jsListener,
			JsEventLabel eventLabel) {
		final JsListenerConf newJsListener = jsListener;
		eventLabel.addMouseListener(new MouseListener() {

			public void mousePressed(MouseEvent e) {

			}

			public void mouseDoubleClicked(MouseEvent e) {
				//TODO ����ʹ������
				
				JsEventLabel currentLabel = (JsEventLabel) e.getSource();
				// // ��õ�ǰEditor
				LFWBaseEditor editor = ((ListenerElementObj) getElementObj())
						.getEditor();
				// ȡ��������������ѡ��״̬
				if (editor instanceof LFWBaseEditor)
					((LFWBaseEditor) editor).getGraph().unSelectAllLabels();

				// ѡ�и�����
				selectLabel(currentLabel);

				// �����¼�����ҳ
				LFWBaseEditor.getActiveEditor().getViewPage()
						.addListenerPropertiesView(newJsListener);

				((ListenerElementObj) getElementObj())
						.setCurrentListener(newJsListener);

				// ������ʾ��������
				reloadPropertySheet((ListenerElementObj) getElementObj());

				EventHandlerConf jsEventObj = (EventHandlerConf) currentLabel
						.getEditableObj();
				if (!jsEventObj.isOnserver()) {
					if (editor instanceof LFWBaseEditor) {
						ListenerEditorHandler listenerHandler = ((LFWBaseEditor) editor)
								.getListenerHandler();
						listenerHandler.createEventEditorItem(jsEventObj,
								newJsListener.getId(), ((LFWBaseEditor) editor)
										.getTabFolder(), editor);
					}
				}

				// ��java�༭����
				else if (jsEventObj.isOnserver()) {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					try {
						String javaEditor = "org.eclipse.jdt.ui.CompilationUnitEditor";
						String path = newJsListener.getServerClazz();
						if (path == null || "".equals(path)) {
							MessageDialog.openError(null, "������ʾ", "�������÷���������!");
							return;
						}
						// ��projectExplorer��ͼ
						page.showView("org.eclipse.ui.navigator.ProjectExplorer");
						// ͨ��treeItem�õ�project
						IProject proj = LFWPersTool.getCurrentProject();
						JavaProject javaProj = (JavaProject) JavaCore
								.create(proj);
						IPackageFragmentRoot[] pfrs;
						pfrs = javaProj.getAllPackageFragmentRoots();
						PackageFragmentRoot root = null;
						if (pfrs != null) {
							for (int i = 0; i < pfrs.length; i++) {
								if (pfrs[i] instanceof JarPackageFragmentRoot
										|| pfrs[i] instanceof ExternalPackageFragmentRoot)
									continue;
								else if (pfrs[i] instanceof PackageFragmentRoot) {
									root = (PackageFragmentRoot) pfrs[i];
									String absPath = proj.getLocation()
											.toString()
											+ "/"
											+ root.getPath()
													.removeFirstSegments(1)
													.makeRelative() + "/";
									absPath += path.replaceAll("\\.", "/");
									absPath += ".java";
									File f = new File(absPath);
									if (f.exists()) {
										root = (PackageFragmentRoot) pfrs[i];
										break;
									}
								}

							}
						}

						if (root == null) {
							MessageDialog.openError(null, "������ʾ", "û���ҵ��ļ����ڰ�!");
							return;
						}
						String filePath = root.getPath().toString() + "/"
								+ path.replaceAll("\\.", "/") + ".java";
						Workspace wp = (Workspace) ResourcesPlugin
								.getWorkspace();
						IPath ph = new Path(filePath);
						IFile file = (IFile) wp.newResource(ph, IResource.FILE);
						FileEditorInput input = new FileEditorInput(file);
						page.openEditor(input, javaEditor);
					} catch (PartInitException e1) {
						e1.printStackTrace();
					} catch (JavaModelException e1) {
						e1.printStackTrace();
					}
				}
			}

			public void mouseReleased(MouseEvent me) {

			}
		});
	}

	/**
	 * ����Listener��Label�¼�
	 * 
	 * @param label
	 */
	public void addListenerLabelListener(JsListenerLabel label) {
		label.addMouseListener(new MouseListener() {
			public void mouseDoubleClicked(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				JsListenerLabel currentLabel = (JsListenerLabel) e.getSource();
				// ��õ�ǰEditor
				LFWBaseEditor editor = ((ListenerElementObj) getElementObj())
						.getEditor();
				// ȡ��������������ѡ��״̬
				if (editor instanceof LFWBaseEditor)
					((LFWBaseEditor) editor).getGraph().unSelectAllLabels();

				// ѡ�и�����
				selectLabel(currentLabel);

				JsListenerConf jsListener = (JsListenerConf) currentLabel
						.getEditableObj();

				// �����¼�����ҳ
				LFWBaseEditor.getActiveEditor().getViewPage().addListenerPropertiesView(jsListener);

				((ListenerElementObj) getElementObj())
						.setCurrentListener(jsListener);

				// ������ʾ��������
				reloadPropertySheet((ListenerElementObj) getElementObj());

			}
		});
	}

	/**
	 * ����Listener
	 * 
	 * @param jsListener
	 */
	public void addListener(JsListenerConf jsListener) {
//		refreshListeners();
		Map<String, JsListenerConf> listenerMap = ((ListenerElementObj) getElementObj())
				.getListenerMap();
		int index = listenerMap.keySet().size();
		for (String id : listenerMap.keySet()) {
			JsListenerConf listener = listenerMap.get(id);
			Map<String, EventHandlerConf> evnetMap = listener
					.getEventHandlerMap();
			index += evnetMap.keySet().size();
		}
		listenerMap.put(jsListener.getId(), jsListener);
		String className = jsListener.getClass().getName();
		String name = className.substring(className.lastIndexOf(".") + 1);
		String text = "[" + name + "]" + jsListener.getId();
		JsListenerLabel label = new JsListenerLabel(text, jsListener);
		addToContent(label, index);
		addListenerLabelListener(label);
		this.height += LINE_HEIGHT;
		resizeHeight();
	}

	/**
	 * ����event
	 * 
	 * @param jsListener
	public void addEvent(JsListenerConf jsListener, EventHandlerConf event) {
		Map<String, EventHandlerConf> eventMap = jsListener
				.getEventHandlerMap();
		int index = eventMap.keySet().size();
		eventMap.put(event.getName(), event);
		JsEventLabel label = new JsEventLabel(event.getName(), event);
		addToContent(label, index);
		this.height += LINE_HEIGHT;
		resizeHeight();
	}
	 */

	/**
	 * ɾ��Listener
	 * 
	 * @param jsListener
	 */
	public void removeListener(JsListenerLabel label) {
		Map<String, JsListenerConf> listenerMap = ((ListenerElementObj) getElementObj())
				.getListenerMap();
//		String id = label.getText();
		String id = label.getText().substring(label.getText().lastIndexOf("]") + 1);
		
		removeEvents(listenerMap.get(id));
		
		listenerMap.remove(id);
		removeFromContent(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * ɾ��Listener�µ�����Event��Label
	 * @param jsListener
	 */
	private void removeEvents(JsListenerConf jsListener) {
		Map<String, EventHandlerConf> eventMap = jsListener.getEventHandlerMap();
		for (String id : eventMap.keySet()) {
			EventHandlerConf event = eventMap.get(id);
			if (event.getScript() != null || event.isOnserver() || event.getSubmitRule() != null) {
				List<IFigure> children = getContentFigure().getChildren();
				for (int i = children.size() - 1; i >= 0; i--) {
					IFigure figure = children.get(i);
					if (figure instanceof JsEventLabel) {
						EventHandlerConf e = (EventHandlerConf) ((JsEventLabel)figure).getEditableObj();
						if (event == e)
							removeFromContent(figure);
						this.height -= LINE_HEIGHT;
					}
				}
			}
		}
	}

	/**
	 * �������ø߶�
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height
				: dimen.height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
