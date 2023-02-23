import javax.swing.*;
class gui{
    public static void main(String args[]){
        JFrame frame = new JFrame("Bin.G Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button = new JButton("HOI");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }
}