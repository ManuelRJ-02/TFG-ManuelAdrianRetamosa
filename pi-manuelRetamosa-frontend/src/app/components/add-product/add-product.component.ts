import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';
import { ProductDTO }    from '../../models/productDTO';
import { ProductService} from '../../services/productService';

@Component({
  selector: 'app-add-product',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent implements OnChanges {
  @Input()  mode: 'add'|'edit'     = 'add';
  @Input()  productToEdit: ProductDTO|null = null;
  @Output() productSaved = new EventEmitter<void>();

  @ViewChild('fileInput', { static: true })
  fileInput!: ElementRef<HTMLInputElement>;
  productName        = '';
  productDescription = '';
  priceBase!: number;
  genericImage       = '';
  category           = '';
  loadingImage       = false;

  constructor(private svc: ProductService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['productToEdit']) {
      this.resetFormFields();
    }
  }

  public resetFormFields(): void {
    if (this.mode==='edit' && this.productToEdit) {
      const p = this.productToEdit!;
      this.productName        = p.productName;
      this.productDescription = p.productDescription;
      this.priceBase          = p.priceBase;
      this.genericImage       = p.genericImage;
      this.category           = p.category;
    } else {
      this.productName = '';
      this.productDescription = '';
      this.priceBase = undefined!;
      this.genericImage = '';
      this.category = '';
      this.fileInput.nativeElement.value = '';
    }
  }

  triggerFileSelect() {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(evt: Event) {
    const inp = evt.target as HTMLInputElement;
    if (!inp.files?.length) return;
    const f = inp.files[0];
    this.loadingImage = true;
    this.svc.uploadImage(f).subscribe({
      next: url => {
        this.genericImage = url;
        this.loadingImage = false;
        inp.value = '';
      },
      error: _ => {
        console.error('Error subiendo imagen');
        this.loadingImage = false;
        inp.value = '';
      }
    });
  }

  onSubmit() {
    const payload: ProductDTO = {
      productName:        this.productName,
      productDescription: this.productDescription,
      priceBase:          this.priceBase,
      genericImage:       this.genericImage,
      category:           this.category
    };
    if (this.mode==='edit' && this.productToEdit?.id!=null) {
      payload.id = this.productToEdit.id;
      this.svc.update(payload.id, payload)
        .subscribe(() => this.closeAndEmit());
    } else {
      this.svc.create(payload)
        .subscribe(() => this.closeAndEmit());
    }
  }

  private closeAndEmit() {
    const el = document.getElementById('product-modal');
    if (el) {
      // @ts-ignore
      bootstrap.Modal.getInstance(el).hide();
    }
    this.productSaved.emit();
  }
}
