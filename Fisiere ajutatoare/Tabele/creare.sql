CREATE TABLE clinica (
    id_clinica INT(11) NOT NULL AUTO_INCREMENT,
    nume_clinica VARCHAR(100) NOT NULL,
    telefon_clinica VARCHAR(11) NOT NULL,
    adresa_clinica VARCHAR(250) NOT NULL,
    PRIMARY KEY (id_clinica),
    CONSTRAINT clinica_nume_clinica_unique UNIQUE (nume_clinica)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE specializare (
    id_specializare INT(11) NOT NULL AUTO_INCREMENT,
    nume_specializare VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_specializare)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE utilizator (
    id_utilizator INT(11) NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    parola VARCHAR(100) NOT NULL,
    rol ENUM('pacient', 'admin', 'medic', 'asistent') NOT NULL DEFAULT 'pacient',
    data_crearii DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    data_modificarii DATETIME DEFAULT NULL,
    PRIMARY KEY (id_utilizator),
    CONSTRAINT utilizator_email_unique UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE asistent (
    id_asistent INT(11) NOT NULL AUTO_INCREMENT,
    id_utilizator INT(11) NOT NULL,
    nume VARCHAR(100) NOT NULL,
    prenume VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefon VARCHAR(11) NOT NULL,
    id_clinica INT(11) NOT NULL,
    data_crearii DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id_asistent),
    CONSTRAINT asistent_email_unique UNIQUE (email),
    CONSTRAINT asistent_telefon_unique UNIQUE (telefon),
    CONSTRAINT asistent_clinica_fk FOREIGN KEY (id_clinica) REFERENCES clinica (id_clinica),
    CONSTRAINT asistent_utilizator_fk FOREIGN KEY (id_utilizator) REFERENCES utilizator (id_utilizator)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE medic (
    id_medic INT(11) NOT NULL AUTO_INCREMENT,
    nume VARCHAR(100) NOT NULL,
    prenume VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefon VARCHAR(11) NOT NULL,
    id_specializare INT(11) NOT NULL,
    id_clinica INT(11) NOT NULL,
    id_utilizator INT(11) NOT NULL,
    data_crearii DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id_medic),
    CONSTRAINT medic_email_unique UNIQUE (email),
    CONSTRAINT medic_telefon_unique UNIQUE (telefon),
    CONSTRAINT medic_clinica_fk FOREIGN KEY (id_clinica) REFERENCES clinica (id_clinica),
    CONSTRAINT medic_specializare_fk FOREIGN KEY (id_specializare) REFERENCES specializare (id_specializare),
    CONSTRAINT medic_utilizator_fk FOREIGN KEY (id_utilizator) REFERENCES utilizator (id_utilizator)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE pacient (
    id_pacient INT(11) NOT NULL AUTO_INCREMENT,
    nume VARCHAR(100) NOT NULL,
    prenume VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefon VARCHAR(11) NOT NULL,
    cnp VARCHAR(13) NOT NULL,
    data_nasterii DATE NOT NULL,
    data_crearii DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    data_modificarii DATETIME DEFAULT NULL,
    id_utilizator INT(11) DEFAULT NULL,
    PRIMARY KEY (id_pacient),
    CONSTRAINT pacient_email_unique UNIQUE (email),
    CONSTRAINT pacient_telefon_unique UNIQUE (telefon),
    CONSTRAINT pacient_cnp_unique UNIQUE (cnp),
    CONSTRAINT pacient_utilizator_fk FOREIGN KEY (id_utilizator) REFERENCES utilizator (id_utilizator)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE pacient_medic (
    id_pacient INT(11) NOT NULL,
    id_medic INT(11) NOT NULL,
    PRIMARY KEY (id_pacient, id_medic),
    CONSTRAINT pacient_medic_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient) ON DELETE CASCADE,
    CONSTRAINT pacient_medic_medic_fk FOREIGN KEY (id_medic) REFERENCES medic (id_medic) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE program_doctor (
    id_program_medic INT(11) NOT NULL AUTO_INCREMENT,
    id_medic INT(11) NOT NULL,
    ziua_saptamanii ENUM('luni', 'marti', 'miercuri', 'joi', 'vineri', 'sambata', 'duminica') NOT NULL DEFAULT 'luni',
    ora_inceput TIME NOT NULL,
    ora_sfarsit TIME NOT NULL,
    PRIMARY KEY (id_program_medic),
    CONSTRAINT program_doctor_medic_fk FOREIGN KEY (id_medic) REFERENCES medic (id_medic)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE serviciu_medical (
    id_serviciu_medical INT(11) NOT NULL AUTO_INCREMENT,
    id_specializare INT(11) NOT NULL,
    nume VARCHAR(100) NOT NULL,
    pret DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_serviciu_medical),
    CONSTRAINT serviciu_medical_specializare_fk FOREIGN KEY (id_specializare) REFERENCES specializare (id_specializare)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE analize (
    id_analiza INT(11) NOT NULL AUTO_INCREMENT,
    id_pacient INT(11) NOT NULL,
    id_specializare INT(11) NOT NULL,
    cod_acces VARCHAR(20) NOT NULL,
    data_analiza DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    data_primire_analiza DATETIME DEFAULT NULL,
    status ENUM('in curs', 'finalizata') NOT NULL DEFAULT 'in curs',
    PRIMARY KEY (id_analiza),
    CONSTRAINT analize_cod_acces_unique UNIQUE (cod_acces),
    CONSTRAINT analize_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient),
    CONSTRAINT analize_specializare_fk FOREIGN KEY (id_specializare) REFERENCES specializare (id_specializare)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE detalii_analiza (
    id_detalii_analiza INT(11) NOT NULL AUTO_INCREMENT,
    id_analiza INT(11) NOT NULL,
    nume_test VARCHAR(100) NOT NULL,
    valoare_obtinuta DECIMAL(10, 2) NOT NULL,
    interval_referinta DECIMAL(10, 2) NOT NULL,
    unitate_masura VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_detalii_analiza),
    CONSTRAINT detalii_analiza_analize_fk FOREIGN KEY (id_analiza) REFERENCES analize (id_analiza)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE programare_analiza (
    id_programare_analiza INT(11) NOT NULL AUTO_INCREMENT,
    id_pacient INT(11) NOT NULL,
    id_analiza INT(11) NOT NULL,
    data_programarii DATE NOT NULL,
    ora_programarii TIME NOT NULL,
    status_programare ENUM('in asteptare', 'confirmata', 'anulata') DEFAULT 'in asteptare',
    PRIMARY KEY (id_programare_analiza),
    CONSTRAINT programare_analiza_analize_fk FOREIGN KEY (id_analiza) REFERENCES analize (id_analiza),
    CONSTRAINT programare_analiza_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE programare_consultatie (
    id_programare_consultatie INT(11) NOT NULL AUTO_INCREMENT,
    id_pacient INT(11) NOT NULL,
    id_medic INT(11) NOT NULL,
    id_serviciu_medical INT(11) NOT NULL,
    data_programarii DATE NOT NULL,
    ora_programarii TIME NOT NULL,
    status_programare ENUM('in asteptare', 'confirmata', 'anulata') NOT NULL DEFAULT 'in asteptare',
    PRIMARY KEY (id_programare_consultatie),
    CONSTRAINT programare_consultatie_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient),
    CONSTRAINT     programare_consultatie_medic_fk FOREIGN KEY (id_medic) REFERENCES medic (id_medic),
    CONSTRAINT programare_consultatie_serviciu_medical_fk FOREIGN KEY (id_serviciu_medical) REFERENCES serviciu_medical (id_serviciu_medical)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE scrisoare_medicala (
    id_scrisoare_medicala INT(11) NOT NULL AUTO_INCREMENT,
    id_pacient INT(11) DEFAULT NULL,
    id_medic INT(11) NOT NULL,
    id_programare_consultatie INT(11) NOT NULL,
    id_serviciu_medical INT(11) NOT NULL,
    id_specializare INT(11) NOT NULL,
    diagnostic VARCHAR(200) NOT NULL,
    continut VARCHAR(500) NOT NULL,
    data_crearii DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    data_modificarii DATETIME DEFAULT NULL,
    PRIMARY KEY (id_scrisoare_medicala),
    CONSTRAINT scrisoare_medicala_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient),
    CONSTRAINT scrisoare_medicala_medic_fk FOREIGN KEY (id_medic) REFERENCES medic (id_medic),
    CONSTRAINT scrisoare_medicala_programare_consultatie_fk FOREIGN KEY (id_programare_consultatie) REFERENCES programare_consultatie (id_programare_consultatie),
    CONSTRAINT scrisoare_medicala_specializare_fk FOREIGN KEY (id_specializare) REFERENCES specializare (id_specializare),
    CONSTRAINT scrisoare_medicala_serviciu_medical_fk FOREIGN KEY (id_serviciu_medical) REFERENCES serviciu_medical (id_serviciu_medical)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE notificare (
    id_notificare INT(11) NOT NULL AUTO_INCREMENT,
    id_programare_consultatie INT(11) DEFAULT NULL,
    id_programare_analiza INT(11) DEFAULT NULL,
    id_pacient INT(11) NOT NULL,
    data_notificare DATETIME NOT NULL,
    tip_notificare ENUM('reamintire', 'confirmare', 'anulata') NOT NULL DEFAULT 'reamintire',
    PRIMARY KEY (id_notificare),
    CONSTRAINT notificare_programare_analiza_fk FOREIGN KEY (id_programare_analiza) REFERENCES programare_analiza (id_programare_analiza),
    CONSTRAINT notificare_programare_consultatie_fk FOREIGN KEY (id_programare_consultatie) REFERENCES programare_consultatie (id_programare_consultatie),
    CONSTRAINT notificare_pacient_fk FOREIGN KEY (id_pacient) REFERENCES pacient (id_pacient)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
