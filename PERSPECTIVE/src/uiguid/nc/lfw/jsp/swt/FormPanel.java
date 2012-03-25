package nc.lfw.jsp.swt;

import java.util.Iterator;

import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class FormPanel extends Composite {
	private FormComp webElement;
	public FormPanel(Composite parent, int style, FormComp webElement) {
		super(parent, style);
		this.webElement = webElement;
		initUI();
	}

	private void initUI() {
		this.setLayout(new GridLayout(8, false));
		Iterator<FormElement> it = webElement.getElementList().iterator();
		while(it.hasNext()){
			FormElement formEle = it.next();
			Label label = new Label(this, SWT.NONE);
			String text = formEle.getText();
			if(text == null)
				text = formEle.getI18nName();
			if(text == null || text.equals(""))
				text = "Î´ÃüÃû";
			label.setText(text);
//			if(formEle.getLabelColor() != null)
//				label.setForeground(Display.getCurrent().getSystemColor(id))
			
			if(formEle.isRequired()){
				label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
			}
			
			Text t = new Text(this, SWT.BORDER);
			GridData gd = new GridData();
			gd.widthHint = 120;
			t.setLayoutData(gd);
			t.setEnabled(formEle.isEnabled());
		}
	}

}
