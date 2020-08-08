package org.hamster.common.boot.test.sql;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Generates testing H2 SQL from certain source such as CSV for testing proposes.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface SqlGenerator {

    /**
     * Generates the list of SQL queries
     *
     * @param tableName
     *         the generated table name
     * @param source
     *         the input stream source
     * @param target
     *         the target output
     * @return generated SQL list in string
     */
    List<String> generate(String tableName, InputStream source, OutputStream target) throws IOException;
}
