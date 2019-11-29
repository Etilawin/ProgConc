import graphic.Window;
import java.awt.Point;
import java.util.concurrent.ExecutorService;

public class VonKochMono {
	private final static double LG_MIN = 8.0;
	Window f;
	ExecutorService e;


	public VonKochMono (Window f, Point a, Point b, Point c, ExecutorService e) {
		this.f = f;
		e.execute(new Cote(f, b, a, e));
		e.execute(new Cote(f, a, c, e));
		e.execute(new Cote(f, c, b, e));
	}			
}