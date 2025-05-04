package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ClienteMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

@Service
@Slf4j
public class ClienteValidation {

    private ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    public Cliente validateCliente(ClienteDTO clienteDTO) {
        log.info("Validando cliente en creacionde factura {}", clienteDTO.toString());
        Cliente cliente = clienteRepository.findByEmail(clienteDTO.getCorreo());

        log.info("Validacion del cliente {}", cliente.toString());
        if (cliente != null) {
            log.info("Se crea nuevo cliente");
            cliente = clienteRepository.save(clienteMapper.toEntity(clienteDTO));
        }

        return cliente;
    }
}
