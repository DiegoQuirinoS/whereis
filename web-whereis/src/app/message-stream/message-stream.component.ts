import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';
import { Observable, of, Subject } from 'rxjs';
import { catchError, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-message-stream',
  templateUrl: './message-stream.component.html',
  styleUrls: ['./message-stream.component.css']
})
export class MessageStreamComponent implements OnInit, OnDestroy {

  private messages:any;

  private destroy$ = new Subject();

  constructor(private http: HttpClient, private rxStompService: RxStompService) { }

  ngOnInit(): void {
    this.messages = [];
    this.rxStompService.watch('/topic/messages')
      .pipe(takeUntil(this.destroy$))
      .subscribe((message: Message) => {
        console.log(JSON.parse(message.body));
        this.messages.push(JSON.parse(message.body));
        this.messages.slice(-5);
      })
  }

  ngOnDestroy(): void{
    this.destroy$.next(null);
    this.destroy$.unsubscribe();
  }

  private handleError(error: HttpErrorResponse): Observable<any>{
    return of(null);
  }
}
