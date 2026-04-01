-- Table: public.tipo_evento

-- DROP TABLE IF EXISTS public.tipo_evento;

CREATE TABLE IF NOT EXISTS public.tipo_evento
(
    id_tipo_evento integer NOT NULL DEFAULT nextval('tipo_evento_id_tipo_evento_seq'::regclass),
    nombre character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipo_evento_pkey PRIMARY KEY (id_tipo_evento),
    CONSTRAINT tipo_evento_nombre_key UNIQUE (nombre)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tipo_evento
    OWNER to postgres;