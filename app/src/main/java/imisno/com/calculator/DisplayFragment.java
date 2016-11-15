package imisno.com.calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    private TextView expressionTextView;
    private TextView currentOperandTextView;

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        expressionTextView = (TextView)view.findViewById(R.id.expressionRepresentation);
        currentOperandTextView = (TextView)view.findViewById(R.id.currentOperand);

        return view;
    }

    public void updateCurrentOperandTextView (String newValue) {
        currentOperandTextView.setText(newValue);
    }

    public void updateExpressionTextView (String newValue) {
        expressionTextView.setText(newValue);
    }
}
