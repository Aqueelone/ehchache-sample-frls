import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISLA, SLA } from 'app/shared/model/sla.model';
import { SLAService } from './sla.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-sla-update',
  templateUrl: './sla-update.component.html'
})
export class SLAUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    rts: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sLAService: SLAService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sLA }) => {
      this.updateForm(sLA);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sLA: ISLA) {
    this.editForm.patchValue({
      id: sLA.id,
      rts: sLA.rts,
      userId: sLA.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sLA = this.createFromForm();
    if (sLA.id !== undefined) {
      this.subscribeToSaveResponse(this.sLAService.update(sLA));
    } else {
      this.subscribeToSaveResponse(this.sLAService.create(sLA));
    }
  }

  private createFromForm(): ISLA {
    return {
      ...new SLA(),
      id: this.editForm.get(['id']).value,
      rts: this.editForm.get(['rts']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISLA>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
