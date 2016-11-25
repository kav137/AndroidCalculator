package imisno.com.calculator;

import imisno.com.calculator.SimpleCalculator;

/**
 * Created by kav on 23.11.2016.
 */

class ProgrammerCalculator extends SimpleCalculator {
    private String decExpression;
    private int radix;

    public void setRadix (int newRadix) {
        radix = newRadix;
        currentOperand.setRadix(newRadix);
        updateCurrentOperandRepresentation();
    }

//    @Override
//    void placeOperand(String operandRepresentation) {
//        setRadix(10);
//        super.placeOperand(operandRepresentation);
//        setRadix(radix);
//    }


    @Override
    void placeOperation(String operationRepresentation) {
        int shadowRadix = getShadowRadix();
        super.placeOperation(operationRepresentation);
        restoreShadowRadix(shadowRadix);
    }

    @Override
    void calculate() throws ValueIsTooLargeException {
        int shadowRadix = getShadowRadix();
        super.calculate();
        restoreShadowRadix(shadowRadix);
    }

    @Override
    void closeBracket() {
        int shadowRadix = getShadowRadix();
        super.closeBracket();
        restoreShadowRadix(shadowRadix);
    }

    private int getShadowRadix () {
        int currentRadix = radix;
        setRadix(10);
        updateCurrentOperandRepresentation();
        return currentRadix;
    }

    private void restoreShadowRadix (int shadowRadix) {
        setRadix(shadowRadix);
        updateCurrentOperandRepresentation();
    }

    public ProgrammerCalculator() {
        super();
        setRadix(10);
    }

}
