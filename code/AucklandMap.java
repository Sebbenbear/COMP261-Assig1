import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.io.*;

//http://zetcode.com/gfx/java2d/basicdrawing/

public class AucklandMap extends GUI {

	private static HashMap <Integer, Node> nodeMap = new HashMap<>();
	private static HashMap <Integer, Road> roadMap = new HashMap<>();
	private static ArrayList<Segment> segmentList = new ArrayList<>();

	private int shiftHorizontal = 0;
	private int shiftVertical = 0;

	private int scale = 100;

	//Auckland Central - Center
	public Location origin = Location.newFromLatLon(-36.847622, 174.763444);

	public AucklandMap(){
		//CONSTRUCTOR DOES NOT NEED TO CONTAIN ANYTHING AS OF YET
	}

	@Override
	protected void redraw(Graphics g) {

		//need to factor in scale and redraw methods
		Dimension d = getDrawingAreaDimension();

		for(Node n : nodeMap.values()){
			n.draw(g, scale, origin, shiftVertical, shiftHorizontal, d);
		}
		for(Segment s : segmentList){
			s.draw(g, scale, origin, shiftVertical, shiftHorizontal, d);
			System.out.println("Drawn" + s);
		}
	}

	@Override
	protected void onClick(MouseEvent e) {
	}

	@Override
	protected void onSearch() {
		getTextOutputArea().setText(getSearchBox().getText());
	}

	@Override
	protected void onMove(Move m) {
		if(m == Move.NORTH){
			shiftVertical -= 10;
			System.out.println("shiftVertical:" + shiftVertical + "shiftHorizontal: " + shiftHorizontal);
		}
		else if(m == Move.SOUTH){
			shiftVertical += 10;
			System.out.println("shiftVertical:" + shiftVertical + "shiftHorizontal: " + shiftHorizontal);
		}
		else if(m == Move.EAST){
			shiftHorizontal += 10;
			System.out.println("shiftVertical:" + shiftVertical + "shiftHorizontal: " + shiftHorizontal);
		}
		else if(m == Move.WEST){
			shiftHorizontal -= 10;
			System.out.println("shiftVertical:" + shiftVertical + "shiftHorizontal: " + shiftHorizontal);
		}
		else if(m == Move.ZOOM_IN){
			//change the scale
			scale += 10;
		}
		else {
			scale -= 10;
		}
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		//PROCESS NODES FILE

		try {
			BufferedReader reader = new BufferedReader(new FileReader(nodes));
			String currentLine = null;
			String [] lineArray;

			while((currentLine = reader.readLine()) != null){
				lineArray = currentLine.split("\t");

				int id = Integer.parseInt(lineArray[0]);

				double lat = Float.parseFloat(lineArray[1]);
				double lon = Float.parseFloat(lineArray[2]);

				Location location = Location.newFromLatLon(lat, lon);

				nodeMap.put(id, new Node(id,location));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Loaded all nodes.");

		//PROCESS ROADS FILE

		try {
			BufferedReader reader = new BufferedReader(new FileReader(roads));   //just reassign, don't need to allocate more space
			String currentLine = null;
			String [] lineArray;

			reader.readLine();

			while((currentLine = reader.readLine()) != null){
				lineArray = currentLine.split("\t");

				int roadid = Integer.parseInt(lineArray[0]);
				int type= Integer.parseInt(lineArray[1]);
				String label = lineArray[2];
				String city = lineArray[3];
				int oneway= Integer.parseInt(lineArray[4]);
				int speed= Integer.parseInt(lineArray[5]);
				int roadclass= Integer.parseInt(lineArray[6]);
				int notforcar= Integer.parseInt(lineArray[7]);
				int notforpede= Integer.parseInt(lineArray[8]);
				int notforbicy= Integer.parseInt(lineArray[9]);

				roadMap.put(roadid, new Road(roadid,type,label,city,oneway,speed,roadclass,notforcar,notforpede,notforbicy));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Loaded all roads.");

		//PROCESS SEGMENTS FILE

		try {
			BufferedReader reader = new BufferedReader(new FileReader(segments));
			String currentLine = null;
			String [] lineArray;

			//THROWAWAY THE FIRST LINE OF TEXT
			reader.readLine();

			while((currentLine = reader.readLine()) != null){
				lineArray = currentLine.split("\t");
				//System.out.println("roadID" + Integer.parseInt(lineArray[0]));
				int roadid = Integer.parseInt(lineArray[0]);
				Double roadLength = Double.parseDouble(lineArray[1]);
				int nodeID1 = Integer.parseInt(lineArray[2]);
				int nodeID2 = Integer.parseInt(lineArray[3]);

				ArrayList<Location> coords = new ArrayList<Location>();
				for(int i = 4; i < lineArray.length; i+=2){
					double lat = Double.parseDouble(lineArray[i]);
					double lon = Double.parseDouble(lineArray[i+1]);
					coords.add(Location.newFromLatLon(lat,lon));
				}
				Road road = roadMap.get(roadid);

				Node startNode = nodeMap.get(nodeID1);
				Node endNode = nodeMap.get(nodeID2);

				segmentList.add(new Segment(road, roadid, roadLength, startNode, endNode, coords));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Loaded all segments.");

		constructGraph();
	}

	public static void constructGraph(){

		Graph graph = new Graph(nodeMap, segmentList, roadMap);

		//Set up all the nodes so they know who their neighbours are
		graph.setNeighbourNodes();

		//Set up all the roads so they know what segments they have

	}

	public static void main(String[] args){
		new AucklandMap();
	}

}
