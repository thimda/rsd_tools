package nc.uap.lfw.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.lfwtools.perspective.MainPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.internal.launching.StandardVM;
import org.eclipse.jdt.internal.launching.StandardVMType;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;

public class LfwPluginVmRunner implements IVMRunner {

	private ILaunchConfiguration launchConfig;
	public void run(VMRunnerConfiguration config, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		String program = constructProgramString(config);
		List arguments= new ArrayList(12);
		arguments.add(program);
		
		//int port= SocketUtil.findFreePort();
		//arguments.add("-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:" + port); //$NON-NLS-1$
		
		IVMInstall fVMInstance = getVMInstall(launchConfig);
		String[] allVMArgs = combineVmArgs(config, fVMInstance);
		addArguments(allVMArgs, arguments);
		addArguments(ensureEncoding(launch, allVMArgs), arguments);
		addBootClassPathArguments(arguments, config);
		
		File workingDir = getWorkingDir(config);
		String[] cp = config.getClassPath();
		if (cp.length > 0) {
			arguments.add("-classpath"); //$NON-NLS-1$
			
			int endIndex = program.indexOf("bin\\javaw.exe");
			String jdkHome = program.substring(0, endIndex);
			String pluginPath = jdkHome + "jre\\lib\\plugin.jar";
			
			String cps = convertClassPath(cp, workingDir.getAbsolutePath());
			cps += File.pathSeparator + pluginPath;
			arguments.add(cps);
		}
		
		arguments.add(config.getClassToLaunch());
		String[] args = config.getProgramArguments();
		if(args != null){
			for (int i= 0; i < args.length; i++) {
				arguments.add(args[i]);
			}
		}
		String[] cmdLine = new String[arguments.size()];
		arguments.toArray(cmdLine);
		
		String[] envp = prependJREPath(config.getEnvironment(), new Path(program));
		
		
		try {
			MainPlugin.getDefault().logInfo("开始启动NC节点：=================, cmd line:");
			for (int i = 0; i < cmdLine.length; i++) {
				MainPlugin.getDefault().logInfo(cmdLine[i]);
			}
			
			Process p = Runtime.getRuntime().exec(cmdLine, envp, workingDir);
			BufferedReader obr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader ebr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			OutThread outT = new OutThread();
			outT.reader = obr;
			
			OutThread errT = new OutThread();
			errT.reader = ebr;
			
			new Thread(outT).start();
			new Thread(errT).start();
//			System.err.println(ebr.readLine());
			//p.waitFor();
			//System.out.println("exit value:" + p.exitValue());
		} 
		catch (IOException e) {
			MainPlugin.getDefault().logError(e);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	class OutThread implements Runnable {
		protected BufferedReader reader;
		public void run() {
			try{
				String str = reader.readLine();
				while(str != null){
					MainPlugin.getDefault().logInfo(str);
					str = reader.readLine();
				}
			}
			catch(Exception e){
				MainPlugin.getDefault().logError(e);
			}
		}
		
	};
	
	protected void addBootClassPathArguments(List arguments, VMRunnerConfiguration config) {
//		String[] prependBootCP= null;
//		String[] bootCP= null;
//		String[] appendBootCP= null;
//		Map map = config.getVMSpecificAttributesMap();
//		if (map != null) {
//			prependBootCP= (String[]) map.get(IJavaLaunchConfigurationConstants.ATTR_BOOTPATH_PREPEND);
//			bootCP= (String[]) map.get(IJavaLaunchConfigurationConstants.ATTR_BOOTPATH);
//			appendBootCP= (String[]) map.get(IJavaLaunchConfigurationConstants.ATTR_BOOTPATH_APPEND);
//		}
//		if (prependBootCP == null && bootCP == null && appendBootCP == null) {
//			// use old single attribute instead of new attributes if not specified
//			bootCP = config.getBootClassPath();
//		}
//		if (prependBootCP != null) {
//			arguments.add("-Xbootclasspath/p:" + convertClassPath(prependBootCP)); //$NON-NLS-1$
//		}
//		if (bootCP != null) {
//			if (bootCP.length > 0) {
//				arguments.add("-Xbootclasspath:" + convertClassPath(bootCP)); //$NON-NLS-1$
//			}
//		}
//		if (appendBootCP != null) {
//			arguments.add("-Xbootclasspath/a:" + convertClassPath(appendBootCP)); //$NON-NLS-1$
//		}
	}

	
	protected void addArguments(String[] args, List v) {
		if (args == null) {
			return;
		}
		for (int i= 0; i < args.length; i++) {
			v.add(args[i]);
		}
	}
	
	protected String[] ensureEncoding(ILaunch launch, String[] vmargs) {
		return new String[]{"-Dfile.encoding=GBK"};
	}
	
	private String[] combineVmArgs(VMRunnerConfiguration configuration, IVMInstall vmInstall) {
		String[] launchVMArgs= configuration.getVMArguments();
		String[] vmVMArgs = vmInstall.getVMArguments();
		if (vmVMArgs == null || vmVMArgs.length == 0) {
			return launchVMArgs;
		}
		String[] allVMArgs = new String[launchVMArgs.length + vmVMArgs.length];
		System.arraycopy(launchVMArgs, 0, allVMArgs, 0, launchVMArgs.length);
		System.arraycopy(vmVMArgs, 0, allVMArgs, launchVMArgs.length, vmVMArgs.length);
		return allVMArgs;
	}
	
	protected File getWorkingDir(VMRunnerConfiguration config) throws CoreException {
		String path = config.getWorkingDirectory();
		if (path == null) {
			return null;
		}
		File dir = new File(path);
		if (!dir.isDirectory()) {
			//abort(MessageFormat.format(LaunchingMessages.StandardVMRunner_Specified_working_directory_does_not_exist_or_is_not_a_directory___0__3, new String[] {path}), null, IJavaLaunchConfigurationConstants.ERR_WORKING_DIRECTORY_DOES_NOT_EXIST); 
		}
		return dir;
	}
	
	
	protected String[] prependJREPath(String[] env, IPath jdkpath) {
		if(true/*win32*/) {
			String BIN = "bin";
			String JRE = "jre";
			IPath jrepath = jdkpath.removeLastSegments(1);
			if(jrepath.lastSegment().equals(BIN)) {
				if(!jrepath.segment(jrepath.segmentCount()-2).equals(JRE)) {
					jrepath = jrepath.removeLastSegments(1).append(JRE).append(BIN);
				}
			}
			else {
				jrepath = jrepath.append(JRE).append(BIN);
			}
			if(jrepath.toFile().exists()) {
				String jrestr = jrepath.toOSString();
				if(env == null){
					Map map = DebugPlugin.getDefault().getLaunchManager().getNativeEnvironment();
					env = new String[map.size()];
					String var = null;
					int index = 0;
					for(Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						var = (String) iter.next();
						String value = (String) map.get(var);
						if (value == null) {
							value = ""; //$NON-NLS-1$
						}
						if (var.equalsIgnoreCase("path")) { //$NON-NLS-1$
							if(value.indexOf(jrestr) == -1) {
								value = jrestr+';'+value;
							}
						}
						env[index] = var+"="+value; //$NON-NLS-1$
						index++;
					}
				} else {
					String var = null;
					int esign = -1;
					for(int i = 0; i < env.length; i++) {
						esign = env[i].indexOf('=');
						if(esign > -1) {
							var = env[i].substring(0, esign);
							if(var != null && var.equalsIgnoreCase("path")) { //$NON-NLS-1$
								if(env[i].indexOf(jrestr) == -1) {
									env[i] = var + "="+jrestr+';'+(esign == env.length ? "" : env[i].substring(esign+1)); //$NON-NLS-1$ //$NON-NLS-2$
									break;
								}
							}
						}
					}
				}
			}
		} 
		return env;
	}
	
	private String convertClassPath(String[] cp, String currDir) {
		int pathCount= 0;
		StringBuffer buf= new StringBuffer();
		if (cp.length == 0) {
			return "";    //$NON-NLS-1$
		}
		for (int i= 0; i < cp.length; i++) {
			if (pathCount > 0) {
				buf.append(File.pathSeparator);
			}
			String path = cp[i];
			path = path.replace(currDir, ".\\");
			buf.append(path);
			pathCount++;
		}
		return buf.toString();
	}

	public void setLaunchConfig(ILaunchConfiguration config){
		launchConfig = config;
	}
	
	private String constructProgramString(VMRunnerConfiguration config) throws CoreException {

		IVMInstall fVMInstance = getVMInstall(launchConfig);
		// Look for the user-specified java executable command
		String command= null;
		Map map= config.getVMSpecificAttributesMap();
		if (map != null) {
			command = (String)map.get(IJavaLaunchConfigurationConstants.ATTR_JAVA_COMMAND);
		}
		
		// If no java command was specified, use default executable
		if (command == null) {
			File exe = null;
			if (fVMInstance instanceof StandardVM) {
				File installLocation = ((StandardVM)fVMInstance).getInstallLocation();
		        if (installLocation != null) {
		            exe = StandardVMType.findJavaExecutable(installLocation);
		        }
			} else {
				exe = StandardVMType.findJavaExecutable(fVMInstance.getInstallLocation());
			}
			if (exe == null) {
			} 
			else {
				return exe.getAbsolutePath();
			}
		}
				
		// Build the path to the java executable.  First try 'bin', and if that
		// doesn't exist, try 'jre/bin'
		String installLocation = fVMInstance.getInstallLocation().getAbsolutePath() + File.separatorChar;
		File exe = new File(installLocation + "bin" + File.separatorChar + command); //$NON-NLS-1$ 		
//		if (fileExists(exe)){
//			return exe.getAbsolutePath();
//		}
//		exe = new File(exe.getAbsolutePath() + ".exe"); //$NON-NLS-1$
//		if (fileExists(exe)){
//			return exe.getAbsolutePath();
//		}
//		exe = new File(installLocation + "jre" + File.separatorChar + "bin" + File.separatorChar + command); //$NON-NLS-1$ //$NON-NLS-2$
//		if (fileExists(exe)) {
//			return exe.getAbsolutePath(); 
//		}
//		exe = new File(exe.getAbsolutePath() + ".exe"); //$NON-NLS-1$
//		if (fileExists(exe)) {
//			return exe.getAbsolutePath(); 
//		}		

		// NOTE: an exception will be thrown - null cannot be returned
		return exe.getAbsolutePath();		
	}	
	
//	private IVMInstall verifyVMInstall(ILaunchConfiguration configuration)
//			throws CoreException {
//		IVMInstall vm = getVMInstall(configuration);
//		File location = vm.getInstallLocation();
//		return vm;
//	}
	
	private IVMInstall getVMInstall(ILaunchConfiguration configuration)
			throws CoreException {
		return JavaRuntime.computeVMInstall(configuration);
	}
	
}
