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