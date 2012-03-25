package nc.lfw.editor.menubar.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.common.tools.LfwGlobalEditorInfo;
import nc.lfw.editor.menubar.DefaultItem;
import nc.lfw.editor.menubar.ListenerClassInfo;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * ѡ��Ĭ�ϲ˵��� �ڶ�ҳ����
 * 
 * @author guoweic
 * 
 */
public class DefaultMenuItemPanel2 extends Canvas {

	/**
	 * ѡ�еĲ˵���
	 */
	private List<DefaultItem> selectedMenuItems = new ArrayList<DefaultItem>();

	private Text packageText;

	private Combo sourceFolderCombo;

	private Text classPrefixText;
	
	private Composite itemsArea;
	
	/**
	 * ѡ�еĲ˵���������󼯺�
	 */
	private Map<DefaultItem, MenuItemParamPanel> menuItemParamPanels = new HashMap<DefaultItem, MenuItemParamPanel>();

	public DefaultMenuItemPanel2(Composite parent, int style) {
		super(parent, style);
		initUI();
	}

	private void initUI() {
		// ���岼��
		GridLayout mainLayout = new GridLayout(4, false);
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(this, SWT.NONE).setText("������");
		packageText = new Text(this, SWT.BORDER);
		packageText.setLayoutData(createGridData(292, 3));

		new Label(this, SWT.NONE).setText("Դ�ļ��У�");
		sourceFolderCombo = new Combo(this, SWT.READ_ONLY);
		sourceFolderCombo.setLayoutData(createGridData(150, 1));
		initSourceFolderComboData();

		new Label(this, SWT.NONE).setText(" ��ǰ׺��");
		classPrefixText = new Text(this, SWT.BORDER);
		classPrefixText.setLayoutData(createGridData(50, 1));
		// ����Ĭ����ǰ׺
		String pagemetaId = LFWPersTool.getCurrentSimpleFolderPath();
		String defaultClassPrefix = String.valueOf(pagemetaId.charAt(0)).toUpperCase() + pagemetaId.substring(1);
		classPrefixText.setText(defaultClassPrefix);

		// �˵�����������
		Group itemsGroup = new Group(this, SWT.NONE);
		itemsGroup.setLayout(new GridLayout());
		GridData itemsGroupGridData = new GridData(GridData.FILL_BOTH);
		itemsGroupGridData.horizontalSpan = 4;
		itemsGroup.setLayoutData(itemsGroupGridData);
		itemsGroup.setText("��������");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(itemsGroup, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayout(new GridLayout());
		scrolledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		itemsArea = new Composite(scrolledComposite, SWT.NONE);
		itemsArea.setLayout(new GridLayout());
//		itemsArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		itemsArea.setSize(645, 900);
		
		scrolledComposite.setContent(itemsArea);

	}

	/**
	 * ������Դ�ļ�������������
	 */
	private void initSourceFolderComboData() {
		sourceFolderCombo.removeAll();
		sourceFolderCombo.add("��ѡ��");
		sourceFolderCombo.setData("��ѡ��", "");
		sourceFolderCombo.select(0);
		
		List<String> sourceFolderList = getAllRootPackage();
		for (String sourceFolder : sourceFolderList) {
			sourceFolderCombo.add(sourceFolder);
			sourceFolderCombo.setData(sourceFolder, sourceFolder);
		}
		
	}

	/**
	 * ��ȡ������Դ�ļ���Ŀ¼
	 * @return
	 */
	private List<String> getAllRootPackage() {
		IProject proj = LFWPersTool.getCurrentProject();
		JavaProject javaProj = (JavaProject) JavaCore.create(proj);
		IPackageFragmentRoot[] pfrs = null;
		try {
			pfrs = javaProj.getAllPackageFragmentRoots();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		List<String> rootPackage = new ArrayList<String>();
		// ��ǰ��Ŀ��
		String projectName = LFWPersTool.getCurrentProject().getName();
		if (pfrs != null) {
			for (int i = 0; i < pfrs.length; i++) {
				if (pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
					continue;
				else if (pfrs[i] instanceof PackageFragmentRoot) {
					PackageFragmentRoot root = (PackageFragmentRoot) pfrs[i];
					// ��Ŀ¼������Ŀ��
					String name = root.getPath().toString().substring(1).substring(0, root.getPath().toString().substring(1).indexOf("/"));
					if (projectName.equals(name)) {
						String absPath = root.getPath().removeFirstSegments(1).makeRelative().toString();
						if (!absPath.startsWith("NC_HOME"))
							rootPackage.add(absPath);
					}
				}

			}
		}
		return rootPackage;

	}

	/**
	 * ����ҳ������
	 * @param selectedMenuItems
	 * @param key
	 */
	public void initPanel(List<DefaultItem> selectedMenuItems, String key) {
		// ������ع�ѡ�����ֱ�ӷ��أ������¼���
		boolean isLoaded = checkMenuItemsLoaded(selectedMenuItems);
		if (isLoaded)
			return;
		
		this.selectedMenuItems = selectedMenuItems;

		menuItemParamPanels.clear();
		for (Control child : itemsArea.getChildren()) {
			child.dispose();
		}

		for (DefaultItem item : selectedMenuItems) {
			Composite container = new Composite(itemsArea, SWT.NONE);
			container.setLayout(new GridLayout(2, false));
			container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Label nameLabel = new Label(container, SWT.NONE);
			nameLabel.setText(item.getText());
			nameLabel.setLayoutData(createGridData(50, 1));
			nameLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_MAGENTA));
			
			MenuItemParamPanel paramPanel = new MenuItemParamPanel(container, SWT.NONE, item.getSuperClazz());
			menuItemParamPanels.put(item, paramPanel);
		}

		itemsArea.layout();
		
		ListenerClassInfo info = (ListenerClassInfo) LfwGlobalEditorInfo.getAttr(key);
		if (null != info) {
			String sourceFolder = info.getSourceFolder();
			String packageName = info.getPackageName();
			String classPrefix = info.getClassPrefix();
			int index = sourceFolderCombo.indexOf(sourceFolder);
			sourceFolderCombo.select(index);
			packageText.setText(packageName);
			classPrefixText.setText(classPrefix);
		}
	}
	
	/**
	 * У���Ƿ��Ѽ��ع�ѡ�е�Ĭ��������
	 * @param selectedMenuItems
	 * @return
	 */
	private boolean checkMenuItemsLoaded(List<DefaultItem> selectedMenuItems) {
		if (menuItemParamPanels.keySet().size() != selectedMenuItems.size())
			return false;
		else {
			for (DefaultItem defaultItem : selectedMenuItems) {
				if (!menuItemParamPanels.containsKey(defaultItem))
					return false;
			}
		}
		return true;
	}

	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public Text getPackageText() {
		return packageText;
	}

	public Combo getSourceFolderCombo() {
		return sourceFolderCombo;
	}

	public Text getClassPrefixText() {
		return classPrefixText;
	}

	public Map<DefaultItem, MenuItemParamPanel> getMenuItemParamPanels() {
		return menuItemParamPanels;
	}

}
