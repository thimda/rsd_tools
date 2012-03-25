package nc.lfw.editor.menubar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �˵�Ĭ�������
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemCreator {

	/**
	 * Ĭ�ϲ˵����б�
	 */
	private static List<DefaultItem> menuItemList = null;
	
	private static Map<String, DefaultItem> menuItemMap = null;
	
	public static final String COMMAND_ID_PREFIX = "uifactory.commands.";
	
	/**
	 * ��ȡĬ�ϲ˵���
	 * @return
	public static List<DefaultItem> getDefaultMenuItems() {
		
		if (null == menuItemList || menuItemList.size() == 0) {
			menuItemList = new ArrayList<DefaultItem>();
			
			// �½���ť
			DefaultItem addItem = new DefaultItem();
			addItem.setText("�½�");
			addItem.setId("_menu_item_add");
			addItem.setCmdId(COMMAND_ID_PREFIX + "addcommand");

			// �޸İ�ť
			DefaultItem editItem = new DefaultItem();
			editItem.setText("�޸�");
			editItem.setId("_menu_item_edit");
			editItem.setCmdId(COMMAND_ID_PREFIX + "editcommand");
			
			// ɾ����ť
			DefaultItem deleteItem = new DefaultItem();
			deleteItem.setText("ɾ��");
			deleteItem.setId("_menu_item_delete");
			deleteItem.setCmdId(COMMAND_ID_PREFIX + "deletecommand");
			

			// ���水ť
			DefaultItem saveItem = new DefaultItem();
			saveItem.setText("����");
			saveItem.setId("_menu_item_save");
			saveItem.setCmdId(COMMAND_ID_PREFIX + "savecommand");


			// ��Ƭ��ʾ��ť
			DefaultItem cardItem = new DefaultItem();
			cardItem.setText("��Ƭ��ʾ");
			cardItem.setId("_menu_item_card");
			cardItem.setCmdId(COMMAND_ID_PREFIX + "carddisplaycommand");


			// �б���ʾ��ť
			DefaultItem listItem = new DefaultItem();
			listItem.setText("�б���ʾ");
			listItem.setId("_menu_item_list");
			listItem.setCmdId(COMMAND_ID_PREFIX + "listdisplaycommand");
			

			// �в����Ӳ˵�
//			DefaultItem lineItem = new DefaultItem();
//			lineItem.setText("�в���");
//			lineItem.setId("_menu_item_line");
//			//lineItem.setCmdId(COMMAND_ID_PREFIX + "savecommand");

			// �������Ӳ˵�
			DefaultItem lineAddItem = new DefaultItem();
			lineAddItem.setText("������");
			lineAddItem.setId("_menu_item_line_add");
			lineAddItem.setCmdId(COMMAND_ID_PREFIX + "line.addlinecommand");
//			lineItem.addChild(lineAddItem);
			
			// �в�������Ӳ˵�
			DefaultItem lineInsertItem = new DefaultItem();
			lineInsertItem.setText("������");
			lineInsertItem.setId("_menu_item_line_insert");
			lineInsertItem.setCmdId(COMMAND_ID_PREFIX + "line.insertlinecommand");
//			lineItem.addChild(lineInsertItem);

			// ��ɾ�������Ӳ˵�
			DefaultItem lineDeleteItem = new DefaultItem();
			lineDeleteItem.setText("ɾ����");
			lineDeleteItem.setId("_menu_item_line_delete");
			lineDeleteItem.setCmdId(COMMAND_ID_PREFIX + "line.deletelinecommand");
//			lineItem.addChild(lineDeleteItem);
			
			// �и��Ʋ����Ӳ˵�
			DefaultItem lineCoypItem = new DefaultItem();
			lineCoypItem.setText("������");
			lineCoypItem.setId("_menu_item_line_copy");
			lineCoypItem.setCmdId(COMMAND_ID_PREFIX + "line.copylinecommand");
//			lineItem.addChild(lineCoypItem);
			
			// ��ճ�������Ӳ˵�
			DefaultItem linePasteItem = new DefaultItem();
			linePasteItem.setText("ճ����");
			linePasteItem.setId("_menu_item_line_paste");
			linePasteItem.setCmdId(COMMAND_ID_PREFIX + "line.pastelinecommand");
//			lineItem.addChild(linePasteItem);
			
			//TODO
			

			menuItemList.add(addItem);
			menuItemList.add(editItem);
			menuItemList.add(deleteItem);
			menuItemList.add(saveItem);
			menuItemList.add(cardItem);
			menuItemList.add(listItem);
//			menuItemList.add(lineItem);
			menuItemList.add(lineAddItem);
			menuItemList.add(lineInsertItem);
			menuItemList.add(lineDeleteItem);
			menuItemList.add(lineCoypItem);
			menuItemList.add(linePasteItem);
			
		}
		return menuItemList;
	}
	 */
	
//	private static final String PACKAGE_NAME = "nc.uap.lfw.core.uif.listener";
	
	/**
	 * ��ȡĬ�ϲ˵���
	 * @return
	 */
	public static List<DefaultItem> getDefaultMenuItems() {
		if (null == menuItemList || menuItemList.size() == 0) {
			menuItemList = new ArrayList<DefaultItem>();
			
			// ��ѯ��ť
//			DefaultItem queryItem = new QueryMenuItem();
//			queryItem.setText("��ѯ");
//			queryItem.setId("query");
//			queryItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			queryItem.setBusinessStatusArray("");
//			queryItem.setSuperClazz(PACKAGE_NAME + ".UifQueryMouseListener");
//			
//			// �½���ť
//			DefaultItem addItem = new AddMenuItem();
//			addItem.setText("�½�");
//			addItem.setId("add");
////			addItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
////			addItem.setBusinessStatusArray("");
//			addItem.setSuperClazz(PACKAGE_NAME + ".UifAddMouseListener");
//			
//			
//			// copy��ť
//			DefaultItem copyItem = new CopyMenuItem();
//			copyItem.setText("����");
//			copyItem.setId("copy");
//			copyItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			copyItem.setBusinessStatusArray("");
//			copyItem.setSuperClazz(PACKAGE_NAME + ".UifCopyMouseListener");
//			
//
//			// �޸İ�ť
//			DefaultItem editItem = new EditMenuItem();
//			editItem.setText("�޸�");
//			editItem.setId("edit");
//			editItem.setOperatorStatusArray(IOperatorState.SINGLESEL);
//			editItem.setBusinessStatusArray("");
//			editItem.setSuperClazz(PACKAGE_NAME + ".UifEditMouseListener");
//			
//			// ɾ����ť
//			DefaultItem deleteItem = new DelMenuItem();
//			deleteItem.setText("ɾ��");
//			deleteItem.setId("delete");
//			deleteItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			deleteItem.setBusinessStatusArray("");
//			deleteItem.setSuperClazz(PACKAGE_NAME + ".UifDelMouseListener");
//
//			// ���水ť
//			DefaultItem saveItem = new SaveMenuItem();
//			saveItem.setText("����");
//			saveItem.setId("save");
//			saveItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			saveItem.setBusinessStatusArray("");
//			saveItem.setSuperClazz(PACKAGE_NAME + ".UifSaveMouseListener");
//			
//			DefaultItem cancelItem = new CancelMenuItem();
//			cancelItem.setText("ȡ��");
//			cancelItem.setId("cancel");
//			cancelItem.setOperatorStatusArray(IOperatorState.EDIT + "," + IOperatorState.ADD);
//			cancelItem.setBusinessStatusArray("");
//			cancelItem.setSuperClazz(PACKAGE_NAME + ".UifCancelMouseListener");
//
//
//			// ��ӡ��ť
//			DefaultItem printItem = new PrintMenuItem();
//			printItem.setText("��ӡ");
//			printItem.setId("print");
//			printItem.setOperatorStatusArray("1");
//			printItem.setBusinessStatusArray("");
//			printItem.setSuperClazz(PACKAGE_NAME + ".UifPrintMouseListener");
//			// �ύ��ť
//			DefaultItem submitItem = new SubmitMenuItem();
//			submitItem.setText("�ύ");
//			submitItem.setId("submit");
//			submitItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			submitItem.setBusinessStatusArray(String.valueOf(IBusinessState.FREE));
//			submitItem.setSuperClazz(PACKAGE_NAME + ".UifCommitMouseListener");
//
//			// ��˰�ť
//			DefaultItem approveItem = new ApproveMenuItem();
//			approveItem.setText("���");
//			approveItem.setId("approve");
//			approveItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			approveItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			approveItem.setSuperClazz(PACKAGE_NAME + ".UifApproveMouseListener");
//			
//			
//			//�ջ� 
//			DefaultItem reCallItem = new ReCalMenuItem();
//			reCallItem.setText("�ջ�");
//			reCallItem.setId("approve");
//			reCallItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			reCallItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			reCallItem.setSuperClazz(PACKAGE_NAME + ".UifReCallMouseListener");
//
//			// ����ť
//			DefaultItem unApproveItem = new UnApproveMenuItem();
//			unApproveItem.setText("����");
//			unApproveItem.setId("unapprove");
//			unApproveItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			unApproveItem.setBusinessStatusArray(IBusinessState.CHECKPASS + "," + IBusinessState.CHECKGOING);
//			unApproveItem.setSuperClazz(PACKAGE_NAME + ".UifUnApproveMouseListener");
//
//			// ����״̬��ť
//			DefaultItem approveStateItem = new ApproveStateMenuItem();
//			approveStateItem.setText("����״̬");
//			approveStateItem.setId("approvestate");
//			approveStateItem.setOperatorStatusArray(IOperatorState.SINGLESEL);
//			approveStateItem.setBusinessStatusArray(""); 
//			approveStateItem.setSuperClazz(PACKAGE_NAME + ".UifApproveStateMouseListener");
//			
//			//������������
//			DefaultItem queryPfinfoItem = new QueryPfInfoMenuItem();
//			queryPfinfoItem.setText("������������");
//			queryPfinfoItem.setId("querypfinfo");
//			queryPfinfoItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			queryPfinfoItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			queryPfinfoItem.setSuperClazz(PACKAGE_NAME + ".UifQueryPfinfoMouseListener");
//			
//			// ��Ƭ��ʾ��ť
//			DefaultItem cardItem = new CardMenuItem();
//			cardItem.setText("��Ƭ��ʾ");
//			cardItem.setId("card");
//			cardItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL);
//			cardItem.setBusinessStatusArray("");
//			cardItem.setSuperClazz(PACKAGE_NAME + ".UifCardMouseListener");
//			
//
//			// �б���ʾ��ť
//			DefaultItem listItem = new ListMenuItem();
//			listItem.setText("�б���ʾ");
//			listItem.setId("list");
//			listItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL);
//			listItem.setBusinessStatusArray("");
//			listItem.setSuperClazz(PACKAGE_NAME + ".UifListMouseListener");
//			
//
//			// �������Ӳ˵�
//			DefaultItem lineAddItem = new LineMenuItem();
//			lineAddItem.setText("������");
//			lineAddItem.setId("add_line");
//			lineAddItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineAddItem.setBusinessStatusArray("");
//			lineAddItem.setSuperClazz(PACKAGE_NAME + ".UifLineAddMouseListener");
//			
//			// �в�������Ӳ˵�
//			DefaultItem lineInsertItem = new LineMenuItem();
//			lineInsertItem.setText("������");
//			lineInsertItem.setId("insert_line");
//			lineInsertItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineInsertItem.setBusinessStatusArray("");
//			lineInsertItem.setSuperClazz(PACKAGE_NAME + ".UifLineInsertMouseListener");
//
//			// ��ɾ�������Ӳ˵�
//			DefaultItem lineDeleteItem = new LineMenuItem();
//			lineDeleteItem.setText("ɾ����");
//			lineDeleteItem.setId("delete_line");
//			lineDeleteItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineDeleteItem.setBusinessStatusArray("");
//			lineDeleteItem.setSuperClazz(PACKAGE_NAME + ".UifLineDelMouseListener");
//			
//			// �и��Ʋ����Ӳ˵�
//			DefaultItem lineCoypItem = new LineMenuItem();
//			lineCoypItem.setText("������");
//			lineCoypItem.setId("copy_line");
//			
//			lineCoypItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineCoypItem.setBusinessStatusArray("");
//			lineCoypItem.setSuperClazz(PACKAGE_NAME + ".UifLineCopyMouseListener");
//			
//			// ��ճ�������Ӳ˵�
//			DefaultItem linePasteItem = new LineMenuItem();
//			linePasteItem.setText("ճ����");
//			linePasteItem.setId("paste_line");
//			
//			linePasteItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			linePasteItem.setBusinessStatusArray("");
//			linePasteItem.setSuperClazz(PACKAGE_NAME + ".UifLinePasteMouseListener");
//			
//
//			menuItemList.add(addItem);
//			menuItemList.add(editItem);
//			menuItemList.add(deleteItem);
//			menuItemList.add(saveItem);
//			menuItemList.add(copyItem);
//			menuItemList.add(cancelItem);
//			menuItemList.add(printItem);
//			menuItemList.add(queryItem);
//			menuItemList.add(cardItem);
//			menuItemList.add(listItem);
//			menuItemList.add(lineAddItem);
//			menuItemList.add(lineInsertItem);
//			menuItemList.add(lineDeleteItem);
//			menuItemList.add(lineCoypItem);
//			menuItemList.add(linePasteItem);
//			menuItemList.add(submitItem);
//			menuItemList.add(approveItem);
//			menuItemList.add(unApproveItem);
//			menuItemList.add(reCallItem);
//			menuItemList.add(queryPfinfoItem);
//			menuItemList.add(approveStateItem);
//			menuItemList.add(lineItem);
			
		}
		return menuItemList;
	}

	/**
	 * ����ID��ȡĬ�ϲ˵���
	 * @param id
	 * @return
	 */
	public static DefaultItem getDefaultMenuItem(String id) {
		if (null == menuItemMap) {
			getDefaultMenuItems();
			menuItemMap = new HashMap<String, DefaultItem>();
			for (DefaultItem item : menuItemList) {
				menuItemMap.put(item.getId(), item);
			}
		}
		if (menuItemMap.containsKey(id))
			return menuItemMap.get(id);
		return null;
	}
	
	/**
	 * ��ȡCommand
	 * @param cmdId
	 * @return
	private static AbstractCommand getRefCmd(String cmdId) {
		String projectPath = LFWPersTool.getProjectPath();
		Map<String, AbstractCommand> commandMap = new DataProviderForDesign().getCommand(projectPath);
		return commandMap.get(cmdId);
	}
	 */
	
	/**
	 * ΪĬ��Item����Listener
	 * @param item
	 * @param defaultItem
	public static void generateListener(MenuItem item, DefaultItem defaultItem) {
		
		AbstractCommand cmd = getRefCmd(defaultItem.getCmdId());
		
		MouseListener listener = new MouseListener();
		listener.setId(item.getId() + "_listener");
		
		RefEventHandlerConf refEvent= new RefEventHandlerConf();
		
		refEvent.setName("onclick");
		refEvent.setRefId(cmd.getId());
		List<Parameter> paramList = cmd.getParamList();
		for (Parameter parameter : paramList) {
			CmdParameter cmdParameter = new CmdParameter();
			cmdParameter.setName(parameter.getName());
			cmdParameter.setValue(parameter.getValue());
			cmdParameter.setDesc(parameter.getDesc());
			refEvent.addCmdParam(cmdParameter);
		}
		
		listener.addEventHandler(refEvent);
		
		item.addListener(listener);
		
	}
	 */
	
	
}
