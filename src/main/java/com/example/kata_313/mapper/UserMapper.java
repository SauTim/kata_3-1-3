package com.example.kata_313.mapper;

import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface UserMapper {
    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    UserDto toUserDto(User entity);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    User toUserEntity(UserDto dto);
}
