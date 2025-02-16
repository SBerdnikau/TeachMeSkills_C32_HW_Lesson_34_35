create function log_user() returns trigger
    language plpgsql
as
$$
BEGIN
    INSERT INTO logs(id, action, action_time, user_id) VALUES (DEFAULT, 'Added user id= ' || NEW.id, DEFAULT,  NEW.id);
    RETURN NEW;
END;
$$;

create trigger user_add_trigger after insert  on users for each row execute procedure log_user();