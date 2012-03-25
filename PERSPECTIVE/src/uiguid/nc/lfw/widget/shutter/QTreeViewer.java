/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package nc.lfw.widget.shutter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

/**
 * QTree的包装类，提供一个MVC形式的组件，类似TreeViewer和Tree的关�?
 * 
 * @author luma
 */
public class QTreeViewer<E> {
	private QTree tree;
	private IQTreeContentProvider<E> contentProvider;
	private IQTreeLabelProvider<E> labelProvider;
	private Object input;
	
	private Map<E, QItem> registry;
	
	private Comparator<E> sorter;
	private IFilter<E> filter;
	
	private List<E> temp;
	
	private Map<E, Animation> animateCache;
	private List<E> expandedCache;
	
	/**
	 * 创建�?��Viewer
	 * 
	 * @param parent
	 * 		父容�?
	 */
	public QTreeViewer(Composite parent) {
		tree = new QTree(parent);
		registry = new HashMap<E, QItem>();
		temp = new ArrayList<E>();
	}
	
	/**
	 * 释放组件资源
	 */
	public void dispose() {
		tree.dispose();
	}
	
	/**
	 * @return
	 * 		true表示已经释放
	 */
	public boolean isDisposed() {
		return tree.isDisposed();
	}
	
	/**
	 * 展开�?��item
	 * 
	 * @param model		
	 * 		model对象
	 */
	public void expandItem(E model) {
		QItem item = getItem(model);
		if(item == null)
			return;
		
		item.setExpanded(true);
	}
	
	/**
	 * 收起�?��item
	 * 
	 * @param model		
	 * 		model对象
	 */
	public void collapseItem(E model) {
		QItem item = getItem(model);
		if(item == null)
			return;
		
		item.setExpanded(false);
	}
	
	/**
	 * 为QTree添加拖动支持
	 * 
	 * @param operations
	 *            a bitwise OR of the supported drag and drop operation types (
	 *            <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *            <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *            the transfer types that are supported by the drag operation
	 * @param listener
	 *            the callback that will be invoked to set the drag data and to
	 *            cleanup after the drag and drop operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addDragSupport(int operations, Transfer[] transferTypes, DragSourceListener listener) {
		DragSource dragSource = new DragSource(tree, operations);
		dragSource.setTransfer(transferTypes);
		dragSource.addDragListener(listener);
	}
	
	/**
	 * 为QTree添加拖放支持
	 * 
	 * @param operations
	 *            a bitwise OR of the supported drag and drop operation types (
	 *            <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *            <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *            the transfer types that are supported by the drop operation
	 * @param listener
	 *            the callback that will be invoked after the drag and drop
	 *            operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addDropSupport(int operations, Transfer[] transferTypes, DropTargetListener listener) {
		DropTarget dropTarget = new DropTarget(tree, operations);
		dropTarget.setTransfer(transferTypes);
		dropTarget.addDropListener(listener);
	}
	
	/**
	 * 展开�?��根节�?
	 */
	public void expandAll() {
		for(E e : registry.keySet()) {
			labelProvider.setExpanded(e, true);
			registry.get(e).setExpanded(true, false);
			doUpdateItem(e, false);
		}
		tree.redraw();
	}
	
	/**
	 * 收起�?��根节�?
	 */
	public void collapseAll() {
		for(E e : registry.keySet()) {
			labelProvider.setExpanded(e, false);
			registry.get(e).setExpanded(false, false);
			doUpdateItem(e, false);
		}
		tree.redraw();
	}
	
	/**
	 * 编辑文本
	 * 
	 * @param model
	 * 		model对象
	 */
	public void editText(E model) {
		QItem item = getItem(model);
		if(item == null)
			return;
		
		tree.editItemText(item);
	}
	
	/**
	 * 保存当前的根节点展开状�?
	 */
	public void saveExpandStatus() {
		if(expandedCache == null)
			expandedCache = new ArrayList<E>();
		expandedCache.clear();
		for(E e : registry.keySet()) {
			if(labelProvider.isExpaned(e))
				expandedCache.add(e);
		}
	}
	
	/**
	 * 恢复根节点的张开状�?
	 */
	public void restoreExpandStatus() {
		if(expandedCache == null)
			return;
		for(E e : registry.keySet()) {
			labelProvider.setExpanded(e, false);
			registry.get(e).setExpanded(false, false);
		}
		for(E e : expandedCache) {
			labelProvider.setExpanded(e, true);			
			registry.get(e).setExpanded(true, true);
		}
		tree.redraw();
	}
	
