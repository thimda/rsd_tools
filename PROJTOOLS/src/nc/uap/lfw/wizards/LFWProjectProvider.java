package nc.uap.lfw.wizards;

import nc.uap.lfw.internal.project.IProjectProvider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * @author dingrf
 * @since 1.6
 */
public class LFWProjectProvider implements IProjectProvider {
	
	/** 向导主页面*/
	private NewWebModuleProjectPage fMainPage;

	public NewWebModuleProjectPage getFMainPage() {
		return fMainPage;
	}

	public void setFMainPage(NewWebModuleProjectPage mainPage) {
		fMainPage = mainPage;
	}

	public String getProjectName() {
		return fMainPage.getProjectName();
	}

	public IProject getProject() {
		return fMainPage.getProjectHandle();
	}

	public IPath getLocationPath() {
		return fMainPage.getLocationPath();
	}

	public String getModuleName() {
		return fMainPage.getModuleName();
	}

	public String getModuleConfig() {
		return fMainPage.getModuleConfig();
	}

	public String getPrivateOut() {
		return "out/private";
	}

	public String getPrivateSrc() {
		return "src/private";
	}

	public String getPublicOut() {
		return "out/public";
	}

	public String getPublicSrc() {
		return "src/public";
	}

	public String getTestOut() {
		return "out/test";
	}

	public String getTestSrc() {
		return "src/test";
	}

	public String getClientOut() {
		return "out/client";
	}

	public String getClientSrc() {
		return "src/client";
	}

	public String getResources() {
		return "resources";
	}

	public String getResourcesOut() {
		return "out/resources";
	}

}
