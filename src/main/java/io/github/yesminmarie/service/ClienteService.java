package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente getClienteById(Integer id);
    Cliente salvar(Cliente cliente);
    void delete(Integer id);
    void update(Integer id, Cliente cliente);
    List<Cliente> find(Cliente filtro);
}
