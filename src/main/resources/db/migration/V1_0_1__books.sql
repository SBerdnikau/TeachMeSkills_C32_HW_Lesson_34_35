create table public.books
(
    id               bigserial
        constraint books_pk
            primary key,
    title            varchar(255) not null,
    author           varchar(255) not null,
    publication_year integer
);