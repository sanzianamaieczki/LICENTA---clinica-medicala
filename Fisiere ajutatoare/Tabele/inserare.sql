SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO clinics (clinic_name, clinic_phone, clinic_address) VALUES
    ('Clinica Bucuresti', '0123456789', 'Str. Libertatii, nr. 10, Bucuresti'),
    ('Clinica Cluj', '0234567891', 'Str. Primaverii, nr. 5, Cluj-Napoca'),
    ('Clinica Timisoara', '0345678912', 'Str. Independentei, nr. 15, Timisoara'),
    ('Clinica Iasi', '0456789123', 'Str. Victoriei, nr. 20, Iasi'),
    ('Clinica Constanta', '0567891234', 'Str. Unirii, nr. 25, Constanta'),
    ('Clinica Brasov', '0678912345', 'Str. Republicii, nr. 8, Brasov'),
    ('Clinica Oradea', '0789123456', 'Str. Libertatii, nr. 3, Oradea');

INSERT INTO specializations (specialization_name) VALUES
    ('Cardiologie'),
    ('Pediatrie'),
    ('Dermatologie'),
    ('Ortopedie'),
    ('Oftalmologie'),
    ('Oncologie'),
    ('Neurologie');

INSERT INTO users (email, password, user_role, created_at) VALUES
    ('admin@clinica.ro', 'admin123', 'admin', NOW()),
    ('pacient1@clinica.ro', 'pacient1', 'patient', NOW()),
    ('pacient2@clinica.ro', 'pacient2', 'patient', NOW()),
    ('pacient3@clinica.ro', 'pacient3', 'patient', NOW()),
    ('medic1@clinica.ro', 'medic1', 'doctor', NOW()),
    ('medic2@clinica.ro', 'medic2', 'doctor', NOW()),
    ('medic3@clinica.ro', 'medic3', 'doctor', NOW()),
    ('medic4@clinica.ro', 'medic4', 'doctor', NOW()),
    ('medic5@clinica.ro', 'medic5', 'doctor', NOW()),
    ('asistent1@clinica.ro', 'asistent1', 'assistant', NOW()),
    ('asistent2@clinica.ro', 'asistent2', 'assistant', NOW()),
    ('asistent3@clinica.ro', 'asistent3', 'assistant', NOW()),
    ('asistent4@clinica.ro', 'asistent4', 'assistant', NOW()),
    ('asistent5@clinica.ro', 'asistent5', 'assistant', NOW());

INSERT INTO patients (last_name, first_name, email, phone, national_id, birth_date, created_at, id_user) VALUES
    ('Popescu', 'Ion', 'pacient1@clinica.ro', '0723456789', '1234567890123', '1985-05-15', NOW(), 2),
    ('Ionescu', 'Maria', 'pacient2@clinica.ro', '0734567890', '2345678901234', '1990-08-20', NOW(), 3),
    ('Georgescu', 'Andrei', 'andrei@clinica.ro', '0745678901', '3456789012345', '1978-11-10', NOW(), NULL),
    ('Dumitrescu', 'Ana', 'ana@clinica.ro', '0756789012', '4567890123456', '2000-04-01', NOW(), 4),
    ('Stan', 'Elena', 'elena@clinica.ro', '0767890123', '5678901234567', '1995-07-25', NOW(), NULL),
    ('Mihai', 'Razvan', 'razvan@clinica.ro', '0778901234', '6789012345678', '1982-03-12', NOW(), NULL),
    ('Baciu', 'Florin', 'florin@clinica.ro', '0789012345', '7890123456789', '1976-09-05', NOW(), NULL);

INSERT INTO doctors (last_name, first_name, email, phone, id_specialization, id_clinics, id_user, created_at) VALUES
    ('Popa', 'Mihai', 'medic1@clinica.ro', '0712345678', 1, 1, 5, NOW()),
    ('Marin', 'Ioana', 'medic2@clinica.ro', '0723456789', 2, 2, 6, NOW()),
    ('Tudor', 'Vasile', 'medic3@clinica.ro', '0734567890', 3, 3, 7, NOW()),
    ('Enache', 'Corina', 'medic4@clinica.ro', '0745678901', 4, 4, 8, NOW()),
    ('Preda', 'Alexandru', 'medic5@clinica.ro', '0756789012', 5, 5, 9, NOW());

INSERT INTO assistants (id_user, last_name, first_name, email, phone, id_clinics, created_at) VALUES
    (10, 'Pop', 'Alina', 'asistent1@clinica.ro', '0712345678', 1, NOW()),
    (11, 'Radulescu', 'Dan', 'asistent2@clinica.ro', '0723456789', 2, NOW()),
    (12, 'Simionescu', 'Elena', 'asistent3@clinica.ro', '0734567890', 3, NOW()),
    (13, 'Morar', 'Stefan', 'asistent4@clinica.ro', '0745678901', 4, NOW()),
    (14, 'Ivan', 'Ioana', 'asistent5@clinica.ro', '0756789012', 5, NOW());

INSERT INTO medical_services (id_specialization, service_name, price) VALUES
    (1, 'Consultatie Cardiologie', 200.50),
    (2, 'Consultatie Pediatrie', 150.99),
    (3, 'Consultatie Dermatologie', 180.70),
    (4, 'Consultatie Ortopedie', 220.20),
    (5, 'Consultatie Oftalmologie', 170.30),
    (6, 'Consultatie Oncologie', 250.00),
    (7, 'Consultatie Neurologie', 230.00);

