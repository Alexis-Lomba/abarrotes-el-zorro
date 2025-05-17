package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.RolService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.RolDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("role-service")
public class RolController {

    private  final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/roles")
    public List<RolDTO> getRoles(){
        return rolService.findAll();
    }

    @PostMapping("/create")
    public RolDTO createRol (@RequestBody RolDTO rolDTO){
        return rolService.createRol(rolDTO);
    }
}
