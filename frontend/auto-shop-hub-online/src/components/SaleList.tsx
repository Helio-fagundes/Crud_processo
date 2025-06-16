
import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { apiService } from "@/services/api";
import { useToast } from "@/hooks/use-toast";

const SaleList = () => {
  const [sales, setSales] = useState<Awaited<ReturnType<typeof apiService.getSales>>>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  const fetchSales = async () => {
    try {
      setLoading(true);
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
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSales();
  }, []);

  const getInstallmentBadge = (quantity: number) => {
    if (quantity === 1) {
      return <Badge variant="default" className="bg-green-500">À Vista</Badge>;
    }
    return <Badge variant="secondary">{quantity}x Parcelas</Badge>;
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="text-gray-500">Carregando vendas...</div>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {sales.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          Nenhuma venda cadastrada ainda.
        </div>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Cliente</TableHead>
              <TableHead>Veículo</TableHead>
              <TableHead>Vendedor</TableHead>
              <TableHead>Data</TableHead>
              <TableHead>Valor</TableHead>
              <TableHead>Parcelas</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {sales.map((sale) => (
              <TableRow key={sale.id}>
                <TableCell>
                  {typeof sale.client === 'string' ? sale.client : 
                    typeof sale.client === 'object' && sale.client?.name ? sale.client.name : 
                    'Cliente não encontrado'}
                </TableCell>
                <TableCell>
                  {typeof sale.vehicle === 'string' ? sale.vehicle : 
                    typeof sale.vehicle === 'object' && sale.vehicle?.model ? sale.vehicle.model : 
                    'Veículo não encontrado'}
                </TableCell>
                <TableCell>
                  {typeof sale.seller === 'string' ? sale.seller : 
                    typeof sale.seller === 'object' && sale.seller?.name ? sale.seller.name : 
                    'Vendedor não encontrado'}
                </TableCell>
                <TableCell>{new Date(sale.saleDate).toLocaleDateString()}</TableCell>
                <TableCell>R$ {sale.salePrice.toLocaleString()}</TableCell>
                <TableCell>{getInstallmentBadge(sale.installmentQuantity)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </div>
  );
};

export default SaleList;
