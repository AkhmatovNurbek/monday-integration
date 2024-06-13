package uz.etherial.mondayintegration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MondayController {

    private final MondayService mondayService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> handleSubscription(@RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> payloadMap = (Map<String, Object>) payload.get("payload");

            if (payloadMap == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payload is missing");
            }

            System.out.println(payload);

            return ResponseEntity.ok("Subscription handled successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling subscription");
        }
    }

//    @PostMapping("/unsubscribe")
//    public String unsubscribeFromTrigger(@RequestBody UnsubscribePayload payload) {
//        try {
//            return mondayService.unsubscribeFromTrigger(payload.getPayload().getWebhookId());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error unsubscribing from trigger: " + e.getMessage();
//        }
//    }

//    @GetMapping("/tasks/dependencies")
//    public List<Integer> getDependentTaskIds(@RequestParam int boardId, @RequestParam int itemId) {
//        try {
//            return mondayService.getDependentTaskIds(boardId, itemId);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @PostMapping("/tasks/updateStatus")
//    public String updateTaskStatus(
//            @RequestParam int boardId,
//            @RequestParam int itemId,
//            @RequestParam String newStatus
//    ) {
//        try {
//            return mondayService.updateTaskStatus(boardId, itemId, newStatus);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error updating task status: " + e.getMessage();
//        }
//    }

    @SneakyThrows
    @PostMapping(value = "/")
    public Object get(@RequestBody Map<String, Object> challenge) {
        if (challenge.containsKey("challenge")) {
            return challenge;
        }
        System.out.println(challenge);
        mondayService.updatedItem(challenge);
        return challenge;
    }
}
