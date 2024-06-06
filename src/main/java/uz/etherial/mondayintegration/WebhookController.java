package uz.etherial.mondayintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @Autowired
    private MondayService mondayService;

    @PostMapping
    public void handleWebhook(@RequestBody WebhookPayload payload) throws IOException {
        if ("Done".equals(payload.getEvent().getColumnValue().getLabel())) {
            int boardId = payload.getBoardId();
            int itemId = payload.getItemId();
            List<Integer> dependentTaskIds = mondayService.getDependentTaskIds(boardId, itemId);

            for (int dependentTaskId : dependentTaskIds) {
                mondayService.updateTaskStatus(boardId, dependentTaskId, "To do");
            }
        }
    }
}
