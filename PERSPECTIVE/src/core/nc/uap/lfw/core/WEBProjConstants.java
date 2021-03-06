package nc.uap.lfw.core;


public class WEBProjConstants{
	public static final String KEY_MODULE_GROUP = "Wizard.NewPage.ModuleGroup";
	public static final String KEY_PRODUCT_NAME = "Wizard.NewPage.ProductName";
	public static final String KEY_MODULE_NAME = "Wizard.NewPage.ModuleName";
	public static final String KEY_MODULE_CONFIG = "Wizard.NewPage.ModuleConfig";
	public static final String KEY_MODULE_PRIORITY = "Wizard.NewPage.ModulePriority";
	public static final String KEY_MODULE_DESC = "Wizard.NewPage.ModuleDesc";
	public static final String KEY_NEWPRJ_MAINPAGE_TITLE = "New.Wizard.MainPage.Title";
	public static final String KEY_NEWPRJ_MAINPAGE_DESC = "New.Wizard.MainPage.Desc";
	public static final String KEY_ENABLE_CP_CLIENT = "Client.Classpath.Feature";
	public static final String MODULE_NATURE = "nc.uap.lfw.ModuleProjectNature";
	public static final String MDE_LIBRARY_CONTAINER_ID = "nc.uap.lfw.library.container";
	public static final String MODULE_CONIFG_BUILDER_ID = "nc.uap.lfw.ModuleBuilder";
	public static final String NC_LAUNCH_ID = "nc.uap.lfw.launch";
	public static final String SERVER_MAIN_CLASS = "ufmiddle.start.tomcat.StartDirectServer";
	public static final String MAIN_APPLET_CLASS = "nc.ui.pub.MainApplet";
	public static final String FIELD_NC_HOME = "FIELD_NC_HOME";
	public static final String MODULES_NC_HOME = "MODULES_NC_HOME";
	public static final String FIELD_EX_MODULES = "FIELD_EX_MODULES";
	public static final String FIELD_CLINET_IP = "FIELD_CLINET_IP";
	public static final String FIELD_CLINET_PORT = "FIELD_CLINET_PORT";
	public static final String EXCEPT_JAR_NC_HOME = "EXCEPT_JAR_NC_HOME";
	public static final String MODULE_NAME_PROPERTY = "module.name";
	public static final String MODULE_CONFIG_PROPERTY = "module.defConfig";
	public static final String FIELD_ENABLE_CP_CLIENT = "FIELD_ENABLE_CP_CLIENT";
	public static final String PORTAL_MODULE_NATURE = "nc.uap.portal.ModuleProjectNature";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final int BETWEEN = 100;
	public static final int FIELD_BETWEEN = 80;
	public static final int DATASETBETWEEN = 100;
	public static final int ELEMENT_CORNER_SIZE = 10;
	public static final String BCPMODULE_NATURE = "nc.uap.mde.BizCompProjectNature";
	
	/* 树节点 用常量*/
	public static final String NODEGROUP_CN = "Pages";
	public static final String PAGEMETA_CN = "Page";
	public static final String APPLICATION = "Applications";
	public static final String APPLICATION_SUB = "Application";
	public static final String MODEL = "Models";
	public static final String MODEL_SUB = "Model";
	public static final String WINDOW = "Windows";
	public static final String WINDOW_SUB = "PageMeta";
	public static final String PUBLIC_VIEW = "PublicViews";
	public static final String PUBLIC_VIEW_SUB = "PublicView";
	public static final String PUBLIC_VIEW_TAGNAME = "Widget";
	public static final String VIEW = "Views";
	public static final String VIEW_SUB = "View";
	public static final String VIRTUALDIR = "虚拟目录";
	public static final String PAGEFLOW = "pageflow";
	public static final String MAIN_WIDGET_CN = "主片段";
	//public static final String WIDGET_CN = "片段";
	public static final String MENUBAR_CN = "菜单";
	public static final String CONTEXT_MENUBAR = "弹出菜单";
	public static final String MENUS_MANAGER_CN = "菜单管理器";
//	public static final String PAGEFLOW_START_CN = "开始";
//	public static final String PAGEFLOW_PAGE_CN = "页面";
//	public static final String PAGEFLOW_DECISION_CN = "决策点";
	public static final String BASE_NODEGROUP_PATH = "web/html/nodes";
	
