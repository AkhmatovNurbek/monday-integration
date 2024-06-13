package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MondayBoard {
    @JsonProperty("data")
    private MondayData data;

    @JsonProperty("account_id")
    private String accountId;
}
