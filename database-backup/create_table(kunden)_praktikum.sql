-- Table: kunden

-- DROP TABLE kunden;

CREATE TABLE kunden
(
  id integer NOT NULL DEFAULT nextval('kdid'::regclass),
  nachname character varying(128),
  vorname character varying(128),
  wohnort character varying(128),
  geburtstag character varying(128),
  volljaehrig character varying(128),
  telefonnummer character varying(128),
  geschlecht character varying(128),
  postleitzahl text,
  adresse text,
  CONSTRAINT kunden_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE kunden
  OWNER TO postgres;
