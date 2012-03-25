package nc.lfw.editor.pagemeta;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.FileUtilities;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class UIGuildImportHtmlDialog extends Dialog {

	public UIGuildImportHtmlDialog(Shell parentShell) {
		super(parentShell);
	}

	protected void okPressed() {
		File file = location.getLocation().toFile();
		//file是目录，将目录下的节点都拷贝到此节点的Html目录下
		if(file.isDirectory()){
			String[] filePaths = FileUtilities.listFile(file);
			String folderPath = LFWPersTool.getCurrentFolderPath();
			String filePath = folderPath + "/html";
			try {
				FileUtilities.copyFileToDir(filePath, filePaths);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		super.okPressed();
	}
	
	

	protected Point getInitialSize() {
		return new Point(450, 150);
	}

	private LocationGroup location = null;
	protected Control createDialogArea(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		location = new LocationGroup(composite);
		return composite;
	}
	
	private final class LocationGroup extends Observable implements Observer, IStringButtonAdapter, IDialogFieldListener
	{
		protected final StringButtonDialogField fLocation;
		private static final String DIALOGSTORE_LAST_EXTERNAL_LOC = JavaUI.ID_PLUGIN + ".last.external.project"; //$NON-NLS-1$

		public LocationGroup(Composite composite)
		{
			fLocation = new StringButtonDialogField(this);
			fLocation.setLabelText("&Directory:");
			fLocation.setButtonLabel("B&rowse..."); //$NON-NLS-1$
			fLocation.doFillIntoGrid(composite, 3);
			fLocation.setDialogFieldListener(this);
			LayoutUtil.setHorizontalGrabbing(fLocation.getTextControl(null));
		}

		protected void fireEvent()
		{
			setChanged();
			notifyObservers();
		}

		protected String getDefaultPath(String name)
		{
			final IPath path = Platform.getLocation().append(name);
			return path.toOSString();
		}

		public void update(Observable o, Object arg)
		{
		
			fireEvent();
		}

		public IPath getLocation()
		{
			return new Path(fLocation.getText().trim());
		}

		

		public void changeControlPressed(DialogField field)
		{
			final DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setMessage("Choose a directory for the project contents:");
			String directoryName = fLocation.getText().trim();
			if (directoryName.length() == 0)
			{
				String prevLocation = JavaPlugin.getDefault().getDialogSettings().get(DIALOGSTORE_LAST_EXTERNAL_LOC);
				if (prevLocation != null)
				{
					directoryName = prevLocation;
				}
			}
			if (directoryName.length() > 0)
			{
				final File path = new File(directoryName);
				if (path.exists())
					dialog.setFilterPath(new Path(directoryName).toOSString());
			}
			final String selectedDirectory = dialog.open();
			if (selectedDirectory != null)
			{
				fLocation.setText(selectedDirectory);
				JavaPlugin.getDefault().getDialogSettings().put(DIALOGSTORE_LAST_EXTERNAL_LOC, selectedDirectory);
			}
		}

		public void dialogFieldChanged(DialogField field)
		{
			//validatePage();
			fireEvent();
		}
	}
	
}
