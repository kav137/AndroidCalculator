package imisno.com.calculator;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class KeyboardFragment extends Fragment {
    final String TAG = "Keyboard fragment";
    OnKeyClickedListener mCallback;

    public KeyboardFragment() {
        // Required empty public constructor
    }

    public interface OnKeyClickedListener {
        void onDigitKeyPressed (String digit);
        void onOperationKeyPressed (String operationRepresentation);
        void onBackspaceKeyPressed ();
        void onClearKeyPressed ();
        void onClearCurrentKeyPressed ();
        void onEqualKeyPressed ();
        void onNegateKeyPressed ();
        void onPeriodKeyPressed ();
        void onOpenBracketKeyPressed ();
        void onCloseBracketKeyPressed ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);

        initDigitKeysHandlers(view);
        initOperationsKeysHandlers(view);
        initServiceKeyHandlers(view);
        //set listeners for operation buttons
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnKeyClickedListener)activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnKeyClickedListener");
        }
    }

    private void initDigitKeysHandlers (View view) {
        ArrayList<View> digitButtons = new ArrayList<>();
        view.findViewsWithText(digitButtons, "digit", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        for (View digitButtonView : digitButtons) {
            Button digitButton = (Button)digitButtonView;
            digitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button thisBtn = (Button)v;
                    mCallback.onDigitKeyPressed(thisBtn.getText().toString());
                }
            });
        }
    }

    private void initOperationsKeysHandlers (View view){
        ArrayList<View> operationButtons = new ArrayList<>();
        view.findViewsWithText(operationButtons, "operation", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        for (View operationButtonView : operationButtons) {
            Button operationButton = (Button)operationButtonView;
            operationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button thisBtn = (Button)v;
                    mCallback.onOperationKeyPressed(thisBtn.getText().toString());
                }
            });
        }
    }

    private void initServiceKeyHandlers (View view) {
        //equal button
        Button equalBtn = (Button)view.findViewById(R.id.buttonEqual);
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEqualKeyPressed();
            }
        });

        //period button
        Button periodBtn = (Button)view.findViewById(R.id.buttonPeriod);
        periodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onPeriodKeyPressed();
            }
        });

        //backspace button
        Button backspaceBtn = (Button)view.findViewById(R.id.buttonBackspace);
        backspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onBackspaceKeyPressed();
            }
        });

        //clear button
        Button clearBtn = (Button)view.findViewById(R.id.buttonClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClearKeyPressed();
            }
        });

        //clear current (CE) button
        Button ceButton = (Button)view.findViewById(R.id.buttonClearCurrent);
        ceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClearCurrentKeyPressed();
            }
        });

        //negate button
        Button negateBtn = (Button)view.findViewById(R.id.buttonNegate);
        negateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNegateKeyPressed();
            }
        });

        //opening bracket
        Button openBracketButton = (Button)view.findViewById(R.id.buttonOpenBracket);
        openBracketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onOpenBracketKeyPressed();
            }
        });

        // closing bracket
        Button closeBracketButton = (Button)view.findViewById(R.id.buttonCloseBracket);
        closeBracketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCloseBracketKeyPressed();
            }
        });
    }
}
