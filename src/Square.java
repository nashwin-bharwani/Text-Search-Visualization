import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JPanel;
/**
 * This class consists the logic for the squares needed for the visualization.
 * Each character is assigned a square for easier understanding of the algorithm.
 * 
 * @author Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina
 *
 */

public class Square extends JPanel implements TableCell
{
	Shape box;
	int x;
	int y;
	int length;
	char character;
	Color color;
	BasicStroke stroke;
	/**
	 * Constructor
	 * @param x the width
	 * @param y the height
	 * @param length the length of the string
	 * @param character the character to be added
	 * @param color the color of the square
	 */
	public Square(int x, int y, int length, char character, Color color)
	{
		//box  = new Rectangle(x, y, length, length);
		this.color = color;
		this.x = x;
		this.y = y;
		this.length = length;
		this.character = character;
		this.stroke = new BasicStroke(1.0f);
		setPreferredSize(new Dimension(length, length));
	}
	/**
	 * For repaint() and validate()
	 */
	public void draw()
	{
		repaint();
		validate();
	}
	/**
	 * Changes the color of the square
	 * @param color the new color
	 */
	public void changeColor(Color color)
	{
		this.color=color;
		this.stroke = new BasicStroke(2.0f);
		draw();
	}
	public Color getColor() { return color; }
	/**
	 * Graphics
	 */
	public void paintComponent(Graphics g)
	{
		  super.paintComponent(g);
		  ((Graphics2D) g).setPaint(color);
		  ((Graphics2D) g).setStroke(stroke);
		  g.drawRect(x, y, length, length);
		  ((Graphics2D) g).setPaint(Color.BLACK);
		  g.drawString(Character.toString(character), x + length/2-2, y + 3*length/4+2);
		  //places.add()
	} 
}