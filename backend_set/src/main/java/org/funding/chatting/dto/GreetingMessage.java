package org.funding.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingMessage {
    private String name;
    private String message;

    public GreetingMessage(String s) {
        this.message = s;
    }
}