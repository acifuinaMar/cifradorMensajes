-- Table: public.token

-- DROP TABLE IF EXISTS public.token;

CREATE TABLE IF NOT EXISTS public.token
(
    id_token integer NOT NULL DEFAULT nextval('token_id_token_seq'::regclass),
    valor_token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fecha_expiracion timestamp without time zone,
    usado boolean DEFAULT false,
    id_mensaje integer,
    fecha_uso timestamp without time zone,
    eliminado boolean DEFAULT false,
    fecha_eliminacion timestamp without time zone,
    CONSTRAINT token_pkey PRIMARY KEY (id_token),
    CONSTRAINT token_id_mensaje_key UNIQUE (id_mensaje),
    CONSTRAINT token_valor_token_key UNIQUE (valor_token),
    CONSTRAINT token_id_mensaje_fkey FOREIGN KEY (id_mensaje)
        REFERENCES public.mensaje (id_mensaje) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token
    OWNER to postgres;