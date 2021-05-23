package food.backend.output;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Scanner;

/**
 * Online implementation of Twilio interface that connects to the Twilio API.
 */
public class TwilioOnline implements Twilio {

    /**
     * The HttpClient that connects to the API.
     */
    private CloseableHttpClient client;

    /**
     * The source phone number (Twilio).
     */
    private String phoneFrom;

    /**
     * The target phone number (yours).
     */
    private String phoneTo;

    /**
     * The Twilio account SID.
     */
    private String twilioSID;

    /**
     * The Twilio account token.
     */
    private String twilioKey;

    /**
     * Creates a new TwilioOnline object.
     * @param twilioSID The Twilio account SID.
     * @param twilioKey The Twilio account token.
     * @param phoneFrom The Twilio phone number.
     * @param phoneTo Your own phone number.
     */
    public TwilioOnline(String twilioSID, String twilioKey, String phoneFrom, String phoneTo) {
        this.twilioSID = twilioSID;
        this.twilioKey = twilioKey;
        this.phoneFrom = phoneFrom;
        this.phoneTo = phoneTo;

        this.client = HttpClients.createDefault();
    }

    @Override
    public boolean sendMessage(String message) {
        try {
            String url = "https://api.twilio.com/2010-04-01/Accounts/" + this.twilioSID + "/Messages";
            HttpPost poster = new HttpPost(url);
            String encoding = Base64.getEncoder().encodeToString((this.twilioSID + ":" + this.twilioKey).getBytes());
            poster.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

            String body = "To=" + this.phoneTo + "&From=" + this.phoneFrom + "&Body=" + message;
            StringEntity entity = new StringEntity(body, ContentType.APPLICATION_FORM_URLENCODED);

            poster.setEntity(entity);

            InputStream stream = client.execute(poster).getEntity().getContent();

            Scanner sc = new Scanner(stream);

            String output = "";

            while (sc.hasNextLine()) {
                output += sc.nextLine();
            }

            if (output.contains("Exception")) {
                return false;
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
