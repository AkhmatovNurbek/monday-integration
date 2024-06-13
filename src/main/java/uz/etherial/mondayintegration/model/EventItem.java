package uz.etherial.mondayintegration.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EventItem {

    private UpdateEvent event;

    @Getter
    @Setter
    public class UpdateEvent {
        private Long boardId;
        private String pulseId;
    }
}
