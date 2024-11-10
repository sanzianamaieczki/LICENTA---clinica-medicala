SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO clinica (nume_clinica, telefon_clinica, adresa_clinica) VALUES
    ('Clinica Bucuresti', '0123456789', 'Str. Libertatii, nr. 10, Bucuresti'),
    ('Clinica Cluj', '0234567891', 'Str. Primaverii, nr. 5, Cluj-Napoca'),
    ('Clinica Timisoara', '0345678912', 'Str. Independentei, nr. 15, Timisoara'),
    ('Clinica Iasi', '0456789123', 'Str. Victoriei, nr. 20, Iasi'),
    ('Clinica Constanta', '0567891234', 'Str. Unirii, nr. 25, Constanta'),
    ('Clinica Brasov', '0678912345', 'Str. Republicii, nr. 8, Brasov'),
    ('Clinica Oradea', '0789123456', 'Str. Libertatii, nr. 3, Oradea');

INSERT INTO specializare (nume_specializare) VALUES
    ('Cardiologie'),
    ('Pediatrie'),
    ('Dermatologie'),
    ('Ortopedie'),
    ('Oftalmologie'),
    ('Oncologie'),
    ('Neurologie');

INSERT INTO utilizator (email, parola, rol, data_crearii) VALUES
    ('admin@clinica.ro', 'admin123', 'admin', NOW()),
    ('pacient1@clinica.ro', 'pacient1', 'pacient', NOW()),
    ('pacient2@clinica.ro', 'pacient2', 'pacient', NOW()),
    ('pacient3@clinica.ro', 'pacient3', 'pacient', NOW()),
    ('medic1@clinica.ro', 'medic1', 'medic', NOW()),
    ('medic2@clinica.ro', 'medic2', 'medic', NOW()),
    ('medic3@clinica.ro', 'medic3', 'medic', NOW()),
    ('medic4@clinica.ro', 'medic4', 'medic', NOW()),
    ('medic5@clinica.ro', 'medic5', 'medic', NOW()),
    ('asistent1@clinica.ro', 'asistent1', 'asistent', NOW()),
    ('asistent2@clinica.ro', 'asistent2', 'asistent', NOW()),
    ('asistent3@clinica.ro', 'asistent3', 'asistent', NOW()),
    ('asistent4@clinica.ro', 'asistent4', 'asistent', NOW()),
    ('asistent5@clinica.ro', 'asistent5', 'asistent', NOW());

INSERT INTO pacient (nume, prenume, email, telefon, cnp, data_nasterii, data_crearii, id_utilizator) VALUES
    ('Popescu', 'Ion', 'pacient1@clinica.ro', '0723456789', '1234567890123', '1985-05-15', NOW(), 2),
    ('Ionescu', 'Maria', 'pacient2@clinica.ro', '0734567890', '2345678901234', '1990-08-20', NOW(), 3),
    ('Georgescu', 'Andrei', 'andrei@clinica.ro', '0745678901', '3456789012345', '1978-11-10', NOW(), NULL),
    ('Dumitrescu', 'Ana', 'ana@clinica.ro', '0756789012', '4567890123456', '2000-04-01', NOW(), 4),
    ('Stan', 'Elena', 'elena@clinica.ro', '0767890123', '5678901234567', '1995-07-25', NOW(), NULL),
    ('Mihai', 'Razvan', 'razvan@clinica.ro', '0778901234', '6789012345678', '1982-03-12', NOW(), NULL),
    ('Baciu', 'Florin', 'florin@clinica.ro', '0789012345', '7890123456789', '1976-09-05', NOW(), NULL);

INSERT INTO medic (nume, prenume, email, telefon, id_specializare, id_clinica, id_utilizator, data_crearii) VALUES
    ('Popa', 'Mihai', 'medic1@clinica.ro', '0712345678', 1, 1, 5, NOW()),
    ('Marin', 'Ioana', 'medic2@clinica.ro', '0723456789', 2, 2, 6, NOW()),
    ('Tudor', 'Vasile', 'medic3@clinica.ro', '0734567890', 3, 3, 7, NOW()),
    ('Enache', 'Corina', 'medic4@clinica.ro', '0745678901', 4, 4, 8, NOW()),
    ('Preda', 'Alexandru', 'medic5@clinica.ro', '0756789012', 5, 5, 9, NOW());

INSERT INTO asistent (id_utilizator, nume, prenume, email, telefon, id_clinica, data_crearii) VALUES
    (10, 'Pop', 'Alina', 'asistent1@clinica.ro', '0712345678', 1, NOW()),
    (11, 'Radulescu', 'Dan', 'asistent2@clinica.ro', '0723456789', 2, NOW()),
    (12, 'Simionescu', 'Elena', 'asistent3@clinica.ro', '0734567890', 3, NOW()),
    (13, 'Morar', 'Stefan', 'asistent4@clinica.ro', '0745678901', 4, NOW()),
    (14, 'Ivan', 'Ioana', 'asistent5@clinica.ro', '0756789012', 5, NOW());

INSERT INTO serviciu_medical (id_specializare, nume, pret) VALUES
    (1, 'Consultatie Cardiologie', 200.50),
    (2, 'Consultatie Pediatrie', 150.99),
    (3, 'Consultatie Dermatologie', 180.70),
    (4, 'Consultatie Ortopedie', 220.20),
    (5, 'Consultatie Oftalmologie', 170.30),
    (6, 'Consultatie Oncologie', 250.00),
    (7, 'Consultatie Neurologie', 230.00);

