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

import static java.lang.Math.max;
import static java.lang.Math.min;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

/**
 * MSN风格窗口
 * 
 * 支持样式:
 * SWT.LEFT: 从左边出�?
 * SWT.RIGHT: 从右边出�?
 * SWT.TOP: 从上面出�?
 * SWT.BOTTOM: 从下面出�?
 * 
 * LEFT和RIGHT，只能出现一�? TOP和BOTTOM，只能出现一�?
 * 
 * 你可以调整bubble的enduringtime和framedelay来调整效果的平滑程度�?
 * 这两个参数的设置必须在open之前进行
 * 
 * @author luma
 */
public class Bubble {
	private Runnable autoHideRunnable = new Runnable() {
		public void run() {
			if(mouseOn && !autoHideWhenMouseOn)
				shell.getDisplay().timerExec(autoHideDelay, this);
			else
				close();
		}
	};
	
	private Runnable redrawRunnable = new Runnable() {
		public void run() {
			shell.redraw();
		}
	};
	
	// 窗口shell对象
	protected Shell shell;
	// 窗口内容缓冲
	protected Image bufferImage;
	// 图片大小，一般等于窗口大�?
	protected Rectangle imageBound;
	// 显示，隐藏标�?
	protected boolean showing, hiding;
	
	// 动画效果持续时间
	protected int enduringTime;
	// 帧延�?
	protected int frameDelay;
	// x和y方向的delta
	protected int deltaX, deltaY;
	// 当前宽度
	protected int width, height;
	// 窗口大小
	protected Point size;
	
	protected int horizontal, vertical;
	
	// 是否自动关闭
	protected boolean autoHide;
	// 显示多少毫秒后自动关�?
	protected int autoHideDelay;
	
	// 鼠标是否在窗口之�?
	protected boolean mouseOn;
	// 鼠标在窗口上时是否关闭窗�?
	protected boolean autoHideWhenMouseOn;
	
	// 窗口的背�?
	protected Image background;
	
	/**
	 * 创建�?��Bubble窗口实例
	 * 
	 * @param parent
	 * 		父窗�?
	 * @param style
	 * 		样式
	 */
	public Bubble(Shell parent, int style) {
		shell = new Shell(parent, SWT.ON_TOP | SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.NO_MERGE_PAINTS);		
		
		checkStyle(style);
		showing = hiding = false;
		autoHideWhenMouseOn = false;
		mouseOn = false;
		autoHide = true;
		autoHideDelay = 5000;
		setEnduringTime(500);
		setFrameDelay(50);
		
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				createBufferImage();
				if(showing) {
					// 增加显示区域
					increaseArea();
					if(isFullShow())
						showing = false;
					
					// do paint
					internalPaint(e);
					
					// prepare next redraw
					if(showing)
						shell.getDisplay().timerExec(frameDelay, redrawRunnable);
					else {
						if(autoHide)
							shell.getDisplay().timerExec(autoHideDelay, autoHideRunnable);
					}
				} else if(hiding) {		
					// 减少显示区域
					decreaseArea();
					if(isFullHide()) {
						hiding = false;
						shell.setVisible(false);
						shell.getDisplay().asyncExec(new Runnable() {
							public void run() {
								shell.close();
							}
						});
					}
					
					// do paint
					internalPaint(e);
					
					// prepare next redraw
					if(hiding)			
						shell.getDisplay().timerExec(frameDelay, redrawRunnable);
				} else {
					e.gc.drawImage(bufferImage, 0, 0);
				}
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
		shell.addMouseTrackListener(new MouseTrackListener() {
			public void mouseEnter(MouseEvent e) {
				mouseOn = true;
			}
			public void mouseExit(MouseEvent e) {
				mouseOn = false;				
			}
			public void mouseHover(MouseEvent e) {
				onHover(e);
			}
		});
		
		size = getInitialSize();
		shell.setSize(size);
		shell.setLocation(getInitialLocation());
		shell.layout();
		calculateDelta();
	}

	/**
	 * 鼠标悬停时调用此方法，缺省什么也不做
	 * 
	 * @param e
	 * 		MouseEvent
	 */
	protected void onHover(MouseEvent e) {
	}

	/**
	 * @return
	 * 		true表示已经全部显示�?
	 */
	private boolean isFullShow() {
		return width == size.x && height == size.y;
	}
	
	/**
	 * @return
	 * 		true表示已经全部隐藏�?
	 */
	private boolean isFullHide() {
		return width == 0 || height == 0;
	}

	/**
	 * 收缩显示区域
	 */
	private void decreaseArea() {
		width = max(width - deltaX, 0);
		height = max(height - deltaY, 0);
		if(horizontal == SWT.NONE)
			width = size.x;
		if(vertical == SWT.NONE)
			height = size.y;
	}

	/**
	 * 调整窗口显示区域
	 */
	private void increaseArea() {
		width = min(width + deltaX, size.x);
		height = min(height + deltaY, size.y);
		if(horizontal == SWT.NONE)
			width = size.x;
		if(vertical == SWT.NONE)
			height = size.y;
	}

	/**
	 * �?��样式
	 * 
	 * @param style
	 */
	private void checkStyle(int style) {
		style &= SWT.NONE | SWT.LEFT | SWT.RIGHT | SWT.TOP | SWT.BOTTOM;
		if((style & SWT.LEFT) != 0)
			horizontal = SWT.LEFT;
		else if((style & SWT.RIGHT) != 0)
			horizontal = SWT.RIGHT;
		else
			horizontal = SWT.NONE;
		
		if((style & SWT.TOP) != 0)
			vertical = SWT.TOP;
		else if((style & SWT.BOTTOM) != 0)
			vertical = SWT.BOTTOM;
		else
			vertical = SWT.NONE;
		
		// 缺省
		if(horizontal == SWT.NONE && vertical == SWT.NONE)
			vertical = SWT.BOTTOM;
	}

