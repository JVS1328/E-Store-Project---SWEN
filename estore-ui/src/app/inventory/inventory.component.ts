import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../product/product.model';
import { AppService } from '../app.service';
import { UAM } from '../uam';

@Component({
	selector: 'app-inventory',
	templateUrl: './inventory.component.html',
	styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

	error: any;
	inventory: Product[];
	navigated = false;
	query: any = "";
	UAM = UAM;

	constructor(
		private appService: AppService,
		private route: ActivatedRoute
	) { }

	setHeading(text: string) {
		document.getElementById("heading")!.textContent = text;
	}

	ngOnInit(): void {
		// Load the inventory.
		this.navigated = true;
		this.query = new URLSearchParams(window.location.search).get("query");
		if (this.query) {
			this.appService.search(this.query).pipe(
				catchError((e) => {
					this.error = e;
					return of(undefined);
				})
			).subscribe(p => (this.inventory = p));
		} else {
			this.appService.getInventory().pipe(
				catchError((e) => {
					this.error = e;
					return of(undefined);
				})
			).subscribe(p => (this.inventory = p));
		}
	}

	newProduct() {
		let product: Product = {
			id: 0,
			name: "New Product",
			description: "Product Description",
			price: 0.00,
			quantity: 0,
			picture: "/assets/car.png"
		};
		this.appService.createProduct(product).subscribe(r => {window.location.reload(); r;});
	}
}
