import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Node {

	public int id;
	private static final int NODE_SIZE = 3;
	public Location loc;
	int scale = 100;

	//FIELDS ENABLING A DIRECTED GRAPH
	private List <Segment> outNeighbours = new ArrayList<Segment>();
	private List <Segment> inNeighbours = new ArrayList<Segment>();

	//FOR DRAWING
	public final Color color = Color.red;

	public Node(int id, Location location){
		this.id = id;
		this.loc = location;
	}

	public void draw(Graphics g, int scale, Location origin, int shiftVertical, int shiftHorizontal, Dimension dimension) {
		//System.out.println(loc.x + " " + loc.y);
		//System.out.println(p.toString());
		//update x, y
		g.setColor(color);
		Point p = loc.asPoint(origin, scale);
		int x = (int) ((p.getX() + shiftHorizontal) + (dimension.width * 0.5));
		int y = (int) ((p.getY() + shiftVertical) + (dimension.height * 0.5));

		g.fillOval(x-NODE_SIZE/2, y-NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
     }

	//CONTAINS WILL BASICALLY BE THE SAME FOR A SQUARE - CHANGE THIS TO OVAL LATER

	//public boolean contains(int x, int y) {
	//	return x > this.x && y > this.y && x < this.x + NODE_SIZE
	//			&& y < this.y + NODE_SIZE;
	//}

	public int getID(){
		return id;
	}

	/*
	 * Removes this Node from the neighbours set first, then adds all the neighbours to their respective sets
	 */
	public void setNeighbourNodes(Set in, Set out){
		in.remove(this);
		out.remove(this);
		outNeighbours.addAll(out);
		inNeighbours.addAll(in);
	}

	public boolean isConnected(Node other){
		if(inNeighbours.contains(other) || outNeighbours.contains(other)){
			return true;
		}
		return false;
	}

	public Location getLocation(){
		return loc;
	}
}













