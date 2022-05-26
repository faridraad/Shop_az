package org.farid.service;

import org.farid.configuration.exception.ServiceException;
import org.farid.configuration.exception.ServiceExceptionHandler;
import org.farid.model.dto.UserDTO;
import org.farid.model.mapper.UserMapper;
import org.farid.utility.CommonUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.farid.configuration.resources.ApplicationProperties;
import org.farid.intrface.IUser;
import org.farid.model.domain.UserNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.entity.Member;
import org.farid.repository.MemberRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUser {

    final ApplicationProperties applicationProperties;
    final MemberRepository memberRepository;
    final UserMapper userMapper;

    protected UserService(ApplicationProperties applicationProperties,
                          MemberRepository memberRepository,
                          UserMapper userMapper) {
        this.applicationProperties = applicationProperties;
        this.memberRepository = memberRepository;
        this.userMapper = userMapper;
    }

    @Override
    public BaseDTO userList() {
        List<UserDTO> userList = new ArrayList<>();
        memberRepository.findAll().forEach(i->userList.add(userMapper.user(i)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userList)
                .build();
    }


    public String encryptPassword(String password) throws Exception {
        DESKeySpec key = new DESKeySpec(applicationProperties.getProperty("password.secret.key").getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

        return CommonUtility.encryptBase64(password, keyFactory.generateSecret(key));
    }
    @Override
    public BaseDTO newUser(UserNewRequest userNewRequest) throws Exception {
        // check duplicate user
        Optional<Member> memberOptional = memberRepository.findByUsername(userNewRequest.getUsername());
        if (memberOptional.isPresent()) {
            throw new ServiceException(
                    applicationProperties.getCode("application.message.user.email.exist.code"),
                    applicationProperties.getProperty("application.message.user.email.exist.text"), HttpStatus.NOT_FOUND);
        }else{

            Member member = new Member();
            member.setUsername(userNewRequest.getUsername());
            member.setPassword(this.encryptPassword(userNewRequest.getPassword()));
            member.setFirstName(userNewRequest.getFirstName());
            member.setLastName(userNewRequest.getLastName());
            member.setGender(userNewRequest.getGender());
            member.setMobileNumber(userNewRequest.getMobileNumber());
            member.setAddress(userNewRequest.getAddress());
            return BaseDTO.builder()
                    .meta(MetaDTO.getInstance(applicationProperties))
                    .data(memberRepository.save(member))
                    .build();
        }

    }
}
