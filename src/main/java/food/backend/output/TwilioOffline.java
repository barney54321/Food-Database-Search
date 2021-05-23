package food.backend.output;

/**
 * Offline implementation of the Twilio interface.
 * Simply prints messages to the terminal instead of sending them as SMS.
 */
public class TwilioOffline implements Twilio {
    @Override
    public boolean sendMessage(String message) {
        System.out.println(message);
        return true;
    }
}
