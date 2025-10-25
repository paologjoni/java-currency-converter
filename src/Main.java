import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
  public static void main(String[] args) {

    //Create main frame
    JFrame frame=new JFrame("My Simple Frame");

    //Frame properties
    frame.setTitle("Currency Converter");
    frame.setSize(400,250);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null); // Centers the window on the screen
    frame.setResizable(false); // Prevents resizing

    //Create labels
    JLabel label1=new JLabel("Currency 1:");
    JLabel label2=new JLabel("Convert from:");
    JLabel label3=new JLabel("Into:");

    //Create text field
    JTextField textField=new JTextField();

    //Create single item select
    String[] currencies={"EUR","USD","GBP","JPY","AUD","CAD","CHF","CNY",};
    JComboBox<String> fromBox=new JComboBox<>(currencies);
    String[] currencies1={"USD","EUR","GBP","JPY","AUD","CAD","CHF","CNY",};
    JComboBox<String> toBox=new JComboBox<>(currencies1);

    //Create panel for input and add layout to it
    JPanel inputs=new JPanel(new GridLayout(3,2,10,10));
    inputs.add(label1);
    inputs.add(textField);
    inputs.add(label2);
    inputs.add(fromBox);
    inputs.add(label3);
    inputs.add(toBox);

    //Make button for conversion
    JButton button=new JButton("Convert");

    //Make the convert button work
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Get selected currencies and user input
        String fromCurrency=(String)fromBox.getSelectedItem();
        String toCurrency=(String)toBox.getSelectedItem();
        String amountText=textField.getText();

        try {
          //Make api url with the from currency
          API api=new API("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/"
                  +fromCurrency.toLowerCase()+".json");

          //Convert input into number
          double amount=Double.parseDouble(amountText);

          //Call api
          double total=amount*api.call(toCurrency.toLowerCase());

          //Print result
          System.out.println(String.format("%.2f %s ==> %.2f %s",amount,fromCurrency,total,toCurrency));

          //Make message for result
          String message=String.format(
                  "Result\n\n%.2f %s = %.2f %s",
                  amount,
                  fromCurrency.toUpperCase(),
                  total,
                  toCurrency.toUpperCase()
          );

          //Display conversion
          JOptionPane.showMessageDialog(
                  frame,
                  message,
                  "Conversion Successful",
                  JOptionPane.INFORMATION_MESSAGE
          );

        } catch (NumberFormatException ex) {
          //Handle invalid input
          JOptionPane.showMessageDialog(frame,"Please enter a valid number for the amount.");
        }
      }
    });

    //Make panel for button, add button to panel
    JPanel panel=new JPanel();
    panel.add(button);

    // Add padding
    inputs.setBorder(new EmptyBorder(20,20,10,20));
    panel.setBorder(new EmptyBorder(10,20,20,20));

    //Add components to main frame
    frame.setLayout(new BorderLayout());
    frame.add(inputs,BorderLayout.CENTER);
    frame.add(panel,BorderLayout.SOUTH);

    //Display window
    frame.setVisible(true);
  }
}
