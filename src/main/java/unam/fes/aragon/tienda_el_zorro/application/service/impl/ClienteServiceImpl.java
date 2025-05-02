package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ClienteMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDto)
                .toList();  
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

} 