package com.example.zhaowang.controller;

import com.example.zhaowang.common.ApiResponse;
import com.example.zhaowang.common.UserResponse;
import com.example.zhaowang.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/sendMsg")
    public ResponseEntity<?> sendMsg(@RequestParam("id") int userId) {
        boolean success = messageService.sendSMS(userId);
        return ResponseEntity.ok().body(new ApiResponse(success));
    }

    @GetMapping("/record")
    public ResponseEntity<?> record(@RequestParam("id") int userId) {
        int count = messageService.getSMSCount(userId);
        return ResponseEntity.ok().body(new UserResponse(userId, count));
    }
}
