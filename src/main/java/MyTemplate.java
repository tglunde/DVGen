import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import net.sf.saxon.Transform;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class MyTemplate {
    public static void main(String[] args) throws Exception {

        MyTemplate.generateArtefacts(MyTemplate.retrieveInterfaceSqlServer(),
                "KAMPDB"
        , "SYS_CAMPAIGN_CORE", "campaign_core", "REP_ETLD", "DB2", "DB2", "TS_CUSTOMER"
                , "TS_CUSTOMER", "Microsoft SQL Server", "CODIAC", "CODIAC_KAMP.dbo"
        , "TARGET_TUFIS_BULK", "SOURCE_CODIAC_WORK"
                , "./target/");

        MyTemplate.generateArtefacts(MyTemplate.retrieveKampMeta(),
                "KAMPDB"
                , "SYS_CAMPAIGN_CORE", "campaign_core", "REP_ETLD", "DB2"
                , "DB2"
                , "TS_CUSTOMER", "TS_CUSTOMER"
                , "DB2", "KAMPMETA", "KAMP_META"
                , "TARGET_TUFIS_BULK", "SOURCE_TUFIS_UR"
                , "./target/");

        /*
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        basicDS.setUrl("jdbc:db2://udbm.udb.tui.de:50200/UDBM");
        basicDS.setUsername("loadudbm");
        basicDS.setPassword("loadudbm");
        ScriptRunner scriptRunner = new ScriptRunner(basicDS.getConnection(), false, false);
        scriptRunner.setLogWriter(new PrintWriter(System.out));
        File directory = new File("./target/sql/");
        for (String sqlFile : directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".sql");
            }
        })) {
            scriptRunner.runScript(new BufferedReader((new FileReader("./target/sql/" + sqlFile))));
        }
        */

        String[] arglist = {"-xsl:src/main/resources/wf.xsl" , "-it:main", "-o:workflows.xml"};
        Transform.main(arglist);

    }

    private static void generateArtefacts(  List<Table> tables
                                          , String subjectArea
                                          , String infaFolder
                                          , String infaProject
                                          , String infaRepository
                                          , String infaDbType
                                          , String targetDbType
                                          , String tablespaceName
                                          , String indexspaceName
                                          , String sourceDbType
                                          , String sourceName
                                          , String sourceSchema
                                          , String infaTargetConnection
                                          , String infaSourceConnection
                                          , String tempDirectory

    ) throws IOException {
        new File(tempDirectory + "/elt/").mkdirs();
        new File(tempDirectory + "/sqlr/").mkdirs();
        new File(tempDirectory + "/sqlha/").mkdirs();
        new File(tempDirectory + "/wf/").mkdirs();
        PebbleEngine pEngine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();
        Map<String,Object> context = new HashMap<String, Object>();
        context.put("infa_folder", infaFolder);
        context.put("infa_project", infaProject);
        context.put("infa_repo", infaRepository);
        context.put("infa_dbtype", infaDbType);
        context.put("table_space_name", tablespaceName);
        context.put("index_space_name", indexspaceName);
        context.put("source_type", sourceDbType);
        context.put("source_name", sourceName);
        context.put("target_type", targetDbType);
        context.put("target_connection", infaTargetConnection);
        context.put("source_connection", infaSourceConnection);
        int n = 0;
        for(Table table : tables) {
            context.put("source_table", table.getName());
            context.put("target_schema", subjectArea + "_HA");
            context.put("columns", Arrays.asList(table.getColumns()));
            context.put( "bks", MyTemplate.getBk(table));
            context.put("table_name", table.getName());
            context.put("schema_name", subjectArea + "_HA");
            context.put("source_schema", sourceSchema);
            MyTemplate.output(pEngine, "create_table_r.sql", context, tempDirectory + "/sqlr/V0_0_" + n + "__" + table.getName() + "_r.sql");
            MyTemplate.output(pEngine, "create_table_ha.sql", context, tempDirectory + "/sqlha/V0_0_" + n++ + "__" + table.getName() + "_ha.sql");
            context.put("target_schema", subjectArea + "_R");
            MyTemplate.output(pEngine, "wf_replication.XML", context, tempDirectory + "/wf/" + table.getName() + ".XML");
            context.put("source_schema", subjectArea + "_R");
            context.put("target_schema", subjectArea + "_HA");
            MyTemplate.output(pEngine, "load_ha.sql", context, tempDirectory + "/elt/load_" + table.getName() + "_ha.sql");
            String content = readFile(tempDirectory + "/elt/load_" + table.getName() + "_ha.sql", StandardCharsets.ISO_8859_1);
            context.put("sql_query", content);
            context.put("wf_name", "wf_load_HA_"+ sourceName + "_" + table.getName());
            context.put("map_name", "m_load_HA_"+ sourceName +"_" + table.getName());
            context.put("ses_name", "s_load_HA_" + sourceName + table.getName());
            MyTemplate.output(pEngine, "wf_exec.XML", context, tempDirectory + "/wf/wf_load_HA_" + table.getName() + ".XML");
        }
    }
    private static List<Column> getBk(Table table) {
        List<Column> bks = new ArrayList<>();
        for(Column column : table.getColumns()) {
            if(column.isPrimaryKey()) {
                bks.add(column);
            }
        }
        return bks;
    }

    private static void output(PebbleEngine pEngine, String templateName, Map<String,Object> context, String targetPath) throws IOException {
        File file = new File(targetPath);
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        PebbleTemplate compiledTemplate = pEngine.getTemplate(templateName);
        compiledTemplate.evaluate(writer, context);
        writer.flush();
        writer.close();
    }
    private static List<Table> retrieveKampMeta() throws SQLException {
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        basicDS.setUrl("jdbc:db2://udbm.udb.tui.de:50200/UDBM");
        basicDS.setUsername("loadudbm");
        basicDS.setPassword("loadudbm");
        ResultSet rs = basicDS.getConnection().createStatement().executeQuery(
                "SELECT  " +
                        "c.TABNAME AS table_name, c.COLNAME AS column_name, " +
                        "c.TYPENAME,  " +
                        "c.\"SCALE\" AS NUMERIC_SCALE, " +
                        "c.\"LENGTH\" AS NUMERIC_PRECISION, " +
                        "CASE WHEN c.\"NULLS\"='N' THEN 'NO' ELSE 'YES' END AS IS_NULLABLE, " +
                        "c.LENGTH AS CHARACTER_MAXIMUM_LENGTH " +
                        "FROM syscat.tables t JOIN syscat.COLUMNS c ON t.TABNAME=c.TABNAME AND t.TABSCHEMA=c.TABSCHEMA WHERE " +
                        "t.tabname LIKE '%_VW' " +
                        "AND t.tabschema='KAMP_META' " +
                        "AND t.TYPE='V' " +
                        "ORDER BY c.TABNAME, c.COLNO ");
        List<String> bkNames = new ArrayList<>();
        bkNames.add("MEASURE");
        bkNames.add("ACTION");
        bkNames.add("HOTEL_CODE");
        bkNames.add("ADVERTISING_DESIGN_CODE");
        bkNames.add("CAMPAIGN_SUB_PROGRAM_CODE");
        bkNames.add("CHANNEL_CODE");
        bkNames.add("COMPANY_CODE");
        bkNames.add("PERSON_CODE");
        bkNames.add("FUNCTION_CODE");
        bkNames.add("CONTACT_TYPE_CODE");
        bkNames.add("INITIATIVE_CODE");
        bkNames.add("LEGAL_ENTITY_CODE");
        bkNames.add("TABLE_NAME");

        List<Table> tables = new ArrayList<Table>();
        Table t = new Table();
        while(rs.next()) {
            if(!rs.getString("TABLE_NAME").equals(t.getName())) {
                //neue tabelle
                t = new Table();
                t.setSchema("CODIAC_HA");
                t.setName(rs.getString("TABLE_NAME"));
                tables.add(t);
            }
            Column column = new Column();
            column.setName(rs.getString("COLUMN_NAME"));
            if(rs.getString("TYPENAME").equals("int")) {
                column.setType("INTEGER");
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("decimal")) {
                column.setTypeCode(Types.DECIMAL);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("double")) {
                column.setTypeCode(Types.DECIMAL);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("int")) {
                column.setTypeCode(Types.INTEGER);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("integer")) {
                column.setTypeCode(Types.INTEGER);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("bigint")) {
                column.setTypeCode(Types.BIGINT);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("character")) {
                column.setTypeCode(Types.VARCHAR);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("varchar")) {
                column.setTypeCode(Types.VARCHAR);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("timestamp")) {
                column.setTypeCode(Types.TIMESTAMP);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("datetime")) {
                column.setTypeCode(Types.TIMESTAMP);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("date")) {
                column.setTypeCode(Types.DATE);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("smallint")) {
                column.setTypeCode(Types.SMALLINT);
            } else if(rs.getString("TYPENAME").equalsIgnoreCase("bit")) {
                column.setTypeCode(Types.SMALLINT);
            } else {
                throw new RuntimeException("unszpported datatype " + rs.getString("TYPENAME"));
            }

            column.setPrecisionRadix(rs.getInt("NUMERIC_PRECISION"));
            int nScale = rs.getInt("NUMERIC_SCALE");
            if(nScale > 0) {
                column.setScale(rs.getInt("NUMERIC_SCALE"));
            }
            column.setRequired("NO".equals(rs.getString("IS_NULLABLE")));
            if(bkNames.contains(column.getName().toUpperCase())) {
                column.setPrimaryKey(true);
                column.setRequired(true);
            }
            t.addColumn(column);
        }
        return tables;
    }
    private static List<Table> retrieveInterfaceSqlServer() throws SQLException {
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        basicDS.setUrl("jdbc:jtds:sqlserver://hajas001cdcs:1433/CODIAC_KAMP;databaseName=CODIAC_KAMP;integratedSecurity=true;");
        ResultSet rs = basicDS.getConnection().createStatement().executeQuery(
                "select c.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.NUMERIC_SCALE, c.NUMERIC_PRECISION, c.IS_NULLABLE, c.CHARACTER_MAXIMUM_LENGTH, c.ORDINAL_POSITION " +
                        "from INFORMATION_SCHEMA.COLUMNS c " +
                        "where 1=1 " +
                        "AND TABLE_NAME IN ('AKTION_KUNDE','AKTION_KUNDE_UE','AKTION_KUNDERETURN','EMAIL','EMAIL_UE') ORDER BY c.TABLE_NAME, c.ORDINAL_POSITION");


        List<String> bkNames = new ArrayList<>();
        bkNames.add("MASSNAHME");
        bkNames.add("AKTION");
        bkNames.add("KD_KUNDENID");
        bkNames.add("IND_PERMISSIONS_ID");
        bkNames.add("OEFFNUNGSID");
        bkNames.add("LINKOEFFNUNGS_ID");

        List<Table> tables = new ArrayList<Table>();
        Table t = new Table();
        while(rs.next()) {
            if(!rs.getString("TABLE_NAME").equals(t.getName())) {
                //neue tabelle
                t = new Table();
                t.setSchema("CODIAC_HA");
                t.setName(rs.getString("TABLE_NAME"));
                tables.add(t);
            }
            Column column = new Column();
            column.setName(rs.getString("COLUMN_NAME"));
            if(rs.getString("DATA_TYPE").equals("int")) {
                column.setType("INTEGER");
            } else if(rs.getString("DATA_TYPE").equals("int")) {
                column.setTypeCode(Types.INTEGER);
            } else if(rs.getString("DATA_TYPE").equals("bigint")) {
                column.setTypeCode(Types.BIGINT);
            } else if(rs.getString("DATA_TYPE").equals("varchar")) {
                column.setTypeCode(Types.VARCHAR);
            } else if(rs.getString("DATA_TYPE").equals("timestamp")) {
                column.setTypeCode(Types.TIMESTAMP);
            } else if(rs.getString("DATA_TYPE").equals("datetime")) {
                column.setTypeCode(Types.TIMESTAMP);
            } else if(rs.getString("DATA_TYPE").equals("date")) {
                column.setTypeCode(Types.DATE);
            } else if(rs.getString("DATA_TYPE").equals("bit")) {
                column.setTypeCode(Types.SMALLINT);
            } else {
                throw new RuntimeException("unszpported datatype " + rs.getString("DATA_TYPE"));
            }

            column.setPrecisionRadix(rs.getInt("NUMERIC_PRECISION"));
            column.setScale(rs.getInt("NUMERIC_SCALE"));
            column.setSize(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
            column.setRequired("NO".equals(rs.getString("IS_NULLABLE")));
            if(bkNames.contains(column.getName().toUpperCase())) {
                column.setPrimaryKey(true);
                column.setRequired(true);
            }
            t.addColumn(column);
        }
        return tables;
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
