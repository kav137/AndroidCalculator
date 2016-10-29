package imisno.com.calculator;

import android.text.format.Formatter;
import android.util.Log;
import android.util.StringBuilderPrinter;

/**
 * Created by kav on 29.10.2016.
 */

class SimpleCalculator extends Calculator {
    private static final String TAG = "Simple Calculator";
    public SimpleCalculator() {
        super();
    }

    @Override
    void selectOperation(String operationRepresentation) {
        if (isExpressionInitiated) {
            if (isCurrentOperandInitiated) {
                placeOperand(currentOperandRepresentation);
                currentOperationRepresentation = operationRepresentation;
                resetCurrentOperand();
            }
            else {
                currentOperationRepresentation = operationRepresentation;
            }
        }
    }

    @Override
    void placeOperand(String operandRepresentation) {

    }

    @Override
    void placeOperation (String operationRepresentation) {

    }

    @Override
    void addSymbolToCurrentOperator(String symbol) {
        String newValue = currentOperandRepresentation + symbol;
        try {
            Double.parseDouble(newValue);
            currentOperandRepresentation = newValue;
        }
        catch (Exception e) {
            Log.d(TAG, String.format("addSymbolToCurrentOperator : prevented conversion of %1$s to double", newValue));
        }
    }

    @Override
    void calculate() {
        
    }
}
