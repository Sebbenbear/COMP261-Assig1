import java.util.ArrayList;
import java.util.List;

public class Road {

	public static int roadid;
	public static int type;
	public static String label;
	public static String city;

	public static int oneway;
	public static int speed;
	public static int roadclass;
	public static int notforcar;
	public static int notforpede;
	public static int notforbicy;
	
	//which segments it contains
	List <Segment> segments = new ArrayList<>();

	public Road(int roadid, int type, String label, String city, int oneway, 
			int speed, int roadclass, int notforcar, int notforpede, int notforbicy){
		this.roadid = roadid;
		this.type = type;
		this.label = label;
		this.city = city;
		this.oneway = oneway;
		this.speed = speed;
		this.roadclass = roadclass;
		this.notforcar = notforcar;
		this.notforpede = notforpede;
		this.notforbicy = notforbicy;
	}

	public int roadid(){
		return roadid;
	}
	
	public int getOneway(){
		return oneway;
	}

}