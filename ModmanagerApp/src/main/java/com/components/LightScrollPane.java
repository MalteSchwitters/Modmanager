package com.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * * @beaninfo attribute: isContainer true attribute: containerDelegate
 * getViewport description: A specialized container that manages a viewport,
 * optional scrollbars and headers
 *
 * @author Vladimir Ikryanov
 */
public class LightScrollPane extends JComponent {

	private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
	private static final int SCROLL_BAR_ALPHA = 100;
	private static final int THUMB_BORDER_SIZE = 2;
	private static final int THUMB_SIZE = 8;
	private static final Color THUMB_COLOR = Color.BLACK;

	private JScrollPane scrollPane;
	private JScrollBar verticalScrollBar;
	private JScrollBar horizontalScrollBar;
	
	public LightScrollPane() {
		initialize();
	}
	
	public LightScrollPane(final JComponent component) {
		initialize();
		setViewportView(component);
	}
	
	private void initialize() {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayer(getVerticalScrollBar(), JLayeredPane.PALETTE_LAYER);
		layeredPane.setLayer(getHorizontalScrollBar(), JLayeredPane.PALETTE_LAYER);
		layeredPane.add(getHorizontalScrollBar());
		layeredPane.add(getVerticalScrollBar());
		layeredPane.add(getScrollPane());

		setLayout(new BorderLayout() {

			@Override
			public void layoutContainer(final Container target) {
				super.layoutContainer(target);
				int width = getWidth();
				int height = getHeight();
				getScrollPane().setBounds(0, 0, width, height);

				int scrollBarSize = 12;
				int cornerOffset = getVerticalScrollBar().isVisible()
						&& getHorizontalScrollBar().isVisible() ? scrollBarSize : 0;
				if (getVerticalScrollBar().isVisible()) {
					getVerticalScrollBar().setBounds(width - scrollBarSize, 0, scrollBarSize,
							height - cornerOffset);
				}
				if (getHorizontalScrollBar().isVisible()) {
					getHorizontalScrollBar().setBounds(0, height - scrollBarSize,
							width - cornerOffset, scrollBarSize);
				}
			}
		});
		add(layeredPane, BorderLayout.CENTER);
		layeredPane.setBackground(Color.BLUE);
	}
	
	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setBorder(null);
			scrollPane.setLayout(new ScrollPaneLayout() {

				@Override
				public void layoutContainer(final Container parent) {
					viewport.setBounds(0, 0, getWidth(), getHeight());
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							displayScrollBarsIfNecessary(viewport);
						}
					});
				}
			});
		}
		return scrollPane;
	}
	
	public JScrollBar getVerticalScrollBar() {
		if (verticalScrollBar == null) {
			verticalScrollBar = getScrollPane().getVerticalScrollBar();
			verticalScrollBar.setVisible(false);
			verticalScrollBar.setUnitIncrement(10);
			verticalScrollBar.setOpaque(false);
			verticalScrollBar.setUI(new MyScrollBarUI());
		}
		return verticalScrollBar;
	}
	
	public JScrollBar getHorizontalScrollBar() {
		if (horizontalScrollBar == null) {
			horizontalScrollBar = getScrollPane().getHorizontalScrollBar();
			horizontalScrollBar.setVisible(false);
			horizontalScrollBar.setUnitIncrement(10);
			horizontalScrollBar.setOpaque(false);
			horizontalScrollBar.setUI(new MyScrollBarUI());
		}
		return horizontalScrollBar;
	}
	
	public void setViewportView(final Component view) {
		getScrollPane().setViewportView(view);
	}
	
	private void displayScrollBarsIfNecessary(final JViewport viewPort) {
		displayVerticalScrollBarIfNecessary(viewPort);
		displayHorizontalScrollBarIfNecessary(viewPort);
	}

	private void displayVerticalScrollBarIfNecessary(final JViewport viewPort) {
		Rectangle viewRect = viewPort.getViewRect();
		Dimension viewSize = viewPort.getViewSize();
		boolean shouldDisplayVerticalScrollBar = viewSize.getHeight() > viewRect.getHeight();
		verticalScrollBar.setVisible(shouldDisplayVerticalScrollBar);
	}

	private void displayHorizontalScrollBarIfNecessary(final JViewport viewPort) {
		Rectangle viewRect = viewPort.getViewRect();
		Dimension viewSize = viewPort.getViewSize();
		boolean shouldDisplayHorizontalScrollBar = viewSize.getWidth() > viewRect.getWidth();
		horizontalScrollBar.setVisible(shouldDisplayHorizontalScrollBar);
	}

	private static class MyScrollBarButton extends JButton {

		private MyScrollBarButton() {
			setOpaque(false);
			setFocusable(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setBorder(BorderFactory.createEmptyBorder());
		}
	}

	private static class MyScrollBarUI extends BasicScrollBarUI {

		@Override
		protected JButton createDecreaseButton(final int orientation) {
			return new MyScrollBarButton();
		}

		@Override
		protected JButton createIncreaseButton(final int orientation) {
			return new MyScrollBarButton();
		}

		@Override
		protected void paintTrack(final Graphics g, final JComponent c,
				final Rectangle trackBounds) {
			// do nothing
		}

		@Override
		protected void paintThumb(final Graphics g, final JComponent c,
				final Rectangle thumbBounds) {
			int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
			int orientation = scrollbar.getOrientation();
			int arc = THUMB_SIZE;
			int x = thumbBounds.x + THUMB_BORDER_SIZE;
			int y = thumbBounds.y + THUMB_BORDER_SIZE;

			int width = orientation == JScrollBar.VERTICAL ? THUMB_SIZE
					: thumbBounds.width - (THUMB_BORDER_SIZE * 2);
			width = Math.max(width, THUMB_SIZE);

			int height = orientation == JScrollBar.VERTICAL
					? thumbBounds.height - (THUMB_BORDER_SIZE * 2) : THUMB_SIZE;
			height = Math.max(height, THUMB_SIZE);

			Graphics2D graphics2D = (Graphics2D) g.create();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(),
					THUMB_COLOR.getBlue(), alpha));
			graphics2D.fillRoundRect(x, y, width, height, arc, arc);
			graphics2D.dispose();
		}
	}
}