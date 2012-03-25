package nc.uap.lfw.perspective.model;

import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.common.StringDataTypeConst;

/**
 * ���峣��
 * @author zhangxya
 *
 */
public interface Constant {
	public static final String DEFAULTNODE = "�ڵ���";
	public static final String DEFAULTREFNODE ="��������";
	public static final String VISIBILITY_PUBLIC="public";
	public static final String VISIBILITY_PROTECTED="protected";
	public static final String VISIBILITY_DEFAULT="default";
	public static final String VISIBILITY_PRIVATE="private";
	public static String[] VISIBILITYS={VISIBILITY_PUBLIC,VISIBILITY_PROTECTED,VISIBILITY_DEFAULT,VISIBILITY_PRIVATE};
	public static String[] HOTKEYMODIFIERS ={"SHIFT","CTRL","ALT","CTRL+SHIFT","CTRL+ALT", "ALT+SHIFT", "CTRL+SHIFT+ALT"};
	public static String[] ISLAZY={"��","��"};
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
	public static String[] 	ISFARMEBORDER ={"��", "��"};
	public static String[] REFTYPE={"NC����","�Զ������"};
//	public static String[] POOLREFTYPE={"�Զ������","��������"};
	public static String[] PARAMREFNODETYPE = {"�����","����","������"};
	public static String[] SCROLLING = {"��ʾ", "����ʾ", "�Զ�"};
	public static String[] ISMAIN={"��","��"};
	public static String[] ISDIALOG={"��","��"};
	public static String[] PLUGOUTTYPE={"normal","widget"};
	public static String[] PLUGINTYPE={"mapping","event"};
	public static String[] ISYES_NO={"��","��"};
	public static String[] CHECKBOXMODEL = {"ֻ�����Լ� ","�����Լ�����","�����Լ����Ӻ͸�"};
	
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
	//У������
	public static String[] FORMATERTYPE = {"email","number","chn", "variable", "date", "phone"};
}