	public static final String AMC_BASE_NODEGROUP_PATH = "/web/html";
	public static final String AMC_APPLICATION_PATH = AMC_BASE_NODEGROUP_PATH + "/applications";
	public static final String AMC_MODEL_PATH = AMC_BASE_NODEGROUP_PATH + "/models";
	public static final String AMC_WINDOW_PATH = "/web/html/nodes"; //AMC_BASE_NODEGROUP_PATH + "/windows";
	public static final String AMC_PUBLIC_VIEW_PATH = "/web/pagemeta/public/widgetpool";
	public static final String AMC_VIEW_PATH = AMC_BASE_NODEGROUP_PATH + "/components/views";
	
	public static final String AMC_APPLICATION_FILENAME = "application.app";
	public static final String AMC_MODEL_FILENAME = "model.mod";
	public static final String AMC_WINDOW_FILENAME = "pagemeta.pm";
	public static final String AMC_PUBLIC_VIEW_FILENAME = "widget.wd";
	public static final String AMC_VIEW_FILENAME = "widget.wd";
	public static final String AMC_UIMETA_FILENAME = "uimeta.um";
	
	public static final String COMPONENTS = "控件";
	public static final String COMPONENTS_EN = "Control";
	public static final String CONTEXT_EN = "Context";
	public static final String DATASET = "数据集";
	public static final String DATASET_EN = "DataSet";
	public static final String PUBLIC_DATASET="公共数据集";
	public static final String PUBLIC_WIDGET="公共iView";
	public static final String PUBLIC_WIDGET_EN= PUBLIC_VIEW;
	public static final String COMBODATA = "下拉数据集";
	public static final String COMBODATA_EN = "ComboData";
	public static final String REFNODE = "参照";
	public static final String REFNODE_EN = "RefNode";
	public static final String PUBLIC_REFNODE="公共参照";
	public static final String COMPONENT_REFNODE = "[参照]";
	public static final String COMPONENT_REFNODE_EN = "[Reference]";
	public static final String COMPONENT_REFNODERELATION = "[参照关系]";
	public static final String COMPONENT_REFNODERELATION_EN = "[ReferenceRelation]";
	public static final String COMPONENT_COMBODATA="[下拉数据]";
	public static final String COMPONENT_COMBODATA_EN="[CombData]";
	public static final String WIDGET_MENUBAR = "菜单";
	public static final String CONTEXT_MENUCOMP_ELEMENT = "[右键菜单]";
	public static final String CONTEXT_MENUCOMP_ELEMENT_EN = "[PopupMenu]";
	public static final String CONTAINER = "容器";
	public static final String CONTAINER_EN = "Container";
	public static final String COMPONENT_GRID = "[表格]";
	public static final String COMPONENT_GRID_EN = "[Grid]";
	public static final String COMPONENT_EXCEL = "[Excel]";
	public static final String COMPONENT_FORM = "[表单]";
	public static final String COMPONENT_FORM_EN = "[Form]";
	public static final String COMPONENT_BUTTON = "[按钮]";
	public static final String COMPONENT_BUTTON_EN = "[Button]";
	public static final String COMPONENT_CHART = "[图表]";
	public static final String COMPONENT_CHART_EN = "[Chart]";
	public static final String COMPONENT_MENUBAR = "[菜单]";
	public static final String COMPONENT_MENUBAR_EN = "[Menu]";
	public static final String COMPONENT_TREE = "[树]";
	public static final String COMPONENT_TREE_EN = "[Tree]";
	public static final String COMPONENT_IMAGE = "[图片]";
	public static final String COMPONENT_IMAGE_EN = "[Image]";
	public static final String COMPONENT_LABEL =  "[标签]";
	public static final String COMPONENT_LABEL_EN =  "[Label]";
	public static final String COMPONENT_TEXTCOMP =  "[文本框]";
	public static final String COMPONENT_TEXTCOMP_EN =  "[TextComp]";
	public static final String COMPONENT_TEXTAREA =  "[TextArea]";
	public static final String COMPONENT_IFRAMECOMP =  "[IFrameComp]";
	public static final String COMPONENT_TOOLBAR =  "[工具条]";
	public static final String COMPONENT_TOOLBAR_EN =  "[ToolBar]";
	public static final String COMPONENT_LINKCOMP =  "[链接]";
	public static final String COMPONENT_LINKCOMP_EN =  "[LinkComp]";
	public static final String COMPONENT_PROGRESSBAR = "[进度条]";
	public static final String COMPONENT_PROGRESSBAR_EN = "[ProgressBar]";
	public static final String COMPONENT_SELFDEFCOMP = "[自定义控件]";
	public static final String COMPONENT_SELFDEFCOMP_EN = "[CustomConizol]";
	public static final String REFNODE_PAGEMETA = "reference";
	public static final String PUBLIC_COMMAND_CN = "公共命令";
	public static final String BUSINESSCOMPONENT = "组件";
	public static final String FUNC_CODE = "funcode";
	
