create function public.log_user() returns trigger
    language plpgsql
as
$$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (id, action, action_time, user_id) VALUES (DEFAULT, 'UPDATE', DEFAULT, OLD.id);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (id, action, action_time, user_id) VALUES (DEFAULT, 'DELETE',DEFAULT, OLD.id);
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$;

create trigger user_add_trigger after insert  on users for each row execute procedure log_user();