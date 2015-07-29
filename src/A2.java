import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Assignment that deals with magic square. Used for practice with 2D arrays,
 * listeners and JavaDoc. It will also help me understand Java graphics a bit
 * better along with File menus and ObjectInputStreams.
 *
 * @author Aumkar Prajapati
 * @version 1.0
 */

public class A2 {

    // A single square object
    public static Square square;

    // A main panel to be attached to the frame
    public static JPanel panel;

    // The main frame to hold the panel and button
    public static JFrame frame;

    // A button for the user to check if it's a magic number or not
    public static JButton button;


    public static void main(String[] args)
    {
        // Init the frame
        frame = new JFrame();

        // Init JPanel
        panel = new JPanel();

        // Init the custom menu bar (created below)
        MenuBar menu = new MenuBar();

        // Set the title of the frame
        frame.setTitle("A2 - Aumkar Prajapati");

        // Make the window visible
        frame.setVisible(true);

        // Exit the program when the exit button is hit on the window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Square frame size
        frame.setSize(600, 600);

        // Add the menu to the top of the frame
        frame.add(menu, BorderLayout.NORTH);

        // Add the JPanel to the center of the layout
        frame.add(panel, BorderLayout.CENTER);

        // For the user to check it's a magic square or not
        button = new JButton("Check");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                square.isMagic();
            }
        });

        // Add the button to the bottom
        frame.add(button, BorderLayout.SOUTH);

    }

    /**
     *  This menu bar is the main menu bar that will be attached to the frame of the
     *  main window. It will contain all the options as required by the assignment details.
     */
    public static class MenuBar extends JMenuBar
    {
        // JMenu "menu" option
        JMenu fileObj;
        JMenuItem[] fileItem;

        // The user input
        static int input;

        // Constructor
        public MenuBar()
        {
            // Menu bar part and the size of the menu
            fileObj = new JMenu("Menu");
            fileItem = new JMenuItem[4];

            // The options
            fileItem[0] = new JMenuItem("New");
            fileItem[1] = new JMenuItem("Read");
            fileItem[2] = new JMenuItem("Save");
            fileItem[3] = new JMenuItem("Exit");

            // Add all the options to the menu
            for (int i = 0; i < fileItem.length; i++)
            {
                fileObj.add(fileItem[i]);
            }

            // Add this to the menu bar
            add(fileObj);

            // All workings of the menu bar are beyond this line.
            // -----------------------------------------------------------------------------

            // NEW OPTION

            fileItem[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Surrounded in a try catch block to prevent strings and other things from ending up in the input box.
                    try {
                        input = Integer.parseInt(JOptionPane.showInputDialog("Input the size of the square (n by n):"));

                        // Make sure the number isn't gigantic. If it is, throw a message box saying to use a smaller value.
                        // The user probably won't use a value that big but this is just in case.
                        if (input > Integer.MAX_VALUE)
                        {
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, "The value you provided is too large, use a smaller value.");
                            input = 0; // So that if something is there, it won't repeat the operation of adding more to the square

                        }

                    }
                    // If the input is non-integer, show a message box with the issue.
                    catch (java.lang.NumberFormatException event)
                    {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Invalid entry, please use a valid integer.");
                        input = 0; // Same reason as above

                    }
                    // Initialize the Square object with a user specified input, only do this when the input is not invalid
                    if (input > 0)
                    {
                        square = new Square(input);
                        input = 0;
                    }

                }
            });

            // -----------------------------------------------------------------------------

            // READ OPTION - will read from a saved magic square (which will be in binary)
            fileItem[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Read");

                    // New File Chooser object
                    JFileChooser chooser = new JFileChooser();

                    // Null component for the file chooser
                    Component component = null;
                    File file = null;
                    int returnVal = chooser.showOpenDialog(component);

                    // If it's a valid file, assign it to File var file, which will be brought into the OIS
                    if (returnVal == chooser.APPROVE_OPTION)
                    {
                        file = chooser.getSelectedFile();
                        System.out.println("Opening: " + file);
                    }

                    try
                    {
                        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
                    }

                    catch(FileNotFoundException event)
                    {
                        event.printStackTrace();
                    }

                    catch(IOException event)
                    {
                        event.printStackTrace();
                    }
                }
            });

            // -----------------------------------------------------------------------------

            // SAVE OPTION - saves the current magic square in binary
            fileItem[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Save");
                }
            });

            // -----------------------------------------------------------------------------

            // EXIT OPTION - exits the program
            fileItem[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exit");
                    System.exit(0);
                }
            });
        }
    }

    /**
     * This is the Square class, it contains everything that is needed to create a Square in which
     * the user can input into. It also contains methods that modify the panel that the user interacts
     * with.
     */
    public static class Square
    {
        int[][] grid;
        JTextField[][] intField;

        /**
         * The Square constructor, in charge of initializing the interface based on user input.
         *
         * @param input the input that dictates the size of the array and the amount of JTextFields
         */
        public Square(int input)
        {
            // Initialize a 2D array of integers to take in user input
            grid = new int[input][input];

            // Debug message to show what the size of the array is in console
            System.out.println("Initialized: " + input);

            // Calls the createInterface method so the user input interface can be created
            createInterface(input);

        }

        /**
         * In charge of creating the interface that the user will input into.
         *
         * @param size allows a specifier for how big the array of JTextFields should be, since it is a square, we don't
         *             need to worry about the width or height of this array
         */
        public void createInterface(int size)
        {
            // A 2D array of JTextFields which will serve as a way for the user to input into the square
            intField = new JTextField[size+1][size+1];

            // A for loop and a nested for loop so that all the elements in the for loop are initialized
            for (int i = 0; i < intField.length - 1; i++)
            {
                for (int j = 0; j < intField.length - 1; j++)
                {
                    System.out.println (i + "," + j); // debug message

                    // Initializes a small text field for the user to enter into
                    intField[i][j] = new JTextField(2);

                    // Adds the current JTextField into the panel
                    panel.add(intField[i][j]);

                }
            }
            // GridLayout so that it initializes into a size by size grid (looks like a square)
            panel.setLayout(new GridLayout(size, size));

            // Pack the frame around the objects to make it look nicer
            frame.pack();
        }

        public void add(int i)
        {

        }


        /**
         * This checks the square for duplicates and to actually see if the values provided make a magic square.
         * This is what the check button in the frame links to, it provides the user with graphical notices as well if
         * they are missing numbers or have provided a bad input.
         *
         * @return true if the number is a magic number and false if it is not
         */
        public boolean isMagic()
        {
            int flag = 0; // Flags if something goes wrong

            // Save all to grid first
            for(int i = 0; i < intField.length - 1; i++)
            {
                for(int j = 0; j < intField.length - 1; j++)
                {
                    try {
                        grid[i][j] = Integer.parseInt(intField[i][j].getText());
                        System.out.println(intField[i][j].getText());
                        System.out.println(grid[i][j]);
                    }

                    // Catch any bad inputs by the user and point them out
                    catch (Exception e)
                    {
                        JFrame frame = new JFrame();

                        // Flag as a bad operation and don't let it think of it as a separate integer no matter what was in the square before
                        flag = 1;

                        // Tell the user where the problem is
                        JOptionPane.showMessageDialog(frame, "There's a bad input in row " + (i+1) + " column " + (j+1) + ". Please check and try again.");

                    }
                }
            }

            // dupe check is broken, fix it
        if (dupeCheck(grid) == true && flag == 0) // If there is a duplicate
        {
            //TODO, other operations to check the magic squareness
            messageHandler(true);
            return true;
        }
        else // if there is not a duplicate
        {
            messageHandler(false);
            return false;
        }
        }

        public boolean dupeCheck(int[][] intArray)
        {
            Set<Integer> intSet = new HashSet<Integer>();

           for (int i = 0; i < intArray.length; i++)
           {
               for (int j = 0; j < intArray.length; j++)
               {
                   if (intSet.contains(intArray[i][j]))
                   return false;
                   intSet.add(intArray[i][j]);

               }
           }
            return true;
        }

        /**
         * Handles the magic square check when the user clicks the button, pops up a message box telling the
         * user whether or not the numbers they specified makes it a magic square or not. This is just to make
         * it look a bit nicer. The isMagic function actually checks if it's a magic square or not.
         *
         * @param bool a boolean so that the message handler can figure out which message box to show
         */
        public void messageHandler(boolean bool)
        {
            // JFrame so that the messageHandler has a place to put the dialog
            JFrame frame = new JFrame();

            // If it's true, show the true message
            if (bool == true)
            {
                JOptionPane.showMessageDialog(frame, "This is a magic square!");
            }

            // Otherwise, show the false message
            else
            {
                JOptionPane.showMessageDialog(frame, "This is not a magic square!");
            }
        }
    }
}
