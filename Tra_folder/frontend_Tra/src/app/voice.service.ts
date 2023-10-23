import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VoiceService {
  generateVoice(text: string): Observable<string> {
    // Simulate an API call by returning a mock URL
    return of(`https://example.com/voice?text=${text}`);
  }

  private apiUrl = "http://localhost:8080/getvoice";

  constructor(private http: HttpClient) {}

  getData(text: string) {
    const params = new HttpParams().set('text', text); // Create query parameter

    return this.http.get<any>(this.apiUrl, { params });
  }





}