package nc.uap.lfw.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import nc.vo.jcom.lang.StringUtil;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


/**
 * XML DOM ��ʽ����������
 * 
 */
public class XmlUtility {
    private static class XmlPrinter {
        private static boolean lastIsAString=false;
    /**
     * ��������ո����ĸ��ո�Ϊ��λ
     *  
     * @param space
     * @return
     * ����ʱ�䣺2004-10-16 16:09:21
     */
    private static String getSpace(int space) {
        char ca[] = new char[space * 4];
        for(int i = 0; i< ca.length; i += 4)
        {
            ca[i]  = ' ';
            ca[i+1]= ' ';
            ca[i+2]= ' ';
            ca[i+3]= ' ';
        }
        return new String(ca);
    }
    
    public static void printDOMTree(PrintWriter pw,Node node, int deepSet) {
        XmlPrinter.printDOMTree( pw,  node, deepSet, "gb2312");
        
    }
    /**
     * ��xml�ڵ㰴��xml���ļ���ʽ����ķ���
     *  
     * @param pw �����
     * @param node  xml�ڵ�
     * @param deepSet �ڵ㼶�Σ�һ��Ϊ0��
     * ����ʱ�䣺2004-10-16 16:10:07
     */
	public static void printDOMTree(Writer writer, Node node, int deepSet,String encoding) 
	{
		int type = node.getNodeType();
		PrintWriter pw = null;
		if (writer instanceof OutputStreamWriter) 
		{
			pw = new PrintWriter(writer);
		} else if (writer instanceof PrintWriter) {
			pw = (PrintWriter) writer;
		} else {
			throw new IllegalArgumentException("Illegal writer to print dom tree.");
		}
		switch (type) {
			// print the document element
			case Node.DOCUMENT_NODE :
				{
					pw.print("<?xml version=\"1.0\" encoding='" + encoding + "'?>");
					/**root�ڵ�ǰ�п�����comment,ֻ����һ��-BY CCH 2006-01-17*/
					Node preComment = ((Document) node).getDocumentElement().getPreviousSibling();
					if(preComment!=null)
					{
						printDOMTree(pw, preComment, deepSet);
					}
					printDOMTree(pw, ((Document) node).getDocumentElement(), deepSet);
					pw.println();
					break;
				}
				// print element with attributes
			case Node.ELEMENT_NODE :
				{
					pw.println();
					pw.print(getSpace(deepSet) + "<");
					pw.print(node.getNodeName());
					NamedNodeMap attrs = node.getAttributes();
					for (int i = 0; i < attrs.getLength(); i++) {
						Node attr = attrs.item(i);
						pw.print(" " + attr.getNodeName() + "=\"" + XmlUtility.getXMLString(attr.getNodeValue()) + "\"");
						if (null == attr.getNodeValue()
							|| attr.getNodeValue().equals("null"))
							lastIsAString = true;
					}
					pw.print(">");
					NodeList children = node.getChildNodes();
					if (children != null) {
						int len = children.getLength();
						for (int i = 0; i < len; i++)
							printDOMTree(pw, children.item(i), deepSet+1);
					}
					break;
				}
				// handle entity reference nodes
			case Node.ENTITY_REFERENCE_NODE :
				{
					pw.print("&");
					pw.print(node.getNodeName());
					pw.print(";");
					break;
				}
				// print cdata sections
			case Node.CDATA_SECTION_NODE :
				{
					pw.print(getSpace(deepSet) + "<![CDATA[");
					pw.print(node.getNodeValue());
					pw.print("]]>");
					break;
				}
				// print text
			case Node.TEXT_NODE :
				{//
				 String value = node.getNodeValue();
				 //.trim();
				 if(value != null){
				 	value = value.trim();
				 	value = getXMLString(value);
				    pw.print(value);
				 }
			    lastIsAString = !"".equals(value);
				//
				    
//					pw.print(node.getNodeValue());
//					lastIsAString = true;
					break;
				}
				// print processing instruction
			case Node.PROCESSING_INSTRUCTION_NODE :
				{
					pw.print(getSpace(deepSet) + "<?");
					pw.print(node.getNodeName());
					String data = node.getNodeValue();
					{
						pw.print("");
						pw.print(data);
					}
					pw.print("?>");
					break;
				}
			case Node.COMMENT_NODE:
				{
				   pw.println();	
				   pw.print(getSpace(deepSet)+"<!--");
				   pw.print(node.getNodeValue()+"-->");
				   break;
				}
		}
		if (type == Node.ELEMENT_NODE) {
			if (!lastIsAString) {
				pw.println();
				pw.print(getSpace(deepSet) + "</");
			} else
				pw.print("</");
			pw.print(node.getNodeName());
			pw.print('>');
			lastIsAString = false;
		}
	}
    }
    
