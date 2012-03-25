package nc.uap.lfw.perspective.project;

import java.io.File;
import java.io.FileFilter;

/**
 * �ļ�������
 * @author zhangxya
 *
 */
public class LFWFileFilter implements FileFilter {

	public LFWFileFilter() {
		// TODO Auto-generated constructor stub
	}

	public boolean accept(File f) {
		return f.isDirectory() || f.getPath().toLowerCase().endsWith("widget.wd");
	}

}
