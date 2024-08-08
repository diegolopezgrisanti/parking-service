ALTER TABLE parking_zones
ADD COLUMN latitude NUMERIC(9,5),
ADD COLUMN longitude NUMERIC(9,5),
ADD COLUMN currency VARCHAR(5),
ADD COLUMN fee_per_minute INTEGER;

UPDATE parking_zones
SET latitude = 41.79788,
    longitude = 3.05944,
    currency = 'EUR',
    fee_per_minute = 300
WHERE id = '8e4488d3-0e5a-4044-8d6d-d3d9e36836d0';

UPDATE parking_zones
SET latitude = 41.43715,
    longitude = 2.24401,
    currency = 'EUR',
    fee_per_minute = 500
WHERE id = '48236e54-017a-446a-b1b5-8e1d85222cc8';

ALTER TABLE parking_zones
ALTER COLUMN latitude SET NOT NULL,
ALTER COLUMN longitude SET NOT NULL,
ALTER COLUMN currency SET NOT NULL,
ALTER COLUMN fee_per_minute SET NOT NULL;
