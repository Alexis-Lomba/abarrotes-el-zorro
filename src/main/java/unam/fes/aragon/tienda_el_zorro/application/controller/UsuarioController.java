package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("usuario-service")
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getAll(){
        return usuarioService.findAll();
    }

    @PostMapping("/create")
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.createUsuario(usuarioDTO);
    }

    @DeleteMapping("/delete-id")
    public void deleteUsuarioById(@RequestParam Long id){
        usuarioService.deleteUsuarioById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO actualizado = usuarioService.updateUsuario(id, usuarioDTO);
        return ResponseEntity.ok(actualizado);
    }


}
