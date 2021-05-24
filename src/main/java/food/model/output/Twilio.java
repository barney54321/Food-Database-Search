package food.model.output;

/**
 * Interface outlining the behaviour of a Twilio object.
 */
public interface Twilio {

    /**
     * Sends a message via SMS using the provided credentials.
     * @param message The message to send.
     * @return Whether the message is successfully sent.
     */
    boolean sendMessage(String message);
}
