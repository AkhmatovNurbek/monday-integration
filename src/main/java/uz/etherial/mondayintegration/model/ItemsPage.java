package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemsPage {

    @JsonProperty("items")
    private List<Item> items;
}
