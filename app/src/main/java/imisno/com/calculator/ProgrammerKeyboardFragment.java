package imisno.com.calculator;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import imisno.com.calculator.utils.ButtonSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgrammerKeyboardFragment extends KeyboardFragment {
    OnLogicKeyClickedListener logicCallback;
    OnToggleListener toggleCallback;

    public ProgrammerKeyboardFragment() {
        // Required empty public constructor
    }

    public interface OnLogicKeyClickedListener {
        void onAndKeyPressed ();
        void onOrKeyPressed ();
        void onNotKeyPressed ();
        void onXorKeyPressed ();
    }

    public interface OnToggleListener {
        void onBinModeEnabled ();
        void onOctModeEnabled ();
        void onDecModeEnabled ();
        void onHexModeEnabled ();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programmer_keyboard, container, false);

        initDigitKeysHandlers(view);
        initOperationsKeysHandlers(view);
        initServiceKeyHandlers(view);
        initLogicalKeysHandlers(view);
        initToggleHandlers(view);

        disableRadixDependentButtons(view);
        enableDecButtons(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            logicCallback = (OnLogicKeyClickedListener)activity;
            toggleCallback = (OnToggleListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLogicKeyClickedListener and OnToggleListener");
        }
    }

    protected void initLogicalKeysHandlers (View view) {
        return;
//        Button andBtn = (Button)view.findViewById(R.id.buttonAnd);
//        andBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logicCallback.onAndKeyPressed();
//            }
//        });
//
//        Button orBtn = (Button)view.findViewById(R.id.buttonOr);
//        orBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logicCallback.onOrKeyPressed();
//            }
//        });
//
//        Button notBtn = (Button)view.findViewById(R.id.buttonNot);
//        notBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logicCallback.onNotKeyPressed();
//            }
//        });
//
//        Button xorBtn = (Button)view.findViewById(R.id.buttonXor);
//        xorBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logicCallback.onXorKeyPressed();
//            }
//        });
    }

    protected void initToggleHandlers (final View view) {
        Button enableBinButton = (Button)view.findViewById(R.id.buttonBin);
        Button enableOctButton = (Button)view.findViewById(R.id.buttonOct);
        Button enableDecButton = (Button)view.findViewById(R.id.buttonDec);
        Button enableHexButton = (Button)view.findViewById(R.id.buttonHex);

        enableBinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRadixDependentButtons(view);
                enableBinButtons(view);
                toggleCallback.onBinModeEnabled();
            }
        });
        enableOctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRadixDependentButtons(view);
                enableOctButtons(view);
                toggleCallback.onOctModeEnabled();
            }
        });
        enableDecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRadixDependentButtons(view);
                enableDecButtons(view);
                toggleCallback.onDecModeEnabled();
            }
        });
        enableHexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRadixDependentButtons(view);
                enableHexButtons(view);
                toggleCallback.onHexModeEnabled();
            }
        });
    }

    private void disableRadixDependentButtons (View view) {
        for (int buttonId : ButtonSet.HEX_DIGIT_BUTTONS) {
            view.findViewById(buttonId).setEnabled(false);
        }
        for (int buttonId : ButtonSet.LOGICAL_BUTTONS) {
            view.findViewById(buttonId).setEnabled(false);
        }
    }

    private void enableBinButtons (View view) {
        for (int buttonId : ButtonSet.BIN_DIGIT_BUTTONS) {
            view.findViewById(buttonId).setEnabled(true);
        }
        for (int buttonId : ButtonSet.LOGICAL_BUTTONS) {
            view.findViewById(buttonId).setEnabled(true);
        }
    }

    private void enableOctButtons (View view) {
        for (int buttonId : ButtonSet.OCT_DIGIT_BUTTONS) {
            view.findViewById(buttonId).setEnabled(true);
        }
    }

    private void enableDecButtons (View view) {
        for (int buttonId : ButtonSet.DEC_DIGIT_BUTTONS) {
            view.findViewById(buttonId).setEnabled(true);
        }
    }

    private void enableHexButtons (View view) {
        for (int buttonId : ButtonSet.HEX_DIGIT_BUTTONS) {
            view.findViewById(buttonId).setEnabled(true);
        }
    }
}
