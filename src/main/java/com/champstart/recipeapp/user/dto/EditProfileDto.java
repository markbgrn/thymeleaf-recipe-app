package com.champstart.recipeapp.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class EditProfileDto {
    private Long id;
    @NotBlank(message = "This field should not be blank")
    private String firstName;
    @NotBlank(message = "This field should not be blank")
    private String lastName;
    private MultipartFile photo;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
