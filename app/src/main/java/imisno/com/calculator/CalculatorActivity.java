package imisno.com.calculator;

import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CalculatorActivity extends FragmentActivity
        implements KeyboardFragment.OnKeyClickedListener,
        ProgrammerKeyboardFragment.OnToggleListener,
            ProgrammerKeyboardFragment.OnLogicKeyClickedListener{

    public Calculator calculator;
    public DisplayFragment displayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, R.string.ui_switchToProgrammingToast, Toast.LENGTH_LONG).show();
            calculator = new ProgrammerCalculator();
            displayFragment = (DisplayFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.simple_display_fragment);
        }
        else {
            Toast.makeText(this, R.string.ui_switchToSimpleToast, Toast.LENGTH_LONG).show();
            calculator = new SimpleCalculator();
            displayFragment = (DisplayFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.simple_display_fragment);
            updateView();
        }
    }


    @Override
    public void onDigitKeyPressed(String digit) {
        calculator.addSymbolToCurrentOperand(digit);
        updateView();
    }

    @Override
    public void onPeriodKeyPressed() {
        calculator.addSymbolToCurrentOperand(".");
        updateView();
    }

    @Override
    public void onOperationKeyPressed(String operationRepresentation) {
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
            calculator.setDoesExpressionResetRequired(true);
            calculator.setDoesOperandResetRequired(true);
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
        calculator.backspaceCurrentOperand();
        updateView();
    }

    @Override
    public void onClearKeyPressed() {
        calculator.reset();
        updateView();
    }

    @Override
    public void onClearCurrentKeyPressed() {
        calculator.resetCurrentOperand();
        updateView();
    }

    @Override
    public void onOpenBracketKeyPressed() {
        calculator.openBracket();
        updateView();
    }

    @Override
    public void onCloseBracketKeyPressed() {
        calculator.closeBracket();
        updateView();
    }

    @Override
    public void onAndKeyPressed() {

    }

    @Override
    public void onOrKeyPressed() {

    }

    @Override
    public void onNotKeyPressed() {

    }

    @Override
    public void onXorKeyPressed() {

    }

    @Override
    public void onBinModeEnabled() {
        calculator.setRadix(2);
        updateView();

    }

    @Override
    public void onOctModeEnabled() {
        calculator.setRadix(8);
        updateView();
    }

    @Override
    public void onDecModeEnabled() {
        calculator.setRadix(10);
        updateView();
    }

    @Override
    public void onHexModeEnabled() {
        calculator.setRadix(16);
        updateView();
    }

    private void updateView (){
        displayFragment.updateCurrentOperandTextView(calculator.getCurrentOperandRepresentation());
        displayFragment.updateExpressionTextView(calculator.getExpressionRepresentation());
    }
}
