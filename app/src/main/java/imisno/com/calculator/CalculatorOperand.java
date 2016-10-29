package imisno.com.calculator;

import android.util.Log;

/**
 * Created by kav on 21.10.2016.
 */

public class CalculatorOperand {
    private double operandValue;
    private String operandRepresentation;
    private int currentRadix = 10;

    public CalculatorOperand(double operandValue, int currentRadix) {
        this.currentRadix = currentRadix;
        this.operandValue = operandValue;
        updateRepresentation();
    }

    public CalculatorOperand(double operandValue) {
        this(operandValue, 10);
    }

    public String getOperandRepresentation() {
        return operandRepresentation;
    }

    public double getOperandValue() {
        return operandValue;

    }

    public void setCurrentRadix(int currentRadix) {
        this.currentRadix = currentRadix;
        updateRepresentation();
    }

    public void setOperandValue(double operandValue) {
        this.operandValue = operandValue;
        updateRepresentation();
    }

    private void updateRepresentation () {
        this.operandRepresentation = Converter.getStringFromValue(this.operandValue, this.currentRadix);
//        Log.d("calcOperand", String.format("current representation : %1$", this.operandRepresentation));
    }
}
