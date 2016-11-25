package imisno.com.calculator;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created by kav on 29.10.2016.
 */

abstract class Calculator {
    protected Expression execExpression; //is used for calculations. all the values are in dec form here
    protected String expressionRepresentation; //is used for display to user. values radix depends on current configuration
    protected boolean doesExpressionResetRequired;
    protected boolean doesOperandResetRequired;

    protected Operand currentOperand;
    protected String currentOperandRepresentation;

    //is used for the moment when currentOperator hasn't been initiated and we can change next operation
    protected String currentOperationRepresentation;
    protected int bracketsCounter;

    abstract void calculate () throws ValueIsTooLargeException;
    // when we press some operation button
    abstract void placeOperation (String operationRepresentation);
    // when we have entered the operand and pass it to expression
    abstract void placeOperand (String operandRepresentation);
    abstract void setRadix (int newRadix);

    public Calculator() {
        execExpression = new Expression();
        expressionRepresentation = "";
        currentOperationRepresentation = null;
        currentOperand = new Operand();
        updateCurrentOperandRepresentation();
        bracketsCounter = 0;
        doesExpressionResetRequired = false;
        doesOperandResetRequired = false;
    }

    // when we press some digit or dot
    void addSymbolToCurrentOperand (String symbol) {
        resetIfNecessary();
        currentOperand.addSymbolToOperand(symbol);
        updateCurrentOperandRepresentation();
    }

    void backspaceCurrentOperand () {
        resetIfNecessary();
        currentOperand.backspace();
        updateCurrentOperandRepresentation();
    }

    void negateCurrentOperand () {
        currentOperand.negate();
        updateCurrentOperandRepresentation();
    }

    void openBracket () {
        resetIfNecessary();
        if (currentOperationRepresentation != null
                || expressionRepresentation.endsWith("(")
                || expressionRepresentation.length() == 0) {
            expressionRepresentation += "(";
            currentOperationRepresentation = null;
        }
        else {
            expressionRepresentation += "*(";
        }
        bracketsCounter++;
    }

    void closeBracket () {
        resetIfNecessary();
        if (bracketsCounter <= 0) {
            return;
        }
        if (expressionRepresentation.endsWith("(")){
            trimExpressionLastChar();
            bracketsCounter--;
            //restore operationRepresentation
            if (expressionRepresentation.length() != 0){
                Character lastChar = expressionRepresentation.charAt(expressionRepresentation.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    currentOperationRepresentation = lastChar.toString();
                }
            }
            return;
        }
        if (currentOperationRepresentation != null) {
            if (currentOperand.isOperandInitiated()){
                placeOperand(currentOperandRepresentation);
            }
            else {
                trimExpressionLastChar();
            }
            currentOperationRepresentation = "*";
            expressionRepresentation += ")*";
        }
        else {
            if (currentOperand.isOperandInitiated()){
                placeOperand(currentOperandRepresentation);
            }
            expressionRepresentation += ")";
        }
        bracketsCounter--;
    }

    void resetIfNecessary () {
        if (doesOperandResetRequired) {
            resetCurrentOperand();
        }
        if (doesExpressionResetRequired) {
            resetExpression();
        }
    }
    void reset () {
        resetCurrentOperand();
        resetExpression();
        bracketsCounter = 0;
    }

    void resetCurrentOperand () {
        currentOperand.reset();
        doesOperandResetRequired = false;
        updateCurrentOperandRepresentation();
    }

    void resetExpression () {
        bracketsCounter = 0;
        currentOperationRepresentation = null;
        doesExpressionResetRequired = false;
        expressionRepresentation = "";
        execExpression.setExpressionString(expressionRepresentation);
    }

    protected void trimExpressionLastChar (){
        expressionRepresentation = expressionRepresentation.substring(0, expressionRepresentation.length() - 1);
    }

    protected void updateCurrentOperandRepresentation () {
        currentOperandRepresentation = currentOperand.getRepresentation();
    }

    public void setDoesExpressionResetRequired(boolean doesExpressionResetRequired) {
        this.doesExpressionResetRequired = doesExpressionResetRequired;
    }

    public void setDoesOperandResetRequired(boolean doesOperandResetRequired) {
        this.doesOperandResetRequired = doesOperandResetRequired;
    }

    public String getExpressionRepresentation() {
        return expressionRepresentation;
    }

    public String getCurrentOperandRepresentation() {
        return currentOperandRepresentation;
    }
}