	/**
	 * 计算移动的delta
	 */
	private void calculateDelta() {
		Point shellSize = shell.getSize();
		deltaX = shellSize.x * frameDelay / enduringTime + 1;
		deltaY = shellSize.y * frameDelay / enduringTime + 1;
	}

	/**
	 * 内部画图操作，用来画�?��背景，然后画正式窗口内容
	 * 
	 * @param e
	 * 		PaintEvent
	 */
	private void internalPaint(PaintEvent e) {
		if(isDisposed())
			return;
		
		// 创建缓冲�?
		Image buffer = new Image(shell.getDisplay(), shell.getClientArea());
		GC gc = new GC(buffer);
		
		// draw background
		gc.drawImage(background, 0, 0);
		// draw window
		gc.drawImage(bufferImage, 
				(horizontal == SWT.LEFT) ? (size.x - width) : 0,
				(vertical == SWT.TOP) ? (size.y - height) : 0,
				width,
				height,
				(horizontal == SWT.LEFT) ? 0 : (size.x - width),
				(vertical == SWT.TOP) ? 0 : (size.y - height),
				width,
				height);
		// draw buffer to screen
		e.gc.drawImage(buffer, 0, 0);
		
		// release resource
		gc.dispose();
		buffer.dispose();	
	}
	
	/**
	 * @return
	 * 		true表示shell已经释放
	 */
	public boolean isDisposed() {
		return shell.isDisposed();
	}

	/**
	 * 打开窗口
	 */
	public void open() {
		if(showing || isFullShow())
			return;
		if(hiding) {
			hiding = false;
			showing = true;			
		} else {
			width = -deltaX;
			height = -deltaY;
			showing = true;			
			createInitialBackground();
			shell.setVisible(true);			
		}
	}
	
	/**
	 * 创建初始的背�?
	 */
	private void createInitialBackground() {
		if(background != null && !background.isDisposed())
			background.dispose();
		GC gc = new GC(shell.getDisplay());
		Rectangle bound = shell.getBounds();
		background = new Image(shell.getDisplay(), bound);
		gc.copyArea(background, bound.x, bound.y);
		gc.dispose();
	}
	
	/**
	 * 关闭窗口
	 */
	public void close() {
		if(hiding || isFullHide())
			return;
		if(showing) {
			showing = false;
			hiding = true;			
		} else {
			width = size.x + deltaX;
			height = size.y + deltaY;
			hiding = true;
			shell.redraw();			
		}
	}
	
	/**
	 * @return
	 * 		初始位置，缺省显示在屏幕右下�?
	 */
	protected Point getInitialLocation() {
		Rectangle dispRect = shell.getDisplay().getClientArea();
		Point shellSize = shell.getSize();
		Point loc = new Point(dispRect.width - shellSize.x - 3, dispRect.height - shellSize.y - 3);
		return loc;
	}
	
	/**
	 * @return
	 * 		窗口初始大小
	 */
	protected Point getInitialSize() {
		return new Point(150, 80);
	}

	/**
	 * 释放资源
	 */
	protected void dispose() {
		if(bufferImage != null && !bufferImage.isDisposed())
			bufferImage.dispose();
		if(background != null && !background.isDisposed())
			background.dispose();
	}

	/**
	 * 创建缓冲image
	 */
	protected void createBufferImage() {
		if(bufferImage == null || bufferImage.isDisposed()) {
			bufferImage = new Image(shell.getDisplay(), shell.getClientArea());
			imageBound = bufferImage.getBounds();
			GC gc = new GC(bufferImage);
			performPaint(gc);
			gc.dispose();
		}
	}
	
	/**
	 * 画窗�?
	 * 
	 * @param gc
	 * 		GC
	 */
	protected void performPaint(GC gc) {		
		gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		gc.fillRectangle(imageBound);
		gc.drawRectangle(0, 0, imageBound.width - 1, imageBound.height - 1);
	}

	/**
	 * @return Returns the enduringTime.
	 */
	public int getEnduringTime() {
		return enduringTime;
	}

	/**
	 * @param enduringTime The enduringTime to set.
	 */
	public void setEnduringTime(int enduringTime) {
		this.enduringTime = enduringTime;
	}

	/**
	 * @return Returns the frameDelay.
	 */
	public int getFrameDelay() {
		return frameDelay;
	}

	/**
	 * @param frameDelay The frameDelay to set.
	 */
	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}

	/**
	 * @return Returns the autoHide.
	 */
	public boolean isAutoHide() {
		return autoHide;
	}

	/**
	 * @param autoHide The autoHide to set.
	 */
	public void setAutoHide(boolean autoHide) {
		this.autoHide = autoHide;
	}

	/**
	 * @return Returns the autoHideDelay.
	 */
	public int getAutoHideDelay() {
		return autoHideDelay;
	}

	/**
	 * @param autoHideDelay The autoHideDelay to set.
	 */
	public void setAutoHideDelay(int autoHideDelay) {
		this.autoHideDelay = autoHideDelay;
	}

	/**
	 * @return Returns the autoHideWhenMouseOn.
	 */
	public boolean isAutoHideWhenMouseOn() {
		return autoHideWhenMouseOn;
	}

	/**
	 * @param autoHideWhenMouseOn The autoHideWhenMouseOn to set.
	 */
	public void setAutoHideWhenMouseOn(boolean autoHideWhenMouseOn) {
		this.autoHideWhenMouseOn = autoHideWhenMouseOn;
	}
}
