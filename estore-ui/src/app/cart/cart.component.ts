import { Cart } from './cart.model';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../product/product.model';
import { AppService } from '../app.service';
import { UAM } from "../uam";
import { Observable } from "rxjs";

import { flatMap } from 'rxjs/operators';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit {

  error: any;
	cart: Product[];
	navigated = false;

	constructor(
		private appService: AppService,
		private route: ActivatedRoute
	) { }


	ngOnInit(): void {
		// Load the cart.
		this.appService.getCartItems().pipe(
			catchError((e) => {
				this.error = e;
				console.log(e);
				return of(undefined);
			})
		).subscribe(p => (this.cart = p));

	}

	removeItem(item : Product) {
		
		/*
		this.appService.getProduct(item.id).pipe(
			flatMap(product => {
				console.log(product);
				product.quantity += 1;
				console.log(product);
				return this.appService.editProduct(product);
			})
		).subscribe( r => r );
		*/

		this.appService.getProduct(item.id).subscribe( product => {
			product.quantity += 1;
			this.appService.editProduct(product).subscribe(s => {
				this.appService.removeCartItem(item).subscribe(r => {window.location.reload(); r;});
			});
		});


		
		
	}

	checkout(cart : Product[]) {
		// For each item in the cart.
		for (let item of cart) {
			// Remove the item.
			this.removeItem(item);
		}
	}

}