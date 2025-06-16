import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { useToast } from "@/hooks/use-toast";
import { Car } from "lucide-react";
import { handleAPIError } from "@/utils/apiErrorHandler";
import { apiService } from "@/services/api";

interface VehicleFormData {
  marca: string;
  modelo: string;
  cor: string;
  ano: string;
  placa: string;
  chassi: string;
  tipoCombustivel: string;
  preco: string;
  status: string;
  disponivel: boolean;
}

interface VehicleAPIData {
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

const VehicleForm = () => {
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState<VehicleFormData>({
    marca: "",
    modelo: "",
    cor: "",
    ano: "",
    placa: "",
    chassi: "",
    tipoCombustivel: "",
    preco: "",
    status: "",
    disponivel: true,
  });

  const mapToAPIData = (formData: VehicleFormData): VehicleAPIData => {
    return {
      brand: formData.marca,
      model: formData.modelo,
      year: formData.ano,
      color: formData.cor,
      plate: formData.placa,
      chassis: formData.chassi,
      fuelType: formData.tipoCombustivel,
      price: formData.preco,
      status: formData.status,
      released: formData.disponivel,
    };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.marca || !formData.modelo || !formData.placa) {
      toast({
        title: "Erro",
        description: "Por favor, preencha os campos obrigatórios",
        variant: "destructive",
      });
      return;
    }

    try {
      setLoading(true);
      const apiData = mapToAPIData(formData);
      console.log("Dados para enviar à API:", apiData);
      
      await apiService.createVehicle(apiData);
      
      toast({
        title: "Sucesso!",
        description: "Veículo cadastrado com sucesso",
      });

      // Reset form
      setFormData({
        marca: "",
        modelo: "",
        cor: "",
        ano: "",
        placa: "",
        chassi: "",
        tipoCombustivel: "",
        preco: "",
        status: "",
        disponivel: true,
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

  const handleInputChange = (field: keyof VehicleFormData, value: string | boolean) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="marca">Marca *</Label>
          <Input
            id="marca"
            value={formData.marca}
            onChange={(e) => handleInputChange("marca", e.target.value)}
            placeholder="Toyota, Honda, Ford..."
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="modelo">Modelo *</Label>
          <Input
            id="modelo"
            value={formData.modelo}
            onChange={(e) => handleInputChange("modelo", e.target.value)}
            placeholder="Corolla, Civic, Focus..."
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="cor">Cor</Label>
          <Input
            id="cor"
            value={formData.cor}
            onChange={(e) => handleInputChange("cor", e.target.value)}
            placeholder="Branco, Preto, Prata..."
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="ano">Ano</Label>
          <Input
            id="ano"
            value={formData.ano}
            onChange={(e) => handleInputChange("ano", e.target.value)}
            placeholder="2023"
            type="number"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="placa">Placa *</Label>
          <Input
            id="placa"
            value={formData.placa}
            onChange={(e) => handleInputChange("placa", e.target.value)}
            placeholder="ABC-1234"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="chassi">Chassi</Label>
          <Input
            id="chassi"
            value={formData.chassi}
            onChange={(e) => handleInputChange("chassi", e.target.value)}
            placeholder="Número do chassi"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="combustivel">Tipo de Combustível</Label>
          <Select onValueChange={(value) => handleInputChange("tipoCombustivel", value)}>
            <SelectTrigger>
              <SelectValue placeholder="Selecione o combustível" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="gasolina">Gasolina</SelectItem>
              <SelectItem value="etanol">Etanol</SelectItem>
              <SelectItem value="flex">Flex</SelectItem>
              <SelectItem value="diesel">Diesel</SelectItem>
              <SelectItem value="gnv">GNV</SelectItem>
              <SelectItem value="eletrico">Elétrico</SelectItem>
              <SelectItem value="hibridoa">Híbrido</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="preco">Preço (R$)</Label>
          <Input
            id="preco"
            value={formData.preco}
            onChange={(e) => handleInputChange("preco", e.target.value)}
            placeholder="50000"
            type="number"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="status">Status</Label>
          <Select onValueChange={(value) => handleInputChange("status", value)}>
            <SelectTrigger>
              <SelectValue placeholder="Selecione o status" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="novo">Novo</SelectItem>
              <SelectItem value="seminovo">Seminovo</SelectItem>
              <SelectItem value="usado">Usado</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="flex items-center space-x-2 md:col-span-2">
          <Switch
            id="disponivel"
            checked={formData.disponivel}
            onCheckedChange={(checked) => handleInputChange("disponivel", checked)}
          />
          <Label htmlFor="disponivel">Veículo disponível para venda</Label>
        </div>
      </div>

      <Button type="submit" className="w-full bg-blue-600 hover:bg-blue-700" disabled={loading}>
        <Car className="mr-2 h-4 w-4" />
        {loading ? "Cadastrando..." : "Cadastrar Veículo"}
      </Button>
    </form>
  );
};

export default VehicleForm;
