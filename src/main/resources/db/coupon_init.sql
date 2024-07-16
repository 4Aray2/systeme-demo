CREATE TABLE public.coupon
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    code character varying(20) NOT NULL,
    discount numeric NOT NULL,
    is_percentage boolean NOT NULL,
    is_active boolean NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.coupon
    OWNER to postgres;