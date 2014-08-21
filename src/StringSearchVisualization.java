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

import javax.swing.Timer;
import javax.swing.JOptionPane; 
/**
 * This class creates the JPanel for the visualization.
 * It also contains the logic for the Rabin Karp String Search algorithm.
 * 
 * @author Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina
 */
public class StringSearchVisualization extends JFrame implements ActionListener
{

	private String keyFeed;
	private String stringFeed;
	private String key;
	private String string;
	int[] occ;
	private int matches;
	int hPoolMatches;
	public static ArrayList<JPanel> places;
	private static final int BASE = 1007;
	private JPanel window;
	private JPanel bottomPane;
	private int tempInt;
	private JButton stringButton;
	private JLabel stringLabel;	
	private JLabel temp;
	private JButton keyButton;
	private int hashKey;
	private int hashSubNeedle;
	private JLabel keyLabel;
	private JTextField textSubmissionField;
	private JButton search;
	private JButton next;
	private JButton urlButton;
	private JLabel hashLabel;
	private JLabel hashLabel2;
	private JLabel needleHashLabel;
	private int m;
	private JLabel haystackHashLabel;
	private String urlSource;	
	private Timer timer;

	/**
	 * The JPanel for the visualization 
	 * @throws IOException 
	 */

	public StringSearchVisualization()
	{
		bottomPane = null;
		window = new JPanel();
		window.setLayout(new GridLayout(2,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel interfacePane = new JPanel();
		interfacePane.setLayout(new GridLayout(5,1));

		textSubmissionField = new JTextField("Please enter a String");	
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
		urlButton = new JButton("Enter URL");
		stringLabel = new JLabel(string);
		keyLabel = new JLabel(key);
		hashLabel = new JLabel();
		hashLabel2 = new JLabel();
		search = new JButton("Search");
		next = new JButton("Next");		
		stringButton.addActionListener(this);
		keyButton.addActionListener(this); 
		search.addActionListener(this);
		next.addActionListener(this);
		urlButton.addActionListener(this);
		//Button layouts
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(stringButton);
		buttonPanel.add(urlButton);
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
		searchButtonPanel.add(hashLabel);
		searchButtonPanel.add(hashLabel2);
		interfacePane.add(textSubmissionField);
		interfacePane.add(buttonPanel);
		interfacePane.add(keyPanel);
		interfacePane.add(stringPanel);
		interfacePane.add(searchButtonPanel);

		//adds the interface pane to the window
		matches = 0;
		window.add(interfacePane);
		this.add(window);		
		horspoolIntocc("jj");
		horspoolSearch("jj","jjj");
		System.out.print(hPoolMatches);
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
		this.setTitle("Rabin-Karp String Search Visualization -- By Nashwin Bharwani, Ja'Quan Taylor, Roxanne Jijina");
	}
	//@SuppressWarnings("null")
	private static StringBuilder getUrlSource(String url) throws IOException {
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						yahoo.openStream()));

		String inputLine = new String("");
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null){
			//System.out.println(inputLine);
			a.append(inputLine.replaceAll("\\s+",""));
		}
		in.close();

