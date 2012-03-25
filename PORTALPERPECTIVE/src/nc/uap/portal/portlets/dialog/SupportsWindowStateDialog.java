package nc.uap.portal.portlets.dialog;

import org.eclipse.jface.dialogs.Dialog;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.WindowState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * window״̬ѡ��Ի���
 * 
 * @author dingrf
 *
 */
public class SupportsWindowStateDialog extends Dialog {
	
	/**����ĳ�ʼ״̬*/
	private String states = "";
	
	/**ѡ��״̬*/
	private List<String> selectedStates = new ArrayList<String>();
	
	/**����״̬����*/
	private List<WindowState> allStates = null;
	
	/**����״̬*/
	private String result = "";

	public SupportsWindowStateDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Point getInitialSize() {
		return new Point(250, 160); 
	}
	
	public String getResult(){
		return result;
	}
	
	protected void okPressed() {
		List<String> selects = getSelectedStates();
		for (int i = 0; i < selects.size(); i++) {
			if(i != selects.size() -1 )
				result += selects.get(i) + ",";
			else 
				result += selects.get(i);
		}
		super.okPressed();
	}
	
	
	public Control createDialogArea(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group group = new Group(container, SWT.NONE);
		group.setText("ѡ��Window״̬");
		
		group.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalIndent = 5;
		group.setLayoutData(gridData);
		getAllStates();
		if (allStates.size() > 0) {
			for (WindowState state : allStates) {
				Button check = new Button(group, SWT.CHECK);
				check.setLayoutData(createGridData(100, 1));
				check.setText(state.toString());
				//check.setData(mode.toString(), mode);
				if (checkStatesContained(state.toString())) {
					check.setSelection(true);
					selectedStates.add(state.toString());
				}
				check.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e) {
						Button check = (Button) e.getSource();
						String value = check.getText();
						if (check.getSelection()) {
							selectedStates.add(value);
						} else if (selectedStates.contains(value)) {
							selectedStates.remove(value);
						}
					}
				});
			}
		}
		return container;
	}
	
	/**
	 * У���Ƿ�����˸�ģʽ
	 * @return
	 */
	private boolean checkStatesContained(String StateValue) {
		String[] stateArray = states.split(",");
		for (String value : stateArray) {
			if (StateValue.equalsIgnoreCase(value.trim()))
				return true;
		}
		return false;
	}
	
	/**
	 * ��ȡ����״̬����
	 * @return
	 */
	private List<WindowState> getAllStates() {
		if (allStates == null) {
			allStates = new ArrayList<WindowState>();
			allStates.add(new WindowState("maximized"));
			allStates.add(new WindowState("minimized"));
			allStates.add(new WindowState("normal"));
		}
		return allStates;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public List<String> getSelectedStates() {
		return selectedStates;
	}

	public void setStates(String states) {
		this.states = states;
	}
}
