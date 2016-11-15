package imisno.com.calculator;

/**
 * Created by kav on 16.11.2016.
 */

class ValueIsTooLargeException extends Exception {
    public ValueIsTooLargeException() {
    }

    public ValueIsTooLargeException(String detailMessage) {
        super(detailMessage);
    }
}
