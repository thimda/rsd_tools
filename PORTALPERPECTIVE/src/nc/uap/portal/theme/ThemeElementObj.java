package nc.uap.portal.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.uap.portal.core.PortalElementObjWithGraph;
import nc.uap.portal.om.LookAndFeel;
import nc.uap.portal.om.Theme;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * LookAndFeel Model
 * 
 * @author dingrf
 */
public class ThemeElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	public static final String PROP_LOOKANDFEEL_ELEMENT = "lookandfeel_element";
	
	/**theme id*/
	public static final String PROP_ID = "id";
	
	/**theme title*/
	public static final String PROP_TITLE = "title";
	
	/**theme i18nName*/
	public static final String PROP_I18NNAME = "i18nName";
	
	/**LFW框架主题ID*/
	public static final String PROP_LFW_THEME_ID = "lfw_theme_id";
	
	private ThemeEleObjFigure fingure;
	
	private LookAndFeel lookAndFeel;

	private List<Theme>  themes;

	/**current Theme*/      
	private Theme  currentTheme;


	public ThemeEleObjFigure getFingure() {
		return fingure;
	}

	public void setFingure(ThemeEleObjFigure fingure) {
		this.fingure = fingure;
	}

	public LookAndFeel getLookAndFeel() {
		return lookAndFeel;
	}

	public void setLookAndFeel(LookAndFeel lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public Theme getCurrentTheme() {
		return currentTheme;
	}

	public void setCurrentTheme(Theme currentTheme) {
		this.currentTheme = currentTheme;
	}

	/**
	 * 属性数组
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[4];
		pds[0] = new TextPropertyDescriptor(PROP_ID, "id");
		pds[0].setCategory("基本");
		pds[1] = new TextPropertyDescriptor(PROP_TITLE,"title");
		pds[1].setCategory("基本");
		pds[2] = new TextPropertyDescriptor(PROP_I18NNAME, "i18nName");
		pds[2].setCategory("基本");
		pds[3] = new TextPropertyDescriptor(PROP_LFW_THEME_ID, "lfwThemeId");
		pds[3].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if (currentTheme==null)
			return;
		if(PROP_ID.equals(id)){
			currentTheme.setId((String)value);
		}
		else if(PROP_TITLE.equals(id)){
			currentTheme.setTitle((String)value);
			fingure.getCurrentLabel().setText((String)value);
		}
		else if(PROP_I18NNAME.equals(id)){
			currentTheme.setI18nName((String)value);
		}
		else if(PROP_LFW_THEME_ID.equals(id)){
			currentTheme.setLfwThemeId((String)value);
		}
	}

	public Object getPropertyValue(Object id) {
		if (currentTheme!=null){
			if(PROP_ID.equals(id))
				return currentTheme.getId()==null?"":currentTheme.getId();
			else if(PROP_TITLE.equals(id))
				return currentTheme.getTitle()==null?"":currentTheme.getTitle();
			else if(PROP_I18NNAME.equals(id))
				return currentTheme.getI18nName()==null?"":currentTheme.getI18nName();
			else if(PROP_LFW_THEME_ID.equals(id))
				return currentTheme.getLfwThemeId()==null?"":currentTheme.getLfwThemeId();
			else return super.getPropertyValue(id);
		}
		else return super.getPropertyValue(id);
	}
}
