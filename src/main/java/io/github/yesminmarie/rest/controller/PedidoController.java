package io.github.yesminmarie.rest.controller;

import io.github.yesminmarie.domain.entity.ItemPedido;
import io.github.yesminmarie.domain.entity.Pedido;
import io.github.yesminmarie.domain.enums.StatusPedido;
import io.github.yesminmarie.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.yesminmarie.rest.dto.InformacaoItemPedidoDTO;
import io.github.yesminmarie.rest.dto.InformacoesPedidoDTO;
import io.github.yesminmarie.rest.dto.PedidoDTO;
import io.github.yesminmarie.service.PedidoService;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
@Api("Api Pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salva um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado")
    })
    public InformacoesPedidoDTO getById(
            @PathVariable
            @ApiParam("Id do pedido") Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(pedido -> converter(pedido))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualiza status de um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Status do pedido atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado")
    })
    public void updateStatus(
            @PathVariable
            @ApiParam("Id do cliente") Integer id,
            @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
