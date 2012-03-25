package nc.uap.lfw.tree.treelevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBasicElementObj;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.comp.CodeTreeLevel;
import nc.uap.lfw.core.comp.RecursiveTreeLevel;
import nc.uap.lfw.core.comp.SimpleTreeLevel;
import nc.uap.lfw.core.comp.TreeLevel;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.tree.TreeElementObj;
import nc.uap.lfw.tree.core.TreeEditor;
import nc.uap.lfw.tree.core.TreeLevelElementObj;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

/**
 * tree level ����widget
 * @author zhangxya
 *
 */
public class TreeLevelWizard extends Wizard {
	
	private TreeLevelSimplePage treeLevelSimplePage;
	private TreeLevelRecusivePage treeLevelRecusivePage;
	private TreeLevelCodeTreePage treeLevelCodeTreePage;
	public static final String TREELEVEL_SIMPLE = "Simple";
	public static final String TREELEVEL_RECUSIVE = "Recursive";
	private String id = null;
	private String dataset = null;
	private String masterKeyField = null;
	private String[] labelFields =null;
	private String labelField = "";
	private String labelDelims = null;
	private String recursiveKeyField = null;;
	private String recursivePKeyField = null;
	private String 	codeField = null;
	private String  codeRule = null;
	private String   detailKeyParameter = "";
	private TreeElementObj treeobjnew  = null;
	private List<String> levelIds = new ArrayList<String>();
	
	private class ModifyTreeLevelCommand extends Command{
		public ModifyTreeLevelCommand(){
			super("�޸�TreeLeve");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}
	private TreeLevelElementObj refds;
	private LFWBasicElementObj treeobj;
	private String flag ;
	
	public LFWBasicElementObj getTreeobj() {
		return treeobj;
	}
	public void setTreeobj(LFWBasicElementObj treeobj) {
		this.treeobj = treeobj;
	}
	public TreeLevelWizard(LFWBasicElementObj treeobj, TreeLevelElementObj refds, String flag){
		super();
		setWindowTitle("����Tree Level �Ի���");
		this.refds = refds;
		this.treeobj = treeobj;
		this.flag = flag;
	}
	private TreeLevelFirstPage treeLevelFirstPage;
	public TreeLevelFirstPage getTreeLevelFirstPage() {
		return treeLevelFirstPage;
	}
	public void setTreeLevelFirstPage(TreeLevelFirstPage treeLevelFirstPage) {
		this.treeLevelFirstPage = treeLevelFirstPage;
	}
	public TreeLevelSimplePage getTreeLevelSimplePage() {
		return treeLevelSimplePage;
	}
	public void setTreeLevelSimplePage(TreeLevelSimplePage treeLevelSimplePage) {
		this.treeLevelSimplePage = treeLevelSimplePage;
	}
	public TreeLevelRecusivePage getTreeLevelRecusivePage() {
		return treeLevelRecusivePage;
	}
	public void setTreeLevelRecusivePage(TreeLevelRecusivePage treeLevelRecusivePage) {
		this.treeLevelRecusivePage = treeLevelRecusivePage;
	}
	public TreeLevelCodeTreePage getTreeLevelCodeTreePage() {
		return treeLevelCodeTreePage;
	}
	public void setTreeLevelCodeTreePage(TreeLevelCodeTreePage treeLevelCodeTreePage) {
		this.treeLevelCodeTreePage = treeLevelCodeTreePage;
	}
	//private TreeLevelSecondPage treeLevelSecondPage;

	
	public void addPages() {
		treeLevelFirstPage = new TreeLevelFirstPage("Tree Level����",treeobj,refds);
		treeLevelSimplePage = new TreeLevelSimplePage("Tree Level ����", treeobj, refds, flag);
		treeLevelRecusivePage = new TreeLevelRecusivePage("Tree Level ����", treeobj, refds, flag);
		treeLevelCodeTreePage = new TreeLevelCodeTreePage("Tree Level ����", treeobj, refds, flag);
		addPage(treeLevelFirstPage);
		addPage(treeLevelSimplePage);
		addPage(treeLevelRecusivePage);
		addPage(treeLevelCodeTreePage);
	}
	
