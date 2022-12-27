package de.bellmannjan.javaguibuilder;

import java.awt.*;
import javax.swing.*;


public class CreateClass extends JFrame {

  
  public CreateClass() {
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 300;
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("CreateClass");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    
    setVisible(true);
  }
  
  public static void main(String[] args) {
    new CreateClass();
  }

}
