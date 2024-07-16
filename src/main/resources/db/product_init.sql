CREATE TABLE public.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    price double precision NOT NULL,
    is_in_stock boolean NOT NULL DEFAULT false
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;