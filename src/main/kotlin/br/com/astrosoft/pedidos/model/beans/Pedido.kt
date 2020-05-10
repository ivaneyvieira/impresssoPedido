package br.com.astrosoft.pedidos.model.beans

import br.com.astrosoft.framework.util.mid
import br.com.astrosoft.pedidos.model.saci
import java.time.LocalDate
import java.time.LocalTime

data class Pedido(
  val loja: Int,
  val numPedido: Int,
  val sigla: String,
  val data: LocalDate,
  val hora: LocalTime,
  val vendedor: String,
  val dddVend: Int,
  val telVend: String,
  val codigo: String,
  val cliente: String,
  val dddCliente: Int,
  val telCliente: String,
  val metodo: String,
  val observacao: String
                 ) {
  val produtos
    get() = saci.listaProduto(loja, numPedido)
  
  private fun formatTelefone(ddd: Int, telefone: String): String {
    val dddStr = if(ddd.toString().length == 2) ddd.toString() else "XX"
    val telStr = when(telefone.length) {
      9    -> "${telefone.substring(0, 1)} ${telefone.substring(1, 5)}-${telefone.substring(5, 9)}"
      8    -> {
        val pre = telefone.mid(0, 1)
        val telefone_nove = if(pre == "8" || pre == "9") "9${telefone}" else " $telefone"
        "${telefone_nove.substring(0, 1)} ${telefone_nove.substring(1, 5)}-${telefone_nove.substring(5, 9)}"
      }
      7    -> {
        val pre = telefone.mid(0, 1)
        val telefone_nove = if(pre == "2") " 3${telefone}" else ""
        if(telefone_nove == "") ""
        else
          "${telefone_nove.substring(0, 1)} ${telefone_nove.substring(1, 5)}-${telefone_nove.substring(5, 9)}"
      }
      else -> ""
    }
    return if(telStr == "") "" else "(${dddStr}) $telStr"
  }
  
  val telVendFormatado
    get() = formatTelefone(dddVend, telVend)
  val telClienteFormatado
    get() = formatTelefone(dddCliente, telCliente)
}