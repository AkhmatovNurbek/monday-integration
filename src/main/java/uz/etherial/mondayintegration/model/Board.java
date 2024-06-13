package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    @JsonProperty("items_page")
    private ItemsPage itemsPage;
}
