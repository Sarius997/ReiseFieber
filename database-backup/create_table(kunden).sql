-- Table: kunden

-- DROP TABLE kunden;

CREATE TABLE kunden
(
  id integer NOT NULL DEFAULT nextval('kunden_id_seq'::regclass),
  nachname text,
  vorname text,
  geschlecht text,
  geburtstag text,
  telefonnummer text,
  wohnort text,
  CONSTRAINT kunden_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE kunden
  OWNER TO postgres;
