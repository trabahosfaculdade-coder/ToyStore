package br.com.Toystore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.Toystore.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsuario(String usuario);
}