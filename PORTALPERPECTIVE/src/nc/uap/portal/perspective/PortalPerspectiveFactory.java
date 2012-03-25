package nc.uap.portal.perspective;

import nc.uap.lfw.perspective.LFWViewSheet;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * ´´½¨PortalPerspective
 * 
 * @author dingrf
 *
 */
public class PortalPerspectiveFactory implements IPerspectiveFactory {
	protected IPageLayout	layout;
	public void createInitialLayout(IPageLayout layout) {
		this.layout = layout;
		String editorArea = layout.getEditorArea();
//		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.2f, editorArea);
//		left.addView("nc.uap.lfw.perspective.project.LFWExplorerTreeView");
		
		IFolderLayout folder = layout.createFolder("left", IPageLayout.LEFT, (float) 0.25, layout.getEditorArea());
//		folder.addView("org.eclipse.ui.navigator.ProjectExplorer");
		//folder.addView(JavaUI.ID_PACKAGES);
		//folder.addView(JavaUI.ID_TYPE_HIERARCHY);
		
		folder.addView("nc.uap.portal.perspective.PortalExplorerTreeView");
		folder.addPlaceholder(IPageLayout.ID_RES_NAV);
		
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.8f, editorArea);
		bottom.addView(LFWViewSheet.class.getCanonicalName());

		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, 0.8f, editorArea);
		right.addView(IPageLayout.ID_PROP_SHEET);
		
		//addActionSets();
		//addNewWizardShortcuts();
		//addViewShortcuts();
		
	}
}
