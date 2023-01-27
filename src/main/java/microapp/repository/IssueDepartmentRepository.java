package microapp.repository;

import microapp.domain.IssueDepartment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IssueDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueDepartmentRepository extends ReactiveCrudRepository<IssueDepartment, Long>, IssueDepartmentRepositoryInternal {
    @Override
    <S extends IssueDepartment> Mono<S> save(S entity);

    @Override
    Flux<IssueDepartment> findAll();

    @Override
    Mono<IssueDepartment> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IssueDepartmentRepositoryInternal {
    <S extends IssueDepartment> Mono<S> save(S entity);

    Flux<IssueDepartment> findAllBy(Pageable pageable);

    Flux<IssueDepartment> findAll();

    Mono<IssueDepartment> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IssueDepartment> findAllBy(Pageable pageable, Criteria criteria);

}