	/**
	 * 刷新全部  
	 */
	public void refresh() {
		saveAnimationStatus();
		tree.stopAllAnimation();
		E[] roots = contentProvider.getElements(input);
		roots = filterModels(roots);
		if(sorter != null)
			Arrays.sort(roots, sorter);
		doUpdateRoot(roots, false);
		for(int i = 0; i < roots.length; i++) 
			doUpdateChildren(roots[i], false, true);
		tree.refresh();
		restoreAnimationStatus();
	}
	
	/**
	 * 刷新�?��item的显示，不刷新子item。如果这个model没有对应的item，则不操�?
	 * 
	 * @param model
	 * 		model对象
	 */
	public void refresh(E model) {
		refresh(model, false);
	}
	
	/**
	 * 刷新�?��item的显示�?如果这个model没有对应的item，则不操�?
	 * 
	 * @param model
	 * 		model对象
	 * @param includeChildren
	 * 		true表示也刷新其子item
	 */
	public void refresh(E model, boolean includeChildren) {
		QItem item = registry.get(model);
		if(item == null)
			return;
		
		if(includeChildren)
			refreshChildren(contentProvider.getParent(model));
		doUpdateItem(model, true);
	}
	
	/**
	 * 刷新子item，不刷新自己
	 * 
	 * @param model
	 */
	public void refreshChildren(E model) {
		QItem item = registry.get(model);
		if(item == null)
			return;
		
		saveAnimationStatus();
		tree.stopAllAnimation();
		doUpdateChildren(model, true);
		restoreAnimationStatus();
	}
	
	/**
	 * 刷新子节�?
	 * 
	 * @param model
	 * 		父节点对�?
	 * @param redraw
	 * 		是否重画
	 * @param includeSub
	 * 		true表示刷新父节点下的所有层
	 */
	private void doUpdateChildren(E model, boolean redraw, boolean includeSub) {
		if(includeSub) {
			doUpdateChildren(model, false);
			E[] children = contentProvider.getChildren(model);
			children = filterModels(children);
			for(int i = 0; i < children.length; i++) {
				doUpdateChildren(children[i], false, true);
			}
			if(redraw)
				tree.refresh();
		} else
			doUpdateChildren(model, redraw);
	}
	
	/**
	 * 刷新子节点，只刷新一层子节点
	 * 
	 * @param model
	 * 		父对�?
	 * @param redraw
	 * 		true更新后重�?
	 */
	private void doUpdateChildren(E model, boolean redraw) {		
		QItem parentItem = registry.get(model);		
		QItem[] childrenItem = parentItem.getItems();
		E[] modelChildren = contentProvider.getChildren(model);		
		modelChildren = filterModels(modelChildren);
		if(sorter != null)
			Arrays.sort(modelChildren, sorter);
		for(int i = 0; i < modelChildren.length; i++) {
			if(i < childrenItem.length) {
				// 重用已有的item
				childrenItem[i].effect = null;
				registry.put(modelChildren[i], childrenItem[i]);
				doUpdateItem(childrenItem[i], modelChildren[i], false);
			} else {
				// 添加新的item
				QItem child = new QItem(parentItem, SWT.NONE);
				registry.put(modelChildren[i], child);
				doUpdateItem(child, modelChildren[i], false);
			}
		}
		// 删除多余的item
		if(modelChildren.length < childrenItem.length)
			parentItem.removeItemFrom(modelChildren.length);
		// 重画
		if(redraw)
			tree.refresh();
	} 
	
	/**
	 * 刷新根节�?
	 * 
	 * @param rootModels
	 * 		根节点model
	 * @param redraw
	 * 		是否重画
	 */
	private void doUpdateRoot(E[] rootModels, boolean redraw) {
		QItem[] roots = tree.getItems();
		for(int i = 0; i < rootModels.length; i++) {
			if(i < roots.length) {
				// 重用已有的item
				roots[i].effect = null;
				registry.put(rootModels[i], roots[i]);
				doUpdateItem(roots[i], rootModels[i], false);
			} else {
				// 添加新的item
				QItem child = new QItem(tree, SWT.NONE);
				registry.put(rootModels[i], child);
				doUpdateItem(child, rootModels[i], false);
			}
		}
		// 删除多余的item
		if(rootModels.length < roots.length)
			tree.removeItemFrom(rootModels.length);
		// 重画
		if(redraw)
			tree.redraw();
	}
	
	/**
	 * 更新�?��item，这个item�?��要存在�?这个方法不检查其存在�?
	 * 
	 * @param model
	 * 		model对象
	 * @param redraw
	 * 		是否重画
	 */
	private void doUpdateItem(E model, boolean redraw) {
		QItem item = registry.get(model);
		doUpdateItem(item, model, redraw);
	}
	
