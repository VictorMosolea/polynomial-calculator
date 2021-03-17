package com.pt2021_1;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    @FXML
    TextField polynomialInput1;
    @FXML
    TextField polynomialInput2;
    @FXML
    TextField resultText;

    @FXML
    Button addButton;
    @FXML
    Button subtractButton;
    @FXML
    Button multiplyButton;
    @FXML
    Button divideButton;
    @FXML
    Button moduloButton;
    @FXML
    Button diffButton1;
    @FXML
    Button diffButton2;
    @FXML
    Button integrateButton1;
    @FXML
    Button integrateButton2;
    @FXML
    Button resultButton;
    @FXML
    Button closeButton;

    @FXML
    Label errorLabel1;
    @FXML
    Label errorLabel2;


    Button selectedButton;

    Polynomial poly1;
    Polynomial poly2;
    Polynomial resultPoly;

    @FXML
    static Polynomial parsePolynomial(String input) {
        Polynomial newPolynomial = new Polynomial();
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            double coefficient = 1.0;
            int exponent = 0;
            String[] dummy = matcher.group(1).split("x");
            if (dummy.length > 0) {
                if (dummy[0].matches(".*\\d.*")) {
                    coefficient = Double.parseDouble(dummy[0]);
                } else if (dummy[0].matches("-.*")) {
                    coefficient = -1.0;
                }
                if (dummy.length > 1) {
                    dummy = dummy[1].split("\\^");
                    exponent = Integer.parseInt(dummy[1]);
                } else if (matcher.group(1).matches(".*x.*")) {
                    exponent = 1;
                }
            } else if (matcher.group(1).matches(".*x.*")) {
                exponent = 1;
            }
            newPolynomial.addMonomial(new Monomial(exponent, coefficient));
        }
        newPolynomial.removeZeroes();
        newPolynomial.reduce();
        return newPolynomial;
    }

    @FXML
    private void selectOperation(ActionEvent event) {
        selectedButton.setStyle("-fx-background-color: #404040");
        selectedButton = (Button) event.getSource();
        selectedButton.setStyle("-fx-background-color: #212121");
    }

    @FXML
    private void closeApp() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedButton = addButton;
        selectedButton.setStyle("-fx-background-color: #212121; -fx-effect: none");
    }

    public boolean validInput(String input) {
        return input.matches("([+-]?(?:(?:\\d+x\\^\\d+)|(?:x\\^\\d+)|(?:\\d+x)|(?:\\d+)|(?:x)))+");
    }

    @FXML
    private void displayResult() {
        poly1 = null;
        poly2 = null;
        resultPoly = null;
        errorLabel1.setText("");
        errorLabel2.setText("");
        resultText.setText("-");
        if (validInput(polynomialInput1.getText()) && selectedButton.getText().equals("DIFFERENTIATE (1)")) {
            poly1 = parsePolynomial(polynomialInput1.getText());
            resultPoly = poly1.differentiate();
        } else if (validInput(polynomialInput1.getText()) && selectedButton.getText().equals("INTEGRATE (1)")) {
            poly1 = parsePolynomial(polynomialInput1.getText());
            resultPoly = poly1.integrate();
        } else if (validInput(polynomialInput2.getText()) && selectedButton.getText().equals("DIFFERENTIATE (2)")) {
            poly2 = parsePolynomial(polynomialInput2.getText());
            resultPoly = poly2.differentiate();
        } else if (validInput(polynomialInput2.getText()) && selectedButton.getText().equals("INTEGRATE (2)")) {
            poly2 = parsePolynomial(polynomialInput2.getText());
            resultPoly = poly2.integrate();
        } else if (validInput(polynomialInput1.getText()) && validInput(polynomialInput2.getText())) {
            poly1 = parsePolynomial(polynomialInput1.getText());
            poly2 = parsePolynomial(polynomialInput2.getText());
            switch (selectedButton.getText()) {
                case "ADD":
                    resultPoly = poly1.add(poly2);
                    break;
                case "SUBTRACT":
                    resultPoly = poly1.subtract(poly2);
                    break;
                case "DIVIDE":
                    if (!poly2.isZero())
                        resultPoly = poly1.divide(poly2);
                    else
                        errorLabel2.setText("INVALID INPUT! CANNOT DIVIDE BY 0!");
                    break;
                case "MOD":
                    if (!poly2.isZero())
                        resultPoly = poly1.modulo(poly2);
                    else
                        errorLabel2.setText("INVALID INPUT! CANNOT MOD 0!");
                    break;
                case "MULTIPLY":
                    resultPoly = poly1.multiply(poly2);
                    break;
            }
        } else {
            if (!validInput(polynomialInput1.getText()) && resultPoly == null && !selectedButton.getText().equals("DIFFERENTIATE (2)") &&
                    !selectedButton.getText().equals("INTEGRATE (2)"))
                errorLabel1.setText("INVALID POLYNOMIAL!");
            if (!validInput(polynomialInput2.getText()) && resultPoly == null && !selectedButton.getText().equals("DIFFERENTIATE (1)") &&
                    !selectedButton.getText().equals("INTEGRATE (1)"))
                errorLabel2.setText("INVALID POLYNOMIAL!");
        }
        if (resultPoly != null) {
            resultPoly.removeZeroes();
            resultPoly.reduce();
            resultText.setText(resultPoly.printPolynomial());
            if (resultText.getText().isEmpty()) {
                resultText.setText("0");
            }
        }
    }
}
