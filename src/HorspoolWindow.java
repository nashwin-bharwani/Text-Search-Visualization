import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JOptionPane; 

public class HorspoolWindow extends JFrame implements ActionListener{

	/**
	 * @param args
	 */


	JPanel tablePanel;
	private String keyFeed;
	private String stringFeed;
	private String key;
	private String string;
	int[] occ;
	private int matches;
	int hPoolMatches;
	public static ArrayList<JPanel> places;
	private JPanel window;
	private JPanel bottomPane;
	private int tempInt;
	private JButton stringButton;
	private JLabel stringLabel;	
	private JLabel temp;
	private JButton keyButton;

	private JLabel keyLabel;
	private JTextField textSubmissionField;
	private JButton search;
	private JButton next;
	private JButton urlButton;


	private boolean matchedCharacter;


	int indx;
	int j;
	int keyLen;
	int stringLen;
	//int hPoolMatches;
	private int m;

	private String urlSource;	
	private Timer timer;

	private JPanel tablePane;
	private JPanel  searchPane;

	HorspoolSearch stringSearch;
	HorspoolTable table;

	HorspoolWindow(){


		bottomPane = null;
		window = new JPanel();
		window.setLayout(new GridLayout(2,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel interfacePane = new JPanel();
		interfacePane.setLayout(new GridLayout(5,1));

		textSubmissionField = new JTextField("Please enter a String.");	
		textSubmissionField.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//Clears the default text
				textSubmissionField.setText("");
			}
		}); 

		//Buttons
		stringButton = new JButton("Enter String");
		keyButton = new JButton("Enter Key");	
		//urlButton = new JButton("Enter URL");
		stringLabel = new JLabel(string);
		keyLabel = new JLabel(key);

