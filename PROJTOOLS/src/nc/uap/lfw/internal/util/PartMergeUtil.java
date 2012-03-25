package nc.uap.lfw.internal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.common.XmlUtility;
import nc.uap.lfw.core.WEBProjPlugin;

import org.eclipse.core.resources.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * part�ϲ���web.xml
 * 
 * @author dingrf
 *
 */

public class PartMergeUtil {

	private static String NC_HOME = ProjCoreUtility.getNcHomeFolderPath().toString();

	private static String HOTWEBS = NC_HOME + "/hotwebs";
	
	/* web.xml ��Ԫ������  */
	private static String LISTENER = "listener";
	
	private static String FILTER = "filter";
	
	private static String FILTER_MAPPING = "filter-mapping";
	
	private static String SERVLET = "servlet";
	
	private static String SERVLET_MAPPING = "servlet-mapping";
	
	/**
	 * ��part�ļ����ݺϲ���web.xml
	 * 
	 * @param proj	��ǰpart��Ŀ
	 * @param partFile	*.part�ļ�
	 */
	public static void mergeWebXml(IProject proj,File partFile){
		String webName = partFile.getName().replace(".part", "");
		String webPath = HOTWEBS + "/" + webName + "/WEB-INF";
		File webFile = new File(webPath + "/web.xml");
		/* ���������web.xml ������ϲ� */
		if (!webFile.exists()){
			return;
		}
		
//		String moduleName = LfwCommonTool.getModuleProperty(proj,"module.name");
		String moduleName = getModuleName(proj ,partFile);
		if (moduleName == null){
			WEBProjPlugin.getDefault().logInfo("δ�ҵ�" + partFile.getPath() + "��ģ����������.module_prj�ļ��Ѽ�manifest.xml�ļ�");
			return;
		}
		
		/* web.xml Buffer*/
 		StringBuffer sb = new StringBuffer();
 		String tempString = null;
 		
 		/* �ҵ�part���֣�����ɾ�� */
 		Boolean needDelete = false;
 		
 		/* �Ƿ��ҵ�part����*/
// 		Boolean findPart = false;
 		
 		Boolean havInsertListener = false;
 		
 		Boolean havInsertFilter = false;
 		
 		Boolean havInsertFilterMapping = false;
 		
 		Boolean havInsertServlet = false;
 		
 		Boolean havInsertServletMapping = false;
 		
 		String paramName = "";

 		BufferedReader reader = null;
		try {
			Document doc = null;
			try{
				doc = XmlUtility.getDocument(partFile);
			}catch(Exception e){
//	 			WEBProjPlugin.getDefault().logError(e.getMessage(),e);
	 			WEBProjPlugin.getDefault().logInfo("part�ļ�Ϊ�ջ��������");
			}	
 			reader = new BufferedReader(new FileReader(webFile));
			while ((tempString = reader.readLine()) != null){
				if (tempString.indexOf("<param-name>modules</param-name>") != -1){
					paramName = "modules";
				}
				if (paramName.equals("modules")  && tempString.indexOf("<param-value>") != -1){
					String modules = tempString.replace("<param-value>", "").replace("</param-value>", "");
					if (("," + modules.trim()+",").indexOf("," + moduleName + ",") == -1){
						modules =  modules + ","+moduleName;
						sb.append("<param-value>"+ modules.trim()+ "</param-value>\n");
	 					continue;
					}
				}
				if (tempString.indexOf("</context-param>")!=-1){
					paramName = "";
				}
				/*ɾ��ԭ��part*/
				if (tempString.indexOf("<!--" + LISTENER + "_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("<!--" + LISTENER + "_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				else if (tempString.indexOf("<!--" + FILTER + "_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("<!--" + FILTER + "_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				else if (tempString.indexOf("<!--" + FILTER_MAPPING + "_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("<!--" + FILTER_MAPPING + "_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				else if (tempString.indexOf("<!--" + SERVLET + "_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("<!--" + SERVLET + "_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				else if (tempString.indexOf("<!--" + SERVLET_MAPPING + "_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("<!--" + SERVLET_MAPPING + "_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				
				/* �����µ�part��Ϣ */
				if (tempString.indexOf("<filter>")!=-1 || tempString.indexOf("<!--" + FILTER + "_begin:")!=-1 ){
					if (!havInsertListener){
						addPartContent(sb,doc,moduleName,LISTENER);
						havInsertListener=true;
					}
				}
				else if (tempString.indexOf("<filter-mapping>")!=-1 || tempString.indexOf("<!--" + FILTER_MAPPING + "_begin:")!=-1 ){
					if (!havInsertListener){
						addPartContent(sb,doc,moduleName,LISTENER);
						havInsertListener=true;
					}
					if (!havInsertFilter){
						addPartContent(sb,doc,moduleName,FILTER);
						havInsertFilter=true;
					}
				}
				else if (tempString.indexOf("<servlet>")!=-1 || tempString.indexOf("<!--" + SERVLET + "_begin:")!=-1 ){
					if (!havInsertListener){
						addPartContent(sb,doc,moduleName,LISTENER);
						havInsertListener=true;
					}
					if (!havInsertFilter){
						addPartContent(sb,doc,moduleName,FILTER);
						havInsertFilter=true;
					}
					if (!havInsertFilterMapping){
						addPartContent(sb,doc,moduleName,FILTER_MAPPING);
						havInsertFilterMapping=true;
					}
				}
				else if (tempString.indexOf("<servlet-mapping>")!=-1 || tempString.indexOf("<!--" + SERVLET_MAPPING + "_begin:")!=-1 ){
					if (!havInsertListener){
						addPartContent(sb,doc,moduleName,LISTENER);
						havInsertListener=true;
					}
					if (!havInsertFilter){
						addPartContent(sb,doc,moduleName,FILTER);
						havInsertFilter=true;
					}
					if (!havInsertFilterMapping){
						addPartContent(sb,doc,moduleName,FILTER_MAPPING);
						havInsertFilterMapping=true;
					}
					if (!havInsertServlet){
						addPartContent(sb,doc,moduleName,SERVLET);
						havInsertServlet=true;
					}
				}
				else if (tempString.indexOf("<session-config>")!=-1 || tempString.indexOf("<jsp-config>")!=-1 
						|| tempString.indexOf("<welcome-file-list>")!=-1 || tempString.indexOf("</web-app>")!=-1){
					if (!havInsertListener){
						addPartContent(sb,doc,moduleName,LISTENER);
						havInsertListener=true;
					}
					if (!havInsertFilter){
						addPartContent(sb,doc,moduleName,FILTER);
						havInsertFilter=true;
					}
					if (!havInsertFilterMapping){
						addPartContent(sb,doc,moduleName,FILTER_MAPPING);
						havInsertFilterMapping=true;
					}
					if (!havInsertServlet){
						addPartContent(sb,doc,moduleName,SERVLET);
						havInsertServlet=true;
					}
					if (!havInsertServletMapping){
						addPartContent(sb,doc,moduleName,SERVLET_MAPPING);
						havInsertServletMapping=true;
					}
				}
				
				if(!needDelete){
					sb.append(tempString + "\n");
				}
			}
			FileUtilities.saveFile(webPath + "/web.xml", sb.toString().getBytes());
 		}
 		catch(Exception e){
 			WEBProjPlugin.getDefault().logError(e.getMessage(),e);
 		}finally{
 			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				WEBProjPlugin.getDefault().logError(e.getMessage(), e);
			}
 		}
	}
	
	/**
	 * ����*.part�ļ�·�����õ�modlueName
	 * 
	 */
	private static String getModuleName(IProject proj, File partFile) {
		String[] bcpNames = LfwCommonTool.getBCPNames(proj);
		if (bcpNames == null || bcpNames.length == 0)
			return LfwCommonTool.getModuleProperty(proj,"module.name");
		else {
			String partPath = partFile.getPath();
			for (int i = 0; i < bcpNames.length; i++) {
				if (partPath.indexOf("/" + bcpNames[i] + "/web/WEB-INF/") > -1  
					|| partPath.indexOf("\\" + bcpNames[i] + "\\web\\WEB-INF\\") > -1 ){
					return 	bcpNames[i];
				}
			}
		}
		return null;
	}

//	private static String getModuleNameByPartFilePath(String partFilePath) {
//		//  /NC_CS_OAINF/informationpublish/web/WEB-INF/portal.part
//		
//	}

	/**
	 * �ϲ�����Ŀ�����д�����Ŀ
	 * 
	 * @param mainProj ����Ŀ
	 */
	public static void mergeParts(IProject mainProj){
		//����Ŀ·��
		String ctx = LfwCommonTool.getLfwProjectCtx(mainProj);
		/*�õ�����lfw��Ŀ*/
		IProject[] projs = LfwCommonTool.getOpenedLfwProjects();
		for (int i = 0; i < projs.length; i++) {
			IProject proj = projs[i];
			String projPath = proj.getLocation().toString();
			String[] prePaths = null;
			if(LfwCommonTool.isBCPProject(proj)) {
				prePaths = LfwCommonTool.getBCPNames(proj);
			}
			else
				prePaths = new String[]{""};
			for (int j = 0; j < prePaths.length; j++) {
				String path = prePaths[j];
				if(!path.equals(""))
					path = "/" + path;
				File partFile = new File(projPath + path + "/web/WEB-INF/" + ctx + ".part");
				if(partFile.exists()){
					mergeWebXml(proj,partFile);
				}
			}
		}
	}
	
	/**
	 * ɾ��*.partʱ����web.xml��ɾ���������
	 * 
	 * @param proj
	 * @param webName
	 */
	public static void deletePart(IProject proj,String webName){
		String webPath = HOTWEBS + "/" + webName + "/WEB-INF";
		File webFile = new File(webPath + "/web.xml");
		/* ���������web.xml ������ */
		if (!webFile.exists()){
			return;
		}
		String moduleName = LfwCommonTool.getModuleProperty(proj,"module.name");
		/* web.xml Buffer*/
 		StringBuffer sb = new StringBuffer();
 		String tempString = null;
 		/* �ҵ�part���֣�����ɾ�� */
 		Boolean needDelete = false;
 		String paramName = "";
 		BufferedReader reader = null;
		try {
 			reader = new BufferedReader(new FileReader(webFile));
			while ((tempString = reader.readLine()) != null){
				/*���� <param-name>modules</param-name>*/
				if (tempString.indexOf("<param-name>modules</param-name>") != -1){
					paramName = "modules";
				}
				else if (paramName.equals("modules")  && tempString.indexOf("<param-value>") != -1){
					String modules = tempString.replace("<param-value>", "").replace("</param-value>", "").trim();
					String newModules = "";
					if (modules.indexOf(moduleName+",")!= -1){
						newModules = modules.replace(moduleName+",", "");
					}
					else if (modules.indexOf(moduleName)!= -1){
						newModules = modules.replace(moduleName, "");
					}
					sb.append("<param-value>" + newModules + "</param-value>\n");
					continue;
				}
				else if (tempString.indexOf("</context-param>")!=-1){
					paramName = "";
				}
				
				/*ɾ��part*/
				if (tempString.indexOf("_begin:"+ moduleName +"-->")!=-1){
					needDelete = true;
				}
				else if (tempString.indexOf("_end:"+ moduleName +"-->")!=-1){
					needDelete = false;
					continue;
				}
				if(!needDelete){
					sb.append(tempString + "\n");
				}
			}
			FileUtilities.saveFile(webPath + "/web.xml", sb.toString().getBytes());
 		}
 		catch(Exception e){
 			WEBProjPlugin.getDefault().logError(e.getMessage(),e);
 		}finally{
 			try {
 				if (reader != null)
 					reader.close();
			} catch (IOException e) {
				WEBProjPlugin.getDefault().logError(e.getMessage(),e);
			}
 		}
				
	}	

	/**
	 * ��web.xml����������
	 * 
	 * @param webBuffer
	 * @param doc
	 * @param moduleName
	 * @param type
	 */
	private static void addPartContent(StringBuffer webBuffer, Document doc, String moduleName, String type) {
		if (doc == null) return;
		NodeList nodeList = doc.getElementsByTagName(type);
		if (nodeList.getLength()<1)
			return;
		webBuffer.append("<!--"+ type +"_begin:"+ moduleName +"-->\n");
		for (int i=0; i<nodeList.getLength(); i++){
			Node node = nodeList.item(i);
 			Writer wr = new StringWriter();
 			XmlUtility.printDOMTree(wr, node, 0, "UTF-8");
			webBuffer.append(wr.toString() + "\n");
		}	
		webBuffer.append("<!--"+ type +"_end:"+ moduleName +"-->\n");
	}
	
}
