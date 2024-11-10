CREATE TABLE clinics (
  id_clinic INT(11) NOT NULL AUTO_INCREMENT,
  clinic_name VARCHAR(100) NOT NULL,
  clinic_phone VARCHAR(11) NOT NULL,
  clinic_address VARCHAR(250) NOT NULL,
  PRIMARY KEY (id_clinic),
  CONSTRAINT clinic_name_UNIQUE UNIQUE (clinic_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE specializations (
  id_specialization INT(11) NOT NULL AUTO_INCREMENT,
  specialization_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE users (
  id_user INT(11) NOT NULL AUTO_INCREMENT,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  user_role ENUM(patient, admin, doctor, assistant) NOT NULL DEFAULT patient,
  created_at DATETIME NOT NULL DEFAULT current_timestamp(),
  updated_at DATETIME DEFAULT NULL,
  PRIMARY KEY (id_user),
  CONSTRAINT email_UNIQUE UNIQUE (email)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE assistants (
  id_assistant INT(11) NOT NULL AUTO_INCREMENT,
  id_user INT(11) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(11) NOT NULL,
  id_clinic INT(11) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id_assistant),
  CONSTRAINT assistant_email_UNIQUE UNIQUE (email),
  CONSTRAINT assistant_phone_UNIQUE UNIQUE (phone),
  CONSTRAINT FK_assistant_clinic FOREIGN KEY (id_clinic) REFERENCES clinics (id_clinic),
  CONSTRAINT FK_assistant_user FOREIGN KEY (id_user) REFERENCES users (id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE doctors (
  id_doctor INT(11) NOT NULL AUTO_INCREMENT,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(11) NOT NULL,
  id_specialization INT(11) NOT NULL,
  id_clinic INT(11) NOT NULL,
  id_user INT(11) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id_doctor),
  CONSTRAINT doctor_email_UNIQUE UNIQUE (email),
  CONSTRAINT doctor_phone_UNIQUE UNIQUE (phone),
  CONSTRAINT FK_doctor_clinic FOREIGN KEY (id_clinic) REFERENCES clinics (id_clinic),
  CONSTRAINT FK_doctor_specialization FOREIGN KEY (id_specialization) REFERENCES specializations (id_specialization),
  CONSTRAINT FK_doctor_user FOREIGN KEY (id_user) REFERENCES users (id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_services (
  id_medical_service INT(11) NOT NULL AUTO_INCREMENT,
  id_specialization INT(11) NOT NULL,
  service_name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_medical_service),
  CONSTRAINT FK_service_specialization FOREIGN KEY (id_specialization) REFERENCES specializations (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE patients (
  id_patient INT(11) NOT NULL AUTO_INCREMENT,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(11) NOT NULL,
  national_id VARCHAR(13) NOT NULL,
  birth_date DATE NOT NULL,
  created_at DATETIME NOT NULL DEFAULT current_timestamp(),
  updated_at DATETIME DEFAULT NULL,
  id_user INT(11) DEFAULT NULL,
  PRIMARY KEY (id_patient),
  CONSTRAINT patient_email_UNIQUE UNIQUE (email),
  CONSTRAINT patient_phone_UNIQUE UNIQUE (phone),
  CONSTRAINT patient_national_id_UNIQUE UNIQUE (national_id),
  CONSTRAINT FK_patient_user FOREIGN KEY (id_user) REFERENCES users (id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE consultation_appointments (
  id_consultation_appointment INT(11) NOT NULL AUTO_INCREMENT,
  id_patient INT(11) NOT NULL,
  id_doctor INT(11) NOT NULL,
  id_medical_service INT(11) NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  appointment_status ENUM(pending, confirmed, canceled) NOT NULL DEFAULT pending,
  PRIMARY KEY (id_consultation_appointment),
  CONSTRAINT FK_appointment_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT FK_appointment_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor),
  CONSTRAINT FK_appointment_service FOREIGN KEY (id_medical_service) REFERENCES medical_services (id_medical_service)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE doctor_schedule (
  id_doctor_schedule INT(11) NOT NULL AUTO_INCREMENT,
  id_doctor INT(11) NOT NULL,
  day_of_week ENUM(monday, tuesday, wednesday, thursday, friday, saturday, sunday) NOT NULL DEFAULT monday,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  PRIMARY KEY (id_doctor_schedule),
  CONSTRAINT FK_schedule_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_analysis (
  id_analysis INT(11) NOT NULL AUTO_INCREMENT,
  id_patient INT(11) NOT NULL,
  id_specialization INT(11) NOT NULL,
  access_code VARCHAR(20) NOT NULL,
  analysis_date DATETIME NOT NULL DEFAULT current_timestamp(),
  result_received_at DATETIME DEFAULT NULL,
  analysis_status ENUM(in_progress, completed) NOT NULL DEFAULT in_progress,
  PRIMARY KEY (id_analysis),
  CONSTRAINT access_code_UNIQUE UNIQUE (access_code),
  CONSTRAINT FK_analysis_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT FK_analysis_specialization FOREIGN KEY (id_specialization) REFERENCES specializations (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_letters (
  id_medical_letter INT(11) NOT NULL AUTO_INCREMENT,
  id_patient INT(11) DEFAULT NULL,
  id_doctor INT(11) NOT NULL,
  id_consultation_appointment INT(11) NOT NULL,
  id_medical_service INT(11) NOT NULL,
  id_specialization INT(11) NOT NULL,
  diagnosis VARCHAR(200) NOT NULL,
  content VARCHAR(500) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT current_timestamp(),
  updated_at DATETIME DEFAULT NULL,
  PRIMARY KEY (id_medical_letter),
  CONSTRAINT FK_letter_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT FK_letter_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor),
  CONSTRAINT FK_letter_appointment FOREIGN KEY (id_consultation_appointment) REFERENCES consultation_appointments (id_consultation_appointment),
  CONSTRAINT FK_letter_service FOREIGN KEY (id_medical_service) REFERENCES medical_services (id_medical_service),
  CONSTRAINT FK_letter_specialization FOREIGN KEY (id_specialization) REFERENCES specializations (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE patient_doctor (
  id_patient INT(11) NOT NULL,
  id_doctor INT(11) NOT NULL,
  PRIMARY KEY (id_patient, id_doctor),
  CONSTRAINT FK_patient_doctor_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient) ON DELETE CASCADE,
  CONSTRAINT FK_patient_doctor_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE analysis_appointments (
  id_analysis_appointment INT(11) NOT NULL AUTO_INCREMENT,
  id_patient INT(11) NOT NULL,
  id_analysis INT(11) NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  appointment_status ENUM(pending, confirmed, canceled) DEFAULT pending,
  PRIMARY KEY (id_analysis_appointment),
  CONSTRAINT FK_analysis_appointment_analysis FOREIGN KEY (id_analysis) REFERENCES medical_analysis (id_analysis),
  CONSTRAINT FK_analysis_appointment_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE analysis_details (
  id_analysis_detail INT(11) NOT NULL AUTO_INCREMENT,
  id_analysis INT(11) NOT NULL,
  test_name VARCHAR(100) NOT NULL,
  obtained_value DECIMAL(10,2) NOT NULL,
  unit VARCHAR(10) NOT NULL,
  min_range DECIMAL(10,2) NOT NULL,
  max_range DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_analysis_detail),
  CONSTRAINT FK_analysis_detail_analysis FOREIGN KEY (id_analysis) REFERENCES medical_analysis (id_analysis)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE notifications (
  id_notification INT(11) NOT NULL AUTO_INCREMENT,
  id_consultation_appointment INT(11) DEFAULT NULL,
  id_analysis_appointment INT(11) DEFAULT NULL,
  id_patient INT(11) NOT NULL,
  notification_date DATETIME NOT NULL,
  notification_type ENUM(reminder, confirmation, canceled) NOT NULL DEFAULT reminder,
  PRIMARY KEY (id_notification),
  CONSTRAINT FK_notification_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT FK_notification_analysis_appointment FOREIGN KEY (id_analysis_appointment) REFERENCES analysis_appointments (id_analysis_appointment),
  CONSTRAINT FK_notification_consultation_appointment FOREIGN KEY (id_consultation_appointment) REFERENCES consultation_appointments (id_consultation_appointment)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
