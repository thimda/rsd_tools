/**
 * 
 */
package nc.uap.lfw.model;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * @author chouhl
 *
 */
public class SelectDataModelDialog extends DialogWithTitle {

	private final static String RadioKey = "RadioKey";
	
	private Group btnGroup;
	
	private int radioValue = 0;
	
	public SelectDataModelDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}
	
	public SelectDataModelDialog(String title) {
		super(null, title);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btnGroup = new Group(parent, SWT.NONE);
		btnGroup.setLayout(new GridLayout(2, false));
		btnGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//元数据
		Button sourceDataBtn = new Button(btnGroup, SWT.RADIO);
		sourceDataBtn.setText("元数据");
		sourceDataBtn.setData(RadioKey, 1);
		sourceDataBtn.setSelection(true);
		//数据集
		Button dataSetBtn = new Button(btnGroup, SWT.RADIO);
		dataSetBtn.setText("数据集");
		dataSetBtn.setData(RadioKey, 2);
		
		return container;
	}
	
	protected void okPressed() {
		Control[] children = btnGroup.getChildren();
		if(children != null && children.length > 0){
			for(Control child : children){
				if(child instanceof Button){
					if(((Button) child).getSelection()){
						radioValue = (Integer)((Button) child).getData(RadioKey);
						break;
					}
				}
			}
		}
		super.okPressed();
	}

	public int getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(int radioValue) {
		this.radioValue = radioValue;
	}
	
}
