import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../shared/services/user.service';

@Component({
  selector: 'app-edit-user-modal',
  templateUrl: './edit-user-modal.component.html',
  styleUrls: ['./edit-user-modal.component.scss'],
  standalone: false
})
export class EditUserModalComponent {
  editUserForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditUserModalComponent>,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.editUserForm = this.fb.group({
      name: [data.name],
      email: [data.email],
      role: [data.role],
      id: [data.id] // Include ID in the form for saving
    });
  }

  save(): void {
    if (this.editUserForm.valid) {
      const updatedUser = this.editUserForm.value; // Get the updated user data from the form
      const userId = this.data.id; // Get the user ID from the injected data
      this.userService.saveUser(userId, updatedUser).subscribe(
        (response) => {
          //console.log('User updated successfully:', response);
          alert('User updated successfully!');
          this.dialogRef.close(updatedUser); // Close the modal and pass the updated data back
        },
        (error) => {
          //console.error('Error updating user:', error);
          alert('Failed to update user. Please try again.');
        }
      );
    } else {
      alert('Please fill out all required fields.');
    }
  }
}