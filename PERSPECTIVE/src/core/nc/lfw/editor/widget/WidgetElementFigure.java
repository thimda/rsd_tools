package nc.lfw.editor.widget;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * Widgetͼ��
 * @author guoweic
 *
 */
public class WidgetElementFigure extends LFWBaseRectangleFigure {

	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 239, 255, 150);
	
	public WidgetElementFigure(WidgetElementObj widgetObj) {
		super(widgetObj);
//		LfwWidgetConf widget = widgetObj.getWidget();
//		addPropertity(widgetObj.PROP_WIDGET_ID, widget.getId());
//		addPropertity(widgetObj.PROP_ISMAIN, isMainWidget ? "��" : "��");
		
//		boolean isMainWidget = widgetObj.isMainWidget();
//		if (isMainWidget)
//			setTypeLabText("<<" + LFWExplorerTreeView.MAIN_WIDGET_CN + ">>");
//		else
//			setTypeLabText("<<" + LFWExplorerTreeView.WIDGET_CN + ">>");
		setTypeLabText("<<" + LFWTool.getViewText(null) + ">>");
		
		setBackgroundColor(bgColor);
		
		widgetObj.setFigure(this);
		
		setTitleText(widgetObj.getWidget().getId(), widgetObj.getWidget().getId());

		addSignals();
		addSlots();
		
		markError(widgetObj.validate());
		// ���ô�С��λ��
		Point point = widgetObj.getLocation();
		dimen = widgetObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}

	
	protected String getTypeText() {
//		boolean isMainWidget = ((WidgetElementObj)getElementObj()).isMainWidget();
//		if (isMainWidget)
//			return "<<MainWidget>>";
		return "<<Widget>>";
	}
	
	/**
	 * ��ʾ�����ź�
	 */
	private void addSignals() {
//		Map<String, LfwSignal> signalMap = ((WidgetElementObj)getElementObj()).getWidget().getSignalMap();
//		for (String signalId : signalMap.keySet()) {
//			SignalLabel label = new SignalLabel(signalId, signalMap.get(signalId));
//			addToContent(label);
//			this.height += LINE_HEIGHT;
//			addSignalLabelListener(label);
//		}
	}
	
	/**
	 * ��ʾ���в�
	 */
	private void addSlots() {
//		Map<String, LfwSlot> slotMap = ((WidgetElementObj)getElementObj()).getWidget().getSlotMap();
//		for (String slotId : slotMap.keySet()) {
//			SlotLabel label = new SlotLabel(slotId, slotMap.get(slotId));
//			addToContent(label);
//			this.height += LINE_HEIGHT;
//			addSlotLabelListener(label);
//		}
	}
	
//	/**
//	 * �����ź�Label���¼�
//	 * @param label
//	 */
//	private void addSignalLabelListener(SignalLabel label) {
//		label.addMouseListener(new MouseListener.Stub() {
//			public void mouseDoubleClicked(MouseEvent e) {
//				
//			}
//			public void mouseReleased(MouseEvent e) {
//				
//			}
//			public void mousePressed(MouseEvent e) {
//				SignalLabel currentLabel = (SignalLabel) e.getSource();
//				// ȡ��������������ѡ��״̬
////				PagemetaEditor.getActivePagemetaEditor().getGraph().unSelectAllLabels();
//				LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
//				// ѡ�и�����
//				selectLabel(currentLabel);
//			}
//		});
//	}
//	
//	/**
//	 * ���Ӳ�Label���¼�
//	 * @param label
//	 */
//	private void addSlotLabelListener(SlotLabel label) {
//		label.addMouseListener(new MouseListener.Stub() {
//			public void mouseDoubleClicked(MouseEvent e) {
//				
//			}
//			public void mouseReleased(MouseEvent e) {
//				
//			}
//			public void mousePressed(MouseEvent e) {
//				SlotLabel currentLabel = (SlotLabel) e.getSource();
//				// ȡ��������������ѡ��״̬
////				PagemetaEditor.getActivePagemetaEditor().getGraph().unSelectAllLabels();
//				LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
//				// ѡ�и�����
//				selectLabel(currentLabel);
//			}
//		});
//	}
//	
//	/**
//	 * �����ź�
//	 * @param signal
//	 */
//	public void addSignal(LfwPlugout signal) {
////		Map<String, LfwSignal> signalMap = ((WidgetElementObj)getElementObj()).getWidget().getSignalMap();
////		int index = signalMap.keySet().size() - 1;
////		// signalMap.put(signal.getId(), signal);
////		SignalLabel label = new SignalLabel(signal.getId(), signal);
////		addToContent(label, index);
////		addSignalLabelListener(label);
////		this.height += LINE_HEIGHT;
////		resizeHeight();
//	}
//	
//	/**
//	 * ���Ӳ�
//	 * @param slot
//	 */
//	public void addSlot(LfwPlugin slot) {
////		LfwWidget widget = ((WidgetElementObj)getElementObj()).getWidget();
////		Map<String, LfwSlot> slotMap = widget.getSlotMap();
////		int index = slotMap.keySet().size() + widget.getSignalMap().keySet().size() - 1;
////		// slotMap.put(slot.getId(), slot);
////		SlotLabel label = new SlotLabel(slot.getId(), slot);
////		addToContent(label, index);
////		addSlotLabelListener(label);
////		this.height += LINE_HEIGHT;
////		resizeHeight();
//	}
//	
//	/**
//	 * ɾ���ź���
//	 * @param label
//	 */
//	public void deleteSignal(SignalLabel label) {
////		Map<String, LfwSignal> signalMap = ((WidgetElementObj)getElementObj()).getWidget().getSignalMap();
////		signalMap.remove(((LfwSignal) label.getEditableObj()).getId());
////		getContentFigure().remove(label);
////		this.height -= LINE_HEIGHT;
////		resizeHeight();
//	}
//	
//	/**
//	 * ɾ������
//	 * @param label
//	 */
//	public void deleteSlot(SlotLabel label) {
////		Map<String, LfwSlot> slotMap = ((WidgetElementObj)getElementObj()).getWidget().getSlotMap();
////		slotMap.remove(((LfwSlot) label.getEditableObj()).getId());
////		getContentFigure().remove(label);
////		this.height -= LINE_HEIGHT;
////		resizeHeight();
//	}
	
	/**
	 * �������ø߶�
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height : dimen.height);
	}

	
}
