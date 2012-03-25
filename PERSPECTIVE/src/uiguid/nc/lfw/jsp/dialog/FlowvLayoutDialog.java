package nc.lfw.jsp.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FlowvLayoutDialog extends Dialog {
	private Text pageText;
	private int pageCount;
	public FlowvLayoutDialog(Shell parentShell) {
		super(parentShell);
	}

	
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
	    container.setLayout(new RowLayout());
	    new Label(container, SWT.NONE).setText("«Î ‰»Î¡– ˝£∫");
	    pageText = new Text(container, SWT.BORDER);
	    pageText.setSize(80, 22);
	    pageText.addModifyListener(new ModifyListener(){

			
			public void modifyText(ModifyEvent e) {
				Text t = (Text) e.getSource();
				String text = t.getText();
				if(text == null || text.equals(""))
					return;
				boolean right = false;
				try{
					Integer.valueOf(text);
					right = true;
				}
				catch (Exception e1) {
				}
				if(!right)
					t.setText("");
			}
	    	
	    });
	    pageText.setText("2");
	    return container;
	}

	public int getPageCount() {
		return pageCount;
	}

	
	protected void okPressed() {
		String pc = pageText.getText();
		pageCount = Integer.parseInt(pc);
		super.okPressed();
	}

}
