import { Component } from '@angular/core';
import { UAM } from './uam';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})

export class AppComponent {
	UAM = UAM;
	title = 'Our eStore!';
	path = document.location.pathname;
}
