package nc.lfw.jsp.swt;

import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIFlowhLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowhPanel;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class DFlowhLayout extends DLayout {

	public DFlowhLayout(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		UIFlowhLayout ele = (UIFlowhLayout) getUiElement();
		int size = ele.getPanelList().size();
		GridLayout gridLayout = new GridLayout(size, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);
	}

	protected void initialize() {
		UIFlowhLayout ele = (UIFlowhLayout) getUiElement();
		List<UILayoutPanel> list = ele.getPanelList();
		Iterator<UILayoutPanel> it = list.iterator();
		while(it.hasNext()){
			UIFlowhPanel pele = (UIFlowhPanel) it.next();
			DFlowhPanel flowhPanel = new DFlowhPanel(this, SWT.NONE, pele);
			GridData gd = new GridData();
			gd.grabExcessVerticalSpace = true;
			gd.verticalAlignment = GridData.FILL;
			
			String width = pele.getWidth();
			if(width == null || "".equals(width)|| Integer.parseInt(width) == 0){
				gd.grabExcessHorizontalSpace = true;
				gd.horizontalAlignment = GridData.FILL;
			}
			else{
				gd.widthHint = Integer.parseInt(width);
			}
			flowhPanel.setLayoutData(gd);
		}
	}

	
	protected Composite getComposite() {
		return this;
	}

	
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UIFlowhLayout uitab = (UIFlowhLayout) getUiElement();
		uitab.getPanelList().remove(uiPanel);
		if(uitab.getPanelList().size() == 0){
			DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
			UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
			uiParentPanel.setElement(null);
			parentPanel.initUI();
		}
		else
			this.initUI();
	}

	@Override
	protected String getName() {
		return UIConstant.DFLOWH;
	}

}
