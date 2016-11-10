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
            placeOperation(operationRepresentation);
        }
    }

    @Override
    void placeOperand(String operandRepresentation) {
        isExpressionInitiated = true;
        expressionRepresentation += operandRepresentation;
        resetCurrentOperand();
    }

    @Override
    void placeOperation (String operationRepresentation) {
        if (isCurrentOperandInitiated) {
            placeOperand(currentOperandRepresentation);
            currentOperationRepresentation = operationRepresentation;
        }
        else {
            if (currentOperationRepresentation != null && currentOperationRepresentation.length() != 0) {
                expressionRepresentation = expressionRepresentation.substring(0, expressionRepresentation.length() - 2);
                currentOperationRepresentation = operationRepresentation;
                expressionRepresentation += currentOperationRepresentation;
            }
        }
    }


    @Override
    void addSymbolToCurrentOperator(String symbol) {
        String newValue = currentOperandRepresentation + symbol;
        try {
            currentOperand.setOperandValue(Double.parseDouble(newValue));
            currentOperandRepresentation = currentOperand.getOperandRepresentation();
        }
        catch (Exception e) {
            Log.d(TAG, String.format("addSymbolToCurrentOperator : prevented conversion of %1$s to double", newValue));
        }
    }

    @Override
    void calculate() {
        if (isCurrentOperandInitiated){
            placeOperand(currentOperandRepresentation);
        }
        if (expressionRepresentation.length() != 0) {
            execExpression.setExpressionString(expressionRepresentation);
            double result = execExpression.calculate();
            currentOperand.setOperandValue(result);

        }
    }
}
