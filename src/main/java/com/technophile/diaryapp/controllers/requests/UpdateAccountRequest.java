package com.technophile.diaryapp.controllers.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class UpdateAccountRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
}
