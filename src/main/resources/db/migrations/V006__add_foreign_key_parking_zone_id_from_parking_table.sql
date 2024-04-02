ALTER TABLE parking
    ADD CONSTRAINT fk_parking_zone_id
        FOREIGN KEY (parking_zone_id)
            REFERENCES parking_zones(id);