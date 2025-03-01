package rs.ac.uns.acs.nais.ColumnarDatabaseService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String KEYSPACE;

    @Value("${spring.data.cassandra.contact-points}")
    private String CONTACT_POINT;

    @Value("${spring.data.cassandra.port}")
    private int PORT;

    @Value("${spring.data.cassandra..schema-action}")
    private String SCHEMA_ACTION;

    @Override
    public String getContactPoints() {
        return CONTACT_POINT;
    }

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(SCHEMA_ACTION);
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(KEYSPACE)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication(3L));
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"rs.ac.uns.acs.nais.ColumnarDatabaseService.entity"};
    }

}