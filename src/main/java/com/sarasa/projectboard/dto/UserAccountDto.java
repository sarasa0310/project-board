package com.sarasa.projectboard.dto;

import com.sarasa.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String username,
        String password,
        String email,
        String nickname,
        String memo,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(Long id, String username, String password, String email, String nickname, String memo, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, username, password, email, nickname, memo, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }

}