		return a;
	}
	/**
	 * Decides what to do depending on the ActionEvent 
	 * @param e	The ActionEvent performed
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (stringButton.equals(e.getSource()))
		{
			urlSource = null;
			temp.setText("   String:");
			replaceSearchString();
		} else if (keyButton.equals(e.getSource()))
		{
			replaceKeyString();
		} else if (search.equals(e.getSource()))
		{
			if(urlSource==null && (stringFeed != null && !stringFeed.isEmpty()))
			{
				key = keyFeed;
				string = stringFeed;
				System.out.println("Attempting to paint the string");
				paintString();
			}else if(urlSource != null && string ==null)
			{
				key = keyFeed;
				try {	
					urlSearch();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (next.equals(e.getSource()))
		{
			iterateThroughSearch();
		} else if (urlButton.equals(e.getSource()))
		{
			string = null;
			temp.setText("   URL:");
			try {
				replaceUrl();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void horspoolIntocc(String pattern)
	{

		occ = new int[Character.MAX_VALUE];
		int m = pattern.length();
		char a;
		for(a=0; a<Character.MAX_VALUE; a++)
		{
			occ[a] = -1;
		}
		for(int j=0; j<m-1;j++)
		{
			a = pattern.charAt(j);
			occ[a] = j;
		}
	}

	private void horspoolSearch(String text, String pattern)
	{
		int i = 0,j ;
		int m = pattern.length();
		int n = text.length();
		hPoolMatches =0;
		while(i<=n-m)
		{
			j = m-1;
			while( j>=0 && pattern.charAt(j)==text.charAt(i+j)) j--;
			if(j<0) hPoolMatches++;
			i += m-1;
			i-=occ[text.charAt(i)];
		}
	}

	private void urlSearch() throws InterruptedException
	{

		if(key!=null&&urlSource!=null)
		{
			if(urlSource.length()<key.length()){ JOptionPane.showMessageDialog(null, "Why am I searching for HAYSTACK in a NEEDLE?");
			return;}
		}

		if (key == null || urlSource == null || key.isEmpty() || urlSource.toString().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "You have some missing data, please fill it in.");
			return;
		}
		if (bottomPane != null)
		{
			window.remove(bottomPane);
		}
		bottomPane = new JPanel();
		bottomPane.setLayout(new GridLayout(4,1));

		places = new ArrayList<JPanel>();
		JPanel squaresPane = drawSquares(0);
		if(urlSource!=null)
		{
			if(urlSource.length() >= 147){squaresPane = drawSquares(147);}
			else{ squaresPane = drawSquares(urlSource.length());}
		}else
		{
			squaresPane = drawSquares(0);
		}
		squaresPane.validate();

		JPanel hashPane = drawHashPane();

		hashPane.validate();
		JLabel matchesLabel = new JLabel("Matches: ");
		bottomPane.add(squaresPane);
		bottomPane.add(hashPane);
		bottomPane.add(matchesLabel);
		window.add(bottomPane);
		window.validate();


		ActionListener iterateSearch = new MyTimerListener(matchesLabel);
		int delay = 50;
		timer = new Timer(delay, iterateSearch);
		timer.start();
	}

	private class MyTimerListener implements ActionListener{

		private JLabel matchesLabel;
		private int iterations;
		int threshhold = 146;
		int breakAway = 146;
		public MyTimerListener(JLabel matchesLabel) {
			this.matchesLabel = matchesLabel;
			iterations = 0;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			m = urlSource.length();
			
			if(m>147){

				tempInt++;
				needleHashLabel.setText(generateHashFormulaText(key));
				haystackHashLabel.setText(generateHashFormulaText(urlSource.toString().substring(tempInt-1, tempInt+key.length()-1)));
				hashSubNeedle = hashString(urlSource.toString().substring(tempInt-1, tempInt+key.length()-1));		
				hashLabel.setText("   hash("+key+") = " + Integer.toString(hashString(key))+ "");
				hashLabel2.setText("   hash(" +urlSource.toString().substring(tempInt-1, tempInt+key.length()-1) + ") = " + hashSubNeedle + "");
				matchesLabel.setText("Matches: " + matches);
				window.repaint();
				if (hashString(key) == hashSubNeedle)
				{
					matches++;
				}
				highLight(tempInt,hashString(key) == hashSubNeedle);
				
				while(!urlSource.toString().isEmpty() && tempInt == 147-key.length())
				{
					if(urlSource.toString().length()>=147)
					{
						urlSource = urlSource.substring(147-key.length(),urlSource.length());
						try {
							tempInt = 0;
							timer.stop();
							urlSearch();
							if (urlSource.length() < 147){
								System.out.println("Exiting the loop");
								return;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}
			else
			{
				
				System.out.println("entered m < 147");
				System.out.println(urlSource.length());
				
				tempInt++;
				
				if (tempInt >= urlSource.length()-key.length()+1){
					System.out.println("exited on " + tempInt);
					timer.stop();
					return;
				}
				
				System.out.println(tempInt);

				needleHashLabel.setText(generateHashFormulaText(key));
				haystackHashLabel.setText(generateHashFormulaText(urlSource.toString().substring(tempInt-1, tempInt+key.length()-1)));
				hashSubNeedle = hashString(urlSource.toString().substring(tempInt-1, tempInt+key.length()-1));		
				hashLabel.setText("   hash("+key+") = " + Integer.toString(hashString(key))+ "");
				hashLabel2.setText("   hash(" +urlSource.toString().substring(tempInt-1, tempInt+key.length()-1) + ") = " + hashSubNeedle + "");
				matchesLabel.setText("Matches: " + matches);
				window.repaint();
				if (hashString(key) == hashSubNeedle)
				{
					matches++;
				}
				highLight(tempInt,hashString(key) == hashSubNeedle);
				
				return;
			}

		}
	}	


	/**
	 * Helper method for actionPerformed()
	 */
	private void iterateThroughSearch()
	{

		if (places == null || places.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Search has not been pressed, please do so.");
			return;
		}

		if (tempInt == string.length()-key.length()+1)
		{
			JOptionPane.showMessageDialog(null, "You have reached the end of the string. Please try to search again");
			return;
		}


		if(key!=null && string!=null &&!key.isEmpty())
		{
			if(!places.isEmpty())
			{
				highLight(tempInt);
				tempInt++;
			}
			updateHashLabel();
		}else
		{
			JOptionPane.showMessageDialog(null, "Dude you need a key AND a string! Trynna pull a fast one?");
		}
	}

	/**
	 * Updates the hash visualization as we search
	 */
	private void updateHashLabel()
	{

		hashKey = hashString(key);
		hashLabel.setText("   hash("+key+") = " + hashKey + "");
		haystackHashLabel.setText(generateUpdatedHashString(hashSubNeedle, string.charAt(tempInt+key.length()-2), string.charAt(tempInt-2), key.length()));
		hashSubNeedle = updateHash(hashSubNeedle, string.charAt(tempInt+key.length()-2), string.charAt(tempInt-2), key.length()); 
		hashLabel2.setText("hash(" + string.substring(tempInt-1, tempInt+key.length()-1) + ") = " + hashSubNeedle +"");
		if(hashKey==hashSubNeedle&&key.equals(string.substring(tempInt-1, tempInt+key.length()-1))) JOptionPane.showMessageDialog(null, "Found a match at character index " + (tempInt-1) +" of the haystack.");
	}

	/**
	 * Generates the formula for the hash visualization
	 * @param text  
	 * @return  
	 */
	private String generateHashFormulaText(String text)
	{
		StringBuffer bufferedText = new StringBuffer();
		for(int i = 0; i < text.length(); i++ )
		{
			bufferedText.append("(");
			bufferedText.append(BASE);
			bufferedText.append("^");
			bufferedText.append(Integer.toString(text.length()-1-i));
			bufferedText.append(")");
			bufferedText.append("*");
			bufferedText.append(Integer.toString((int)text.charAt(i)));
			if (i != key.length()-1){bufferedText.append(" + ");}
		}
		return bufferedText.toString();
	}

	/**
	 * The visualization for the hash
	 * @param oldHash the old hash
	 * @param newChar the new char to be added to the hash
	 * @param oldChar the old character to be removed from the hash
	 * @param length the length of the hash
	 * @return
	 */
	private String generateUpdatedHashString(int oldHash, char newChar, char oldChar, int length){

		StringBuffer bufferedText = new StringBuffer();

		bufferedText.append(oldHash);
		bufferedText.append(" - ");
		bufferedText.append(Integer.toString((int) oldChar));
		bufferedText.append("*(");
		bufferedText.append(BASE);
		bufferedText.append("^");
		bufferedText.append(Integer.toString(length-1));
		bufferedText.append(")*");
		bufferedText.append(BASE);
		bufferedText.append(" + ");
		bufferedText.append(Integer.toString((int)newChar));
		bufferedText.append("*");
		bufferedText.append(BASE);
		bufferedText.append("^");
		bufferedText.append("0");

		return bufferedText.toString();
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
	private void replaceUrl() throws IOException
	{
		String urlFeedTemp= textSubmissionField.getText();
		if(!urlFeedTemp.isEmpty())
		{
			urlSource = textSubmissionField.getText();
			stringLabel.setText(urlSource);
			textSubmissionField.setText("");
			urlSource = getUrlSource(urlSource).toString();
		}

	}
	/**
	 * Highlights the squares for the search visualization
	 * @param num the number of squares to highlight
	 */
	private void highLight(int num, boolean match)
	{
		for(int i = 0; i < key.length(); i++)
		{

			((Square) places.get(i+num)).changeColor(Color.RED);
		}
		for(int i = 0; i < num; i++)
		{
			if(!(((Square) places.get(i)).getColor()).equals(Color.GREEN))
				((Square) places.get(i)).changeColor(Color.BLACK);

		}
		if(match){((Square) places.get(num-1)).changeColor(Color.GREEN);}
	}
	private void highLight(int num)
	{
		for(int i = 0; i < key.length(); i++)
		{
			((Square) places.get(i+num)).changeColor(Color.RED);
		}
		for(int i = 0; i < num; i++)
		{
			((Square) places.get(i)).changeColor(Color.BLACK);
		}
	}
	/**
	 * Displays appropriate messages to user if input errors exist
	 */
	private void paintString(){

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

		JPanel squaresPane = drawSquares(0);
		if(string!=null)
		{
			System.out.println("Drawing " + string.length() + " squares.");
			squaresPane = drawSquares(string.length());
		}else
		{
			System.out.println("Drawing no squares");
			squaresPane = drawSquares(0);
		}
		squaresPane.validate();

		JPanel hashPane = drawHashPane();

		hashPane.validate();

		bottomPane.add(squaresPane);
		bottomPane.add(hashPane);
		window.add(bottomPane);


		highLight(tempInt);

		tempInt++;

		needleHashLabel.setText(generateHashFormulaText(key));
		haystackHashLabel.setText(generateHashFormulaText(string.substring(tempInt-1, tempInt+key.length()-1)));
		hashSubNeedle = hashString(string.substring(tempInt-1, tempInt+key.length()-1));		
		hashLabel.setText("   hash("+key+") = " + Integer.toString(hashString(key))+ "");
		hashLabel2.setText("   hash(" +string.substring(tempInt-1, tempInt+key.length()-1) + ") = " + hashSubNeedle + "");

		window.validate();

		if (hashString(key) == hashSubNeedle)
		{
			JOptionPane.showMessageDialog(null, "Found a match at character index " + (tempInt-1) +" of the haystack.");
		}
	}
	/**
	 * Draws the hash panel
	 * @return the JPanel
	 */
	private JPanel drawHashPane(){

		JPanel hashPane = new JPanel();
		JPanel needleHashPane = new JPanel();
		JPanel haystackHashPane = new JPanel();

		hashPane.setLayout(new GridLayout(2,1));
		needleHashPane.setLayout(new GridLayout(1,2));
		haystackHashPane.setLayout(new GridLayout(1,2));

		needleHashPane.add(new JLabel("   needle Hash: "));
		haystackHashPane.add(new JLabel("   haystack Hash: "));

		needleHashLabel = new JLabel("needle placeholder");
		haystackHashLabel = new JLabel("haystack placeholder");

		needleHashPane.add(needleHashLabel);
		haystackHashPane.add(haystackHashLabel);

		hashPane.add(needleHashPane);
		hashPane.add(haystackHashPane);

		hashPane.validate();

		return hashPane;
	}
	/**
	 * Draws the squares on the JPanel
	 * @param num the number of squares to draw
	 * @return the JPanel
	 */
	private JPanel drawSquares(int num)
	{

		JPanel visualizationPane = new JPanel();
		visualizationPane.setLayout(new FlowLayout());

		for (int i = 0; i < num; i++)
		{
			if(string!= null){
				JPanel sq = new Square(0, 0, 15, string.charAt(i), Color.BLACK);
				places.add(sq);
				sq.validate();
				visualizationPane.add(sq);
			}else if(urlSource != null)
			{
				JPanel sq = new Square(0, 0, 15, urlSource.toString().charAt(i), Color.BLACK);
				places.add(sq);
				sq.validate();
				visualizationPane.add(sq);
			}
		}

		return visualizationPane;

	}
	/**
	 * Rabin karp Logic
	 * @param pattern  the needle
	 * @return the hash of the string
	 */
	public static int hashString(String pattern) 
	{
		int hash = 0;
		int num = pattern.length()-1;
		for(int i = 0; i < pattern.length(); i++ )
		{
			hash += (power(BASE, num-i))*(int)pattern.charAt(i);	
		}
		return hash;
	}

	/**
	 * To use instead of Math.pow
	 * @param base the base
	 * @param exponent the exponent
	 * @return the solution
	 */
	public static int power(long base, int exponent)
	{
		if(exponent == 0) return 1;
		if(exponent == 1) return (int) base;
		long solution = 1;
		for(int i = 0; i < exponent; i++)
		{
			solution *= base;
		}
		return (int)(solution);
	}
	/**
	 * Rolling hash function
	 * @param oldHash the old hash
	 * @param newChar the char to be added from the hash
	 * @param oldChar the char to be removed from the hash
	 * @param length the length of the hash
	 * @return
	 */
	public static int updateHash(int oldHash, char newChar, char oldChar, int length) 
	{
		int newHash = (int) ((oldHash - oldChar * power(BASE, length-1)) * BASE + newChar*power(BASE, 0));
		return newHash;
	}

}