CREATE TABLE aeroporto (
  id_aeroporto INTEGER PRIMARY KEY,
  nome_aeroporto TEXT NOT NULL,
  codigo_iata TEXT,
  cidade TEXT NOT NULL,
  codigo_pais_iso TEXT,
  latitude REAL,
  longitude REAL,
  altitude REAL
);

