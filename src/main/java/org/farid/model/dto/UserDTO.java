package org.farid.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    Integer id;
    String username;
    String firstName;
    String lastName;
    String mobileNumber;

}
