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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

/**
 * 逐渐显示逐渐消失的窗口，用户可以覆盖其方法实现自己的窗口布局
 * 
 * @author luma
 */
public class DieAway {
	private Runnable redrawRunnable = new Runnable() {
		public void run() {
			shell.redraw();
		}
	};
	
	// 窗口shell对象
	protected Shell shell;
	// 窗口内容缓冲
	private ImageData bufferData;
	// 图片大小，一般等于窗口大�?
	private Rectangle imageBound;
	// 显示，隐藏标�?
	private boolean showing, hiding;
	
	// alpha递进和�?减的大小
	private int alphaDelta;
	// 动画效果持续时间
	protected int enduringTime;
	// 帧延�?
	protected int frameDelay;
	
	// 当前的alpha�?
	private int currentAlpha;
	
	// 窗口的背�?
	private Image background;
	
	/**
	 * 创建�?��DieAway窗口实例
	 * 
	 * @param parent
	 * 		父窗�?
	 */
	public DieAway(Shell parent) {
		shell = new Shell(parent, SWT.ON_TOP | SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.NO_MERGE_PAINTS);		
		
		showing = hiding = false;
		setEnduringTime(1500);
		setFrameDelay(50);
		
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				createBufferImage();
				if(showing) {
					// adjust alpha
					currentAlpha = min(currentAlpha + alphaDelta, 255);
					if(currentAlpha == 255)
						showing = false;
					
					// do paint
					internalPaint(e);
					
					// prepare next redraw
					if(showing) 
						shell.getDisplay().timerExec(frameDelay, redrawRunnable);
				} else if(hiding) {			
					// adjust alpha
					currentAlpha = max(currentAlpha - alphaDelta, 0);
					if(currentAlpha == 0) {
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
					bufferData.alpha = 255;
					Image img = new Image(shell.getDisplay(), bufferData);
					e.gc.drawImage(img, 0, 0);
					img.dispose();
				}
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
		
		shell.setSize(getInitialSize());
		shell.setLocation(getInitialLocation());
		shell.layout();
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
		bufferData.alpha = currentAlpha;
		Image img = new Image(shell.getDisplay(), bufferData);
		
		// 创建缓冲�?
		Image buffer = new Image(shell.getDisplay(), shell.getClientArea());
		GC gc = new GC(buffer);
		
		// draw
		gc.drawImage(background, 0, 0);
		gc.drawImage(img, 0, 0);
		e.gc.drawImage(buffer, 0, 0);
		img.dispose();
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
		if(showing || currentAlpha == 255)
			return;
		if(hiding) {
			hiding = false;
			showing = true;			
		} else {
			currentAlpha = 0;
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
		if(hiding || currentAlpha == 0)
			return;
		if(showing) {
			showing = false;
			hiding = true;			
		} else {
			currentAlpha = 255 + alphaDelta;
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
		Point size = shell.getSize();
		Point loc = new Point(dispRect.width - size.x - 3, dispRect.height - size.y - 3);
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
		bufferData = null;
		if(background != null && !background.isDisposed())
			background.dispose();
	}

	/**
	 * 创建缓冲image
	 */
	protected void createBufferImage() {
		if(bufferData == null) {
			Image bufferImage = new Image(shell.getDisplay(), shell.getClientArea());
			imageBound = bufferImage.getBounds();
			GC gc = new GC(bufferImage);
			performPaint(gc);
			gc.dispose();
			bufferData = bufferImage.getImageData();
			bufferImage.dispose();
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
		calculateAlphaDelta();
	}

	/**
	 * 计算alpha间隔
	 */
	private void calculateAlphaDelta() {
		alphaDelta = 255 * frameDelay / enduringTime + 1;
		alphaDelta = max(1, alphaDelta);
		alphaDelta = min(255, alphaDelta);
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
		calculateAlphaDelta();
	}
}
