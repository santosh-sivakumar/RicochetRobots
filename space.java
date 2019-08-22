public class Space {
	
	int x;
	int y;

	Space (int x,int y) {
		assert (x <= 16): "Incompatible space";
		assert (y <= 16): "Incompatible space";
		this.x = x;
		this.y = y;
	}
}