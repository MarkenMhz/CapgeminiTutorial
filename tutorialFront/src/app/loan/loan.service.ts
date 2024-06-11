import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, throwError } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';

@Injectable({
    providedIn: 'root'
})
export class LoanService {

    constructor(
        private http: HttpClient
    ) { }

    getLoans(pageable: Pageable, clientName?: string, gameTitle?: string, date?: Date): Observable<LoanPage> {
        let filterParams = '';
        let url = 'http://localhost:8080/loan';

        if(clientName != null) {
            filterParams += 'clientName='+clientName+'&';
        }

        if(gameTitle != null) {
            filterParams += 'gameTitle='+gameTitle+'&';
        }

        if(date != null) {
            filterParams += 'date='+date.toISOString();
        }

        if(filterParams === '') {
            return this.http.post<LoanPage>(url, {pageable:pageable});    
        } else {
            url += '?' + filterParams;
            return this.http.post<LoanPage>(url, {pageable:pageable});
        }
    }

    saveLoan(loan: Loan): Observable<void> {

        let url = 'http://localhost:8080/loan';
        if (loan.id != null) url += '/'+loan.id;

        return this.http.put<void>(url, loan);
    }

    deleteLoan(idLoan : number): Observable<void> {
        return this.http.delete<void>('http://localhost:8080/loan/'+idLoan);
    }    

    getAllLoans(): Observable<Loan[]> {
        return this.http.get<Loan[]>('http://localhost:8080/loan');
    }

}