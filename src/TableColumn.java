import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JPanel;
/**
 * This class consists the logic for the squares needed for the visualization.
 * Each character is assigned a square for easier understanding of the algorithm.
 * 
 * @author Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina
 *
 */

public class TableColumn extends JPanel implements TableCell
{
	Shape box;
	int x;
	int y;
	int length;
	char character_1;
	String character_2;
	Color color;
	BasicStroke stroke;
	/**
	 * Constructor
	 * @param x the width
	 * @param y the height
	 * @param length the length of the string
	 * @param character the character to be added
	 * @param color the color of the square
	 * @return 
	 */
	public TableColumn(int x, int y, int length, char character_1, int value, Color color)
	{
		//box  = new Rectangle(x, y, length, length);
		this.color = color;
		this.x = x;
		this.y = y;
		this.length = length;
		this.character_1 = character_1;
		this.character_2 = Integer.toString(value);
		this.stroke = new BasicStroke(1.0f);
		setPreferredSize(new Dimension(length, length));
	}
	
	public TableColumn(int x, int y, int length, char character_1, char character_2, Color color)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.length = length;
		this.character_1 = character_1;
		this.character_2 = Character.toString(character_2);
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
	/**
	 * Graphics
	 */
	public void paintComponent(Graphics g)
	{
		  super.paintComponent(g);
		  ((Graphics2D) g).setPaint(color);
		  ((Graphics2D) g).setStroke(stroke);
		  g.drawRect(x, y, length, length);
		  g.drawRect(x, y+length, length, length);	
		  ((Graphics2D) g).setPaint(Color.BLACK);	
		  g.drawString(Character.toString(character_1), x + length/2-2, y + 3*length/4+2);
		  g.drawString(character_2, x + length/2-2, y + length + 3*length/4+2);
		  
		  //places.add()
	} 
}