package com.example.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String content;

    private String mediaUrl;

    @NotEmpty
    private Set<Long> categoryIds;
}
