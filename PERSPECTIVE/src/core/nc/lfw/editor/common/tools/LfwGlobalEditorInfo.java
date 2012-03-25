package nc.lfw.editor.common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

/**
 * �༭����ʱ��Ϣ
 * 
 * @author guoweic
 * 
 */
public class LfwGlobalEditorInfo implements Serializable {

	private static final long serialVersionUID = 8339476671146844449L;

	// ��Ϣ����
	private static Map<String, Serializable> attrMap;

	// ������ļ�����
	private static File file;
	
	// ������ļ��л���·��
	private static final String BASE_FOLDER = "\\temp";
	
	// ������ļ���
	private static final String FILE_NAME = "GlobalEditorInfo.temp";
	
	/**
	 * �½��ļ�
	 */
	private static void createFile() {
		try {
			String folder = LFWPersTool.getProjectPath() + BASE_FOLDER;
			String fileName = FILE_NAME;
			
			File f = new File(folder);
			if (!f.exists())
				f.mkdirs();
			file = new File(folder + "\\" + fileName);
			if (!f.exists())
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������Ϣ
	 * @param key
	 * @param obj
	 */
	public static void addAttribute(String key, Serializable obj) {
		if (null == attrMap)
			attrMap = getAttrMap();
		attrMap.put(key, obj);
		writeToFile();
	}

	/**
	 * ������д���ļ�
	 */
	private static void writeToFile() {
		try {
			if (null == file || !file.exists())
				createFile();
			LFWAMCPersTool.checkOutFile(file.getAbsolutePath());
			FileOutputStream fo = new FileOutputStream(file);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(attrMap);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ����
	 * @param key
	 * @return
	 */
	public static Serializable getAttr(String key) {
		Map<String, Serializable> map = getAttrMap();
		if (map.containsKey(key))
			return map.get(key);
		return null;
	}
	
	/**
	 * ���ļ��л�ȡMap����
	 * 
	 * @return
	 */
	private static Map<String, Serializable> getAttrMap() {
		if (null == file || !file.exists()) {
			createFile();
		}
		try {
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);
			attrMap = (Map<String, Serializable>) oi.readObject();
		} catch (Exception e) {
			// ���������ļ�
			if (file.exists())
				file.delete();
			attrMap = new HashMap<String, Serializable>();
			writeToFile();
		}
		return attrMap;
	}

}
