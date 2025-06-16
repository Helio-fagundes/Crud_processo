
const API_BASE_URL = 'http://localhost:8080';

interface Client {
  id: string;
  name: string;
  socialSecurity: string;
  phone: string;
  email: string;
  address: string;
  number: string;
  complement: string;
  neighborhood: string;
  city: string;
  state: string;
}

interface Vehicle {
  id: string;
  brand: string;
  model: string;
  year: string;
  color: string;
  plate: string;
  chassis: string;
  fuelType: string;
  price: string;
  status: string;
  released: boolean;
}

interface Seller {
  id: string;
  name: string;
  cpf: string;
  phone: string;
  email: string;
  manager: boolean;
}

interface Sale {
  id: string;
  client: string | { name: string; cpf?: string; phone?: string };
  vehicle: string | { model: string; color?: string; plate?: string };
  seller: string | { name: string; cpf?: string; phone?: string };
  saleDate: string;
  salePrice: number;
  installmentQuantity: number;
}

interface PaymentClient {
  name: string;
  cpf: string;
  phone: string;
}

interface PaymentVehicle {
  model: string;
  color: string;
  plate: string;
}

interface PaymentSeller {
  name: string;
  cpf: string;
  phone: string;
}

interface Payment {
  id: string;
  client: PaymentClient;
  vehicle: PaymentVehicle;
  seller: PaymentSeller;
  saleDate: string | null;
  salePrice: number | null;
  installmentQuantity: number;
  saleId: string | null;
  paymentType: string;
  paymentMethod: string;
  installment: boolean;
  amount: number;
  received: boolean;
  movement: string;
}

interface ApiResponse<T> {
  data?: T;
  message?: string;
  status?: number;
}

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    
    const config: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    const response = await fetch(url, config);
    
    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw {
        status: response.status,
        message: errorData.message || 'Erro na requisição',
        ...errorData
      };
    }

    return response.json();
  }

  // Client endpoints
  async getClients(): Promise<Client[]> {
    return this.request<Client[]>('/clients');
  }

  async createClient(clientData: any) {
    return this.request('/clients', {
      method: 'POST',
      body: JSON.stringify(clientData),
    });
  }

  // Vehicle endpoints
  async getVehicles(): Promise<Vehicle[]> {
    return this.request<Vehicle[]>('/vehicles');
  }

  async createVehicle(vehicleData: any) {
    return this.request('/vehicles', {
      method: 'POST',
      body: JSON.stringify(vehicleData),
    });
  }

  // Seller endpoints
  async getSellers(): Promise<Seller[]> {
    return this.request<Seller[]>('/sellers');
  }

  async createSeller(sellerData: any) {
    return this.request('/sellers', {
      method: 'POST',
      body: JSON.stringify(sellerData),
    });
  }

  // Sale endpoints
  async getSales(): Promise<Sale[]> {
    return this.request<Sale[]>('/sales');
  }

  async createSale(saleData: any) {
    return this.request('/sales', {
      method: 'POST',
      body: JSON.stringify(saleData),
    });
  }

  // Payment endpoints
  async getPayments(): Promise<Payment[]> {
    return this.request<Payment[]>('/payments');
  }

  async createPayment(paymentData: any) {
    return this.request('/payments', {
      method: 'POST',
      body: JSON.stringify(paymentData),
    });
  }
}

export const apiService = new ApiService();
