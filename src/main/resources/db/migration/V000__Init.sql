CREATE TABLE artists (
	artist_id uuid NOT NULL,
	id bpchar(16) NOT NULL,
	urn varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	is_verified bool DEFAULT false NOT NULL,
	is_public bool DEFAULT false NOT NULL,
	real_name varchar(255) NULL,
	birth_date date NULL,
	biography varchar(250) NULL,
	description text NULL,
	nationality varchar(255) NULL,
	thumbnail varchar(255) NULL,
	background varchar(255) NULL,
	ref_code varchar(100) NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	total_likes int8 DEFAULT 0 NOT NULL,
	total_views int8 DEFAULT 0 NOT NULL,
	total_shares int8 DEFAULT 0 NOT NULL
);

CREATE TABLE tracks (
	track_id uuid NOT NULL,
	id bpchar(16) NOT NULL,
	urn varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	is_public bool DEFAULT false NOT NULL,
	description text NULL,
	released_date varchar(10) NULL,
	duration_sec int4 DEFAULT 0 NOT NULL,
	is_playable bool DEFAULT false NOT NULL,
	thumbnail varchar(255) NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	total_likes int8 DEFAULT 0 NOT NULL,
	total_views int8 DEFAULT 0 NOT NULL,
	total_shares int8 DEFAULT 0 NOT NULL,
	total_listens int8 DEFAULT 0 NOT NULL
);

CREATE TABLE track_artist (
	id uuid NOT NULL,
	track_id uuid NOT NULL,
	artist_id uuid NOT NULL,
	is_active bool DEFAULT true NOT NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL
);