    private XmlUtility() {

    }

    /**
     * ��XML��ʽ���DOM��
     *
     * @param pw �����
     * @param node ������ node
     * @param deepSet ������ȣ�0 ������
     */
    public static void printDOMTree(PrintWriter pw, Node node, int deepSet) {
        XmlPrinter.printDOMTree(pw, node, deepSet);
    }
    
    /**
     * ����XML��ʽ�����д���������ȥ
     * ��������:(2001-4-14 20:09:18)
     * @param fileBuffer StringBuffer
     * @param node org.w3c.dom.Node
     * @param depth int ����������
     */
    public static  void writeXMLFormatString(
    		StringBuffer fileBuffer,
    		Node node,
    		int depth) {
    	OutputStream out = new ByteArrayOutputStream();
    	Writer writer = new OutputStreamWriter(out);
    	printDOMTree(writer,node,depth,"UTF-8");
    	try{
			writer.close();
		} 
    	catch (IOException e){
		}
		fileBuffer.append(out.toString());
    }
    
    
    public static DocumentBuilder getDocumentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilderFactory dbf = DocumentBuilderFactoryImpl.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        try {
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("XML����������ʧ��!");
        }
    }
    
    
    /**
     * ����һ�����ĵ�
     * @return
     */
    public static Document getNewDocument() {
        return getDocumentBuilder().newDocument();
    }

    /**
     * �õ�����node����node
     *
     * @param node ������node
     * @param tagName Ҫȡ�õ���node����
     * @return ��node
     */
    public static Node getChildNodeOf(Node node, String tagName) {
        for(Node temp = node.getFirstChild(); temp != null; temp = temp.getNextSibling())
            if(temp.getNodeType() == Node.ELEMENT_NODE
                    && tagName.equals(temp.getNodeName())) {
                return temp;
            }
        return null;
    }

    
    /**
     * ȡ��ָ���ӽڵ��ֵ
     * @param node
     * @param tagName
     * @return
     */
    public static String getChildNodeValueOf(Node node, String tagName) {
        if(tagName.equals(node.getNodeName())) {
            return getValueOf(node);
        }
        for(Node temp = node.getFirstChild(); temp != null; temp = temp.getNextSibling()) {
            if(temp.getNodeType() == Node.ELEMENT_NODE && tagName.equals(temp.getNodeName())) {
                return getValueOf(temp);
            }
        }
        return null;
    }

    public static final String getValueOf(Node node) {
        if(node == null) {
            return null;
        }
        else if(node instanceof Text) {
            return node.getNodeValue().trim();
        }
        else if(node instanceof Element) {
            ((Element) node).normalize();
            Node temp = node.getFirstChild();
            if(temp != null && (temp instanceof Text))
                return temp.getNodeValue().trim();
            else
                return "";
        }
        else {
            return node.getNodeValue().trim();
        }
    }

    public static final String getAtrributeValueOf(Node node, String attribute) {
        Node _node = node.getAttributes().getNamedItem(attribute);
        return getValueOf(_node);
    }

    @SuppressWarnings("unchecked")
	public static Iterator getElementsByTagName(Element element, String tag) {
        ArrayList<Element> children = new ArrayList<Element>();
        if(element != null && tag != null) {
            NodeList nodes = element.getElementsByTagName(tag);
            for(int i = 0; i < nodes.getLength(); i++) {
                Node child = nodes.item(i);
//      System.out.println("Name: " + child.getNodeName() + ", Value: " + child.getFirstChild().getNodeValue());
                children.add((Element) child);
            }
        }
        return children.iterator();
    }

    @SuppressWarnings("unchecked")
	public static Iterator getElementsByTagNames(Element element, String[] tags) {
        List<Element> children = new ArrayList<Element>();
        if(element != null && tags != null) {
            List tagList = Arrays.asList(tags);
            NodeList nodes = element.getChildNodes();
            for(int i = 0; i < nodes.getLength(); i++) {
                Node child = nodes.item(i);
                if(child.getNodeType() == Node.ELEMENT_NODE
                        && tagList.contains(((Element) child).getTagName())) {
                    children.add((Element) child);
                }
            }
        }
        return children.iterator();
    }

    /**
     * get the xml root document of the xml descriptor
     *
     * @param url the xml descriptor url
     * @return
     */
    public static Document getDocument(URL url) throws Exception {
        InputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            return getDocumentBuilder().parse(is);
        }
        finally {
            if(is != null) {
                try {
                    is.close();
                }
                catch(IOException e) {

                }
            }
        }
    }

    public static Document getDocument(File file) throws Exception {
        InputStream is = null;
        try {
            return getDocumentBuilder().parse(file);
        }
        finally {
            if(is != null) {
                try {
                    is.close();
                }
                catch(IOException e) {

                }
            }
        }
    }

    public static Document getDocument(String file) throws Exception {
        InputStream is = null;
        try {
            return getDocumentBuilder().parse(file);
        }
        finally {
            if(is != null) {
                try {
                    is.close();
                }
                catch(IOException e) {

                }
            }
        }
    }

    /**
     * ע��Դ�ڵ㼰���ӽڵ�����Ϊ�ӽڵ����Ŀ��ڵ��ϣ�����src��dest����ͬ����<br>
     * ʹ�ô˷���ʱ���ע��(By cch)
     * Copies the source tree into the specified place in a destination
     * tree. The source node and its children are appended as children
     * of the destination node.
     * <p>
     * <em>Note:</em> This is an iterative implementation.
     */
    @SuppressWarnings("null")
	public static void copyInto(Node src, Node dest) throws DOMException {

        Document factory = dest.getOwnerDocument();

        //Node start = src;
        Node parent = null;
        Node place = src;

        // traverse source tree
        while (place != null) {

            // copy this node
            Node node = null;
            int type = place.getNodeType();
            switch (type) {
            case Node.CDATA_SECTION_NODE: {
                node = factory.createCDATASection(place.getNodeValue());
                break;
            }
            case Node.COMMENT_NODE: {
                node = factory.createComment(place.getNodeValue());
                break;
            }
            case Node.ELEMENT_NODE: {
                Element element = factory.createElement(place.getNodeName());
                node = element;
                NamedNodeMap attrs = place.getAttributes();
                int attrCount = attrs.getLength();
                for (int i = 0; i < attrCount; i++) {
                    Attr attr = (Attr) attrs.item(i);
                    String attrName = attr.getNodeName();
                    String attrValue = attr.getNodeValue();
                    element.setAttribute(attrName, attrValue);
/*
                    if (domimpl && !attr.getSpecified()) {
                        ((Attr) element.getAttributeNode(attrName)).setSpecified(false);
                    }
*/
                }
                break;
            }
            case Node.ENTITY_REFERENCE_NODE: {
                node = factory.createEntityReference(place.getNodeName());
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                node = factory.createProcessingInstruction(place.getNodeName(),
                        place.getNodeValue());
                break;
            }
            case Node.TEXT_NODE: {
                node = factory.createTextNode(place.getNodeValue());
                break;
            }
            default: {
                throw new IllegalArgumentException("can't copy node type, " +
                        type + " (" +
                        node.getNodeName() + ')');
            }
            }
            dest.appendChild(node);
            
            // iterate over children
            if (place.hasChildNodes()) {
            	parent = place;
            	place = place.getFirstChild();
            	dest = node;
            }
            else if(parent == null)
            {
            	place = null;
            }
            else
            {
	            // advance
            	place = place.getNextSibling();
                while (place == null && parent!=null && dest!=null) {
                    place = parent.getNextSibling();
                    parent = parent.getParentNode();
                    dest = dest.getParentNode();
                }
            }

        }

    } // copyInto(Node,Node)
    /** Finds and returns the first child element node. */
    public static Element getFirstChildElement(Node parent) {

    	if(parent == null) return null;
        // search for node
        Node child = parent.getFirstChild();
        while (child != null) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) child;
            }
            child = child.getNextSibling();
        }

        // not found
        return null;

    } // getFirstChildElement(Node):Element

    /** Finds and returns the last child element node. */
    public static Element getLastChildElement(Node parent) {

    	if(parent == null) return null;
        // search for node
        Node child = parent.getLastChild();
        while (child != null) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) child;
            }
            child = child.getPreviousSibling();
        }

        // not found
        return null;

    } // getLastChildElement(Node):Element

    /** Finds and returns the next sibling element node. */
    public static Element getNextSiblingElement(Node node) {

    	if(node == null) return null;
        // search for node
        Node sibling = node.getNextSibling();
        while (sibling != null) {
            if (sibling.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) sibling;
            }
            sibling = sibling.getNextSibling();
        }

        // not found
        return null;

    } // getNextSiblingElement(Node):Element


    /** Finds and returns the first child node with the given name. */
    public static Element getFirstChildElement(Node parent, String elemName) {

    	if(parent == null) return null;
        // search for node
        Node child = parent.getFirstChild();
        while (child != null) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName().equals(elemName)) {
                    return (Element) child;
                }
            }
            child = child.getNextSibling();
        }

        // not found
        return null;

    } // getFirstChildElement(Node,String):Element

    /** Finds and returns the last child node with the given name. */
    public static Element getLastChildElement(Node parent, String elemName) {

    	if(parent == null) return null;
        // search for node
        Node child = parent.getLastChild();
        while (child != null) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName().equals(elemName)) {
                    return (Element) child;
                }
            }
            child = child.getPreviousSibling();
        }

        // not found
        return null;

    } // getLastChildElement(Node,String):Element

    /** Finds and returns the next sibling node with the given name. */
    public static Element getNextSiblingElement(Node node, String elemName) {

    	if(node == null) return null;
        // search for node
        Node sibling = node.getNextSibling();
        while (sibling != null) {
            if (sibling.getNodeType() == Node.ELEMENT_NODE) {
                if (sibling.getNodeName().equals(elemName)) {
                    return (Element) sibling;
                }
            }
            sibling = sibling.getNextSibling();
        }

        // not found
        return null;

    } // getNextSiblingdElement(Node,String):Element

    /**
     * Returns the concatenated child text of the specified node.
     * This method only looks at the immediate children of type
     * <code>Node.TEXT_NODE</code> or the children of any child
     * node that is of type <code>Node.CDATA_SECTION_NODE</code>
     * for the concatenation.
     *
     * @param node The node to look at.
     */
    public static String getChildText(Node node) {

        // is there anything to do?
        if (node == null) {
            return null;
        }

        // concatenate children text
        StringBuffer str = new StringBuffer();
        Node child = node.getFirstChild();
        while (child != null) {
            short type = child.getNodeType();
            if (type == Node.TEXT_NODE) {
                str.append(child.getNodeValue());
            } else if (type == Node.CDATA_SECTION_NODE) {
                str.append(getChildText(child));
            }
            child = child.getNextSibling();
        }

        // return text value
        return str.toString();

    } // getChildText(Node):String

    /**
     * ��ȡ��Ԫ��Element���ı�ֵ,���û���ı��ڵ�,����null
     *(ע���getChildText����������)
     * @param ele The node to look at.
     */
    public static String getElementText(Element ele) {

        // is there anything to do?
        if (ele == null) {
            return null;
        }
        // get children text
        Node child = ele.getFirstChild();
        if (child != null) {
            short type = child.getNodeType();
            if (type == Node.TEXT_NODE) {
                return child.getNodeValue();
            }
        }
        // return text value
        return null;
    } // getChildText(Node):String


    public static String getFirstChildElementText(Node node) {
        return getElementText(getFirstChildElement(node));
    }

    public static String getLastChildElementText(Node node) {
        return getElementText(getLastChildElement(node));
    }

    public static String getNextSiblingElementText(Node node) {
        return getElementText(getNextSiblingElement(node));
    }

    public static String getFirstChildElementText(Node node, String elemName) {
        return getElementText(getFirstChildElement(node, elemName));
    }

    public static String getLastChildElementText(Node node, String elemName) {
        return getElementText(getLastChildElement(node, elemName));
    }

    public static String getNextSiblingElementText(Node node, String elemName) {
        return getElementText(getNextSiblingElement(node, elemName));
    }

    /**
     * ����Ҷ��Ԫ�أ�ע�ⴴ����Ԫ�ز����Զ���ӵ�doc
     * @param doc �����ĵ�
     * @param eleName Ԫ����
     * @param text Ԫ��ֵ
     * @return
     */
    public static Element createLeafElement(Document doc, String eleName, String text) {
        Element ele = doc.createElement(eleName);
        if(text!=null){
           ele.appendChild(doc.createTextNode(text));
        }
        return ele;
    }

    /**
     * ������Ԫ��
     * @param element
     * @param child_ele_name
     * @param text
     */
    public static void addChildElement(Element element, String child_ele_name, String text) {
        Document doc = element.getOwnerDocument();
        Element sub_element = createLeafElement(doc, child_ele_name, text);
        element.appendChild(sub_element);
    }
    
    /**
     * ����ָ���ĵ���ĳ������������еĵ� index ��Ԫ�ء�
     * �������ڣ�(01-4-23 14:27:11)
     * @return org.w3c.dom.Element
     * @param doc org.w3c.dom.Document
     * @param tagName java.lang.String
     * @param index int
     */
    public static Element getElement(Document doc, String tagName, int index) {
     NodeList rows = doc.getDocumentElement().getElementsByTagName(tagName);
     return (Element) rows.item(index);
    }

    /**
     * �����µĿհ�XML�ĵ�
     * @return
     * @throws ParserConfigurationException
     */
    public static Document newDocument() throws ParserConfigurationException {  
        return  getDocumentBuilder().newDocument();
    }

    /**
     * ��XML�ļ�д��writerָ����Ŀ�ĵ�  <br>
     * ���磺<br><code>
     * 	File c = new File("c:/aa.xml");
     *  Writer wr = null;
     *  try
     *  {
     *      wr = new OutputStreamWriter(new FileOutputStream(c),"UTF-8");
     *      XMLUtil.printDOMTree(wr,doc,0,"UTF-8");
     *  }
     *  catch (IOException e)
	 *  {
	 *  	e.printStackTrace();
	 *  }
	 *  finally
	 *  {
	 *    	try
	 *  	{
	 *  		wr.flush();
	 *  		wr.close();
	 *  	} catch (IOException e){}
	 *  } 
	 *  </code><br>
     * <b>��ע��<b>������������Ҫָ�����룬Ϊ���ֶԸ����ַ���֧�֣�����ʹ��UTF-8. <br>
     * 
     * @param pw
     * @param node  
     * @param deepSet һ��Ϊ0����
     * @param encoding XML�ļ�ͷ�ı���,��<?xml version="1.0" encoding='UTF-8'?>
     */
    public static void printDOMTree(Writer pw, Node node, int deepSet, String encoding) {
        XmlUtility.XmlPrinter.printDOMTree(new PrintWriter(pw), node, deepSet,encoding);
    }
    
    /**
     * ת��XML�ļ��е������ַ����������ַ�<br>
     * ���磺�� "a&ltb" ת��Ϊ "a<b"
     * @since V1.00
     * @return java.lang.String ת����ϵ��ַ���
     * @param value java.lang.String ��ת�����ַ���
     */
    public static String getRegularString(String value) {
    	if (value != null) {
    		value = value.toString().trim();
    		//��ΪXML������ֵ���ܰ����ַ����̶ֹ���ʵ�����õ��ַ�
    		value = StringUtility.replaceAllString(value,  "&lt;","<");
    		value = StringUtility.replaceAllString(value, "&gt;",">");
    		value = StringUtility.replaceAllString(value,  "&quot;","\"");
    		value = StringUtility.replaceAllString(value,  "&apos;","\'");
    		value = StringUtility.replaceAllString(value, "&amp;","&");		
    	}
    	return value;
    }
    
    /**
     * �������ַ����е������ַ�ת��Ϊ��Ӧ��ת���ַ�<br>
     * ���磺��"a<b" ת��Ϊ "a&ltb"
     * @since V1.00
     * @return java.lang.String
     * @param value java.lang.String
     */
    public static String getXMLString(String value) {
    	if (value != null) {
    		value = value.toString().trim();
    		//��ΪXML������ֵ���ܰ����ַ����̶ֹ���ʵ�����õ��ַ�
    		value = StringUtility.replaceAllString(value, "&", "&amp;");
    		value = StringUtility.replaceAllString(value, "<", "&lt;");
    		value = StringUtility.replaceAllString(value, "<", "&lt;");
    		value = StringUtility.replaceAllString(value, ">", "&gt;");
    		value = StringUtility.replaceAllString(value, "\"", "&quot;");
    		value = StringUtility.replaceAllString(value, "\'", "&apos;");
    	}
    	return value;
    }
    
    /**
     * ȡ�ýڵ�node�����е���Ԫ��(Ƕ�ײ��ң�������������,��TextԪ��)
     * @param node
     * @return
     */
    public static List<Node> getAllChildNodes(Node node)
    {
    	if(node == null) return null;
    	List<Node> result = new ArrayList<Node>();
    	NodeList nodelist = node.getChildNodes();
    	for(int i=0;i<nodelist.getLength();i++)
    	{
    		Node curnode = nodelist.item(i);
    		int type = curnode.getNodeType();
    		if(type != Node.TEXT_NODE)
    			result.add(nodelist.item(i));
    		List<Node> childlist = getAllChildNodes(curnode);
    		if(childlist!=null)
    			result.addAll(childlist);
    	}
    	return result;
    }
	/**
	 * ��parentNode�µ�������nodeȫ�����ӵ�destNode
	 * �����destNode�µ�TextContent
	 * @author Larrylau
	 * @param destNode
	 * @param parentNode
	 */
	public static Node appendChildNodes(Node destNode, Node parentNode)
	{
		if(destNode != null && parentNode != null)
		{
			destNode.setTextContent("");
			NodeList list = parentNode.getChildNodes();
			if (list != null)
			{
				for (int i = 0; i < list.getLength(); i++)
				{
					Node node = list.item(i);
					if (node != null)
					{
						copyInto(node, destNode);
					}
				}
			}
		}
		return destNode;
	}
	/**
	 * ����xmlƬ�ϣ�����һ���µ�document
	 * @author Larrylau
	 * @param xmlSnippet
	 * @return
	 * @throws PfxxException
	 * @throws SAXException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static Document rebuildDocument(String xmlSnippet) throws SAXException, IOException, UnsupportedEncodingException
	{
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\'UTF-8\'?><root>");
		buffer.append(xmlSnippet);
		buffer.append("</root>");
		Document tmpDoc = getDocumentBuilder().parse(new ByteArrayInputStream(buffer.toString().getBytes("UTF-8")));
		return tmpDoc;
	}
	/**
	 * ɾ��Node�µ�������Node
	 * @author Larrylau
	 * @param parentNode
	 */
	public static Node removeChildren(Node parentNode)
	{
		if(parentNode != null)
		{
			NodeList list = parentNode.getChildNodes();
			if (list != null)
			{
				for (int i = 0; i < list.getLength(); i++)
				{
					Node node = list.item(i);
					if (node != null)
					{
						parentNode.removeChild(node);
					}
				}
			}
		}
		return parentNode;
	}
	/**
	 * ����ǰNode������Contents�����һ��StringBuffer
	 * @author Larrylau
	 * @param parentNode
	 * @return
	 */
	public static StringBuffer getContentsOfNode(Node parentNode)
	{
		StringBuffer stringBuffer = new StringBuffer();
		if(parentNode != null)
		{
			NodeList list = parentNode.getChildNodes();
			if (list != null)
			{
				for (int i = 0; i < list.getLength(); i++)
				{
					Node node = list.item(i);
					if (node != null)
					{
						writeXMLFormatString(stringBuffer, node, 0);
					}
				}
			}
		}
		return stringBuffer;
	}
}

