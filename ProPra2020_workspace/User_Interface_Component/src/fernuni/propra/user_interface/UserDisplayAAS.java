package fernuni.propra.user_interface;
import fernuni.propra.internal_data_model.IRoom;

public class UserDisplayAAS {
	public void display(IRoom room) {
		RoomPanelAbstract roomPanel = new RoomPanel(room);
		RoomFrame roomFrame = new RoomFrame(roomPanel);
	}

}