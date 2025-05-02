package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;
    private ClientMapper clienteMapper;

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .toList();  
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) throws Exception {
        log .info("ServiceImpl : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new ErrorNegocio("YA existe un cliente con ese correo");
        }

        clienteRepository.save(cliente);
        clienteDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);

        return clienteDTO;
    }

} 