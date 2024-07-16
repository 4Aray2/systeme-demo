CREATE TABLE public.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    price double precision NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;