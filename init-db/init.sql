create sequence public.player_id_seq;

alter sequence public.player_id_seq owner to postgres;

create sequence public.club_id_seq;

alter sequence public.club_id_seq owner to postgres;

create sequence public.brand_id_seq;

alter sequence public.brand_id_seq owner to postgres;

create sequence public.user_id_seq;

alter sequence public.user_id_seq owner to postgres;

create table public.club
(
    name varchar not null
        constraint club_pk_2
            unique,
    id   bigserial
        constraint club_pk
            primary key
);

alter table public.club
    owner to postgres;

create table public.player
(
    id      bigserial
        constraint player_pk
            primary key,
    name    varchar not null
        constraint player_pk_2
            unique,
    price   integer,
    club_id integer
        constraint player_fk
            references public.club
            on update cascade on delete set null
);

alter table public.player
    owner to postgres;

create table public.brand
(
    id   bigserial
        constraint brand_pk
            primary key,
    name varchar not null
        constraint brand_pk_2
            unique
);

alter table public.brand
    owner to postgres;

create table public.player_brand
(
    brand_id  bigint
        constraint brand_fk
            references public.brand
            on update cascade on delete set null,
    player_id bigint
        constraint player_fk
            references public.player
            on update cascade on delete set null
);

alter table public.player_brand
    owner to postgres;

create table public.my_user
(
    password   varchar                                         not null,
    username   varchar                                         not null
        constraint my_user_uk
            unique,
    role       varchar                                         not null,
    created_at timestamp                                       not null,
    updated_at timestamp                                       not null,
    id         bigint default nextval('user_id_seq'::regclass) not null
        constraint my_user_pk
            primary key,
    firstname  varchar                                         not null,
    lastname   varchar                                         not null
);

alter table public.my_user
    owner to postgres;

alter sequence public.player_id_seq owned by public.player.id;
alter sequence public.club_id_seq owned by public.club.id;
alter sequence public.brand_id_seq owned by public.brand.id;
alter sequence public.user_id_seq owned by public.my_user.id;

