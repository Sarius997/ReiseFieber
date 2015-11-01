-- Table: kundenreise

-- DROP TABLE kundenreise;

CREATE TABLE kundenreise
(
  id integer NOT NULL DEFAULT nextval('kundereiseid'::regclass),
  k_id integer,
  r_id integer,
  storno boolean,
  CONSTRAINT kunden_reise_id PRIMARY KEY (id),
  CONSTRAINT kundenreise_k_id_fkey FOREIGN KEY (k_id)
      REFERENCES kunden (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT kundenreise_r_id_fkey FOREIGN KEY (r_id)
      REFERENCES reise (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE kundenreise
  OWNER TO postgres;
