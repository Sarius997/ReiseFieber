-- Sequence: kundereiseid

-- DROP SEQUENCE kundereiseid;

CREATE SEQUENCE kundereiseid
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 10
  CACHE 1;
ALTER TABLE kundereiseid
  OWNER TO postgres;
