package br.com.astrosoft.pedidos.view.reports

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter
import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType
import net.sf.dynamicreports.report.builder.style.StyleBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.LEFT
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.RIGHT
import net.sf.dynamicreports.report.constant.VerticalTextAlignment.MIDDLE
import net.sf.dynamicreports.report.definition.ReportParameters
import java.awt.Color
import java.util.*


object Templates {

  var rootStyle: StyleBuilder? = null
  

  var boldStyle: StyleBuilder? = null
  

  var italicStyle: StyleBuilder? = null
  
  /**
   * Constant `boldCenteredStyle`
   */
  var boldCenteredStyle: StyleBuilder? = null
  
  /**
   * Constant `bold12CenteredStyle`
   */
  var bold12CenteredStyle: StyleBuilder? = null
  
  /**
   * Constant `bold18CenteredStyle`
   */
  var bold18CenteredStyle: StyleBuilder? = null
  
  /**
   * Constant `bold22CenteredStyle`
   */
  var bold22CenteredStyle: StyleBuilder? = null
  
  /**
   * Constant `columnStyle`
   */
  var columnStyle: StyleBuilder? = null
  
  /**
   * Constant `columnTitleStyle`
   */
  var columnTitleStyle: StyleBuilder? = null
  
  /**
   * Constant `groupStyle`
   */
  var groupStyle: StyleBuilder? = null
  
  /**
   * Constant `subtotalStyle`
   */
  var subtotalStyle: StyleBuilder? = null
  
  /**
   * Constant `reportTemplate`
   */
  var reportTemplate: ReportTemplateBuilder? = null
  
  /**
   * Constant `currencyType`
   */
  var currencyType: CurrencyType? = null
  
  /**
   * Constant `dynamicReportsComponent`
   */
  var dynamicReportsComponent: ComponentBuilder<*, *>? = null
  
  /**
   * Constant `footerComponent`
   */
  var footerComponent: ComponentBuilder<*, *>? = null
  
  /**
   * Creates custom component which is possible to add to any report band component
   *
   * @param label a [java.lang.String] object.
   * @return a [net.sf.dynamicreports.report.builder.component.ComponentBuilder] object.
   */
  fun createTitleComponent(label: String?): ComponentBuilder<*, *> {
    return DynamicReports.cmp.horizontalList()
      .add(dynamicReportsComponent,
           DynamicReports.cmp.text(label)
             .setStyle(bold18CenteredStyle)
             .setHorizontalTextAlignment(RIGHT))
      .newRow()
      .add(DynamicReports.cmp.line())
      .newRow()
      .add(DynamicReports.cmp.verticalGap(10))
  }
  
  fun createCurrencyValueFormatter(label: String): CurrencyValueFormatter {
    return CurrencyValueFormatter(label)
  }
  
  class CurrencyType: BigDecimalType() {
    override fun getPattern(): String {
      return "$ #,###.00"
    }
    
    companion object {
      private const val serialVersionUID = 1L
    }
  }
  
   class CurrencyValueFormatter(private val label: String):
    AbstractValueFormatter<String, Number?>() {
 
    companion object {
      private const val serialVersionUID = 1L
    }
  
     override fun format(value: Number?, reportParameters: ReportParameters?): String {
       return label + currencyType!!.valueToString(value,
                                                   reportParameters?.locale)
     }
   }
  
  init {
    rootStyle =
      DynamicReports.stl.style()
        .setPadding(2)
    boldStyle =
      DynamicReports.stl.style(rootStyle)
        .bold()
    italicStyle =
      DynamicReports.stl.style(rootStyle)
        .italic()
    boldCenteredStyle =
      DynamicReports.stl.style(boldStyle)
        .setTextAlignment(CENTER, MIDDLE)
    bold12CenteredStyle =
      DynamicReports.stl.style(boldCenteredStyle)
        .setFontSize(12)
    bold18CenteredStyle =
      DynamicReports.stl.style(boldCenteredStyle)
        .setFontSize(18)
    bold22CenteredStyle =
      DynamicReports.stl.style(boldCenteredStyle)
        .setFontSize(22)
    columnStyle =
      DynamicReports.stl.style(rootStyle)
        .setVerticalTextAlignment(MIDDLE)
    columnTitleStyle =
      DynamicReports.stl.style(columnStyle)
        .setBorder(DynamicReports.stl.pen1Point())
        .setHorizontalTextAlignment(CENTER)
        .setBackgroundColor(Color.LIGHT_GRAY)
        .bold()
    groupStyle =
      DynamicReports.stl.style(boldStyle)
        .setHorizontalTextAlignment(LEFT)
    subtotalStyle =
      DynamicReports.stl.style(boldStyle)
        .setTopBorder(DynamicReports.stl.pen1Point())
    val crosstabGroupStyle =
      DynamicReports.stl.style(columnTitleStyle)
    val crosstabGroupTotalStyle =
      DynamicReports.stl.style(columnTitleStyle)
        .setBackgroundColor(Color(170, 170, 170))
    val crosstabGrandTotalStyle =
      DynamicReports.stl.style(columnTitleStyle)
        .setBackgroundColor(Color(140, 140, 140))
    val crosstabCellStyle =
      DynamicReports.stl.style(columnStyle)
        .setBorder(DynamicReports.stl.pen1Point())
    val tableOfContentsCustomizer =
      DynamicReports.tableOfContentsCustomizer()
        .setHeadingStyle(0,
                         DynamicReports.stl.style(rootStyle)
                           .bold())
    reportTemplate =
      DynamicReports.template()
        .setLocale(Locale.ENGLISH)
        .setColumnStyle(columnStyle)
        .setColumnTitleStyle(columnTitleStyle)
        .setGroupStyle(groupStyle)
        .setGroupTitleStyle(groupStyle)
        .setSubtotalStyle(subtotalStyle)
        .highlightDetailEvenRows()
        .crosstabHighlightEvenRows()
        .setCrosstabGroupStyle(crosstabGroupStyle)
        .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
        .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
        .setCrosstabCellStyle(crosstabCellStyle)
        .setTableOfContentsCustomizer(tableOfContentsCustomizer)
    currencyType = CurrencyType()
    val link = DynamicReports.hyperLink("http://www.dynamicreports.org")
    dynamicReportsComponent =
      DynamicReports.cmp.horizontalList(DynamicReports.cmp.image(
        Templates::class.java.getResource("images/dynamicreports.png"))
                                          .setFixedDimension(60, 60),
                                        DynamicReports.cmp.verticalList(DynamicReports.cmp.text("DynamicReports")
                                                                          .setStyle(bold22CenteredStyle)
                                                                          .setHorizontalTextAlignment(
                                                                            LEFT),
                                                                        DynamicReports.cmp.text("http://www.dynamicreports.org")
                                                                          .setStyle(italicStyle)
                                                                          .setHyperLink(link)))
        .setFixedWidth(300)
    footerComponent =
      DynamicReports.cmp.pageXofY()
        .setStyle(DynamicReports.stl.style(boldCenteredStyle)
                    .setTopBorder(DynamicReports.stl.pen1Point()))
  }
}