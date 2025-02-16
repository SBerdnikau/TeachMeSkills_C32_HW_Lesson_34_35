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