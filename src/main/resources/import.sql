-- CREATE SEQUENCE per_seq;

INSERT INTO adress (city, street) VALUES ('Surgut', 'Komsomolsky');
INSERT INTO adress (city, street) VALUES ('Surgut', 'Energetikov');
INSERT INTO adress (city, street) VALUES ('Surgut', 'Mira');
INSERT INTO adress (city, street) VALUES ('Nefteugansk', 'Neftyanikov');
INSERT INTO adress (city, street) VALUES ('Nefteugansk', 'Parkovaya');

INSERT INTO person (name, birthday, adress_id) VALUES ('Dmitry', '1981-11-18', 1);
INSERT INTO person (name, birthday, adress_id) VALUES ('Catherine', '2000-05-07', 2);
INSERT INTO person (name, birthday, adress_id) VALUES ('Ivan', '1985-09-01', 4);
INSERT INTO person (name, birthday, adress_id) VALUES ('Maxim', '1978-07-27', 3);

