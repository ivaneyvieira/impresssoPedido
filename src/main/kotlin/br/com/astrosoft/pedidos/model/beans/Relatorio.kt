package br.com.astrosoft.pedidos.model.beans

import br.com.astrosoft.pedidos.model.saci

data class Relatorio(
  val ordno: Int,
  val storeno: Int,
  val prdno: String,
  val localizacao: String,
  val name: String,
  val grade: String,
  val mfno_ref: String,
  val tipo: String,
  val qtty: Double,
  val fornecedor: Int,
  val embalagem: Double
                    ) {
  val saldoApp
    get() = saci.listaProduto(ordno).firstOrNull {
      it.prdno == prdno && it.grade == grade
    }?.saldo ?: 0
}