package ltd.highsoft.frameworks.persistence.mongo;

import ltd.highsoft.frameworks.domain.core.*;
import ltd.highsoft.frameworks.test.container.WithTestContainers;
import ltd.highsoft.frameworks.test.mongo.*;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@WithTestContainers(containers = MongoContainer.class)
public class MongoRepositoryTest extends MongoTest {

    private MongoTemplate mongoTemplate;
    private MongoRepository<MongoTestAggregate, TestAggregate> repository;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(mongoClient(), databaseName());
        repository = new MongoRepository<>(mongoTemplate, MongoTestAggregate.class, MongoTestAggregate::new, MongoTestAggregate::asDomain);
    }

    @Test
    void should_be_able_to_put_aggregates_to_database() {
        repository.put(new TestAggregate("1", "hello"));
        assertThat(mongoTemplate.findById("1", MongoTestAggregate.class)).isEqualTo(new MongoTestAggregate(new TestAggregate("1", "hello")));
    }

    @Test
    void should_be_able_to_put_batch_of_aggregates_to_database() {
        repository.putAll(List.of(new TestAggregate("1", "hello"), new TestAggregate("2", "hello2")));
        assertThat(mongoTemplate.findById("1", MongoTestAggregate.class)).isEqualTo(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(mongoTemplate.findById("2", MongoTestAggregate.class)).isEqualTo(new MongoTestAggregate(new TestAggregate("2", "hello2")));
    }

    @Test
    void should_be_able_to_get_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(repository.get("1")).isEqualTo(new TestAggregate("1", "hello"));
    }

    @Test
    void should_failed_to_get_aggregate_from_database_when_aggregate_not_found() {
        Throwable throwable = catchThrowable(() -> repository.get("not-existed-id"));
        assertThat(throwable).isInstanceOf(AggregateNotFoundException.class);
        assertThat(throwable).hasMessage("error.aggregate-not-found: [MongoTestAggregate, not-existed-id]");
    }

    @Test
    void should_be_able_to_get_aggregates_from_database_by_queries() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(repository.get(query(where("id").is("1")))).isEqualTo(new TestAggregate("1", "hello"));
    }

    @Test
    void should_failed_to_get_aggregate_from_database_by_query_when_aggregate_not_found() {
        Throwable throwable = catchThrowable(() -> repository.get(query(where("id").is("not-existed-id"))));
        assertThat(throwable).isInstanceOf(AggregateNotFoundException.class);
        assertThat(throwable).hasMessage("error.aggregate-not-found: [MongoTestAggregate, Query: { \"id\" : \"not-existed-id\"}, Fields: {}, Sort: {}]");
    }

    @Test
    void should_be_able_to_get_optional_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(repository.getOptional("1")).isEqualTo(Optional.of(new TestAggregate("1", "hello")));
    }

    @Test
    void should_be_able_to_get_optional_aggregates_from_database_when_aggregate_not_exist() {
        assertThat(repository.getOptional("not-exist")).isEqualTo(Optional.empty());
    }

    @Test
    void should_be_able_to_get_optional_aggregates_from_database_by_queries() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(repository.getOptional(query(where("id").is("1")))).isEqualTo(Optional.of(new TestAggregate("1", "hello")));
    }

    @Test
    void should_be_able_to_get_optional_aggregates_from_database_by_queries_when_aggregate_not_exist() {
        assertThat(repository.getOptional(query(where("id").is("not-exist")))).isEqualTo(Optional.empty());
    }

    @Test
    void should_be_able_to_remove_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        repository.remove("1");
        assertThat(mongoTemplate.findById("1", MongoTestAggregate.class)).isNull();
    }

    @Test
    void should_be_able_to_remove_all_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        repository.removeAll();
        assertThat(mongoTemplate.findById("1", MongoTestAggregate.class)).isNull();
    }

    @Test
    void should_be_able_to_remove_all_matched_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello2")));
        repository.removeAll(query(where("name").regex("hello")));
        assertThat(mongoTemplate.count(new Query(), MongoTestAggregate.class)).isEqualTo(0);
    }

    @Test
    void should_be_able_to_remove_aggregates_from_database_by_ids() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello2")));
        repository.remove(List.of("1", "2"));
        assertThat(mongoTemplate.count(new Query(), MongoTestAggregate.class)).isEqualTo(0);
    }

    @Test
    void should_be_able_to_find_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello3")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello1")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("3", "hello2")));
        assertThat(repository.list(query(where("name").regex("hello")))).hasSize(3);
    }

    @Test
    void should_be_able_to_find_paged_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello3")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello1")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("3", "hello2")));
        Page<TestAggregate> found = repository.list(query(where("name").regex("hello")), PageRequest.of(1, 1, by("name").descending()));
        assertThat(found.content()).hasSize(1);
        assertThat(found.content()).containsExactly(new TestAggregate("3", "hello2"));
    }

    @Test
    void should_be_able_to_find_aggregates_from_database_as_streams() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello3")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello1")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("3", "hello2")));
        assertThat(repository.stream(query(where("name").regex("hello")))).hasSize(3);
    }

    @Test
    void should_be_able_to_count_aggregates_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("2", "hello2")));
        assertThat(repository.count(query(where("name").regex("hello")))).isEqualTo(2);
    }

    @Test
    void should_be_able_to_test_aggregate_existence_from_database() {
        mongoTemplate.save(new MongoTestAggregate(new TestAggregate("1", "hello")));
        assertThat(repository.exists(Query.query(where("id").is("1")))).isTrue();
        assertThat(repository.exists(Query.query(where("id").is("2")))).isFalse();
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(MongoTestAggregate.class);
    }

}