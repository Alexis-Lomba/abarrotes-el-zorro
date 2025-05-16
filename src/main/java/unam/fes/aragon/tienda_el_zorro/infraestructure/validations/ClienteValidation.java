package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

@Service
@Slf4j
@AllArgsConstructor
public class ClienteValidation {

    private final ClienteRepository clienteRepository;
    private final ClientMapper clienteMapper;

    public Cliente validateCliente(ClienteDTO clienteDTO) {
        log.info("Validando cliente en creación de factura {}", clienteDTO.toString());

        Cliente cliente = clienteRepository.findByEmail(clienteDTO.getEmail());

        if (cliente == null) {
            log.info("Cliente no existe, creando nuevo");
            cliente = clienteRepository.save(clienteMapper.toEntity(clienteDTO));
        } else {
            log.info("Cliente existente encontrado: {}", cliente.toString());
        }

        return cliente;
    }

}
