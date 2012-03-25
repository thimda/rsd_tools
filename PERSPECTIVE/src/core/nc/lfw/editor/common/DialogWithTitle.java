package nc.lfw.editor.common;

import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * �����п��Դ������Dialog
 * @author zhangxya
 *
 */
public class DialogWithTitle extends IconAndMessageDialog {

	private String title;
	
	private Image dialogTitleImage = PaletteImage.getDialogImgDescriptor().createImage();
	
	public DialogWithTitle(Shell parentShell, String title) {
		super(parentShell);
		this.title = title;
	}
	
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (title != null) {
			shell.setText(title);
		}
        if(dialogTitleImage != null){
        	shell.setImage(dialogTitleImage);
        }
     }

	@Override
	protected Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}