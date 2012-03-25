package nc.uap.lfw.funnode;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.common.tools.LfwGlobalEditorInfo;
import nc.lfw.editor.menubar.ListenerClassInfo;
import nc.uap.lfw.perspective.action.NcPatternGenerator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * ҳ������ѡ��Ի���
 * @author zhangxya
 *
 */
public class PageTypeSelectedDialog extends Dialog {

	//ҳ������
	private Integer pageType;
	
	//����
	private String rootPackage;
	
	//�Ƿ񽫲˵������ڱ���
	private boolean isBody;
	
	//�Ƿ�֧�ֶ�״̬��ʾ
	private boolean isSupportMultiVisible;
	
	//��·���ؼ�
	private Combo rootPathField = null;
	
	//���������ڵİ��ؼ�
	private Text listenerPackage;
	
	//ҳ������ѡ��ؼ�
	private Combo pagetypeComp = null;
	
	//�Ƿ񽫲˵������ڱ����ϰ�ť
	private Button bodyToolbar;
	
	//�Ƿ�֧�ֶ�״̬��ʾ
	private Button suportMultiVisibilty;
	

	public PageTypeSelectedDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Point getInitialSize() {
		return new Point(418,200); 
	}

	
	public boolean isBody(){
		return isBody;
	}
	
	public Integer getPageType(){
		return pageType;
	}
	
	public String getRootPackage(){
		return rootPackage;
	}
	
	public boolean isSupportMultiVisible(){
		return isSupportMultiVisible;
	}
	
	protected void okPressed() {
		pageType = (Integer)pagetypeComp.getData(pagetypeComp.getText());
		rootPackage = (String) rootPathField.getText();
		isBody = bodyToolbar.getSelection();
		isSupportMultiVisible = suportMultiVisibilty.getSelection();
		String lisPackage = listenerPackage.getText();
		if(lisPackage == null || lisPackage.trim().equals("")){
			MessageDialog.openError(null, "������ʾ", "�����������ڰ���!");
			return;
		}
		ListenerClassInfo info = new ListenerClassInfo();
		info.setPackageName(lisPackage);
		String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentFolderPath() + "." + NcPatternGenerator.GENERATElISTENER;
		LfwGlobalEditorInfo.addAttribute(key, info);
		super.okPressed();
	}

	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label pageTypeLabel = new Label(container, SWT.NONE);
		pageTypeLabel.setText("ҳ������ѡ��:");
		pagetypeComp = new Combo(container, SWT.READ_ONLY);
		pagetypeComp.setLayoutData(new GridData(212,15));
//		pagetypeComp.add("����������_��Ƭ����");
//		pagetypeComp.setData("����������_��Ƭ����", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST);
		
//		pagetypeComp.add("����������_�б�����");
//		pagetypeComp.setData("����������_�б�����", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST);
//		
//		pagetypeComp.add("������ͷ��_��Ƭ����");
//		pagetypeComp.setData("������ͷ��_��Ƭ����", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST);
//		
//		pagetypeComp.add("������ͷ��_�б�����");
//		pagetypeComp.setData("������ͷ��_�б�����", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST);
//		
//		pagetypeComp.add("��Ƭ������");
//		pagetypeComp.setData("��Ƭ������", ExtAttrConstants.PAGETYPE_CARD_MASCHI);
//		pagetypeComp.add("��Ƭ����ͷ��");
//		pagetypeComp.setData("��Ƭ����ͷ��", ExtAttrConstants.PAGETYPE_CARD_SINGAL);
//		pagetypeComp.add("�б�������");
//		pagetypeComp.setData("�б�������", ExtAttrConstants.PAGETYPE_LIST_MASCHI);
//		pagetypeComp.add("�б���ͷ��");
//		pagetypeComp.setData("�б���ͷ��", ExtAttrConstants.PAGETYPE_LIST_SINGAL);
//		pagetypeComp.select(0);
		
		Label rootPath = new Label(container, SWT.NONE);
		rootPath.setText("��·��:");
		rootPathField = new Combo(container, SWT.READ_ONLY);
		rootPathField.setLayoutData(new GridData(200,15));
		String[] rootPackages = LFWPersTool.getAllRootPackage();
		if(rootPackages !=  null)
			rootPathField.setItems(rootPackages);
		rootPathField.select(0);
		
		Label listenerpackage = new Label(container, SWT.NONE);
		listenerpackage.setText("���������:");
		listenerPackage = new Text(container, SWT.NONE);
		listenerPackage.setLayoutData(new GridData(222,15));
		String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentFolderPath() + "." + "GenerateListener";
		ListenerClassInfo listener = (ListenerClassInfo) LfwGlobalEditorInfo.getAttr(key);
		String packageName = null;
		if(listener != null){
			packageName  = listener.getPackageName();
		}
		if(packageName != null)
			listenerPackage.setText(packageName);
		
		bodyToolbar = new Button(container, SWT.CHECK);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		bodyToolbar.setText("�Ƿ񽫲˵������ڱ�����");
		if(pagetypeComp.getText().equals("����������_��Ƭ����") || pagetypeComp.getText().equals("����������_�б�����")
				|| pagetypeComp.getText().equals("��Ƭ������") || pagetypeComp.getText().equals("�б�������"))
			bodyToolbar.setVisible(true);
			
		else 
			bodyToolbar.setVisible(false);
		//֧�ֶ�״̬��ʾ
		suportMultiVisibilty = new Button(container, SWT.CHECK);
		suportMultiVisibilty.setText("�Ƿ�֧�ֶ�״̬��ʾ");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		suportMultiVisibilty.setLayoutData(gridData);
		return container;
	}

}

