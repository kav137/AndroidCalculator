package imisno.com.calculator;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created by kav on 29.10.2016.
 */

abstract class Calculator {
    protected Expression execExpression; //is used for calculations. all the values are in dec form here
    protected String expressionRepresentation; //is used for display to user. values radix depends on current configuration
//    protected boolean isExpressionInitiated;
    protected boolean doesResetRequired;

    protected Operand currentOperand;
    protected String currentOperandRepresentation;

    //is used for the moment when currentOperator hasn't been initiated and we can change next operation
    protected String currentOperationRepresentation;

    public Calculator() {
        execExpression = new Expression();
        expressionRepresentation = "";
//        isExpressionInitiated = false;
        doesResetRequired = false;
        currentOperand = new Operand();
        currentOperandRepresentation = currentOperand.getRepresentation();
        currentOperationRepresentation = null;
    }

    public void setDoesResetRequired(boolean doesResetRequired) {
        this.doesResetRequired = doesResetRequired;
    }

    public void setExecExpression(Expression execExpression) {
        this.execExpression = execExpression;
    }

    public String getExpressionRepresentation() {
        return expressionRepresentation;
    }

    public String getCurrentOperandRepresentation() {
        return currentOperandRepresentation;
    }

    abstract void calculate () throws ValueIsTooLargeException;
    // when we press some operation button
    abstract void placeOperation (String operationRepresentation);
    // when we have entered the operand and pass it to expression
    abstract void placeOperand (String operandRepresentation);
    // when we press some digit or dot

    void addSymbolToCurrentOperand (String symbol) {
        if (doesResetRequired) {
            reset();
        }
        currentOperand.addSymbolToOperand(symbol);
        currentOperandRepresentation = currentOperand.getRepresentation();
    };

    void reset () {
        resetCurrentOperand();
        resetExpression();
        doesResetRequired = false;
    }

    void resetCurrentOperand () {
        currentOperand.reset();
        currentOperandRepresentation = currentOperand.getRepresentation();
    }

    void resetExpression () {
//        isExpressionInitiated = false;
        currentOperationRepresentation = null;
        doesResetRequired = false;
        expressionRepresentation = "";
        execExpression.setExpressionString(expressionRepresentation);
    }

    void backspaceCurrentOperand () {
        currentOperand.backspace();
        currentOperandRepresentation = currentOperand.getRepresentation();
    }

    void negateCurrentOperand () {
        currentOperand.negate();
        currentOperandRepresentation = currentOperand.getRepresentation();
    }

    protected void trimExpressionLastChar (){
        expressionRepresentation = expressionRepresentation.substring(0, expressionRepresentation.length() - 1);
    }
}
