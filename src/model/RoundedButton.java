package model;

import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class RoundedButton extends JButton {
    private int radius; // Radius of the rounded corners

    public RoundedButton(String text, int radius) {
        super(text); // Set the button's text
        this.radius = radius;
        setOpaque(false); // Make the button transparent
        setContentAreaFilled(false); // Don't fill the content area (necessary for transparent buttons)
        setFocusPainted(false); // Don't paint a focus border
        setBorderPainted(false); // Don't paint the border
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Prepare the graphics object and set rendering hints
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded button background
        if (getModel().isPressed()) {
            g2d.setColor(getBackground().darker()); // Make the button appear pressed
        } else if (getModel().isRollover()) {
            g2d.setColor(getBackground().brighter()); // Highlight the button on mouse over
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw the text over the button (centrally aligned)
        FontMetrics metrics = g.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setColor(getForeground());
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }
    
}