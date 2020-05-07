package br.com.astrosoft.pedidos.model.beans

import br.com.astrosoft.pedidos.model.saci

data class Pedido(val storeno: Int, val ordno: Int, val custno: Int, val paymno: Int,
                  val status: Int, val gravado: Boolean) {
  fun produtos(user: UserSaci?) = saci
    .listaProduto(ordno)
    .filtraLocalizacoes()
    .filter {user?.isLocalizacaoCompativel(it.abreviacao) ?: false}
    .sortedWith(compareBy({it.abreviacao}, {it.descricao}, {it.grade}))
  
  fun abreviacoes(user: UserSaci?) = produtos(user)
    .filtraLocalizacoes()
    .groupBy {it.abreviacao}
    .entries
    .sortedBy {-it.value.size}
    .map {it.key}
  
  private fun List<ProdutoPedido>.filtraLocalizacoes(): List<ProdutoPedido> {
    return this.groupBy {ProdutoKey(it.prdno, it.grade)}
      .flatMap {entry ->
        val list = entry.value.filter {
          (!it.abreviacao.startsWith("EXP4")) && (!it.abreviacao.startsWith("CD00"))
        }
        if(list.isEmpty()) entry.value else list
      }
  }
  
  fun isSaldoInssuficiente(user: UserSaci?): Boolean {
    val produtos = produtos(user)
    return if(produtos.isEmpty())
      false
    else {
      produtos.any {it.saldoInsuficiente}
    }
  }
  
  val isClienteValido
    get() = custno in listOf(478, 21295, 21333, 102773, 108751, 120420, 709327, 901705)
  val isMetodoValido
    get() = paymno == 292
  val isStatusValido
    get() = status == 1
  
  companion object {
    fun findPedidos(numeroOrigem: Int?): Pedido? {
      numeroOrigem ?: return null
      return saci.listaPedido(numeroOrigem)
    }
    
    fun findProduto(prdno: String): List<Produto> {
      return saci.findProduto(prdno)
    }
    
    fun findAbreviacoes(): List<String> {
      return saci.findAbreviacoes()
    }
    
    fun removeProduto(pedido: Pedido, produto: ProdutoPedido) {
      saci.removePedido(pedido.ordno, produto.prdno, produto.grade)
    }
    
    fun listaRelatorio(ordno: Int): List<Relatorio> {
      return saci.listaRelatorio(ordno)
    }
  }
}

data class ProdutoKey(val prdno: String, val grade: String)
