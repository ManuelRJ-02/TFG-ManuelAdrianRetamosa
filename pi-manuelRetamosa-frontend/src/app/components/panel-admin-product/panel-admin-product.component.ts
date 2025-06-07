import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService} from '../../services/productService';
import { ProductDTO } from '../../models/productDTO';
import { AddProductComponent } from '../add-product/add-product.component';

@Component({
  selector: 'app-panel-admin-product',
  imports: [CommonModule, AddProductComponent],
  templateUrl: './panel-admin-product.component.html',
  styleUrl: './panel-admin-product.component.css'
})
export class PanelAdminProductComponent implements OnInit {
  products: ProductDTO[] = [];
  selectedProduct: ProductDTO|null = null;

  @ViewChild(AddProductComponent) productModal!: AddProductComponent;

  constructor(private svc: ProductService) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.svc.getAll().subscribe({
      next: data => this.products = data,
      error: err => console.error('Error cargando productos:',err)
    });
  }

  onAddProduct() {
    this.selectedProduct = null;
    this.productModal.mode = 'add';
    this.productModal.productToEdit = null;
    this.productModal.resetFormFields();
    this.openModal();
  }

  onEditProduct(p: ProductDTO) {
    this.selectedProduct = { ...p };
    this.productModal.mode = 'edit';
    this.productModal.productToEdit = this.selectedProduct;
    this.openModal();
  }

  onDeleteProduct(id: number|undefined) {
    if (!id) return;
    if (!confirm('¿Eliminar este producto?')) return;
    this.svc.delete(id).subscribe(() => this.loadProducts());
  }

  onProductSaved() {
    this.loadProducts();
  }

  private openModal() {
    const el = document.getElementById('product-modal');
    if (el) {
      // @ts-ignore
      new bootstrap.Modal(el,{keyboard:false}).show();
    }
  }
}
