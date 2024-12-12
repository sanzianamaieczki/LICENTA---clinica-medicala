INSERT INTO `clinics` (clinic_name, clinic_phone, clinic_address, created_at) VALUES
('Clinica București', '0211234567', 'Str. Libertății, nr. 10, București', NOW()),
('Clinica Cluj', '0264123456', 'Str. Primăverii, nr. 5, Cluj-Napoca', NOW()),
('Clinica Timișoara', '0256123456', 'Str. Independenței, nr. 15, Timișoara', NOW());

INSERT INTO `patients` (last_name, first_name, email, phone, national_id, birth_date, created_at) VALUES
('Popescu', 'Ion', 'ion.popescu@example.com', '0720123456', '1234567890123', '1980-05-15', NOW()),
('Ionescu', 'Maria', 'maria.ionescu@example.com', '0721123456', '2234567890123', '1990-06-25', NOW()),
('Georgescu', 'Vasile', 'vasile.georgescu@example.com', '0722123456', '3234567890123', '1975-12-05', NOW());

INSERT INTO `specializations` (specialization_name) VALUES
('Cardiologie'),
('Dermatologie'),
('Pediatrie');

INSERT INTO `users` (email, password, user_role, created_at) VALUES
('admin@example.com', 'admin123', 'admin', NOW()),
('doctor1@example.com', 'doctor123', 'doctor', NOW()),
('assistant@example.com', 'assistant123', 'assistant', NOW()),
('patient1@example.com', 'patient123', 'patient', NOW());

INSERT INTO `doctors` (last_name, first_name, email, phone, id_specialization, id_clinic, created_at) VALUES
('Pop', 'Ioan', 'ioan.pop@clinica.ro', '0730123456', 1, 1, NOW()),
('Mihai', 'Andrei', 'andrei.mihai@clinica.ro', '0731123456', 2, 2, NOW()),
('Radu', 'Elena', 'elena.radu@clinica.ro', '0732123456', 3, 3, NOW());

INSERT INTO `medical_services` (id_doctor, service_name, price, medical_service_type, created_at) VALUES
(1, 'Consultatie Cardiologie', 250.00, 'consultation', NOW()),
(2, 'Consultatie Dermatologie', 200.00, 'consultation', NOW()),
(3, 'Analize Pediatrie', 300.00, 'analysis', NOW());

INSERT INTO `patient_doctor` (id_patient, id_doctor) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO `appointments` (id_patient, id_medical_service, appointment_date, appointment_time, appointment_status, created_at) VALUES
(1, 1, '2024-12-10', '10:00:00', 'confirmed', NOW()),
(2, 2, '2024-12-11', '11:00:00', 'pending', NOW()),
(3, 3, '2024-12-12', '12:00:00', 'canceled', NOW());

INSERT INTO `doctor_schedule` (id_doctor, day_of_week, start_time, end_time) VALUES
(1, 'monday', '09:00:00', '17:00:00'),
(2, 'tuesday', '09:00:00', '17:00:00'),
(3, 'wednesday', '09:00:00', '17:00:00');

INSERT INTO `medical_analysis` (id_patient, id_medical_service, access_code, analysis_date, analysis_status, created_at) VALUES
(1, 3, 'CODE12345', NOW(), 'in_progress', NOW()),
(2, 3, 'CODE67890', NOW(), 'completed', NOW());

INSERT INTO `medical_letters` (id_appointment, diagnosis, content, created_at) VALUES
(1, 'Hipertensiune', 'Pacientul prezintă hipertensiune arterială.', NOW()),
(2, 'Dermatită', 'Pacientul are dermatită de contact.', NOW());

INSERT INTO `notifications` (id_appointment, notification_date, notification_type) VALUES
(1, NOW(), 'reminder'),
(2, NOW(), 'confirmation'),
(3, NOW(), 'canceled');

INSERT INTO `analysis_details` (id_analysis, test_name, obtained_value, unit, min_range, max_range) VALUES
(1, 'Hemoglobină', 13.5, 'g/dL', 12.0, 16.0),
(1, 'Glicemie', 90.0, 'mg/dL', 70.0, 100.0),
(2, 'Colesterol', 180.0, 'mg/dL', 100.0, 200.0);
