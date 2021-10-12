import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { throwError } from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class DemoService {

	headers = new HttpHeaders({
		'Content-Type': 'application/json',
		'Cache-Control': 'no-cache, no-store',
		'Pragma': 'no-cache',
		'Expires': '0'
	});


	handleError(error: HttpErrorResponse) {
		let errMsg = '';
		if (error.error instanceof ErrorEvent) {
			errMsg = 'An error occurred:' + error.error.message;
		} else {
			errMsg = `Backend returned code ${error.status}, ` +
				`body was: ${error.error}`;
		}
		return throwError(
			errMsg);
	}

	constructor(private http: HttpClient) { }

	makeRequest(operation: string): Observable<string> {
		let _url = 'postRequest?operation=' + operation;
		return this.http.get<string>(_url, { headers: this.headers }).pipe(
			catchError(this.handleError));
	}
}
