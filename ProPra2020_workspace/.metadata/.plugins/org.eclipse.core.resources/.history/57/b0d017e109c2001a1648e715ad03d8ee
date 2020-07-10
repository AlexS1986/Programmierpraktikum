package fernuni.propra.user_interface;
import fernuni.propra.internal_data_model.IRoom;

/**
 * This is an use case that provides the user with 
 * access to the possibility to display an {@link IRoom}
 * graphically on a screen.
 * <p>
 * The actual displaying is delegated to 
 * {@link RoomFrame} and {@link RoomPanel}
 * <p>
 * @author alex
 *
 */
public class UserDisplayAAS {
	/**
	 * Displays an {@link IRoom} on the screen.
	 * 
	 * @param room : The {@link IRoom} to be displayed.
	 */
	public void display(IRoom room) {
		RoomPanelAbstract roomPanel = new RoomPanel(room);
		RoomFrame roomFrame = new RoomFrame(roomPanel);
	}

}
