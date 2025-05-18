package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.LoginRequest;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("user-service")
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users")
    public List<UsuarioDTO> getAll(){
        return usuarioService.findAll();
    }

    @PostMapping("/create")
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.createUsuario(usuarioDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-id/{id}")
    public void deleteUsuarioById(@PathVariable Long id){
        usuarioService.deleteUsuarioById(id);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequest loginRequest) {
        UsuarioDTO usuario = usuarioService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO actualizado = usuarioService.updateUsuario(usuarioDTO, id);
        return ResponseEntity.ok(actualizado);
    }

}
