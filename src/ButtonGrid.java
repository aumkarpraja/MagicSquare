import javax.swing.JFrame; //imports JFrame library
import javax.swing.JButton; //imports JButton library
import java.awt.GridLayout; //imports GridLayout library

public class ButtonGrid {

    JFrame frame=new JFrame(); //creates frame
    JButton[][] grid; //names the grid of buttons

    public ButtonGrid(int width, int length){ //constructor
        frame.setLayout(new GridLayout(width,length)); //set layout
        grid=new JButton[width][length]; //allocate the size of grid
        for(int y=0; y<length; y++){
            for(int x=0; x<width; x++){
                grid[x][y]=new JButton("("+x+","+y+")"); //creates new button
                frame.add(grid[x][y]); //adds button to grid
            }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); //sets appropriate size for frame
        frame.setVisible(true); //makes frame visible
    }
    public static void main(String[] args) {
        new ButtonGrid(60,60);//makes new ButtonGrid with 2 parameters
    }
}