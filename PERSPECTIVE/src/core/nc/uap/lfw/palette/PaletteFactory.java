package nc.uap.lfw.palette;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.Connection;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.datasets.core.DsRelationConnection;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.excel.DatasetToExcelConnection;
import nc.uap.lfw.form.DatasetToFormConnection;
import nc.uap.lfw.grid.DatasetToGridConnection;
import nc.uap.lfw.grid.core.GridLevelElementObj;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;
import nc.uap.lfw.refnode.RefNodeElementObj;
import nc.uap.lfw.refnoderel.DatasetFieldElementObj;
import nc.uap.lfw.refnoderel.RefNodeRelConnection;
import nc.uap.lfw.tree.TreeTopLevelConnection;
import nc.uap.lfw.tree.core.TreeLevelElementObj;
import nc.uap.lfw.tree.treelevel.TreeLevelChildConnection;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.events.MouseEvent;

/**
 * ����Palette
 * 
 * @author zhangxya
 * 
 */
public class PaletteFactory {
	public static class TemplateCreationTool extends CreationTool {
		
		protected boolean handleButtonUp(int button) {
			if (button == 1) {
				setUnloadWhenFinished(false);
			} else {
				setUnloadWhenFinished(true);
			}
			boolean b = super.handleButtonUp(button);
			return b;
		}

	}

	public static class CustomConnectionCreationTool extends
			ConnectionCreationTool {

		
		protected boolean handleButtonUp(int button) {
			if (button == 1) {
				setUnloadWhenFinished(false);
			} else {
				setState(STATE_TERMINAL);
				setUnloadWhenFinished(true);
			}
			return super.handleButtonUp(button);
		}

	}

	public static class CustomSelectionTool extends SelectionTool {

		
		public void mouseUp(MouseEvent me, EditPartViewer viewer) {
			if (me.stateMask == 589824) {
				super.getTargetEditPart().performRequest(
						new CtrlMouseUpRequest());
			} else {
				super.mouseUp(me, viewer);
			}
		}

	}

