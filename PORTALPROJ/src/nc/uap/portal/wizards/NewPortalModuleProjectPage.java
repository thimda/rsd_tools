package nc.uap.portal.wizards;

import nc.uap.lfw.wizards.NewWebModuleProjectPage;

import org.eclipse.jface.wizard.IWizardPage;

/**
 * protal��ҳ��
 * 
 * @author dingrf
 *
 */
public class NewPortalModuleProjectPage extends NewWebModuleProjectPage{

	public NewPortalModuleProjectPage(String pageName) {
		super(pageName);
	}
	public IWizardPage getNextPage() {
		return null;
	}
}
