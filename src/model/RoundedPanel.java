package model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int radius; // Radius of the rounded corners

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Make the panel transparent to see the rounded corners
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint the panel's components and background

        // Cast the Graphics object to Graphics2D for more sophisticated control
        Graphics2D g2d = (Graphics2D) g.create();

        // Antialiasing for smoother corners
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a rounded rectangle that fills the panel
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2d.dispose(); // Clean up the Graphics2D object
    }

   
}