import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane; 

public class StringSearch extends JFrame implements ActionListener{
	private JPanel window;
	private JButton rK;
	private JButton hP;
	private static StringSearch test;
	private StringSearchVisualization rabinK;
	private HorspoolWindow hPool;
	public StringSearch()
	{
		window = new JPanel();
		window.setLayout(new GridLayout(1,2));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel interfacePane = new JPanel();
		interfacePane.setLayout(new GridLayout(2,1));
		rK = new JButton("Rabin Karp");
		hP = new JButton("Horspool");
		rK.addActionListener(this);
		hP.addActionListener(this);
		interfacePane.add(rK);
		interfacePane.add(hP);
		window.add(interfacePane);
		this.add(window);		
		this.setSize(1000, 600);
		this.setResizable(false);
		this.setTitle("String Search Visualization -- By Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina");
	}
	public void actionPerformed(ActionEvent e)
	{
		if (rK.equals(e.getSource()))
		{
			test.setVisible(false);
			rabinK = new StringSearchVisualization();
			rabinK.setVisible(true);
		}
		else if (hP.equals(e.getSource()))
		{
			test.setVisible(false);
			hPool = new HorspoolWindow();
			hPool.setVisible(true);
		}

	}
	public static void main(String[] args)
	{
		test = new StringSearch();
		test.setVisible(true);
	}
}
