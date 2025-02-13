create table if not exists public.books
(
    id               bigserial
        constraint books_pk
            primary key,
    title            char not null,
    author           char not null,
    publication_year integer
);

alter table public.books
    owner to postgres;

create table if not exists public.users
(
    id    bigserial
        constraint users_pk
            primary key,
    name  char not null,
    email char not null
);

alter table public.users
    owner to postgres;

create table if not exists public.logs
(
    id          bigserial
        constraint logs_pk
            primary key,
    action      char,
    action_time timestamp default CURRENT_TIMESTAMP,
    user_id     integer not null
        constraint user_id
            references public.users
);

alter table public.logs
    owner to postgres;

create table if not exists public.reservations
(
    id               bigserial
        constraint reservations_pk
            primary key,
    book_id          bigint not null
        constraint book_id
            references public.books,
    user_id          bigint not null
        constraint user_id
            references public.users,
    reservation_date date default CURRENT_DATE,
    return_date      date   not null
);

alter table public.reservations
    owner to postgres;

