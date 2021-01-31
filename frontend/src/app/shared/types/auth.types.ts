export const AuthTypes = {
  LOGGED_IN: 'LOGGED_IN',
  LOGGED_OUT: 'LOGGED_OUT',
  LOADING: 'LOADING',
};

export interface LoginResponse {
  email: string;
  name: string;
  userId: string;
}
