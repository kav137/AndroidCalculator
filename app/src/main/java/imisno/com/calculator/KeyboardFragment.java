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
        void onEqualKeyPressed ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);

        //set listeners for buttons
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

}
