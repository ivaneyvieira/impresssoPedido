SELECT O.storeno                                                                                 AS loja,
       ordno                                                                                     AS numPedido,
       IFNULL(S.sname, '')                                                                       AS sigla,
       CAST(O.date AS DATE)                                                                      AS data,
       SEC_TO_TIME(O.l4)                                                                         AS hora,
       TRIM(MID(V.sname, 1,
		locate('   ', RPAD(V.sname, 30, ' '))))                                          AS vendedor,
       cast(
	 IF(V.celular = 0, if(V.tel = 0, '', V.tel), V.celular) AS CHAR)                         AS telVend,
       cast(C.no AS CHAR)                                                                        AS codigo,
       C.name                                                                                    AS cliente,
       cast(IF(C.celular = 0, IF(MID(C.tel, 1, 10) = 0, '', MID(C.tel, 1, 10)),
	       C.celular) AS CHAR)                                                               AS telCliente,
       M.name
         AS metodo,
       cast(TRIM(TRAILING '\n' FROM CONCAT(TRIM(MID(remarks__480, 1, 40)), '\n',
					   TRIM(MID(remarks__480, 41, 40)), '\n',
					   TRIM(MID(remarks__480, 81, 40)), '\n',
					   TRIM(MID(remarks__480, 121, 40)), '\n',
					   TRIM(MID(remarks__480, 161, 40)), '\n',
					   TRIM(MID(remarks__480, 201, 40)), '\n',
					   TRIM(MID(remarks__480, 241, 40)), '\n',
					   TRIM(MID(remarks__480, 281, 40)))) as char) as observacao
FROM sqldados.eord         AS O
  LEFT JOIN sqldados.emp   AS V
	      ON O.empno = V.no
  LEFT JOIN sqldados.store AS S
	      ON S.no = O.storeno
  LEFT JOIN sqldados.custp AS C
	      ON C.no = O.custno
  LEFT JOIN sqldados.paym  AS M
	      ON M.no = O.paymno
LEFT JOIN sqldados.eordrk AS OB
ON OB.ordno = O.ordno
AND OB.storeno = O.storeno
WHERE O.ordno = :ordno
  AND O.storeno = :storeno