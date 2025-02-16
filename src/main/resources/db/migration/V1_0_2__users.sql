create table public.users
(
    id    bigserial
        constraint users_pk
            primary key,
    name  varchar(255) not null,
    email varchar(255) not null
);

create trigger user_update_trigger after update on public.users for each row
execute procedure public.log_user();

create trigger user_delete_trigger after delete on public.users for each row
execute procedure public.log_user();