INSERT INTO doctor_schedule (id_doctor, day_of_week, start_time, end_time) VALUES
    (1, 'monday', '09:00:00', '17:00:00'),
    (1, 'tuesday', '09:00:00', '17:00:00'),
    (1, 'friday', '09:00:00', '17:00:00'),
    (2, 'tuesday', '08:00:00', '16:00:00'),
    (2, 'wednesday', '08:00:00', '16:00:00'),
    (2, 'thursday', '08:00:00', '16:00:00'),
    (3, 'monday', '10:00:00', '18:00:00'),
    (3, 'thursday', '10:00:00', '18:00:00'),
    (3, 'friday', '10:00:00', '18:00:00'),
    (4, 'tuesday', '09:00:00', '17:00:00'),
    (4, 'wednesday', '09:00:00', '17:00:00'),
    (4, 'friday', '09:00:00', '17:00:00'),
    (5, 'monday', '08:00:00', '16:00:00'),
    (5, 'wednesday', '08:00:00', '16:00:00'),
    (5, 'friday', '08:00:00', '16:00:00');

INSERT INTO medical_analysis (id_patient, id_specialization, access_code, analysis_date, analysis_status) VALUES
    (1, 1, 'ABC123', NOW(), 'in_progress'),
    (2, 2, 'DEF456', NOW(), 'completed'),
    (3, 3, 'GHI789', NOW(), 'in_progress'),
    (4, 4, 'JKL012', NOW(), 'completed'),
    (5, 5, 'MNO345', NOW(), 'in_progress'),
    (6, 6, 'PQR678', NOW(), 'completed'),
    (7, 7, 'STU910', NOW(), 'in_progress');

INSERT INTO analysis_details (id_analysis, test_name, obtained_value, min_range, max_range, unit) VALUES
    (1, 'Colesterol', 180.00, 150.00, 200.00, 'mg/dL'),
    (1, 'Trigliceride', 100.00, 50.00, 150.00, 'mg/dL'),
    (2, 'Hemoglobina', 13.50, 12.00, 16.00, 'g/dL'),
    (3, 'Glicemie', 110.00, 70.00, 100.00, 'mg/dL'),
    (4, 'Uree', 45.00, 20.00, 50.00, 'mg/dL'),
    (5, 'Creatinina', 1.2, 0.7, 1.5, 'mg/dL'),
    (6, 'Proteine', 7.0, 6.0, 8.0, 'g/dL'),
    (7, 'Albumina', 4.5, 3.5, 5.2, 'g/dL');

INSERT INTO analysis_appointments (id_patient, id_analysis, appointment_date, appointment_time, appointment_status) VALUES
    (1, 1, '2024-10-01', '09:00:00', 'confirmed'),
    (2, 2, '2024-10-02', '10:30:00', 'pending'),
    (3, 3, '2024-10-03', '11:00:00', 'canceled'),
    (4, 4, '2024-10-04', '09:00:00', 'confirmed'),
    (5, 5, '2024-10-05', '08:30:00', 'confirmed'),
    (6, 6, '2024-10-06', '10:00:00', 'pending'),
    (7, 7, '2024-10-07', '11:30:00', 'pending');

INSERT INTO consultation_appointments (id_patient, id_doctor, id_medical_service, appointment_date, appointment_time, appointment_status) VALUES
    (1, 1, 1, '2024-10-08', '09:00:00', 'confirmed'),
    (2, 2, 2, '2024-10-09', '10:30:00', 'pending'),
    (3, 3, 3, '2024-10-10', '11:00:00', 'canceled'),
    (4, 4, 4, '2024-10-11', '09:00:00', 'confirmed'),
    (5, 5, 5, '2024-10-12', '08:30:00', 'confirmed'),
    (6, 1, 6, '2024-10-13', '09:30:00', 'pending'),
    (7, 2, 7, '2024-10-14', '10:00:00', 'pending');

INSERT INTO patient_doctor (id_patient, id_doctor) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 1),
    (5, 5),
    (6, 3);

INSERT INTO notifications (id_consultation_appointment, id_analysis_appointment, id_patient, notification_date, notification_type) VALUES
    (1, NULL, 1, NOW(), 'reminder'),
    (2, NULL, 2, NOW(), 'confirmation'),
    (NULL, 3, 3, NOW(), 'canceled'),
    (4, NULL, 4, NOW(), 'reminder'),
    (NULL, 5, 5, NOW(), 'confirmation'),
    (6, NULL, 6, NOW(), 'canceled'),
    (7, NULL, 7, NOW(), 'reminder');

INSERT INTO medical_letters (id_patient, id_doctor, id_consultation_appointment, id_medical_service, id_specialization, diagnosis, content, created_at) VALUES
    (1, 1, 1, 1, 1, 'Hipertensiune arteriala', 'Pacientul necesita tratament cu medicamente antihipertensive.', NOW()),
    (2, 2, 2, 2, 2, 'Infectie respiratorie', 'Pacientul necesita antibiotice si odihna.', NOW()),
    (3, 3, 3, 3, 3, 'Alergie de piele', 'Pacientul necesita creme antihistaminice.', NOW()),
    (4, 4, 4, 4, 4, 'Fractura de mana', 'Pacientul necesita ghips pentru recuperare.', NOW()),
    (5, 5, 5, 5, 5, 'Miopie', 'Pacientul necesita ochelari de vedere.', NOW()),
    (1, 1, 6, 6, 6, 'Cancer pulmonar', 'Pacientul necesita chimioterapie.', NOW()),
    (2, 2, 7, 7, 7, 'Migrena cronica', 'Pacientul necesita tratament neurologic.', NOW());


SET FOREIGN_KEY_CHECKS = 1;
