import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {UserDTO} from '../../models/userDTO';
import { SessionService} from '../../services/SessionService';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  constructor(private sessionService: SessionService) {}

  user: UserDTO | null = null;

  ngOnInit() {
    this.user = this.sessionService.getUser();
  }

}
