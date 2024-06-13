package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkedPulseId {
    @JsonProperty("linkedPulseId")
    private String linkedPulseId;
}
