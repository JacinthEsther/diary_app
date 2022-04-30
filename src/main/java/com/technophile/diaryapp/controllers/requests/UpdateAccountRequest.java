package com.technophile.diaryapp.controllers.requests;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateAccountRequest {

    private String email;
    private String password;
}
