SELECT LPAD(prdno * 1, 6, '0')           AS codigo,
       TRIM(MID(P.name, 1, 37))          AS descricao,
       E.grade                           AS grade,
       TRIM(MID(P.name, 37, 3))          AS un,
       E.qtty / 1000                     AS qtd,
       E.price / 100                     AS vlUnit,
       (E.qtty / 1000) * (E.price / 100) AS vlTotal
FROM sqldados.eoprd       AS E
  INNER JOIN sqldados.prd AS P
	       ON P.no = E.prdno
WHERE E.storeno = :storeno
  AND E.ordno = :ordno