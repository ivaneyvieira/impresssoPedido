SELECT O.storeno                                                    AS loja,
       ordno                                                        AS numPedido,
       CAST(O.date AS DATE)                                         AS data,
       SEC_TO_TIME(O.l4)                                            AS hora,
       TRIM(MID(V.sname, 1, locate('   ', RPAD(V.sname, 30, ' ')))) AS vendedor,
       V.tel                                                        AS telVend,
       C.no                                                         AS codigo,
       C.name                                                       AS cliente,
       C.celular                                                    AS telCliente,
       M.sname                                                      AS metodo
FROM sqldados.eord         AS O
  LEFT JOIN sqldados.emp   AS V
	      ON O.empno = V.no
  LEFT JOIN sqldados.custp AS C
	      ON C.no = O.custno
  LEFT JOIN sqldados.paym  AS M
	      ON M.no = O.paymno
WHERE O.ordno = :ordno
  AND O.storeno = :storeno