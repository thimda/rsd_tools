package nc.uap.portal.page;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.XmlUtility;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.om.Display;
import nc.uap.portal.om.LookAndFeel;
import nc.uap.portal.om.Page;
import nc.uap.portal.om.PortletDisplay;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.om.Skin;
import nc.uap.portal.om.Theme;

import org.eclipse.swt.ole.win32.Variant;

/**
 *��Flex���ݽ���
 * 
 * @author dingrf
 */
public class PageDataProviderImpl implements IPageDataProvider {

	/**
	 * ����Portlet����
	 */
	public void loadPortletCates(PortalPageEditor portalPageEditor,String callBack) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		List<Display>  displays = PortalConnector.getAllDisplays(projectPath,projectModuleName);
		Document doc = XmlUtility.getNewDocument(); 
		Element root = doc.createElement("Cates");
		doc.appendChild(root);	
		for (Display display : displays){
			for (PortletDisplayCategory pdc :display.getCategory()){
				Element node = doc.createElement("Cate");
				node.setAttribute("id", pdc.getId());
				node.setAttribute("text", pdc.getText());
				node.setAttribute("i18name", pdc.getI18nName());
				root.appendChild(node);			
			}
		}
		Writer wr = new StringWriter();
		XmlUtility.printDOMTree(wr, doc, 0, "UTF-8");
		String xml = wr.toString();
		int[] methodIDs = portalPageEditor.getAutomation()
		.getIDsOfNames(new String[] { "CallFunction" });
		String arg = "<invoke name=\""+callBack+"\" returntype=\"xml\"><arguments><string>"+xml+"</string></arguments></invoke>"  ;
		Variant[] methodArgs = {
				new Variant(arg) };
		portalPageEditor.getAutomation().invoke(methodIDs[0], methodArgs);
	}

	/**
	 *��ȡPortlet
	 */
	public void loadPortlet(PortalPageEditor portalPageEditor,
			String callBack, String cateId) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		List<Display>  displays = PortalConnector.getAllDisplays(projectPath,projectModuleName);
		Document doc = XmlUtility.getNewDocument();
		Element root = doc.createElement("Portlets");
		doc.appendChild(root);		
		for (Display display : displays){
			for (PortletDisplayCategory pdc :display.getCategory()){
				if (pdc.getId().equals(cateId)){
					createElement(pdc,doc,root);
					break;
				}
			}
		}
		Writer wr = new StringWriter();
		XmlUtility.printDOMTree(wr, doc, 0, "UTF-8");
		String xml = wr.toString();
		int[] methodIDs = portalPageEditor.getAutomation()
		.getIDsOfNames(new String[] { "CallFunction" });
		String arg = "<invoke name=\""+callBack+"\" returntype=\"xml\"><arguments><string>"+xml+"</string></arguments></invoke>"  ;
		Variant[] methodArgs = {
				new Variant(arg) };
		portalPageEditor.getAutomation().invoke(methodIDs[0], methodArgs);
	}
	
	private void  createElement(PortletDisplayCategory pdc,Document doc,Element root){
		for (PortletDisplay pd :pdc.getPortletDisplayList()){
			Element node = doc.createElement("Portlet");
			node.setAttribute("id", pd.getId());
			node.setAttribute("title", pd.getTitle());
			root.appendChild(node);
		}
	} 

	/**
	 *��ȡskin
	 */
	public void loadSkin(PortalPageEditor portalPageEditor, String callBack,
			String type) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		List<Skin> skins = PortalConnector.getAllSkins(projectPath, projectModuleName, type);
		Document doc = XmlUtility.getNewDocument();
		Element root = doc.createElement("Skins");
		root.setAttribute("type", type);
		doc.appendChild(root);		
		for (Skin skin :skins){
			Element node = doc.createElement("Skin");
			node.setAttribute("id", skin.getId());
			node.setAttribute("name", skin.getName());
			root.appendChild(node);
		}
		Writer wr = new StringWriter();
		XmlUtility.printDOMTree(wr, doc, 0, "UTF-8");
		String xml = wr.toString();
		int[] methodIDs = portalPageEditor.getAutomation()
		.getIDsOfNames(new String[] { "CallFunction" });
		String arg = "<invoke name=\""+callBack+"\" returntype=\"xml\"><arguments><string>"+xml+"</string></arguments></invoke>"  ;
		Variant[] methodArgs = {
				new Variant(arg) };
		portalPageEditor.getAutomation().invoke(methodIDs[0], methodArgs);

	}

	/**
	 *��ȡtheme
	 */
	public void loadTheam(PortalPageEditor portalPageEditor, String callBack,
			String groupId) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		LookAndFeel lookAndFeel= PortalConnector.getLookAndFeel(projectPath,projectModuleName);
		Document doc = XmlUtility.getNewDocument();
		Element root = doc.createElement("Theams");
		doc.appendChild(root);
		if (lookAndFeel != null){
			for (Theme theme :lookAndFeel.getTheme()){
				Element node = doc.createElement("Theam");
				node.setAttribute("id", theme.getId());
				node.setAttribute("title", theme.getTitle());
				root.appendChild(node);
			}
		}
		Writer wr = new StringWriter();
		XmlUtility.printDOMTree(wr, doc, 0, "UTF-8");
		String xml = wr.toString(); 
		int[] methodIDs = portalPageEditor.getAutomation()
		.getIDsOfNames(new String[] { "CallFunction" });
		String arg = "<invoke name=\""+callBack+"\" returntype=\"xml\"><arguments><string>"+xml+"</string></arguments></invoke>"  ;
		Variant[] methodArgs = {
				new Variant(arg) };
		portalPageEditor.getAutomation().invoke(methodIDs[0], methodArgs);
	}

	public void loadXML(PortalPageEditor portalPageEditor, String callBack) {
		Page page = (Page) portalPageEditor.getPageTreeItem().getData();
		String xml = PortalConnector.pageToString(page);
		int[] methodIDs = portalPageEditor.getAutomation()
		.getIDsOfNames(new String[] { "CallFunction" });
		String arg = "<invoke name=\""+callBack+"\" returntype=\"xml\"><arguments><string>"+xml+"</string></arguments></invoke>";
		Variant[] methodArgs = {
				new Variant(arg) };
		portalPageEditor.getAutomation().invoke(methodIDs[0], methodArgs);
	}

	public void saveXml(PortalPageEditor portalPageEditor, String callBack,String xml) throws UnsupportedEncodingException {
			Page page = (Page) portalPageEditor.getPageTreeItem().getData();
			String pageXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +URLDecoder.decode(xml, "UTF-8");
			String projectPath = LFWPersTool.getProjectWithBcpPath();
			String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
			PortalConnector.savePageToXml(projectPath, projectModuleName, page.getPagename(),pageXml );
			portalPageEditor.getPageTreeItem().setData(PortalConnector.getPage(projectPath, projectModuleName, page.getPagename())); 
			portalPageEditor.setDirty(false);
	}
}
