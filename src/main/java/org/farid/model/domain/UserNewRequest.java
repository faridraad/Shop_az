package org.farid.model.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserNewRequest {

    @NotBlank
    @Email
    @Size(max = 250)
    String username;

    @NotBlank
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^(09[0-9]*)$")
    @ApiModelProperty(name = "mobileNumber", value = "09127374074", required = true)
    String mobileNumber;

    @NotBlank
    @Size(max =1500)
    String address;

    Integer gender = 0;
}
