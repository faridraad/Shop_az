package org.farid.model.mapper;

import org.farid.model.dto.UserDTO;
import org.farid.model.entity.Member;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO user(Member member);
}
