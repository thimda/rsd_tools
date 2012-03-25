package nc.uap.lfw.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.mlr.Checks;
import nc.uap.lfw.mlr.CreateResFileChange;
import nc.uap.lfw.mlr.MLRPropertyCache;
import nc.uap.lfw.mlr.MLRes;
import nc.uap.lfw.mlr.MLResElement;
import nc.uap.lfw.mlr.MLResSubstitution;
import nc.uap.lfw.mlr.MyPropertyFileDocumentModel;
import nc.uap.lfw.plugin.Activator;
import nc.uap.lfw.tool.Helper;
import nc.uap.lfw.tool.ProjConstants;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.internal.corext.refactoring.changes.TextChangeCompatibility;
import org.eclipse.jdt.internal.corext.refactoring.nls.KeyValuePair;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * xml���� Refactoring
 * 
 * @author dingrf
 *
 */
public class MLRRefactoring extends Refactoring{

	/** project*/
	private IProject project;
//	/** ��Դ�ļ�ģ������*/
//	private String resModuleName;
	/** ��Դ�ļ�����*/
//	private String resFileName;
//	/** keyֵǰ׺*/
//	private String prefix;
	private List<MLResSubstitution> substitutionsList;
//	private MLResSubstitution rawSubstitutions[];
	private MLResSubstitution substitutions[];
	
	/**��ǰPageNode*/
	private PageNode currentPageNode;
	
	private String resourceHomePath;
	/** xml�ļ�����*/
	private Map<String,String> contents;
	/** xml�ļ�ԭʼ����*/
	private Map<String,String> oldContents;
	/**���ж�����ԴCache*/
	private MLRPropertyCache propCache;
	/**page2*/
	private ExternalizeMLRWizardPage2 page2;
	/**Դ�ļ������ı�*/
	private boolean sourceFileChanged=false;
	
	/**���ɵ�key�б�*/
	private List<String> newKeyList;

	
	public MLRRefactoring(IProject project){
		
		this.project = project;
		propCache = new MLRPropertyCache(project, getResouceHomePath());
//		String eleName = "xxxxxxxxxxxxx";
//		prefix = eleName + "-";
//		resModuleName = project.getName()+"_nodes";
//		resFileName = eleName + "res.properties";
//		rawSubstitutions = createRawSubstitution();
//		substitutions = rawSubstitutions;
	}

	/**
	 * ȡ��Դ�ļ�·��
	 * 
	 * @return
	 */
	public String getResouceHomePath(){
		if (resourceHomePath == null){
			String prefix = "";
//			String elePath = xmlFile.getLocation().toString();
			String elePath = "eeeeeeeeeeeeeee";
			String projPath = project.getLocation().toOSString();
			int index = elePath.indexOf(projPath);
			if (index != -1)
				index += projPath.length();
			if (index != -1 && index < elePath.length())
				prefix = elePath.substring(index);
			resourceHomePath = (new StringBuilder(prefix)).append("/resources").toString();
		}
		return resourceHomePath;
	}

