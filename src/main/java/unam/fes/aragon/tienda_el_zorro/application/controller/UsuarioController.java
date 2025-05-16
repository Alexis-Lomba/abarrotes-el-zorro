package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.LoginRequest;

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

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/create")
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.createUsuario(usuarioDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequest loginRequest) {
        UsuarioDTO usuario = usuarioService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/delete-id")
    public void deleteUsuarioById(@RequestParam Long id){
        usuarioService.deleteUsuarioById(id);
    }

}
