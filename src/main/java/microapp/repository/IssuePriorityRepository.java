package microapp.repository;

import microapp.domain.IssuePriority;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IssuePriority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuePriorityRepository extends ReactiveCrudRepository<IssuePriority, Long>, IssuePriorityRepositoryInternal {
    @Override
    <S extends IssuePriority> Mono<S> save(S entity);

    @Override
    Flux<IssuePriority> findAll();

    @Override
    Mono<IssuePriority> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssuePriorityRepositoryInternal {
    <S extends IssuePriority> Mono<S> save(S entity);

    Flux<IssuePriority> findAllBy(Pageable pageable);

    Flux<IssuePriority> findAll();

    Mono<IssuePriority> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IssuePriority> findAllBy(Pageable pageable, Criteria criteria);

}
