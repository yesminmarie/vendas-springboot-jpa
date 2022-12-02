package io.github.yesminmarie.rest.controller;

import io.github.yesminmarie.domain.entity.Cliente;
import io.github.yesminmarie.service.ClienteService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {

        this.clienteService = clienteService;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente getClienteById(
            @PathVariable
            @ApiParam("Id do cliente") Integer id ){
        return clienteService.getClienteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente){

        return clienteService.salvar(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deleta um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public void delete(
            @PathVariable
            @ApiParam("Id do cliente") Integer id){
        clienteService.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public void update( @PathVariable @ApiParam("Id do cliente") Integer id,
                        @RequestBody @Valid Cliente cliente){
        clienteService.update(id, cliente);
    }

    @GetMapping
    @ApiOperation("Faz um filtro por qualquer propriedade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado")
    })
    public List<Cliente> find(Cliente filtro){
        return clienteService.find(filtro);
    }
}