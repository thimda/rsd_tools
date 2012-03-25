package nc.lfw.jsp.dialog;

import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.ChartComp;
import nc.uap.lfw.core.comp.CheckBoxComp;
import nc.uap.lfw.core.comp.CheckboxGroupComp;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.IFrameComp;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.comp.LinkComp;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.RadioComp;
import nc.uap.lfw.core.comp.RadioGroupComp;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.comp.SelfDefComp;
import nc.uap.lfw.core.comp.TextAreaComp;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.text.ComboBoxComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIConstant;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;



public class WidgetDialog extends Dialog {
	private PageMeta pm;
	private LfwWidget widget;
	private String[] selectId = new String[3];
	//private String formcompStyle = "defaultStyle";
	private int type;
	public static final int TYPE_WIDGET = 1;
	public static final int TYPE_TREE = 2;
	public static final int TYPE_GRID = 3;
	public static final int TYPE_TEXT = 4;
	public static final int TYPE_FORM = 5;
	public static final int TYPE_BUTTON = 6;
	public static final int TYPE_MENUBAR = 7;
	public static final int TYPE_IFRAME = 8;
	public static final int TYPE_LABEL = 9;
	public static final int TYPE_IMAGE = 10;
	public static final int TYPE_TOOLBAR = 11;
	public static final int TYPE_LINKCOMP = 12;
	public static final int SELF_TYPE_FORM = 13;         //自定义表单类型
	
	public static final int TYPE_SELFDEFCOMP = 14;
	public static final int TYPE_CHARTCOMP = 15;
	
	//EXCEL
	public static final int TYPE_EXCELCOMP = 16;
	
	public static final String TEXT_STRING = "字符输入框";
	public static final String TEXT_COMBO = "下拉框";
	public static final String TEXT_PASSWORD = "密码框";
	public static final String TEXT_CHECKBOX = "CheckBox";
	public static final String TEXT_REFERENCE = "参照";
	public static final String TEXT_TEXTAREA = "TextArea";
	public static final String TEXT_RADIOGROP = "RadioGroupComp";
	public static final String TEXT_CHECKBOXGROUP = "CheckboxGroupComp";
	public static final String TEXT_RADIOCOMP = "RadioComp";
	
	public WidgetDialog(Shell parentShell, PageMeta pm, LfwWidget widget, int type) {
		super(parentShell);
		this.type = type;
		this.pm = pm;
		this.widget = widget;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gd);
		
		container.setLayout(new GridLayout(1, false));
		
		Table t = new Table(container, SWT.BORDER);
		t.setLayoutData(gd);
		t.setHeaderVisible(true);
		
		if(type == TYPE_WIDGET)
			setWidgetData(t);
		else if(type == TYPE_TREE)
			setTreeData(t);
		else if(type == TYPE_GRID)
			setGridData(t);
		else if(type == TYPE_TEXT)
			setTextData(t);
		else if(type == TYPE_FORM){
			setFormData(t);
			//************************* add by limingf
			/*Composite composite = new Composite(container,SWT.NONE);
			composite.setLayout(new GridLayout(2,false));
			Button bt1 = new Button(composite,SWT.RADIO);
			bt1.setText("默认布局");
			
			Button bt2 = new Button(composite,SWT.RADIO);
			bt2.setText("自定义布局");
		bt1.setSelection(true);
			bt1.addSelectionListener(new SelectionListener(){
				public void widgetDefaultSelected(SelectionEvent e) {
				}
				public void widgetSelected(SelectionEvent e) {
					formcompStyle = "defaultStyle";
				}
			});
			bt2.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			public void widgetSelected(SelectionEvent e) {
				formcompStyle = "selfdefStyle";
			}
		});*/
			//*************************
		}
		else if(type == TYPE_BUTTON)
			setButtonData(t);
		else if(type == TYPE_LABEL)
			setLabelData(t);
		else if(type == TYPE_IMAGE)
			setImageData(t);
		else if(type == TYPE_LINKCOMP)
			setLinkCompData(t);
		else if(type == TYPE_MENUBAR)
			setMenubarData(t);
		else if(type == TYPE_IFRAME)
			setIFrameData(t);
		else if(type == TYPE_TOOLBAR)
			setToolbarData(t);
		else if(type == TYPE_SELFDEFCOMP)
			setSelfDefData(t);
		else if(type == TYPE_CHARTCOMP)
			setChartData(t);
		else if(type == TYPE_EXCELCOMP)
			setExcelData(t);
		
