package uz.etherial.mondayintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Value {
    @JsonProperty("index")
    private Integer index;

    @JsonProperty("post_id")
    private String postId = null;

    @JsonProperty("changed_at")
    private String changedAt;

    @JsonProperty("linkedPulseIds")
    private List<LinkedPulseId> linkedPulseIds;
}
