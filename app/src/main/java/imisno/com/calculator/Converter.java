package imisno.com.calculator;

import android.util.Log;

/**
 * Created by kav on 21.10.2016.
 */

public class Converter {
    private static final String TAG = "Converter";

    public static double getValueFromString (String str, int radix) {
        double retValue;
        try {
            retValue = Integer.valueOf(str, radix);
        }
        catch (Exception e) {
            retValue = 0;
            Log.e(TAG, String.format("getValueFromString: impossible to get value from %1$s with radix %2$s", str, radix ));
        }
        return retValue;
    }


    public static String getStringFromValue (double value, int radix) {
        String retStr = "";
        switch (radix) {
            case 2 : {
                retStr = Integer.toBinaryString((int)value);
                break;
            }
            case 8 : {
                retStr = Integer.toOctalString((int)value);
                break;
            }
            case 10 : {
                retStr = Double.toString(value);
                break;
            }
            case 16 : {
                retStr = Integer.toHexString((int)value);
                break;
            }
            default: {
                Log.e(TAG, String.format("getStringFromValue: wrong radix %1$s", radix ));
            }
        }
        return retStr;
    }
}
