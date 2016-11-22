package imisno.com.calculator;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kav on 13.11.2016.
 */

class Operand {
    private final String TAG = "Operand";
    private String representation;
    private double decValue;
    private int radix;
    private boolean isOperandInitiated;
    private boolean isPeriodProvided;
    private boolean isNegate;
    private final Pattern decPattern =  Pattern.compile("(0|\\d+)(?:\\.\\d*)?");
    private final Pattern binPattern = Pattern.compile("1(1|0)+");
    private final Pattern octPattern = Pattern.compile("([1-7][0-7]+)");
    private final Pattern hexPattern = Pattern.compile("([1-9A-Fa-f][0-9A-Fa-f]*)");


    public Operand() {
        setRadix(10);
        setRepresentation("0");
        isPeriodProvided = false;
        isNegate = false;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
        setDecValueAccordingRepresentation();
        if (decValue == 0) {
            isOperandInitiated = false;
        }
        else {
            isOperandInitiated = true;
        }
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public void addSymbolToOperand (String symbol){
        if (isPeriodProvided && symbol.equals(".")){
            return;
        }
        if (isOperandInitiated){
            //trim too big values (int)
            if (representation.length() >= 11 && !isPeriodProvided && !symbol.equals(".")){
                representation = representation.substring(0, representation.length() - 1);
            }
            //trim floating part
            if (representation.length() >= 17 && isPeriodProvided){
                if (representation.charAt(representation.length() - 1) == '.') {
                    isPeriodProvided = false;
                }
                representation = representation.substring(0, representation.length() - 1);
            }

            String newRepresentation = representation + symbol;
            if (symbol.equals(".")){
                isPeriodProvided = true;
            }
            representation = newRepresentation;
            setDecValueAccordingRepresentation();
        }
        else{
            if (symbol.equals("0") && representation.equals("0")){
                return;
            }

            if (symbol.equals(".")){
                representation += "."; //representation is "0" already
                isPeriodProvided = true;
            }
            else{
                representation = symbol;
            }
            setDecValueAccordingRepresentation();
            isOperandInitiated = true;
        }
    }

    public void backspace () {
        if (!isOperandInitiated) {
            return;
        }
        int indexOfLastChar = representation.length() - 1;
        Character lastSymbol = representation.charAt(indexOfLastChar);
        if (lastSymbol == '.'){
            isPeriodProvided = false;
        }
        representation = representation.substring(0, indexOfLastChar);
        if (representation.length() == 0 || representation.equals("-") || representation.equals("-0")){
            representation = "0";
            isNegate = false;
            isOperandInitiated = false;
        }
        setDecValueAccordingRepresentation();
    }

    public void negate () {
        if (decValue == 0) {
            return;
        }
        if (isNegate) {
            representation = representation.substring(1, representation.length());
        }
        else {
            representation = "-" + representation;
        }
        isNegate = !isNegate;
        setDecValueAccordingRepresentation();
    }

    public void reset () {
        representation = "0";
        isOperandInitiated = false;
        isPeriodProvided = false;
        isNegate = false;
        setDecValueAccordingRepresentation();
    }

    public String getRepresentation() {
        return representation;
    }

    public double getDecValue() {
        return decValue;
    }

    public boolean isOperandInitiated() {
        return isOperandInitiated;
    }

    public boolean isNegate() {
        return isNegate;
    }

    private void setDecValueAccordingRepresentation(){
        decValue = Converter.getDecValueFromString(representation, radix);
    }

    private boolean checkRepresentationForPattern (String testString) {
        Matcher matcher;
        switch (radix) {
            case 2 : {
                matcher = binPattern.matcher(testString);
                break;
            }
            case 8 : {
                matcher = octPattern.matcher(testString);
                break;
            }
            case 10 : {
                matcher = decPattern.matcher(testString);
                break;
            }
            case 16 : {
                matcher = hexPattern.matcher(testString);
                break;
            }
            default:{
                Log.e(TAG, "checkRepresentationForPattern : unknown radix " + radix);
                return false;
            }
        }
        return (matcher.groupCount() == 1);
    }
}
