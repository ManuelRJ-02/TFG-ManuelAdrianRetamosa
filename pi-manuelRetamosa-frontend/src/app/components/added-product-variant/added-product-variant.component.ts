import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';
import { ProductVariantDTO } from '../../models/productVariantDTO';
import { ProductVariantService} from '../../services/productVariantService';
import { ProductService} from '../../services/productService';
import { ProductDTO } from '../../models/productDTO';

declare var bootstrap: any;

@Component({
  selector: 'app-added-product-variant',
  imports: [CommonModule, FormsModule],
  templateUrl: './added-product-variant.component.html',
  styleUrl: './added-product-variant.component.css'
})
export class AddedProductVariantComponent implements OnInit, OnChanges {
  @Input() mode: 'add' | 'edit' = 'add';
  @Input() variantToEdit: ProductVariantDTO | null = null;
  @Output() variantSaved = new EventEmitter<void>();

  @ViewChild('fileInput', { static: true })
  fileInput!: ElementRef<HTMLInputElement>;

  products: ProductDTO[] = [];

  productId!: number;
  productVariantSize = '';
  color = '';
  price!: number;
  stock!: number;
  productVariantImage = '';
  loadingImage = false;

  errorMessages: string[] = [];

  constructor(
    private variantSvc: ProductVariantService,
    private productSvc: ProductService
  ) {}

  ngOnInit(): void {
    this.productSvc.getAll().subscribe({
      next: prods => this.products = prods,
      error: err => console.error('Error cargando productos:', err)
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['variantToEdit']) {
      this.resetFormFields();
    }
  }

  resetFormFields(): void {
    this.errorMessages = [];

    if (this.mode === 'edit' && this.variantToEdit) {
      const v = this.variantToEdit;
      this.productId = v.productId;
      this.productVariantSize = v.productVariantSize;
      this.color = v.color;
      this.price = v.price;
      this.stock = v.stock;
      this.productVariantImage = v.productVariantImage;
    } else {
      this.productId = undefined!;
      this.productVariantSize = '';
      this.color = '';
      this.price = undefined!;
      this.stock = undefined!;
      this.productVariantImage = '';
      if (this.fileInput?.nativeElement) {
        this.fileInput.nativeElement.value = '';
      }
    }
  }

  triggerFileSelect(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(evt: Event): void {
    const inp = evt.target as HTMLInputElement;
    if (!inp.files?.length) return;
    const file = inp.files[0];

    this.loadingImage = true;
    this.variantSvc.uploadImage(file).subscribe({
      next: url => {
        this.productVariantImage = url;
        this.loadingImage = false;
        inp.value = '';
      },
      error: () => {
        console.error('Error subiendo imagen variante');
        this.loadingImage = false;
        inp.value = '';
      }
    });
  }

  onSubmit(): void {
    this.errorMessages = [];

    const payload: ProductVariantDTO = {
      productId: this.productId,
      productVariantSize: this.productVariantSize,
      color: this.color,
      price: this.price,
      stock: this.stock,
      productVariantImage: this.productVariantImage,
      available: false,
      productName: '',
      productDescription: ''
    };

    const obs = this.mode === 'edit' && this.variantToEdit?.id != null
      ? this.variantSvc.update(this.variantToEdit.id, payload)
      : this.variantSvc.create(payload);

    obs.subscribe({
      next: () => this.closeAndEmit(),
      error: err => {
        const body = err.error;
        if (Array.isArray(body?.errors)) {
          this.errorMessages = body.errors;
        } else if (body?.message) {
          this.errorMessages = [body.message];
        } else {
          this.errorMessages = ['Error inesperado al guardar variante'];
        }
      }
    });
  }

  private closeAndEmit(): void {
    const modalEl = document.getElementById('product-variant-modal');
    if (modalEl) {
      // @ts-ignore
      let modal = bootstrap.Modal.getInstance(modalEl);
      if (!modal) {
        // @ts-ignore
        modal = new bootstrap.Modal(modalEl);
      }
      modal.hide();
    }
    this.variantSaved.emit();
  }
}
