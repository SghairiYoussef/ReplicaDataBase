package com.sysrep.clientreader;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/read")
@AllArgsConstructor
public class ReaderController {

    private final MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> read(@RequestParam String key) {
        for (int i = 0; i < 3; i++) {
            String queueName = "read_queue_" + (i + 1);
            String response = messageProducer.sendAndReceive(queueName, key);
            System.out.println("testing queue: " + queueName);
            if (response != null) {
                System.out.println("response: from queue " + queueName + " is " + response);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/v2")
    public ResponseEntity<String> readV2(@RequestParam String key) {
        String response = messageProducer.sendAndReceive("read_queue_1", key);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
