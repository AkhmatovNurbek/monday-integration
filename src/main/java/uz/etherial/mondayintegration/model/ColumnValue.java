package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import uz.etherial.mondayintegration.config.ValueDeserializer;

@Getter
@Setter
public class ColumnValue {
    private String id;
    private String text;
    @JsonProperty("value")
    @JsonDeserialize(using = ValueDeserializer.class)
    private Value value;
}
