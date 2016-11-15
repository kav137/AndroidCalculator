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
        if (doesResetRequired) {
            resetExpression();
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
        if (doesResetRequired){
            resetExpression();
            doesResetRequired = false;
        }
        if (operandRepresentation.charAt(operandRepresentation.length()-1) == '.') {
            trimExpressionLastChar();
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
        if (expressionRepresentation.length() != 0) {
            execExpression.setExpressionString(expressionRepresentation);
            BigDecimal result = BigDecimal.valueOf(execExpression.calculate());
            currentOperand.setRepresentation(result.toPlainString());
            currentOperandRepresentation = currentOperand.getRepresentation();
            if (currentOperandRepresentation.length() >= 18){
                doesResetRequired = true;
                throw new ValueIsTooLargeException("too large result");
            }
        }
        doesResetRequired = true;
    }

}
