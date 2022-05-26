package org.farid.intrface;


import org.farid.model.domain.UserNewRequest;
import org.farid.model.dto.BaseDTO;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public interface IUser {

    BaseDTO userList ();
    BaseDTO newUser (UserNewRequest userNewRequest) throws Exception;

}
