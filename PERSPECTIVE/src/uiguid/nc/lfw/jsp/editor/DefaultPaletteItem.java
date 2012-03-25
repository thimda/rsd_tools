package nc.lfw.jsp.editor;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * A default implementation of IPaletteItem.
 * This palette item inserts simple text.
 */
public class DefaultPaletteItem implements IPaletteItem {
	
	private String name;
	private ImageDescriptor image;
	private String content;
	
	/**
	 * The constructor.
	 * 
	 * @param name     item name
	 * @param image    icon
	 * @param content  insert text
	 */
	public DefaultPaletteItem(String name,ImageDescriptor image,String content){
		this.name    = name;
		this.image   = image;
		this.content = content;
	}
	
	public ImageDescriptor getImageDescriptor() {
		return image;
	}
	
	public String getLabel() {
		return name;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void execute(){
//		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
//		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
//		try {
//			int caret = content.length();
//			if(content.indexOf("></")!=-1){
//				caret = content.indexOf("></") + 1;
//			}
//			doc.replace(sel.getOffset(),sel.getLength(),content);
//			editor.selectAndReveal(sel.getOffset() + caret, 0);
//		} catch(Exception ex){
//			HTMLPlugin.logException(ex);
//		}
	}
}
