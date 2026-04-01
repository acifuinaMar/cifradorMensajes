-- Table: public.mensaje

-- DROP TABLE IF EXISTS public.mensaje;

CREATE TABLE IF NOT EXISTS public.mensaje
(
    id_mensaje integer NOT NULL DEFAULT nextval('mensaje_id_mensaje_seq'::regclass),
    texto_cifrado text COLLATE pg_catalog."default" NOT NULL,
    fecha_creacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    fecha_lectura timestamp without time zone,
    id_usuario integer NOT NULL,
    id_estado integer NOT NULL,
    clave_privada text COLLATE pg_catalog."default",
    CONSTRAINT mensaje_pkey PRIMARY KEY (id_mensaje),
    CONSTRAINT mensaje_id_estado_fkey FOREIGN KEY (id_estado)
        REFERENCES public.estado_mensaje (id_estado) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT mensaje_id_usuario_fkey FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id_usuario) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.mensaje
    OWNER to postgres;