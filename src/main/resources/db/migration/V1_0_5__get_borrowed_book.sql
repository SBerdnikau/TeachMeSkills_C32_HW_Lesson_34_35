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