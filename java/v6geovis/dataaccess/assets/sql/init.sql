


create table grid_data(
      id uuid default uuid_generate_v4() primary key,
      meta_id uuid not null,
      chuck_idx int,
      datas double precision[]
);