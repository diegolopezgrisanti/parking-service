CREATE TABLE IF NOT EXISTS vehicles (
    id UUID PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(20) NOT NULL,
    plate VARCHAR(15) NOT NULL,
    country VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT vehicles_plate_user_id_key UNIQUE (plate, user_id)
);