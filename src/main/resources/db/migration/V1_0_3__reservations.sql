create table public.reservations
(
    id  bigserial
        constraint reservations_pk
            primary key,
    book_id   bigint not null
        constraint book_id
            references public.books
            on update cascade on delete cascade,
    user_id bigint not null
        constraint user_id
            references public.users
            on update cascade on delete cascade,
    reservation_date date default CURRENT_DATE,
    return_date      date   not null
);