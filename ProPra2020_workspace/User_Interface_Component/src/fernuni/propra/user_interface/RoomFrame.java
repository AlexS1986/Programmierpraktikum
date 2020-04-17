package fernuni.propra.user_interface;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class RoomFrame extends JFrame{
	RoomPanelAbstract roomPanel;
	
	public RoomFrame(RoomPanelAbstract roomPanel) {
		this.roomPanel = roomPanel;
		setTitle(this.roomPanel.getRoomID());
		add(roomPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

}