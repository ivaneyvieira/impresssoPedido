package br.com.astrosoft.pedidos.view

import br.com.astrosoft.pedidos.viewmodel.DefautlViewModel
import br.com.astrosoft.pedidos.viewmodel.IDefaultView
import br.com.astrosoft.framework.view.ViewLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "", layout = AbastecimentoLayout::class)
@PageTitle("")
class DefaultView: ViewLayout<DefautlViewModel>(), IDefaultView {
  override val viewModel = DefautlViewModel(this)
  
  override fun beforeEnter(event: BeforeEnterEvent?) {
    event?.forwardTo(PedidoView::class.java)
    super.beforeEnter(event)
  }

}