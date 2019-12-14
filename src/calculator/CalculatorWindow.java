package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class CalculatorWindow <T extends Number> extends JFrame {
    public static CalculatorWindow calculatorWindow = null;
    private JPanel buttonsPanel;
    private JLabel label;
    private JButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
    buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonDecimal, buttonEquals, buttonClearEntry;
    private double a = 0.0;
    private double b = 0.0;
    private Double result;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public CalculatorWindow() throws HeadlessException {
        setTitle("Calculator");
        setSize(400, 600);
        setMaximumSize(new Dimension(600, 800));
        setMinimumSize(new Dimension(300, 500));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        initButtons();
        initLabel();
        setVisible(true);
    }

    private void clearEntry(ActionEvent e){
        label.setText("");
        result = 0.0;
        a = 0.0;
        b = 0.0;
    }

    private void buttonClicked(ActionEvent e) {
        label.setText(label.getText() + (e.getActionCommand()));
    }

    private void calculate(ActionEvent e) {
        if (label.getText().contains("*")||label.getText().contains("/")||label.getText().contains("-")
                || label.getText().contains("+")) {
            findResult(e);
        } else if (label.getText().isEmpty() && e.getActionCommand().equals("-")) {
            buttonClicked(e);
        } else {
            a = Double.parseDouble(label.getText());
            buttonClicked(e);
        }
    }

    private void findResult(ActionEvent e) {
        if (label.getText().contains("*") && !label.getText().endsWith("*")) {
            multiply();
        } else if (label.getText().contains("/") && !label.getText().endsWith("/")) {
            divide();
        } else if (label.getText().contains("-") && !label.getText().endsWith("-")) {
            subtract(e);
        } else if ((label.getText().contains("+") && !label.getText().endsWith("+")) || e.getActionCommand().equals("+")){
            sum();
        }
        if (label.getText().equals("ERROR")) {
            return;
        } else if (e.getActionCommand() != "=") {
            copyToKeyboard(result.toString());
            label.setText(result.toString() + e.getActionCommand());
        } else if (e.getActionCommand() == "=") {
            label.setText(result.toString());
            copyToKeyboard(label.getText());
        }
        a = result;
        b = 0.0;
        result = 0.0;
    }

    private void copyToKeyboard(String string){
        StringSelection selection = new StringSelection(string);
        clipboard.setContents(selection, selection);
    }

    private void initButtons(){
        add(buttonsPanel = new JPanel(), BorderLayout.CENTER);
        buttonsPanel.add(button7 = new JButton("7"));
        button7.addActionListener(this::buttonClicked);
        buttonsPanel.add(button8 = new JButton("8"));
        button8.addActionListener(this::buttonClicked);
        buttonsPanel.add(button9 = new JButton("9"));
        button9.addActionListener(this::buttonClicked);
        buttonsPanel.add(buttonPlus = new JButton("+"));
        buttonPlus.addActionListener(this::calculate);
        buttonsPanel.add(button4 = new JButton("4"));
        button4.addActionListener(this::buttonClicked);
        buttonsPanel.add(button5 = new JButton("5"));
        button5.addActionListener(this::buttonClicked);
        buttonsPanel.add(button6 = new JButton("6"));
        button6.addActionListener(this::buttonClicked);
        buttonsPanel.add(buttonMinus = new JButton("-"));
        buttonMinus.addActionListener(this::calculate);
        buttonsPanel.add(button1 = new JButton("1"));
        button1.addActionListener(this::buttonClicked);
        buttonsPanel.add(button2 = new JButton("2"));
        button2.addActionListener(this::buttonClicked);
        buttonsPanel.add(button3 = new JButton("3"));
        button3.addActionListener(this::buttonClicked);
        buttonsPanel.add(buttonMultiply = new JButton("*"));
        buttonMultiply.addActionListener(this::calculate);
        buttonsPanel.add(buttonClearEntry = new JButton("CE"));
        buttonClearEntry.addActionListener(this::clearEntry);
        buttonsPanel.add(button0 = new JButton("0"));
        button0.addActionListener(this::buttonClicked);
        buttonsPanel.add(buttonDecimal = new JButton("."));
        buttonDecimal.addActionListener(this::buttonClicked);
        buttonsPanel.add(buttonDivide = new JButton("/"));
        buttonDivide.addActionListener(this::calculate);
        buttonsPanel.setLayout(new GridLayout(4,4,2, 2));
        add(buttonEquals = new JButton("="), BorderLayout.PAGE_END);
        buttonEquals.setPreferredSize(new Dimension(100,100));
        buttonEquals.addActionListener(this::findResult);
    }

    private void initLabel(){
        label = new JLabel("");
        label.setPreferredSize(new Dimension(100, 100));
        label.setFont(new Font("ARIAL", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        add(label, BorderLayout.PAGE_START);
    }

    private void multiply(){
        b = Double.parseDouble(label.getText().split("\\*")[1]);
        result = a * b;
    }

    private void divide(){
        b = Double.parseDouble(label.getText().split("\\/")[1]);
        if (b == 0) {
            label.setText("ERROR");
            result = 0.0;
            return;
        }
        result = a / b;
    }

    private void subtract(ActionEvent e){
        if (label.getText().startsWith("-") && label.getText().split("\\-").length == 1){
            a = Double.parseDouble(label.getText());
        } else if (label.getText().startsWith("-") && label.getText().split("\\-").length > 2) {
            a = Double.parseDouble("-" + label.getText().split("\\-")[1]);
            b = Double.parseDouble(label.getText().split("\\-")[2]);
        } else if ((e.getActionCommand() == "-" || label.getText().contains("-"))
                && label.getText().split("\\-")[1].length() >= 1
                && !e.getActionCommand().equals("+")){
            b = Double.parseDouble(label.getText().split("\\-")[1]);
        }
        result = a - b;
    }

    private void sum (){
        if (label.getText().startsWith("-")){
            a = Double.parseDouble(label.getText());
        } else if (label.getText().startsWith("-") && label.getText().split("\\+").length > 1) {
            a = Double.parseDouble(label.getText().split("\\+")[0]);
            b = Double.parseDouble(label.getText().split("\\+")[1]);
        } else if (label.getText().split("\\+").length > 1) {
            b = Double.parseDouble(label.getText().split("\\+")[1]);
        }
        result = a + b;
    }

    public static CalculatorWindow getInstance(){
        if(calculatorWindow != null){
            return calculatorWindow;
        } else {
            return new CalculatorWindow();
        }
    }
}