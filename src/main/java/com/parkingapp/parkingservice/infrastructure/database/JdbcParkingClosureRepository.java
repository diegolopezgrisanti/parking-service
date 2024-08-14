package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.parkingapp.parkingservice.domain.parking.PaymentStatus.PROCESSED;

public class JdbcParkingClosureRepository implements ParkingClosureRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcParkingClosureRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ParkingClosure> getParkingsWithPendingPayment(int batchSize, Instant endDateUntil) {
        Map<String, Object> params = new HashMap<>();
        params.put("endDateUntil", Timestamp.from(endDateUntil));
        params.put("batchSize", batchSize);

        return namedParameterJdbcTemplate.query(
                """
                SELECT
                    p.id AS parking_id,
                    p.start_date,
                    p.end_date,
                    pz.currency,
                    pz.fee_per_minute
                FROM parking p
                JOIN parking_zones pz ON p.parking_zone_id = pz.id
                WHERE payment_status = 'PENDING'
                AND end_date < :endDateUntil
                LIMIT :batchSize
                FOR UPDATE SKIP LOCKED
                """,
                params,
                new JdbcParkingClosureRepository.ParkingRowMapper()
        );
    }

    @Override
    public void markAsProcessed(UUID parkingId, Instant processedAt) {
        String sql = """
            UPDATE parking
            SET processed_date = :processedAt,
                payment_status = :paymentStatus::payment_status
            WHERE id = :parkingId
        """;

        Map<String, Object> params = Map.of(
                "processedAt", Timestamp.from(processedAt),
                "paymentStatus", PROCESSED.name(),
                "parkingId", parkingId
        );

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void markAsFailed(UUID parkingId, Instant processedAt) {
        String sql = """
            UPDATE parking
            SET processed_date = :processedAt
            WHERE id = :parkingId
        """;

        Map<String, Object> params = Map.of(
                "processedAt", Timestamp.from(processedAt),
                "parkingId", parkingId
        );

        namedParameterJdbcTemplate.update(sql, params);
    }

    private class ParkingRowMapper implements RowMapper<ParkingClosure> {

        @Override
        public ParkingClosure mapRow(ResultSet rs, int rowNum) throws SQLException {

            CurrencyUnit currency = Monetary.getCurrency(rs.getString("currency"));
            int feePerMinute = rs.getInt("fee_per_minute");
            Amount amount = new Amount(currency, feePerMinute);

            return new ParkingClosure(
                    amount,
                    rs.getTimestamp("start_date").toInstant(),
                    rs.getTimestamp("end_date").toInstant(),
                    UUID.fromString(rs.getString("parking_id"))
            );
        }
    }
}
