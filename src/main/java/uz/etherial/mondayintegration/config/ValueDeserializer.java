package uz.etherial.mondayintegration.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.etherial.mondayintegration.model.Value;

import java.io.IOException;

public class ValueDeserializer extends JsonDeserializer<Value> {

    @Override
    public Value deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = p.readValueAsTree();

        if (node.isTextual()) {
            String jsonString = node.asText();
            return mapper.readValue(jsonString, Value.class);
        }
        return null;
    }
}
