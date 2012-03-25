package nc.uap.lfw.perspective.model;

import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.common.StringDataTypeConst;

/**
 * 定义常量
 * @author zhangxya
 *
 */
public interface Constant {
	public static final String DEFAULTNODE = "节点组";
	public static final String DEFAULTREFNODE ="公共参照";
	public static final String VISIBILITY_PUBLIC="public";
	public static final String VISIBILITY_PROTECTED="protected";
	public static final String VISIBILITY_DEFAULT="default";
	public static final String VISIBILITY_PRIVATE="private";
	public static String[] VISIBILITYS={VISIBILITY_PUBLIC,VISIBILITY_PROTECTED,VISIBILITY_DEFAULT,VISIBILITY_PRIVATE};
	public static String[] HOTKEYMODIFIERS ={"SHIFT","CTRL","ALT","CTRL+SHIFT","CTRL+ALT", "ALT+SHIFT", "CTRL+SHIFT+ALT"};
	public static String[] ISLAZY={"是","否"};
	public static String[] LABELPOSITION={"left", "right"};
	public static String[] CHECKBOXTYPE ={StringDataTypeConst.bOOLEAN, StringDataTypeConst.BOOLEAN,StringDataTypeConst.UFBOOLEAN};
	public static String[] ISVISIBLE={"Y","N"};
	public static String[] ISFIXHEADER={"Y","N"};
	public static String[] ISEDITABLE={"Y","N"};
	public static String[] ISIMAGEONLY={"Y","N"};
	public static String[] ISNULLBALE={"Y","N"};
	public static String[] ISPRIMARYKEY={"Y","N"};
	public static String[] ISLOCK={"Y","N"};
	public static String[] SELECTONLY={"Y","N"};	
	public static String[] ISNEXTLINE={"Y","N"};	
	public static String[] ISSUMCOL={"Y","N"};
	public static String[] ISAUTOEXPEND={"Y","N"};
	public static String[] TEXTALIGN={"center","left","right"};
	public static String[] ISSORTALBE={"Y","N"};
	public static String[] 	ISFARMEBORDER ={"否", "是"};
	public static String[] REFTYPE={"NC参照","自定义参照"};
//	public static String[] POOLREFTYPE={"自定义参照","参数参照"};
	public static String[] PARAMREFNODETYPE = {"表格型","树型","树表型"};
	public static String[] SCROLLING = {"显示", "不显示", "自动"};
	public static String[] ISMAIN={"是","否"};
	public static String[] ISDIALOG={"是","否"};
	public static String[] PLUGOUTTYPE={"normal","widget"};
	public static String[] PLUGINTYPE={"mapping","event"};
	public static String[] ISYES_NO={"是","否"};
	public static String[] CHECKBOXMODEL = {"只设置自己 ","设置自己和子","设置自己和子和父"};
	
	public static String[] TEXTTYPE={EditorTypeConst.STRINGTEXT,EditorTypeConst.INTEGERTEXT, EditorTypeConst.CHECKBOX
		,EditorTypeConst.DECIMALTEXT, EditorTypeConst.PWDTEXT, EditorTypeConst.DATETEXT,EditorTypeConst.FILEUPLOAD,EditorTypeConst.RADIOGROUP,
		EditorTypeConst.REFERENCE, EditorTypeConst.COMBODATA,EditorTypeConst.TEXTAREA,EditorTypeConst.RICHEDITOR, EditorTypeConst.CHECKBOXGROUP,
		EditorTypeConst.RADIOCOMP, EditorTypeConst.SELFDEF, EditorTypeConst.DATETIMETEXT, EditorTypeConst.FILECOMP, EditorTypeConst.LIST};
	
	public static String[] DATATYPE={StringDataTypeConst.STRING,StringDataTypeConst.INTEGER,StringDataTypeConst.DOUBLE,StringDataTypeConst.dOUBLE
		,StringDataTypeConst.UFDOUBLE,StringDataTypeConst.FLOATE,StringDataTypeConst.fLOATE,StringDataTypeConst.bYTE,StringDataTypeConst.BYTE
		,StringDataTypeConst.bOOLEAN,StringDataTypeConst.BOOLEAN,StringDataTypeConst.UFBOOLEAN,
		StringDataTypeConst.DATE, StringDataTypeConst.UFDATETIME, StringDataTypeConst.BIGDECIMAL,StringDataTypeConst.lONG,StringDataTypeConst.lONG,
		StringDataTypeConst.UFDATE,StringDataTypeConst.UFTIME,StringDataTypeConst.CHAR,StringDataTypeConst.UFNUMBERFORMAT,
		StringDataTypeConst.Decimal, StringDataTypeConst.ENTITY, StringDataTypeConst.OBJECT
		,StringDataTypeConst.CUSTOM,StringDataTypeConst.MEMO, StringDataTypeConst.UFLiteralDate, StringDataTypeConst.UFDate_begin, StringDataTypeConst.UFDate_end};
	//校验类型
	public static String[] FORMATERTYPE = {"email","number","chn", "variable", "date", "phone"};
}

