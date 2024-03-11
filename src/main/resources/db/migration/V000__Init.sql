CREATE TABLE IF NOT EXISTS artists (
	artist_id uuid NOT NULL,
	id bpchar(16) NULL,
	urn varchar(100) NULL,
	"name" varchar(255) NULL,
	is_verified bool NOT NULL,
	is_public bool NOT NULL,
	real_name varchar(255) NULL,
	birth_date date NULL,
	biography varchar(250) NULL,
	description text NULL,
	nationality varchar(255) NULL,
	thumbnail varchar(255) NULL,
	background varchar(255) NULL,
	ref_code varchar(100) NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS track_artist (
	id uuid NOT NULL,
	track_id uuid NOT NULL,
	artist_id uuid NOT NULL,
	is_active bool NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS tracks (
	track_id uuid NOT NULL,
	id bpchar(16) NULL,
	urn varchar(100) NULL,
	"name" varchar(255) NULL,
	is_public bool NOT NULL,
	description text NULL,
	released_date varchar(10) NULL,
	duration_sec int4 NOT NULL,
	is_playable bool NOT NULL,
	thumbnail varchar(255) NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL
);