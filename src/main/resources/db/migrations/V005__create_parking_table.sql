CREATE TABLE IF NOT EXISTS parkings (
    id UUID PRIMARY KEY,
    plate VARCHAR(15) NOT NULL,
    city_id UUID NOT NULL,
    parking_zone_id UUID NOT NULL,
    expiration TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(255) NOT NULL,
    created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
