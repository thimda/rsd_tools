package nc.uap.lfw.mlr;

import nc.uap.lfw.tool.*;
import nc.uap.lfw.wizard.PageNode;

/**
 * ������Դ�滻��
 * 
 * @author dingrf
 *
 */
public class MLResSubstitution{

	/**����Ԫ��*/
	private MLResElement element;
	
	/**����ģ��*/
	private String langModule;
	
	/**����ֵ*/
	private String value;
	
	/**Ӣ��ֵ*/
	private String englishValue;
	
	/**���ķ���*/
	private String twValue;
	
	/**����key*/
	private String key;
	
	/**ѡ������*/
	private String selectKey;
	
	/**ԭʼ����*/
	private String oldKey;
	
	/**�Ѵ���key*/
	private String extKey;
	
	/**ʹ�ù�����Դkey*/
	private String commKey;
	
	/**�Ѵ��ڶ���ģ��*/
	private String extLangModule;
	
	/**keyֵǰ׺*/
	private String prefix;
	
	/**״̬  0��������1�����£�2��ɾ����3��������Դ,4������,5:����,6:�޸�*/
	private int state;
	
	/**��Դ�ļ�*/
	private MLRes simpchnRes;
	private MLRes tradchnRes;
	private MLRes englishRes;
	
	/**�ļ�id*/
	private String filePath;
	
	/** ��Դ�ļ�����*/
	private String resFileName;
	
	/**PageNode*/
	private PageNode pageNode;
	
	public MLResSubstitution(MLResElement element, String value){
		langModule = "";
		key = "";
		extKey = "";
		extLangModule = "";
		prefix = "prefix";
		state = 0;
		this.element = element;
		this.value = value;
		twValue = ISNConvert.gbToBig5(value);
		englishValue = value;
	}

	public String getValue(){
		return value;
	}

	public String getKey(){
		return key;
	}

	public String getKeyWithPrefix(){
		return prefix + key;
	}
	
	/**
	 * ����״̬ȡMLResSubstitution��keyֵ
	 * @return
	 */
	public String getRealKey(){
		if (state == 0 || state == 6)
			return getKeyWithPrefix();
		if (state == 1)
			return getExtKey();
		if (state == 3)
			return getCommKey();
		else
			return "";
	}

	public int getState(){
		return state;
	}

	public void setValue(String value){
		this.value = value;
	}

	public void setKey(String key){
		this.key = key;
	}

	public void setState(int state){
		if (Helper.isEmptyString(extKey) && state == 1)
			state = 2;
		this.state = state;
	}

	public MLResElement getElement(){
		return element;
	}

	public void setPrefix(String prefix){
		this.prefix = prefix;
	}

	public String getPrefix(){
		return this.prefix;
	}
	
	public String getLangModule(){
		return langModule;
	}

	public String getRealLangModule(){
		if (state == 0 || state == 5 || state == 6)
			return getLangModule();
		if (state == 1)
			return extLangModule;
		else
			return "";
	}

	public void setLangModule(String langModule){
		this.langModule = langModule;
	}

	public String getExtKey(){
		return extKey;
	}

	public void setExtKey(String extKey){
		this.extKey = extKey;
	}

	public String getCommKey() {
		return commKey;
	}

	public void setCommKey(String commKey) {
		this.commKey = commKey;
	}

	public String getExtLangModule(){
		return extLangModule;
	}

	public void setExtLangModule(String extLangModule){
		this.extLangModule = extLangModule;
	}

	public String getEnglishValue(){
			return englishValue;
	}

	public void setEnglishValue(String englishValue){
		this.englishValue = englishValue;
	}

	public String getTwValue(){
			return twValue;
	}

	public void setTwValue(String twValue){
		this.twValue = twValue;
	}

	public MLRes getSimpchnRes() {
		return simpchnRes;
	}

	public void setSimpchnRes(MLRes simpchnRes) {
		this.simpchnRes = simpchnRes;
		this.value = simpchnRes.getValue(); 
	}

	public MLRes getTradchnRes() {
		return tradchnRes;
	}

	public void setTradchnRes(MLRes tradchnRes) {
		this.tradchnRes = tradchnRes;
		this.twValue = tradchnRes.getValue();
	}

	public MLRes getEnglishRes() {
		return englishRes;
	}

	public void setEnglishRes(MLRes englishRes) {
		this.englishRes = englishRes;
		this.englishValue = englishRes.getValue();
	}
	
	public MLRes getNotNullResByLangCode(String langCode){
		MLRes res = null;
		if ("simpchn".equals(langCode) && simpchnRes != null)
			res = simpchnRes;
		else if ("tradchn".equals(langCode) && tradchnRes != null)
			res = tradchnRes;
		else if ("english".equals(langCode) && englishRes != null)
			res = englishRes;
		
		if (res == null)
			if (simpchnRes != null)
				res = simpchnRes;
			else if (tradchnRes != null)
				res = tradchnRes;
			else if (englishRes != null)
				res = englishRes;
		return res;
	}
	
	/**
	 * �Ƿ����޸�
	 * 
	 * @return
	 */
	public boolean hasModifyed(){
		return simpchnUpdated() || tradchnUpdated() || englishUpdated();
	}
	
	/**
	 * �Ƿ���¼�������
	 * 
	 * @return
	 */
	public boolean simpchnUpdated(){
		return simpchnRes == null || !simpchnRes.getValue().equals(value);
	}

	/**
	 * �Ƿ���·�������
	 * 
	 * @return
	 */
	public boolean tradchnUpdated(){
		return tradchnRes == null || !tradchnRes.getValue().equals(this.twValue);
	}

	/**
	 * �Ƿ����Ӣ��
	 * 
	 * @return
	 */
	public boolean englishUpdated(){
		return englishRes == null || !englishRes.getValue().equals(this.englishValue);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getResFileName() {
		return resFileName;
	}

	public void setResFileName(String resFileName) {
		this.resFileName = resFileName;
	}

	public PageNode getPageNode() {
		return pageNode;
	}

	public void setPageNode(PageNode pageNode) {
		this.pageNode = pageNode;
	}

	public String getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(String selectKey) {
		this.selectKey = selectKey;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey;
	}
	
}
