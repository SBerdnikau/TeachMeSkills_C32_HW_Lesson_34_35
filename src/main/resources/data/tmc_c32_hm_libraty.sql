-- auto-generated definition

create database tmc_c32_hm_library
    with owner postgres;

create table public.books
(
    id               bigserial
        constraint books_pk
            primary key,
    title            varchar(255) not null,
    author           varchar(255) not null,
    publication_year integer
);

alter table public.books
    owner to postgres;

create table public.users
(
    id    bigserial
        constraint users_pk
            primary key,
    name  varchar(255) not null,
    email varchar(255) not null
);

alter table public.users
    owner to postgres;

create table public.logs
(
    id          bigserial
        constraint logs_pk
            primary key,
    action      varchar(255),
    action_time timestamp default CURRENT_TIMESTAMP,
    user_id     bigint not null
        constraint user_id
            references public.users
);

alter table public.logs
    owner to postgres;

create table public.reservations
(
    id               bigserial
        constraint reservations_pk
            primary key,
    book_id          bigint not null
        constraint book_id
            references public.books
            on update cascade on delete cascade,
    user_id          bigint not null
        constraint user_id
            references public.users
            on update cascade on delete cascade,
    reservation_date date default CURRENT_DATE,
    return_date      date   not null
);

alter table public.reservations
    owner to postgres;

create function public.get_last_registered_user() returns character varying
    language plpgsql
as
$$
DECLARE
    last_user VARCHAR;
BEGIN
    SELECT name INTO last_user
    FROM users
    ORDER BY id DESC
    LIMIT 1;

    IF last_user IS NULL THEN
        RETURN 'No users registered';
    END IF;

    RETURN last_user;
END;
$$;

alter function public.get_last_registered_user() owner to postgres;

create function public.get_last_borrowed_book()
    returns TABLE(book_title character varying, publication_year integer, username character varying)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT books.title, books.publication_year, users.name
        FROM reservations
                 JOIN books  ON reservations.book_id = books.id
                 JOIN users  ON reservations.id = users.id
        ORDER BY reservations.reservation_date DESC
        LIMIT 1;

    IF NOT FOUND THEN
        RETURN QUERY SELECT 'No books borrowed'::VARCHAR, NULL, NULL;
    END IF;
END;
$$;

alter function public.get_last_borrowed_book() owner to postgres;

create function public.get_all_books()
    returns TABLE(id bigint, title character varying, author character varying, publication_year integer)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT books.id, books.title, books.author, books.publication_year
        FROM books
        ORDER BY books.id;
END;
$$;

alter function public.get_all_books() owner to postgres;

create trigger user_add_trigger after insert  on users for each row execute procedure log_user();

create function log_user() returns trigger
    language plpgsql
as
$$
BEGIN
        INSERT INTO logs(id, action, action_time, user_id) VALUES (DEFAULT, 'Added user id= ' || NEW.id, DEFAULT,  NEW.id);
        RETURN NEW;
END;
$$;