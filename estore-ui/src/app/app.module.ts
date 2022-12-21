import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductComponent } from './product/product.component';
import { CartComponent } from './cart/cart.component';
import { InventoryComponent } from './inventory/inventory.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NavSearchComponent } from './nav-search/nav-search.component';
import { LoginComponent } from './login/login.component';
import { ProductEditorComponent } from './product-editor/product-editor.component';
import { AppRoutingModule } from './router.module';
import { AppService } from './app.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
	declarations: [
		AppComponent,
		ProductComponent,
		CartComponent,
		InventoryComponent,
		NavbarComponent,
		NavSearchComponent,
		LoginComponent,
		ProductEditorComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		ReactiveFormsModule,
		AppRoutingModule,
		HttpClientModule
	],
	providers: [
		AppService
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
