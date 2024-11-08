CREATE TABLE IF NOT EXISTS exercise_list (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
    );

INSERT IGNORE INTO exercise_list (name) VALUES
    ('BENCH_PRESS'), ('PUSH_UP'), ('DUMBBELL_FLY'), ('PULL_UP'), ('BARBELL_ROW'),
    ('DEADLIFT'), ('SQUAT'), ('LEG_PRESS'), ('LUNGES'), ('BICEP_CURL'),
    ('HAMMER_CURL'), ('TRICEP_EXTENSION'), ('DIPS'), ('WRIST_CURL'),
    ('REVERSE_WRIST_CURL'), ('SHOULDER_PRESS'), ('LATERAL_RAISE'),
    ('PLANK'), ('CRUNCHES'), ('LEG_RAISES'), ('JUMP_ROPE'), ('RUNNING'), ('CYCLING');

CREATE TABLE IF NOT EXISTS goal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
    );

INSERT IGNORE INTO goal (name) VALUES
    ('LOSE_WEIGHT'), ('GAIN_WEIGHT'), ('BUILD_MASS'), ('BULK'),
    ('SHRED'), ('GAIN_STRENGTH'), ('ENHANCE_CARDIO');

CREATE TABLE IF NOT EXISTS level (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
    );

INSERT IGNORE INTO level (name) VALUES
    ('BEGINNER'), ('REGULAR'), ('ABOVE_AVERAGE'), ('ATHLETE'), ('MR_OLYMPIA');

CREATE TABLE IF NOT EXISTS muscle_group (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
    );

INSERT IGNORE INTO muscle_group (name) VALUES
    ('CHEST'), ('BACK'), ('LEGS'), ('BICEPS'), ('TRICEPS'),
    ('FOREARMS'), ('SHOULDERS'), ('CORE'), ('CARDIO');

CREATE TABLE IF NOT EXISTS week_day (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
    );

INSERT IGNORE INTO week_day (name) VALUES
    ('MONDAY'), ('TUESDAY'), ('WEDNESDAY'), ('THURSDAY'),
    ('FRIDAY'), ('SATURDAY'), ('SUNDAY');


CREATE TABLE IF NOT EXISTS athlete (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    weight DOUBLE NOT NULL,
    height INT NOT NULL,
    starting_date DATE NOT NULL,
    goal VARCHAR(20) NOT NULL,
    level VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS routine (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    weekly_frequency INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exercise_list VARCHAR(255) NOT NULL,
    muscle_group VARCHAR(20) NOT NULL,
    sets INT NOT NULL,
    repetitions INT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL
    );


CREATE TABLE IF NOT EXISTS athlete_routine (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    athlete_id BIGINT NOT NULL,
    routine_id BIGINT NOT NULL,
    FOREIGN KEY (athlete_id) REFERENCES athlete(id) ON DELETE CASCADE,
    FOREIGN KEY (routine_id) REFERENCES routine(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS routine_exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    routine_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    FOREIGN KEY (routine_id) REFERENCES routine(id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE
    );

