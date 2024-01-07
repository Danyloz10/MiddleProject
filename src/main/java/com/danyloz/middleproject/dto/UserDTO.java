package com.danyloz.middleproject.dto;

import com.danyloz.middleproject.entity.User;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String jwtToken;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.jwtToken = user.getJwtToken();
    }
}
