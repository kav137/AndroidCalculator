package imisno.com.calculator;

import android.nfc.Tag;
import android.text.format.Formatter;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by kav on 29.10.2016.
 */

class SimpleCalculator extends Calculator {
    private static final String TAG = "Simple Calculator";
    public SimpleCalculator() {
        super();
    }

    @Override
    void placeOperation(String operationRepresentation) {
        //check operand if it is valid we continue with it's value, drop otherwise
        if (isValueLimitsExceeded(new BigDecimal(currentOperandRepresentation))){
            resetIfNecessary();
        }
        if (currentOperand.isOperandInitiated()) {
            placeOperand(currentOperandRepresentation);
            currentOperationRepresentation = operationRepresentation;
            expressionRepresentation += currentOperationRepresentation;
        }
        else {
            if (currentOperationRepresentation != null && currentOperationRepresentation.length() != 0) {
                trimExpressionLastChar();
                currentOperationRepresentation = operationRepresentation;
                expressionRepresentation += currentOperationRepresentation;
            }
        }
    }

    @Override
    void placeOperand(String operandRepresentation) {
        resetIfNecessary();
        //what for?
//        if (currentOperationRepresentation == null && bracketsCounter != 0){
//            resetExpression();
//        }
        if (operandRepresentation.charAt(operandRepresentation.length()-1) == '.' && operandRepresentation.length() > 1) {
            currentOperand.backspace();
            operandRepresentation = currentOperand.getRepresentation();
        }
        if (currentOperand.isNegate()){
            expressionRepresentation += "(" + operandRepresentation + ")";
        }
        else {
            expressionRepresentation += operandRepresentation;
        }
        resetCurrentOperand();
    }

    @Override
    void calculate () throws ValueIsTooLargeException{
        if (currentOperand.isOperandInitiated()){
            placeOperand(currentOperandRepresentation);
        }
        else if (currentOperationRepresentation != null){
            trimExpressionLastChar();
            currentOperationRepresentation = null;
        }

        //close all brackets
        while(bracketsCounter > 0) {
            expressionRepresentation += ")";
            bracketsCounter--;
        }

        //just to be sure
        if (expressionRepresentation.length() != 0) {
            execExpression.setExpressionString(expressionRepresentation);
            BigDecimal result = BigDecimal.valueOf(execExpression.calculate());
            currentOperand.setRepresentation(result.toPlainString());
            currentOperandRepresentation = currentOperand.getRepresentation();

            int lengthLimit = 18;
            if (currentOperandRepresentation.length() >= lengthLimit){
                currentOperand.setRepresentation(currentOperandRepresentation.substring(0, lengthLimit-1));
                currentOperandRepresentation = currentOperand.getRepresentation();
            }

            if (isValueLimitsExceeded(result)){
                doesOperandResetRequired = true;
                currentOperand.reset();
                throw new ValueIsTooLargeException("too large result");
            }
        }
        doesOperandResetRequired = true;
        doesExpressionResetRequired = true;
    }

    private boolean isValueLimitsExceeded (BigDecimal value){
        boolean isLimitExceeded = false;
        String limit = "999999999999";
        int maxComparisonResult = value.compareTo(new BigDecimal(limit));
        int minComparisonResult = value.compareTo(new BigDecimal("-" + limit));
        if (maxComparisonResult == 1 || minComparisonResult == -1) {
            isLimitExceeded = true;
        }

        return isLimitExceeded;
    }

}
