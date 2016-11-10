package imisno.com.calculator;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CalculatorActivity extends FragmentActivity
        implements KeyboardFragment.OnKeyClickedListener{
    TextView temp;
    public Calculator calculator;
    public void clickDigitButton (String digit) {
        calculator.addSymbolToCurrentOperator(digit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        temp = (TextView)findViewById(R.id.temp);

        Calculator calculator = new SimpleCalculator();
    }

    @Override
    public void onDigitKeyPressed(String digit) {
        if(temp.getText().toString() == getString(R.string.hello_blank_fragment)){
            temp.setText("");
        }
        temp.setText(temp.getText() + digit);
    }

    @Override
    public void onEqualKeyPressed() {

    }

    @Override
    public void onOperationKeyPressed(String operationRepresentation) {

    }
}
