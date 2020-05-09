SELECT O.storeno                                                                      AS loja,
       ordno                                                                          AS numPedido,
       IFNULL(S.sname, '')                                                            AS sigla,
       CAST(O.date AS DATE)                                                           AS data,
       SEC_TO_TIME(O.l4)                                                              AS hora,
       TRIM(MID(V.sname, 1, locate('   ', RPAD(V.sname, 30, ' '))))                   AS vendedor,
       IF(V.celular = 0, if(V.tel = 0, '', V.tel), V.celular)                         AS telVend,
       C.no                                                                           AS codigo,
       C.name                                                                         AS cliente,
       IF(C.celular = 0, IF(MID(C.tel, 1, 10) = 0, '', MID(C.tel, 1, 10)), C.celular) AS telCliente,
       M.sname                                                                        AS metodo
FROM sqldados.eord         AS O
  LEFT JOIN sqldados.emp   AS V
	      ON O.empno = V.no
  LEFT JOIN sqldados.store AS S
	      ON S.no = O.storeno
  LEFT JOIN sqldados.custp AS C
	      ON C.no = O.custno
  LEFT JOIN sqldados.paym  AS M
	      ON M.no = O.paymno
WHERE O.ordno = :ordno
  AND O.storeno = :storeno