package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.SaveMessageRequest;

public interface MessageService {
    void saveUserMessage(SaveMessageRequest request);
}
