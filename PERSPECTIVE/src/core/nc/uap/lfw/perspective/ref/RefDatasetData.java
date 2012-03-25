package nc.uap.lfw.perspective.ref;

import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;

/**
 * 获取公共池中data
 * @author zhangxya
 *
 */
public class RefDatasetData {
  public  static Map<String, Map<String, Dataset>>  getDatasets(String ctx){
	  return LFWConnector.getAllPoolDatasets(ctx);
  }
  
  public  static Map<String, Map<String, LfwWidget>>  getPoolWidgets(String ctx){
	  return LFWConnector.getAllPoolWidgets(ctx);
  }
}
