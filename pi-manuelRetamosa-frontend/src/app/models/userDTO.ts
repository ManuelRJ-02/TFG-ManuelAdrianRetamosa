export interface UserDTO {
  id?: number;
  userName: string;
  surname: string;
  email: string;
  userPassword: string;
  phoneNumber?: string;
  avatar?: string;
  roles?: string[];
}
