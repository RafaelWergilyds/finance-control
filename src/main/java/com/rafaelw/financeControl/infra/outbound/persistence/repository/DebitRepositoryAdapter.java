package com.rafaelw.financeControl.infra.outbound.persistence.repository;

import com.rafaelw.financeControl.infra.outbound.persistence.repository.mappers.DebitMapper;
import com.rafaelw.financeControl.application.ports.out.DebitRepositoryPort;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.PaginatedResponse;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.Pagination;
import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.application.dto.DebitFilterDTO;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.DebitPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.specifications.DebitSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DebitRepositoryAdapter implements DebitRepositoryPort {

    @Autowired
    private JpaDebitRepository jpaDebitRepository;

    @Autowired
    private DebitMapper debitMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Debit save(Debit debit) {
        DebitPersist debitPersist = debitMapper.toPersist(debit);
        return debitMapper.toDomain(jpaDebitRepository.save(debitPersist));
    }

    @Override
    public PaginatedResponse<Debit> findAllByUser(Long userId, DebitFilterDTO filter, Integer pageSize, Long cursor) {
        Specification<DebitPersist> spec = (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);

        spec = spec.and(DebitSpecification.addFilter(filter));

        return Pagination.paginate(jpaDebitRepository,
                spec, pageSize, cursor, debitMapper::toDomain, "id");
    }

    @Override
    public Optional<Debit> findByIdAndUserId(Long id, Long userId) {
        return jpaDebitRepository.findByIdAndUserId(id, userId).map(debitMapper::toDomain);
    }

    @Override
    public BigDecimal getTotalSumAmount(Long userId, DebitFilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
        Root<DebitPersist> root = query.from(DebitPersist.class);

        Predicate filterPredicate = DebitSpecification.addFilter(filter).toPredicate(root, query, cb);
        Predicate userPredicate = cb.equal(root.get("user").get("id"), userId);

        query.select(cb.sum(root.get("amount"))).where(cb.and(userPredicate, filterPredicate));

        BigDecimal total = entityManager.createQuery(query).getSingleResult();

        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void delete(Long id) {
        jpaDebitRepository.deleteById(id);
    }
}
