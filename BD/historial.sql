-- Table: public.historial

-- DROP TABLE IF EXISTS public.historial;

CREATE TABLE IF NOT EXISTS public.historial
(
    id_historial integer NOT NULL DEFAULT nextval('historial_id_historial_seq'::regclass),
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    descripcion text COLLATE pg_catalog."default",
    id_mensaje integer,
    id_tipo_evento integer,
    id_usuario integer,
    CONSTRAINT historial_pkey PRIMARY KEY (id_historial),
    CONSTRAINT historial_id_mensaje_fkey FOREIGN KEY (id_mensaje)
        REFERENCES public.mensaje (id_mensaje) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT historial_id_tipo_evento_fkey FOREIGN KEY (id_tipo_evento)
        REFERENCES public.tipo_evento (id_tipo_evento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.historial
    OWNER to postgres;