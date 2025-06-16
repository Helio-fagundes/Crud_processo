import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { User } from "lucide-react";
import { handleAPIError } from "@/utils/apiErrorHandler";
import { apiService } from "@/services/api";

interface ClientFormData {
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  endereco: string;
  numero: string;
  complemento: string;
  bairro: string;
  cidade: string;
  estado: string;
}

interface ClientAPIData {
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

const ClientForm = () => {
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState<ClientFormData>({
    nome: "",
    cpf: "",
    telefone: "",
    email: "",
    endereco: "",
    numero: "",
    complemento: "",
    bairro: "",
    cidade: "",
    estado: "",
  });

  const mapToAPIData = (formData: ClientFormData): ClientAPIData => {
    return {
      name: formData.nome,
      socialSecurity: formData.cpf,
      phone: formData.telefone,
      email: formData.email,
      address: formData.endereco,
      number: formData.numero,
      complement: formData.complemento,
      neighborhood: formData.bairro,
      city: formData.cidade,
      state: formData.estado,
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

      await apiService.createClient(apiData);
      
      toast({
        title: "Sucesso!",
        description: "Cliente cadastrado com sucesso",
      });

      // Reset form
      setFormData({
        nome: "",
        cpf: "",
        telefone: "",
        email: "",
        endereco: "",
        numero: "",
        complemento: "",
        bairro: "",
        cidade: "",
        estado: "",
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

  const handleInputChange = (field: keyof ClientFormData, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2 md:col-span-2">
          <Label htmlFor="nome">Nome Completo *</Label>
          <Input
            id="nome"
            value={formData.nome}
            onChange={(e) => handleInputChange("nome", e.target.value)}
            placeholder="João da Silva"
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

        <div className="space-y-2 md:col-span-2">
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            type="email"
            value={formData.email}
            onChange={(e) => handleInputChange("email", e.target.value)}
            placeholder="joao@email.com"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="endereco">Endereço</Label>
          <Input
            id="endereco"
            value={formData.endereco}
            onChange={(e) => handleInputChange("endereco", e.target.value)}
            placeholder="Rua das Flores"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="numero">Número</Label>
          <Input
            id="numero"
            value={formData.numero}
            onChange={(e) => handleInputChange("numero", e.target.value)}
            placeholder="123"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="complemento">Complemento</Label>
          <Input
            id="complemento"
            value={formData.complemento}
            onChange={(e) => handleInputChange("complemento", e.target.value)}
            placeholder="Apt 45"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="bairro">Bairro</Label>
          <Input
            id="bairro"
            value={formData.bairro}
            onChange={(e) => handleInputChange("bairro", e.target.value)}
            placeholder="Centro"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="cidade">Cidade</Label>
          <Input
            id="cidade"
            value={formData.cidade}
            onChange={(e) => handleInputChange("cidade", e.target.value)}
            placeholder="São Paulo"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="estado">Estado</Label>
          <Input
            id="estado"
            value={formData.estado}
            onChange={(e) => handleInputChange("estado", e.target.value)}
            placeholder="SP"
          />
        </div>
      </div>

      <Button type="submit" className="w-full bg-green-600 hover:bg-green-700" disabled={loading}>
        <User className="mr-2 h-4 w-4" />
        {loading ? "Cadastrando..." : "Cadastrar Cliente"}
      </Button>
    </form>
  );
};

export default ClientForm;
