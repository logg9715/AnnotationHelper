package main;

import javax.swing.JFrame;

public class PopupWindow extends JFrame{
	
	PopupWindow (String init_title, boolean init_reisze) {
		setTitle(init_title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(init_reisze);
	}
	
	public void visibleSetting(int sizeX, int sizeY) {
		setSize(sizeX, sizeY);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}


interface PopupInterface{
	void createElements();
	void closeWindow();
}