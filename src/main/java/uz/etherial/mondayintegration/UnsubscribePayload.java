package uz.etherial.mondayintegration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UnsubscribePayload {
    private Payload payload;

    @Getter
    @Setter
    public class Payload {
        private String webhookId;
    }
}
