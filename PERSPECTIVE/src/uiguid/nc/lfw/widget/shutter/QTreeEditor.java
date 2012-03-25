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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * QItem的文字编辑器
 * 
 * @author luma
 */
public class QTreeEditor {
	private QTree tree;
	private QItem item;
	private Control editor;
	private Rectangle editorRect;
	
	private Listener scrollbarListener;
	private Listener resizeListener;
	
	/**
	 * 为QTree添加直接编辑支持
	 * 
	 * @param tree
	 * 		QTree
	 */
	public QTreeEditor(QTree tree) {
		this.tree = tree;
		
		editorRect = new Rectangle(0, 0, 0, 0);
		scrollbarListener = new Listener() {
			public void handleEvent(Event event) {
				if(editor == null || editor.isDisposed())
					return;
				resize();
			}
		};
		resizeListener = new Listener() {
			public void handleEvent(Event event) {
				resize();
			}
		};
		
		tree.addListener(SWT.Resize, resizeListener);
		ScrollBar vBar = tree.getVerticalBar();
		if(vBar != null)
			vBar.addListener(SWT.Selection, scrollbarListener);
	}
	
	/**
	 * 释放这个组件
	 */
	public void dispose() {
		if(!tree.isDisposed()) {
			tree.removeListener(SWT.Resize, resizeListener);
			ScrollBar vBar = tree.getVerticalBar();
			if(vBar != null)
				vBar.removeListener(SWT.Selection, scrollbarListener);
		}
		
		tree = null;
		item = null;
		editor = null;
		editorRect = null;
		resizeListener = null;
		scrollbarListener = null;
	}
	
	/**
	 * 改变控件位置
	 */
	private void resize() {
		if (editor == null || editor.isDisposed()) 
			return;
		if (tree.isDisposed()) 
			return;
		if (item == null || item.isDisposed()) 
			return;		
	
		editor.setBounds(computeBounds());
		if(editor.isVisible())
			editor.setFocus();
	}
	
	/**
	 * 计算编辑器应该出现的位置
	 * 
	 * @return
	 * 		位置矩形
	 */
	private Rectangle computeBounds() {
		if(item == null || item.isDisposed())
			return QItem.DUMMY_BOUND;
		
		Rectangle textBound = item.getTextBound();
		editorRect.width = textBound.width + 10;
		editorRect.height = textBound.height;
		editorRect.x = textBound.x + tree.getItemIndent(item);
		editorRect.y = textBound.y + tree.getRelativeYAtItem(item);
		return editorRect;
	}

	/**
	 * @return Returns the editor.
	 */
	public Control getEditor() {
		return editor;
	}

	/**
	 * @param editor The editor to set.
	 */
	public void setEditor(Control editor) {
		if (editor == null) {
			this.editor = null;
			return;
		}
		
		this.editor = editor;		
		resize();
		if (editor == null || editor.isDisposed())
			return;
		editor.setVisible(true);
	}
	
	/**
	 * 设置编辑器空间和要编辑的Item
	 * 
	 * @param editor
	 * 		编辑器控�?
	 * @param item
	 * 		QItem
	 */
	public void setEditor(Control editor, QItem item) {
		setItem(item);
		setEditor(editor);
	}

	/**
	 * @return Returns the item.
	 */
	public QItem getItem() {
		return item;
	}

	/**
	 * @param item The item to set.
	 */
	public void setItem(QItem item) {
		this.item = item;
		resize();
	}
}
