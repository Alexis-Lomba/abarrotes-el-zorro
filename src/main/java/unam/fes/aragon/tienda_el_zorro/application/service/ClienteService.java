package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAll();
    
    ClienteDTO createCliente(ClienteDTO clienteDTO) throws Exception;

    void deleteClienteByNombre(String nombre);

    void deleteClientById(Long id);

    ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO);

} 