	public boolean canFinish() {
		if(this.getContainer().getCurrentPage() == treeLevelSimplePage || this.getContainer().getCurrentPage() == treeLevelRecusivePage
				|| this.getContainer().getCurrentPage() == treeLevelCodeTreePage)
			return true;
		else return false;
		}
	
	
	
	public boolean performFinish() {
		boolean type1 = treeLevelFirstPage.getTreelevelTypeSi().getSelection();
		boolean type2 = treeLevelFirstPage.getTreelevelTypeRe().getSelection();
		boolean type3 = treeLevelFirstPage.getTreelevelTypeCode().getSelection();
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view != null){
			if(treeobj instanceof TreeElementObj)
				 treeobjnew = (TreeElementObj)treeobj;
			else if(treeobj instanceof TreeLevelElementObj){
				TreeLevelElementObj treelevelEle = (TreeLevelElementObj)treeobj;
				//TreeLevel treelevel = treelevelEle.getTreelevel();
				LFWBasicElementObj parent = treelevelEle.getParentTreeLevel();
				while(parent != null && !(parent instanceof TreeElementObj)){
					 TreeLevelElementObj parentnew = (TreeLevelElementObj)parent;
					 parent = parentnew.getParentTreeLevel();
				}
				treeobjnew = (TreeElementObj)parent;
			}
				
			TreeViewComp treeViewcomp = treeobjnew.getTreeComp();
			TreeLevel topLevel = treeViewcomp.getTopLevel();
			if(type1){
				id = treeLevelSimplePage.getId().getText();
				treeobjnew.setId(id);
				dataset = treeLevelSimplePage.getDataset().getText();
				masterKeyField = treeLevelSimplePage.getMasterKeyField().getText();
				labelFields = treeLevelSimplePage.getRightlabelFields().getItems();
				labelField = "";
				for (int i = 0; i < labelFields.length; i++) {
					if(i != labelFields.length - 1)
						labelField += labelFields[i] + ",";
					else
						labelField += labelFields[i];
				}
				labelDelims = treeLevelSimplePage.getLabelDelims().getText();
				if(topLevel == null){
					topLevel = new SimpleTreeLevel();
					topLevel.setId(id);
					topLevel.setDataset(dataset);
					topLevel.setMasterKeyField(masterKeyField);
					topLevel.setLabelFields(labelField);
					topLevel.setLabelDelims(labelDelims);
					treeViewcomp.setTopLevel(topLevel);
				}else{
					//�Ƿ������޸Ĵ�TopLevel
					if(topLevel.getDataset().equals(refds.getDs().getId())){
						TreeLevel childLevel = topLevel.getChildTreeLevel();
						treeViewcomp.setTopLevel(null);
						topLevel = new SimpleTreeLevel();
						topLevel.setChildTreeLevel(childLevel);
						topLevel.setId(id);
						topLevel.setDataset(dataset);
						topLevel.setMasterKeyField(masterKeyField);
						topLevel.setLabelFields(labelField);
						topLevel.setLabelDelims(labelDelims);
						treeViewcomp.setTopLevel(topLevel);
					}
					/**/
					else{
						//toplevel��levelId
						if(!topLevel.getDataset().equals(refds.getDs().getId()))
							levelIds.add(topLevel.getId());
						detailKeyParameter = treeLevelSimplePage.getDetailKeyParameter().getText();
						TreeLevel childLevel = topLevel.getChildTreeLevel();
						TreeLevel originalLevel = null;
						while(childLevel != null){
							//�Ƿ��޸Ĵ�childLeve
							if(childLevel.getDataset().equals(refds.getDs().getId())){
								TreeLevel childchiLevel = childLevel.getChildTreeLevel();
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(null);
								}else{
									topLevel.setChildTreeLevel(null);
								}
								childLevel = new SimpleTreeLevel();
								childLevel.setId(id);
								childLevel.setDataset(dataset);
								childLevel.setMasterKeyField(masterKeyField);
								childLevel.setLabelFields(labelField);
								childLevel.setLabelDelims(labelDelims);
								childLevel.setChildTreeLevel(childchiLevel);
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(childLevel);
								}else{
									topLevel.setChildTreeLevel(childLevel);
								}
								break;
							}
							else{
								levelIds.add(childLevel.getId());
								originalLevel = childLevel;
								childLevel = childLevel.getChildTreeLevel();
							}
						}
							
						if(childLevel == null){
							if(levelIds.contains(id)){
								MessageDialog.openError(null, "TreeLevl ����", "TreeLeveId����ǰ���ظ�������������!");
								treeLevelSimplePage.getId().setFocus();
								return false;
							}
							childLevel = new SimpleTreeLevel();
							childLevel.setId(id);
							childLevel.setDataset(dataset);
							childLevel.setMasterKeyField(masterKeyField);
							childLevel.setLabelFields(labelField);
							childLevel.setLabelDelims(labelDelims);
							childLevel.setDetailKeyParameter(detailKeyParameter);
							if(originalLevel != null){
								originalLevel.setChildTreeLevel(childLevel);
							}else{
								topLevel.setChildTreeLevel(childLevel);
							}
							//��treellevelElement����treelevel
						}
						treeViewcomp.setTopLevel(topLevel);
						refds.setTreelevel(childLevel);
					}
				}
			}else if(type2){
				id = treeLevelRecusivePage.getId().getText();
				treeobjnew.setId(id);
				dataset = treeLevelRecusivePage.getDataset().getText();
				masterKeyField = treeLevelRecusivePage.getMasterKeyField().getText();
				labelFields = treeLevelRecusivePage.getRightlabelFields().getItems();
				labelField = "";
				for (int i = 0; i < labelFields.length; i++) {
					if(i != labelFields.length - 1)
						labelField += labelFields[i] + ",";
					else
						labelField += labelFields[i];
				}
				labelDelims = treeLevelRecusivePage.getLabelDelims().getText();
				recursiveKeyField = treeLevelRecusivePage.getRecursiveKeyField().getText();;
				recursivePKeyField = treeLevelRecusivePage.getRecursivePKeyField().getText();
				if(topLevel == null){
					RecursiveTreeLevel topLevelre = new RecursiveTreeLevel();
					topLevelre.setId(id);
					topLevelre.setDataset(dataset);
					topLevelre.setMasterKeyField(masterKeyField);
					topLevelre.setRecursiveKeyField(recursiveKeyField);
					topLevelre.setRecursivePKeyField(recursivePKeyField);
					topLevelre.setLabelFields(labelField);
					topLevelre.setLabelDelims(labelDelims);
					treeViewcomp.setTopLevel(topLevelre);
				}
				else{
					//�Ƿ������޸Ĵ�TopLevel
					if(topLevel.getDataset().equals(refds.getDs().getId())){
						TreeLevel childLev = topLevel.getChildTreeLevel();
						treeViewcomp.setTopLevel(null);
						RecursiveTreeLevel topLevelre = new RecursiveTreeLevel();
						topLevelre.setChildTreeLevel(childLev);
						topLevelre.setId(id);
						topLevelre.setDataset(dataset);
						topLevelre.setMasterKeyField(masterKeyField);
						topLevelre.setRecursiveKeyField(recursiveKeyField);
						topLevelre.setRecursivePKeyField(recursivePKeyField);
						topLevelre.setLabelFields(labelField);
						topLevelre.setLabelDelims(labelDelims);
						treeViewcomp.setTopLevel(topLevelre);
					}else{
						if(!topLevel.getDataset().equals(refds.getId()))
							levelIds.add(topLevel.getId());
						detailKeyParameter = treeLevelRecusivePage.getDetailKeyParameter().getText();
						TreeLevel childLevel = topLevel.getChildTreeLevel();
						TreeLevel originalLevel = null;
						while(childLevel != null){
							if(childLevel.getDataset().equals(refds.getDs().getId())){
								TreeLevel childchiLevel = childLevel.getChildTreeLevel();
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(null);
								}else{
									topLevel.setChildTreeLevel(null);
								}
								RecursiveTreeLevel chidlevelre = new RecursiveTreeLevel();
								chidlevelre.setId(id);
								chidlevelre.setDataset(dataset);
								chidlevelre.setMasterKeyField(masterKeyField);
								chidlevelre.setLabelFields(labelField);
								chidlevelre.setRecursiveKeyField(recursiveKeyField);
								chidlevelre.setRecursivePKeyField(recursivePKeyField);
								chidlevelre.setLabelDelims(labelDelims);
								chidlevelre.setDetailKeyParameter(detailKeyParameter);
								chidlevelre.setChildTreeLevel(childchiLevel);
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(chidlevelre);
								}else{
									topLevel.setChildTreeLevel(chidlevelre);
								}
								break;
							}
							else{
								//������ǵ�ǰ�򿪵�TreeLevel,��dsid�����ȥ
								if(!childLevel.getDataset().equals(refds.getId()))
									levelIds.add(childLevel.getId());
								originalLevel = childLevel;
								childLevel = childLevel.getChildTreeLevel();
							}
						}
						
						if(childLevel == null){
							if(levelIds.contains(id)){
								MessageDialog.openError(null, "TreeLevl ����", "TreeLeveId����ǰ���ظ�������������!");
								treeLevelSimplePage.getId().setFocus();
								return false;
							}
							RecursiveTreeLevel chidlevelre = new RecursiveTreeLevel();
							chidlevelre.setId(id);
							chidlevelre.setDataset(dataset);
							chidlevelre.setMasterKeyField(masterKeyField);
							chidlevelre.setLabelFields(labelField);
							chidlevelre.setRecursiveKeyField(recursiveKeyField);
							chidlevelre.setRecursivePKeyField(recursivePKeyField);
							chidlevelre.setLabelDelims(labelDelims);
							chidlevelre.setDetailKeyParameter(detailKeyParameter);
							if(originalLevel != null){
								originalLevel.setChildTreeLevel(chidlevelre);
							}else{
								topLevel.setChildTreeLevel(chidlevelre);
							}
						}
						treeViewcomp.setTopLevel(topLevel);
						
						//��treellevelElement����treelevel
						refds.setTreelevel(childLevel);
					}
				}
			}else if(type3){
				id = treeLevelCodeTreePage.getId().getText();
				treeobjnew.setId(id);
				dataset = treeLevelCodeTreePage.getDataset().getText();
				masterKeyField = treeLevelCodeTreePage.getMasterKeyField().getText();
				labelFields = treeLevelCodeTreePage.getRightlabelFields().getItems();
				labelField = "";
				for (int i = 0; i < labelFields.length; i++) {
					if(i != labelFields.length - 1)
						labelField += labelFields[i] + ",";
					else
						labelField += labelFields[i];
				}
				labelDelims = treeLevelCodeTreePage.getLabelDelims().getText();
				recursiveKeyField = treeLevelCodeTreePage.getRecursiveKeyField().getText();;
				recursivePKeyField = treeLevelCodeTreePage.getRecursivePKeyField().getText();
				codeField = treeLevelCodeTreePage.getCodeField().getText();
				codeRule = treeLevelCodeTreePage.getCodeField().getText();
				if(topLevel == null){
					CodeTreeLevel topLevelcode = new CodeTreeLevel();
					topLevelcode.setId(id);
					topLevelcode.setDataset(dataset);
					topLevelcode.setMasterKeyField(masterKeyField);
					topLevelcode.setRecursiveKeyField(recursiveKeyField);
					topLevelcode.setRecursivePKeyField(recursivePKeyField);
					topLevelcode.setLabelFields(labelField);
					topLevelcode.setCodeField(codeField);
					topLevelcode.setCodeRule(codeRule);
					topLevelcode.setLabelDelims(labelDelims);
					treeViewcomp.setTopLevel(topLevelcode);
				}
				else{
					//�Ƿ������޸Ĵ�TopLevel
					if(topLevel.getDataset().equals(refds.getDs().getId())){
						TreeLevel childLev = topLevel.getChildTreeLevel();
						treeViewcomp.setTopLevel(null);
						CodeTreeLevel topLevelcode = new CodeTreeLevel();
						topLevelcode.setChildTreeLevel(childLev);
						topLevelcode.setId(id);
						topLevelcode.setDataset(dataset);
						topLevelcode.setMasterKeyField(masterKeyField);
						topLevelcode.setRecursiveKeyField(recursiveKeyField);
						topLevelcode.setRecursivePKeyField(recursivePKeyField);
						topLevelcode.setDetailKeyParameter(detailKeyParameter);
						topLevelcode.setLabelFields(labelField);
						topLevelcode.setCodeField(codeField);
						topLevelcode.setCodeRule(codeRule);
						topLevelcode.setLabelDelims(labelDelims);
						treeViewcomp.setTopLevel(topLevelcode);
					}
					else{
						if(!topLevel.getDataset().equals(refds.getId()))
							levelIds.add(topLevel.getId());
						detailKeyParameter = treeLevelCodeTreePage.getDetailKeyParameter().getText();
						TreeLevel childLevel = topLevel.getChildTreeLevel();
						TreeLevel originalLevel = null;
						while(childLevel != null){
							if(childLevel.getDataset().equals(refds.getDs().getId())){
								TreeLevel childchiLevel = childLevel.getChildTreeLevel();
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(null);
								}else{
									topLevel.setChildTreeLevel(null);
								}
								CodeTreeLevel chidlevelcode = new CodeTreeLevel();
								chidlevelcode.setId(id);
								chidlevelcode.setDataset(dataset);
								chidlevelcode.setMasterKeyField(masterKeyField);
								chidlevelcode.setLabelFields(labelField);
								chidlevelcode.setRecursiveKeyField(recursiveKeyField);
								chidlevelcode.setRecursivePKeyField(recursivePKeyField);
								chidlevelcode.setCodeField(codeField);
								chidlevelcode.setCodeRule(codeRule);
								chidlevelcode.setLabelDelims(labelDelims);
								chidlevelcode.setDetailKeyParameter(detailKeyParameter);
								chidlevelcode.setChildTreeLevel(childchiLevel);
								if(originalLevel != null){
									originalLevel.setChildTreeLevel(chidlevelcode);
								}else{
									topLevel.setChildTreeLevel(chidlevelcode);
								}
								break;
							}
							else{
								if(!childLevel.getDataset().equals(refds.getId()))
									levelIds.add(childLevel.getId());
								originalLevel = childLevel;
								childLevel = childLevel.getChildTreeLevel();
							}
						}
						if(childLevel == null){
							if(levelIds.contains(id)){
								MessageDialog.openError(null, "TreeLevl ����", "TreeLeveId����ǰ���ظ�������������!");
								treeLevelSimplePage.getId().setFocus();
								return false;
							}
							CodeTreeLevel chidlevelcode = new CodeTreeLevel();
							chidlevelcode.setId(id);
							chidlevelcode.setDataset(dataset);
							chidlevelcode.setMasterKeyField(masterKeyField);
							chidlevelcode.setLabelFields(labelField);
							chidlevelcode.setCodeField(codeField);
							chidlevelcode.setCodeRule(codeRule);
							chidlevelcode.setRecursiveKeyField(recursiveKeyField);
							chidlevelcode.setRecursivePKeyField(recursivePKeyField);
							chidlevelcode.setLabelDelims(labelDelims);
							chidlevelcode.setDetailKeyParameter(detailKeyParameter);
							if(originalLevel != null){
								originalLevel.setChildTreeLevel(chidlevelcode);
							}else{
								topLevel.setChildTreeLevel(chidlevelcode);
							}
						}
						treeViewcomp.setTopLevel(topLevel);
					}
					
					//��treellevelElement����treelevel
					//refds.setTreelevel(childLevel);
				}
			}
			LfwWidget lfwwidget = LFWPersTool.getLfwWidget();
			Map<String, WebComponent> map = lfwwidget.getViewComponents().getComponentsMap();
			map.put(treeViewcomp.getId(), treeViewcomp);
			LFWPersTool.setLfwWidget(lfwwidget);
			ModifyTreeLevelCommand cmd = new ModifyTreeLevelCommand();
			if(TreeEditor.getActiveEditor() != null)
				TreeEditor.getActiveEditor().executComand(cmd);
			return true;
		}
		return false;
	}
}
