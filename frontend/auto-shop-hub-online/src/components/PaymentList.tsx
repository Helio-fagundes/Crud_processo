
import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { apiService } from "@/services/api";
import { useToast } from "@/hooks/use-toast";

const PaymentList = () => {
  const [payments, setPayments] = useState<Awaited<ReturnType<typeof apiService.getPayments>>>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  const fetchPayments = async () => {
    try {
      setLoading(true);
      const response = await apiService.getPayments();
      setPayments(response);
    } catch (error: any) {
      console.error('Erro ao buscar pagamentos:', error);
      toast({
        title: "Erro",
        description: "Não foi possível carregar os pagamentos",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPayments();
  }, []);

  const getStatusBadge = (received: boolean) => {
    return received ? (
      <Badge variant="default" className="bg-green-500">Recebido</Badge>
    ) : (
      <Badge variant="destructive">Pendente</Badge>
    );
  };

  const getMovementBadge = (movement: string) => {
    const movementLower = movement.toLowerCase();
    return movementLower === "entrada" ? (
      <Badge variant="default" className="bg-blue-500">Entrada</Badge>
    ) : movementLower === "reservado" ? (
      <Badge variant="default" className="bg-yellow-500">Reservado</Badge>
    ) : (
      <Badge variant="secondary">Saída</Badge>
    );
  };

  const getPaymentTypeBadge = (type: string) => {
    const typeLower = type.toLowerCase();
    return typeLower === "parcelado" ? (
      <Badge variant="secondary">Parcelado</Badge>
    ) : (
      <Badge variant="default">À Vista</Badge>
    );
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="text-gray-500">Carregando pagamentos...</div>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {payments.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          Nenhum pagamento cadastrado ainda.
        </div>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Cliente</TableHead>
              <TableHead>Veículo</TableHead>
              <TableHead>Vendedor</TableHead>
              <TableHead>Tipo</TableHead>
              <TableHead>Método</TableHead>
              <TableHead>Valor</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Movimento</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {payments.map((payment) => (
              <TableRow key={payment.id}>
                <TableCell>
                  <div>
                    <div className="font-medium">{payment.client.name}</div>
                    <div className="text-sm text-gray-500">{payment.client.phone}</div>
                  </div>
                </TableCell>
                <TableCell>
                  <div>
                    <div className="font-medium">{payment.vehicle.model}</div>
                    <div className="text-sm text-gray-500">{payment.vehicle.color} - {payment.vehicle.plate}</div>
                  </div>
                </TableCell>
                <TableCell>
                  <div>
                    <div className="font-medium">{payment.seller.name}</div>
                    <div className="text-sm text-gray-500">{payment.seller.phone}</div>
                  </div>
                </TableCell>
                <TableCell>{getPaymentTypeBadge(payment.paymentType)}</TableCell>
                <TableCell className="capitalize">{payment.paymentMethod.replace('_', ' ')}</TableCell>
                <TableCell>R$ {payment.amount.toLocaleString()}</TableCell>
                <TableCell>{getStatusBadge(payment.received)}</TableCell>
                <TableCell>{getMovementBadge(payment.movement)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </div>
  );
};

export default PaymentList;
