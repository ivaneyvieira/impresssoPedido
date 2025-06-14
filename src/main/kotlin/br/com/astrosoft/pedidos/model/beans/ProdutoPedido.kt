package br.com.astrosoft.pedidos.model.beans

data class ProdutoPedido(
  val codigo: String,
  val descricao: String,
  val grade: String,
  val un: String,
  val qtd: Double,
  val vlUnit: Double,
  val pDesconto: Double,
  val vlDesconto: Double,
  val vlTotal: Double
)