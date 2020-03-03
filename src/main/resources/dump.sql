--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Ukrainian_Ukraine.1252' LC_CTYPE = 'Ukrainian_Ukraine.1252';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: check_results; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.check_results (
    id bigint NOT NULL,
    url text,
    status character varying(255),
    response_code integer,
    response_size bigint,
    details text,
    monitored boolean,
    last_check timestamp without time zone,
    duration bigint
);


ALTER TABLE public.check_results OWNER TO postgres;

--
-- Name: configs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configs (
    id integer NOT NULL,
    querying_interval bigint NOT NULL,
    response_time_ok bigint NOT NULL,
    response_time_warning bigint NOT NULL,
    response_time_critical integer NOT NULL,
    expected_http_code integer NOT NULL,
    min_expected_response_size bigint NOT NULL,
    max_expected_response_size bigint NOT NULL,
    monitored boolean DEFAULT true NOT NULL,
    url text NOT NULL
);


ALTER TABLE public.configs OWNER TO postgres;

--
-- Name: configs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.configs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.configs_id_seq OWNER TO postgres;

--
-- Name: configs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.configs_id_seq OWNED BY public.configs.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: configs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configs ALTER COLUMN id SET DEFAULT nextval('public.configs_id_seq'::regclass);


--
-- Data for Name: check_results; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.check_results (id, url, status, response_code, response_size, details, monitored, last_check, duration) FROM stdin;
\.


--
-- Data for Name: configs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.configs (id, querying_interval, response_time_ok, response_time_warning, response_time_critical, expected_http_code, min_expected_response_size, max_expected_response_size, monitored, url) FROM stdin;
266	2000	2000	3000	4000	200	1000	100000	t	https://www.wikipedia.org/
270	2000	5000	6000	10000	200	1000	100000	t	https://www.wikipedia.org/
\.


--
-- Name: configs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.configs_id_seq', 305, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 4, true);


--
-- Name: check_results check_results_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.check_results
    ADD CONSTRAINT check_results_pk PRIMARY KEY (id);


--
-- Name: configs configs_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configs
    ADD CONSTRAINT configs_pk PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

