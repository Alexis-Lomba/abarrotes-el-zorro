package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import unam.fes.aragon.tienda_el_zorro.application.service.ClienteService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;

@Slf4j
@Controller
@RequestMapping("client-service")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/clients")
    public void getClients(){

    }

    @PostMapping("/create")
    public ClienteDTO createClient(@RequestBody ClienteDTO request) throws Exception {
        log.info("Comienza la cración del del Cliente DTO: {}", request);
        return clienteService.createCliente(request);
    }
}
