package nc.uap.lfw.perspective.listener;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * �ļ�����
 * @author guoweic
 *
 */
public class FileUtil {

	/**
	 * ɾ���¼���ʱ�ļ�
	 * @param ctrl
	public static void deleteEventFile(EventEditorControl eventCtrl) {
		deleteFile(eventCtrl.getFilePath());
	}
	 */

	/**
	 * �����ļ�
	 * @param folder
	 * @param fileName
	 * @param text
	 */
	public static void saveToFile(String folder, String fileName, String text) {
		File f = new File(folder);
		if (!f.exists())
			f.mkdirs();
		f = new File(folder + "\\" + fileName);
		try {
			if (!f.exists())
				f.createNewFile();
			saveToFile(f, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ�
	 * @param f
	 * @param text
	 */
	public static void saveToFile(File f, String text) {
		BufferedWriter bf = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(f);
			osw = new OutputStreamWriter(fos, ResourcesPlugin.getEncoding());
			bf = new BufferedWriter(osw);
			bf.write(null == text ? "" : text);
			bf.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * �����ļ�
	 * @param fileName
	 * @param text
	 */
	public static void saveToFile(String fileName, String text)
    {
        File f = new File(fileName);
        if(!f.exists())
            try
            {
                f.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return;
            }
        saveToFile(f, text);
    }
	
	/**
	 * ɾ���ļ�
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		File f = new File(fileName);
		if (f.list() != null && f.list().length > 0) {
			for (String child : f.list()) {
				deleteFile(fileName + "\\" + child);
			}
		}
		if (f.exists())
        	f.delete();
	}
	
	// ������Ĭ�ϴ�С
	private static final int SIZE = 4096;
	
	private static final String CHARSET_NAME  = "UTF-8";
	
	/**
	 * ��ȡ�ļ�����
	 * @param file
	 * @return
	 */
	public static String readFile(IFile file) {
		if (file.exists()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileInputStream in = null;
			try {
				in = (FileInputStream) file.getContents();
				// �����ֽ����黺����
				byte[] buff = new byte[SIZE];
				int len = in.read(buff);
				while (len != -1) {
					out.write(buff, 0, len);
					len = in.read(buff);
				}
				return new String(out.toByteArray(), CHARSET_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	/**
	 * �����ļ�
	 * @param folder
	 * @param fileName
	 * @param text
	 */
	public static void renameFile(String folder, String oldName, String newName) {
		File oldFile = new File(folder + "\\" + oldName);
		if (!oldFile.exists())
			return;
		File newFile = new File(folder + "\\" + newName);
		try {
			oldFile.renameTo(newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