	/* 树节点右键菜单*/
	public static final String USERRELATE = "关联NC开发用户";
	public static final String NEW_PAGEMETA = "新建Page";
	public static final String EDIT_PAGEMETA = "编辑Page";
	public static final String DEL_PAGEMETA = "删除Page";
	public static final String NEW_APPLICATION = "新建Application";
	public static final String EDIT_APPLICATION = "编辑Application";
	public static final String DEL_APPLICATION = "删除Application";
	public static final String NEW_MODEL = "新建Model";
	public static final String EDIT_MODEL = "编辑Model";
	public static final String DEL_MODEL = "删除Model";
	public static final String NEW_WINDOW = "新建Window";
	public static final String EDIT_WINDOW = "编辑Window";
	public static final String DEL_WINDOW = "删除Window";
	public static final String ADD_WINDOW = "增加Window";
	public static final String NEW_VIEW = "新建View";
	public static final String EDIT_VIEW = "编辑View";
	public static final String DEL_VIEW = "删除View";
	public static final String RENAME_VIEW = "修改View名称";  
	public static final String MAPPING = "Mapping";
	public static final String NEW_PUBLIC_VIEW = "新建PublicView";
	public static final String EDIT_PUBLIC_VIEW = "编辑PublicView";
	public static final String DEL_PUBLIC_VIEW = "删除PublicView";
	public static final String NEW_VIRTUALDIR = "新建虚拟目录";
	public static final String DEL_VIRTUALDIR = "删除虚拟目录";
	public static final String NEW_PAGEFLOW = "新建PageFlow";
	public static final String NEW_DATASET = "新建数据集";
	public static final String COPYMESSAGE = "拷贝";
	public static final String EDIT_DATASET = "编辑数据集";
	public static final String DEL_DATASET = "删除数据集";
	public static final String NEW_REF_DATASET = "新建引用数据集";
	public static final String NEW_PUBLIC_DATASET = "新建公共数据集";
	public static final String NEW_COMBO_DATASET = "新建下拉数据集";
	public static final String DEL_COMBO_DATASET = "删除下拉数据集";
	public static final String NEW_DS_FORM_COMPONENT = "从组件引入数据集";
	public static final String NEW_REFNODE = "新建参照";
	public static final String DEL_REFNODE = "删除参照";
	public static final String NEW_REFNODE_REF = "新建参照关系";
	public static final String DEL_REFNODE_REF = "删除参照关系";
	public static final String NEW_PUBLIC_REFNODE = "新建公共参照";
	public static final String NEW_WIDGET = "新建iView";
	public static final String EDIT_WIDGET = "编辑iView";
	public static final String DEL_WIDGET = "删除iView";
	public static final String RENAME_WIDGET = "修改iView名称";
	public static final String NEW_PUBLIC_WIDGET = "新建公共iView";
	public static final String DEL_PUBLIC_WIDGET = "删除公共iView";
	public static final String NEW_WIDGET_FROM_POOL = "从公共池中引用iView";
	public static final String NEW_VIEW_FROM_PUBLIC_VIEW = "从PublicView中引用View";
	
	public static final String UI_DESIGN ="UI设计";
	public static final String UI_GUIDE ="UI设计向导";
	public static final String DEL_UI_GUIDE ="删除UI设计";
	public static final String UI_GUILD_IMPORT_HTML ="导入美工";
	
	public static final String PUBLISH_NODE ="发布节点";
	public static final String PUBLISH_NC ="发布到NC";
	public static final String REGISTER_FUNCTION_NODE ="注册功能节点";
	public static final String MANAGE_MENU_CATEGORY ="管理菜单分类";
	public static final String REGISTER_MENU ="注册菜单";
	public static final String REGISTER_FORM_TYPE = "注册单据类型";
	public static final String CANCEL_PUBLISH_NC ="取消NC发布";
	public static final String REGISTER_MENU_ITEM ="注册按钮";
	public static final String NC_PATTERN_GENERATOR ="NC模式化生成";
	public static final String EDIT_NC_MENU ="编辑NC菜单";
	
