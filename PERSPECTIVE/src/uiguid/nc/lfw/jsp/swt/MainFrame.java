package nc.lfw.jsp.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainFrame {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setSize(800, 600);
		try{
			
//			InputStream input = new FileInputStream("d:\\uimeta.um");
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
//			StringBuffer buf = new StringBuffer();
//			String str = reader.readLine();
//			while(str != null){
//				buf.append(str);
//				str = reader.readLine();
//			}
			
			//UIMeta uimeta = new UIMetaParserUtil().parseUIMeta(buf.toString());
//			UIMeta uimeta = NCConnector.getUIMeta("d:", null, null);
			//new DesignPanel(shell, SWT.NONE, uimeta);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}

}
