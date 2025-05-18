package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;

@Service
public class ClientMapper {

    public Cliente toEntity(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .id(clienteDTO.getId())
                .nombre(clienteDTO.getNombre())
                .apellido(clienteDTO.getApellido())
                .email(clienteDTO.getCorreo())
                .build();
    }

    public ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .correo(cliente.getEmail())
                .build();
    }
}
