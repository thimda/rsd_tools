package nc.uap.lfw.mlr;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import nc.uap.lfw.plugin.Activator;
import nc.uap.lfw.tool.Helper;
import nc.uap.lfw.tool.ProjConstants;
import nc.uap.lfw.tool.UTFProperties;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;

/**
 *
 * 多语资源缓存
 * @author dingrf
 *
 */
public class MLRPropertyCache{

	/**
	 * 多语资源MAP
	 * key: 语言分类+模块名称
	 * value: MAP(key:文件名,value:文件中的多语资源)
	 */
	private Map<String,Map<String,UTFProperties>> propMap;
	
	private IProject project;
	
	/**资源文件根路径*/
	private String resHomePath;

	public MLRPropertyCache(IProject project, String resHomePath){
		propMap = new HashMap<String,Map<String,UTFProperties>>();
		this.project = project;
		this.resHomePath = resHomePath;
	}

	/**
	 * 根据资源id取多语资源
	 * 
	 * @param langCode
	 * @param langClass
	 * @param resID
	 * @return
	 */
	public MLRes findLocalMLRes(String bcpName,String langCode, String langClass, String resID){
		langClass = langClass.toLowerCase();
		Map<String,UTFProperties> props = (Map<String,UTFProperties>)propMap.get((new StringBuilder(langCode)).append(langClass).toString());
		if (props == null){
			props = getMLRprop(bcpName,langCode,langClass);
			propMap.put((new StringBuilder(langCode)).append(langClass).toString(), props);
		}
		MLRes res = null;
		for (Iterator<String> iter = props.keySet().iterator(); iter.hasNext();){
			String fileName = (String)iter.next();
			UTFProperties prop = (UTFProperties)props.get(fileName);
			if (prop.containsKey(resID)){
				res = new MLRes(fileName, resID, prop.getProperty(resID));
				break;
			}
		}
		return res;
	}
	
	/**
	 * 取某一模块的多语资源
	 * 
	 * @param langCode
	 * @param langClass
	 * @return
	 */
	public List<MLRes> findLocalMLResList(String bcpName,String langCode, String langClass){
		Map<String,UTFProperties> props = new HashMap<String,UTFProperties>();
		List<MLRes> mlrList = new ArrayList<MLRes>();
		props = getMLRprop(bcpName,langCode,langClass);
		for (Iterator<String> iter = props.keySet().iterator(); iter.hasNext();){
			String fileName = (String)iter.next();
			UTFProperties prop = (UTFProperties)props.get(fileName);
			for (Iterator<String> it = prop.keySet().iterator(); it.hasNext();){
				String resID = (String)it.next();
				MLRes res = new MLRes(fileName, resID, (String)prop.get(resID));
				mlrList.add(res);
			}
		}
		return mlrList; 
		
	}
	
	/**
	 * 加载某一模块的多语资源MAP
	 * 
	 * @param bpcName 业务组件
	 * @param langCode
	 * @param langClass
	 * @return
	 */
	private Map<String,UTFProperties> getMLRprop(String bcpName,String langCode, String langClass){
		Map<String,UTFProperties> props = new HashMap<String,UTFProperties>();
		IFolder folder = null;
		if (bcpName == null || bcpName.equals(""))
			folder = project.getFolder((new StringBuilder(resHomePath)).append("/"+bcpName+"/" + ProjConstants.LANG + "/").append(langCode).append("/").append(langClass).toString());
		else
			folder = project.getFolder((new StringBuilder(resHomePath)).append("/"+bcpName+"/" + ProjConstants.LANG + "/").append(langCode).append("/").append(langClass).toString());
			
		if (folder.exists())
			try{
				IResource childs[] = folder.members();
				int count = childs != null ? childs.length : 0;
				for (int i = 0; i < count; i++){
					IResource res = childs[i];
					if ((res instanceof IFile) && res.getFileExtension().equalsIgnoreCase("properties")){
						IFile localfile = (IFile)res;
						java.io.InputStream in = null;
						if (localfile.exists())
							try{
								in = localfile.getContents();
							}
							catch (CoreException e){
								Activator.getDefault().logError(e.getMessage(), e);
							}
						if (in != null){
							in = new BufferedInputStream(in);
							try{
								UTFProperties prop = new UTFProperties(localfile.getCharset());
								prop.load(in);
								props.put(localfile.getName(), prop);
							}
							catch (IOException e){
								Activator.getDefault().logError(e.getMessage(), e);
							}
						}
					}
				}
			}
			catch (CoreException e){
				Activator.getDefault().logError(e.getMessage(), e);
			}
		return props;
	}
	
	/**
	 * 保存common多语资源
	 * 
	 * @param langCode
	 * @param resList
	 */
	public void saveCommMLRes(String bcpName,String langCode,List<MLRes> resList){
		String langClass = "common";
		String fileName = "commonres.properties";
		IFolder folder = null;
		if (bcpName==null ||bcpName.equals(""))
			folder = project.getFolder((new StringBuilder(resHomePath)).append("/").append(ProjConstants.LANG).append("/").append(langCode).append("/").append(langClass).toString());
		else
			folder = project.getFolder((new StringBuilder(resHomePath)).append("/"+bcpName+"/").append(ProjConstants.LANG).append("/").append(langCode).append("/").append(langClass).toString());
			
		ByteArrayInputStream stream = null;
		try {
			if (!folder.exists())
				Helper.createFolder(folder);
//				folder.create(true, true, null);
			
			IFile file = project.getFile((new StringBuilder(resHomePath)).append("/").append(ProjConstants.LANG).append("/").append(langCode).append("/").append(langClass).toString() + "/" + fileName);
			StringWriter swriter = new StringWriter();
			PrintWriter writer = new PrintWriter(swriter);
			for(MLRes m : resList){
				writer.println(m.getResID() + "=" + m.getValue());
			}
			String initContent = swriter.getBuffer().toString();
			stream = new ByteArrayInputStream(initContent.getBytes());
			if (file.exists()){
				file.setContents(stream, false, false, null);
			}
			else{
				file.create(stream, false, null);
			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}finally{
			if (stream!=null)
				try{
					stream.close();
				}catch(Exception e){}
		}
	}
}
