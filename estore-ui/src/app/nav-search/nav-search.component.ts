import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../product/product.model';
import { AppService } from '../app.service';

@Component({
    selector: 'nav-search',
    templateUrl: './nav-search.component.html',
    styleUrls: ['./nav-search.component.css']
})
export class NavSearchComponent implements OnInit {

    error: any;
    searchQuery: string = '';
    inventory: Product[];

    constructor(private appService: AppService, private route: ActivatedRoute) {

    }

    doSearch() {
        this.appService.search(this.searchQuery).pipe(
            catchError((e) => {
                this.error = e;
                return of(undefined);
            })
        ).subscribe(p => (this.inventory = p));
        window.location.href = `products?query=${this.searchQuery}`;

    }

    ngOnInit(): void {
    }
}
