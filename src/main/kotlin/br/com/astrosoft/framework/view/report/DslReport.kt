package br.com.astrosoft.framework.view.report

import br.com.astrosoft.pedidos.model.beans.Pedido
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.DynamicReports.col
import net.sf.dynamicreports.report.builder.DynamicReports.report
import net.sf.dynamicreports.report.builder.DynamicReports.stl
import net.sf.dynamicreports.report.builder.DynamicReports.type
import net.sf.dynamicreports.report.builder.column.ColumnBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import java.util.*
import kotlin.reflect.KProperty1

fun report(block: JasperReportBuilder.() -> JasperReportBuilder): JasperReportBuilder {
  return report().block()
}

fun JasperReportBuilder.columns(block: ColumnList.() -> Void): JasperReportBuilder {
  val listColu = ColumnList()
  listColu.block()
  this.columns(* listColu.toTypedArray())
  return this
}

class ColumnList {
  private val list = mutableListOf<ColumnBuilder<*, *>>()
  fun toTypedArray() = list.toTypedArray()
  
  fun addColumnString(title: String, property: KProperty1<*, String>) {
    list.add(col.column(title, property.name, type.stringType()))
  }
  
  fun addColumnDate(title: String, property: KProperty1<*, Date>) {
    val column = col.column(title, property.name, type.dateType())
    column.setPattern("dd/mm/yy")
    list.add(column)
  }

  fun addColumnDouble(title: String, property: KProperty1<*, Double>, pattern : String = "0,000.####") {
    val column = col.column(title, property.name, type.doubleType())
    column.setPattern(pattern)
    column.setStyle(stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
    list.add(column)
  }
}