		search = new JButton("Search");
		next = new JButton("Next");		
		stringButton.addActionListener(this);
		keyButton.addActionListener(this); 
		search.addActionListener(this);
		next.addActionListener(this);
		//urlButton.addActionListener(this);
		//Button layouts
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(stringButton);
		//buttonPanel.add(urlButton);
		buttonPanel.add(keyButton);

		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new GridLayout(1,2));
		keyPanel.add(new JLabel("   Key:"));
		keyPanel.add(keyLabel);

		JPanel stringPanel = new JPanel();
		stringPanel.setLayout(new GridLayout(1,2));
		temp = new JLabel("   String:");
		stringPanel.add(temp);
		stringPanel.add(stringLabel);

		JPanel searchButtonPanel = new JPanel();
		searchButtonPanel.setLayout(new GridLayout(2,2));
		searchButtonPanel.add(search);
		searchButtonPanel.add(next);
		//searchButtonPanel.add(hashLabel);
		//searchButtonPanel.add(hashLabel2);
		interfacePane.add(textSubmissionField);
		interfacePane.add(buttonPanel);
		interfacePane.add(keyPanel);
		interfacePane.add(stringPanel);
		interfacePane.add(searchButtonPanel);

		//adds the interface pane to the window
		matches = 0;
		window.add(interfacePane);
		this.add(window);		
		setVisible(true);
		//		try {
		//			String temp = getUrlSource("http://www.cumhuriyet.com.tr/?hn=298710").toString();//.replaceAll("\\s+","");
		//			//temp = temp.replaceAll("\\s+","");
		//			System.out.print(temp);
		//		} catch (IOException e1) {
		//			// TODO Auto-generated catch block
		//			e1.printStackTrace();
		//		}
		this.setSize(1000, 600);
		this.setResizable(false);
		this.setTitle("Horspool String Search Visualization -- By Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina");
		window.validate();
	}
	private void paintStringHorspool(){

		if(key!=null&&string!=null)
		{
			if(string.length()<key.length()){ JOptionPane.showMessageDialog(null, "Why am I searching for HAYSTACK in a NEEDLE?");
			return;}
		}

		if (key == null || string == null || key.isEmpty() || string.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "You have some missing data, please fill it in.");
			return;
		}

		if (bottomPane != null)
		{
			window.remove(bottomPane);
		}

		System.out.println("Attempting to create the bottom pane");

		bottomPane = new JPanel();
		bottomPane.setLayout(new GridLayout(3,1));

		places = new ArrayList<JPanel>();
		tempInt = 0;

		stringSearch = new HorspoolSearch(key, string);
		table = new HorspoolTable(key);

		tablePane = table.getHorspoolPane();
		searchPane = stringSearch.getHorspoolVisual();

		j =keyLen-1;
		while( j>=0 && key.charAt(j)==string.charAt(indx+j))
		{
			//			table.highlightCell(string.charAt(j));
			//			stringSearch.highlightCell(indx+j);
			j--;

			//Thread.sleep(1000);
			//window.repaint();
		}				

		if (j == -1){
			JOptionPane.showMessageDialog(null, "Found a match at location 0");
		}

		indx = 0;
		if (j == -1){
			table.highlightCell(string.charAt(indx));
			stringSearch.highlightCell(indx);
			indx++;
		} else {

			table.highlightCell(string.charAt(indx+j));
			stringSearch.highlightCell(indx+j);		

		}
		tablePane.validate();
		searchPane.validate();
		tablePane.repaint();
		searchPane.repaint();

		bottomPane.add(searchPane);
		bottomPane.add(tablePane);
		window.add(bottomPane);

		window.validate();

	}


	public void actionPerformed(ActionEvent e)
	{
		if (stringButton.equals(e.getSource()))
		{
			stringFeed = textSubmissionField.getText();
			replaceSearchString();
		} else if (keyButton.equals(e.getSource()))
		{
			keyFeed = textSubmissionField.getText();
			replaceKeyString();
		} else if (search.equals(e.getSource()))
		{
			if((stringFeed != null && !stringFeed.isEmpty()))
			{
				key = keyFeed;
				string = stringFeed;
				System.out.println("Attempting to paint the string");
				stringLen = string.length();
				keyLen = key.length();
				indx = 0;
				hPoolMatches = 0;
				paintStringHorspool();
			}
		} else if (next.equals(e.getSource()))
		{
			try {
				iterateThroughSearch();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

	}
	private void iterateThroughSearch() throws InterruptedException {
		iterate();
	}

	/**
	 * Helper method for actionPerformed()
	 * Clears the input field and adds the text to the appropriate field
	 */
	void replaceSearchString()
	{
		String stringFeedTemp= textSubmissionField.getText();
		if(!stringFeedTemp.isEmpty())
		{
			if(stringFeedTemp.length()>195)
			{
				stringFeed = textSubmissionField.getText().substring(0,196);
			}else
			{
				stringFeed = textSubmissionField.getText();
			}
			stringLabel.setText(stringFeed);
			textSubmissionField.setText("");
		}
	}
	/**
	 * Clears the input field and adds the text to the appropriate field 
	 */
	void replaceKeyString()
	{
		String keyFeedTemp= textSubmissionField.getText();
		if(!keyFeedTemp.isEmpty())
		{
			if(keyFeedTemp.length()>195)
			{
				keyFeed = textSubmissionField.getText().substring(0,196);	
			}else
			{
				keyFeed = textSubmissionField.getText();
			}
			keyLabel.setText(keyFeed);
			textSubmissionField.setText("");
		}
	}

	public void iterate() throws InterruptedException
	{

		if (indx+key.length() >= string.length()){
			return; 	
		}

		stringSearch = new HorspoolSearch(key, string);
		window.remove(searchPane);

		occ = table.getTable();
		if(indx<stringLen-keyLen)
		{
			j =keyLen-1;
			while( j>=0 && key.charAt(j)==string.charAt(indx+j))
			{
				j--;

			}

			if(j<0) {
				hPoolMatches++;
				JOptionPane.showMessageDialog(null,"Match found at location " + indx);
				indx++;
			} else {
				indx += keyLen-1;
				indx-=occ[string.charAt(indx)];		
			}
			window.remove(bottomPane);
			bottomPane = new JPanel();
			bottomPane.setLayout(new GridLayout(3,1));

			places = new ArrayList<JPanel>();
			tempInt = 0;

			stringSearch = new HorspoolSearch(key, string);
			stringSearch.createVisual(indx);
			table = new HorspoolTable(key);

			tablePane = table.getHorspoolPane();
			searchPane = stringSearch.getHorspoolVisual();

			tablePane.validate();
			searchPane.validate();

			bottomPane.add(searchPane);
			bottomPane.add(tablePane);
			window.add(bottomPane);

			//Shift of the table is the length - the highlighted -1
			if (indx+key.length() >= string.length()){
				return; 	
			}	
			
			table.highlightCell(string.charAt(indx+j));
			stringSearch.highlightCell(indx+j);

			window.validate();

		}
		System.out.println(hPoolMatches);
	}

}







