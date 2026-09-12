package com.github.bernabaris.dunningmanagement.impl;

import com.github.bernabaris.dunningmanagement.repository.DunningProcedureRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Repository
public class DunningProcedureRepositoryImpl implements DunningProcedureRepository {
    private final DataSource dataSource;

    public DunningProcedureRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getDunningLevel(Long invoiceId) {

        String sql = "{call GET_DUNNING_LEVEL(?, ?)}";

        try (
                Connection connection = dataSource.getConnection();
                CallableStatement callableStatement = connection.prepareCall(sql)
        ) {

            callableStatement.setLong(1, invoiceId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();

            return callableStatement.getString(2);

        } catch (Exception e) {
            throw new RuntimeException("Failed to call GET_DUNNING_LEVEL procedure", e);
        }
    }
}