INSERT INTO program_doctor (id_medic, ziua_saptamanii, ora_inceput, ora_sfarsit) VALUES
    (1, 'luni', '09:00:00', '17:00:00'),
    (1, 'marti', '09:00:00', '17:00:00'),
    (1, 'vineri', '09:00:00', '17:00:00'),
    (2, 'marti', '08:00:00', '16:00:00'),
    (2, 'miercuri', '08:00:00', '16:00:00'),
    (2, 'joi', '08:00:00', '16:00:00'),
    (3, 'luni', '10:00:00', '18:00:00'),
    (3, 'joi', '10:00:00', '18:00:00'),
    (3, 'vineri', '10:00:00', '18:00:00'),
    (4, 'marti', '09:00:00', '17:00:00'),
    (4, 'miercuri', '09:00:00', '17:00:00'),
    (4, 'vineri', '09:00:00', '17:00:00'),
    (5, 'luni', '08:00:00', '16:00:00'),
    (5, 'miercuri', '08:00:00', '16:00:00'),
    (5, 'vineri', '08:00:00', '16:00:00');

INSERT INTO analize (id_pacient, id_serviciu_medical, cod_acces, data_analiza, status) VALUES
    (1, 1, 'ABC123', NOW(), 'in curs'),
    (2, 2, 'DEF456', NOW(), 'finalizata'),
    (3, 3, 'GHI789', NOW(), 'in curs'),
    (4, 4, 'JKL012', NOW(), 'finalizata'),
    (5, 5, 'MNO345', NOW(), 'in curs'),
    (6, 6, 'PQR678', NOW(), 'finalizata'),
    (7, 7, 'STU910', NOW(), 'in curs');

INSERT INTO detalii_analiza (id_analiza, nume_test, valoare_obtinuta, interval_minim, interval_maxim, unitate_masura) VALUES
    (1, 'Colesterol', 180.00, 150.00, 200.00, 'mg/dL'),
    (1, 'Trigliceride', 100.00, 50.00, 150.00, 'mg/dL'),
    (2, 'Hemoglobina', 13.50, 12.00, 16.00, 'g/dL'),
    (3, 'Glicemie', 110.00, 70.00, 100.00, 'mg/dL'),
    (4, 'Uree', 45.00, 20.00, 50.00, 'mg/dL'),
    (5, 'Creatinina', 1.2, 0.7, 1.5, 'mg/dL'),
    (6, 'Proteine', 7.0, 6.0, 8.0, 'g/dL'),
    (7, 'Albumina', 4.5, 3.5, 5.2, 'g/dL');

INSERT INTO programare_analiza (id_pacient, id_analiza, data_programarii, ora_programarii, status_programare) VALUES
    (1, 1, '2024-10-01', '09:00:00', 'confirmata'),
    (2, 2, '2024-10-02', '10:30:00', 'in_asteptare'),
    (3, 3, '2024-10-03', '11:00:00', 'anulata'),
    (4, 4, '2024-10-04', '09:00:00', 'confirmata'),
    (5, 5, '2024-10-05', '08:30:00', 'confirmata'),
    (6, 6, '2024-10-06', '10:00:00', 'in_asteptare'),
    (7, 7, '2024-10-07', '11:30:00', 'in_asteptare');

INSERT INTO programare_consultatie (id_pacient, id_medic, id_serviciu_medical, data_programarii, ora_programarii, status_programare) VALUES
    (1, 1, 1, '2024-10-08', '09:00:00', 'confirmata'),
    (2, 2, 2, '2024-10-09', '10:30:00', 'in_asteptare'),
    (3, 3, 3, '2024-10-10', '11:00:00', 'anulata'),
    (4, 4, 4, '2024-10-11', '09:00:00', 'confirmata'),
    (5, 5, 5, '2024-10-12', '08:30:00', 'confirmata'),
    (6, 1, 6, '2024-10-13', '09:30:00', 'in_asteptare'),
    (7, 2, 7, '2024-10-14', '10:00:00', 'in_asteptare');

INSERT INTO pacient_medic (id_pacient, id_medic) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 1),
    (5, 5),
    (6, 3);

INSERT INTO notificare (id_programare_consultatie, id_programare_analiza, id_pacient, data_notificare, tip_notificare) VALUES
    (1, NULL, 1, NOW(), 'reamintire'),
    (2, NULL, 2, NOW(), 'confirmare'),
    (NULL, 3, 3, NOW(), 'anulata'),
    (4, NULL, 4, NOW(), 'reamintire'),
    (NULL, 5, 5, NOW(), 'confirmare'),
    (6, NULL, 6, NOW(), 'anulata'),
    (7, NULL, 7, NOW(), 'reamintire');

INSERT INTO scrisoare_medicala (id_pacient, id_medic, id_programare_consultatie, id_serviciu_medical, id_specializare, diagnostic, continut, data_crearii) VALUES
    (1, 1, 1, 1, 1, 'Hipertensiune arteriala', 'Pacientul necesita tratament cu medicamente antihipertensive.', NOW()),
    (2, 2, 2, 2, 2, 'Infectie respiratorie', 'Pacientul necesita antibiotice si odihna.', NOW()),
    (3, 3, 3, 3, 3, 'Alergie de piele', 'Pacientul necesita creme antihistaminice.', NOW()),
    (4, 4, 4, 4, 4, 'Fractura de mana', 'Pacientul necesita ghips pentru recuperare.', NOW()),
    (5, 5, 5, 5, 5, 'Miopie', 'Pacientul necesita ochelari de vedere.', NOW()),
    (1, 1, 6, 6, 6, 'Cancer pulmonar', 'Pacientul necesita chimioterapie.', NOW()),
    (2, 2, 7, 7, 7, 'Migrena cronica', 'Pacientul necesita tratament neurologic.', NOW());


SET FOREIGN_KEY_CHECKS = 1;
