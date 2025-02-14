create or replace function public.get_all_books()
    returns TABLE(id bigint, title character, author character, publicationyear integer)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT  books.id, books.title, books.author, books.publication_year
        FROM books
        ORDER BY books.id;
END;
$$;

alter function public.get_all_books() owner to postgres;

create or replace function public.get_last_registered_user() returns character
    language plpgsql
as
$$
DECLARE
    last_user character;
BEGIN
    SELECT users.name FROM users ORDER BY users.id DESC LIMIT 1;

    IF last_user IS NULL THEN
        RETURN  'No users registered';
    END IF;

    RETURN last_user;
END;
$$;

alter function public.get_last_registered_user() owner to postgres;

create or replace function public.get_last_borrowed_book()
    returns TABLE(book_title character, publication_year integer, username character)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT books.title, books.publication_year, users.name
        FROM reservations
                 INNER JOIN books ON reservations.book_id = books.id
                 INNER JOIN users ON reservations.user_id = users.id
        ORDER BY reservations.reservation_date DESC
        LIMIT 1;

    IF NOT FOUND THEN
        RETURN QUERY SELECT 'No books borrowed'::CHARACTER,NULL,NULL;
    END IF;

END ;
$$;

alter function public.get_last_borrowed_book() owner to postgres;

