package org.farid.service.intrface;

import org.farid.model.domain.ProviderNewRequest;
import org.farid.model.dto.BaseDTO;

public interface IProvider {

    BaseDTO providerList ();
    BaseDTO newProvider(ProviderNewRequest providerNewRequest);
    BaseDTO getProviderById(Integer id);
}
