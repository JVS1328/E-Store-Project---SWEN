import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { AppService } from '../app.service';
import { UAM } from '../uam';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
	errorMessage: string = "";
	username: string = "";
	password: string = "";
	reg_username: string = "";
	reg_password: string = "";
	nextUrl: string = "/";
	is_login: boolean = true; // to keep track of login/register
	
	constructor(private appService: AppService, private route: ActivatedRoute) { }

	ngOnInit(): void {
		this.nextUrl = new URLSearchParams(window.location.search).get("next")!;
	}

	login() {
		this.appService.loginUser(this.username, this.password).subscribe(lr => {
			if (!lr.success) {
				this.errorMessage = lr.message;
			} else {
				UAM.setToken(lr.token);
				UAM.setAdmin(lr.admin);
				document.location = this.nextUrl;
			}
		});
	}
	
	register() {
		this.appService.registerUser(this.reg_username, this.reg_password).subscribe(lr => {
			if (!lr.success) {
				this.errorMessage = lr.message;
			} else {
				UAM.setToken(lr.token);
				UAM.setAdmin(false);
				document.location = this.nextUrl;
			}
		});
	}

	/**
	 * To show Sign up form
	 */
	show_signup() {
		this.is_login = false;
	}

	/**
	 * To show login form
	 */
	show_login() {
		this.is_login = true;
	}
}