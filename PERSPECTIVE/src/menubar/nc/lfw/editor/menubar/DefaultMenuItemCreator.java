package nc.lfw.editor.menubar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单默认项构造器
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemCreator {

	/**
	 * 默认菜单项列表
	 */
	private static List<DefaultItem> menuItemList = null;
	
	private static Map<String, DefaultItem> menuItemMap = null;
	
	public static final String COMMAND_ID_PREFIX = "uifactory.commands.";
	
	/**
	 * 获取默认菜单项
	 * @return
	public static List<DefaultItem> getDefaultMenuItems() {
		
		if (null == menuItemList || menuItemList.size() == 0) {
			menuItemList = new ArrayList<DefaultItem>();
			
			// 新建按钮
			DefaultItem addItem = new DefaultItem();
			addItem.setText("新建");
			addItem.setId("_menu_item_add");
			addItem.setCmdId(COMMAND_ID_PREFIX + "addcommand");

			// 修改按钮
			DefaultItem editItem = new DefaultItem();
			editItem.setText("修改");
			editItem.setId("_menu_item_edit");
			editItem.setCmdId(COMMAND_ID_PREFIX + "editcommand");
			
			// 删除按钮
			DefaultItem deleteItem = new DefaultItem();
			deleteItem.setText("删除");
			deleteItem.setId("_menu_item_delete");
			deleteItem.setCmdId(COMMAND_ID_PREFIX + "deletecommand");
			

			// 保存按钮
			DefaultItem saveItem = new DefaultItem();
			saveItem.setText("保存");
			saveItem.setId("_menu_item_save");
			saveItem.setCmdId(COMMAND_ID_PREFIX + "savecommand");


			// 卡片显示按钮
			DefaultItem cardItem = new DefaultItem();
			cardItem.setText("卡片显示");
			cardItem.setId("_menu_item_card");
			cardItem.setCmdId(COMMAND_ID_PREFIX + "carddisplaycommand");


			// 列表显示按钮
			DefaultItem listItem = new DefaultItem();
			listItem.setText("列表显示");
			listItem.setId("_menu_item_list");
			listItem.setCmdId(COMMAND_ID_PREFIX + "listdisplaycommand");
			

			// 行操作子菜单
//			DefaultItem lineItem = new DefaultItem();
//			lineItem.setText("行操作");
//			lineItem.setId("_menu_item_line");
//			//lineItem.setCmdId(COMMAND_ID_PREFIX + "savecommand");

			// 行增加子菜单
			DefaultItem lineAddItem = new DefaultItem();
			lineAddItem.setText("增加行");
			lineAddItem.setId("_menu_item_line_add");
			lineAddItem.setCmdId(COMMAND_ID_PREFIX + "line.addlinecommand");
//			lineItem.addChild(lineAddItem);
			
			// 行插入操作子菜单
			DefaultItem lineInsertItem = new DefaultItem();
			lineInsertItem.setText("插入行");
			lineInsertItem.setId("_menu_item_line_insert");
			lineInsertItem.setCmdId(COMMAND_ID_PREFIX + "line.insertlinecommand");
//			lineItem.addChild(lineInsertItem);

			// 行删除操作子菜单
			DefaultItem lineDeleteItem = new DefaultItem();
			lineDeleteItem.setText("删除行");
			lineDeleteItem.setId("_menu_item_line_delete");
			lineDeleteItem.setCmdId(COMMAND_ID_PREFIX + "line.deletelinecommand");
//			lineItem.addChild(lineDeleteItem);
			
			// 行复制操作子菜单
			DefaultItem lineCoypItem = new DefaultItem();
			lineCoypItem.setText("复制行");
			lineCoypItem.setId("_menu_item_line_copy");
			lineCoypItem.setCmdId(COMMAND_ID_PREFIX + "line.copylinecommand");
//			lineItem.addChild(lineCoypItem);
			
			// 行粘贴操作子菜单
			DefaultItem linePasteItem = new DefaultItem();
			linePasteItem.setText("粘贴行");
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
	 * 获取默认菜单项
	 * @return
	 */
	public static List<DefaultItem> getDefaultMenuItems() {
		if (null == menuItemList || menuItemList.size() == 0) {
			menuItemList = new ArrayList<DefaultItem>();
			
			// 查询按钮
//			DefaultItem queryItem = new QueryMenuItem();
//			queryItem.setText("查询");
//			queryItem.setId("query");
//			queryItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			queryItem.setBusinessStatusArray("");
//			queryItem.setSuperClazz(PACKAGE_NAME + ".UifQueryMouseListener");
//			
//			// 新建按钮
//			DefaultItem addItem = new AddMenuItem();
//			addItem.setText("新建");
//			addItem.setId("add");
////			addItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
////			addItem.setBusinessStatusArray("");
//			addItem.setSuperClazz(PACKAGE_NAME + ".UifAddMouseListener");
//			
//			
//			// copy按钮
//			DefaultItem copyItem = new CopyMenuItem();
//			copyItem.setText("复制");
//			copyItem.setId("copy");
//			copyItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			copyItem.setBusinessStatusArray("");
//			copyItem.setSuperClazz(PACKAGE_NAME + ".UifCopyMouseListener");
//			
//
//			// 修改按钮
//			DefaultItem editItem = new EditMenuItem();
//			editItem.setText("修改");
//			editItem.setId("edit");
//			editItem.setOperatorStatusArray(IOperatorState.SINGLESEL);
//			editItem.setBusinessStatusArray("");
//			editItem.setSuperClazz(PACKAGE_NAME + ".UifEditMouseListener");
//			
//			// 删除按钮
//			DefaultItem deleteItem = new DelMenuItem();
//			deleteItem.setText("删除");
//			deleteItem.setId("delete");
//			deleteItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			deleteItem.setBusinessStatusArray("");
//			deleteItem.setSuperClazz(PACKAGE_NAME + ".UifDelMouseListener");
//
//			// 保存按钮
//			DefaultItem saveItem = new SaveMenuItem();
//			saveItem.setText("保存");
//			saveItem.setId("save");
//			saveItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			saveItem.setBusinessStatusArray("");
//			saveItem.setSuperClazz(PACKAGE_NAME + ".UifSaveMouseListener");
//			
//			DefaultItem cancelItem = new CancelMenuItem();
//			cancelItem.setText("取消");
//			cancelItem.setId("cancel");
//			cancelItem.setOperatorStatusArray(IOperatorState.EDIT + "," + IOperatorState.ADD);
//			cancelItem.setBusinessStatusArray("");
//			cancelItem.setSuperClazz(PACKAGE_NAME + ".UifCancelMouseListener");
//
//
//			// 打印按钮
//			DefaultItem printItem = new PrintMenuItem();
//			printItem.setText("打印");
//			printItem.setId("print");
//			printItem.setOperatorStatusArray("1");
//			printItem.setBusinessStatusArray("");
//			printItem.setSuperClazz(PACKAGE_NAME + ".UifPrintMouseListener");
//			// 提交按钮
//			DefaultItem submitItem = new SubmitMenuItem();
//			submitItem.setText("提交");
//			submitItem.setId("submit");
//			submitItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			submitItem.setBusinessStatusArray(String.valueOf(IBusinessState.FREE));
//			submitItem.setSuperClazz(PACKAGE_NAME + ".UifCommitMouseListener");
//
//			// 审核按钮
//			DefaultItem approveItem = new ApproveMenuItem();
//			approveItem.setText("审核");
//			approveItem.setId("approve");
//			approveItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			approveItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			approveItem.setSuperClazz(PACKAGE_NAME + ".UifApproveMouseListener");
//			
//			
//			//收回 
//			DefaultItem reCallItem = new ReCalMenuItem();
//			reCallItem.setText("收回");
//			reCallItem.setId("approve");
//			reCallItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			reCallItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			reCallItem.setSuperClazz(PACKAGE_NAME + ".UifReCallMouseListener");
//
//			// 反审按钮
//			DefaultItem unApproveItem = new UnApproveMenuItem();
//			unApproveItem.setText("弃审");
//			unApproveItem.setId("unapprove");
//			unApproveItem.setOperatorStatusArray(IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			unApproveItem.setBusinessStatusArray(IBusinessState.CHECKPASS + "," + IBusinessState.CHECKGOING);
//			unApproveItem.setSuperClazz(PACKAGE_NAME + ".UifUnApproveMouseListener");
//
//			// 审批状态按钮
//			DefaultItem approveStateItem = new ApproveStateMenuItem();
//			approveStateItem.setText("审批状态");
//			approveStateItem.setId("approvestate");
//			approveStateItem.setOperatorStatusArray(IOperatorState.SINGLESEL);
//			approveStateItem.setBusinessStatusArray(""); 
//			approveStateItem.setSuperClazz(PACKAGE_NAME + ".UifApproveStateMouseListener");
//			
//			//联查审批单据
//			DefaultItem queryPfinfoItem = new QueryPfInfoMenuItem();
//			queryPfinfoItem.setText("联查审批单据");
//			queryPfinfoItem.setId("querypfinfo");
//			queryPfinfoItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL + "," + IOperatorState.MULTISEL);
//			queryPfinfoItem.setBusinessStatusArray(IBusinessState.CHECKGOING + "," + IBusinessState.COMMIT);
//			queryPfinfoItem.setSuperClazz(PACKAGE_NAME + ".UifQueryPfinfoMouseListener");
//			
//			// 卡片显示按钮
//			DefaultItem cardItem = new CardMenuItem();
//			cardItem.setText("卡片显示");
//			cardItem.setId("card");
//			cardItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL);
//			cardItem.setBusinessStatusArray("");
//			cardItem.setSuperClazz(PACKAGE_NAME + ".UifCardMouseListener");
//			
//
//			// 列表显示按钮
//			DefaultItem listItem = new ListMenuItem();
//			listItem.setText("列表显示");
//			listItem.setId("list");
//			listItem.setOperatorStatusArray(IOperatorState.INIT + "," + IOperatorState.SINGLESEL);
//			listItem.setBusinessStatusArray("");
//			listItem.setSuperClazz(PACKAGE_NAME + ".UifListMouseListener");
//			
//
//			// 行增加子菜单
//			DefaultItem lineAddItem = new LineMenuItem();
//			lineAddItem.setText("增加行");
//			lineAddItem.setId("add_line");
//			lineAddItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineAddItem.setBusinessStatusArray("");
//			lineAddItem.setSuperClazz(PACKAGE_NAME + ".UifLineAddMouseListener");
//			
//			// 行插入操作子菜单
//			DefaultItem lineInsertItem = new LineMenuItem();
//			lineInsertItem.setText("插入行");
//			lineInsertItem.setId("insert_line");
//			lineInsertItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineInsertItem.setBusinessStatusArray("");
//			lineInsertItem.setSuperClazz(PACKAGE_NAME + ".UifLineInsertMouseListener");
//
//			// 行删除操作子菜单
//			DefaultItem lineDeleteItem = new LineMenuItem();
//			lineDeleteItem.setText("删除行");
//			lineDeleteItem.setId("delete_line");
//			lineDeleteItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineDeleteItem.setBusinessStatusArray("");
//			lineDeleteItem.setSuperClazz(PACKAGE_NAME + ".UifLineDelMouseListener");
//			
//			// 行复制操作子菜单
//			DefaultItem lineCoypItem = new LineMenuItem();
//			lineCoypItem.setText("复制行");
//			lineCoypItem.setId("copy_line");
//			
//			lineCoypItem.setOperatorStatusArray(IOperatorState.ADD + "," + IOperatorState.EDIT);
//			lineCoypItem.setBusinessStatusArray("");
//			lineCoypItem.setSuperClazz(PACKAGE_NAME + ".UifLineCopyMouseListener");
//			
//			// 行粘贴操作子菜单
//			DefaultItem linePasteItem = new LineMenuItem();
//			linePasteItem.setText("粘贴行");
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
	 * 根据ID获取默认菜单项
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
	 * 获取Command
	 * @param cmdId
	 * @return
	private static AbstractCommand getRefCmd(String cmdId) {
		String projectPath = LFWPersTool.getProjectPath();
		Map<String, AbstractCommand> commandMap = new DataProviderForDesign().getCommand(projectPath);
		return commandMap.get(cmdId);
	}
	 */
	
	/**
	 * 为默认Item生成Listener
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
