package br.com.astrosoft.framework.view

import br.com.astrosoft.pedidos.model.saci
import br.com.astrosoft.pedidos.view.AbastecimentoLayout
import br.com.astrosoft.pedidos.view.EditarView
import br.com.astrosoft.framework.model.RegistryUserInfo
import br.com.astrosoft.framework.view.LoginView.Companion.LOGIN_PATH
import com.github.mvysny.karibudsl.v10.navigateToView
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo

@Route(LOGIN_PATH)
@Theme(value = Lumo::class, variant = Lumo.DARK)
class LoginView: VerticalLayout(), BeforeEnterObserver {
  private val appName = RegistryUserInfo.appName
  private val version = "Versão ${RegistryUserInfo.version}"
  private val loginForm = LoginFormApp(appName, version) {
    AbastecimentoLayout.updateLayout()
    navigateToView<LoginView>()
  }
  
  init {
    //add(loginForm)
    element.appendChild(loginForm.element)
    loginForm.isOpened = true
  }
  
  companion object {
    const val LOGIN_PATH = "login"
  }
  
  override fun beforeEnter(event: BeforeEnterEvent?) {
    saci.findUser(RegistryUserInfo.usuario)
      ?.let {usuario ->
        when {
          usuario.editar   -> event?.rerouteTo(EditarView::class.java)
          else             -> null
        }
      }
  }
}