package br.com.astrosoft.pedidos.model

import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.DB
import br.com.astrosoft.pedidos.model.beans.Pedido
import br.com.astrosoft.pedidos.model.beans.ProdutoPedido

class QuerySaci: QueryDB(driver, url, username, password) {
  fun listaProduto(storeno: Int, ordno: Int): List<ProdutoPedido> {
    val sql = "/sqlSaci/listaProdutos.sql"
    val lista = query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.addParameter("ordno", ordno)
      q.executeAndFetch(ProdutoPedido::class.java)
    }
    
    return lista
  }
  
  fun listaPedido(storeno: Int?, ordno: Int?): Pedido? {
    ordno ?: return null
    storeno ?: return null
    val sql = "/sqlSaci/listaPedidos.sql"
    
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.addParameter("ordno", ordno)
      q.executeAndFetch(Pedido::class.java)
        .firstOrNull()
    }
  }
  
  fun findAbreviacoes(): List<String> {
    val sql = "/sqlSaci/findAbreviacoes.sql"
    return query(sql) {q ->
      q.executeScalarList(String::class.java)
    }
  }
  
  fun removePedido(ordno: Int, prdno: String, grade: String) {
    val sql = "/sqlSaci/removerPedido.sql"
    val storeno = 4
    return script(sql) {q ->
      q.addOptionalParameter("storeno", storeno)
      q.addOptionalParameter("ordno", ordno)
      q.addOptionalParameter("prdno", prdno)
      q.addOptionalParameter("grade", grade)
      q.executeUpdate()
    }
  }
  
  fun atualizarQuantidade(ordno: Int, prdno: String, grade: String, qtty: Int) {
    val sql = "/sqlSaci/atualizarQuantidade.sql"
    val storeno = 4
    return script(sql) {q ->
      q.addOptionalParameter("storeno", storeno)
      q.addOptionalParameter("ordno", ordno)
      q.addOptionalParameter("prdno", prdno)
      q.addOptionalParameter("grade", grade)
      q.addOptionalParameter("qtty", qtty)
      q.executeUpdate()
    }
  }
  
  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val test = db.test
    val ipServer =
      url.split("/")
        .getOrNull(2)
  }
}

val saci = QuerySaci()



