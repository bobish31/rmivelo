-- *********** -- *********** -- *********** -- ******* --
--														--
--														--
--		SCRIPT GENERAL DE CREATION DE L'ENSEMBLE		--
--			DE LA BASE DEDIEE AU PROJET RMI				--
--														--
--		Postgre impose d'avoir les variables			--
--		des requêtes sans majuscules : la casse			--
--		n'est pas prise en compte !						--
--		D'où les identifiants sans majuscules...		--
--														--
--														--
-- *********** -- *********** -- *********** -- *******	--


	-- *********** -- *********** -- *********** -- *******
	--			SCRIPT DE CREATION DE LA BASE
	-- *********** -- *********** -- *********** -- *******

		CREATE DATABASE "rmivelo" WITH TEMPLATE = template0 ENCODING = 'UTF8';
		ALTER DATABASE "rmivelo" OWNER TO postgres;




	-- *********** -- *********** -- *********** -- *******
	--			SCRIPT DE CREATION DES TABLES
	-- *********** -- *********** -- *********** -- *******

		-------------------------------------------------------
		-- TABLE VELO
		-------------------------------------------------------
		CREATE TABLE velo (
			identifiantvelo integer NOT NULL,
			operationnel BOOLEAN DEFAULT TRUE,
			fk_identifiantstation integer
		);

		ALTER TABLE public.velo OWNER TO postgres;
		CREATE SEQUENCE identifiantvelo_seq
			INCREMENT BY 1
			NO MAXVALUE
			NO MINVALUE
			CACHE 1;

		ALTER TABLE public.identifiantvelo_seq OWNER TO postgres;
		ALTER SEQUENCE identifiantvelo_seq OWNED BY velo.identifiantvelo;
		-- Initialiser la séquence selon le nombre d'INSERT faits à la main dans ce script
		SELECT pg_catalog.setval('identifiantvelo_seq', 5, true);


		-------------------------------------------------------
		-- TABLE UTILISATEUR
		-------------------------------------------------------
		CREATE TABLE utilisateur (
				numero integer NOT NULL,
				code integer NOT NULL,
				tech BOOLEAN DEFAULT FALSE
			);

		ALTER TABLE public.utilisateur OWNER TO postgres;
		CREATE SEQUENCE numero_seq
			INCREMENT BY 1
			NO MAXVALUE
			NO MINVALUE
			CACHE 1;

		ALTER TABLE public.numero_seq OWNER TO postgres;
		ALTER SEQUENCE numero_seq OWNED BY utilisateur.numero;
		-- Initialiser la séquence selon le nombre d'INSERT faits à la main dans ce script
		SELECT pg_catalog.setval('numero_seq', 5, true);


		-------------------------------------------------------
		-- TABLE STATION
		-------------------------------------------------------
		CREATE TABLE station (
				identifiantstation integer NOT NULL,
				capacite integer NOT NULL,
				nbretraits integer NOT NULL DEFAULT 0,
				nbdepots integer NOT NULL DEFAULT 0,
				latitude real NOT NULL,
				longitude real NOT NULL
			);

		ALTER TABLE public.station OWNER TO postgres;
		CREATE SEQUENCE identifiantstation_seq
			INCREMENT BY 1
			NO MAXVALUE
			NO MINVALUE
			CACHE 1;

		ALTER TABLE public.identifiantstation_seq OWNER TO postgres;
		ALTER SEQUENCE identifiantstation_seq OWNED BY station.identifiantstation;
		-- Initialiser la séquence selon le nombre d'INSERT faits à la main dans ce script
		SELECT pg_catalog.setval('identifiantstation_seq', 5, true);


		-------------------------------------------------------
		-- TABLE UTILISER
		-------------------------------------------------------
		CREATE TABLE utiliser (
				utiliser_id integer NOT NULL,
				fk_identifiantvelo integer NOT NULL,
				fk_numero integer NOT NULL,
				dateretrait timestamp with time zone NOT NULL,
				datedepot timestamp with time zone
			);

		ALTER TABLE public.utiliser OWNER TO postgres;
		CREATE SEQUENCE utiliser_id_seq
			INCREMENT BY 1
			NO MAXVALUE
			NO MINVALUE
			CACHE 1;
		ALTER TABLE public.utiliser_id_seq OWNER TO postgres;
		ALTER SEQUENCE utiliser_id_seq OWNED BY utiliser.utiliser_id;
		-- Initialiser la séquence selon le nombre d'INSERT faits à la main dans ce script
		SELECT pg_catalog.setval('utiliser_id_seq', 1, true);


		-------------------------------------------------------
		-- MISE EN PLACE DES SEQUENCES
		-------------------------------------------------------
		ALTER TABLE velo ALTER COLUMN identifiantvelo SET DEFAULT nextval('identifiantvelo_seq'::regclass);
		ALTER TABLE utilisateur ALTER COLUMN numero SET DEFAULT nextval('numero_seq'::regclass);
		ALTER TABLE station ALTER COLUMN identifiantstation SET DEFAULT nextval('identifiantstation_seq'::regclass);
		ALTER TABLE utiliser ALTER COLUMN utiliser_id SET DEFAULT nextval('utiliser_id_seq'::regclass);


		-------------------------------------------------------
		-- MISE EN PLACE DES CLES PRIMAIRES
		-------------------------------------------------------
		ALTER TABLE ONLY velo
			ADD CONSTRAINT identifiantvelo_pkey PRIMARY KEY (identifiantvelo);
		ALTER TABLE ONLY utilisateur
			ADD CONSTRAINT numero_pkey PRIMARY KEY (numero);
		ALTER TABLE ONLY station
			ADD CONSTRAINT identifiantstation_pkey PRIMARY KEY (identifiantstation);
		ALTER TABLE ONLY utiliser
			ADD CONSTRAINT utiliser_id_pkey PRIMARY KEY (utiliser_id);


		-------------------------------------------------------
		-- MISE EN PLACE DES CLES ETRANGERES
		-------------------------------------------------------
		ALTER TABLE ONLY velo
			ADD CONSTRAINT velo_identifiantstation_fk FOREIGN KEY (fk_identifiantstation) REFERENCES station(identifiantstation);
		ALTER TABLE ONLY utiliser
			ADD CONSTRAINT utiliser_identifiantvelo_fk FOREIGN KEY (fk_identifiantvelo) REFERENCES velo(identifiantvelo);
		ALTER TABLE ONLY utiliser
			ADD CONSTRAINT utiliser_numero_fk FOREIGN KEY (fk_numero) REFERENCES utilisateur(numero);

	-- *********** -- *********** -- *********** -- *******
	--				   SCRIPT D'INSERTION
	-- *********** -- *********** -- *********** -- *******

		INSERT INTO utilisateur (numero, code) VALUES (1, 0000);
		INSERT INTO utilisateur (numero, code) VALUES (2, 1111);
		INSERT INTO utilisateur (numero, code) VALUES (3, 2222);
		INSERT INTO utilisateur (numero, code) VALUES (4, 3333);
		INSERT INTO utilisateur (numero, code) VALUES (5, 4444);

		-- Les nombre de retraits et dépôts sont automatiquement remplis à 0 grâce à la structure de création
		INSERT INTO station (identifiantstation, capacite, latitude, longitude) VALUES (1, 20, 43.585909, 1.447364);
		INSERT INTO station (identifiantstation, capacite, latitude, longitude) VALUES (2, 20, 43.583776, 1.443702);
		INSERT INTO station (identifiantstation, capacite, latitude, longitude) VALUES (3, 20, 43.585632, 1.428026);
		INSERT INTO station (identifiantstation, capacite, latitude, longitude) VALUES (4, 20, 43.591544, 1.418569);
		INSERT INTO station (identifiantstation, capacite, latitude, longitude) VALUES (5, 20, 43.587916, 1.424500);

		INSERT INTO velo (identifiantvelo, operationnel, fk_identifiantstation) VALUES (1, TRUE, 1);
		INSERT INTO velo (identifiantvelo, operationnel, fk_identifiantstation) VALUES (2, TRUE, 1);
		INSERT INTO velo (identifiantvelo, operationnel, fk_identifiantstation) VALUES (3, TRUE, 2);
		INSERT INTO velo (identifiantvelo, operationnel, fk_identifiantstation) VALUES (4, TRUE, 3);
		INSERT INTO velo (identifiantvelo, operationnel, fk_identifiantstation) VALUES (5, TRUE, 4);