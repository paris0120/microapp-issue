package microapp.repository;

import microapp.domain.IssueAssignment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IssueAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueAssignmentRepository extends ReactiveCrudRepository<IssueAssignment, Long>, IssueAssignmentRepositoryInternal {
    Flux<IssueAssignment> findAllBy(Pageable pageable);

    @Override
    <S extends IssueAssignment> Mono<S> save(S entity);

    @Override
    Flux<IssueAssignment> findAll();

    @Override
    Mono<IssueAssignment> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssueAssignmentRepositoryInternal {
    <S extends IssueAssignment> Mono<S> save(S entity);

    Flux<IssueAssignment> findAllBy(Pageable pageable);

    Flux<IssueAssignment> findAll();

    Mono<IssueAssignment> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IssueAssignment> findAllBy(Pageable pageable, Criteria criteria);

}
