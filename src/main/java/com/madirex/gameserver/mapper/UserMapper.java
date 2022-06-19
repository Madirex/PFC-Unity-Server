package com.madirex.gameserver.mapper;

import com.madirex.gameserver.dto.user.CreateUserDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    /**
     * Convertir DAO a DTO
     * @param user DAO
     * @return DTO
     */
    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Convertir DTO a DAO
     * @param userDTO DTO
     * @return DAO
     */
    public User fromDTO(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Convertir DTO de CreateUserDTO a DAO
     * @param userDTO CreateUserDTO
     * @return DAO
     */
    public User fromDTOCreate(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Convertir lista de usuarios DAO a lista de usuarios DTO
     * @param users lista de usuarios DAO
     * @return lista de usuarios DTO
     */
    public List<UserDTO> toDTO(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
