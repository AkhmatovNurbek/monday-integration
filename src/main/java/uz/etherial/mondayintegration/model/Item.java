package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {
    private String id;
    private String name;

    @JsonProperty("column_values")
    private List<ColumnValue> columnValues;
}
