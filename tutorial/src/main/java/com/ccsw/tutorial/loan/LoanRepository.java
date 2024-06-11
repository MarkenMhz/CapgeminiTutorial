package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findAll(Specification<Loan> specification, Pageable pageable);

    @Query("SELECT l FROM Loan l WHERE l.game.id = :gameId AND :startDate <= l.endDate AND :endDate >= l.startDate AND l.client.id <> :clientId")
    List<Loan> findIfGameIsAllreadyOnALoan(@Param("clientId") Long clientId, @Param("gameId") Long gameID, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.client.id = :clientId AND :startDate <= l.endDate AND :endDate >= l.startDate")
    int countClientLoansInThePeriod(@Param("clientId") Long clientId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
