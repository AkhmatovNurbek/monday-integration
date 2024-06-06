package uz.etherial.mondayintegration;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MondayService {

    @Value("${monday.api.token}")
    private String apiToken;

    @Value("${monday.api.url}")
    private String apiUrl;

    private final OkHttpClient client = new OkHttpClient();

    public List<Integer> getDependentTaskIds(int boardId, int itemId) throws IOException {
        String query = String.format("{ boards (ids: %d) { items (ids: %d) { column_values(ids: \"dependency\") { text } } } }", boardId, itemId);
        String response = executeQuery(query);
        return parseDependentTaskIds(response);
    }

    public String updateTaskStatus(int boardId, int itemId, String newStatus) throws IOException {
        String mutation = String.format("mutation { change_column_value(board_id: %d, item_id: %d, column_id: \"status\", value: \"{\\\"label\\\":\\\"%s\\\"}\") { id } }", boardId, itemId, newStatus);
        return executeQuery(mutation);
    }

    private String executeQuery(String query) throws IOException {
        JSONObject json = new JSONObject();
        json.put("query", query);

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", apiToken)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    private List<Integer> parseDependentTaskIds(String response) {
        List<Integer> taskIds = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse.getJSONObject("data").getJSONArray("boards")
                .getJSONObject(0).getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONArray dependencies = item.getJSONArray("column_values")
                    .getJSONObject(0).getJSONArray("linkedPulseIds");

            for (int j = 0; j < dependencies.length(); j++) {
                taskIds.add(dependencies.getJSONObject(j).getInt("linkedPulseId"));
            }
        }
        return taskIds;
    }
}
