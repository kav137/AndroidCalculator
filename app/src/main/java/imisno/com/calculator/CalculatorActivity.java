package imisno.com.calculator;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends FragmentActivity
        implements KeyboardFragment.OnKeyClickedListener{
    TextView temp;//TODO remove later

    public Calculator calculator;
    public DisplayFragment displayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        temp = (TextView)findViewById(R.id.temp); //TODO remove later

        calculator = new SimpleCalculator();
        displayFragment = (DisplayFragment)getSupportFragmentManager()
                .findFragmentById(R.id.simple_display_fragment);
        updateView();
    }

    @Override
    public void onDigitKeyPressed(String digit) {
        logger(digit);//TODO remove later

        calculator.addSymbolToCurrentOperand(digit);
        displayFragment.updateCurrentOperandTextView(calculator.getCurrentOperandRepresentation());
    }

    @Override
    public void onPeriodKeyPressed() {
        logger(".");

        calculator.addSymbolToCurrentOperand(".");
        updateView();
    }

    @Override
    public void onOperationKeyPressed(String operationRepresentation) {
        logger(operationRepresentation);

        calculator.placeOperation(operationRepresentation);
        updateView();
    }

    @Override
    public void onEqualKeyPressed() {
        try {
            calculator.calculate();
        }
        catch (NumberFormatException nfe) {
            Toast.makeText(this, R.string.ui_illegalResult, Toast.LENGTH_LONG).show();
            calculator.reset();
        }
        catch (ValueIsTooLargeException e) {
            Toast.makeText(this, R.string.ui_tooLargeResult, Toast.LENGTH_LONG).show();
            calculator.setDoesResetRequired(true);
//            calculator.reset();
        }
        updateView();
    }

    @Override
    public void onNegateKeyPressed() {
        calculator.negateCurrentOperand();
        updateView();
    }

    @Override
    public void onBackspaceKeyPressed() {
        logger(" bksp ");

        calculator.backspaceCurrentOperand();
        updateView();
    }

    @Override
    public void onClearKeyPressed() {
        calculator.reset();
        updateView();
    }

    private void logger (String symbol) {
        if(temp.getText().toString() == getString(R.string.logger)){
            temp.setText("");
        }
        String newValue = temp.getText() + symbol;
        temp.setText(newValue);
    }

    private void updateView (){
        displayFragment.updateCurrentOperandTextView(calculator.getCurrentOperandRepresentation());
        displayFragment.updateExpressionTextView(calculator.getExpressionRepresentation());
    }
}
