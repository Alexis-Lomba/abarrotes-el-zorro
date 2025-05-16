package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("client-service")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clients")
    public List<ClienteDTO> getClients(){
        return clienteService.findAll();
    }

    @PostMapping("/create")
    public ClienteDTO createClient(@RequestBody ClienteDTO request) throws Exception {
        log.info("Comienza la cración del del Cliente DTO: {}", request);
        return clienteService.createCliente(request);
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestParam String nombre){
        clienteService.deleteClienteByNombre(nombre);
    }

    @DeleteMapping("delete-id")
    public void deleteClientById(@RequestParam Long id){
        clienteService.deleteClientById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO actualizado = clienteService.updateCliente(id, clienteDTO);
        return ResponseEntity.ok(actualizado);
    }

}
