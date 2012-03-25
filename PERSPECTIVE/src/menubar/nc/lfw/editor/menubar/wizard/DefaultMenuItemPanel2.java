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
 * 选择默认菜单项 第二页内容
 * 
 * @author guoweic
 * 
 */
public class DefaultMenuItemPanel2 extends Canvas {

	/**
	 * 选中的菜单项
	 */
	private List<DefaultItem> selectedMenuItems = new ArrayList<DefaultItem>();

	private Text packageText;

	private Combo sourceFolderCombo;

	private Text classPrefixText;
	
	private Composite itemsArea;
	
	/**
	 * 选中的菜单项参数对象集合
	 */
	private Map<DefaultItem, MenuItemParamPanel> menuItemParamPanels = new HashMap<DefaultItem, MenuItemParamPanel>();

	public DefaultMenuItemPanel2(Composite parent, int style) {
		super(parent, style);
		initUI();
	}

	private void initUI() {
		// 总体布局
		GridLayout mainLayout = new GridLayout(4, false);
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(this, SWT.NONE).setText("包名：");
		packageText = new Text(this, SWT.BORDER);
		packageText.setLayoutData(createGridData(292, 3));

		new Label(this, SWT.NONE).setText("源文件夹：");
		sourceFolderCombo = new Combo(this, SWT.READ_ONLY);
		sourceFolderCombo.setLayoutData(createGridData(150, 1));
		initSourceFolderComboData();

		new Label(this, SWT.NONE).setText(" 类前缀：");
		classPrefixText = new Text(this, SWT.BORDER);
		classPrefixText.setLayoutData(createGridData(50, 1));
		// 设置默认类前缀
		String pagemetaId = LFWPersTool.getCurrentSimpleFolderPath();
		String defaultClassPrefix = String.valueOf(pagemetaId.charAt(0)).toUpperCase() + pagemetaId.substring(1);
		classPrefixText.setText(defaultClassPrefix);

		// 菜单项配置区域
		Group itemsGroup = new Group(this, SWT.NONE);
		itemsGroup.setLayout(new GridLayout());
		GridData itemsGroupGridData = new GridData(GridData.FILL_BOTH);
		itemsGroupGridData.horizontalSpan = 4;
		itemsGroup.setLayoutData(itemsGroupGridData);
		itemsGroup.setText("参数设置");
		
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
	 * 加载资源文件夹下拉框数据
	 */
	private void initSourceFolderComboData() {
		sourceFolderCombo.removeAll();
		sourceFolderCombo.add("请选择");
		sourceFolderCombo.setData("请选择", "");
		sourceFolderCombo.select(0);
		
		List<String> sourceFolderList = getAllRootPackage();
		for (String sourceFolder : sourceFolderList) {
			sourceFolderCombo.add(sourceFolder);
			sourceFolderCombo.setData(sourceFolder, sourceFolder);
		}
		
	}

	/**
	 * 获取所有资源文件夹目录
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
		// 当前项目名
		String projectName = LFWPersTool.getCurrentProject().getName();
		if (pfrs != null) {
			for (int i = 0; i < pfrs.length; i++) {
				if (pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
					continue;
				else if (pfrs[i] instanceof PackageFragmentRoot) {
					PackageFragmentRoot root = (PackageFragmentRoot) pfrs[i];
					// 该目录所在项目名
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
	 * 加载页面内容
	 * @param selectedMenuItems
	 * @param key
	 */
	public void initPanel(List<DefaultItem> selectedMenuItems, String key) {
		// 如果加载过选中项，则直接返回，不重新加载
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
	 * 校验是否已加载过选中的默认项内容
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
