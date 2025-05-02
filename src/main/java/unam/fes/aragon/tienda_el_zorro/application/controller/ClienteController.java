package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
