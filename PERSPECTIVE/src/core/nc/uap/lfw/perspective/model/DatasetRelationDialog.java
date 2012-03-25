package nc.uap.lfw.perspective.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.datasets.core.DatasetsEditor;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.event.conf.DatasetListener;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.action.NewDsAction;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * ds Relation ����
 * @author zhangxya
 *
 */
public class DatasetRelationDialog  extends TitleAreaDialog {
	private class ModifyDsRelation extends Command{
		public ModifyDsRelation(){
			super("�޸�DSRelation");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}

	private DatasetElementObj source = null;
	private DatasetElementObj target = null;
	public DatasetRelationDialog(Shell parentShell, DatasetElementObj source, DatasetElementObj target) {
		super(parentShell);
		this.source = source;
		this.target = target;
		// TODO Auto-generated constructor stub
	}
	private Text id = null;

	
	@SuppressWarnings({ "static-access", "unchecked" })
	protected void okPressed() {
		Dataset mainDs = source.getDs();
		Map<String, JsListenerConf> listenerMap = mainDs.getListenerMap();
		boolean success = false;
		DatasetListener dsListener = null;
		for (Iterator<String> itwd = listenerMap.keySet().iterator(); itwd.hasNext();) {
			String listenerId = (String) itwd.next();
			JsListenerConf listener = listenerMap.get(listenerId);
			if(listener instanceof DatasetListener){
				dsListener = (DatasetListener) listener;
				break;
			}
		}
				
//			String server = listener.getServerClazz();
//			try {
//				Class serverClass = Class.forName(server);
//				Class defaultClass = Class.forName("nc.uap.lfw.core.event.deft.DefaultDatasetServerListener");
//				if(serverClass.isAssignableFrom(defaultClass) && listener instanceof DatasetListener){
//					dsListener = (DatasetListener) listener;
//					break;
//				}	
//			} catch (ClassNotFoundException e) {
//				MainPlugin.getDefault().logError(e);
//			}
//		}
		if(dsListener != null){
			Map<String, EventHandlerConf>  eventMap = dsListener.getEventHandlerMap();
			for (Iterator<String> it = eventMap.keySet().iterator(); it.hasNext();) {
				String eventId = it.next();
				if(eventId.equals("onAfterRowSelect")){
					EventHandlerConf event = eventMap.get(eventId);
					if(event.isOnserver()){
						success = true;
						break;
					}
				}
			}
		}
		//�½�Listener
		else{
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			dsListener = new DatasetListener();
			EventHandlerConf eventHandler = dsListener.getOnDataLoadEvent();
			DatasetRule dsRule = new DatasetRule();
			dsRule.setId(mainDs.getId());
			dsRule.setType(DatasetRule.TYPE_CURRENT_LINE);
			WidgetRule widgetRule = new WidgetRule();
			widgetRule.setId(widget.getId());
			widgetRule.addDsRule(dsRule);
			eventHandler.getSubmitRule().addWidgetRule(widgetRule);
			eventHandler.setOnserver(true);
			dsListener.addEventHandler(eventHandler);
			dsListener.setId(NewDsAction.DEFAULTLISTENER);
			dsListener.setServerClazz(NewDsAction.DEFAULTSERVER);
			//onAfterRowSelect
			EventHandlerConf roweventHandler = dsListener.getOnAfterRowSelectEvent();
			roweventHandler.getSubmitRule().addWidgetRule(widgetRule);
			roweventHandler.setOnserver(true);
			dsListener.addEventHandler(roweventHandler);
			mainDs.addListener(dsListener);
			widget.getViewModels().addDataset(mainDs);
			LFWPersTool.saveWidget(widget);
			success = true;
		}
		//�������û�м�Ĭ��datasetLoadListener
		if(!success){
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			EventHandlerConf eventHandler = dsListener.getOnAfterRowSelectEvent();
			eventHandler.setOnserver(true);
			DatasetRule dsRule = new DatasetRule();
			dsRule.setId(mainDs.getId());
			dsRule.setType(DatasetRule.TYPE_CURRENT_LINE);
			WidgetRule widgetRule = new WidgetRule();
			widgetRule.setId(widget.getId());
			widgetRule.addDsRule(dsRule);
			eventHandler.getSubmitRule().addWidgetRule(widgetRule);
			dsListener.addEventHandler(eventHandler);
			mainDs.addListener(dsListener);
			widget.getViewModels().addDataset(mainDs);
			LFWPersTool.saveWidget(widget);
		}
		String dsRelatinId = id.getText();
		String masterKey = masterKeyField.getText();
		String detailForeign = detailForeignKey.getText();
		//String aggVOName = aggVOClassName.getText();
		if(dsRelatinId == null || dsRelatinId.equals("")){
			MessageDialog.openConfirm(this.getShell(), "��ʾ", "������DatasetRelation id���ֶ�");
			id.setFocus();
		}
		if(masterKey == null || masterKey.equals("")){
			MessageDialog.openConfirm(this.getShell(), "��ʾ", "������DatasetRelation masterKeyField");
			masterKeyField.setFocus();
		}
		if(detailForeign == null || detailForeign.equals("")){
			MessageDialog.openConfirm(this.getShell(), "��ʾ", "������DatasetRelation detailForeignKey");
			detailForeignKey.setFocus();
		}
//		if(aggVOName == null || aggVOName.equals("")){
//			MessageDialog.openConfirm(this.getShell(), "��ʾ", "������DatasetRelation aggVOClassName");
//			aggVOClassName.setFocus();
//		}
		
		LfwWidget lfwwidget = LFWPersTool.getLfwWidget();
		if(lfwwidget == null)
			return;
		DatasetRelations dsrelations = lfwwidget.getViewModels().getDsrelations();
		if(dsrelations == null){
			dsrelations = new DatasetRelations();
			//lfwwidget.getViewModels().ad
		}
		DatasetRelation oldDsRelation = dsrelations.getDsRelation(source.getDs().getId(), target.getDs().getId());
		if(oldDsRelation != null){
			dsrelations.removeDsRelation(oldDsRelation);
		}
		DatasetRelation newDsRelation = new DatasetRelation();
		newDsRelation.setId(dsRelatinId);
		newDsRelation.setMasterDataset(source.getDs().getId());
		newDsRelation.setDetailDataset(target.getDs().getId());
		newDsRelation.setMasterKeyField(masterKey);
		newDsRelation.setDetailForeignKey(detailForeign);
		//newDsRelation.setAggVOClassName(aggVOName);
		dsrelations.addDsRelation(newDsRelation);
		lfwwidget.getViewModels().setDsrelations(dsrelations);
		LFWPersTool.setLfwWidget(lfwwidget);

		super.okPressed();
		if(DatasetsEditor.getActiveEditor() != null){
			ModifyDsRelation cmd = new ModifyDsRelation();
			DatasetsEditor.getActiveEditor().executComand(cmd);
		}
		
		
	}
	
