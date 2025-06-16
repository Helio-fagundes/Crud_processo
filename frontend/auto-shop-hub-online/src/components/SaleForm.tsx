import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import { ShoppingCart } from "lucide-react";
import { handleAPIError } from "@/utils/apiErrorHandler";
import { apiService } from "@/services/api";

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

interface SaleFormData {
  client: string;
  vehicle: string;
  seller: string;
  saleDate: string;
  salePrice: string;
  installmentQuantity: string;
}

interface SaleAPIData {
  client: string;
  vehicle: string;
  seller: string;
  saleDate: string;
  salePrice: number;
  installmentQuantity: number;
}

const SaleForm = () => {
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);
  const [clients, setClients] = useState<Client[]>([]);
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [loadingData, setLoadingData] = useState(true);
  const [formData, setFormData] = useState<SaleFormData>({
    client: "",
    vehicle: "",
    seller: "",
    saleDate: "",
    salePrice: "",
    installmentQuantity: "",
  });

  const fetchData = async () => {
    try {
      setLoadingData(true);
      console.log('Iniciando busca de dados para SaleForm...');
      
      console.log('Buscando clientes...');
      const clientsResponse = await apiService.getClients();
      console.log('Clientes carregados:', clientsResponse.length);
      setClients(clientsResponse);
      
      console.log('Buscando veículos...');
      const vehiclesResponse = await apiService.getVehicles();
      console.log('Veículos carregados:', vehiclesResponse.length);
      console.log('Todos os veículos:', vehiclesResponse);
      
      const availableVehicles = vehiclesResponse.filter(v => v.status === 'Disponível');
      console.log('Veículos disponíveis:', availableVehicles.length);
      console.log('Veículos disponíveis detalhes:', availableVehicles);
      setVehicles(availableVehicles);
      
      console.log('Buscando vendedores...');
      const sellersResponse = await apiService.getSellers();
      console.log('Vendedores carregados:', sellersResponse.length);
      setSellers(sellersResponse);
      
      console.log('Todos os dados carregados com sucesso');
    } catch (error: any) {
      console.error('Erro ao buscar dados:', error);
      console.error('Erro detalhado:', {
        message: error.message,
        status: error.status,
        stack: error.stack
      });
      toast({
        title: "Erro",
        description: "Não foi possível carregar os dados necessários. Verifique se o servidor está rodando.",
        variant: "destructive",
      });
    } finally {
      setLoadingData(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const mapToAPIData = (formData: SaleFormData): SaleAPIData => {
    return {
      client: formData.client,
      vehicle: formData.vehicle,
      seller: formData.seller,
      saleDate: `${formData.saleDate}T12:00:00`,
      salePrice: parseFloat(formData.salePrice),
      installmentQuantity: parseInt(formData.installmentQuantity),
    };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.client || !formData.vehicle || !formData.seller || !formData.saleDate || !formData.salePrice || !formData.installmentQuantity) {
      toast({
        title: "Erro",
        description: "Por favor, preencha todos os campos obrigatórios",
        variant: "destructive",
      });
      return;
    }

    try {
      setLoading(true);
      const apiData = mapToAPIData(formData);
      console.log("Dados para enviar à API:", apiData);

      await apiService.createSale(apiData);
      
      toast({
        title: "Sucesso!",
        description: "Venda cadastrada com sucesso",
      });

      // Reset form
      setFormData({
        client: "",
        vehicle: "",
        seller: "",
        saleDate: "",
        salePrice: "",
        installmentQuantity: "",
      });
    } catch (error) {
      const errorMessage = handleAPIError(error);
      toast({
        title: "Erro",
        description: errorMessage,
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (field: keyof SaleFormData, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const selectedVehicle = vehicles.find(vehicle => vehicle.id === formData.vehicle);

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="client">Cliente *</Label>
          <Select value={formData.client} onValueChange={(value) => handleInputChange("client", value)}>
            <SelectTrigger>
              <SelectValue placeholder={loadingData ? "Carregando clientes..." : "Selecione um cliente"} />
            </SelectTrigger>
            <SelectContent>
              {clients.length === 0 && !loadingData ? (
                <div className="p-2 text-sm text-gray-500">Nenhum cliente encontrado</div>
              ) : (
                clients.map((client) => (
                  <SelectItem key={client.id} value={client.id}>
                    {client.name} - {client.phone}
                  </SelectItem>
                ))
              )}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="vehicle">Veículo *</Label>
          <Select value={formData.vehicle} onValueChange={(value) => handleInputChange("vehicle", value)}>
            <SelectTrigger>
              <SelectValue placeholder={loadingData ? "Carregando veículos..." : "Selecione um veículo"} />
            </SelectTrigger>
            <SelectContent>
              {vehicles.length === 0 && !loadingData ? (
                <div className="p-2 text-sm text-gray-500">Nenhum veículo disponível encontrado</div>
              ) : (
                vehicles.map((vehicle) => (
                  <SelectItem key={vehicle.id} value={vehicle.id}>
                    {vehicle.brand} {vehicle.model} {vehicle.year} - R$ {parseFloat(vehicle.price).toLocaleString()}
                  </SelectItem>
                ))
              )}
            </SelectContent>
          </Select>
        </div>

        {selectedVehicle && (
          <div className="md:col-span-2 p-4 bg-gray-50 rounded-lg">
            <h4 className="font-semibold mb-2">Detalhes do Veículo</h4>
            <p><strong>Marca/Modelo:</strong> {selectedVehicle.brand} {selectedVehicle.model}</p>
            <p><strong>Ano:</strong> {selectedVehicle.year}</p>
            <p><strong>Cor:</strong> {selectedVehicle.color}</p>
            <p><strong>Placa:</strong> {selectedVehicle.plate}</p>
            <p><strong>Preço:</strong> R$ {parseFloat(selectedVehicle.price).toLocaleString()}</p>
          </div>
        )}

        <div className="space-y-2">
          <Label htmlFor="seller">Vendedor *</Label>
          <Select value={formData.seller} onValueChange={(value) => handleInputChange("seller", value)}>
            <SelectTrigger>
              <SelectValue placeholder={loadingData ? "Carregando vendedores..." : "Selecione um vendedor"} />
            </SelectTrigger>
            <SelectContent>
              {sellers.length === 0 && !loadingData ? (
                <div className="p-2 text-sm text-gray-500">Nenhum vendedor encontrado</div>
              ) : (
                sellers.map((seller) => (
                  <SelectItem key={seller.id} value={seller.id}>
                    {seller.name} {seller.manager && "(Gerente)"}
                  </SelectItem>
                ))
              )}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="saleDate">Data da Venda *</Label>
          <Input
            id="saleDate"
            type="date"
            value={formData.saleDate}
            onChange={(e) => handleInputChange("saleDate", e.target.value)}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="salePrice">Preço da Venda *</Label>
          <Input
            id="salePrice"
            type="number"
            step="0.01"
            value={formData.salePrice}
            onChange={(e) => handleInputChange("salePrice", e.target.value)}
            placeholder="0,00"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="installmentQuantity">Quantidade de Parcelas *</Label>
          <Input
            id="installmentQuantity"
            type="number"
            min="1"
            value={formData.installmentQuantity}
            onChange={(e) => handleInputChange("installmentQuantity", e.target.value)}
            placeholder="1"
            required
          />
        </div>
      </div>

      <Button type="submit" className="w-full bg-green-600 hover:bg-green-700" disabled={loading}>
        <ShoppingCart className="mr-2 h-4 w-4" />
        {loading ? "Cadastrando..." : "Cadastrar Venda"}
      </Button>
    </form>
  );
};

export default SaleForm;
