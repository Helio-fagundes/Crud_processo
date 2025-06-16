
interface APIError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  details: null | any;
}

export const handleAPIError = (error: any): string => {
  if (error.status === 400 && error.message) {
    return error.message;
  }
  
  return "Erro inesperado. Tente novamente.";
};

export const isAPIError = (error: any): error is APIError => {
  return error && typeof error === 'object' && 'status' in error && 'message' in error;
};
