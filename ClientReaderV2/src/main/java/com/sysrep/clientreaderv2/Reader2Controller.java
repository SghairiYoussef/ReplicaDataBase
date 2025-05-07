package com.sysrep.clientreaderv2;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/read/v2")
@RequiredArgsConstructor
public class Reader2Controller {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<List<String>> readMajorityLines() {
        int replicaCount = 3;
        Map<String, Integer> lineFrequency = new HashMap<>();

        for (int i = 1; i <= replicaCount; i++) {
            // Send 'READ_ALL' to each replica
            rabbitTemplate.convertAndSend("read_queue_" + i, "READ_ALL");
        }

        for (int i = 1; i <= replicaCount; i++) {
            for (int j = 0; j < 100; j++) {
                Message msg = rabbitTemplate.receive("read_queue_" + i, 500);
                if (msg == null) break;

                String line = new String(msg.getBody(), StandardCharsets.UTF_8);
                if (line.startsWith("ERROR")) continue;
                lineFrequency.put(line, lineFrequency.getOrDefault(line, 0) + 1);
            }
        }

        List<String> majorityLines = lineFrequency.entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        return ResponseEntity.ok(majorityLines);
    }
}