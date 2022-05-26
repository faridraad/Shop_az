package org.farid.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderNewRequest {

    @NotBlank
    String title;

    @NotBlank
    String address;

    @NotBlank
    String phoneNumber;
}
