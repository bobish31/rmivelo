CREATE DATABASE "rmivelo" WITH TEMPLATE = template0 ENCODING = 'UTF8';
	ALTER DATABASE "rmivelo" OWNER TO postgres;

	CREATE TABLE velo (
	    velo_id integer NOT NULL,
	    velo_operationnel BOOLEAN DEFAULT TRUE,
	    velo_enCirculation BOOLEAN DEFAULT FALSE
	); 	

	ALTER TABLE public.velo OWNER TO postgres;
	CREATE SEQUENCE velo_id_seq
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	
	ALTER TABLE public.velo_id_seq OWNER TO postgres;
	ALTER SEQUENCE velo_id_seq OWNED BY velo.velo_id;

	
	CREATE TABLE j_soc_dev (
	    jsd_id integer NOT NULL,
	    jsd_soc_k bigint NOT NULL,
	    jsd_dev_k bigint NOT NULL
	);
	ALTER TABLE public.j_soc_dev OWNER TO postgres;
	CREATE SEQUENCE j_soc_dev_jsd_id_seq
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	ALTER TABLE public.j_soc_dev_jsd_id_seq OWNER TO postgres;
	ALTER SEQUENCE j_soc_dev_jsd_id_seq OWNED BY j_soc_dev.jsd_id;
	SELECT pg_catalog.setval('j_soc_dev_jsd_id_seq', 5, true);
	
	
	CREATE TABLE langage (
	    lan_id integer NOT NULL,
	    lan_nom character varying(64) NOT NULL
	);
	ALTER TABLE public.langage OWNER TO postgres;
	CREATE SEQUENCE langage_lan_id_seq
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	ALTER TABLE public.langage_lan_id_seq OWNER TO postgres;
	ALTER SEQUENCE langage_lan_id_seq OWNED BY langage.lan_id;
	SELECT pg_catalog.setval('langage_lan_id_seq', 3, true);
	
	
	CREATE TABLE societe (
	    soc_id integer NOT NULL,
	    soc_nom character varying(64) NOT NULL
	);
	ALTER TABLE public.societe OWNER TO postgres;
	CREATE SEQUENCE societe_soc_id_seq
	    INCREMENT BY 1
	    NO MAXVALUE
	    NO MINVALUE
	    CACHE 1;
	ALTER TABLE public.societe_soc_id_seq OWNER TO postgres;
	ALTER SEQUENCE societe_soc_id_seq OWNED BY societe.soc_id;
	SELECT pg_catalog.setval('societe_soc_id_seq', 2, true);
	
	
	ALTER TABLE developpeur ALTER COLUMN dev_id SET DEFAULT nextval('developpeur_dev_id_seq'::regclass);
	ALTER TABLE j_soc_dev ALTER COLUMN jsd_id SET DEFAULT nextval('j_soc_dev_jsd_id_seq'::regclass);
	ALTER TABLE langage ALTER COLUMN lan_id SET DEFAULT nextval('langage_lan_id_seq'::regclass);
	ALTER TABLE societe ALTER COLUMN soc_id SET DEFAULT nextval('societe_soc_id_seq'::regclass);
	
	INSERT INTO developpeur (dev_id, dev_nom, dev_prenom, dev_lan_k) VALUES (1, 'HERBY', 'Cyrille', 1);
	INSERT INTO developpeur (dev_id, dev_nom, dev_prenom, dev_lan_k) VALUES (2, 'PITON', 'Thomas', 3);
	INSERT INTO developpeur (dev_id, dev_nom, dev_prenom, dev_lan_k) VALUES (3, 'COURTEL', 'Angelo', 2);
	
	INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k) VALUES (1, 1, 1);
	INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k) VALUES (2, 1, 2);
	INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k) VALUES (3, 1, 3);
	INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k) VALUES (4, 2, 1);
	INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k) VALUES (5, 2, 3);
	
	INSERT INTO langage (lan_id, lan_nom) VALUES (1, 'Java');
	INSERT INTO langage (lan_id, lan_nom) VALUES (2, 'PHP');
	INSERT INTO langage (lan_id, lan_nom) VALUES (3, 'C++');
	
	INSERT INTO societe (soc_id, soc_nom) VALUES (1, 'Societe 1');
	INSERT INTO societe (soc_id, soc_nom) VALUES (2, 'Societe 2');
	
	
	ALTER TABLE ONLY developpeur
	    ADD CONSTRAINT developpeur_pkey PRIMARY KEY (dev_id);
	ALTER TABLE ONLY j_soc_dev
	    ADD CONSTRAINT j_soc_dev_pkey PRIMARY KEY (jsd_id);
	ALTER TABLE ONLY langage
	    ADD CONSTRAINT langage_pkey PRIMARY KEY (lan_id);
	ALTER TABLE ONLY societe
	    ADD CONSTRAINT societe_pkey PRIMARY KEY (soc_id);
	
	CREATE INDEX fki_ ON developpeur USING btree (dev_lan_k);
	CREATE INDEX fki_fki_developpeur ON j_soc_dev USING btree (jsd_dev_k);
	CREATE INDEX fki_fki_societe ON j_soc_dev USING btree (jsd_soc_k);
	
	
	ALTER TABLE ONLY developpeur
	    ADD CONSTRAINT developpeur_dev_lan_k_fkey FOREIGN KEY (dev_lan_k) REFERENCES langage(lan_id);
	ALTER TABLE ONLY j_soc_dev
	    ADD CONSTRAINT fki_developpeur FOREIGN KEY (jsd_dev_k) REFERENCES developpeur(dev_id);
	ALTER TABLE ONLY j_soc_dev
	    ADD CONSTRAINT fki_societe FOREIGN KEY (jsd_soc_k) REFERENCES societe(soc_id);