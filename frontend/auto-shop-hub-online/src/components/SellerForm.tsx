import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { useToast } from "@/hooks/use-toast";
import { UserCheck } from "lucide-react";
import { handleAPIError } from "@/utils/apiErrorHandler";
import { apiService } from "@/services/api";

interface SellerFormData {
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  gerente: boolean;
}

interface SellerAPIData {
  name: string;
  cpf: string;
  phone: string;
  email: string;
  manager: boolean;
}

const SellerForm = () => {
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState<SellerFormData>({
    nome: "",
    cpf: "",
    telefone: "",
    email: "",
    gerente: false,
  });

  const mapToAPIData = (formData: SellerFormData): SellerAPIData => {
    return {
      name: formData.nome,
      cpf: formData.cpf,
      phone: formData.telefone,
      email: formData.email,
      manager: formData.gerente,
    };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.nome || !formData.cpf || !formData.telefone) {
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

      await apiService.createSeller(apiData);
      
      toast({
        title: "Sucesso!",
        description: "Vendedor cadastrado com sucesso",
      });

      // Reset form
      setFormData({
        nome: "",
        cpf: "",
        telefone: "",
        email: "",
        gerente: false,
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

  const handleInputChange = (field: keyof SellerFormData, value: string | boolean) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="space-y-4">
        <div className="space-y-2">
          <Label htmlFor="nome">Nome Completo *</Label>
          <Input
            id="nome"
            value={formData.nome}
            onChange={(e) => handleInputChange("nome", e.target.value)}
            placeholder="Maria Santos"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="cpf">CPF *</Label>
          <Input
            id="cpf"
            value={formData.cpf}
            onChange={(e) => handleInputChange("cpf", e.target.value)}
            placeholder="123.456.789-00"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="telefone">Telefone *</Label>
          <Input
            id="telefone"
            value={formData.telefone}
            onChange={(e) => handleInputChange("telefone", e.target.value)}
            placeholder="(11) 99999-9999"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            type="email"
            value={formData.email}
            onChange={(e) => handleInputChange("email", e.target.value)}
            placeholder="maria@autovendas.com"
          />
        </div>

        <div className="flex items-center space-x-2">
          <Switch
            id="gerente"
            checked={formData.gerente}
            onCheckedChange={(checked) => handleInputChange("gerente", checked)}
          />
          <Label htmlFor="gerente">É gerente?</Label>
        </div>
      </div>

      <Button type="submit" className="w-full bg-purple-600 hover:bg-purple-700" disabled={loading}>
        <UserCheck className="mr-2 h-4 w-4" />
        {loading ? "Cadastrando..." : "Cadastrar Vendedor"}
      </Button>
    </form>
  );
};

export default SellerForm;
