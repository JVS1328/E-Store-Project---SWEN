import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from './product.model';
import { AppService } from '../app.service';
import { UAM } from '../uam';

@Component({
	selector: 'app-product',
	templateUrl: './product.component.html',
	styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {
	
	error: any;
	product: Product;
	navigated = false;
	UAM = UAM;

	BIDDABLE_LIMIT = 5;
	BID_WAIT = 800;
	timeRemaining: number = 0;
	
	constructor(
		private appService: AppService,
		private route: ActivatedRoute
	) { }

	ngOnInit(): void {
		this.route.params.forEach((params: Params) => {
			const id = +params['id'];
			this.navigated = true;
			this.appService.getProduct(id).pipe(
				catchError((e) => {
					this.error = e;
					return of(undefined);
				})
			).subscribe(p => (this.product = p));
		});
	}
	
	addToCart(): void {
		this.product.quantity -= 1;
		this.appService.editProduct(this.product).subscribe(r => r);
		this.appService.addCartItem(this.product).subscribe(r => r);
	}

	updateTime() {
		let input = ( <number> ( <unknown> (<HTMLInputElement>document.getElementById("bid")!).value))!;
		this.timeRemaining = Math.floor(this.BID_WAIT / input);
	}

	bid() {
		let input = ( <number> ( <unknown> (<HTMLInputElement>document.getElementById("bid")!).value))!;
		if (input < this.product.price) {
			// alert("Bid is too low!");
		} else {
			this.timeRemaining = Math.floor(this.BID_WAIT / input);
			
			var refreshID = setInterval( () =>
				{
					this.timeRemaining -= 1;
					if (this.timeRemaining <= 0) {
						this.addToCart();
						clearInterval(refreshID);
					}
				},
				1000
			);
		}
		
		
	}
}