	/**
	 * �ҵ�����i18nName���ԵĽڵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void createRawSubstitution(List<PageNode> pageNodes){
		for (PageNode p : pageNodes){
			if (!p.isFile())
				continue;
			File file = new File(p.getPath());
			try {
				if (this.contents == null)
					this.contents = new HashMap<String,String>();
				Map<String,String> keyMap = new HashMap<String,String>();
				String cx = getModifiedContent(file,p,keyMap);
				this.contents.put(p.getPath(), cx);
				Document doc = DocumentHelper.parseText(this.contents.get(p.getPath())) ;
				Document oldDoc = DocumentHelper.parseText(this.oldContents.get(p.getPath())) ;
				List<Element> nodes =  doc.selectNodes("//*[@"+ ProjConstants.I18NNAME +"]");
				
				//��i18nName��Ӧ�����ԣ�˳��text,title,caption
				for (Element e : nodes){
					String value;
					if (e.attribute("text") !=null){
						value = e.attribute("text").getText();
					}
					else if(e.attribute("title") !=null){
						value = e.attribute("title").getText();
					}
					else if (e.attribute("caption") !=null){
						value = e.attribute("caption").getText();
					}
					else
						value ="";
					//���� MLResSubstitution
					String resID = e.attribute(ProjConstants.I18NNAME).getText();
					String i18n = ProjConstants.I18NNAME + "=\"" + e.attribute(ProjConstants.I18NNAME).getText() + "\"";
//					int start = this.contents.get(p.getPath()).indexOf(i18n); 
//					int length = i18n.length();
					MLResElement ele = new MLResElement(value,0,0);
				    MLResSubstitution substitution = new MLResSubstitution(ele, ele.getValue());
				    substitution.setPrefix(p.getPrefix());
				    String langDir ="";
				    if (e.attribute(ProjConstants.LANG_DIR) != null)
				    	langDir = e.attribute(ProjConstants.LANG_DIR).getText();
				    substitution.setLangModule(langDir);
				    substitution.setFilePath(p.getPath());
				    substitution.setSelectKey(i18n);
				    substitution.setResFileName(p.getRoot()+"res.properties");
				    substitution.setPageNode(p);
				    
				    //�Ƿ�Ϊ����
				    boolean isNew = true;	
				    
				    //�Ѷ��ﻯ�ǹ�����Դ
				    if (this.oldContents.get(p.getPath()).indexOf(resID)!= -1 && !langDir.equals(ProjConstants.COMM)){
				    	List<Element> tempNodes = oldDoc.selectNodes("//*[@"+ ProjConstants.I18NNAME +"='"+resID+"']");
				    	for(Element el : tempNodes){
				    		//����i18nName �� langDir�ж��Ƿ��Ѵ���
				    		if (el.attribute(ProjConstants.LANG_DIR)!= null && el.attribute(ProjConstants.LANG_DIR).getText().equals(langDir)){
				    			substitution.setExtKey(resID);
				    			substitution.setExtLangModule(e.attribute(ProjConstants.LANG_DIR).getText());
				    			substitution.setState(1);
				    			MLRes simpchnRes = propCache.findLocalMLRes(p.getBcpName(), ProjConstants.LANGCODE_SIMPCHN, substitution.getExtLangModule(), resID);
//NO_EN				    			
//				    			MLRes tradchnRes = propCache.findLocalMLRes(ProjConstants.LANGCODE_TRADCHN, substitution.getExtLangModule(), resID);
//				    			MLRes englishRes = propCache.findLocalMLRes(ProjConstants.LANGCODE_ENGLISH, substitution.getExtLangModule(), resID);
				    			if (simpchnRes != null)
				    				substitution.setSimpchnRes(simpchnRes);
//NO_EN				    			
//				    			if (tradchnRes != null)
//				    				substitution.setTradchnRes(tradchnRes);
//				    			if (englishRes != null)
//				    				substitution.setEnglishRes(englishRes);
				    			isNew = false;
				    			break;
				    		}
				    		else{
				    			//����
				    			substitution.setState(4);
				    			isNew = false;
				    		}
				    	}
				    }
				  //������Դ
				    else if (this.oldContents.get(p.getPath()).indexOf(resID)!= -1 && langDir.equals(ProjConstants.COMM)){
				    	substitution.setCommKey(resID);
				    	substitution.setState(3);
						MLRes simpchnRes = propCache.findLocalMLRes(p.getBcpName(),ProjConstants.LANGCODE_SIMPCHN, substitution.getLangModule(), resID);
						MLRes tradchnRes = propCache.findLocalMLRes(p.getBcpName(),ProjConstants.LANGCODE_TRADCHN, substitution.getLangModule(), resID);
						MLRes englishRes = propCache.findLocalMLRes(p.getBcpName(),ProjConstants.LANGCODE_ENGLISH, substitution.getLangModule(), resID);
						if (simpchnRes != null)
							substitution.setSimpchnRes(simpchnRes);
//						if (tradchnRes != null)
//							substitution.setTradchnRes(tradchnRes);
//						if (englishRes != null)
//							substitution.setEnglishRes(englishRes);
						isNew = false;
				    }
				    
				    if (isNew){
					    //ȥ���ظ�id
				    	String k = null;
					    if (substitution.getValue() != null && !substitution.getValue().equals("")){
					    	if (keyMap.containsKey(substitution.getValue())){
					    		k = keyMap.get(substitution.getValue());
					    		String s = ProjConstants.I18NNAME + "=\"" + p.getPrefix() + k + "\"";
					    		String  c = this.contents.get(p.getPath());
					    		c = c.replace(substitution.getSelectKey(), s);
					    		this.contents.put(p.getPath(), c);
					    		substitution.setSelectKey(s);
					    	}
					    	else{
					    		k = resID.replace(substitution.getPrefix() , "");
					    		keyMap.put(substitution.getValue(), k);
					    	}
					    } 
					    substitution.setKey(k);
				    }
				    if (substitutionsList == null)
				    	substitutionsList = new ArrayList(); 
				    substitutionsList.add(substitution);
				}
				//�ҵ��к��ֵ���û��i18nName����
				while (!cx.equals("")){
					String s = cx.substring(0,cx.indexOf("\n")+1);
					if (s.length() != s.getBytes().length  && s.indexOf(ProjConstants.I18NNAME) == -1){
						
//						int start = this.contents.get(p.getPath()).indexOf(s); 
//						int length = s.length();
						MLResElement ele = new MLResElement("��i18nName",0,0);
					    MLResSubstitution substitution = new MLResSubstitution(ele, ele.getValue());
					    substitution.setPrefix(p.getPrefix());
					    substitution.setFilePath(p.getPath());
					    substitution.setResFileName(p.getRoot()+"res.properties");
					    substitution.setPageNode(p);
					    substitution.setLangModule(p.getResModuleName());
					    substitution.setState(6);
					    
						String s1 = s.trim();
						substitution.setValue(getSimpchn(s1));
						String c = this.getContents().get(p.getPath());
						
					    //ȥ���ظ�id
						String k =null;
					    if (substitution.getValue() != null && !substitution.getValue().equals("")){
					    	if (keyMap.containsKey(substitution.getValue())){
					    		k = keyMap.get(substitution.getValue());
					    		String select = ProjConstants.I18NNAME + "=\"" + p.getPrefix() + k + "\"";
//					    		c = c.replace(substitution.getSelectKey(), select);
					    		substitution.setSelectKey(select);
					    	}
					    	else{
					    		k = getKey(cx, p).replace(p.getPrefix(), "");
					    		keyMap.put(substitution.getValue(), k);
					    	}
					    } 
					    substitution.setKey(k);
						String s2 = s1.replaceFirst(" ", " " +  ProjConstants.I18NNAME + "=\"" + substitution.getRealKey() + "\" " + ProjConstants.LANG_DIR + "=\"" + p.getResModuleName() +"\" ");
						c = c.replace(s1, s2);
						this.contents.put(p.getPath(), c);
						substitution.setOldKey(s1);
						substitution.setSelectKey(s2);
						substitution.getElement().setFPosition(c.indexOf(s2),s2.length());
					    
					    if (substitutionsList == null)
					    	substitutionsList = new ArrayList(); 
					    substitutionsList.add(substitution);
						
					}
					cx = cx.replace(s, "");
				}
			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		if (substitutionsList != null){
//			rawSubstitutions = (MLResSubstitution[])substitutionsList.toArray(new MLResSubstitution[0]);
			substitutions = (MLResSubstitution[])substitutionsList.toArray(new MLResSubstitution[0]);
		}
		//����λ����Ϣ
		for (PageNode p : pageNodes){
			updateSubPostion(p);
		}
		
	}
	
	/**
	 * �޸�xml�ļ����Կյ�i18nName����ID,������langDirʱ������langDir
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	private String getModifiedContent(File file,PageNode p,Map<String,String> keyMap) throws Exception {
		String cxOld = FileUtilities.fetchFileContent(file,"UTF-8");
		String cx = cxOld;
		if (this.oldContents == null)
			this.oldContents = new HashMap<String,String>();
		this.oldContents.put(p.getPath(), cxOld);
		
		//��i18nName��ֵ
		List<String> newKeys = new ArrayList<String>();
		while (cx.indexOf(ProjConstants.I18NNAME_ISNULL) != -1){
			String k = getKey(cx,p);
			newKeys.add(k);
			cx = cx.replaceFirst(ProjConstants.I18NNAME_ISNULL, ProjConstants.I18NNAME +"=\""+ k +"\"");
		}
		// �滻langDir="" Ϊ langDir="moduleName"
		cx = cx.replace(ProjConstants.LANG_ISNULL, ProjConstants.LANG_DIR + "=\""+ p.getResModuleName() +"\"");
		
		//����i18nName��û��langDir���ԵĽڵ����langDir����
		Document doc = DocumentHelper.parseText(cx);
		List<Element> nodes =  doc.selectNodes("//*[@" + ProjConstants.I18NNAME + "]");
		for (Element e : nodes){
			String k = e.attribute(ProjConstants.I18NNAME).getText();
			if (newKeys.contains(k)){
				String i18nName = ProjConstants.I18NNAME + "=\"" + k + "\""; 
				if (e.attribute(ProjConstants.LANG_DIR)==null){
					cx = cx.replaceFirst(i18nName, i18nName + " " + ProjConstants.LANG_DIR +  "=\""+ p.getResModuleName() +"\"");
				}
			}
		}
		
		if (cx!= cxOld)
			p.setChanged(true);
		return cx;
	}
	
	/**
	 * ȡģ������е�������ԴID�б�
	 * 
	 * @param resModuleName
	 * @return
	 */
	private List<String> getModuleKeyList(String bcpName,String resModuleName) {
		List<MLRes> simpchnResList = (List<MLRes>) propCache.findLocalMLResList(bcpName,ProjConstants.LANGCODE_SIMPCHN, resModuleName);
//NO_EN		
//		List<MLRes> tradchnResList = (List<MLRes>) propCache.findLocalMLRes(ProjConstants.LANGCODE_TRADCHN, resModuleName);
//		List<MLRes> englishResList = (List<MLRes>) propCache.findLocalMLRes(ProjConstants.LANGCODE_ENGLISH, resModuleName);
		List<String> keyList = new ArrayList<String>();
		for (MLRes m : simpchnResList){
			if (!keyList.contains(m.getResID()))
				keyList.add(m.getResID());
		}
//		for (MLRes m : tradchnResList){
//			if (!keyList.contains(m.getResID()))
//				keyList.add(m.getResID());
//		}
//		for (MLRes m : englishResList){
//			if (!keyList.contains(m.getResID()))
//				keyList.add(m.getResID());
//		}
		return keyList;
	}

