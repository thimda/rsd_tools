package nc.uap.lfw.button;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.WebElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * button model
 * @author zhangxya
 *
 */
public class ButtonElementObj extends LFWWebComponentObj{

	private static final long serialVersionUID = 6253081418703115641L;
	private ButtonComp buttonComp;
	
	public static final String PROP_TEXT = "element_Text";
	public static final String PROP_TIP = "element_TIP";
	public static final String PROP_I18NNAME = "element_I118NNAME";
	public static final String PROP_REFIMG = "element_REFIMG";
//	public static final String PROP_ALIGN = "element_ALIGN";
	public static final String PROP_LANGDIR = "element_LANGDIR";
	public static final String PROP_TIPI18NNAME= "element_TIPI18NNAME";
	public static final String PROP_BUTTON_ELEMENT ="button_element";
	public static final String PROP_BUTTON_HOTKEY ="button_HOTKEY";
	public static final String PROP_DISPLAY_HOTKEY ="button_DISPLAYHOTKEY";
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[8];
		pds[0] = new TextPropertyDescriptor(PROP_TEXT, "显示值");
		pds[0].setCategory("高级");
		pds[1] = new TextPropertyDescriptor(PROP_TIP,"提示");
		pds[1].setCategory("高级");
		pds[2] = new TextPropertyDescriptor(PROP_I18NNAME,"多语显示值");
		pds[2].setCategory("高级");
		pds[3] = new TextPropertyDescriptor(PROP_REFIMG,"引用图形");
		pds[3].setCategory("高级");
//		pds[4] = new TextPropertyDescriptor(PROP_ALIGN,"位置");
//		pds[4].setCategory("高级");
		pds[4] = new TextPropertyDescriptor(PROP_LANGDIR, "多语目录");
		pds[4].setCategory("高级");
		pds[5] = new TextPropertyDescriptor(PROP_TIPI18NNAME, "多语提示");
		pds[5].setCategory("高级");
		
		pds[6] = new HotKeySetPropertyDescriptor(PROP_BUTTON_HOTKEY, "热键");
		pds[6].setCategory("高级");
		
		pds[7] = new NoEditableTextPropertyDescriptor(PROP_DISPLAY_HOTKEY, "热键显示值");
		pds[7].setCategory("高级");
		
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_TEXT.equals(id))
			buttonComp.setText((String)value);
		else if(PROP_TIP.equals(id)){
			buttonComp.setTip((String)value);
		}
		else if(PROP_I18NNAME.equals(id)){
			buttonComp.setI18nName((String)value);		
		}
		else if(PROP_REFIMG.equals(id)){
			buttonComp.setRefImg((String)value);
		}
//		else if(PROP_ALIGN.equals(id)){
//			buttonComp.setAlign((String)value);
//		}
		else if(PROP_LANGDIR.equals(id))
			buttonComp.setLangDir((String)value);
		else if(PROP_TIPI18NNAME.equals(id))
			buttonComp.setTipI18nName((String)value);
		else if(PROP_BUTTON_HOTKEY.equals(id)){
			String text = (String) value;
			buttonComp.setHotKey(text.substring(text.lastIndexOf("+") + 1));
			buttonComp.setDisplayHotKey((String)value);
		}
		else if(PROP_DISPLAY_HOTKEY.equals(id))
			buttonComp.setDisplayHotKey((String)id);
		
	}
	public Object getPropertyValue(Object id) {
		if(PROP_TEXT.equals(id))
			return buttonComp.getText() == null?"":buttonComp.getText();
		else if(PROP_TIP.equals(id))
			return buttonComp.getTip() == null?"":buttonComp.getTip();
		else if(PROP_I18NNAME.equals(id))
			return buttonComp.getI18nName() == null?"":buttonComp.getI18nName();
		else if(PROP_REFIMG.equals(id))
			return buttonComp.getRefImg() == null?"":buttonComp.getRefImg();
//		else if(PROP_ALIGN.equals(id))
//			return buttonComp.getAlign() == null?"":buttonComp.getAlign();
		else if(PROP_LANGDIR.equals(id))
			return buttonComp.getLangDir() == null?"":buttonComp.getLangDir();
		else if(PROP_TIPI18NNAME.equals(id))
			return buttonComp.getTipI18nName() == null?"":buttonComp.getTipI18nName();
		else if(PROP_BUTTON_HOTKEY.equals(id))
			return buttonComp.getHotKey() == null?"":buttonComp.getHotKey();
		else if(PROP_DISPLAY_HOTKEY.equals(id))
			return buttonComp.getDisplayHotKey() == null?"":buttonComp.getDisplayHotKey();
		else return super.getPropertyValue(id);
	}
	
	public WebElement getWebElement() {
		return buttonComp;
	}

	public ButtonComp getButtonComp() {
		return buttonComp;
	}
	
	public void setButtonComp(ButtonComp buttonComp) {
		this.buttonComp = buttonComp;
		fireStructureChange(PROP_BUTTON_ELEMENT, buttonComp);
	}
}
