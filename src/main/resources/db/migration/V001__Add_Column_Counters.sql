ALTER TABLE artists ADD COLUMN total_likes bigint NOT NULL DEFAULT 0;
ALTER TABLE artists ADD COLUMN total_views bigint NOT NULL DEFAULT 0;
ALTER TABLE artists ADD COLUMN total_shares bigint NOT NULL DEFAULT 0;

ALTER TABLE tracks ADD COLUMN total_likes bigint NOT NULL DEFAULT 0;
ALTER TABLE tracks ADD COLUMN total_views bigint NOT NULL DEFAULT 0;
ALTER TABLE tracks ADD COLUMN total_shares bigint NOT NULL DEFAULT 0;
ALTER TABLE tracks ADD COLUMN total_listens bigint NOT NULL DEFAULT 0;
