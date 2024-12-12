CREATE TABLE clinics (
  id_clinic int(11) NOT NULL AUTO_INCREMENT,
  clinic_name varchar(100) NOT NULL,
  clinic_phone varchar(11) NOT NULL,
  clinic_address varchar(250) NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_clinic),
  UNIQUE KEY clinic_name_UNIQUE (clinic_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE patients (
  id_patient int(11) NOT NULL AUTO_INCREMENT,
  last_name varchar(100) NOT NULL,
  first_name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  phone varchar(11) NOT NULL,
  national_id varchar(13) NOT NULL,
  birth_date date NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_patient),
  UNIQUE KEY patient_email_UNIQUE (email),
  UNIQUE KEY patient_phone_UNIQUE (phone),
  UNIQUE KEY patient_national_id_UNIQUE (national_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE specializations (
  id_specialization int(11) NOT NULL AUTO_INCREMENT,
  specialization_name varchar(100) NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE users (
  id_user int(11) NOT NULL AUTO_INCREMENT,
  email varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  user_role enum('patient','admin','doctor','assistant') NOT NULL DEFAULT 'patient',
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_user),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE doctors (
  id_doctor int(11) NOT NULL AUTO_INCREMENT,
  last_name varchar(100) NOT NULL,
  first_name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  phone varchar(11) NOT NULL,
  id_specialization int(11) NOT NULL,
  id_clinic int(11) NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_doctor),
  UNIQUE KEY doctor_email_UNIQUE (email),
  UNIQUE KEY doctor_phone_UNIQUE (phone),
  KEY FK_doctor_clinic (id_clinic),
  KEY FK_doctor_specialization (id_specialization),
  CONSTRAINT FK_doctor_clinic FOREIGN KEY (id_clinic) REFERENCES clinics (id_clinic),
  CONSTRAINT FK_doctor_specialization FOREIGN KEY (id_specialization) REFERENCES specializations (id_specialization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_services (
  id_medical_service int(11) NOT NULL AUTO_INCREMENT,
  id_doctor int(11) NOT NULL,
  service_name varchar(100) NOT NULL,
  price decimal(10,2) NOT NULL,
  medical_service_type enum('consultation','analysis') NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_medical_service),
  KEY medical_services_doctors_FK (id_doctor),
  CONSTRAINT medical_services_doctors_FK FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE patient_doctor (
  id_patient int(11) NOT NULL,
  id_doctor int(11) NOT NULL,
  PRIMARY KEY (id_patient,id_doctor),
  KEY FK_patient_doctor_doctor (id_doctor),
  CONSTRAINT FK_patient_doctor_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor) ON DELETE CASCADE,
  CONSTRAINT FK_patient_doctor_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE appointments (
  id_appointment int(11) NOT NULL AUTO_INCREMENT,
  id_patient int(11) NOT NULL,
  id_medical_service int(11) NOT NULL,
  appointment_date date NOT NULL,
  appointment_time time NOT NULL,
  appointment_status enum('pending','confirmed','canceled') NOT NULL DEFAULT 'pending',
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_appointment),
  KEY FK_appointment_patient (id_patient),
  KEY FK_appointment_service (id_medical_service),
  CONSTRAINT FK_appointment_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT FK_appointment_service FOREIGN KEY (id_medical_service) REFERENCES medical_services (id_medical_service)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE doctor_schedule (
  id_doctor_schedule int(11) NOT NULL AUTO_INCREMENT,
  id_doctor int(11) NOT NULL,
  day_of_week enum('monday','tuesday','wednesday','thursday','friday','saturday','sunday') NOT NULL DEFAULT 'monday',
  start_time time NOT NULL,
  end_time time NOT NULL,
  PRIMARY KEY (id_doctor_schedule),
  KEY FK_schedule_doctor (id_doctor),
  CONSTRAINT FK_schedule_doctor FOREIGN KEY (id_doctor) REFERENCES doctors (id_doctor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_analysis (
  id_analysis int(11) NOT NULL AUTO_INCREMENT,
  id_patient int(11) NOT NULL,
  id_medical_service int(11) NOT NULL,
  access_code varchar(20) NOT NULL,
  analysis_date datetime NOT NULL DEFAULT current_timestamp(),
  result_received_at datetime DEFAULT NULL,
  analysis_status enum('in_progress','completed') NOT NULL DEFAULT 'in_progress',
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_analysis),
  UNIQUE KEY access_code_UNIQUE (access_code),
  KEY FK_analysis_patient (id_patient),
  KEY medical_analysis_medical_services_FK (id_medical_service),
  CONSTRAINT FK_analysis_patient FOREIGN KEY (id_patient) REFERENCES patients (id_patient),
  CONSTRAINT medical_analysis_medical_services_FK FOREIGN KEY (id_medical_service) REFERENCES medical_services (id_medical_service)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medical_letters (
  id_medical_letter int(11) NOT NULL AUTO_INCREMENT,
  id_appointment int(11) NOT NULL,
  diagnosis varchar(200) NOT NULL,
  content varchar(500) NOT NULL,
  created_at datetime NOT NULL DEFAULT current_timestamp(),
  updated_at datetime DEFAULT NULL,
  PRIMARY KEY (id_medical_letter),
  KEY FK_letter_appointment (id_appointment),
  CONSTRAINT FK_letter_appointment FOREIGN KEY (id_appointment) REFERENCES appointments (id_appointment)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE notifications (
  id_notification int(11) NOT NULL AUTO_INCREMENT,
  id_appointment int(11) DEFAULT NULL,
  notification_date datetime NOT NULL,
  notification_type enum('reminder','confirmation','canceled') NOT NULL DEFAULT 'reminder',
  PRIMARY KEY (id_notification),
  KEY FK_notification_consultation_appointment (id_appointment),
  CONSTRAINT FK_notification_consultation_appointment FOREIGN KEY (id_appointment) REFERENCES appointments (id_appointment)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE analysis_details (
  id_analysis_detail int(11) NOT NULL AUTO_INCREMENT,
  id_analysis int(11) NOT NULL,
  test_name varchar(100) NOT NULL,
  obtained_value decimal(10,2) NOT NULL,
  unit varchar(10) NOT NULL,
  min_range decimal(10,2) NOT NULL,
  max_range decimal(10,2) NOT NULL,
  PRIMARY KEY (id_analysis_detail),
  KEY FK_analysis_detail_analysis (id_analysis),
  CONSTRAINT FK_analysis_detail_analysis FOREIGN KEY (id_analysis) REFERENCES medical_analysis (id_analysis)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
