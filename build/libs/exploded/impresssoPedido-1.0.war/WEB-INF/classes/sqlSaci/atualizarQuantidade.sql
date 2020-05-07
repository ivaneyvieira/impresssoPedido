UPDATE sqldados.eord
SET bits13 = bits13 | POW(2, 0)
WHERE storeno = :storeno AND ordno = :ordno;

UPDATE sqldados.eoprd
SET qtty = :qtty * 1000
WHERE storeno = :storeno AND ordno = :ordno AND prdno = LPAD(:prdno, 16, ' ') AND grade = :grade;

UPDATE sqldados.eoprdf
SET qtty = :qtty * 1000
WHERE storeno = :storeno AND
      ordno = :ordno AND
      prdno = LPAD(:prdno, 16, ' ') AND
      grade = :grade;

UPDATE sqldados.eoprd2
SET qtty = :qtty * 1000
WHERE storeno = :storeno AND
      ordno = :ordno AND
      prdno = LPAD(:prdno, 16, ' ') AND
      grade = :grade;

UPDATE sqldados.eoprd3
SET qtty = :qtty * 1000
WHERE storeno = :storeno AND
      ordno = :ordno AND
      prdno = LPAD(:prdno, 16, ' ') AND
      grade = :grade