	/**
	 * ������key
	 * 
	 * @param content
	 * @return
	 */
	public String getKey(String content,PageNode p){
		int i = 1;
		String key = createKey(i);
		String keyWithPrefix = p.getPrefix() + key;
		
		//ģ�����Ѵ��ڵ�key
		List<String> keyList = getModuleKeyList(p.getBcpName(),p.getResModuleName());
		
		if (newKeyList == null)
			newKeyList = new ArrayList<String>();
		//��key����Ϊģ����Դ�����е�key��Ҳ�����뵱ǰ�ļ������ظ�
		while (newKeyList.contains(keyWithPrefix) || keyList.contains(keyWithPrefix) || content.indexOf(keyWithPrefix)!= -1){
			key = createKey(++i);
			keyWithPrefix = p.getPrefix() + key;
		}
		keyList.add(keyWithPrefix);
		newKeyList.add(keyWithPrefix);
		return keyWithPrefix;
	}
	
	/**
	 * ����key
	 * 
	 * @param counter
	 * @return
	 */
	private String createKey(int counter){
		String temp = "000000";
		String str = String.valueOf(counter);
		if (str.length() > temp.length())
			return str.substring(str.length() - temp.length());
		else
			return (new StringBuilder(String.valueOf(temp.substring(0, temp.length() - str.length())))).append(str).toString();
	}

