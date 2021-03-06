package nc.uap.portal.freemarker.model;

//import hudson.freemarker_ide.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.ISourceViewer;

public class ItemSet {

	private ISourceViewer viewer;
	private List regions;
	private List directives;
	private List topLevelDirectives;
	private Map directiveRegions;
	private List macroDefinitions = new ArrayList();

	public ItemSet (ISourceViewer viewer, IResource resource) {
		this.viewer = viewer;
		regions = new ArrayList();
		// get all regions
		int index = 0;
		while (true) {
			try {
				ITypedRegion region = viewer.getDocument().getPartition(index);
				regions.add(region);
				index = region.getOffset() + region.getLength() + 1;
			}
			catch (BadLocationException e) {
				break;
			}
		}
		parse(viewer, resource);
	}

	private void parse (ISourceViewer viewer, IResource resource) {
		try {
			this.directives = new ArrayList();
			this.directiveRegions = new HashMap();
			this.topLevelDirectives = new ArrayList();
			
			Stack stackDirectives = new Stack();
			List fullDirectives = new ArrayList();
			for (Iterator i=regions.iterator(); i.hasNext(); ) {
				ITypedRegion region = (ITypedRegion) i.next();
				Item directive = ItemFactory.getItem(region, viewer, resource);
				if (null != directive) {
					directive.setItemSet(this);
					if (directive instanceof MacroDirective) {
						macroDefinitions.add(directive);
					}
					if (stackDirectives.size() == 0) {
						topLevelDirectives.add(directive);
					}
					directiveRegions.put(new Integer(region.getOffset()), directive);
					if (!directive.isEndItem()) directives.add(directive);
					if (!directive.isStartItem()) {
						Item directiveCheck = getFirstNestableItem(stackDirectives);
						if (directive.isStartAndEndItem()) {
							// not a true nestable but sub items will be nested
							if (null != directiveCheck && directiveCheck.isStartAndEndItem()) {
								if (directiveCheck.relatesToItem(directive)) {
									directiveCheck.relateItem(directive);
									directive.relateItem(directiveCheck);
								}
								stackDirectives.pop();
								directiveCheck = getFirstNestableItem(stackDirectives);
							}
							directiveCheck = getFirstNestableItem(stackDirectives);
							if (null != directiveCheck) {
								directiveCheck.addSubDirective(directive);
								directiveCheck.relateItem(directive);
								directive.relateItem(directiveCheck);
							}
							stackDirectives.push(directive);
						}
						else {
							if (null != directiveCheck && directive.isEndItem() && directiveCheck.isStartAndEndItem()) {
								if (directiveCheck.relatesToItem(directive)) {
									directiveCheck.relateItem(directive);
									directive.relateItem(directiveCheck);
								}
								stackDirectives.pop();
								directiveCheck = getFirstNestableItem(stackDirectives);
							}
							if (null != directiveCheck && directiveCheck.relatesToItem(directive)) {
								directiveCheck.relateItem(directive);
								directive.relateItem(directiveCheck);
								if (directive.isEndItem()) {
									Item peek = (Item) stackDirectives.peek();
									while (null != peek && peek.relatesToItem(directive)) {
										if (peek.isStartItem()) {
											stackDirectives.pop();
											break;
										}
										else {
											stackDirectives.pop();
											peek = (Item) ((stackDirectives.size()>0) ? stackDirectives.peek() : null);
										}
									}
								}
								else {
									directiveCheck.addSubDirective(directive);
									stackDirectives.push(directive);
								}
							}
							else if (!directive.isNestable() && !directive.isEndItem()) {
								if (null != directiveCheck) {
									directiveCheck.addSubDirective(directive);
								}
							}
							else if (directive.isNestable() && !directive.isEndItem()) {
								if (null != directiveCheck) {
									directiveCheck.addSubDirective(directive);
									stackDirectives.push(directive);
								}
							}
							else {
								// we have an invalid stack
								// FIXME come up with a better way to handle this
								return;
							}
						}
					}
					else {
						if (stackDirectives.size() > 0) {
							((Item) stackDirectives.peek()).addSubDirective(directive);
						}
						if (directive.isNestable())
							stackDirectives.push(directive);
					}
				}
			}
		}
		catch (Exception e) {
			PortalPlugin.getDefault().logError(e.getMessage(), e);
		}
		Collections.sort(macroDefinitions);
	}

	private Item getFirstNestableItem (Stack directives) {
		if (directives.size() == 0) return null;
		else {
			Item directiveCheck = null;
			for (int i=directives.size()-1; i>=0; i++){
				directiveCheck = (Item) directives.get(i);
				if (directiveCheck.isNestable()) return directiveCheck;
			}
			return null;
		}
	}

	public Item[] getRootItems () {
		return (Item[]) topLevelDirectives.toArray(
				new Item[topLevelDirectives.size()]);
	}

	public Item getSelectedItem (int offset) {
		ITypedRegion region = getRegion(offset);
		if (null == region) return null;
		else return (Item) directiveRegions.get(new Integer(region.getOffset()));
	}

	public Item getContextItem (int offset) {
		Item directive = getSelectedItem(offset);
		if (null == directive && null != directives) {
			Item dt = null;
			for (Iterator i=directives.iterator(); i.hasNext(); ) {
				Item t = (Item) i.next();
				if (t.isNestable() && t.getRegion().getOffset() < offset)
					dt = t;
				else if (t.isEndItem() && t.getRegion().getOffset() < offset)
					dt = null;
			}
			return dt;
		}
		else return directive;
	}

	private ITypedRegion getRegion (int offset) {
		try {
			return viewer.getDocument().getPartition(offset);
		}
		catch (BadLocationException e) {
			return null;
		}
	}

	public List getMacroDefinitions() {
		return macroDefinitions;
	}

	public Item getPreviousItem (int offset) {
		Item item = getContextItem(offset);
		if (null == item) {
			for (Iterator i=directives.iterator(); i.hasNext(); ) {
				Item itemSub = (Item) i.next();
				if (itemSub.getRegion().getOffset() + itemSub.getRegion().getOffset() < offset)
					item = itemSub;
				else
					break;
			}
		}
		return item;
	}

	public Item getPreviousStartItem (int offset) {
		Item item = null;
		for (Iterator i=directives.iterator(); i.hasNext(); ) {
			Item itemSub = (Item) i.next();
			if (itemSub.getRegion().getOffset() > offset) break;
			if (itemSub.isStartItem()) {
				Item itemSub2 = itemSub.getEndItem();
				if (null == itemSub2 || itemSub2.getRegion().getOffset() > offset)
					item = itemSub;
			}
		}
		return item;
	}

	public Item getItem (IRegion region) {
		if (null == directiveRegions) return null;
		else return (Item) directiveRegions.get(region);
	}

	public Item getItem (int offset) {
		for (Iterator i=directives.iterator(); i.hasNext(); ) {
			Item item = (Item) i.next();
			if (item.getRegion().getOffset() <= offset && item.getRegion().getOffset() + item.getRegion().getLength() >= offset)
				return item;
		}
		return null;
	}
}