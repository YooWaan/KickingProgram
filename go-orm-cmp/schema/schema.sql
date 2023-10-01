
CREATE TYPE geokind AS ENUM ('area', 'country', 'city');

CREATE TABLE IF NOT EXISTS geo (
  ID serial NOT NULL,
  NAME varchar NOT NULL,
  MP geometry(MULTIPOLYGON, 4326) NOT NULL,
  Kind geokind NOT NULL,
  PRIMARY KEY (ID)
);
