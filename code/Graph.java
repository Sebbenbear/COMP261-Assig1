import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Graph {

	private Map<Integer,Node> nodes = new HashMap<>();
	private Map <Integer,Road> roads = new HashMap<>();
	private List <Segment> segments = new ArrayList<>();

	public Graph (Map nodes, List segments, Map roads){
		this.nodes = nodes;
		this.segments = segments;
		this.roads = roads;
	}

	//List all the Nodes
	public void listNodes (){
		for(Map.Entry<Integer,Node> id : nodes.entrySet()){
			System.out.println(id.getValue().toString());
		}
	}

	//List all edges
	public void listEdges (){
		for(Entry<Integer, Road> id : roads.entrySet()){
			System.out.println(id.getValue().toString());
		}
	}

	public void setNeighbourNodes(){

		for(Node n : nodes.values()){      			       //for every node

			Set <Node> inNeighbours = new HashSet<>();     //make two new sets to add nodes to for every segment.
			Set <Node> outNeighbours = new HashSet<>();

			for(Segment s : segments){    					//for every segment
				Set <Node> segmentNodes = s.getNodes();
				if(segmentNodes.contains(n)){	   			//if the segments array of nodes contains this node

					//IF THE ROAD THE SEGMENT IS LINKED TO IS ONE WAY, THEN ADD TO OUTNEIGHBOURS
					if(s.getRoad().getOneway() == 0){
						inNeighbours.add(n);
					}
					else {
						outNeighbours.add(n);
					}
				}
			}
			//NOW THAT YOU'VE ADDED THE NODE FROM THE SEGMENT, GO ON TO ADD THE OTHER ONES. WILL ONLY CONTAIN THE CURRENT NODE ONCE, SINCE IT'S A SET
			//update neighbours
			n.setNeighbourNodes(inNeighbours, outNeighbours);
		}
	}
}

























