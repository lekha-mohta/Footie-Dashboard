package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChartPanel;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * SKELETON IMPLEMENTATION
 */

public class RadarChartPanel extends AbstractRadarChartPanel {
    public RadarChartPanel(AbstractPlayerDashboardPanel parentPanel, AbstractRadarChart radarPlot) {
        super(parentPanel, radarPlot);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO remove code below and implement
        super.paintComponent(g);

        //Getting the dimensions of the radar chart area
        Dimension d = getSize();
        Graphics2D g2 = (Graphics2D) g;

        //Specifying the num of pentagons to be drawn
        int numPentagons = 10;
        // Specifies radius incrementing values for each subsequent pentagon
        int radiusIncrement = 25;

        //Drawing the Pentagons
        for (int i = numPentagons; i > 0; i--) {
            int radius = radiusIncrement * i;
            int sides = 5;
            int cx = d.width / 2;
            int cy = d.height / 2;

            int[] xpoint = new int[sides];
            int[] ypoint = new int[sides];

            for (int j = 0; j < sides; j++) {
                double angle = (2 * Math.PI / sides) * j;
                int x = cx + (int) (Math.cos(angle) * radius);
                int y = cy + (int) (Math.sin(angle) * radius);
                xpoint[j] = x;
                ypoint[j] = y;
            }

            g2.setColor(Color.BLACK);
            g2.drawPolygon(xpoint, ypoint, sides);
        }
    }
}









