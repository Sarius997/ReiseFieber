-- Column: id

-- ALTER TABLE kunden DROP COLUMN id;

ALTER TABLE kunden ADD COLUMN id integer;
ALTER TABLE kunden ALTER COLUMN id SET NOT NULL;
ALTER TABLE kunden ALTER COLUMN id SET DEFAULT nextval('kunden_id_seq'::regclass);