	public static final String NC_TEMPLATE ="NC模板关联";
	public static final String USE_NC_TEMPLATE ="关联NC单据模板";
	public static final String USE_NC_QUERY_TEMPLAGE ="关联NC查询模板";
	public static final String EDIT_NC_TEMPLATE ="编辑关联模板";
	public static final String CANCEL_NC_TEMPLATE ="取消关联NC单据模板";
	public static final String CANCEL_NC_QUERY_TEMPLATE ="取消关联NC查询模板";
	
	public static final String NEW_CONTEXT_MENU ="新建弹出菜单";
	public static final String DEL_CONTEXT_MENU ="删除弹出菜单";
	
	public static final String NEW_GRID ="新建表格";
	public static final String DEL_GRID ="删除表格";
	public static final String NEW_EXCEL ="新建Excel";
	public static final String DEL_EXCEL ="删除Excel";
	public static final String NEW_TREE ="新建树";
	public static final String DEL_TREE ="删除树";
	public static final String NEW_FORM ="新建表单";
	public static final String DEL_FORM ="删除表单";
	public static final String NEW_BUTTON ="新建按钮";
	public static final String DEL_BUTTON ="删除按钮";
	public static final String NEW_TEXTCOMP ="新建文本框";
	public static final String DEL_TEXTCOMP ="删除文本框";
	public static final String NEW_IFRAME ="新建Iframe";
	public static final String DEL_IFRAME ="删除Iframe";
	public static final String NEW_LABEL ="新建标签";
	public static final String DEL_LABEL ="删除标签";
	public static final String NEW_IMAGE ="新建图片";
	public static final String DEL_IMAGE ="删除图片";
	public static final String NEW_TOOLBAR ="新建工具条";
	public static final String DEL_TOOLBAR ="删除工具条";
	public static final String NEW_LINKCOMP ="新建链接";
	public static final String DEL_LINKCOMP ="删除链接";
	public static final String NEW_PROGRESSBAR ="新建进度条";
	public static final String DEL_PROGRESSBAR ="删除进度条";
	public static final String NEW_SELF_DEF_COMP ="新建自定义控件";
	public static final String DEL_SELF_DEF_COMP ="删除自定义控件";
	public static final String NEW_CHART ="新建图表";
	public static final String DEL_CHART ="删除图表";
	
	public static final String NEW_MENUBAR ="新建菜单";
	public static final String DEL_MENUBAR ="删除菜单";
	
	public static final String DEL_CARD_LAYOUT ="删除card布局";
	public static final String DEL_OUTLOOKBAR ="删除OutLookbar";
	public static final String DEL_TAB ="删除Tab页签";
	
	
	public static final String REFRESH = "刷新";
	public static final String NEW = "新建";
	public static final String ADD = "增加";
	public static final String MODIFY = "修改";
	public static final String EDITOR = "编辑";
	public static final String DELETE = "删除";
	public static final String REFERENCE = "引用";
	public static final String REGISTRY = "注册";
	public static final String MANAGER = "管理";
	
	public static final String BASIC = "基本";
	public static final String ID = "ID";
	public static final String CAPTION = "标题";
	public static final String I18NNAME = "名称";
	public static final String SOURCE_PACKAGE = "源文件夹";
	public static final String CONTROLLER_CLASS = "控制类";
	public static final String DEFAULT_WINDOW = "默认Window";
	public static final String PAGEMODEL = "pagemodel";
	public static final String PROCESS_CLASS = "处理类";
	
	public static final String PROP_CAPTION = "CAPTION";
	public static final String PROP_I18NNAME = "I18NNAME";
	public static final String PROP_PROCESS_CLASS = "PROCESS_CLASS";
	public static final String PROP_SOURCE_PACKAGE = "SOURCE_PACKAGE";
	public static final String PROP_CONTROLLER_CLASS = "CONTROLLER_CLASS";
	public static final String PROP_DEFAULT_WINDOW = "DEFAULT_WINDOW";
	public static final String PROP_PAGEMODEL = "PAGEMODEL";
	public static final String PROP_ID = "ID";
	
	public static final String NEW_PLUGOUTDESC ="新建输出描述";
	public static final String NEW_PLUGINDESC ="新建输入描述";
	public static final String PLUGINDESC ="输入描述";
	public static final String PLUGOUTDESC ="输出描述";
	public static final String PLUGOUTEMIT ="触发器";

	public static final String ATTRIBUTE ="属性";
	public static final String EVENT = "事件";
	
	public static final String DEFAULT_WINDOW_ID = "defaultWindowId";
}
