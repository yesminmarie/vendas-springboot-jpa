package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Cliente;
import io.github.yesminmarie.domain.repository.ClientesRepository;
import io.github.yesminmarie.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private Validator validator;

    private ClienteService clienteService;

    @Mock
    private ClientesRepository clientesRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.clienteService = new ClienteServiceImpl(clientesRepository);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Verifica se o método getClienteById() traz o cliente quando é inserido um id válido")
    void verificaSeBuscaClientePorIdValido() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");

        Mockito.when(clientesRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.getClienteById(1);

        assertEquals(1, cliente.getId());
        assertEquals("Maria", cliente.getNome());
        assertEquals("76328651015", cliente.getCpf());
    }

    @Test
    @DisplayName("Verifica se o método getClienteById() lança exceção quando cliente não existe")
    void verificaSeLancaExcecaoNaBuscaPorIdQueNaoExiste() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> clienteService.getClienteById(1));
        assertEquals("404 NOT_FOUND \"Cliente não encontrado.\"", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se o cliente é salvo passando todos os dados corretamente")
    void verificaSeSalvaClientePassandoTodosOsDadosCorretmente() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        clienteService.salvar(cliente);
        Mockito.verify(clientesRepository).save(cliente);
    }

    @Test
    @DisplayName("Verifica se a mensagem de cpf incorreto é mostrada.")
    void verificaSeMostraMensagemCpfIncorreto() {
        Cliente cliente = criaCliente(1, "Maria", "12345678901");

        Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

        boolean hasExpectedPropertyPath = constraintViolations.stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .anyMatch("cpf"::equals);
        boolean hasExpectedViolationMessage = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch("{campo.cpf.invalido}"::equals);

        assertAll(
                () -> assertFalse(constraintViolations.isEmpty()),
                () -> assertTrue(hasExpectedPropertyPath),
                () -> assertTrue(hasExpectedViolationMessage)
        );

    }

    @Test
    @DisplayName("Verifica se o cliente é deletado caso o id passado esteja correto")
    void verificaSeClienteDeletadoCasoIdCorreto() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");

        Mockito.when(clientesRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.delete(1);

        Mockito.verify(clientesRepository).delete(cliente);
    }

    @Test
    @DisplayName("Verifica se é lançada exceção caso o id do cliente a ser deletado esteja incorreto")
    void verificaSeELancadaExcecaoCasoIdClienteASerDeletadoEstejaIncorreto() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> clienteService.delete(1));
        assertEquals("404 NOT_FOUND \"Cliente não encontrado\"", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se o cliente é atualizado caso o id passado esteja correto")
    void verificaSeClienteAtualizadoCasoIdCorreto() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Cliente cliente2 = criaCliente(1, "Pedro", "76328651015");

        Mockito.when(clientesRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.update(1, cliente2);

        Mockito.verify(clientesRepository).save(cliente2);
    }

    @Test
    @DisplayName("Verifica se é lançada exceção caso o id do cliente a ser atualizado esteja incorreto")
    void verificaSeELancadaExcecaoCasoIdClienteASerAtualizadoEstejaIncorreto() {
        Cliente clienteAtualizado = criaCliente(1, "Pedro", "76328651015");

        Exception exception = assertThrows(ResponseStatusException.class,
                () -> clienteService.update(2, clienteAtualizado));
        assertEquals("404 NOT_FOUND \"Cliente não encontrado\"", exception.getMessage());
    }

    @Test
    void verificaMetodofind() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        Mockito.when(clientesRepository.findAll()).thenReturn(clientes);

        clienteService.find(cliente);

        assertEquals(1, clientes.get(0).getId());
        assertEquals("Maria", clientes.get(0).getNome());
        assertEquals("76328651015", clientes.get(0).getCpf());
    }

    private Cliente criaCliente(Integer id, String nome, String cpf){
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);

        return cliente;
    }
}