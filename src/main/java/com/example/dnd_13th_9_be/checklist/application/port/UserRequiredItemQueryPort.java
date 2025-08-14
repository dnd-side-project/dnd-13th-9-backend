package com.example.dnd_13th_9_be.checklist.application.port;

public interface UserRequiredItemQueryPort {
    boolean existsByUserIdAndItemId(Long userId, Long  itemId);

}
