import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClientService } from 'src/app/client/client.service';
import { Client } from 'src/app/client/model/Client';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { Loan } from '../model/Loan';
import { LoanService } from '../loan.service';
import { MatDatepicker, MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
    selector: 'app-loan-edit',
    templateUrl: './loan-edit.component.html',
    styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent implements OnInit {

    loan: Loan; 
    games: Game[];
    clients: Client[];
    errorSavingStr: string;
    errorSaving: boolean;
    

    constructor(
        public dialogRef: MatDialogRef<LoanEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private loanService: LoanService,
        private gameService: GameService,
        private clientService: ClientService,
    ) { }

    ngOnInit(): void {
        if (this.data.loan != null) {
            this.loan = Object.assign({}, this.data.loan);
        }
        else {
            this.loan = new Loan();
        }

        this.gameService.getGames().subscribe(
            games => {
                this.games = games;

                if (this.loan.game != null) {
                    let gameFilter: Game[] = games.filter(game => game.id == this.data.loan.game.id);
                    if (gameFilter != null) {
                        this.loan.game = gameFilter[0];
                    }
                }
            }
        );

        this.clientService.getClients().subscribe(
            clients => {
                this.clients = clients

                if (this.loan.client != null) {
                    let clientFilter: Client[] = clients.filter(client => client.id == this.data.loan.client.id);
                    if (clientFilter != null) {
                        this.loan.client = clientFilter[0];
                    }
                }
            }
        );
    }

    onSave() {
        this.loanService.saveLoan(this.loan).subscribe(result => {
            this.dialogRef.close();
        },
        error => {
            this.errorSaving = true;
            this.errorSavingStr = error.error;
        });    
    }  

    onClose() {
        this.dialogRef.close();
    }
}