	/**
	 * ����ǰ׺
	 * 
	 */
	private void refreshPrefix(){
		if (currentPageNode == null)
			return;
		int count = substitutions != null ? substitutions.length : 0;
		for (int i = 0; i < count; i++){
			if (substitutions[i].getPageNode() != currentPageNode)
				continue;
			if (substitutions[i].getState()==0){
				String oldKey = substitutions[i].getPrefix() + substitutions[i].getKey(); 
				substitutions[i].setPrefix(currentPageNode.getPrefix());
				String contents = this.contents.get(currentPageNode.getPath());
				contents = contents.replace(oldKey, substitutions[i].getPrefix() + substitutions[i].getKey());
				this.contents.put(currentPageNode.getPath(), contents);
			}
		}
	}

	/**
	 * ���¶���ģ��
	 * 
	 */
	//TODO   ���ܲ���ɾ�� 
//	private void refreshLangModule(){
//		int count = substitutions != null ? substitutions.length : 0;
//		List<String> keyList = getModuleKeyList(resModuleName);
//		for (int i = 0; i < count; i++){
//			if (substitutions[i].getState()==0){
//				//����langDir
//				String oldLangModule = substitutions[i].getLangModule(); 
//				substitutions[i].setLangModule(resModuleName);
//				String contents = this.contents.get(currentPageNode.getPath());
//				contents = contents.replace(ProjConstants.LANG_DIR + "=\"" + oldLangModule + "\"", ProjConstants.LANG_DIR+ "=\"" + resModuleName + "\"");
//				this.contents.put(currentPageNode.getPath(), contents);
//				//����i18nName
//				String oldkey = substitutions[i].getRealKey();
//				//��ģ����Դ�ļ��д���ǰ��key
//				if (keyList.contains(oldkey)){
//					String newKey = getKey(contents,keyList);
//					substitutions[i].setKey(newKey.replace(prefix, ""));
//					contents = this.contents.get(currentPageNode.getPath());
//					contents = contents.replaceFirst(ProjConstants.I18NNAME+ "=\"" + oldkey + "\"", ProjConstants.I18NNAME +"=\""+ newKey +"\"");
//					this.contents.put(currentPageNode.getPath(), contents);
//					
//				}
//			}
//		}
//	}
	
