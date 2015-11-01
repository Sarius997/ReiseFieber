-- Table: reise

-- DROP TABLE reise;

CREATE TABLE reise
(
  id integer NOT NULL DEFAULT nextval('reiseid'::regclass),
  name text,
  ziel text,
  teilnehmerzahl text,
  beginn text,
  ende text,
  preisproperson text,
  kosten text,
  CONSTRAINT reise_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reise
  OWNER TO postgres;
