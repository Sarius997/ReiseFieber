-- Sequence: reiseid

-- DROP SEQUENCE reiseid;

CREATE SEQUENCE reiseid
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE reiseid
  OWNER TO postgres;
