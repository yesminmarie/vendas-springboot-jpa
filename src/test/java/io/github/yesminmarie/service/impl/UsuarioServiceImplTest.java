package io.github.yesminmarie.service.impl;

import io.github.yesminmarie.domain.entity.Usuario;
import io.github.yesminmarie.domain.repository.UsuarioRepository;
import io.github.yesminmarie.exception.SenhaInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioServiceImplTest {

    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.encoder = new BCryptPasswordEncoder();
        this.usuarioService = new UsuarioServiceImpl(encoder, usuarioRepository);
    }

    @Test
    @DisplayName("Verifica se o usuário é salvo passando todos os dados corretamente")
    void verificaSeSalvaUsuarioPassandoTodosOsDadosCorretamente() {
        Usuario usuario = criaUsuario(1, "teste", "123", true);
        usuarioService.salvar(usuario);
        Mockito.verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Verifica se o usuário autenticado possui roles ADMIN e USER passando admin igual true")
    void verificaSeUsuarioAutenticadoPossuiRolesAdminUserPassandoAdminIgualTrue() {
        String senhaCriptografada = encoder.encode("123");
        Usuario usuario = criaUsuario(1, "teste", "123", true);
        Usuario usuarioComSenhaCriptografada = criaUsuario(1, "teste", senhaCriptografada, true);
        Mockito.when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuarioComSenhaCriptografada));

        UserDetails user = usuarioService.autenticar(usuario);

        String roles = user.getAuthorities().toString();

        assertEquals("teste", user.getUsername());
        assertEquals("[ROLE_ADMIN, ROLE_USER]", roles);
    }

    @Test
    @DisplayName("Verifica se o usuário autenticado possui apenas role USER passando admin igual false")
    void verificaSeUsuarioAutenticadoPossuiApenasRoleUserPassandoAdminIgualFalse() {
        String senhaCriptografada = encoder.encode("123");
        Usuario usuario = criaUsuario(1, "teste", "123", false);
        Usuario usuarioComSenhaCriptografada = criaUsuario(1, "teste", senhaCriptografada, false);
        Mockito.when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuarioComSenhaCriptografada));

        UserDetails user = usuarioService.autenticar(usuario);

        String roles = user.getAuthorities().toString();

        assertEquals("teste", user.getUsername());
        assertEquals("[ROLE_USER]", roles);
    }

    @Test
    @DisplayName("Verifica se lança exceção de senha inválida caso senha esteja incorreta")
    void verificaSeLancaExcecaoSenhaInvalidaCasoSenhaEstejaIncorreta() {
        String senhaCriptografada = encoder.encode("123");
        Usuario usuario = criaUsuario(1, "teste", "111", false);
        Usuario usuarioComSenhaCriptografada = criaUsuario(1, "teste", senhaCriptografada, false);
        Mockito.when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuarioComSenhaCriptografada));

        Exception exception = assertThrows(SenhaInvalidaException.class,
                () -> usuarioService.autenticar(usuario));

        assertEquals("Senha inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se lança exceção caso usuario nao seja encontrado")
    void verificaSeLancaExcecaoCasoUsuarioNaoSejaEncontrado() {
        Usuario usuario = criaUsuario(1, "teste", "111", false);
        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> usuarioService.autenticar(usuario));

        assertEquals("Usuário não encontrado na base de dados.", exception.getMessage());
    }

    private Usuario criaUsuario(Integer id, String login, String senha, boolean admin){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setAdmin(admin);
        return usuario;
    }
}