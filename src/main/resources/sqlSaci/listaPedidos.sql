SELECT O.storeno AS loja, O.ordno AS numPedido, IFNULL(S.sname, '') AS sigla,
       CAST(O.date AS DATE) AS data, SEC_TO_TIME(O.l4) AS hora,
       TRIM(MID(V.sname, 1, locate('   ', RPAD(V.sname, 30, ' ')))) AS vendedor,
       IFNULL(V.ddd, 0) AS dddVend,
       cast(IF(V.celular NOT IN (7, 8, 9), if(V.tel NOT IN (7, 8, 9), '', V.tel),
               V.celular) AS CHAR) AS telVend, cast(C.no AS CHAR) AS codigo, C.name AS cliente,
       IFNULL(MID(C.ddd, 1, 5) * 1, 0) AS dddCliente,
       cast(IF(length(C.celular) NOT IN (7, 8, 9),
               if(length(MID(C.tel, 1, 10) * 1) NOT IN (7, 8, 9), '', MID(C.tel, 1, 10) * 1),
               C.celular) AS CHAR) AS telCliente, M.name AS metodo,
       cast(TRIM(TRAILING '\n' FROM
                 CONCAT(TRIM(MID(remarks__480, 1, 40)), '\n', TRIM(MID(remarks__480, 41, 40)), '\n',
                        TRIM(MID(remarks__480, 81, 40)), '\n', TRIM(MID(remarks__480, 121, 40)),
                        '\n', TRIM(MID(remarks__480, 161, 40)), '\n',
                        TRIM(MID(remarks__480, 201, 40)), '\n', TRIM(MID(remarks__480, 241, 40)),
                        '\n', TRIM(MID(remarks__480, 281, 40)), '\n',
                        TRIM(MID(remarks__480, 321, 40)), '\n', TRIM(MID(remarks__480, 361, 40)),
                        '\n', TRIM(MID(remarks__480, 401, 40)), '\n',
                        TRIM(MID(remarks__480, 441, 40)), '\n',
                        TRIM(MID(remarks__480, 481, 40)))) AS CHAR) AS observacao
FROM sqldados.eord               AS O
       LEFT JOIN sqldados.emp    AS V
       ON O.empno = V.no
       LEFT JOIN sqldados.store  AS S
       ON S.no = O.storeno
       LEFT JOIN sqldados.custp  AS C
       ON C.no = O.custno
       LEFT JOIN sqldados.paym   AS M
       ON M.no = O.paymno
       LEFT JOIN sqldados.eordrk AS OB
       ON OB.ordno = O.ordno AND OB.storeno = O.storeno
WHERE O.ordno = :ordno
  AND O.storeno = :storeno