import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../product/product.model';
import { AppService } from '../app.service';
import { UAM } from '../uam';

@Component({
	selector: 'app-product',
	templateUrl: './product-editor.component.html',
	styleUrls: ['./product-editor.component.css']
})

export class ProductEditorComponent implements OnInit {
	
	error: any;
	product: Product;
	navigated = false;
	UAM = UAM;
	
	constructor(
		private appService: AppService,
		private route: ActivatedRoute
	) { }

	ngOnInit(): void {
		if (!this.UAM.isAdmin()) {
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
		} else if (this.UAM.loggedIn()) {
			document.location = "/?accessdenied=yup";
		} else {
			document.location = "/login?next="+document.location.pathname;
		}
	}
	
	save(): void {
		this.appService.editProduct(this.product).subscribe(r => {
			window.location.assign(window.location.href.split('/')[0] + "/products");
			r;
		});
	}
	
	delete(): void {
		this.appService.deleteProduct(this.product).subscribe(r => {
			window.location.assign(window.location.href.split('/')[0] + "/products");
			r;
		});

	}
}
