package uz.etherial.mondayintegration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookPayload {
    private Event event;
    private int boardId;
    private int itemId;

    @Getter
    @Setter
    public static class Event {
        private ColumnValue columnValue;

        @Getter
        @Setter
        public static class ColumnValue {
            private String label;
        }
    }
}


