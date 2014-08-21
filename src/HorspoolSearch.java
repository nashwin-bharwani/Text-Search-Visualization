import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JPanel;


public class HorspoolSearch {

	String key;
	String string;
	int [] table;
	JPanel horspoolVisual;
	int currPos;
	int checkKeyPos;
	boolean innerWhile;
	ArrayList <JPanel> cells;
	
	HorspoolSearch(String key, String string){
		this.key = key;
		this.string = string;
		cells = new ArrayList <JPanel> ();
		HorspoolTable t = new HorspoolTable(key);
		table = t.getTable();
		currPos = 0;
		checkKeyPos = key.length()-1;
		horspoolVisual = createVisual(0);
	}
	
	/*
	 * 		JPanel tablePane = new JPanel();
		tablePane.setLayout(new FlowLayout());		
		
		
		for (int i = 0; i < key.length(); i++){
			JPanel column = new TableColumn(0,0,15,key.charAt(i), occ[key.charAt(i)], Color.BLACK);
			column.setPreferredSize(new Dimension(16,31));
			tablePane.add(column);

		}
		

		return tablePane;
	 */
	
	public JPanel getHorspoolVisual(){
		return horspoolVisual;
	}
	
	JPanel createVisual(int keyPos){
		cells = new ArrayList <JPanel> ();
		JPanel horspoolVisual = new JPanel();
		horspoolVisual.setLayout(new FlowLayout());
		
		for (int i = 0; i < string.length(); i++){
			JPanel index;
			if (i >= keyPos && i < key.length()+keyPos){
				index = new TableColumn(0,0,15,string.charAt(i), key.charAt(i-keyPos), Color.BLACK);
			} else {
				index = new Square(0,0,15, string.charAt(i), Color.BLACK);
			}
			index.setPreferredSize(new Dimension(16,31));
			horspoolVisual.add(index);
			cells.add(index);
		}
		this.horspoolVisual = horspoolVisual;
		return horspoolVisual;
	}
	
	
	
	public void highlightCell(int index){
//		removeAllHighlights();
//		JPanel obj = cells.get(index);
//		((TableCell) obj).changeColor(Color.RED);
//		
		removeAllHighlights();
		((TableCell) cells.get(index)).changeColor(Color.RED);
		
	}
	
	void removeAllHighlights(){
		for (JPanel curr : cells){
			if (curr instanceof Square){
				((TableCell) curr).changeColor(Color.BLACK);
			}
		}
	}
	
	public JPanel iterate(){
		
		/*
		 * 		int i = 0,j ;
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
		 */
		
		
		System.out.println("Entered iterate");
		return null;
		
		
//		int m = string.length();
//		int n = key.length();
//		
//		if (currPos <= n-m){
//			
//		}
//		
//		horspoolVisual.createVisual();
	}
	
}
	