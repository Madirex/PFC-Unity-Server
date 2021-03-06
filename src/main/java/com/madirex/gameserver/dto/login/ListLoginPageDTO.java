package com.madirex.gameserver.dto.login;

import com.madirex.gameserver.config.APIConfig;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListLoginPageDTO {
    private final LocalDateTime query = LocalDateTime.now();
    private final String project = APIConfig.API_NAME;
    private final String version = APIConfig.API_VERSION;
    private List<LoginDTO> data;
    @NotNull(message = "La paginación no puede ser nula")
    @Min(value = 0, message = "La paginación no puede ser negativa")
    private int currentPage;
    @NotNull(message = "El número de elementos totales no pueden ser nulos")
    @Min(value = 0, message = "El número de elementos totales no pueden ser negativos")
    private long totalElements;
    @NotNull(message = "El número total de páginas no pueden ser nulos")
    @Min(value = 0, message = "El número total de páginas no puede ser negativo")
    private int totalPages;
}