import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeoService {

  constructor(private httpClient: HttpClient) { }

  public get():Observable<any>{
    return this.httpClient
            .get("http://localhost:9080/api/geo/");          
  }
}
