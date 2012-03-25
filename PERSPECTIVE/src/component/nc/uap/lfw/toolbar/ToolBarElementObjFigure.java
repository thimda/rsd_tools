package nc.uap.lfw.toolbar;

import java.util.HashMap;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.comp.ToolBarItem;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class ToolBarElementObjFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 115,204,101);
	private ToolBarElementObj toolbarobj;

	// �ܸ߶�
	private int height = 0;
	public ToolBarElementObjFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<������>>");
		setBackgroundColor(bgColor);
		toolbarobj = (ToolBarElementObj) ele;
		toolbarobj.setFigure(this);
		setTitleText(toolbarobj.getToolbarComp().getId(), toolbarobj.getToolbarComp().getId());
		markError(toolbarobj.validate());
		addItems();
		Point point = toolbarobj.getLocation();
		int x, y;
		if(point != null){
			x = point.x;
			y = point.y;
		}else{
			x = 100;
			y = 100;
		}
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(x, y, 150,
				150 < this.height ? this.height : 150));
	}

	
	
	protected String getTypeText() {
		return "<<������>>";
	}
	
	/**
	 * �������ø߶�
	 */
	private void resizeHeight() {
		setSize(150, 150 < this.height ? this.height
				: 150);
	}

//	MatchField match = matches[i];
//	String isMatch = match.getIsmacth();
//	String writeField = match.getWriteField();
//	DsFieldLabel lab = new DsFieldLabel(writeField, isMatch);
//	hmAttrToPropLabel.put(writeField, lab);
//	getAttrsFigure().add(lab);
//	this.height += LINE_HEIGHT;
//	resizeHeight();
	
	private HashMap<ToolBarItem, ToolBarItemLabel> hmAttrToPropLabel = new HashMap<ToolBarItem, ToolBarItemLabel>();
	public void addItem(ToolBarItem toolbarItem) {
		ToolBarComp toolbar = toolbarobj.getToolbarComp();
		int index = 0;
		if (null != toolbar.getElements())
			index = toolbar.getElements().length -1 ;
		//toolbar.addElement(toolbarItem);
		ToolBarItemLabel label = new ToolBarItemLabel(toolbarItem);
		hmAttrToPropLabel.put(toolbarItem, label);
		addToContent(label, index);
		addItemLabelListener(label);
		this.height += LINE_HEIGHT;
		resizeHeight();
	}
	
	
	public void deleteToolBarItem(ToolBarItem toolbarItem){
		ToolBarComp toolbar = toolbarobj.getToolbarComp();
		if (toolbar.getElementById(toolbarItem.getId()) != null) {
			toolbar.deleteElement(toolbarItem);
		}
		ToolBarItemLabel label = hmAttrToPropLabel.get(toolbarItem);
		getContentFigure().remove(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * ɾ������
	 * 
	 * @param label
	 */
	public void deleteItem(ToolBarItemLabel label) {
		ToolBarItem item = (ToolBarItem) label.getEditableObj();
		ToolBarComp toolbar = toolbarobj.getToolbarComp();
		if (toolbar.getElementById(item.getId()) != null) {
			toolbar.deleteElement(item);
		}
		getContentFigure().remove(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	public void updatItem(ToolBarItem item){
		ToolBarItemLabel figure = hmAttrToPropLabel.get(item);
		if(figure != null)
			figure.updateFigure(item);
	}
	
	private void addItemLabelListener(ToolBarItemLabel label) {
		label.addMouseListener(new MouseListener.Stub() {
			public void mouseDoubleClicked(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				ToolBarItemLabel currentLabel = (ToolBarItemLabel) e.getSource();
				// ȡ��������������ѡ��״̬
				LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
				// ѡ�и�����
				selectLabel(currentLabel);

				// ��ʾ����
				ToolBarItem currentItem = (ToolBarItem) ((ToolBarItemLabel) currentLabel).getEditableObj();
				toolbarobj.setCurrentItem(currentItem);
					// ������ʾ��������
				reloadPropertySheet(toolbarobj);
//				
//				// ������ʾListener����
				((ToolBarGraph)ToolBarEditor.getActiveEditor().getGraph())
						.reloadListenerFigure((ToolBarItem)currentLabel.getEditableObj());
			}
		});
	}
	
	/**
	 * ��ʾ��������
	 */
	private void addItems() {
		ToolBarComp toolbar = toolbarobj.getToolbarComp();
		ToolBarItem[] items = toolbar.getElements();
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				ToolBarItem item =items[i];
				ToolBarItemLabel label = new ToolBarItemLabel(item);
				addToContent(label);
				hmAttrToPropLabel.put(item, label);
				this.height += LINE_HEIGHT;
				addItemLabelListener(label);
			}
		}
	}
}
	
