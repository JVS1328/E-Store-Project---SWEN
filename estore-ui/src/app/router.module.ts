import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { ProductComponent } from './product/product.component';
import { CartComponent } from './cart/cart.component';
import { InventoryComponent } from './inventory/inventory.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProductEditorComponent } from './product-editor/product-editor.component';

const routes: Routes = [
  { path: 'products', component: InventoryComponent },
  { path: 'cart', component: CartComponent},
  { path: 'products/:id', component: ProductComponent },
  { path: 'products/:id/edit', component: ProductEditorComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {

}
