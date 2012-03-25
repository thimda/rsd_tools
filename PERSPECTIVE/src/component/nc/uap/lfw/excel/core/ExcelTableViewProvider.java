package nc.uap.lfw.excel.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.ObjectComboCellEditor;
import nc.uap.lfw.core.comp.ExcelColumn;
import nc.uap.lfw.core.comp.ExcelColumnGroup;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class ExcelTableViewProvider extends LabelProvider implements
		ITableLabelProvider, ITreeContentProvider {
	ExcelPropertiesView viewer;
	public ExcelTableViewProvider(ExcelPropertiesView viewer) {
		super();
		this.viewer = viewer;
	}
	


	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof ExcelColumnGroup){
		switch (columnIndex) {
			case 0:
				return PaletteImage.getCreateDsImgDescriptor().createImage();
			}
		}
		return null;
	}
	
	

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if(element instanceof ExcelColumn){
			return ((ExcelColumn)element).getId() == null? null:((ExcelColumn)element).getId();
			}
			else if(element instanceof ExcelColumnGroup){
				return ((ExcelColumnGroup)element).getId() == null? null:((ExcelColumnGroup)element).getId();
			}
			break;
		case 1:
			if(element instanceof ExcelColumn){
			return ((ExcelColumn)element).getField() == null? null:((ExcelColumn)element).getField();
			}
			break;
		case 2:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getText() == null? null:((ExcelColumn)element).getText();
				}
			else if(element instanceof ExcelColumnGroup){
				return ((ExcelColumnGroup)element).getText() == null? null:((ExcelColumnGroup)element).getText();
			}
			break;
		case 3:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getI18nName() == null? null:((ExcelColumn)element).getI18nName();
				}
			else if(element instanceof ExcelColumnGroup){
				return ((ExcelColumnGroup)element).getI18nName() == null? null:((ExcelColumnGroup)element).getI18nName();
			}
				break;
		case 4:
			if(element instanceof ExcelColumn){
			return ((ExcelColumn)element).getLangDir() == null? null:((ExcelColumn)element).getLangDir() ;
			}
			break;
		
		
		case 5:
			if(element instanceof ExcelColumn){
				return String.valueOf(((ExcelColumn)element).getWidth());
				}
			
				break;
		case 6:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isVisible() == true?"Y":"N";
				}
			else if(element instanceof ExcelColumnGroup){
				return ((ExcelColumnGroup)element).isVisible() == true? "Y":"N";
			}
				break;
		case 7:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isEditable() == true? "Y":"N";
				}
				break;
		case 8:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getColumBgColor() == null? null:((ExcelColumn)element).getColumBgColor();
				}
				break;
		case 9:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getTextAlign()== null? null:((ExcelColumn)element).getTextAlign();
				}
			break;
		case 10:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getTextColor() == null? null:((ExcelColumn)element).getTextColor();
				}
				break;
		case 11:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isFixedHeader() == true? "Y": "N";
				}
				break;
		case 12:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getEditorType()== null? null:((ExcelColumn)element).getEditorType();
				}
			break;
		case 13:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getRenderType()== null? null:((ExcelColumn)element).getRenderType();
			}
			break;
		case 14:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getRefNode()== null? null:((ExcelColumn)element).getRefNode();
				}
			break;
		case 15:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getRefComboData()== null? null:((ExcelColumn)element).getRefComboData();
				}
			break;
		case 16:
		if(element instanceof ExcelColumn){
			return ((ExcelColumn)element).isImageOnly() == true? "Y":"N";
			}
			break;
		case 17:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isSumCol() == true? "Y":"N";
				}
				break;
		case 18:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isAutoExpand() == true? "Y":"N";
				}
				break;
		case 19:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isSortable() == true? "Y":"N";
				}
				break;
		case 20:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).getColmngroup() == null? "":((ExcelColumn)element).getColmngroup();
				}
				break;
		case 21:
			if(element instanceof ExcelColumn){
				return ((ExcelColumn)element).isNullAble() == true? "Y":"N";
				}
				break;
			}
		
		return null;
	}


	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Field){
			return new Object[0];
		}
		else return new Object[0];
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if(element instanceof Field)
			return true;
		else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof List){
			List list = (List) inputElement;
			ArrayList<IExcelColumn> gridList = new ArrayList<IExcelColumn>();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					IExcelColumn column = (IExcelColumn) list.get(i);
					gridList.add(column);
					if(column instanceof ExcelColumnGroup){
						ExcelColumnGroup columngroup = (ExcelColumnGroup) column;
						List child = columngroup.getChildColumnList();
						if(child != null)
							gridList.addAll(child);
					}
				
				}
			}
			
			CellEditor editor = viewer.getCellEditorByColName("ËùÊô×é");
			String[] valuegroup = viewer.getColumnGroup();
			if(editor instanceof ObjectComboCellEditor){
				ObjectComboCellEditor objEditor = (ObjectComboCellEditor) editor;
				objEditor.setObjectItems(valuegroup);
			}
			return ((List) gridList).toArray();
		}
		else 
			return new Object[0];
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}

