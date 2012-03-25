package nc.lfw.jsp.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class BorderLayoutDialog extends Dialog {

	private ChoosePanel topPanel;
	private ChoosePanel leftPanel;
	private ChoosePanel centerPanel;
	private ChoosePanel bottomPanel;
	private ChoosePanel rightPanel;
	private boolean[] sels;
	public BorderLayoutDialog(Shell shell) {
		super(shell);
	}

	
	protected Control createDialogArea(Composite parent) {
		 Composite container = (Composite) super.createDialogArea(parent);
	     container.setLayout(new GridLayout(3, false));
	     topPanel = new ChoosePanel(container, SWT.BORDER, "ÉÏ", false);
	     GridData gd1 = new GridData();
	     gd1.horizontalSpan = 3;
	     gd1.horizontalAlignment = SWT.CENTER;
	     topPanel.setLayoutData(gd1);
	     
	     GridData gd2 = new GridData();
	     gd2.horizontalAlignment = SWT.LEFT;
	     //gd2.grabExcessHorizontalSpace = true;
	     leftPanel = new ChoosePanel(container, SWT.BORDER, "×ó          ", false);
	     leftPanel.setLayoutData(gd2);
	     centerPanel = new ChoosePanel(container, SWT.BORDER, "ÖÐ          ", true);
	     gd2.horizontalAlignment = SWT.LEFT;
	     centerPanel.setLayoutData(gd2);
	     
	     rightPanel = new ChoosePanel(container, SWT.BORDER, "ÓÒ          ", false);
	     gd2.horizontalAlignment = SWT.RIGHT;
	     rightPanel.setLayoutData(gd2);
	     
	     bottomPanel = new ChoosePanel(container, SWT.BORDER, "ÏÂ          ", false);
	     bottomPanel.setLayoutData(gd1);
	     return container;
	}

	
	protected void okPressed() {sels = new boolean[5];
		if(topPanel.getChecked())
			sels[0] = true;
		if(leftPanel.getChecked())
			sels[1] = true;
		if(centerPanel.getChecked())
			sels[2] = true;
		if(rightPanel.getChecked())
			sels[3] = true;		
		if(bottomPanel.getChecked())
			sels[4] = true;
		super.okPressed();
		
	}

	public boolean[] getSels(){
		
		return sels;
	}
	
}

class ChoosePanel extends Canvas{
	private String text;
	private boolean readonly = false;
	private Button bt;
	public ChoosePanel(Composite parent, int style, String text, boolean readonly) {
		super(parent, style);
		this.text = text;
		this.readonly = readonly;
		initUI();
	}

	private void initUI() {
		this.setLayout(new FillLayout());
		int style = SWT.CHECK;
		if(readonly){
			style = SWT.RADIO;
		}
		bt = new Button(this, style);
		bt.setText(text);
		if(readonly)
			bt.setSelection(true);
	}
	
	public boolean getChecked() {
		return bt.getSelection();
	}
	
}