		t.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				selectId[0] = ((TableItem)e.item).getText(0);
				selectId[1] = ((TableItem)e.item).getText(1);
				String type = ((TableItem)e.item).getText(2);
				if(type != null){
					if(type.equals(TEXT_STRING))
						selectId[2] = UIConstant.TEXT_TYPE_STRING;
					else if(type.equals(TEXT_COMBO)){
						selectId[2] = UIConstant.TEXT_TYPE_COMBO;
					}
					else if(type.equals(TEXT_RADIOCOMP))
						selectId[2] = UIConstant.TEXT_RADIOCOMP;
					else if(type.equals(TEXT_CHECKBOX))
						selectId[2] = UIConstant.TEXT_TYPE_CHECKBOX;
					else if(type.equals(TEXT_TEXTAREA))
						selectId[2] = UIConstant.TEXT_TYPE_TEXTAREA;
					else if(type.equals(TEXT_RADIOGROP))
						selectId[2] = UIConstant.TEXT_RADIOGROUP;
					else if(type.equals(TEXT_CHECKBOXGROUP))
						selectId[2] = UIConstant.TEXT_CHECKBOXGROUP;
					else
						selectId[2] = UIConstant.TEXT_TYPE_STRING;
				}
			}
		});

		return container;
	}
	private void setIFrameData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("子页面ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				IFrameComp[] gridComps = (IFrameComp[]) tmpWidget.getViewComponents().getComponentByType(IFrameComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						IFrameComp grid = (IFrameComp) gridComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] buttonComps = (WebComponent[]) widget.getViewComponents().getComponentByType(IFrameComp.class);
			if(buttonComps != null){
				for (int i = 0; i < buttonComps.length; i++) {
					IFrameComp grid = (IFrameComp) buttonComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{grid.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setToolbarData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("工具条ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] toolbarComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(ToolBarComp.class);
				if(toolbarComps != null){
					for (int i = 0; i < toolbarComps.length; i++) {
						ToolBarComp toolbarcomp = (ToolBarComp) toolbarComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{toolbarcomp.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] toolbarComps = (WebComponent[]) widget.getViewComponents().getComponentByType(ToolBarComp.class);
			if(toolbarComps != null){
				for (int i = 0; i < toolbarComps.length; i++) {
					ToolBarComp toolbar = (ToolBarComp) toolbarComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{toolbar.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setMenubarData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("菜单ID");
		tc1.setWidth(260);
		
//		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
//		tc2.setText("所属片段");
//		tc2.setWidth(100);
		
		if(widget == null){
			MenubarComp[] menubars = pm.getViewMenus().getMenuBars();
			for (MenubarComp menubar : menubars) {
				TableItem item1 = new TableItem(t, SWT.NONE);
				item1.setText(new String[]{menubar.getId()});
			}
		}
		else {
			tc1.setWidth(140);
			TableColumn tc2 = new TableColumn(t, SWT.CENTER);
			tc2.setText("所属片段");
			tc2.setWidth(100);
			MenubarComp[] menubars = widget.getViewMenus().getMenuBars();
			for (MenubarComp menubar : menubars) {
				TableItem item1 = new TableItem(t, SWT.NONE);
				item1.setText(new String[]{menubar.getId(), widget.getId()});
			}
		}
	}
	
	private void setLabelData(Table t){
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("标签ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] labelComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(LabelComp.class);
				if(labelComps != null){
					for (int i = 0; i < labelComps.length; i++) {
						LabelComp label = (LabelComp) labelComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{label.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] labelComps = (WebComponent[]) widget.getViewComponents().getComponentByType(LabelComp.class);
			if(labelComps != null){
				for (int i = 0; i < labelComps.length; i++) {
					LabelComp label = (LabelComp) labelComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{label.getId(), widget.getId()});
				}
			}
		}
	}
	
	
	private void setImageData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("图片ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] imageComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(ImageComp.class);
				if(imageComps != null){
					for (int i = 0; i < imageComps.length; i++) {
						ImageComp image = (ImageComp) imageComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{image.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] imageComps = (WebComponent[]) widget.getViewComponents().getComponentByType(ImageComp.class);
			if(imageComps != null){
				for (int i = 0; i < imageComps.length; i++) {
					ImageComp image = (ImageComp) imageComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{image.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setLinkCompData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("链接ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] linkComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(LinkComp.class);
				if(linkComps != null){
					for (int i = 0; i < linkComps.length; i++) {
						LinkComp link = (LinkComp) linkComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{link.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] linkComps = (WebComponent[]) widget.getViewComponents().getComponentByType(LinkComp.class);
			if(linkComps != null){
				for (int i = 0; i < linkComps.length; i++) {
					LinkComp link = (LinkComp) linkComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{link.getId(), widget.getId()});
				}
			}
		}
	}
	
	
	private void setButtonData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("按钮ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(ButtonComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						ButtonComp grid = (ButtonComp) gridComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] buttonComps = (WebComponent[]) widget.getViewComponents().getComponentByType(ButtonComp.class);
			if(buttonComps != null){
				for (int i = 0; i < buttonComps.length; i++) {
					ButtonComp grid = (ButtonComp) buttonComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{grid.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setChartData(Table t){
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("图表ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
//			LfwWidget[] widgets = pm.getWidgets();
//			for (LfwWidget tmpWidget : widgets) {
//				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(ButtonComp.class);
//				if(gridComps != null){
//					for (int i = 0; i < gridComps.length; i++) {
//						ButtonComp grid = (ButtonComp) gridComps[i];
//						TableItem item1 = new TableItem(t, SWT.NONE);
//						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
//					}
//				}
//			}
		}
		else{
			WebComponent[] chartComps = (WebComponent[]) widget.getViewComponents().getComponentByType(ChartComp.class);
			if(chartComps != null){
				for (int i = 0; i < chartComps.length; i++) {
					ChartComp chart = (ChartComp) chartComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{chart.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setExcelData(Table t){
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("Excel控件ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
//			LfwWidget[] widgets = pm.getWidgets();
//			for (LfwWidget tmpWidget : widgets) {
//				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(ButtonComp.class);
//				if(gridComps != null){
//					for (int i = 0; i < gridComps.length; i++) {
//						ButtonComp grid = (ButtonComp) gridComps[i];
//						TableItem item1 = new TableItem(t, SWT.NONE);
//						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
//					}
//				}
//			}
		}
		else{
			WebComponent[] excelComps = (WebComponent[]) widget.getViewComponents().getComponentByType(ExcelComp.class);
			if(excelComps != null){
				for (int i = 0; i < excelComps.length; i++) {
					ExcelComp excel = (ExcelComp) excelComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{excel.getId(), widget.getId()});
				}
			}
		}
	}
	
	private void setSelfDefData(Table t){
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("自定义控件ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] selfComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(SelfDefComp.class);
				if(selfComps != null){
					for (int i = 0; i < selfComps.length; i++) {
						SelfDefComp self = (SelfDefComp) selfComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{self.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] selfdefComps = (WebComponent[]) widget.getViewComponents().getComponentByType(SelfDefComp.class);
			if(selfdefComps != null){
				for (int i = 0; i < selfdefComps.length; i++) {
					SelfDefComp self = (SelfDefComp) selfdefComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{self.getId(), widget.getId()});
				}
			}
		}
	}
	
	
	private void setFormData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("表单ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(FormComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						FormComp grid = (FormComp) gridComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] formComps = (WebComponent[]) widget.getViewComponents().getComponentByType(FormComp.class);
			if(formComps != null){
				for (int i = 0; i < formComps.length; i++) {
					FormComp grid = (FormComp) formComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{grid.getId(), widget.getId()});
				}
			}
		}
	}
	private void setTextData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("文本框ID");
		tc1.setWidth(120);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(70);
		
		TableColumn tc3 = new TableColumn(t, SWT.CENTER);
		tc3.setText("类型");
		tc3.setWidth(80);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(TextComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						TextComp text = (TextComp) gridComps[i];
						String[] values = new String[3];
						values[0] = text.getId();
						values[1] = tmpWidget.getId();
						String type = TEXT_STRING;
						if(text instanceof ComboBoxComp){
							type = TEXT_COMBO;
						}
						else if(text instanceof CheckBoxComp)
							type = TEXT_CHECKBOX;
						else if(text instanceof ReferenceComp)
							type = TEXT_REFERENCE;
						else if(text instanceof TextAreaComp)
							type = TEXT_TEXTAREA;
						else if(text instanceof RadioGroupComp)
							type = TEXT_RADIOGROP;
						else if(text instanceof CheckboxGroupComp)
							type = TEXT_CHECKBOXGROUP;
						else if(text instanceof RadioComp)
							type = TEXT_RADIOCOMP;
						//else if(text instanceof TextComp)
						values[2] = type;
						//item1.setText(values);
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(values);
					}
				}
			}
		}
		else{
			WebComponent[] textComps = (WebComponent[]) widget.getViewComponents().getComponentByType(TextComp.class);
			if(textComps != null){
				for (int i = 0; i < textComps.length; i++) {
					TextComp text = (TextComp) textComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					String[] values = new String[3];
					values[0] = text.getId();
					values[1] = widget.getId();
					String type = TEXT_STRING;
					if(text instanceof ComboBoxComp){
						type = TEXT_COMBO;
					}
					else if(text instanceof CheckBoxComp)
						type = TEXT_CHECKBOX;
					else if(text instanceof ReferenceComp)
						type = TEXT_REFERENCE;
					else if(text instanceof TextAreaComp)
						type = TEXT_TEXTAREA;
					else if(text instanceof RadioGroupComp)
						type = TEXT_RADIOGROP;
					else if(text instanceof CheckboxGroupComp)
						type = TEXT_CHECKBOXGROUP;
					else if(text instanceof RadioComp)
						type = TEXT_RADIOCOMP;
					//else if(text instanceof TextComp)
					values[2] = type;
					item1.setText(values);
				}
			}
		}
	}
	private void setGridData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("表格ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(GridComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						GridComp grid = (GridComp) gridComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] gridComps = (WebComponent[]) widget.getViewComponents().getComponentByType(GridComp.class);
			if(gridComps != null){
				for (int i = 0; i < gridComps.length; i++) {
					GridComp grid = (GridComp) gridComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{grid.getId(), widget.getId()});
				}
			}
		}
	}
	private void setTreeData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("树ID");
		tc1.setWidth(140);
		
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		tc2.setText("所属片段");
		tc2.setWidth(100);
		
		if(widget == null){
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget tmpWidget : widgets) {
				WebComponent[] gridComps = (WebComponent[]) tmpWidget.getViewComponents().getComponentByType(TreeViewComp.class);
				if(gridComps != null){
					for (int i = 0; i < gridComps.length; i++) {
						TreeViewComp grid = (TreeViewComp) gridComps[i];
						TableItem item1 = new TableItem(t, SWT.NONE);
						item1.setText(new String[]{grid.getId(), tmpWidget.getId()});
					}
				}
			}
		}
		else{
			WebComponent[] treeComps = (WebComponent[]) widget.getViewComponents().getComponentByType(TreeViewComp.class);
			if(treeComps != null){
				for (int i = 0; i < treeComps.length; i++) {
					TreeViewComp grid = (TreeViewComp) treeComps[i];
					TableItem item1 = new TableItem(t, SWT.NONE);
					item1.setText(new String[]{grid.getId(), widget.getId()});
				}
			}
		}
	}

	private void setWidgetData(Table t) {
		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		tc1.setText("片段ID");
		tc1.setWidth(280);

		LfwWidget[] widgets = pm.getWidgets();
		for (LfwWidget tmpWidget : widgets) {
			TableItem item1 = new TableItem(t, SWT.NONE);
			item1.setText(new String[]{tmpWidget.getId()});
		}
	}

	
	protected void okPressed() {
		if(selectId == null || selectId[0] == null){
			MessageDialog.openInformation(this.getShell(), "提示", "请选择一个片段");
			return;
		}
		super.okPressed();
	}
	
	public String[] getSelectId() {
		return selectId;
	}
	public PageMeta getPm() {
		return pm;
	}
	public void setPm(PageMeta pm) {
		this.pm = pm;
	}
	public LfwWidget getWidget() {
		return widget;
	}
	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	protected Point getInitialSize() {
		return new Point(300, 400);
	}
/*	
	public String getFormcompStyle(){
		return formcompStyle;
	}*/
}
