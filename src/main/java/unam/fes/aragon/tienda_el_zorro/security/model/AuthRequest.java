package unam.fes.aragon.tienda_el_zorro.security.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
