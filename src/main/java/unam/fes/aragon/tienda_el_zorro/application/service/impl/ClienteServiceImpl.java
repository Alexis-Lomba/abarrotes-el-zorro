package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ValidateEmail;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;
    private ClientMapper clienteMapper;
    private ValidateEmail validateEmail;

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .toList();  
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) throws Exception {
        log .info("ServiceImpl : {}", clienteDTO);

        validateEmail.validate(clienteDTO.getCorreo());

        clienteRepository.save(clienteMapper.toEntity(clienteDTO));
        clienteDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);

        return clienteDTO;
    }

} 