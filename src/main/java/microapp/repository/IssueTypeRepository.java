package microapp.repository;

import microapp.domain.IssueType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IssueType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueTypeRepository extends ReactiveCrudRepository<IssueType, Long>, IssueTypeRepositoryInternal {
    @Override
    <S extends IssueType> Mono<S> save(S entity);

    @Override
    Flux<IssueType> findAll();

    @Override
    Mono<IssueType> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssueTypeRepositoryInternal {
    <S extends IssueType> Mono<S> save(S entity);

    Flux<IssueType> findAllBy(Pageable pageable);

    Flux<IssueType> findAll();

    Mono<IssueType> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IssueType> findAllBy(Pageable pageable, Criteria criteria);

}
