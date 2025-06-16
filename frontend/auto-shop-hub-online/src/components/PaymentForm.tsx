
import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { useToast } from "@/hooks/use-toast";
import { CreditCard } from "lucide-react";
import { handleAPIError } from "@/utils/apiErrorHandler";
import { apiService } from "@/services/api";

interface PaymentFormData {
  saleId: string;
  paymentType: string;
  paymentMethod: string;
  installment: boolean;
  amount: string;
  received: boolean;
  movement: string;
}

interface PaymentAPIData {
  saleId: string;
  paymentType: string;
  paymentMethod: string;
  installment: boolean;
  amount: number;
  received: boolean;
  movement: string;
}

const PaymentForm = () => {
  const { toast } = useToast();
  const [loading, setLoading] = useState(false);
  const [sales, setSales] = useState<Awaited<ReturnType<typeof apiService.getSales>>>([]);
  const [loadingSales, setLoadingSales] = useState(true);
  const [formData, setFormData] = useState<PaymentFormData>({
    saleId: "",
    paymentType: "",
    paymentMethod: "",
    installment: false,
    amount: "",
    received: false,
    movement: "",
  });

  const fetchSales = async () => {
    try {
      setLoadingSales(true);
      const response = await apiService.getSales();
      setSales(response);
    } catch (error: any) {
      console.error('Erro ao buscar vendas:', error);
      toast({
        title: "Erro",
        description: "Não foi possível carregar as vendas",
        variant: "destructive",
      });
    } finally {
      setLoadingSales(false);
    }
  };

  useEffect(() => {
    fetchSales();
  }, []);

  const mapToAPIData = (formData: PaymentFormData): PaymentAPIData => {
    return {
      saleId: formData.saleId,
      paymentType: formData.paymentType,
      paymentMethod: formData.paymentMethod,
      installment: formData.installment,
      amount: parseFloat(formData.amount),
      received: formData.received,
      movement: formData.movement,
    };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.saleId || !formData.paymentType || !formData.paymentMethod || !formData.amount || !formData.movement) {
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

      await apiService.createPayment(apiData);
      
      toast({
        title: "Sucesso!",
        description: "Pagamento cadastrado com sucesso",
      });

      // Reset form
      setFormData({
        saleId: "",
        paymentType: "",
        paymentMethod: "",
        installment: false,
        amount: "",
        received: false,
        movement: "",
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

  const handleInputChange = (field: keyof PaymentFormData, value: string | boolean) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const selectedSale = sales.find(sale => sale.id === formData.saleId);

  const getClientName = (client: string | { name: string; cpf?: string; phone?: string }) => {
    return typeof client === 'string' ? client : client?.name || 'Cliente não encontrado';
  };

  const getVehicleName = (vehicle: string | { model: string; color?: string; plate?: string }) => {
    return typeof vehicle === 'string' ? vehicle : vehicle?.model || 'Veículo não encontrado';
  };

  const getSellerName = (seller: string | { name: string; cpf?: string; phone?: string }) => {
    return typeof seller === 'string' ? seller : seller?.name || 'Vendedor não encontrado';
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2 md:col-span-2">
          <Label htmlFor="saleId">Venda *</Label>
          <Select value={formData.saleId} onValueChange={(value) => handleInputChange("saleId", value)}>
            <SelectTrigger>
              <SelectValue placeholder={loadingSales ? "Carregando vendas..." : "Selecione uma venda"} />
            </SelectTrigger>
            <SelectContent>
              {sales.map((sale) => (
                <SelectItem key={sale.id} value={sale.id}>
                  {getClientName(sale.client)} - {getVehicleName(sale.vehicle)} (R$ {sale.salePrice.toLocaleString()})
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        {selectedSale && (
          <div className="md:col-span-2 p-4 bg-gray-50 rounded-lg">
            <h4 className="font-semibold mb-2">Detalhes da Venda</h4>
            <p><strong>Cliente:</strong> {getClientName(selectedSale.client)}</p>
            <p><strong>Veículo:</strong> {getVehicleName(selectedSale.vehicle)}</p>
            <p><strong>Vendedor:</strong> {getSellerName(selectedSale.seller)}</p>
            <p><strong>Data da Venda:</strong> {new Date(selectedSale.saleDate).toLocaleDateString()}</p>
            <p><strong>Valor:</strong> R$ {selectedSale.salePrice.toLocaleString()}</p>
            <p><strong>Parcelas:</strong> {selectedSale.installmentQuantity}x</p>
          </div>
        )}

        <div className="space-y-2">
          <Label htmlFor="paymentType">Tipo de Pagamento *</Label>
          <Select value={formData.paymentType} onValueChange={(value) => handleInputChange("paymentType", value)}>
            <SelectTrigger>
              <SelectValue placeholder="Selecione o tipo" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="entrada">Entrada</SelectItem>
              <SelectItem value="parcela">Parcela</SelectItem>
              <SelectItem value="quitacao">Quitação</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="paymentMethod">Método de Pagamento *</Label>
          <Select value={formData.paymentMethod} onValueChange={(value) => handleInputChange("paymentMethod", value)}>
            <SelectTrigger>
              <SelectValue placeholder="Selecione o método" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="dinheiro">Dinheiro</SelectItem>
              <SelectItem value="cartao_credito">Cartão de Crédito</SelectItem>
              <SelectItem value="cartao_debito">Cartão de Débito</SelectItem>
              <SelectItem value="pix">PIX</SelectItem>
              <SelectItem value="transferencia">Transferência</SelectItem>
              <SelectItem value="financiamento">Financiamento</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="amount">Valor *</Label>
          <Input
            id="amount"
            type="number"
            step="0.01"
            value={formData.amount}
            onChange={(e) => handleInputChange("amount", e.target.value)}
            placeholder="0,00"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="movement">Movimento *</Label>
          <Select value={formData.movement} onValueChange={(value) => handleInputChange("movement", value)}>
            <SelectTrigger>
              <SelectValue placeholder="Selecione o movimento" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="entrada">Entrada</SelectItem>
              <SelectItem value="saida">Saída</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="flex items-center space-x-2">
          <Switch
            id="installment"
            checked={formData.installment}
            onCheckedChange={(checked) => handleInputChange("installment", checked)}
          />
          <Label htmlFor="installment">Parcelado</Label>
        </div>

        <div className="flex items-center space-x-2">
          <Switch
            id="received"
            checked={formData.received}
            onCheckedChange={(checked) => handleInputChange("received", checked)}
          />
          <Label htmlFor="received">Recebido</Label>
        </div>
      </div>

      <Button type="submit" className="w-full bg-orange-600 hover:bg-orange-700" disabled={loading}>
        <CreditCard className="mr-2 h-4 w-4" />
        {loading ? "Cadastrando..." : "Cadastrar Pagamento"}
      </Button>
    </form>
  );
};

export default PaymentForm;
