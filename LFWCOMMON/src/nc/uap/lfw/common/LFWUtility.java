package nc.uap.lfw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

public class LFWUtility {
	public static String getContextFromResource(IProject project) {
		String module = null;
		File f = project.getLocation().toFile();
		File moduleFile = new File(f, ".module_prj");
		if (moduleFile.exists()) {
			try {
				InputStream is = new FileInputStream(moduleFile);
				Properties prop = new Properties();
				prop.load(is);
				module = prop.getProperty("module.webContext");
			} catch (Exception e) {
				e.printStackTrace();
				//DefaultUIPlugin.getDefault().logError(e.getMessage(), e);
			}
			return module;
		}
		return "lfw";
	}
}