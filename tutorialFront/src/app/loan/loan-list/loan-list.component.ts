import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';
import { Client } from 'src/app/client/model/Client';
import { ClientService } from 'src/app/client/client.service';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';

import * as _moment from 'moment';

export const MY_FORMATS = {
    parse: {
      dateInput: 'LL',
    },
    display: {
      dateInput: 'LL',
      monthYearLabel: 'MMM YYYY',
      dateA11yLabel: 'LL',
      monthYearA11yLabel: 'MMMM YYYY',
    },
  };

@Component({
selector: 'app-loan-list',
templateUrl: './loan-list.component.html',
styleUrls: ['./loan-list.component.scss'],
})
export class LoanListComponent implements OnInit {

    loan: Loan[];
    clients: Client[];
    games: Game[];

    filterClient: Client;
    filterGame: Game;
    filterDate: Date;

    pageNumber: number = 0;
    pageSize: number = 5;
    totalElements: number = 0;

    dataSource = new MatTableDataSource<Loan>();
    displayedColumns: string[] = ['id', 'client.name', 'game.title', 'startDate', 'endDate', 'action'];

    constructor(
        private loanService: LoanService,
        private clientService: ClientService,
        private gameService: GameService,
        public dialog: MatDialog,
    ) { }

    ngOnInit(): void {
        this.loadPage();
        this.loadClients();
        this.loadGames();
    }

    onCleanFilter(): void {
        this.filterGame = null;
        this.filterClient = null;
        this.filterDate = null;

        this.onSearch();
    }

    onSearch(): void {

        let pageable : Pageable =  {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            sort: [{
                property: 'id',
                direction: 'ASC'
            }]
        }

        let clientName = this.filterClient ? this.filterClient.name : null;
        let gameTitle = this.filterGame ? this.filterGame.title : null;
        let date: Date = this.filterDate ? new Date(this.filterDate) : null;

        this.loanService.getLoans(pageable, clientName, gameTitle, date).subscribe(data => {
            this.dataSource.data = data.content;
            this.pageNumber = data.pageable.pageNumber;
            this.pageSize = data.pageable.pageSize;
            this.totalElements = data.totalElements;
        });
    }

    loadPage(event?: PageEvent) {

        let pageable : Pageable =  {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            sort: [{
                property: 'id',
                direction: 'ASC'
            }]
        }

        if (event != null) {
            pageable.pageSize = event.pageSize
            pageable.pageNumber = event.pageIndex;
        }

        this.loanService.getLoans(pageable).subscribe(data => {
            this.dataSource.data = data.content;
            this.pageNumber = data.pageable.pageNumber;
            this.pageSize = data.pageable.pageSize;
            this.totalElements = data.totalElements;
        });

    }

    loadClients(): void {
        this.clientService.getClients().subscribe(
            clients => {
                this.clients = clients;
            }
        );
    }

    loadGames(): void {
        this.gameService.getGames().subscribe(
            games => {
                this.games = games;
            }
        );
    }

    createLoan() {      
        const dialogRef = this.dialog.open(LoanEditComponent, {
            data: {}
        });

        dialogRef.afterClosed().subscribe(result => {
            this.ngOnInit();
        });      
    }  

    editLoan(loan: Loan) {    
        const dialogRef = this.dialog.open(LoanEditComponent, {
            data: { loan: loan }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.ngOnInit();
        });    
    }

    deleteLoan(loan: Loan) {    
        const dialogRef = this.dialog.open(DialogConfirmationComponent, {
            data: { title: "Eliminar préstamo", description: "Atención si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?" }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.loanService.deleteLoan(loan.id).subscribe(result =>  {
                    this.ngOnInit();
                }); 
            }
        });
    }
}

function provideMomentDateAdapter(MY_FORMATS: { parse: { dateInput: string; }; display: { dateInput: string; monthYearLabel: string; dateA11yLabel: string; monthYearA11yLabel: string; }; }): import("@angular/core").Provider {
    throw new Error('Function not implemented.');
}
