-- Table: public.estado_mensaje

-- DROP TABLE IF EXISTS public.estado_mensaje;

CREATE TABLE IF NOT EXISTS public.estado_mensaje
(
    id_estado integer NOT NULL DEFAULT nextval('estado_mensaje_id_estado_seq'::regclass),
    nombre character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT estado_mensaje_pkey PRIMARY KEY (id_estado),
    CONSTRAINT estado_mensaje_nombre_key UNIQUE (nombre)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.estado_mensaje
    OWNER to postgres;