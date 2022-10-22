import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeReply } from '../qe-reply.model';

@Component({
  selector: 'jhi-qe-reply-detail',
  templateUrl: './qe-reply-detail.component.html',
})
export class QeReplyDetailComponent implements OnInit {
  qeReply: IQeReply | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeReply }) => {
      this.qeReply = qeReply;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
