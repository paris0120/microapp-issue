package microapp.repository;

import microapp.domain.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Issue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueRepository extends ReactiveCrudRepository<Issue, Long>, IssueRepositoryInternal {
    Flux<Issue> findAllBy(Pageable pageable);

    @Override
    <S extends Issue> Mono<S> save(S entity);

    @Override
    Flux<Issue> findAll();

    @Override
    Mono<Issue> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssueRepositoryInternal {
    <S extends Issue> Mono<S> save(S entity);

    Flux<Issue> findAllBy(Pageable pageable);

    Flux<Issue> findAll();

    Mono<Issue> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Issue> findAllBy(Pageable pageable, Criteria criteria);

}
