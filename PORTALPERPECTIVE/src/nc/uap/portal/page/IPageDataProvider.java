package nc.uap.portal.page;

import java.io.UnsupportedEncodingException;


/**
 *��Flex���ݽ���
 * 
 * @author dingrf
 */
public interface IPageDataProvider {
	/**
	 *��ȡPortlet���� 
	 */
	public void loadPortletCates(PortalPageEditor portalPageEditor,String callBack);
	
	/**
	 *��ȡPortlet
	 */
	public void loadPortlet(PortalPageEditor portalPageEditor,String callBack, String cateId);

	/**
	 *��ȡtheme
	 */
	public void loadTheam(PortalPageEditor portalPageEditor, String callBack,String groupId);

	/**
	 *��ȡskin
	 */
	public void loadSkin(PortalPageEditor portalPageEditor, String callBack,String type);

	/**
	 *��ȡpage
	 */
	public void loadXML(PortalPageEditor portalPageEditor, String callBack);

	/**
	 *����page
	 * @throws UnsupportedEncodingException 
	 */
	public void saveXml(PortalPageEditor portalPageEditor, String callBack,String xml) throws UnsupportedEncodingException;
	
}
