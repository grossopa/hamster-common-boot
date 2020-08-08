package org.hamster.common.boot.test.sql;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

/**
 * Generates H2 compatible insert queries from CSV file.
 *
 * <p>
 * CSV file name (excluding the suffix) represents the table name. the first line as header represents each column
 * name.
 * </p>
 *
 * @author Jack Yin
 * @since 1.0
 */
public class CsvH2SqlGenerator implements SqlGenerator {

    static final String INSERT_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2});";
    static final String NULL = "NULL";
    static final char[] SPECIAL_CHARACTERS = new char[]{'\b', '\t', '\n', '\f', '\r', '\"', '\\'};

    @Override
    public List<String> generate(String tableName, InputStream source, OutputStream target) throws IOException {
        CSVParser parserResult = CSVFormat.EXCEL.parse(new InputStreamReader(source));
        List<String> headerNames = parserResult.getHeaderNames();
        String headerString = StringUtils.join(headerNames, "' ,'");
        List<CSVRecord> records = parserResult.getRecords();

        List<String> sqls = newArrayListWithExpectedSize(records.size() - 1);
        for (int i = 1; i < records.size(); i++) {
            CSVRecord record = records.get(i);
            String values = headerNames.stream().map(headerName -> {
                String value = record.get(headerName);
                if (!NULL.equalsIgnoreCase(value) && !NumberUtils.isCreatable(value)) {
                    value = "'" + value + "'";
                    if (StringUtils.containsAny(value, SPECIAL_CHARACTERS)) {
                        value = "STRINGDECODE(" + value + ")";
                    }
                }
                return value;
            }).collect(joining(" ,"));
            sqls.add(format(INSERT_TEMPLATE, tableName, headerString, values));
        }
        return sqls;
    }

}