	public static PaletteRoot createPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createDatasetBusinessComponentPalette());
		paletteRoot.add(createConnectionPalette());
		return paletteRoot;
	}
	
	
	private static ToolEntry createDsRelationConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����",
				"����һ���µĹع�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return DsRelationConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	
	/**
	 * ����Grid������
	 * @return
	 */
	public static PaletteRoot createGridPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		//paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createGridComponentPalette());
		paletteRoot.add(createGridRelationPalette());
		return paletteRoot;
	}
	
	
	/**
	 * ����excel������
	 * @return
	 */
	public static PaletteRoot createExcelPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		//paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createExcelComponentPalette());
		paletteRoot.add(createExcelRelationPalette());
		return paletteRoot;
	}
	
	
	/**
	 * ����refnode��ϵ������
	 * @return
	 */
	public static PaletteRoot createRefNodeRelPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(creatRefnodeRelComponentPalette());
		paletteRoot.add(createRefnodeRelRelationPalette());
		return paletteRoot;
	}
	
	/**
	 * ����refnode��ϵ���
	 * @return
	 */
	private static PaletteContainer creatRefnodeRelComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("���ֶ�",
				"����һ��Field", DatasetFieldElementObj.class,
				new SimpleFactory(DatasetFieldElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		
		entries.add(entityTE);
		
		ToolEntry detailTE = new CombinedTemplateCreationEntry("�Ӳ���",
				"����һ������", RefNodeElementObj.class,
				new SimpleFactory(RefNodeElementObj.class), PaletteImage.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		detailTE.setToolClass(TemplateCreationTool.class);
		entries.add(detailTE);
		drawer.addAll(entries);
		return drawer;

	}
	
	/**
	 * �������չ�ϵ
	 * @return
	 */
	private static PaletteContainer createRefnodeRelRelationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createRenodeRelConnection();
		entries.add(relationREF);
		drawer.addAll(entries);
		return drawer;
	}
	
	private static ToolEntry createRenodeRelConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("���չ���",
				"�������չ�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return RefNodeRelConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	/**
	 * ����toolbar������
	 * @return
	 */
	public static PaletteRoot createToolbarPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createToolbarElePalette());
		return paletteRoot;
	}
	
	/**
	 * ����Form������
	 * @return
	 */
	public static PaletteRoot createFormPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		//paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createFormComponentPalette());
		paletteRoot.add(createFormRelationPalette());
		return paletteRoot;
	}
	
	private static ToolEntry createFormConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����",
				"����һ���µĹع�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return DatasetToFormConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	/**
	 * form����������
	 * @return
	 */
	private static PaletteContainer createFormRelationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createFormConnection();
		entries.add(relationREF);
		drawer.addAll(entries);
		return drawer;
	}
	
	
	/**
	 * ����tree������
	 * @return
	 */
	public static PaletteRoot createTreePalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		//paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createTreeComponentPalette());
		paletteRoot.add(createTreeRelationPalette());
		return paletteRoot;
	}
	
	private static PaletteContainer createTreeComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("TreeLevel",
				"����һ��TreeLevel", TreeLevelElementObj.class,
				new SimpleFactory(TreeLevelElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		entries.add(entityTE);
		ToolEntry entityCon = createContextMenuEntry();
		entries.add(entityCon);
		drawer.addAll(entries);
		return drawer;

	}
	
	/**
	 * tree ����������
	 * @return
	 */
	private static PaletteContainer createTreeRelationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createTreeConnection();
		entries.add(relationREF);
		ToolEntry relationChild = createTreeLevelConnection();
		entries.add(relationChild);
		drawer.addAll(entries);
		return drawer;
	}
	
	private static ToolEntry createTreeConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����Level",
				"����һ���µĹع�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return TreeTopLevelConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	

	/**
	 * grid ����������
	 * @return
	 */
	private static PaletteContainer createGridRelationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createGridConnection();
		//ToolEntry contextRelation = createContextConnection();
		entries.add(relationREF);
		//entries.add(contextRelation);
		
		ToolEntry relationTreeREF = createGridLevelConnection();
		entries.add(relationTreeREF);
		//ToolEntry relationChild = createTreeLevelConnection();
		//entries.add(relationChild);
		
		drawer.addAll(entries);
		return drawer;
	}
	
	private static ToolEntry createGridLevelConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����Level",
				"����һ���µĹع�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return TreeTopLevelConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	/**
	 * excel������ϵ������
	 * @return
	 */
	private static PaletteContainer createExcelRelationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createExcelConnection();
		//ToolEntry contextRelation = createContextConnection();
		entries.add(relationREF);
		//entries.add(contextRelation);
		drawer.addAll(entries);
		return drawer;
	}
	
	/**
	 * �������ݼ�
	 * @return
	 */
	private static ToolEntry createGridConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("�������ݼ�",
				"�����������ݼ���ϵ", new CreationFactory() {
					public Object getNewObject() {
						return DatasetToGridConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	
	/**
	 * �������ݼ�
	 * @return
	 */
	private static ToolEntry createExcelConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("�������ݼ�",
				"�����������ݼ���ϵ", new CreationFactory() {
					public Object getNewObject() {
						return DatasetToExcelConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	
	/**
	 * �����Ҽ��˵�
	 * @return
	 */
//	private static ToolEntry createContextConnection() {
//		ToolEntry relationTE = new ConnectionCreationToolEntry("�����Ҽ��˵�",
//				"���������Ҽ��˵���ϵ", new CreationFactory() {
//					public Object getNewObject() {
//						return GridToContextMenuConnection.class;
//					}
//
//					public Object getObjectType() {
//						return new Integer(1);
//					}
//				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
//						.getRelationImgDescriptor());
//		relationTE.setToolClass(CustomConnectionCreationTool.class);
//		return relationTE;
//	}
	
	/**
	 * 
	 * gridҵ�����������
	 */
	private static PaletteContainer createGridComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("�����ݼ�",
				"����һ�����ݼ�", RefDatasetElementObj.class,
				new SimpleFactory(RefDatasetElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		
		entries.add(entityTE);
		
		entries.add(createGridLevelEntry());
		
		ToolEntry entityCon = createContextMenuEntry();
		
		entries.add(entityCon);
		//
		drawer.addAll(entries);
		return drawer;

	}
	
	/**
	 * 
	 * formҵ�����������
	 */
	private static PaletteContainer createFormComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("�����ݼ�",
				"����һ�����ݼ�", RefDatasetElementObj.class,
				new SimpleFactory(RefDatasetElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		
		entries.add(entityTE);
				
		ToolEntry entityCon = createContextMenuEntry();
		
		entries.add(entityCon);
		//
		drawer.addAll(entries);
		return drawer;

	}
	/**
	 * gridLevelҵ�����
	 * @return
	 */
	private static ToolEntry createGridLevelEntry() {
		ToolEntry entityTE = new CombinedTemplateCreationEntry("GridLevel",
				"����һ��GridLevel", GridLevelElementObj.class,
				new SimpleFactory(GridLevelElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		return entityTE;
	}
	
	/**
	 * 
	 * excelҵ�����������
	 */
	private static PaletteContainer createExcelComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("�����ݼ�",
				"����һ�����ݼ�", RefDatasetElementObj.class,
				new SimpleFactory(RefDatasetElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		
		entries.add(entityTE);
		
//		ToolEntry entityCon = createContextMenuEntry();
//		
//		entries.add(entityCon);
		//
		drawer.addAll(entries);
		return drawer;

	}
	
	/**
	 * �����Ҽ��˵�component
	 * @return
	 */
	private static ToolEntry createContextMenuEntry(){
		ToolEntry entityCon= new CombinedTemplateCreationEntry("�����Ҽ��˵�",
				"�����Ҽ��˵�", ContextMenuElementObj.class,
				new SimpleFactory(ContextMenuElementObj.class), PaletteImage
						.getCreateMenuImgDescriptor(), PaletteImage
						.getCreateMenuImgDescriptor());
		entityCon.setToolClass(TemplateCreationTool.class);
		return entityCon;
	}
	
	/**
	 * ����toolbar���������
	 * @return
	 */
	private static PaletteContainer createToolbarElePalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityCon= new CombinedTemplateCreationEntry("�����Ҽ��˵�",
				"�����Ҽ��˵�", ContextMenuElementObj.class,
				new SimpleFactory(ContextMenuElementObj.class), PaletteImage
						.getCreateMenuImgDescriptor(), PaletteImage
						.getCreateMenuImgDescriptor());
		entityCon.setToolClass(TemplateCreationTool.class);
		entries.add(entityCon);
		drawer.addAll(entries);
		return drawer;

	}
	
	
	/**
	 * ����ds���ϵ������
	 * @return
	 */
	public static PaletteRoot createDatasetsPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createDsFieldRalationPalette());
		return paletteRoot;
	}

	
	/**
	 * ����Pagemeta�༭��������
	 * @author guoweic
	 * @return
	 */
	public static PaletteRoot createPagemetaPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createPagemetaBusinessComponentPalette());
		//TODO
		paletteRoot.add(createPagemetaConnectionPalette());
		return paletteRoot;
	}
	
	/**
	 * ����Widget�༭��������
	 * @author guoweic
	 * @return
	 */
	public static PaletteRoot createWidgetPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createWidgetBusinessComponentPalette());
		return paletteRoot;
	}

	
//	/**
//	 * ����PageFlow�༭��������
//	 * @author guoweic
//	 * @return
//	 */
//	public static PaletteRoot createPageFlowPalette() {
//		PaletteRoot paletteRoot = new PaletteRoot();
//		paletteRoot.add(createBasePalette(paletteRoot));
//		paletteRoot.add(createPageFlowBusinessComponentPalette());
//		paletteRoot.add(createPageFlowConnectionPalette());
//		return paletteRoot;
//	}
	
	private static PaletteContainer createBasePalette(PaletteRoot root) {
		PaletteContainer container = new PaletteGroup("Base Palette");
		SelectionToolEntry entry = new SelectionToolEntry("ѡ��");
		entry.setToolClass(CustomSelectionTool.class);
		container.add(entry);
		return container;
	}

	private static PaletteContainer createDsFieldRalationPalette(){
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createDsRelationConnection();
		entries.add(relationREF);
		drawer.addAll(entries);
		return drawer;
	}
	private static PaletteContainer createConnectionPalette() {
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();

//		ToolEntry relationTE = new ConnectionCreationToolEntry("����",
//				"����һ���µĹ�����ϵ", new CreationFactory() {
//					public Object getNewObject() {
//						return Connection.class;
//					}
//
//					public Object getObjectType() {
//						return new Integer(1);
//					}
//				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
//						.getRelationImgDescriptor());
//		relationTE.setToolClass(CustomConnectionCreationTool.class);
		ToolEntry relationTE = createConnection();
		entries.add(relationTE);
		relationTE.setToolClass(CustomConnectionCreationTool.class);
//		ToolEntry relationREF = new ConnectionCreationToolEntry("�ӹ���",
//				"����һ���ӹ�����ϵ", new CreationFactory() {
//					public Object getNewObject() {
//						return ChildConnection.class;
//					}
//
//					public Object getObjectType() {
//						return new Integer(1);
//					}
//				}, PaletteImage.getDependImgDescriptor(), PaletteImage
//						.getDependImgDescriptor());
//
//		relationREF.setToolClass(CustomConnectionCreationTool.class);
		//ToolEntry relationREF = createSubConnection();
		//entries.add(relationREF);
		////.setToolClass(CustomConnectionCreationTool.class);
		drawer.addAll(entries);
		return drawer;

	}
	
	private static ToolEntry createTreeLevelConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("�ӹ���Level",
				"����һ���ӹ�����ϵ", new CreationFactory() {
					public Object getNewObject() {
						return TreeLevelChildConnection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getDependImgDescriptor(), PaletteImage
						.getDependImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}

	/**
	 * ����Dataset�༭����ҵ�����������
	 * @return
	 */
	private static PaletteContainer createDatasetBusinessComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("��������",
				"����һ���µ�����Dataset", RefDatasetElementObj.class,
				new SimpleFactory(RefDatasetElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		entries.add(entityTE);
		//
		drawer.addAll(entries);
		return drawer;

	}

	/**
	 * ����Pagemeta�༭����ҵ�����������
	 * @author guoweic
	 * @return
	 */
	private static PaletteContainer createPagemetaBusinessComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		
		ToolEntry entityTE = new CombinedTemplateCreationEntry(LFWTool.getViewText(null),
				"����һ���µ�" + LFWTool.getViewText(null), WidgetElementObj.class,
				new SimpleFactory(WidgetElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		
		entityTE.setToolClass(TemplateCreationTool.class);
		
		entries.add(entityTE);
		
		drawer.addAll(entries);
		return drawer;

	}
//
//	/**
//	 * ����PageFlow�༭����ҵ�����������
//	 * @author guoweic
//	 * @return
//	 */
//	private static PaletteContainer createPageFlowBusinessComponentPalette() {
//		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
//		List<ToolEntry> entries = new ArrayList<ToolEntry>();
//
//		ToolEntry startTE = new CombinedTemplateCreationEntry("��ʼ",
//				"����һ���µĿ�ʼ�ڵ�", PfStartElementObj.class,
//				new SimpleFactory(PfStartElementObj.class), PaletteImage
//						.getStartImgDescriptor(), PaletteImage
//						.getStartImgDescriptor());
//		startTE.setToolClass(TemplateCreationTool.class);
//		entries.add(startTE);
//
////		ToolEntry pageTE = new CombinedTemplateCreationEntry(WEBProjConstants.PAGEFLOW_PAGE_CN,
////				"����һ���µ�" + WEBProjConstants.PAGEFLOW_PAGE_CN, PfPageElementObj.class,
////				new SimpleFactory(PfPageElementObj.class), PaletteImage
////						.getEntityImgDescriptor(), PaletteImage
////						.getEntityImgDescriptor());
////		pageTE.setToolClass(TemplateCreationTool.class);
////		entries.add(pageTE);
////
////		ToolEntry decesionTE = new CombinedTemplateCreationEntry(WEBProjConstants.PAGEFLOW_DECISION_CN,
////				"����һ���µ�" + WEBProjConstants.PAGEFLOW_DECISION_CN, PfDecisionElementObj.class,
////				new SimpleFactory(PfDecisionElementObj.class), PaletteImage
////						.getEntityImgDescriptor(), PaletteImage
////						.getEntityImgDescriptor());
////		decesionTE.setToolClass(TemplateCreationTool.class);
////		entries.add(decesionTE);
//		
//		drawer.addAll(entries);
//		return drawer;
//
//	}
	
	/**
	 * ����Widget�༭����ҵ�����������
	 * @author guoweic
	 * @return
	 */
	private static PaletteContainer createWidgetBusinessComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
//		ToolEntry entityTE = new CombinedTemplateCreationEntry(LFWExplorerTreeView.WIDGET_CN,
//				"����һ���µ�" + LFWExplorerTreeView.WIDGET_CN, WidgetElementObj.class,
//				new SimpleFactory(WidgetElementObj.class), PaletteImage
//						.getEntityImgDescriptor(), PaletteImage
//						.getEntityImgDescriptor());
//		entityTE.setToolClass(TemplateCreationTool.class);
//		
//		entries.add(entityTE);
		
		drawer.addAll(entries);
		return drawer;

	}
	
	/**
	 * ����Pagemeta�༭���Ĺ�����ϵ������
	 * @author guoweic
	 * @return
	 */
	private static PaletteContainer createPagemetaConnectionPalette() {
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		entries.add(createConnection());
		drawer.addAll(entries);
		return drawer;
	}
	
	/**
	 * ����PageFlow�༭���Ĺ�����ϵ������
	 * @author guoweic
	 * @return
	 */
//	private static PaletteContainer createPageFlowConnectionPalette() {
//		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
//		List<ToolEntry> entries = new ArrayList<ToolEntry>();
//		entries.add(createConnection());
//		drawer.addAll(entries);
//		return drawer;
//	}
	

	
	/**
	 * ����Listenerͼ��
	 * @author guoweic
	 * @return
	private static ToolEntry createListenerComponentPalette() {
		ToolEntry entityTE = new CombinedTemplateCreationEntry("Listener",
				"����һ���µ�Listener", ListenerElementObj.class,
				new SimpleFactory(ListenerElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		return entityTE;
	}
	 */
	
	/**
	 * ����������ϵ
	 * @author guoweic
	 * @return
	 */
	private static ToolEntry createConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����",
				"����һ���µĹ�����ϵ", new CreationFactory() {
					public Object getNewObject() {
//						return WidgetConnector.class;
						return Connection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}
	
	/**
	 * �����ӹ�����ϵͼ��
	 * @author guoweic
	 * @return
	 */
//	private static ToolEntry createSubConnection() {
//		ToolEntry relationTE = new ConnectionCreationToolEntry("�ӹ���",
//				"����һ���ӹ�����ϵ", new CreationFactory() {
//					public Object getNewObject() {
//						return ChildConnection.class;
//					}
//
//					public Object getObjectType() {
//						return new Integer(1);
//					}
//				}, PaletteImage.getDependImgDescriptor(), PaletteImage
//						.getDependImgDescriptor());
//		relationTE.setToolClass(CustomConnectionCreationTool.class);
//		return relationTE;
//	}
	
}
