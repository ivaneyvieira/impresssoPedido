package br.com.astrosoft.pedidos.view

import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.SubWindowPDF
import br.com.astrosoft.pedidos.model.beans.ProdutoPedido
import br.com.astrosoft.pedidos.viewmodel.PedidoViewModel
import br.com.astrosoft.pedidos.viewmodel.IPedidoView
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.pedidos.model.beans.Pedido
import com.github.mvysny.karibudsl.v10.addColumnFor
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.formItem
import com.github.mvysny.karibudsl.v10.getColumnBy
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.responsiveSteps
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.grid.ColumnTextAlign.END
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.data.provider.SortDirection.ASCENDING
import com.vaadin.flow.data.renderer.NumberRenderer
import com.vaadin.flow.data.value.ValueChangeMode.EAGER
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import java.text.DecimalFormat

@Route //(layout = AbastecimentoLayout::class)
@Theme(value = Lumo::class, variant = Lumo.DARK)
@PageTitle("Editar")
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoView: ViewLayout<PedidoViewModel>(), IPedidoView {
  private lateinit var edtFoneCli: TextField
  private lateinit var edtNome: TextField
  private lateinit var edtCodigo: TextField
  private lateinit var edtFoneVen: TextField
  private lateinit var edtVendedor: TextField
  private lateinit var edtHora: TextField
  private lateinit var edtData: TextField
  private lateinit var edtLoja: IntegerField
  private lateinit var lblGravado: Label
  private var gridProduto: Grid<ProdutoPedido>
  private lateinit var edtPedido: IntegerField
  override val viewModel: PedidoViewModel = PedidoViewModel(this)
  private val dataProviderProdutos = ListDataProvider<ProdutoPedido>(mutableListOf())
  
  init {
    val toolBar = HorizontalLayout().apply {
      button("Visualizar") {
        icon = VaadinIcon.SEARCH.create()
        onLeftClick {
          viewModel.preview()
        }
      }
      button("Baixar"){
        icon = VaadinIcon.DOWNLOAD.create()
        onLeftClick {
          viewModel.download()
        }
      }
    }
    form("Pesquisar pedidos", toolBar) {
      isExpand = false
      this.responsiveSteps {
        "0px"(1, top)
        "30em"(6, aside)
      }
      formItem("Loja") {
        colspan = 2
        edtLoja = integerField() {
          value = 4
          width = "8em"
        }
      }
      formItem("Orçamento") {
        colspan = 4
        edtPedido = integerField() {
          width = "8em"
          valueChangeMode = EAGER
          addValueChangeListener {event ->
            if(event.isFromClient) {
              val numPedido = event.value
              viewModel.updateGrid(numLoja, numPedido)
            }
          }
        }
      }
      
      formItem("Data Hora") {
        colspan = 2
        horizontalLayout {
          width = "8em"
          edtData = textField() {
            setWidthFull()
            isReadOnly = true
          }
          edtHora = textField() {
            setWidthFull()
            isReadOnly = true
          }
        }
      }
      formItem("Vendedor") {
        colspan = 2
        edtVendedor = textField() {
          setWidthFull()
          isReadOnly = true
        }
      }
      formItem("Fone") {
        colspan = 2
        edtFoneVen = textField() {
          width = "8em"
          isReadOnly = true
        }
      }
      formItem("Cliente") {
        colspan = 4
        horizontalLayout {
          edtCodigo = textField() {
            width = "8em"
            isReadOnly = true
          }
          edtNome = textField() {
            setWidthFull()
            isReadOnly = true
          }
        }
      }
      formItem("Fone") {
        colspan = 2
        edtFoneCli = textField() {
          width = "8em"
          isReadOnly = true
        }
      }
    }
    gridProduto = grid(dataProvider = dataProviderProdutos) {
      isExpand = true
      isMultiSort = true
      addThemeVariants(LUMO_COMPACT)
      setSelectionMode(SelectionMode.SINGLE)
      
      addColumnFor(ProdutoPedido::codigo) {
        setHeader("Código")
        flexGrow = 1
        this.textAlign = END
      }
      addColumnFor(ProdutoPedido::descricao) {
        setHeader("Descrição")
        flexGrow = 8
      }
      addColumnFor(ProdutoPedido::grade) {
        setHeader("Grade")
        flexGrow = 1
      }
      addColumnFor(ProdutoPedido::un) {
        setHeader("Un")
        flexGrow = 1
      }
      
      addColumnFor(ProdutoPedido::qtd, NumberRenderer(ProdutoPedido::qtd, DecimalFormat("0.###"))) {
        setHeader("Qtd")
        flexGrow = 1
        this.textAlign = END
      }
      addColumnFor(ProdutoPedido::vlUnit, NumberRenderer(ProdutoPedido::vlUnit, DecimalFormat("0.00"))) {
        setHeader("R$ Unit")
        flexGrow = 1
        this.textAlign = END
      }
      addColumnFor(ProdutoPedido::vlTotal, NumberRenderer(ProdutoPedido::vlTotal, DecimalFormat("0.00"))) {
        setHeader("R$ Total")
        flexGrow = 1
        this.textAlign = END
      }
      
      sort(listOf(
        GridSortOrder(getColumnBy(ProdutoPedido::codigo), ASCENDING),
        GridSortOrder(getColumnBy(ProdutoPedido::grade), ASCENDING)
                 ))
    }
    toolbar {
    }
  }
  
  override val numPedido: Int
    get() = edtPedido.value ?: 0
  override val numLoja: Int
    get() = edtLoja.value ?: 0
  override val produtos: List<ProdutoPedido>
    get() = dataProviderProdutos.items.toList()
  
  override fun updateGrid(pedido: Pedido?) {
    edtData.value = pedido?.data.format()
    edtHora.value = pedido?.hora.format()
    edtVendedor.value = pedido?.vendedor ?: ""
    edtFoneVen.value = pedido?.telVend ?: ""
    edtCodigo.value = pedido?.codigo ?: ""
    edtNome.value = pedido?.cliente ?: ""
    edtFoneCli.value = pedido?.telCliente ?: ""
    dataProviderProdutos.items.clear()
    dataProviderProdutos.items.addAll(pedido?.produtos.orEmpty())
    dataProviderProdutos.refreshAll()
  }
  
  override fun showRelatorio(byteArray: ByteArray) {
    SubWindowPDF(byteArray).open()
  }
  
  override fun downloadPdf(byteArray: ByteArray) {
    
  }
}
