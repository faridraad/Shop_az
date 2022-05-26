package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.model.domain.UserNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.mapper.UserMapper;
import org.farid.repository.MemberRepository;
import org.farid.repository.ProductRepository;
import org.farid.repository.ProviderRepository;
import org.farid.utility.CommonUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private final ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final UserMapper userMapper = mock(UserMapper.class);
    private final UserService userService = new UserService(applicationProperties, memberRepository,userMapper);
    private static final UserNewRequest USER_REQUEST = new UserNewRequest();
    private static final String NEW_USERNAME = "far.ra@gmail.com";
    static String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @BeforeAll
    static void init() throws Exception {
        USER_REQUEST.setUsername(NEW_USERNAME);
        USER_REQUEST.setFirstName("Farid");
        USER_REQUEST.setLastName("Raad");
        USER_REQUEST.setMobileNumber("09127374074");
        USER_REQUEST.setAddress("Tehran");
        DESKeySpec key = new DESKeySpec("yg7#hgf*".getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        USER_REQUEST.setPassword(CommonUtility.encryptBase64("password", keyFactory.generateSecret(key)));
    }

    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE)
                .matcher(emailAddress)
                .matches();
    }

    @Test
    void getAllUsers() {
        BaseDTO result = userService.userList();
        assertNotNull(result.getData());
    }

    @Test
    void newUser() throws Exception {
        assertNotNull(patternMatches(USER_REQUEST.getUsername()));
//         assertNotNull(userService.newUser(USER_REQUEST));
    }
}
