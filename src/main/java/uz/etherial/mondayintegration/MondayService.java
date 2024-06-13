package uz.etherial.mondayintegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.etherial.mondayintegration.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MondayService {
    private static final String COLUMN_ID = "status8__1";
    private static final String NEW_VALUE = "To do";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private MondayBoard mondayBoard;

    private List<Item> allItems = new ArrayList<>();

    private Object executeQuery(String query) throws IOException {
        JSONObject json = new JSONObject();
        json.put("query", query);
        return webClient
                .post()
                .bodyValue(json.toString())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public void getAllItemsInBoard(Long boardId) throws IOException {
        String query = String.format("query {\n" +
                "  boards(ids: 6714877704){\n" +
                "    items_page(limit: 500){\n" +
                "      items{\n" +
                "        id\n" +
                "        name\n" +
                "        column_values(ids: [\"status8__1\",\"dependency__1\"]){\n" +
                "          id\n" +
                "          value\n" +
                "          text\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        Object response = executeQuery(query);
        parseBoard(response);
    }

    @SneakyThrows
    public Object updatedItem(Map<String, Object> challenge) {
        EventItem eventItem = objectMapper.convertValue(challenge, EventItem.class);
        getAllItemsInBoard(eventItem.getEvent().getBoardId());
        allItems = mondayBoard.getData().getBoards().get(0).getItemsPage().getItems();
        for (int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            for (int j = 0; j < item.getColumnValues().size(); j++) {
                if (item.getColumnValues().get(j).getValue() != null) {
                    List<LinkedPulseId> linkedPulseIds = item.getColumnValues().get(j).getValue().getLinkedPulseIds();
                    if (linkedPulseIds != null) {
                        for (int k = 0; k < linkedPulseIds.size(); k++) {
                            if (linkedPulseIds.get(k).getLinkedPulseId().equals(eventItem.getEvent().getPulseId())) {
                                checkDependentItems(item);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    @SneakyThrows
    public Object getItemWithStatus(String pulseId) {
        String query = String.format("query {\n" +
                "      items(ids: %s){\n" +
                "        id\n" +
                "        name\n" +
                "        column_values(ids: [\"status8__1\"]){\n" +
                "          id\n" +
                "          value\n" +
                "          text\n" +
                "        }\n" +
                "      }\n" +
                "}", pulseId);
        Object o = executeQuery(query);
        return parseItem(o);
    }

    public void checkDependentItems(Item item) {
        int count = 0;
        List<LinkedPulseId> linkedPulseIds = item.getColumnValues().get(1).getValue().getLinkedPulseIds();
        for (int k = 0; k < linkedPulseIds.size(); k++) {
            for (int l = 0; l < allItems.size(); l++) {
                if (linkedPulseIds.get(k).getLinkedPulseId().equals(allItems.get(l).getId()) && allItems.get(l).getColumnValues().get(0).getText().equals("Done")) {
                    count++;
                    if (count == linkedPulseIds.size()) {
                        updateItemColumn(item.getId());
                        log.info("Item updated with id: {}",item);
                    }
                }
            }
        }
        count = 0;
    }


    private void parseBoard(Object response) {
        this.mondayBoard = objectMapper.convertValue(response, MondayBoard.class);
        System.out.println(JSONObject.wrap(mondayBoard));
    }

    private Object parseItem(Object item) {
        ItemsPage itemsPage = objectMapper.convertValue(item, ItemsPage.class);
        return itemsPage;
    }


    @SneakyThrows
    public void updateItemColumn(String itemId) {
        String query = String.format("mutation {\n" +
                "  change_column_value(\n" +
                "    board_id: 6714877704,\n" +
                "    item_id: %s,\n" +
                "    column_id: \"%s\",\n" +
                "    value: \"{\\\"label\\\": \\\"%s\\\"}\"\n" +
                "  ) {\n" +
                "    id\n" +
                "  }\n" +
                "}", itemId, COLUMN_ID, NEW_VALUE);
        executeQuery(query);
    }
}
