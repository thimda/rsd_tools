package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class DTreeViewComp extends DComponent {
	private Tree tree = null;
	public DTreeViewComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	protected void initialize() {
		tree = new Tree(this, SWT.BORDER);
		tree.setData(this);
		TreeItem item1 = new TreeItem(tree, SWT.NONE);
		item1.setText("ˮ��");
		
		TreeItem item11 = new TreeItem(item1, SWT.NONE);
		item11.setText("ƻ��");
		
		TreeItem item12 = new TreeItem(item1, SWT.NONE);
		item12.setText("��");
		
		TreeItem item13 = new TreeItem(item1, SWT.NONE);
		item13.setText("����");

		TreeItem item2 = new TreeItem(tree, SWT.NONE);
		item2.setText("�߲�");
		TreeItem item21 = new TreeItem(item2, SWT.NONE);
		item21.setText("��ײ�");
		TreeItem item22 = new TreeItem(item2, SWT.NONE);
		item22.setText("���ܲ�");
		
		
		TreeItem item3 = new TreeItem(tree, SWT.NONE);
		item3.setText("����");
		
		TreeItem item31 = new TreeItem(item3, SWT.NONE);
		item31.setText("����");
		TreeItem item32 = new TreeItem(item3, SWT.NONE);
		item32.setText("Ѽ��");
		
		TreeItem item33 = new TreeItem(item3, SWT.NONE);
		item33.setText("�쵰");
		
		
	}

	
	protected Composite getComposite() {
		return tree;
	}

	@Override
	protected String getName() {
		return UIConstant.DTREE;
	}

}
