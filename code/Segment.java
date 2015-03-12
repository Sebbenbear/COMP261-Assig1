import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Segment {

	public int roadID;
	public double roadLength;
	//public static List<Double> coords = new ArrayList<Double>();  //OR ARRAY?

	//store all the locations and put them in an arraylist
	public List<Location> coords = new ArrayList<Location>();

	public Node startNode;
	public Node endNode;

	public Road road;  //which road it belongs to

	public Color color = Color.blue;

	public Segment (Road road, int roadID, double roadLength, Node startNode, Node endNode, ArrayList <Location> coords){
		this.road = road;
		this.roadID = roadID;
		this.roadLength = roadLength;
		this.startNode = startNode;
		this.endNode = endNode;
		this.coords = coords;
	}

	public Set getNodes(){
		Set <Node> nodes = new HashSet<>();
		nodes.add(startNode);
		nodes.add(endNode);
		return nodes;
	}

	public void draw(Graphics g, int scale, Location origin, int shiftVertical, int shiftHorizontal, Dimension d) {
		g.setColor(color);
		Point p1 = (coords.get(0).asPoint(origin, scale));
		Point p2;
		int x1, y1, x2, y2;

		for(int i = 1; i < coords.size(); i++){
			p2 = (coords.get(i).asPoint(origin, scale));

			x1 = (int) (p1.x + shiftHorizontal + (d.width * 0.5));
			y1 = (int) (p1.y + shiftVertical  + (d.height * 0.5));
			x2 = (int) (p2.x + shiftHorizontal + (d.width * 0.5));
			y2 = (int) (p2.y + shiftVertical + (d.height * 0.5));

			g.drawLine(x1, y1, x2, y2);   //fix? but is tidier this way than casting to int
			//p2 = p1;
			p1 = p2;
			System.out.println("p1: " + p1 + " p2: " + p2);
		}
	}

	public Road getRoad(){
		return road;
	}





}
