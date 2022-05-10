package com.technophile.diaryapp.controllers.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class UpdateUserDTO {
    private String email;
    private String password;
}
