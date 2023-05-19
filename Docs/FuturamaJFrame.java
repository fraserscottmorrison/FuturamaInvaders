//
//  FuturamaJFrame.java
//  Futurama
//
//  Created by Fraser on 7/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

/*
	This is the JFrame class for Futurama Space Invaders.  It creates an instance of the JPanel
	class, adds it to the content pane of the window, and then makes the window visible.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FuturamaJFrame extends JFrame {
	public FuturamaJFrame(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel frameContent = new FuturamaJPanel();
		Container visibleArea = getContentPane();
		visibleArea.add(frameContent);
		setVisible(true);
	}
}


