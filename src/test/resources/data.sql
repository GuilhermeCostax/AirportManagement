INSERT INTO aeroporto (id_aeroporto, nome_aeroporto, codigo_iata, cidade, codigo_pais_iso, latitude, longitude, altitude) VALUES
  (1, 'Goroka Airport', 'GKA', 'Goroka', NULL, -6.08168983459, 145.391998291, 5282.0),
  (2, 'Madang Airport', 'MAG', 'Madang', NULL, -5.20707988739, 145.789001465, 20.0);

ALTER TABLE aeroporto ALTER COLUMN id_aeroporto RESTART WITH 3;
