package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ValidateEmail;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

import java.util.List;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClientMapper clienteMapper;
    private final ValidateEmail validateEmail;
    private final FindIdService findIdService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClientMapper clienteMapper, ValidateEmail validateEmail, FindIdService findIdService) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.validateEmail = validateEmail;
        this.findIdService = findIdService;
    }

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .toList();  
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) throws Exception {
        log .info("ServiceImpl : {}", clienteDTO);

        validateEmail.validate(clienteDTO.getEmail());

        clienteRepository.save(clienteMapper.toEntity(clienteDTO));
        clienteDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);

        return clienteDTO;
    }

    @Override
    public void deleteClienteByNombre(String nombre) {
        clienteRepository.deleteByNombre(nombre);
    }

    @Override
    public void deleteClientById(Long id){
        findIdService.findIdCliente(id);
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ClienteDTO updateCliente(Long id, ClienteDTO dto) {
        Cliente cliente = findIdService.findIdCliente(id);

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(cliente);
    }


} 