package nc.lfw.jsp.swt;

import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.core.comp.GridColumn;
import nc.uap.lfw.core.comp.GridColumnGroup;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.IGridColumn;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DGridComp extends DComponent {
	private Table t = null;
	public DGridComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	protected void initialize() {
		
		t = new Table(this, SWT.BORDER);
		t.setData(this);
		
		t.setHeaderVisible(true);
		
		GridComp grid = (GridComp) getWebElement();
		List<IGridColumn> list = grid.getColumnList();
		if(list == null)
			return;
		Iterator<IGridColumn> it = list.iterator();
		while(it.hasNext()){
			/* 只显示 GridColumn ，不显示 GridColumnGroup*/
			IGridColumn gridColumn = it.next();
			if(gridColumn instanceof GridColumnGroup)
				continue;
			if (gridColumn instanceof GridColumn){
				GridColumn col = (GridColumn) gridColumn;
				TableColumn tc1 = new TableColumn(t, SWT.CENTER);
				String text = col.getText();
				if(text == null)
					text = "未命名";
				tc1.setText(text);
				int width = col.getWidth();
				if(width == -1)
					width = 140;
				tc1.setWidth(width);
			}
		}
		
		int rowSize = 5;
		int colSize = 3;//list.size();
		for (int i = 0; i < rowSize; i++) {
			String[] cols = new String[colSize];
			for (int j = 0; j < cols.length; j++) {
				cols[j] = "data" + i + j;
			}
			TableItem item1 = new TableItem(t, SWT.NONE);
			item1.setText(cols);
		}
//		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
//		TableColumn tc3 = new TableColumn(t, SWT.CENTER);
//		tc2.setText("第二列");
//		tc2.setWidth(100);
//		tc3.setText("第三列");
//		tc3.setWidth(100);
		
		
//		
//		TableItem item2 = new TableItem(t, SWT.NONE);
//		item2.setText(new String[]{"data21", "data22", "data23"});
//		
//		
//		TableItem item3 = new TableItem(t, SWT.NONE);
//		item3.setText(new String[]{"data31", "data32", "data33"});
	}

	
	protected Composite getComposite() {
		return t;
	}

	@Override
	protected String getName() {
		return UIConstant.DGRID;
	}

}
