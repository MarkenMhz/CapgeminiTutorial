package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, String clientName, String gameTitle, Date date) {
        Specification<Loan> specification = Specification.where(null);

        if (gameTitle != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("game.title", ":", gameTitle)));
        }

        if (clientName != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("client.name", ":", clientName)));
        }

        if (date != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("startDate", ":<", date)));
            specification = specification.and(new LoanSpecification(new SearchCriteria("endDate", ":>", date)));
        }

        return this.loanRepository.findAll(specification, dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, LoanDto dto) throws Exception {
        Loan loan;
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        Long differenceDays = endDate.getTime() - startDate.getTime();
        differenceDays = differenceDays / (1000 * 60 * 60 * 24); //Pasamos de milisegundos a días

        //Validacion fechas
        if (endDate.before(startDate)) {
            throw new Exception("La fecha de fin del prestamo no puede ser anterior a la inicio del mismo");
        }

        //Validacion de los 14 dias
        if (differenceDays > 14) {
            throw new Exception("El prestamo es más largo de 14 dias");
        }

        //Validación juego ya prestado
        List<Loan> existingLoans = loanRepository.findIfGameIsAllreadyOnALoan(dto.getClient().getId(), dto.getGame().getId(), startDate, endDate);

        if (existingLoans.stream().anyMatch(existingLoan -> !existingLoan.getClient().equals(dto.getClient()))) {
            throw new Exception("El mismo juego ya esta prestado a otro cliente el mismo día");
        }

        //Validación cliente ya tiene prestamo
        int clientLoans = loanRepository.countClientLoansInThePeriod(dto.getClient().getId(), dto.getStartDate(), dto.getEndDate());

        System.out.printf("ClientLoans: " + clientLoans);

        if (clientLoans >= 1)
            throw new Exception("El cliente ya tiene un prestamo en esas fechas");

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, loan, "id", "client", "game");
        loan.setClient(clientService.get(dto.getClient().getId()));
        loan.setGame(gameService.get(dto.getGame().getId()));

        this.loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exist");
        }

        this.loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }
}
