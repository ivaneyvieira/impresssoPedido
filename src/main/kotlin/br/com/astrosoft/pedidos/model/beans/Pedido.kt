package br.com.astrosoft.pedidos.model.beans

import br.com.astrosoft.pedidos.model.saci
import java.time.LocalDate
import java.time.LocalTime

data class Pedido(
  val loja: Int,
  val numPedido: Int,
  val sigla : String,
  val data: LocalDate,
  val hora: LocalTime,
  val vendedor: String,
  val telVend: String,
  val codigo: String,
  val cliente: String,
  val telCliente: String,
  val metodo: String,
  val observacao : String
                 ) {
  val produtos
    get() = saci.listaProduto(loja, numPedido)
}