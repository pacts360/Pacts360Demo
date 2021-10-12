import { Component } from '@angular/core';
import { DemoService } from './demo.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Pacts Demo';
  url = '';

  constructor(private demoService: DemoService) { }
  postRequest() {
	this.demoService.makeRequest(this.url).subscribe(
		(res) => {
			alert('Response received. See log file.');
		}, (err) => {
			alert('An error occurred: ' + err);
		}
	)
  }
}
