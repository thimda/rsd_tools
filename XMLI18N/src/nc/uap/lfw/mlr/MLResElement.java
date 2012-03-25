package nc.uap.lfw.mlr;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.Region;

/**
 * ����Ԫ��
 * 
 * @author dingrf
 *
 */
public class MLResElement{

	/**����Ԫ��ֵ*/
	private String fValue;
	
	/**Ԫ�����ļ���λ��*/
	private Region fPosition;

	public MLResElement(String value, int start, int length){
		fValue = value;
		Assert.isNotNull(fValue);
		fPosition = new Region(start, length);
	}

	public Region getPosition(){
		return fPosition;
	}

	public String getValue(){
		return fValue;
	}

	public void setValue(String value){
		fValue = value;
	}

	public Region getFPosition() {
		return fPosition;
	}

	public void setFPosition(int start, int length) {
		fPosition = new Region(start, length);
	}

}
