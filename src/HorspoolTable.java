import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class HorspoolTable {

	int [] occ;
	JPanel tablePane;
	String key;
	ArrayList <JPanel> cellList;
	ArrayList <Character> locations; 

	HorspoolTable(String key){
		this.key = key;
		this.cellList = new ArrayList<JPanel>();
		this.locations = new ArrayList<Character>();
		this.occ = generateTable(key);
		this.tablePane = generateFrame();
	}
	
	private int [] generateTable(String key){

		int [] occ = new int[Character.MAX_VALUE];
		int m = key.length();
		char a;

		for(a=0; a<Character.MAX_VALUE; a++)
		{
			occ[a] = -1;
		}

		for(int j=0; j<m-1;j++)
		{
			a = key.charAt(j);
			occ[a] = j;
		}

		return occ;
	}

	protected int getSkip(char a){
		return occ[a];
	}

	protected int [] getTable(){
		return occ;
	}

	private JPanel generateFrame(){
		JPanel tablePane = new JPanel();
		tablePane.setLayout(new FlowLayout());
		Set <Character> found = new HashSet <Character> ();

		for (int i = 0; i < key.length(); i++){
			if (!found.contains(key.charAt(i))){
				JPanel column = new TableColumn(0,0,15,key.charAt(i), occ[key.charAt(i)], Color.BLACK);
				column.setPreferredSize(new Dimension(16,31));
				tablePane.add(column);
				cellList.add(column);
				locations.add(key.charAt(i));
				found.add(key.charAt(i));
			}
		} 

		JPanel column = new TableColumn(0,0,15,'*', key.length(), Color.BLACK);
		column.setPreferredSize(new Dimension(16,31));
		tablePane.add(column);
		cellList.add(column);

		return tablePane;
	}

	protected JPanel getHorspoolPane(){
		return tablePane;
	}
	
	private int getLocation(char a){
		if (locations.indexOf(a) < 0){
			return cellList.size()-1;
		} 
		
		return locations.indexOf(a);
	}
	
	protected void highlightCell(char a){
		removeAllHighlights();
		((TableColumn) cellList.get(getLocation(a))).changeColor(Color.RED);
	}
	
	protected void removeAllHighlights(){
		for (JPanel curr : cellList){
			((TableColumn) curr).changeColor(Color.BLACK);
		}
	}
	
	
}

