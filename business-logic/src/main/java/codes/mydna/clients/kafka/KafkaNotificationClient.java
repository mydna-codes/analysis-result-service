package codes.mydna.clients.kafka;

import codes.mydna.auth.common.models.User;
import codes.mydna.lib.Email;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaNotificationClient {

    public static final Logger LOG = Logger.getLogger(KafkaNotificationClient.class.getSimpleName());

    @Inject
    @StreamProducer
    private Producer<String, String> producer;

    public void notifyAboutFinishedAnalysis(User user, String analysisName, long executionTime){

        Email email = new Email();
        email.setTo(user.getEmail());
        email.setSubject("Analysis has finished");
        email.setContent("Analysis '" + analysisName + "' has finished in " + millisToPrettyOutput(executionTime));
        sendEmail(email);
    }

    private static String millisToPrettyOutput(long millis){

        return String.format("%01d hours %01d minutes %01d seconds",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void sendEmail(Email email){

        String jsonEmail;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonEmail = mapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            LOG.severe("Failed to serialize email object!");
            throw new InternalServerErrorException("Failed to serialize email object!");
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(
                "send_email",
                UUID.randomUUID().toString(),
                jsonEmail);

        producer.send(record, (metadata, e) -> { if (e != null) LOG.severe(e.getMessage()); });
    }

}
