package fernuni.propra.user_interface;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * A frame that displays an {@link IRoom} instance. The window can be closed by
 * clicking
 * <p>
 * The actual room display is implemented in {@link RoomPanelAbstract}.
 * <p>
 * 
 * @author alex
 *
 */
public class RoomFrame extends JFrame {
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