	public void updateSubPostion(PageNode pageNode){
		PageNode p = null;
		if (pageNode != null)
			p = pageNode;
		else if (currentPageNode != null)
			p = currentPageNode;
		if (p == null)
			return;
		int count = substitutions != null ? substitutions.length : 0;
		//����λ����Ϣ
		for (int i = 0; i < count; i++){
			if (substitutions[i].getPageNode() != p)
				continue;
			if (substitutions[i].getState() == 4){
//				String value = ProjConstants.I18NNAME + "=\"" + substitutions[i].getSelectKey() + "\"";;
				String value = substitutions[i].getSelectKey();
				substitutions[i].getElement().setFPosition(this.contents.get(p.getPath()).indexOf(value), value.length());
			}
			else if (substitutions[i].getState() == 5){
				String value = substitutions[i].getSelectKey();
				substitutions[i].getElement().setFPosition(this.contents.get(p.getPath()).indexOf(value), value.length());
			}
			else{
//				String newKey = ProjConstants.I18NNAME + "=\"" + substitutions[i].getRealKey() + "\"";
				String newKey = substitutions[i].getSelectKey();
				substitutions[i].getElement().setFPosition(this.contents.get(p.getPath()).indexOf(newKey), newKey.length());
			}
		}
	}
	
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException{
		RefactoringStatus refactoringstatus;
		pm.beginTask("����ִ���ⲿ��������", 3);
		RefactoringStatus result = new RefactoringStatus();
		result.merge(Checks.validateModifiesFiles(getAllFilesToModify(), getValidationContext()));
		if (!needModifyPropFile() && !needModifySourceFile())
			result.addFatalError("û���κζ�����Դ�ⲿ����");
		try{
			int count = substitutions != null ? substitutions.length : 0;
			for (int i = 0; i < count; i++){
				IFile fsimpchn = getResBoundleFile(ProjConstants.LANGCODE_SIMPCHN,substitutions[i].getRealLangModule(),substitutions[i].getResFileName());
				if (fsimpchn.exists() && !fsimpchn.getCharset().equalsIgnoreCase("GBK"))
					fsimpchn.setCharset("GBK", pm);
//ON_EN				
//				IFile ftradchn = getResBoundleFile(ProjConstants.LANGCODE_TRADCHN,substitutions[i].getResFileName());
//				if (ftradchn.exists())
//					ftradchn.setCharset("UTF-16", pm);
//				IFile fenglis = getResBoundleFile(ProjConstants.LANGCODE_ENGLISH,substitutions[i].getResFileName());
//				if (fenglis.exists() && !fenglis.getCharset().equalsIgnoreCase("GBK"))
//					fenglis.setCharset("GBK", pm);
			}	
		}
		catch (Exception e){
			result.addFatalError((new StringBuilder()).append(e.getClass()).append(":").append(e.getMessage()).toString());
		}
		refactoringstatus = result;
		pm.done();
		return refactoringstatus;
	}

