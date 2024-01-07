package com.danyloz.middleproject.dto;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
