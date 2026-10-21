package br.com.melqui.livraria.service;

import br.com.melqui.livraria.exception.CredenciaisInvalidasException;
import br.com.melqui.livraria.exception.EmailJaCadastradoException;
import br.com.melqui.livraria.model.Usuario;
import br.com.melqui.livraria.repository.UsuarioRepository;
import br.com.melqui.livraria.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // cadastro: não deixa repetir e-mail e salva a senha embaralhada (BCrypt), nunca a senha pura
    public void registrar(String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    // login: acha o usuário, confere a senha e, se estiver certa, devolve o token com o e-mail dele
    public String login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(CredenciaisInvalidasException::new);
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }
        return jwtService.gerarToken(usuario.getEmail());
    }
}
