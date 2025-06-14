SELECT CAST(LPAD(prdno * 1, 6, '0') AS CHAR)                                   AS codigo,
       TRIM(MID(P.name, 1, 37))                                                AS descricao,
       E.grade                                                                 AS grade,
       TRIM(MID(P.name, 37, 3))                                                AS un,
       ROUND(E.qtty / 1000)                                                    AS qtd,
       TRUNCATE(E.price / 100, 2)                                              AS vlUnit,
       @DESC := if(IFNULL(M.mult, 0) = 0, 0.00, 1.00 - (M.mult / (100 * 100))) as pDesconto,
       TRUNCATE((E.qtty / 1000) * (E.price / 100) * (@DESC), 2)                AS vlDesconto,
       TRUNCATE((E.qtty / 1000) * (E.price / 100) * (1.00 - @DESC), 2)         AS vlTotal
FROM sqldados.eoprd AS E
         INNER JOIN sqldados.prd AS P
                    ON P.no = E.prdno
         inner join sqldados.eord AS O
                    ON O.storeno = E.storeno AND O.ordno = E.ordno
         INNER JOIN sqldados.paym AS M
                    ON M.no = O.paymno
WHERE E.storeno = :storeno
  AND E.ordno = :ordno