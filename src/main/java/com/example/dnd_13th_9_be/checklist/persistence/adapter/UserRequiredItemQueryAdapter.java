package com.example.dnd_13th_9_be.checklist.persistence.adapter;

import com.example.dnd_13th_9_be.checklist.application.port.UserRequiredItemQueryPort;

public class UserRequiredItemQueryAdapter implements UserRequiredItemQueryPort {

    @Override
    public boolean existsByUserIdAndItemId(Long userId, Long itemId) {
        return false;
    }
}
