import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITicket, Ticket } from 'app/shared/model/ticket.model';
import { TicketService } from './ticket.service';

@Component({
  selector: 'jhi-ticket-update',
  templateUrl: './ticket-update.component.html'
})
export class TicketUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    description: [],
    status: []
  });

  constructor(protected ticketService: TicketService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ticket }) => {
      this.updateForm(ticket);
    });
  }

  updateForm(ticket: ITicket) {
    this.editForm.patchValue({
      id: ticket.id,
      description: ticket.description,
      status: ticket.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ticket = this.createFromForm();
    if (ticket.id !== undefined) {
      this.subscribeToSaveResponse(this.ticketService.update(ticket));
    } else {
      this.subscribeToSaveResponse(this.ticketService.create(ticket));
    }
  }

  private createFromForm(): ITicket {
    return {
      ...new Ticket(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicket>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
