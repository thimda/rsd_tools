package nc.uap.portal.freemarker.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.uap.portal.freemarker.configuration.ConfigurationManager;
import nc.uap.portal.freemarker.configuration.MacroLibrary;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.source.ISourceViewer;


public class MacroInstance extends AbstractDirective {

	private MacroEndInstance endInstance;
	private String name;

	protected void init(ITypedRegion region, ISourceViewer viewer, IResource resource) throws Exception {
		name = getSplitValue(0);
	}

	public boolean isStartItem() {
		return true;
	}

	public void relateItem(Item directive) {
		if (directive instanceof MacroEndInstance)
			endInstance = (MacroEndInstance) directive;
	}

	public boolean relatesToItem(Item directive) {
		if (directive instanceof MacroEndInstance) {
			MacroEndInstance endDirective = (MacroEndInstance) directive;
			return (null == endDirective.getName()
					|| endDirective.getName().length() == 0
					|| endDirective.getName().equals(getName()));
		}
		else return false;
	}

	public MacroEndInstance getEndDirective() {
		return endInstance;
	}

	public Item[] getRelatedItems() {
		if (null == relatedItems) {
			ArrayList l = new ArrayList();
			if (null != getEndDirective())
				l.add(getEndDirective());
			relatedItems = (Item[]) l.toArray(new Item[l.size()]);
		}
		return relatedItems;
	}
	private Item[] relatedItems;

	public String getTreeImage() {
		return "macro_instance.png";
	}

	public String getName() {
		return name;
	}

	public ICompletionProposal[] getCompletionProposals(int offset, Map context) {
		ContentWithOffset contentWithOffset = splitContents(offset);
		int index = contentWithOffset.getIndex();
		int subOffset = contentWithOffset.getOffsetInIndex();
		int directiveOffset = contentWithOffset.getOffset();
		String[] contents = contentWithOffset.getContents();
		if (index == 0 && !contentWithOffset.wasLastCharSpace()) {
			// name
			String prefix = contents[index].substring(0, subOffset);
			List l = new ArrayList();
			for (Iterator i=getItemSet().getMacroDefinitions().iterator(); i.hasNext(); ) {
				MacroDirective macro = (MacroDirective) i.next();
				if (macro.getName().startsWith(prefix)) {
					l.add(getCompletionProposal(offset, subOffset,
							macro.getName(), contents[0]));
				}
			}
			MacroLibrary[] libraries = ConfigurationManager.getInstance(getResource().getProject()).getMacroLibraries();
			for (int i=0; i<libraries.length; i++) {
				for (int j=0; j<libraries[i].getMacros().length; j++) {
					MacroDirective macro = libraries[i].getMacros()[j];
					if (macro.getName().startsWith(prefix)) {
						l.add(getCompletionProposal(offset, subOffset,
								macro.getName(), contents[0]));
					}
				}
			}
			return completionProposals(l);
		}
		else if ((contentWithOffset.wasLastCharSpace())
				|| !contents[index-1].equals("=")) {
			String name = contents[0];
			// see if we can find a macro match
			MacroDirective match = null;
			for (Iterator i=getItemSet().getMacroDefinitions().iterator(); i.hasNext(); ) {
				MacroDirective macro = (MacroDirective) i.next();
				if (macro.getName().equals(name)) {
					match = macro;
					break;
				}
			}
			if (null == match) {
				MacroLibrary[] libraries = ConfigurationManager.getInstance(getResource().getProject()).getMacroLibraries();
				for (int i=0; i<libraries.length; i++) {
					for (int j=0; j<libraries[i].getMacros().length; j++) {
						MacroDirective macro = libraries[i].getMacros()[j];
						if (macro.getName().equals(name)) {
							match = macro;
							break;
						}
					}
					if (null != match) break;
				}
			}
			if (null != match) {
				String prefix = null;
				if (contentWithOffset.wasLastCharSpace() || contents.length < index+1)
					prefix = "";
				else
					prefix = contents[index].substring(0, subOffset);
				List l = new ArrayList();
				for (int i=0; i<match.getAttributes().length; i++) {
					if (match.getAttributes()[i].startsWith(prefix)) {
						l.add(getCompletionProposal(offset, subOffset,
								match.getAttributes()[i], (contentWithOffset.isNextCharSpace() || contents.length < index+1) ? "" : contents[index]));
					}
				}
				return completionProposals(l);
			}
		}
		return null;
	}
}