import java.io.IOException;

public class pointsDBtest {

	public static void main(String[] args) throws IOException {
		
		pointsDB db = new pointsDB("Wagner12");
		db.setNewPoints(10000300);
		db.update();
		db.close();
	}
}
