package br.com.astrosoft.pedidos.viewmodel

import br.com.astrosoft.pedidos.model.beans.Pedido
import br.com.astrosoft.pedidos.model.beans.ProdutoPedido
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.pedidos.model.saci
import br.com.astrosoft.pedidos.view.reports.RelatorioPedido


class PedidoViewModel(view: IPedidoView): ViewModel<IPedidoView>(view) {
  private fun findPedidos(): Pedido? {
    return saci.listaPedido(view.numLoja, view.numPedido)
  }
  
  fun imprimir() = exec {
    val pedido = findPedidos() ?: fail("Pedido não encontrado")
    val relatorio = RelatorioPedido(pedido)
    val byteArray = relatorio.build()
    view.showRelatorio(byteArray)
  }
  
  fun updateGrid(numLoja: Int?, numPedido: Int?) {
    val pedido = saci.listaPedido(numLoja, numPedido)
    view.updateGrid(pedido)
  }
}

interface IPedidoView: IView {
  val numPedido: Int
  val numLoja: Int
  val produtos: List<ProdutoPedido>
  
  fun updateGrid(pedido: Pedido?)
  
  fun showRelatorio(byteArray : ByteArray)
}
