package br.com.astrosoft.pedidos.model.beans

import br.com.astrosoft.pedidos.model.QueryAppEstoque
import br.com.astrosoft.framework.util.lpad

data class ProdutoPedido(
  val codigo: String,
  val descricao: String,
  val grade : String,
  val un: String,
  val qtd: Double,
  val vlUnit: Double,
  val vlTotal: Double
                        )