	/**
	 * 更新�?��item，item是指定的，model也是指定�?
	 * 
	 * @param item
	 * 		QItem
	 * @param model
	 * 		model对象
	 * @param redraw
	 * 		是否重画
	 */
	private void doUpdateItem(QItem item, E model, boolean redraw) {
		item.setData(model);
		for(int i = 0; i < QItem.MAX_ATTACHMENT; i++)
			item.addAttachment(i, labelProvider.getAttachment(model, i), false);
		item.setForeground(labelProvider.getForeground(model), false);
		item.setDecorationImage(labelProvider.getDecoration(model), false);
		item.setImage(labelProvider.getImage(model), false);
		item.setExpanded(labelProvider.isExpaned(model), false);
		item.setPrefix(labelProvider.getPrefix(model), false);
		item.setText(labelProvider.getText(model), redraw);
	}
	
	/**
	 * 过滤model
	 * 
	 * @param models
	 * 		model数组
	 * @return
	 * 		已过滤的数组
	 */
	@SuppressWarnings("unchecked")
	private E[] filterModels(E[] models) {
		if(filter == null)
			return models;
		temp.clear();
		for(int i = 0; i < models.length; i++) {
			if(filter.select(models[i]))
				temp.add(models[i]);
		}
		return (E[])temp.toArray();
	}
	
	/**
	 * @param model
	 * @return
	 * 		true表示目前有一个动画在进行
	 */
	public boolean hasAnimation(E model) {
		QItem item = getItem(model);
		if(item == null)
			return false;
		
		return tree.hasAnimation(item);
	}
	
	/**
	 * 得到model对应的item
	 * 
	 * @param model
	 * 		model对象
	 * @return
	 * 		QItem对象，如果没有，返回null
	 */
	public QItem getItem(E model) {
		return registry.get(model);
	}

	/**
	 * @return
	 * 		实际的QTree控件
	 */
	public QTree getQTree() {
		return tree;
	}

	/**
	 * @return Returns the contentProvider.
	 */
	public IQTreeContentProvider<E> getContentProvider() {
		return contentProvider;
	}

	/**
	 * @param contentProvider The contentProvider to set.
	 */
	public void setContentProvider(IQTreeContentProvider<E> contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * @return Returns the lableProvider.
	 */
	public IQTreeLabelProvider<E> getLabelProvider() {
		return labelProvider;
	}

	/**
	 * @param lableProvider The lableProvider to set.
	 */
	public void setLabelProvider(IQTreeLabelProvider<E> lableProvider) {
		this.labelProvider = lableProvider;
	}

	/**
	 * @return Returns the input.
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * @param input The input to set.
	 */
	public void setInput(Object input) {
		this.input = input;
		refresh();
	}

	/**
	 * @return Returns the sorter.
	 */
	public Comparator<E> getSorter() {
		return sorter;
	}

	/**
	 * @param sorter The sorter to set.
	 */
	public void setSorter(Comparator<E> sorter) {
		this.sorter = sorter;
	}

	/**
	 * @return Returns the filter.
	 */
	public IFilter<E> getFilter() {
		return filter;
	}

	/**
	 * @param filter The filter to set.
	 */
	public void setFilter(IFilter<E> filter) {
		this.filter = filter;
	}
	
	/**
	 * �?���?��动画
	 * 
	 * @param model
	 * @param type
	 */
	public void startAnimation(E model, Animation type) {
		QItem item = getItem(model);
		if(item == null)
			return;
		
		tree.startAnimation(item, type);
	}
	
	/**
	 * 停止�?��动画
	 * 
	 * @param model
	 */
	public void stopAnimation(E model) {
		QItem item = getItem(model);
		if(item == null)
			return;
		
		tree.stopAnimation(item);
	}
	
	/**
	 * 保存动画状�?
	 */
	@SuppressWarnings("unchecked")
	public void saveAnimationStatus() {
		if(animateCache == null)
			animateCache = new HashMap<E, Animation>();
		animateCache.clear();
		
		synchronized(tree.animateList) {
			for(QItem item : tree.animateList) {
				animateCache.put((E)item.getData(), Animation.valueOf(item.effect));
			}
		}
	}
	
	/**
	 * 恢复动画状�?
	 */
	public void restoreAnimationStatus() {
		for(E model : animateCache.keySet()) {
			startAnimation(model, animateCache.get(model));
		}
	}
	
	/**
	 * @return
	 * 		true表示�?��的根节点都是收起�?
	 */
	public boolean isAllRootCollapsed() {
		E[] roots = contentProvider.getElements(input);
		for(E e : roots) {
			if(labelProvider.isExpaned(e))
				return false;
		}
		return true;
	}
}
