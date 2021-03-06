package com.madirex.gameserver.mapper;

import com.madirex.gameserver.dto.login.LoginDTO;
import com.madirex.gameserver.model.Login;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoginMapper {
    private final ModelMapper modelMapper;

    /**
     * Convertir DAO a DTO
     * @param login DAO
     * @return DTO
     */
    public LoginDTO toDTO(Login login) {
        return modelMapper.map(login, LoginDTO.class);
    }

    /**
     * Convertir DTO a DAO
     * @param loginDTO DTO
     * @return DAO
     */
    public Login fromDTO(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, Login.class);
    }

    /**
     * Convertir lista de DAO a lista de dTO
     * @param logins lista DAO
     * @return lista DTO
     */
    public List<LoginDTO> toDTO(List<Login> logins) {
        return logins.stream().map(this::toDTO).collect(Collectors.toList());
    }
}