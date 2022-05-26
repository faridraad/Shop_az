package org.farid.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderDTO  {
    Integer id;
    String title;
    String address;
    String phoneNumber;

}
