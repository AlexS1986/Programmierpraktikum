package fernuni.propra.internal_data_model;


public class Lamp extends Point{
	private boolean on;
	
	public Lamp(double x, double y) {
		super(x, y);
	}

	public void turnOn() {
		on = true;
	}
	
	public void turnOff() {
		on = false;
	}
	
	public boolean getOn() {
		return on;
	}
	
	
}
