package ie.curiositysoftware.allocation.engine;

import ie.curiositysoftware.allocation.dto.AllocationType;
import ie.curiositysoftware.allocation.dto.DataAllocationResult;
import ie.curiositysoftware.allocation.dto.ResultMergeMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataAllocationGherkinResolver
{
    private DataAllocationEngine m_DataAllocationEngine;

    private String m_ServerName;

    public DataAllocationGherkinResolver(DataAllocationEngine engine, String serverName)
    {
        this.m_DataAllocationEngine = engine;

        this.m_ServerName = serverName;
    }

    public void ResolveGherkinFeatures(String sourceLocation, String targetLocation) throws IOException {
        // 1) Get all unresolved features
        File[] files = new File(sourceLocation).listFiles((File f) -> f.getName().endsWith(".feature"));

        // 2) Perform allocation
        List<AllocationType> allocationTypes = new ArrayList<AllocationType>();
        String dataPattern = "(?<allocName>@Data\\(\\s*poolName\\s*=\\s*\"(?<pool>[^\"]*)\"\\s*,\\s*suiteName\\s*=\\s*\"(?<suite>[^\"]*)\"\\s*,\\s*test\\s*=\\s*\"(?<test>[^\"]*)\"\\)\\[(?<rowIndex>\\d)\\]\\[(?<colName>[^\"]*)\\])";
        Pattern dataRegex = Pattern.compile(dataPattern);

        String dataTablePattern = "(?<allocName>@DataTable\\(\\s*poolName\\s*=\\s*\"(?<pool>[^\"]*)\"\\s*,\\s*suiteName\\s*=\\s*\"(?<suite>[^\"]*)\"\\s*,\\s*test\\s*=\\s*\"(?<test>[^\"]*)\"\\)\\[(?<colName>[^\"]*)\\])";
        Pattern dataTableRegex = Pattern.compile(dataTablePattern);

        String dataTableInitialPattern = "\\|.*@DataTable.*\\|";
        Pattern dataTableInitialRegex = Pattern.compile(dataTableInitialPattern);

        ArrayList<String> allocUnique = new ArrayList<String>();
        for (File f : files) {
            // 2.1) Parse it into string
            String content = new String (Files.readAllBytes(f.toPath()));

            // 2.2) Look for data tags @Data{} -> bundle values
            Matcher matcher = dataRegex.matcher(content);
            while(matcher.find()) {
                String pool = matcher.group("pool");
                String suite = matcher.group("suite");
                String test = matcher.group("test");

                if (!allocUnique.contains(pool+suite+test)) {
                    AllocationType allocationType = new AllocationType(pool, suite, test);
                    allocationTypes.add(allocationType);

                    allocUnique.add(pool+suite+test);
                }
            }

            // 2.3) Look for data tags @DataTable{} -> bundle values
            Matcher dataTableMatcher = dataTableRegex.matcher(content);
            while(dataTableMatcher.find()) {
                String pool = dataTableMatcher.group("pool");
                String suite = dataTableMatcher.group("suite");
                String test = dataTableMatcher.group("test");

                if (!allocUnique.contains(pool+suite+test)) {
                    AllocationType allocationType = new AllocationType(pool, suite, test);
                    allocationTypes.add(allocationType);

                    allocUnique.add(pool+suite+test);
                }
            }
        }

        if (!m_DataAllocationEngine.resolvePools(m_ServerName, allocationTypes)) {
            System.out.println("Error during resolve - " + m_DataAllocationEngine.getErrorMessage());
        }

        // 3) Replace values
        for (File f : files) {
            // 3.1) Parse to string
            String content = new String(Files.readAllBytes(f.toPath()));

            // 3.2) -> @Data [Index][Name] -> simple, retrieve value and replace
            Matcher matcher = dataRegex.matcher(content);
            while (matcher.find()) {
                String pool = matcher.group("pool");
                String suite = matcher.group("suite");
                String test = matcher.group("test");
                String rowIndex = matcher.group("rowIndex");
                String colName = matcher.group("colName");
                String allocName = matcher.group("allocName");

                // Get result
                DataAllocationResult allocResult = m_DataAllocationEngine.getDataResult(pool, suite, test);

                content = content.replaceAll(Pattern.quote(allocName), allocResult.getDataRows().get(Integer.parseInt(rowIndex)).get(colName).toString());

            }

            // 3.3) If row contains @DataTable -> Retrieve and copy down result with itterator
            Matcher dTablematcher = dataTableInitialRegex.matcher(content);
            while (dTablematcher.find()) {
                String dTableRow = dTablematcher.group();

                Matcher dTableRowMatcher = dataTableRegex.matcher(dTableRow);

                Integer noRows = null;
                ArrayList<String> rows = new ArrayList<String>();
                while (dTableRowMatcher.find()) {
                    String pool = dTableRowMatcher.group("pool");
                    String suite = dTableRowMatcher.group("suite");
                    String test = dTableRowMatcher.group("test");
                    String colName = dTableRowMatcher.group("colName");
                    String allocName = dTableRowMatcher.group("allocName");

                    // Get result
                    DataAllocationResult allocResult = m_DataAllocationEngine.getDataResult(pool, suite, test, ResultMergeMethod.MinimumProductLoopBack);

                    if (allocResult != null && noRows == null) {
                        noRows = allocResult.getDataRows().size();

                        for (int i = 0; i < noRows; i++) {
                            rows.add(dTableRow);
                        }
                    }

                    if (noRows != null) {
                        for (int i = 0; i < noRows; i++) {
                            try {
                                rows.set(i, rows.get(i).replaceAll(Pattern.quote(allocName), allocResult.getDataRows().get(i).get(colName).toString()));
                            } catch (Exception e) {
                                System.out.println("No value found in result set for test '" + test + "' in column '" + colName + "'");

                                rows.set(i, rows.get(i).replaceAll(Pattern.quote(allocName), ""));
                            }
                        }
                    }
                }

                // Insert
                content = content.replaceAll(Pattern.quote(dTableRow),String.join("\r\n", rows));
            }


            // 3.4) Write feature to resolved directory - replace if exists
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetLocation + f.getName()));
            writer.write(content);
            writer.close();
        }
    }
}
