import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductVariantService} from '../../services/productVariantService';
import { ProductVariantDTO } from '../../models/productVariantDTO';
import { AddedProductVariantComponent} from '../added-product-variant/added-product-variant.component';

@Component({
  selector: 'app-panel-admin-product-variant',
  imports: [CommonModule, AddedProductVariantComponent],
  templateUrl: './panel-admin-product-variant.component.html',
  styleUrl: './panel-admin-product-variant.component.css'
})
export class PanelAdminProductVariantComponent implements OnInit {
  variants: ProductVariantDTO[] = [];
  selectedVariant: ProductVariantDTO | null = null;
  @ViewChild(AddedProductVariantComponent) modal!: AddedProductVariantComponent;

  constructor(private svc: ProductVariantService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.svc.getAll().subscribe({
      next: data => this.variants = data,
      error: err => console.error('Error cargando variantes:', err)
    });
  }

  onAdd() {
    this.selectedVariant = null;
    this.modal.mode = 'add';
    this.modal.variantToEdit = null;
    this.modal.resetFormFields();
    this.open();
  }

  onEdit(v: ProductVariantDTO) {
    this.selectedVariant = { ...v };
    this.modal.mode = 'edit';
    this.modal.variantToEdit = this.selectedVariant;
    this.open();
  }

  onDelete(id?: number) {
    if (!id || !confirm('¿Eliminar variante?')) return;
    this.svc.delete(id).subscribe(() => this.load());
  }

  onSaved() {
    this.load();
  }

  private open() {
    const el = document.getElementById('product-variant-modal');
    if (el) {
      // @ts-ignore
      new bootstrap.Modal(el, { keyboard: false }).show();
    }
  }
}
