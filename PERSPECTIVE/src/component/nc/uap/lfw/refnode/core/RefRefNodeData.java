package nc.uap.lfw.refnode.core;

import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.uap.lfw.core.refnode.IRefNode;

/**
 * ��ȡ��������data
 * @author zhangxya
 *
 */
public class RefRefNodeData {
  public  static Map<String, Map<String, IRefNode>>  getRefNodes(String ctx){
	  return LFWConnector.getAllPoolRefNodes(ctx);
  }
}
