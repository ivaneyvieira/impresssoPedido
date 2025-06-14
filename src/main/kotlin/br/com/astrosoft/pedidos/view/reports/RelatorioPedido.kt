package br.com.astrosoft.pedidos.view.reports

import br.com.astrosoft.framework.util.format
import br.com.astrosoft.pedidos.model.beans.Pedido
import br.com.astrosoft.pedidos.model.beans.ProdutoPedido
import net.sf.dynamicreports.report.builder.DynamicReports.*
import net.sf.dynamicreports.report.builder.column.ColumnBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER
import net.sf.dynamicreports.report.exception.DRException
import java.io.ByteArrayOutputStream

class RelatorioPedido(val pedido: Pedido) {
  val colCodigo =
    col.column("Código", ProdutoPedido::codigo.name, type.stringType())
      .apply {
        this.setFixedWidth(50)
        this.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
      }
  val colDescricao =
    col.column("Descrição", ProdutoPedido::descricao.name, type.stringType())
      .apply {
        this.setFixedWidth(200)
      }
  val colGrade =
    col.column("Grade", ProdutoPedido::grade.name, type.stringType())
      .apply {
        this.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
      }
  val colUn =
    col.column("Un", ProdutoPedido::un.name, type.stringType())
      .apply {
        this.setFixedWidth(35)
        this.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
      }
  val colQtd =
    col.column("Qtd", ProdutoPedido::qtd.name, type.doubleType())
      .apply {
        this.setPattern("#,##0.####")
        this.setFixedWidth(40)
      }
  val colVlUnit =
    col.column("R$ Unit", ProdutoPedido::vlUnit.name, type.doubleType())
      .apply {
        this.setPattern("#,##0.00")
      }
  val colVlTotal =
    col.column("R$ Total", ProdutoPedido::vlTotal.name, type.doubleType())
      .apply {
        this.setPattern("#,##0.00")
      }
  val colVlDesconto =
    col.column("Desconto", ProdutoPedido::vlDesconto.name, type.doubleType())
      .apply {
        this.setPattern("#,##0.00")
      }

  fun build(): ByteArray {
    return try {
      val outputStream = ByteArrayOutputStream()
      val colunms = columnBuilder().toTypedArray()
      report().title(titleBuider())
        .columns(* colunms)
        .columnGrid(* colunms)
        .subtotalsAtSummary(* subtotalBuilder().toTypedArray())
        .setDataSource(dataSource())
        .summary(pageFooterBuilder())
        .setSubtotalStyle(stl.style().setTopBorder(stl.pen1Point()))
        .pageFooter(cmp.pageNumber().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
        .setTemplate(Templates.reportTemplate)
        .toPdf(outputStream)
      outputStream.toByteArray()
    } catch (e: DRException) {
      e.printStackTrace()
      ByteArray(0)
    }
  }

  private fun pageFooterBuilder(): ComponentBuilder<*, *>? {
    return cmp.verticalList()
      .add(
        cmp.text(""),
        cmp.horizontalList()
          .add(
            cmp.text("OBS:")
              .setFixedWidth(30),
            cmp.text(pedido.observacao)
              .setFixedWidth(300)
          )
      )
  }

  private fun titleBuider(): ComponentBuilder<*, *>? {
    val imageLogo = RelatorioPedido::class.java.getResource("/img/logoEnegecopi.jpg")
    return cmp.horizontalList()
      .add(
        cmp.verticalList()
          .add(
            cmp.text("ENGECOPI ${pedido.sigla}")
              .setStyle(Templates.boldStyle),
            cmp.image(imageLogo)
              .setFixedDimension(80, 60)
          )
          .setFixedWidth(100),
        cmp.verticalList()
          .add(
            cmp.text("Orçamento: ${pedido.numPedido}")
              .setStyle(Templates.bold18CenteredStyle)
              .setHorizontalTextAlignment(CENTER),
            cmp.horizontalList()
              .add(
                cmp.text("Data/Hora:")
                  .setFixedWidth(55),
                cmp.text(pedido.data.format() + " às " + pedido.hora.format())
                  .setFixedWidth(90),
                cmp.text("Vendedor:")
                  .setFixedWidth(55),
                cmp.text(pedido.vendedor)
                  .setFixedWidth(155),
                cmp.text("Fone:")
                  .setFixedWidth(30),
                cmp.text(pedido.telVendFormatado)
              ),
            cmp.horizontalList()
              .add(
                cmp.text("Cliente:")
                  .setFixedWidth(55),
                cmp.text(pedido.codigo + " " + pedido.cliente)
                  .setFixedWidth(300),
                cmp.text("Fone:")
                  .setFixedWidth(30),
                cmp.text(pedido.telClienteFormatado)
              )
          )
      )
  }

  private fun dataSource(): List<ProdutoPedido> {
    return pedido.produtos
  }

  private fun subtotalBuilder(): List<SubtotalBuilder<*, *>> {
    val topPad = 10
    return listOf(
      sbt.text("", colCodigo),
      sbt.text("", colDescricao),
      sbt.text("", colGrade),
      sbt.text("", colUn),
      sbt.text("", colQtd),
      sbt.text("Total R$", colVlUnit)
        .setLabelStyle(
          stl.style().setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
            .setPadding(stl.padding().setTop(topPad))
        )
        .setStyle(
          stl.style().setTopBorder(stl.pen1Point()).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
            .setPadding(stl.padding().setTop(topPad))
        ),
      sbt.sum(colVlDesconto)
        .setStyle(stl.style().setTopBorder(stl.pen1Point()).setPadding(stl.padding().setTop(topPad))),
      sbt.sum(colVlTotal)
        .setStyle(stl.style().setTopBorder(stl.pen1Point()).setPadding(stl.padding().setTop(topPad)))
    )
  }

  private fun columnBuilder(): List<ColumnBuilder<*, *>> {
    return listOf(colCodigo, colDescricao, colGrade, colUn, colQtd, colVlUnit, colVlDesconto, colVlTotal)
  }

  init {
    build()
  }
}
