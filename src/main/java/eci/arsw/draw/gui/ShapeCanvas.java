/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.draw.gui;

import eci.arsw.draw.controller.IController;
import eci.arsw.draw.gui.shapes.EllipseRenderer;
import eci.arsw.draw.gui.shapes.LineRenderer;
import eci.arsw.draw.gui.shapes.RectangleRenderer;
import eci.arsw.draw.gui.shapes.Renderer;
import eci.arsw.draw.model.Line;
import eci.arsw.draw.model.Rectangle;
import eci.arsw.draw.model.Ellipse;
import eci.arsw.draw.model.Shape;
import eci.arsw.pattern.observer.Observer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import java.util.List;

/**
 *
 * @author fchaves
 */
public class ShapeCanvas extends JPanel implements Observer {
    IController controller; 
    
    public ShapeCanvas(IController controller) {
        this.controller = controller;
        mouseControl();
        this.controller.addObserver(this);
    }
    
    @Override
    public void update() {
        repaint(); 
    }
    
    private void mouseControl() {
        
        addMouseListener(new MouseAdapter() {
            Point startDrag, endDrag;

            @Override
            public void mousePressed(MouseEvent e) {
               startDrag = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endDrag = new Point(e.getX(), e.getY());
                if (Math.abs(endDrag.x - startDrag.x) > 2 && Math.abs(endDrag.y - startDrag.y) > 2) {
                    controller.commandAddShape(startDrag, endDrag);
                }
                startDrag = null;
                endDrag = null;
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        
        List<Shape> list = controller.getShapes();
        Renderer renderer = controller.getRenderer();
        
        LineRenderer lineRenderer = renderer.createLineRenderer();
        RectangleRenderer rectangleRenderer = renderer.createRectangleRenderer();
        EllipseRenderer ellipseRenderer = renderer.createEllipseRenderer();
        
        
        for(Shape shape : list) {
            switch(shape.getElementType()) {
                case Line: 
                    lineRenderer.draw(g2, (Line)shape);
                    break;
                case Rectangle:
                    rectangleRenderer.draw(g2, (Rectangle)shape);
                    break;
                case Ellipse:
                    ellipseRenderer.draw(g2, (Ellipse)shape);                    
                    break;
            }
        }
    } 
    
}