	protected Point getInitialSize() {
		return new Point(300,300); 
	}
	private Combo masterKeyField = null;
	private Combo  detailForeignKey = null;
	//private Text aggVOClassName = null;
	protected Control createDialogArea(Composite parent) {
		setTitle("����DS Relation�Ի���"); 
		setMessage("����DS Relation�Ի���");
		//parent.setLayout(new FillLayout(SWT.VERTICAL|SWT.Activate));
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		Label labelId = new Label(grouId, SWT.NONE);
		labelId.setText("FielRelation ID:");
		id = new Text(grouId, SWT.NONE);
		id.setLayoutData(new GridData(150,15));
		id.setText(source.getDs().getId() + "_" + target.getDs().getId());
		
		Label labelMasterkey = new Label(grouId, SWT.NONE);
		labelMasterkey.setText("masterKeyField:");
		masterKeyField = new Combo(grouId, SWT.READ_ONLY);
		masterKeyField.setLayoutData(new GridData(120,15));
		Field[] fields = source.getDs().getFieldSet().getFields();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			String fieldId = fields[i].getId();
			if(fieldId != null)
				list.add(fieldId);
		}
		masterKeyField.setItems((String[])list.toArray(new String[list.size()]));
		masterKeyField.select(0);
		
		Field[] fieldtarget = target.getDs().getFieldSet().getFields();
		List<String> listtarget = new ArrayList<String>();
		for (int i = 0; i < fieldtarget.length; i++) {
			String fieldId = fieldtarget[i].getId();
			if(fieldId != null)
				listtarget.add(fieldId);
		}
	
		Label labelDetailForeign = new Label(grouId, SWT.NONE);
		labelDetailForeign.setText("detailForeignKey:");
		detailForeignKey=  new Combo(grouId, SWT.READ_ONLY);
		detailForeignKey.setLayoutData(new GridData(120,15));
		detailForeignKey.setItems((String[])listtarget.toArray(new String[listtarget.size()]));
		detailForeignKey.select(0);
//		
//		Label labelaggVO = new Label(grouId, SWT.NONE);
//		labelaggVO.setText("aggVOClassName:");
//		aggVOClassName=  new Text(grouId, SWT.NONE);
//		aggVOClassName.setLayoutData(new GridData(150,15));
		//����Ĭ��ֵ
		setDefaultData();
		return container;
		
	}
	private void setDefaultData(){
		LfwWidget lfwwidget = LFWPersTool.getLfwWidget();
		if(lfwwidget != null){
			DatasetRelations dsrelations = lfwwidget.getViewModels().getDsrelations();
			if(dsrelations != null){
				DatasetRelation oldDsRelation = dsrelations.getDsRelation(source.getDs().getId(), target.getDs().getId());
				if(oldDsRelation != null){
					String dsRelatinId = oldDsRelation.getId();
					String masterKey = oldDsRelation.getMasterKeyField();
					String detailForeign = oldDsRelation.getDetailForeignKey();
//					String aggVOName = oldDsRelation.getAggVOClassName();
//					if(aggVOName == null)
//						aggVOName = "";
					id.setText(dsRelatinId);
					masterKeyField.setText(masterKey);
					detailForeignKey.setText(detailForeign);
					//aggVOClassName.setText(aggVOName);
				}
			}
		}
	}
}