	/**
	 * �õ�������Ҫ�޸ĵ�file
	 * 
	 * @return
	 */
	private IFile[] getAllFilesToModify(){
		List<IFile> list = new ArrayList();
//		if (needModifySourceFile()){
//			list.add(new IFile(""));
//		}
		
		if (needModifyPropFile()){
			int count = substitutions != null ? substitutions.length : 0;
			for (int i = 0; i < count; i++){
				IFile fsimpchn = getResBoundleFile(ProjConstants.LANGCODE_SIMPCHN,substitutions[i].getRealLangModule(),substitutions[i].getResFileName());
				if (fsimpchn.exists())
					list.add(fsimpchn);
//NO_EN				
//				IFile ftradchn = getResBoundleFile(ProjConstants.LANGCODE_TRADCHN,substitutions[i].getResFileName());
//				if (ftradchn.exists())
//					list.add(ftradchn);
//				IFile fenglis = getResBoundleFile(ProjConstants.LANGCODE_ENGLISH,substitutions[i].getResFileName());
//				if (fenglis.exists())
//					list.add(fenglis);
			}	
		}
		return (IFile[])list.toArray(new IFile[0]);
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException{
//		if (substitutions == null || substitutions.length == 0)
//			return RefactoringStatus.createFatalErrorStatus("û����Ҫ�ⲿ���Ķ�����Դ��");
//		else
			return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException{
		CompositeChange compositechange;
		pm.beginTask("", 3);
		CompositeChange change = new CompositeChange("�ⲿ��NC������Դ");
		if (needModifyPropFile()){
			createUpdateResFileModifyChange(change);
//			addResFileModifyChange(change, ProjConstants.LANGCODE_SIMPCHN);
//			addResFileModifyChange(change, ProjConstants.LANGCODE_ENGLISH);
//			addResFileModifyChange(change, ProjConstants.LANGCODE_TRADCHN);
		}
		pm.worked(1);
		if (needModifySourceFile()){
				createModifySourceChange(change);
//				Change c = createModifySourceChange();
//				change.add(c);
		}
		
		pm.worked(1);
		compositechange = change;
		pm.done();
		return compositechange;
	}

	private boolean needModifySourceFile(){
		boolean b = false;
		if (sourceFileChanged)
			return true;
		int count = substitutions != null ? substitutions.length : 0;
		for (int i = 0; i < count; i++){
			MLResSubstitution sub = substitutions[i];
			if (sub.getState() == 0){
				b = true;
				break;
			}
		}
		return b;
	}

	private boolean needModifyPropFile(){
		boolean b = false;
		int count = substitutions != null ? substitutions.length : 0;
		for (int i = 0; i < count; i++){
			MLResSubstitution sub = substitutions[i];
			if (sub.getState() == 0 || sub.hasModifyed()){
				b = true;
				break;
			}
		}
		return b;
	}

	/**
	 * ��Դ�ļ��ı�
	 * 
	 * @param change
	 * @param langCode
	 * @throws CoreException
	 */
//	private void addResFileModifyChange(CompositeChange change, String langCode)throws CoreException{
//		//��������Դ�ļ�
//		CreateResFileChange cfc = createFileChange(langCode);
//		if (cfc != null){
//			change.add(cfc);
//		} 
//		//������Դ�ļ�
//		else{
//			TextFileChange tfc = createTextFileChange(langCode);
//			change.add(tfc);
//		}
//	}
	

	/**
	 * �޸�Դ�ļ�
	 * 
	 * @return
	 * @throws CoreException
	 */
	private void createModifySourceChange(CompositeChange change)throws CoreException{
		for (PageNode p: page2.getPageNodes()){
			if (p.isFile() && p.isChanged()){
				TextChange c =  new TextFileChange("�޸�Դ�ļ�", p.getFile());
				int l = c.getCurrentDocument(null).getLength();
				TextChangeCompatibility.addTextEdit(c, "�޸�Դ��", new ReplaceEdit(0, l, this.getContents().get(p.getPath())));
				change.add(c);
			}
		}
	}
	
	/**
	 * �޸���Դ�ļ�
	 * 
	 * @param change
	 * @throws CoreException
	 */
	
	private void createUpdateResFileModifyChange(CompositeChange change)throws CoreException{
		HashMap<String,TextFileChange> map = new HashMap<String,TextFileChange>();
		HashMap<IFile,MyPropertyFileDocumentModel> pfdmHM = new HashMap<IFile,MyPropertyFileDocumentModel>();
		HashMap<IFile,StringBuilder> simpchnHM = new HashMap<IFile,StringBuilder>();
//NO_EN		
//		HashMap<IFile,StringBuilder> tradchnHM = new HashMap<IFile,StringBuilder>();
//		HashMap<IFile,StringBuilder> englishHM = new HashMap<IFile,StringBuilder>();
		int count = substitutions != null ? substitutions.length : 0;
		List<String> changedKeys = new ArrayList<String>();
		for (int i = 0; i < count; i++){
			MLResSubstitution sub = substitutions[i];
//			if (sub.getState() == 2){
//				createTextFileDelResChange(sub, sub.getRealKey(), ProjConstants.LANGCODE_SIMPCHN, map);
//				createTextFileDelResChange(sub, sub.getRealKey(), ProjConstants.LANGCODE_TRADCHN, map);
//				createTextFileDelResChange(sub, sub.getRealKey(), ProjConstants.LANGCODE_ENGLISH, map);
//			} else 
			//�������޸Ļ��޸�
			if (sub.getState() == 1 || sub.getState() == 0 || sub.getState() == 6){
				String resId = sub.getRealKey();
				//ȥ���ظ�
				if (changedKeys.contains(resId))
					continue;
				changedKeys.add(resId);
				if (sub.simpchnUpdated()){
					String newValue = sub.getValue();
					MLRes res = sub.getSimpchnRes();
					String oldValue = res != null ? res.getValue() : null;
					createTextFileChange(sub, resId, oldValue, newValue, ProjConstants.LANGCODE_SIMPCHN, map, simpchnHM, pfdmHM);
				}
//NO_EN				
//				if (sub.tradchnUpdated()){
//					String newValue = sub.getTwValue();
//					MLRes res = sub.getTradchnRes();
//					String oldValue = res != null ? res.getValue() : null;
//					createTextFileChange(sub, resId, oldValue, newValue, ProjConstants.LANGCODE_TRADCHN, map, tradchnHM, pfdmHM);
//				}
//				if (sub.englishUpdated())				{
//					String newValue = sub.getEnglishValue();
//					MLRes res = sub.getEnglishRes();
//					String oldValue = res != null ? res.getValue() : null;
//					createTextFileChange(sub, resId, oldValue, newValue, ProjConstants.LANGCODE_ENGLISH, map, englishHM, pfdmHM);
//				}
			}
		}	
		createNewFileChange(change, simpchnHM, "GBK","��������������Դ�ļ�:");
//NO_EN		
//		createNewFileChange(change, tradchnHM, "UTF-16","��������������Դ�ļ�:");
//		createNewFileChange(change, englishHM, "GBK","����Ӣ����Դ�ļ�:");
		TextFileChange changes[] = (TextFileChange[])map.values().toArray(new TextFileChange[0]);
		for (int i = 0; i < changes.length; i++)
			change.add(changes[i]);
	}
	
	private void createNewFileChange(CompositeChange change, HashMap<IFile,StringBuilder> hm, String charSet,String name){
		CreateResFileChange cfc;
		for (Iterator<IFile> iter = hm.keySet().iterator(); iter.hasNext(); change.add(cfc)){
			IFile file = iter.next();
			StringBuilder sb = (StringBuilder)hm.get(file);
			cfc = new CreateResFileChange(file.getFullPath(), sb.toString(), charSet);
			cfc.setName((new StringBuilder(name)).append(file.getFullPath().toOSString()).toString());
		}

	}

	private void createTextFileChange(MLResSubstitution sub, String resId, String oldValue, String newValue, String langCode, HashMap<String,TextFileChange> map, HashMap<IFile,StringBuilder> hm,HashMap<IFile,MyPropertyFileDocumentModel> pfdmHM)throws CoreException{
		IFile file = getMLResfile(langCode, sub);
		if (oldValue != null){
			String key = file.getLocation().toOSString();
			TextFileChange tfChange = (TextFileChange)map.get(key);
			if (tfChange == null){
				tfChange = new TextFileChange("�޸Ķ�����Դ", file);
				map.put(key, tfChange);
			}
			MyPropertyFileDocumentModel model = (MyPropertyFileDocumentModel)pfdmHM.get(file);
			if (model == null){
				model = new MyPropertyFileDocumentModel(tfChange.getCurrentDocument(new NullProgressMonitor()));
				pfdmHM.put(file, model);
			}
			KeyValuePair oldKVP = new KeyValuePair(resId, Helper.unwindEscapeChars(oldValue));
			KeyValuePair newKVP = new KeyValuePair(resId, Helper.unwindEscapeChars(newValue));
			ReplaceEdit edit = model.replace(oldKVP, newKVP);
			TextChangeCompatibility.addTextEdit(tfChange, (new StringBuilder("�޸Ķ�����Դ")).append(resId).toString(), edit);
		} 
		else if (file.exists()){
			String key = file.getLocation().toOSString();
			TextFileChange tfChange = (TextFileChange)map.get(key);
			if (tfChange == null){
				tfChange = new TextFileChange("�޸Ķ�����Դ", file);
				map.put(key, tfChange);
			}
			MyPropertyFileDocumentModel model = (MyPropertyFileDocumentModel)pfdmHM.get(file);
			if (model == null){
				model = new MyPropertyFileDocumentModel(tfChange.getCurrentDocument(new NullProgressMonitor()));
				pfdmHM.put(file, model);
			}
			KeyValuePair newKVP = new KeyValuePair(resId, Helper.unwindEscapeChars(newValue));
			org.eclipse.text.edits.InsertEdit edit = model.insert(newKVP);
			TextChangeCompatibility.addTextEdit(tfChange, (new StringBuilder("���������Դ")).append(resId).toString(), edit);
		} 
		else{
			String LineDelimiter = Helper.getLineDelimiterPreference(project);
			StringBuilder sb = (StringBuilder)hm.get(file);
			if (sb == null){
				sb = new StringBuilder();
				hm.put(file, sb);
			}
			sb.append(resId).append("=").append(Helper.unwindEscapeChars(newValue)).append(LineDelimiter);
		}
	}

	/**
	 * ȡ��Դ�ļ�
	 * 
	 * @param langCode
	 * @param sub
	 * @return
	 */
	private IFile getMLResfile(String langCode, MLResSubstitution sub){
		IFile file = null;
		MLRes res = sub.getNotNullResByLangCode(langCode);
		if (res != null)
			file = project.getFile((new StringBuilder(getResouceHomePath())).append("/"+ProjConstants.LANG+"/").append(langCode).append("/").append(sub.getLangModule()).append("/").append(res.getResFileName()).toString());
		else{
			file =project.getFile((new StringBuilder(getResouceHomePath())).append("/"+ProjConstants.LANG+"/").append(langCode).append("/").append(sub.getLangModule()).append("/").append(sub.getResFileName()).toString());
		}
		if (file != null && file.exists())
			try{
				if ("simpchn".equals(langCode) && !file.getCharset().equalsIgnoreCase("GBK"))
					file.setCharset("GBK", new NullProgressMonitor());
//NO_EN				
//				else
//				if ("tradchn".equals(langCode))
//					file.setCharset("UTF-16", new NullProgressMonitor());
//				else
//				if ("english".equals(langCode) && !file.getCharset().equalsIgnoreCase("GBK"))
//					file.setCharset("GBK", new NullProgressMonitor());
			}
			catch (Exception e){
				Activator.getDefault().logError(e.getMessage(), e);
			}
		return file;
	}
	
	/***
	 * ����s�еĺ���
	 * @param s
	 * @return
	 */
	private String getSimpchn(String s){
		StringBuffer sb = new StringBuffer();
		String temp;
		boolean begin = false;
		int count = 0;
		for (int i = 0 ; i<s.length(); i++){
			temp = String.valueOf(s.charAt(i));
			if (temp.getBytes().length == 2){
				sb.append(temp);
				if (!begin)
					begin=true;
			}
			else if (begin){
				break;
			}
		}
		return sb.toString();
	}
	
//	private String getSubValue(MLResSubstitution sub, String langCode){
//		String value = sub.getValue();
//		if (langCode.equals("english"))
//			value = sub.getEnglishValue();
//		else
//		if (langCode.equals("tradchn"))
//			value = sub.getTwValue();
//		return value;
//	}

	public IFile getResBoundleFile(String langCode,String resModuleName,String resFileName){
		return Helper.getResBoundleFile(project, getResouceHomePath(), langCode, resModuleName, resFileName);
	}

	public String getName(){
			return (new StringBuilder("LFW������Դ�ⲿ��:")).toString();
	}

//	public String getResFileName(){
//		return resFileName;
//	}
//
//	public void setResFileName(String resFileName){
//		this.resFileName = resFileName;
//	}

//	public String getResModuleName(){
//		return resModuleName;
//	}

//	public void setResModuleName(String resModuleName){
//		this.resModuleName = resModuleName;
//		//TODO ���ܲ���ɾ��
//		refreshLangModule();
//		updateSubPostion();
//		page2.refreshSoruseView();
//	}

	public static MLRRefactoring create(IProject project){
			return new MLRRefactoring(project);
	}
	public MLResSubstitution[] getSubstitutions(){
		return substitutions;
	}

	public String getPrefix(){
		if (currentPageNode != null)
			return currentPageNode.getPrefix();
		return null;
	}

	public void setPrefix(String prefix){
		if (currentPageNode != null){
			String old = currentPageNode.getPrefix();
			currentPageNode.setPrefix(prefix);
			if (!old.equals(prefix)){
				refreshPrefix();
				updateSubPostion(null);
				page2.refreshSoruseView();
			}
		}
			
		
	}

	public boolean isSourceFileChanged() {
		return sourceFileChanged;
	}

	public void setSourceFileChanged(boolean sourceFileChanged) {
		this.sourceFileChanged = sourceFileChanged;
	}

	public void setPage2(ExternalizeMLRWizardPage2 page2) {
		this.page2 = page2;
	}
	
	public IProject getProject(){
		return project;
	}

	public PageNode getCurrentPageNode() {
		return currentPageNode;
	}

	public void setCurrentPageNode(PageNode currentPageNode) {
		this.currentPageNode = currentPageNode;
	}

	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}

	public Map<String, String> getOldContents() {
		return oldContents;
	}

	public void setOldContents(Map<String, String> oldContents) {
		this.oldContents = oldContents;
	}

	
//	public void savePersistentProperty(){
//		IResource res = null;
//		if (cu != null)
//			res = cu.getResource();
//		if (xmlFile!=null)
//			res = xmlFile;
//		try{
//			res.setPersistentProperty(moduleNameQN, getResModuleName());
//			res.setPersistentProperty(fileNameQN, getResFileName());
//		}
//		catch (CoreException e){
//			e.printStackTrace();
//		}
//	}
//
}
