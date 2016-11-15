package imisno.com.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kav on 21.10.2016.
 */

public class CalculatorOperand {
    private final String TAG = "CalculatorOperand";
    private double decValue;
    private String operandRepresentation;
    private int currentRadix;
//    private final Pattern decPattern = Pattern.compile("(0|\\d+)(\\.\\d*)?");
//    private final Pattern binPattern = Pattern.compile("1(1|0)+");
//    private final Pattern octPattern = Pattern.compile("([1-7][0-7]+)");

//    public boolean checkInputString (String newRepresentation) {
//        Matcher m = operandPattern.matcher(newRepresentation);
//        return (m.groupCount() == 1);
//    }



    private boolean isPeriodProvided;
    private boolean isCharAfterPeriodProvided;
    private boolean isOperandInitiated;


    public void setValueByRepresentation (String newRepresentation) {
        try {
            decValue = Converter.getDecValueFromString(newRepresentation, currentRadix);
            isOperandInitiated = true;
            updateRepresentation();
        }
        catch (Exception e){
            Log.e(TAG, String.format("SetValueByRepresentation : impossible " +
                    "to convert %1$s with radix %2$s", newRepresentation, currentRadix ) );
        }
    }

    private void updateRepresentation () {
        String newRepresentation = Converter.getStringFromDecValue(decValue, currentRadix);
        newRepresentation = (new BigDecimal(newRepresentation)).toPlainString();
        if (isPeriodProvided && !newRepresentation.contains(".")){
            newRepresentation += ".";
        }
        else if (newRepresentation.contains(".")){
            newRepresentation = newRepresentation.substring(0, newRepresentation.indexOf("."));
        }
        operandRepresentation = newRepresentation;
    }

    public void reset () {
        setValueByRepresentation("0");
        isPeriodProvided = false;
        isCharAfterPeriodProvided = false;
        isOperandInitiated = false;
    }

    public void addSymbolToOperand (String symbol) {
        if (isPeriodProvided){
            if (symbol == "."){
                return;
            }
            else {
                isCharAfterPeriodProvided = true;
            }
        }
        if (symbol == ".") {
            isPeriodProvided = true;
        }
        if (isOperandInitiated){
            setValueByRepresentation(operandRepresentation + symbol);
        }
        else {
            setValueByRepresentation(symbol);
        }
    }

    public void backspace () {
        if (decValue == 0){
            return;
        }
        if (isCharAfterPeriodProvided){
            if (operandRepresentation.indexOf(".") == operandRepresentation.length() - 2){
                isCharAfterPeriodProvided = false;
            }
        }
        else {
            if (isPeriodProvided){
                isPeriodProvided = false;
            }
        }
        operandRepresentation = operandRepresentation.substring(0, operandRepresentation.length() - 1);
        setValueByRepresentation(operandRepresentation);
    }

    public CalculatorOperand(int currentRadix) {
        this.currentRadix = currentRadix;
        decValue = 0;
        isOperandInitiated = false;
        isPeriodProvided = false;
        isCharAfterPeriodProvided = false;
        updateRepresentation();
    }

    public CalculatorOperand() {
        this(10);
    }

    public String getOperandRepresentation() {
        return operandRepresentation;
    }

    public boolean getOperandState () {
        return isOperandInitiated;
    }

    public double getDecValue() {
        return decValue;
    }

    public void setCurrentRadix(int currentRadix) {
        this.currentRadix = currentRadix;
        updateRepresentation();
    }

}
