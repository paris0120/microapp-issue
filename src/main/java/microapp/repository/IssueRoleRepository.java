package microapp.repository;

import microapp.domain.IssueRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IssueRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueRoleRepository extends ReactiveCrudRepository<IssueRole, Long>, IssueRoleRepositoryInternal {
    @Override
    <S extends IssueRole> Mono<S> save(S entity);

    @Override
    Flux<IssueRole> findAll();

    @Override
    Mono<IssueRole> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssueRoleRepositoryInternal {
    <S extends IssueRole> Mono<S> save(S entity);

    Flux<IssueRole> findAllBy(Pageable pageable);

    Flux<IssueRole> findAll();

    Mono<IssueRole> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IssueRole> findAllBy(Pageable pageable, Criteria criteria);

}
