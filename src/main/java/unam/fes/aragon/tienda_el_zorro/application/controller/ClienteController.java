package unam.fes.aragon.tienda_el_zorro.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("client-service")
public class ClienteController {

    @PostMapping("/clients")
    public void getClients(){

    }

    @PostMapping("/create")
    public void createClient(){

    }
}
