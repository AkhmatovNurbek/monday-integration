package uz.etherial.mondayintegration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MondayController {

    private final MondayService mondayService;

    @PutMapping("/tasks/{itemId}/status")
    public String updateDependentTaskStatuses(@PathVariable int itemId, @RequestParam String status) throws IOException {
        int boardId = 123456; // Your board ID here
        String response = mondayService.updateTaskStatus(boardId, itemId, status);

        // Get dependent tasks and update their statuses if the task is marked as done
        if ("Done".equals(status)) {
            String dependentTasksResponse = mondayService.getDependentTaskIds(boardId, itemId).toString();
            // Parse dependentTasksResponse to get the list of dependent task IDs
            // For simplicity, we assume you have a method to extract these IDs
            List<Integer> dependentTaskIds = parseDependentTaskIds(dependentTasksResponse);

            for (int dependentTaskId : dependentTaskIds) {
                mondayService.updateTaskStatus(boardId, dependentTaskId, "To do");
            }
        }

        return response;
    }

    // Method to parse dependent task IDs from the response
    private List<Integer> parseDependentTaskIds(String response) {
        // Implement your parsing logic here
        // Return a list of dependent task IDs
        return new ArrayList<>();
    }


    @PostMapping("/message")
    public String getMessage(@RequestBody String something) {
        System.out.println(something);
        return something;
    }
}
