package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public final class TaskTideTheme {
	public static final Color NAVY = Color.decode("#06346F");
	public static final Color BLUE = Color.decode("#074995");
	public static final Color WAVE = Color.decode("#0CC0DF");
	public static final Color SKY = Color.decode("#E8F8FC");
	public static final Color BACKGROUND = Color.decode("#F5F8FC");
	public static final Color SURFACE = Color.WHITE;
	public static final Color TEXT = Color.decode("#102033");
	public static final Color MUTED = Color.decode("#5D6B7C");
	public static final Color BORDER = Color.decode("#DCE7F3");
	public static final Color SUCCESS = Color.decode("#16A085");
	public static final Color WARNING = Color.decode("#F4A261");
	public static final Color DANGER = Color.decode("#D94A4A");

	public static final Font DISPLAY_FONT = new Font("Helvetica", Font.BOLD, 56);
	public static final Font TITLE_FONT = new Font("Helvetica", Font.BOLD, 34);
	public static final Font SECTION_FONT = new Font("Helvetica", Font.BOLD, 24);
	public static final Font BODY_FONT = new Font("Sans Serif", Font.PLAIN, 17);
	public static final Font SMALL_FONT = new Font("Sans Serif", Font.PLAIN, 14);
	public static final Font BUTTON_FONT = new Font("Sans Serif", Font.BOLD, 16);

	private TaskTideTheme() {
	}

	public static ImageIcon icon(String fileName) {
		File file = new File("images/" + fileName);
		if (file.exists()) {
			return new ImageIcon(file.getAbsolutePath());
		}
		return new ImageIcon();
	}

	public static ImageIcon scaledIcon(String fileName, int width, int height) {
		ImageIcon source = icon(fileName);
		if (source.getIconWidth() <= 0 || source.getIconHeight() <= 0) {
			return source;
		}
		Image scaled = source.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}

	public static void setFrameIcon(JFrame frame) {
		ImageIcon icon = icon("logologo.png");
		if (icon.getIconWidth() > 0) {
			frame.setIconImage(icon.getImage());
		}
	}

	public static JLabel logoLabel(int size) {
		JLabel label = new JLabel(scaledIcon("logobl.png", size, size));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	public static void stylePrimaryButton(JButton button) {
		button.setBackground(BLUE);
		button.setForeground(Color.WHITE);
		button.setFont(BUTTON_FONT);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public static void styleSecondaryButton(JButton button) {
		button.setBackground(SKY);
		button.setForeground(BLUE);
		button.setFont(BUTTON_FONT);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(BORDER, 1));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public static void styleIconButton(JButton button) {
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setFocusPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public static void styleTextField(JTextField field) {
		field.setFont(BODY_FONT);
		field.setForeground(TEXT);
		field.setBackground(Color.WHITE);
		field.setCaretColor(BLUE);
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(BORDER, 1),
				new EmptyBorder(8, 12, 8, 12)));
	}

	public static void makeCard(JComponent component) {
		component.setBackground(SURFACE);
		component.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(BORDER, 1),
				new EmptyBorder(18, 18, 18, 18)));
	}

	public static class GradientPanel extends JPanel {
		private final Color start;
		private final Color end;

		public GradientPanel(Color start, Color end) {
			this.start = start;
			this.end = end;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setPaint(new GradientPaint(0, 0, start, getWidth(), getHeight(), end));
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.dispose();
			super.paintComponent(g);
		}
	}

	public static class RoundedCard extends JPanel {
		private final int radius;
		private final Color borderColor;

		public RoundedCard(int radius) {
			this(radius, BORDER);
		}

		public RoundedCard(int radius, Color borderColor) {
			this.radius = radius;
			this.borderColor = borderColor;
			setOpaque(false);
			setBorder(new EmptyBorder(18, 18, 18, 18));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(getBackground());
			g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2d.setColor(borderColor);
			g2d.setStroke(new BasicStroke(1));
			g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2d.dispose();
			super.paintComponent(g);
		}
	}
}
