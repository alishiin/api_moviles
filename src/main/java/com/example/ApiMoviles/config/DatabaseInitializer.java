package com.example.ApiMoviles.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(1)
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Verificar y crear columna stock si no existe
            if (!columnExists("producto", "stock")) {
                logger.info("Creando columna 'stock' en tabla 'producto'...");
                jdbcTemplate.execute("ALTER TABLE producto ADD COLUMN stock INTEGER DEFAULT 0 NOT NULL");
                logger.info("Columna 'stock' creada exitosamente");

                // Actualizar registros existentes
                jdbcTemplate.execute("UPDATE producto SET stock = 0 WHERE stock IS NULL");
                logger.info("Registros existentes actualizados con stock = 0");
            } else {
                logger.info("Columna 'stock' ya existe en la tabla 'producto'");
            }

            // Verificar y crear columna tallas_disponibles si no existe
            if (!columnExists("producto", "tallas_disponibles")) {
                logger.info("Creando columna 'tallas_disponibles' en tabla 'producto'...");
                jdbcTemplate.execute("ALTER TABLE producto ADD COLUMN tallas_disponibles VARCHAR(500)");
                logger.info("Columna 'tallas_disponibles' creada exitosamente");
            } else {
                logger.info("Columna 'tallas_disponibles' ya existe en la tabla 'producto'");
            }

        } catch (Exception e) {
            logger.error("Error al inicializar la base de datos: ", e);
        }
    }

    private boolean columnExists(String tableName, String columnName) {
        try {
            String query = "SELECT 1 FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
            Integer result = jdbcTemplate.queryForObject(query, Integer.class, tableName, columnName);
            return result != null;
        } catch (Exception e) {
            logger.warn("Error al verificar existencia de columna {}.{}: {}", tableName, columnName, e.getMessage());
            return false;
        }
    }
}
