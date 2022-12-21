import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';
import { UAM } from '../uam';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	current: String = "Home";
	loggedInUser: boolean = false;
	loggedInAdmin: boolean = false;
	UAM: any = UAM;

	constructor(router: Router, private appService: AppService) {
		// Set active Navbar element based on router path
		router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				this.setActive(event.url);
			}
		});
	}

	ngOnInit(): void {
		this.checkLoginAlive();
	}

	checkLoginAlive(): void {
		if (this.UAM.loggedIn()) {
			this.appService.checkAlive().subscribe(alive => {
				if (!alive.alive) {
					this.UAM.logout();
				}
			});
		}
	}

	setActive(input: String): void {
		this.current = input;
	}

	logout(): void {
		this.appService.logoutUser().subscribe(_ => {
			this.UAM.logout();
		});
	}
	
	currentHref(): string {
		return document.location.pathname;
	}
}
