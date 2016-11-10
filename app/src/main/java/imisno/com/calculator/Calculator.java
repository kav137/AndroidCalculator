package imisno.com.calculator;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created by kav on 29.10.2016.
 */

abstract class Calculator {
    protected Expression execExpression; //is used for calculations. all the values are in dec form here
    protected String expressionRepresentation; //is used for display to user. values radix depends on current configuration
    protected boolean isExpressionInitiated;

    protected CalculatorOperand currentOperand;
    protected String currentOperandRepresentation;
    protected boolean isCurrentOperandInitiated;

    //is used for the moment when currentOperator hasn't been initiated and we can change next operation
    protected String currentOperationRepresentation;

    public Calculator() {
        execExpression = new Expression();
        expressionRepresentation = "";
        isExpressionInitiated = false;
        currentOperand = new CalculatorOperand(0);
        currentOperandRepresentation = currentOperand.getOperandRepresentation();
        isCurrentOperandInitiated = false;
        currentOperationRepresentation = null;
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

    abstract void calculate ();
    // when we press some operation button
    abstract void selectOperation (String operationRepresentation);
    // when we have entered the operand and pass it to expression
    abstract void placeOperand (String operandRepresentation);
    // when we have to pass the operation to expression
    abstract void placeOperation (String operationRepresentation);
    // when we press some digit or dot
    abstract void addSymbolToCurrentOperator (String symbol);


    void resetCurrentOperand (){
        currentOperand.setOperandValue(0);
        currentOperandRepresentation = currentOperand.getOperandRepresentation();
        isCurrentOperandInitiated = false;
    }
}
